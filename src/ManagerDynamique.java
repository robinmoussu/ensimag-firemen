import java.util.ArrayList;
import java.util.ListIterator;

/** Manager calculant dynamiquement les actions à effectuer pour chacuns des
 * robots
 */
class ManagerDynamique extends Manager {
    
    ArrayList<Managed> managed;
    protected boolean finSimulation;

    public ManagerDynamique(Simulateur simu, DonneesSimulation simuData)
            throws SimulationException {
        super(simu, simuData);
        this.finSimulation = false;
        
        ArrayList<Robot> robots = this.simuData.getRobots();
        
        managed = new ArrayList<>(robots.size());
        for(Robot robot: robots) {
            managed.add(new DoNothing(robot, simu));
        }
    }
    
    @Override
    public void manage()
            throws SimulationException {
        if (this.managed.size() != this.simuData.getRobots().size()) {
            throw new SimulationException("Le nombre de robots présents dans "
                    + "la simulation ne correspond pas avec le nombre de "
                    + "robots managés. Peut être que vous avez creer ce "
                    + "manager avant d'avoir fini d'ajouter touts les robots à "
                    + "la simulation ?");
        }
        
        // si tout les robots sont inactifs, la simu est terminée
        this.finSimulation = true;
        
        ListIterator<Managed> itr = this.managed.listIterator();
        while (itr.hasNext()) {
            Managed m = itr.next();
            if (m.actionFinie()) {
                // Il faut lui trouver un nouvel objectif

                if (m.robot.estVide()) {
                    m = new ChercheEau(this.simuData, m.getRobot(), simu);
                    itr.set(m);
                } else {
                    m = new EteindreIncendie(this.simuData, m.getRobot(), simu);
                    itr.set(m);
                }
            }
            
            // Au moins un robot est actif -> la simu n'est pas terminée
            if (!m.actionFinie()) {
                this.finSimulation = false;
            }
            
            m.doAction();
        }
    }

    @Override
    public void signaleSuccessEvent(Evenement e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void signaleFailEvent(Evenement e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean finSimulation() {
        return this.finSimulation;
    }
}
