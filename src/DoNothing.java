
/** N'exécute aucune action.
 */
class DoNothing extends Managed {

    public DoNothing(Robot robot, Simulateur simu) {
        super(robot, simu);
    }

    @Override
    protected  void doInternalAction() {
        // Rien à faire
        System.err.println("Le robot " + this.robot + " n'a plus rien à faire");
    }

    @Override
    public boolean actionFinie() {
        return true;
    }

}