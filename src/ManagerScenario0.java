/**
 * Scénario 0 de manager.
 * Ce manager se contente de tester la réaction du simulateur si un robot sort de la carte.
 * @author Amanda Sambath
 * @date 2014-11-15
 */
public class ManagerScenario0 extends Manager {
    int i;

    
    /**
     * Constructeur du manager.
     * @param simu Simulateur associé
     * @param simuData Données de simulations
     */
    public ManagerScenario0(Simulateur simu, DonneesSimulation simuData) {
        super(simu, simuData);
        i = 0;
    }


    /**
     * Implémentation des décisions du manager.
     */
    @Override
    public void manage() {
        Robot r = simuData.getRobots().get(0);
        Carte c = simuData.getCarte();
        Date d = simu.getDate();

        // Ne créer les événements qu'une seule fois !
        if (this.i == 0) {
            // Implementer le calcul de la date de fin d'exe avec incrementeDate et simu.getDate
            EventMoveRobot e1 = new EventMoveRobot(d, r, Direction.NORD, c);
            simu.ajouteEvenement(e1);

            EventMoveRobot e2 = new EventMoveRobot(e1.getDateFin(), r, Direction.NORD, c);
            simu.ajouteEvenement(e2);

            EventMoveRobot e3 = new EventMoveRobot(e2.getDateFin(), r, Direction.NORD, c);
            simu.ajouteEvenement(e3);

            EventMoveRobot e4 = new EventMoveRobot(e3.getDateFin(), r, Direction.NORD, c);
            simu.ajouteEvenement(e4);
        }

        this.i = i + 1;
    }


    /**
     * Indiquer le succès de l'exécution d'un événement.
     * @param e Evénement exécuté
     */
    @Override
    public void signaleSuccessEvent(Evenement e) {
        return;
    }


    /**
     * Indiquer l'échec de l'exécution d'un événement.
     * @param e Evénement exécuté
     */
    @Override
    public void signaleFailEvent(Evenement e) {
        System.out.println("[ERR] Erreur de simulation dans le Scenario 0");
        return;
    }

}
