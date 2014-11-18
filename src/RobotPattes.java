/**
 * Description d'un robot à pattes.
 * @author Thibaud Backenstrass
 * @date 2014-11-17
 */
public class RobotPattes extends Robot {
    static private int vitesse = 30;
    static final int volumeMax = Integer.MAX_VALUE; // Réservoir infini
    static final int tempsRemplissage = 0;
    static final int volumeIntervention = 10;
    static final int dureeIntervention = 1;

    
    /**
     * Constructeur de robot à pattes.
     * @param pos Case sur laquelle le robot est crée
     */
    public RobotPattes(Case pos) {
        super(pos, vitesse, volumeMax, tempsRemplissage, volumeIntervention, dureeIntervention);
        volumeEau = volumeMax;
    }
    /**
     * Constructeur de robot à pattes.
     * @param pos Case sur laquelle le robot est crée
     * @param vitesse Vitesse du robot, ignorée si invalide
     */
    public RobotPattes(Case pos, int vitesse) {
        this(pos);
        if(vitesse > 0) {
            this.vitesse = vitesse; // Pas de contrainte sur la vitesse ?
        }
    }
   

    /**
     * Accesseur sur la vitesse du robot en fonction de la nature du terrain.
     * @param terrain Nature du terrain sur laquelle est le robot
     * @return Vitesse du robot sur la case courante
     */
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

    
    /**
     * Image représentant le robot dans l'interface graphique.
     * @return Chaîne de caractères indiquant l'emplacement de l'image PNG
     */
    @Override
    public String getImage() {
        return "images/pattes.png";
    }


    /**
     * Déplacer le robot sur une case voisine de terrain compatible.
     * On s'assure également que le réservoir de ce robot est toujours plein.
     * @param c Case sur laquelle déplacer le robot
     * @throws SimulationException si cette case n'est pas accessible au robot
     */
    @Override
    public void deplacer(Case c) throws SimulationException {
        if(c.estVoisine(this.getPosition())==false || c.getTerrain()==NatureTerrain.EAU)  {
            throw new SimulationException("Case inaccessible pour le robot sélectionné !");
        }
        
        this.setPosition(c);
        this.volumeEau = this.volumeMax; // Maintenir le réservoir rempli
    }


    /**
     * Remplir le réservoir d'eau du robot si la case le permet.
     * @param carte Carte sur laquelle le robot évolue
     * @throws SimulationException si la case courante ne permet pas un remplissage du réservoir
     */
    @Override
    public void remplirReservoir(Carte carte) {
        // Action à adopter en cas de remplissage : exception ?
        this.volumeEau = this.volumeMax;
    }
}
