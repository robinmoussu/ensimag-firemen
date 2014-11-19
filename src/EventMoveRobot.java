/**
 * Evénement de déplacement d'un robot.
 * Cette classe concrétise la classe abstraite Evenement.
 * @author Amanda Sambath
 * @date 2014-11-15
 */
public class EventMoveRobot extends Evenement {
	private Robot robot;
	private Case dest; // Case de destination
	private Carte carte;

	/**
     * Constructeur d'événement de déplacement de robot.
     * @param d Objet-date indiquant la date de début d'événement
     * @param r Référence sur le robot à déplacer
     * @param c Case de destination
     * @param c Carte à laquelle le robot est rattaché
     */
	public EventMoveRobot(Date d, Robot r, Case dest, Carte carte) {
		super(d);
		this.robot = r;
		this.dest = dest:
		this.carte = carte;
		this.dateFin = new Date ((long)(d.getDate() + carte.getTailleCases() * 3.6 / robot.getVitesse(robot.getPosition().getTerrain()))); // Attention, vitesse en km/h, à passer en m/s

	}


	/**
     * Exécution de l'événement.
     * @throws SimulationException si l'exécution de l'événement a échoué
     */
	public void execute() throws SimulationException {
		Case cur = robot.getPosition();
        System.out.println(cur.toString());
		robot.deplacer(dest);
        System.out.println(robot.getPosition().toString());
	}
}
