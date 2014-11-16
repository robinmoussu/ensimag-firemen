public class EventRemplirRobot extends Evenement {

	private Robot robot;
	private Carte carte;

	// Constructeur
	public EventRemplirRobot(Date d, Robot r, Carte c) {
		super(d);
		this.robot = r;
		this.carte = c;
		this.dateFin = d.dateInc((long)(carte.getTailleCases() / robot.getVitesse(robot.getPosition().getTerrain())));
	}

	// Constructeur de copie
	//public EventRemplirRobot(EventRemplirRobot e) {
	//	super(e);
	//	this.robot = e.robot;
	//	this.carte = e.carte;
	//	this.dateFinExe = e.dateFinExe;
	//}

	// Execute concrètement l'évènement
	public void execute() throws SimulationException {
		robot.remplirReservoir(this.carte);
	}
}
