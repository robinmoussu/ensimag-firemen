/**
 * Scénario 1 de Manager.
 * Ce manager teste les déplacements, le remplissage, la vidange de robots ainsi que l'extinction d'incendies.
 * @author Amanda Sambath
 * @date 2014-11-15
 */
public class ManagerScenario1 extends Manager {
    int i;


    /**
     * Constructeur de manager.
     * @param simu Simulateur associé
     * @param simuData Données de simulation associées
     */
	public ManagerScenario1(Simulateur simu, DonneesSimulation simuData) {
		super(simu, simuData);
        i = 0;
	}


    /**
     * Méthode de gestion des événements à exécuter.
     */
	@Override
    public void manage() {
		Robot r = simuData.getRobots().get(1);
		Carte c = simuData.getCarte();
		Date d = simu.getDate();

        if(this.i == 0) {
            EventMoveRobot e1 = new EventMoveRobot(d, r, r.getPosition().getVoisin(Direction.NORD), c);
    		simu.ajouteEvenement(e1);

	    	EventViderRobot e2 = new EventViderRobot(e1.getDateFin(), r, 50, simuData);
		    simu.ajouteEvenement(e2);

    		EventMoveRobot e3 = new EventMoveRobot(e2.getDateFin(), r, r.getPosition().getVoisin(Direction.OUEST), c);
	    	simu.ajouteEvenement(e3);

    		EventMoveRobot e4 = new EventMoveRobot(e3.getDateFin(), r, r.getPosition().getVoisin(Direction.OUEST), c);
	    	simu.ajouteEvenement(e4);

    		EventRemplirRobot e5 = new EventRemplirRobot(e4.getDateFin(), r, c);
    		simu.ajouteEvenement(e5);

	    	EventMoveRobot e6 = new EventMoveRobot(e5.getDateFin(), r, r.getPosition().getVoisin(Direction.EST), c);
    		simu.ajouteEvenement(e6);

	    	EventMoveRobot e7 = new EventMoveRobot(e6.getDateFin(), r, r.getPosition().getVoisin(Direction.EST), c);
    		simu.ajouteEvenement(e7);
		
    		EventViderRobot e8 = new EventViderRobot(e7.getDateFin(), r, 50, simuData);
	    	simu.ajouteEvenement(e8);
        }

        this.i = i+1;
	}


    /**
     * Indique le succès de l'exécution d'un événement.
     * @param e Evénement exécuté
     */
	@Override
	public void signaleSuccessEvent(Evenement e) {
		return;
	}


    /**
     * Indique l'échec de l'exécution d'un événement.
     * @param e Evénement exécuté
     */
	@Override
	public void signaleFailEvent(Evenement e) {
		System.out.println("[ERR] Erreur de simulation du Scenario 1");
		return;
	}

}
