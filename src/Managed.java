
/** À chaque sous-classe de Managed est associé une suite d'action permettant
 * de réaliser une tâche.
 */
abstract class Managed {
    protected Robot robot;
    boolean finished;
    protected Simulateur simu;

    /**
     * @param robot Robot à controler
     * @param simu Simultateur permettant de simuler l'environnement du robot
     */
    public Managed(Robot robot, Simulateur simu) {
        this.robot = robot;
        this.finished = false;
        this.simu = simu;
    }
    
    public Robot getRobot() {
        return robot;
    }
    
    /** Fonction à appeller à chaque fois que le robot géré par cette classe
     * peut exécuter une tache.
     * Permet de factoriser les tâches communes à toutes les sous-classes.
     * 
     * @throws SimulationException En cas d'ordre incorrects
     */
    final void doAction() throws SimulationException {
        if (!this.finished) {
            doInternalAction();
        }
    }
    
    /** Fonction s'occupant de réaliser de donner les ordres au robot.
     * 
     * @throws SimulationException En cas d'ordres incorrects
     */
    abstract protected void doInternalAction() throws SimulationException;
    
    public boolean actionFinie() {
        return finished;
    }
}