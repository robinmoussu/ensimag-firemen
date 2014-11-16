public class EventViderRobot extends Evenement {

	private Robot robot;
	private int nbIntervention;
	private DonneesSimulation donneesSimulation;

	// Constructeur
	public EventViderRobot(Date d, Robot r, int n, DonneesSimulation data) {
		super(d);
		this.robot = r;
		this.nbIntervention = n;
		this.donneesSimulation = data;
		this.dateFin = new Date (d.getDate() + r.getDureeIntervention() * n);
	}

	// Constructeur de copie
	//public EventViderRobot(EventViderRobot e) {
	//	super(e);
	//	this.robot = e.robot;
	//	this.nbIntervention = e.nbIntervention;
	//	this.donneesSimulation = e.donneesSimulation;
	//	this.dateFinExe = e.dateFinExe;
	//}

	// Execute concrètement l'évènement
	public void execute() throws SimulationException {
		robot.deverserEau(donneesSimulation, nbIntervention);
	}
}
