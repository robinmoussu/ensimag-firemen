
import java.util.ArrayList;
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

    public ManagerDynamique(Simulateur simu, DonneesSimulation simuData)
            throws SimulationException {
        super(simu, simuData);
        
        ArrayList<Robot> robots = this.simuData.getRobots();
        
        managed = new ArrayList<>(robots.size());
        for(Robot robot: robots) {
            managed.add(new Managed(robot));
        }
    }

    static protected class Managed {
        protected Robot robot;

        /** Le parcourt en court du robot
         * Peut être null si le robot n'a pas de tache affecté en court
         */
        protected Astar parcourt;
        
        protected Objectif typeObjectif;
        
        enum Objectif {
            ChercheEau,
            None,
        }

        public Managed(Robot robot) {
            this.robot = robot;
            this.parcourt = null;
            this.typeObjectif = Objectif.None;
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
        
        for(Managed m : managed) {
            if (m.typeObjectif == Managed.Objectif.None) {
                // Il faut lui trouver un nouvel objectif
                
                if (m.robot.estVide()) {
                    vaChercherEau(m);
                }
            }
            
            if (m.typeObjectif != Managed.Objectif.None) {
                if (m.typeObjectif == Managed.Objectif.ChercheEau) {
                    System.out.println("Recherche d'eau…");
                    if (this.simuData.getCarte().estBordEau(m.robot.position)) {
                        System.out.println("On remplie le robot…");
                        m.robot.remplirReservoir(this.simuData.getCarte());
                    } else {
                        System.out.println("On rapproche le robot");
                        m.robot.deplacer(m.parcourt.next(
                            m.robot.getPosition()));
                    }
                }
            } else {
                System.err.println("Le robot " + m.robot +
                        " n'a plus rien à faire");
            }
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
    
    ////////////////////
    // protected
    
    protected void vaChercherEau(Managed m) throws SimulationException {
        // On rempli le robot avec l'eau la plus proche
        Astar astar;
        PriorityQueue<Astar> eauProche;

        eauProche = new PriorityQueue<>();
        for (Case eau : this.simuData.getCaseEau()) {
            astar = new Astar(this.simuData.getCarte(),
                    m.robot.getPosition(), eau, m.robot);
            eauProche.add(astar);
        }
        
        m.parcourt = eauProche.peek();
        if (m.parcourt != null) {
            m.typeObjectif = Managed.Objectif.ChercheEau;
        }
    }
}
