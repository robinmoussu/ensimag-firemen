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
    }
    public RobotRoues(Case pos, int vitesse) {
        this(pos);
        this.vitesse = vitesse; // Aucune contrainte sur la vitesse ?
    }
    
    // Pas de mutateurs : la vitesse ne change pas en cours de route !
    
    // Renvoie le type du robot
    @Override
    public TypeRobot getTypeRobot() {
        return TypeRobot.ROUES;
    }

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

    // Déplacer le robot sur une case
    // On doit vérifier que les cases sont voisines, et que la nature du terrain soit compatible
    @Override
    public void deplacer(Case c) {
        if(c.estVoisine(this.getPosition())==false || (c.getTerrain()!=NatureTerrain.TERRAIN_LIBRE && c.getTerrain()!=NatureTerrain.HABITAT) ) {
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
