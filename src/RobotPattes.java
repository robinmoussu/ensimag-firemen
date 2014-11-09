// Description d'un robot PATTES
// Dernière modification : Thibaud BACKENSTRASS, 9 novembre
public class RobotPattes extends Robot {
    // Attributs
    static private int vitesse = 30;
    static final int volumeMax = Integer.MAX_VALUE; // Réservoir infini
    static final int tempsRemplissage = 0;
    static final int volumeIntervention = 10;
    static final int dureeIntervention = 1;

    // Constructeurs
    public RobotPattes(Case pos) {
        super(pos, vitesse, volumeMax, tempsRemplissage, volumeIntervention, dureeIntervention);
    }
    public RobotPattes(Case pos, int vitesse) {
        this(pos);
        this.vitesse = vitesse; // Pas de contrainte sur la vitesse ?
    }
    
    // Pas de mutateurs : la vitesse ne change pas en cours de route !

    // Renvoie la vitesse du robot en fonction du terrain
    @Override
    public int getVitesse(NatureTerrain terrain) {
        if(terrain == NatureTerrain.EAU) {
            return 0;
        }
        else if(terrain == NatureTerrain.ROCHE) {
            return this.vitesse/3; // Sujet peu précis : la vitesse est divisée par trois ou égale à 10km/h ?
        }
        else {
            return this.vitesse;
        }
    }

    // Déplacer le robot sur une case
    // On doit vérifier que les cases sont voisines, et que la nature du terrain soit compatible
    @Override
    public void deplacer(Case c) {
        if(c.estVoisine(this.getPosition())==false || c.getTerrain()==NatureTerrain.EAU)  {
            throw new ArithmeticException("Case inaccessible pour le robot sélectionné !");
        }
        else {
            this.setPosition(c);
        }
    }

    // Déverser le réservoir d'eau
    // La méthode doit être redéfinie, car le robot à pattes ne se vide jamais
    @Override
    public void deverserEau(int nbInterventions) {
        return; // Ne rien faire...
    }

    // Remplir le réservoir d'eau si la case le permet
    @Override
    public void remplirReservoir(Carte carte) {
        // Action à adopter en cas de remplissage : exception ?
        this.volumeEau = this.volumeMax;
    } 

}
