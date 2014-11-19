
import java.util.Iterator;

/**
 * Classe abstraite de description d'un robot.
 * @author Thibaud Backenstrass
 * @date 2014-11-17
 */
abstract class Robot implements ValideCase {
    protected Case position;
    protected int volumeEau; // en litres, 0 par défaut
    // Attributs à définir dans les sous-classes
    protected int vitesse; // en km/h
    protected int volumeMax; // en litres
    protected int tempsRemplissage; // en secondes
    protected int volumeIntervention; // en litres
    protected int dureeIntervention; // en secondes


    /**
     * Constructeur abstrait de robot.
     * @param pos Case sur laquelle créer le robot
     * @param vitesse Vitesse par défaut du robot (en km/h)
     * @param volumeMax Volume du réservoir du robot (en litres)
     * @param tempsRemplissage Temps de remplissage du robot (en secondes)
     * @param volumeIntervention Volume d'une intervention unitaire (en litres)
     * @param dureeIntervention Durée d'une intervention unitaire (en secondes)
     */
    public Robot(Case pos, int vitesse, int volumeMax, int tempsRemplissage,
            int volumeIntervention, int dureeIntervention) {
        this.position = pos;
        this.vitesse = vitesse;
        this.volumeMax = volumeMax;
        this.tempsRemplissage = tempsRemplissage;
        this.volumeIntervention = volumeIntervention;
        this.dureeIntervention = dureeIntervention;
    }


    /**
     * Accesseur sur la position du robot.
     * @return Case sur laquelle se situe le robot
     */
    public Case getPosition() {
        return this.position;
    }

    /**
     * Accesseur sur le volume d'eau contenu dans le réservoir.
     * @return Volume d'eau (en litres) actuellement dans le réservoir du robot
     */
    public int getVolumeEau() {
        return this.volumeEau;
    }

    /**
     * Accesseur sur la vitesse du robot en fonction de la nature du terrain.
     * @param terrain Nature du terrain sur la case du robot
     * @return Vitesse du robot sur la case courante
     */
    abstract public int getVitesse(NatureTerrain terrain);
    /**
     * Accesseur sur la durée de remplissage du réservoir du robot.
     * @return Durée de remplissage du robot, en secondes
     */
    public int getTempsRemplissage() {
        return this.tempsRemplissage;
    }
    /**
     * Accesseur sur le volume d'une intervention unitaire.
     * @return Volume d'eau d'une intervention unitaire, en litres
     */
    public int getVolumeIntervention() {
        return this.volumeIntervention;
    }
    /**
     * Accesseur sur la durée d'une intervention unitaire.
     * @return Duréee d'une intervention unitaire, en secondes
     */
    public int getDureeIntervention() {
        return this.dureeIntervention;
    }
    /**
     * Accesseur sur l'image du robot.
     * @return Chaîne représentant le robot sur l'interface graphique
     */
    abstract public String getImage();


    /**
     * Mutateur de la position du robot.
     * @param c Case sur laquelle déplacer le robot
     */
    public void setPosition(Case c) {
        this.position = c;
    }

    // Predicats
    public boolean estPlein() {
        return this.volumeEau == this.volumeMax;
    }
    public boolean estVide() {
        return this.volumeEau == 0;
    }

    /** Vider le réservoir d'eau d'un robot.
    *
     * @param simulation Données de simulation
     * @param nbInterventions Nombre d'interventions unitaires à effectuer
     * @throws SimulationException si le réservoir est vide
     */
    public void deverserEau(DonneesSimulation simulation, int nbInterventions)
            throws SimulationException {
        if(volumeEau == 0 || nbInterventions>volumeEau/volumeIntervention) {
            throw new SimulationException("Pas assez d'eau dans le réservoir "
                    + "!");
        }

        // Diminuer l'eau du reservoir du robot
        this.volumeEau -= nbInterventions*volumeIntervention;
        Iterator<Incendie> itr = simulation.getIncendies().iterator();
        while(itr.hasNext()) {
            Incendie i = itr.next();
            // On parcourt les incendies pour voir s'il y en a un à éteindre
            if(i.getPosition().equals(this.position) == true) {
                i.decrementeIntensite(nbInterventions*volumeIntervention);
System.out.println("Reste dans le réservoir: " + this.volumeEau);
                if (i.getIntensite() == 0) {
                    itr.remove();
                }
                break; // Sortir du for, un robot n'est que sur une case à la
                // fois...
            }
        }
    }


    /**
     * Déplacer le robot sur une case voisine compatible.
     * @param c Case sur laquelle déplacer le robot
     * @throws SimulationException si cette case n'est pas accessible au robot
     */
    abstract public void deplacer(Case c) throws SimulationException;

    /**
     * Savoir si le robot peut être rempli sur la case courante.
     * @param carte Carte sur laquelle le robot se trouve
     * @return true si le robot peut être remplis
     */
    abstract public boolean estRemplissable(Carte carte);

    /**
     * Remplir le réservoir d'eau du robot si la case le permet.
     * @param carte Carte sur laquelle le robot évolue
     * @throws SimulationException si la case courante ne permet pas un
     * remplissage du réservoir
     */
    abstract public void remplirReservoir(Carte carte) throws SimulationException;

    @Override
    public boolean estValide(Case c) {
        return getVitesse(c.getTerrain()) > 0;
    }
    
    /**
     * Savoir si un robot peut éteindre un feu sur la case courante. 
     * @param simuData Données de simulation
     * @return true si le robot peut éteindre un feu
     */
    public boolean peutEteindreFeu(DonneesSimulation simuData) {
        for (Incendie incendie : simuData.getIncendies()) {
            if (this.position.equals(incendie.getPosition())) {
                return true;
            }
        }

        return false;
    }
}
