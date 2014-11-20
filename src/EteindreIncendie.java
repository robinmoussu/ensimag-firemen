
import java.util.PriorityQueue;

/** Stratégie de recherche d'incendie
 */
public class EteindreIncendie extends Managed {
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
    protected void doInternalAction() throws SimulationException {
        System.out.println("Recherche d'incendies...");
        if (this.robot.peutEteindreFeu(this.data)) {
            System.out.println("On éteind l'incendie...");
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
                    System.out.println("On était déjà arrivé sur le feu, et il"
                            + " est éteint");
                    this.finished = true;
                } else {
                    this.robot.deplacer(next);
                }
            }
        }
    }
}
