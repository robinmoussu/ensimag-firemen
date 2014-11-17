/**
 * Evénement de vidange du réservoir d'un robot.
 * Cette classe concrétise la classe abstraite Evenement
 * @author Amanda Sambath
 * @date 2014-11-15
 */
public class EventViderRobot extends Evenement {
	private Robot robot;
	private int nbIntervention;
	private DonneesSimulation donneesSimulation;

	/**
     * Constructeur d'événement de vidage d'un robot.
     * @param d Objet-date indiquant le début de l'exécution de l'événement
     * @param r Robot sur lequel appliquer la vidange
     * @param n Nombre d'interventions unitaires à faire faire au robot
     * @param data Données de simulation sur lequel le robot opère
     */
	public EventViderRobot(Date d, Robot r, int n, DonneesSimulation data) {
		super(d);
		this.robot = r;
		this.nbIntervention = n;
		this.donneesSimulation = data;
		this.dateFin = new Date (d.getDate() + r.getDureeIntervention() * n);
	}


	/**
     * Exécution de l'événement.
     * @throws SimulationException si l'exécution de l'événement a échoué
     */
	public void execute() throws SimulationException {
		robot.deverserEau(donneesSimulation, nbIntervention);
	}
}
