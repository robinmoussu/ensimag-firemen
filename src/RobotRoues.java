// Description d'un robot ROUES
// Dernière modification : Thibaud BACKENSTRASS, 10 novembre
public class RobotRoues extends Robot {
    // Attributs
    static private int vitesse = 80;
    static final int volumeMax = 5000;
    static final int tempsRemplissage = 600;
    static final int volumeIntervention = 100;
    static final int dureeIntervention = 5;

    // Constructeurs
    public RobotRoues(Case pos) {
        super(pos, vitesse, volumeMax, tempsRemplissage, volumeIntervention, dureeIntervention);
        volumeEau = volumeMax; // Remplir le réservoir
    }
    public RobotRoues(Case pos, int vitesse) {
        this(pos);
        if(vitesse > 0) {
            this.vitesse = vitesse; // Aucune contrainte sur la vitesse ?
        }
    }
    
    // Pas de mutateurs : la vitesse ne change pas en cours de route !
    
    // Renvoie la vitesse du robot en fonction du terrain
    @Override
    public int getVitesse(NatureTerrain terrain) {
        if(terrain == NatureTerrain.TERRAIN_LIBRE || terrain == NatureTerrain.HABITAT) {
            return this.vitesse;
        }
        else {
            return 0;
        }
    }

    // Renvoie l'image du robot
    public String getImage() {
        return "images/roues.png";
    }

    // Déplacer le robot sur une case
    // On doit vérifier que les cases sont voisines, et que la nature du terrain soit compatible
    @Override
    public void deplacer(Case c) throws SimulationException {
        if(c.estVoisine(this.getPosition())==false || (c.getTerrain()!=NatureTerrain.TERRAIN_LIBRE && c.getTerrain()!=NatureTerrain.HABITAT) ) {
            throw new SimulationException("Case inaccessible pour le robot sélectionné !");
        }
        
        this.setPosition(c);
    }

    // Remplir le réservoir d'eau si la case le permet
    @Override
    public void remplirReservoir(Carte carte) throws SimulationException {
        if(carte.estBordEau(this.getPosition()) != true) {
            throw new SimulationException("Impossible de remplir le réservoir sur la case actuelle !");
        }
        
        this.volumeEau = this.volumeMax;
    } 

}
