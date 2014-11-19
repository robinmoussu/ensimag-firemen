
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.PriorityQueue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author robin
 */
public class ManagerDynamique extends Manager {
    
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
            throw new SimulationException("Le nombre de robots présent dans "
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

abstract class Managed {
    protected Robot robot;
    boolean finished;
    protected Simulateur simu;

    public Managed(Robot robot, Simulateur simu) {
        this.robot = robot;
        this.finished = false;
        this.simu = simu;
    }

    public Robot getRobot() {
        return robot;
    }
    
    final void doAction() throws SimulationException {
        if (!this.finished) {
            doInternalAction();
        }
    }
    abstract void doInternalAction() throws SimulationException;
    
    public boolean actionFinie() {
        return finished;
    }
}

class DoNothing extends Managed {

    public DoNothing(Robot robot, Simulateur simu) {
        super(robot, simu);
    }

    @Override
    void doInternalAction() {
        // Rien à faire
        System.err.println("Le robot " + this.robot + " n'a plus rien à faire");
    }

    @Override
    public boolean actionFinie() {
        return true;
    }

}

class ChercheEau extends Managed {
    protected Astar parcourt;
    protected DonneesSimulation data;

    public ChercheEau(DonneesSimulation data, Robot robot, Simulateur simu)
            throws SimulationException {
        super(robot, simu);
        this.data = data;
        
        // On rempli le robot avec l'eau la plus proche
        Astar astar;
        PriorityQueue<Astar> eauProche;

        if (!this.robot.estRemplissable(this.data.getCarte())) {
            eauProche = new PriorityQueue<>();
            ArrayList<Case> caseEau = data.getCaseEau();
            for (Case eau : caseEau) {
                astar = new Astar(data.getCarte(),
                        this.robot.getPosition(), eau, this.robot);
                eauProche.add(astar);
            }

            this.parcourt = eauProche.peek();
            if (this.parcourt != null) {
            } else {
                this.finished = true;
            }
        }
    }

    @Override
    void doInternalAction() throws SimulationException {
        System.out.println("Recherche d'eau…" + this.robot.estRemplissable(this.data.getCarte()));
        if (this.robot.estRemplissable(this.data.getCarte())) {
            System.out.println("On rempli le robot…");
            this.robot.remplirReservoir(this.data.getCarte());
            if (this.robot.estPlein()) {
                this.finished = true;
            }
        } else {
            System.out.println("On rapproche le robot");
            this.robot.deplacer(this.parcourt.next(
                    this.robot.getPosition()));
        }
    }
}

class EteindreIncendie extends Managed {
    protected Astar parcourt;
    protected DonneesSimulation data;

    public EteindreIncendie(DonneesSimulation data, Robot robot, Simulateur simu)
            throws SimulationException {
        super(robot, simu);
        this.data = data;
        
        // On cherche l'incendie le plus proche
        Astar astar;
        PriorityQueue<Astar> feuProche;
        
        if (!this.robot.peutEteindreFeu(data)) {
            feuProche = new PriorityQueue<>();
            for (Incendie feu : data.getIncendies()) {
                astar = new Astar(data.getCarte(),
                        this.robot.getPosition(), feu.getPosition(), this.robot);
                feuProche.add(astar);
            }

            this.parcourt = feuProche.peek();
        }
    }

    @Override
    void doInternalAction() throws SimulationException {
        System.out.println("Recherche d'incendies…");
        if (this.robot.peutEteindreFeu(this.data)) {
            System.out.println("On éteind l'incendie…");
            this.robot.deverserEau(this.data, 1);
            this.finished = true;
        } else {
            if (this.parcourt == null) {
                // On était déjà arrivée sur le feu, et il est éteint
                this.finished = true;
            } else {
                System.out.println("On rapproche le robot");
                Case next = this.parcourt.next(this.robot.getPosition());
                if (next.equals(this.robot.getPosition())) {
                    System.out.println("On était déjà arrivée sur le feu, et il"
                            + " est éteint");
                    this.finished = true;
                } else {
                    this.robot.deplacer(next);
                }
            }
        }
    }
}
