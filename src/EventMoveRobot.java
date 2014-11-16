public class EventMoveRobot extends Evenement {

	private Robot robot;
	private Direction direction;
	private Carte carte;

	// Constructeur
	public EventMoveRobot(Date d, Robot r, Direction dir, Carte c) {
		super(d);
		this.robot = r;
		this.direction = dir;
		this.carte = c;
		this.dateFin = new Date ((long)(d.getDate() + carte.getTailleCases() * 3.6 / robot.getVitesse(robot.getPosition().getTerrain()))); // Attention, vitesse en km/h, à passer en m/s

	}

	// Constructeur de copie
	//public EventMoveRobot(EventMoveRobot e) {
	//	super(e);
	//	this.robot = e.robot;
	//	this.direction = e.direction;
	//	this.carte = e.carte;
	//	this.dateFinExe = e.dateFinExe;
	//}

	// Execute concrètement l'évènement
	public void execute() throws SimulationException {
		Case cur = robot.getPosition();
        System.out.println(cur.toString());
		Case c = carte.getVoisin(cur, this.direction);
		robot.deplacer(c);
        System.out.println(robot.getPosition().toString());
	}
}
