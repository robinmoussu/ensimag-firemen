/**
 * Description d'un robot à roues.
 * @author Thibaud Backenstrass
 * @date 2014-11-17
 */
public class RobotRoues extends Robot {
    static private int vitesse = 80;
    static final int volumeMax = 5000;
    static final int tempsRemplissage = 600;
    static final int volumeIntervention = 100;
    static final int dureeIntervention = 5;


    /**
     * Constructeur de robot à roues.
     * @param pos Case sur laquelle le robot est crée
     */
    public RobotRoues(Case pos) {
        super(pos, vitesse, volumeMax, tempsRemplissage, volumeIntervention,
                dureeIntervention);
        volumeEau = volumeMax; // Remplir le réservoir

    }
    /**
     * Constructeur de robot à roues.
     * @param pos Case sur laquelle le robot est crée
     * @param vitesse Vitesse du robot, ignorée si invalide
     */
    public RobotRoues(Case pos, int vitesse) {
        this(pos);
        if(vitesse > 0) {
            this.vitesse = vitesse; // Aucune contrainte sur la vitesse ?
        }
    }
    
    
    /**
     * Accesseur sur la vitesse du robot en fonction de la nature du terrain.
     * @param terrain Nature du terrain sur laquelle est le robot
     * @return Vitesse du robot sur la case courante
     */
    @Override
    public int getVitesse(NatureTerrain terrain) {
        if(terrain == NatureTerrain.TERRAIN_LIBRE
                || terrain == NatureTerrain.HABITAT) {
            return this.vitesse;
        }
        else {
            return 0;
        }
    }

    
    /**
     * Image représentant le robot dans l'interface graphique.
     * @return Chaîne de caractères indiquant l'emplacement de l'image PNG
     */
    public String getImage() {
        return "images/roues.png";
    }


    /**
     * Déplacer le robot sur une case voisine de terrain compatible.
     * @param c Case sur laquelle déplacer le robot
     * @throws SimulationException si cette case n'est pas accessible au robot
     */
    @Override
    public void deplacer(Case c) throws SimulationException {
        System.out.println (this.position.toString() + " " + this.position.getTerrain().toString());
        System.out.println (c.toString() + " " + c.getTerrain().toString());
        if(c.estVoisine(this.getPosition())==false
                || (c.getTerrain()!=NatureTerrain.TERRAIN_LIBRE
                && c.getTerrain()!=NatureTerrain.HABITAT) ) {
            throw new SimulationException("Case inaccessible pour le robot "
                    + "à roue sélectionné ! ");
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
        return carte.estBordEau(this.position);
    }

    /**
     * Remplir le réservoir d'eau du robot si la case le permet.
     * @param carte Carte sur laquelle le robot évolue
     * @throws SimulationException si la case courante ne permet pas un
     * remplissage du réservoir
     */
    @Override
    public void remplirReservoir(Carte carte) throws SimulationException {
        if(carte.estBordEau(this.getPosition()) != true) {
            throw new SimulationException("Impossible de remplir le réservoir "
                    + "depuis la case actuelle " + this.position.toString() + "!");
        }
        
        this.volumeEau = this.volumeMax;
    }
}
