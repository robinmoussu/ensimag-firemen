/**
 * Description d'un robot drone.
 * @author Thibaud Backenstrass
 * @date 2014-11-17
 */
public class RobotDrone extends Robot {
    static private int vitesse = 100; // <150
    static final int volumeMax = 10000;
    static final int tempsRemplissage = 1800;
    static final int volumeIntervention = 10000;
    static final int dureeIntervention = 30;


    /**
     * Constructeur de robot drone.
     * @param pos Case sur laquelle le robot est crée
     */
    public RobotDrone(Case pos) {
        super(pos, vitesse, volumeMax, tempsRemplissage, volumeIntervention, dureeIntervention);
        volumeEau = volumeMax;
    }
    /**
     * Constructeur de robot drone.
     * @param pos Case sur laquelle le robot est crée
     * @param vitesse Vitesse du robot, ignorée si invalide
     */
    public RobotDrone(Case pos, int vitesse) {
        this(pos);
        if(vitesse>0 && vitesse<=150) {
            this.vitesse = vitesse;
        }
    }
    
    
    /**
     * Accesseur sur la vitesse du robot en fonction de la nature du terrain.
     * @param terrain Nature du terrain sur laquelle est le robot
     * @return Vitesse du robot sur la case courante
     */
    @Override
    public int getVitesse(NatureTerrain terrain) {
        return this.vitesse;
    }

    
    /**
     * Image représentant le robot dans l'interface graphique.
     * @return Chaîne de caractères indiquant l'emplacement de l'image PNG
     */
    @Override
    public String getImage() {
        return "images/drone.png";
    }

   
    /**
     * Déplacer le robot sur une case voisine de terrain compatible.
     * @param c Case sur laquelle déplacer le robot
     * @throws SimulationException si cette case n'est pas accessible au robot
     */
    @Override
    public void deplacer(Case c) throws SimulationException {
        if(c.estVoisine(this.getPosition())==false) {
            throw new SimulationException("Case inaccessible pour le robot sélectionné !");
        }
        
        this.setPosition(c);
    }

    /**
     * Savoir si le robot peut être rempli sur la case courante.
     * @param carte Carte sur laquelle le robot se trouve
     * @return true si le robot peut être rempli
     */
    @Override
    public boolean estRemplissable(Carte carte) {
        return this.getPosition().getTerrain() == NatureTerrain.EAU;
    }
    
    /**
     * Remplir le réservoir d'eau du robot si la case le permet.
     * @param carte Carte sur laquelle le robot évolue
     * @throws SimulationException si la case courante ne permet pas un remplissage du réservoir
     */
    @Override
    public void remplirReservoir(Carte carte) throws SimulationException {
        if((this.getPosition()).getTerrain() != NatureTerrain.EAU) {
            throw new SimulationException("Impossible de remplir le réservoir sur la case actuelle !");
        }
        
        this.volumeEau = this.volumeMax;
    }


}
