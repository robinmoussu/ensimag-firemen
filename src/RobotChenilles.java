// Description d'un robot CHENILLES
// Dernière modification : Thibaud BACKENSTRASS, 10 novembre
public class RobotChenilles extends Robot {
    // Attributs
    static private int vitesse = 60; // <=80
    static final int volumeMax = 2000;
    static final int tempsRemplissage = 300;
    static final int volumeIntervention = 100;
    static final int dureeIntervention = 8;

    // Constructeurs
    public RobotChenilles(Case pos) {
        super(pos, vitesse, volumeMax, tempsRemplissage, volumeIntervention, dureeIntervention);
    }
    public RobotChenilles(Case pos, int vitesse) {
        this(pos);
        if(vitesse>0 && vitesse<=80) {
            this.vitesse = vitesse;
        }
    }
    
    // Pas de mutateurs : la vitesse ne change pas en cours de route !

    // Renvoie le type du robot
    @Override
    public TypeRobot getTypeRobot() {
        return TypeRobot.CHENILLES;
    }

    // Renvoie la vitesse du robot en fonction du terrain
    @Override
    public int getVitesse(NatureTerrain terrain) {
        if(terrain == NatureTerrain.EAU || terrain == NatureTerrain.ROCHE) {
            return 0;
        }
        else if(terrain == NatureTerrain.FORET) {
            return this.vitesse/2;
        }
        else {
            return this.vitesse;
        }
    }

    // Déplacer le robot sur une case
    // On doit vérifier que les cases sont voisines, et que la nature du terrain soit compatible
    @Override
    public void deplacer(Case c) {
        if(c.estVoisine(this.getPosition())==false || c.getTerrain()==NatureTerrain.EAU || c.getTerrain()==NatureTerrain.ROCHE)  {
            throw new ArithmeticException("Case inaccessible pour le robot sélectionné !");
        }
        else {
            this.setPosition(c);
        }
    }

    // Remplir le réservoir d'eau si la case le permet
    @Override
    public void remplirReservoir(Carte carte) {
        if(carte.estBordEau(this.getPosition()) != true) {
            throw new ArithmeticException("Impossible de remplir le réservoir sur la case actuelle !");
        }
        else {
            this.volumeEau = this.volumeMax;
        }
    } 

}
