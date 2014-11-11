// Description d'un robot DRONE
// Dernière modification : Thibaud BACKENSTRASS, 10 novembre
public class RobotDrone extends Robot {
    // Attributs
    static private int vitesse = 100; // <150
    static final int volumeMax = 10000;
    static final int tempsRemplissage = 1800;
    static final int volumeIntervention = 10000;
    static final int dureeIntervention = 30;

    // Constructeurs
    public RobotDrone(Case pos) {
        super(pos, vitesse, volumeMax, tempsRemplissage, volumeIntervention, dureeIntervention);
    }
    public RobotDrone(Case pos, int vitesse) {
        this(pos);
        if(vitesse>0 && vitesse<=150) {
            this.vitesse = vitesse;
        }
    }
    
    // Pas de mutateurs : la vitesse ne change pas en cours de route !
    
    // Renvoie la vitesse du robot en fonction du terrain
    @Override
    public int getVitesse(NatureTerrain terrain) {
        return this.vitesse;
    }

    // Renvoie l'image du robot
    @Override
    public String getImage() {
        return "images/drone.png";
    }

    // Déplacer le robot sur une case
    // On doit vérifier que les cases sont voisines, et que la nature du terrain soit compatible
    @Override
    public void deplacer(Case c) {
        if(c.estVoisine(this.getPosition())==false) {
            throw new ArithmeticException("Case inaccessible pour le robot sélectionné !");
        }
        else {
            this.setPosition(c);
        }
    }

    // Remplir le réservoir d'eau si la case le permet
    @Override
    public void remplirReservoir(Carte carte) {
        if((this.getPosition()).getTerrain() != NatureTerrain.EAU) {
            throw new ArithmeticException("Impossible de remplir le réservoir sur la case actuelle !");
        }
        else {
           this.volumeEau = this.volumeMax;
        }
    }

}
