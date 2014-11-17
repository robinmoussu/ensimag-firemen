/**
 * Evénement de remplissage du réservoir d'un robot.
 * Cette classe concrétise la classe abstraite Evenement
 * @author Amanda Sambath
 * @date 2014-11-15
 */
public class EventRemplirRobot extends Evenement {
	private Robot robot;
	private Carte carte;

	/**
     * Constructeur d'événement de remplissage du réservoir d'un robot.
     * @param d Objet-date indiquant le début de l'exécution de l'événement
     * @param r Robot sur lequel appliquer l'événement
     * @param c Carte sur laquelle le robot évolue
     */
	public EventRemplirRobot(Date d, Robot r, Carte c) {
		super(d);
		this.robot = r;
		this.carte = c;
		this.dateFin = new Date (d.getDate() + r.getTempsRemplissage());
	}


    /**
     * Exécution de l'événement.
     * @throws SimulationException si l'exécution de l'événement a échoué
     */
	public void execute() throws SimulationException {
		robot.remplirReservoir(this.carte);
	}
}
