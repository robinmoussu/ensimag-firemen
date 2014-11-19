
import java.util.ArrayList;
import java.util.PriorityQueue;

/** S'occupe d'aller remplir un robot.
 */
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
    protected void doInternalAction() throws SimulationException {
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