public class ManagerScenario1 extends Manager {
    int i;

	// Constructeur
	public ManagerScenario1(Simulateur simu, DonneesSimulation simuData) {
		super(simu, simuData);
        i = 0;
	}

	@Override
    public void manage() {
		Robot r = simuData.getRobots().get(1);
		Carte c = simuData.getCarte();
		Date d = simu.getDate();

        if(this.i == 0) {
    		EventMoveRobot e1 = new EventMoveRobot(d, r, Direction.NORD, c);
    		simu.ajouteEvenement(e1);

	    	EventViderRobot e2 = new EventViderRobot(e1.getDateFin(), r, 50, simuData);
		    simu.ajouteEvenement(e2);

    		EventMoveRobot e3 = new EventMoveRobot(e2.getDateFin(), r, Direction.OUEST, c);
	    	simu.ajouteEvenement(e3);

    		EventMoveRobot e4 = new EventMoveRobot(e3.getDateFin(), r, Direction.OUEST, c);
	    	simu.ajouteEvenement(e4);

    		EventRemplirRobot e5 = new EventRemplirRobot(e4.getDateFin(), r, c);
    		simu.ajouteEvenement(e5);

	    	EventMoveRobot e6 = new EventMoveRobot(e5.getDateFin(), r, Direction.EST, c);
    		simu.ajouteEvenement(e6);

	    	EventMoveRobot e7 = new EventMoveRobot(e6.getDateFin(), r, Direction.EST, c);
    		simu.ajouteEvenement(e7);
		
    		EventViderRobot e8 = new EventViderRobot(e7.getDateFin(), r, 50, simuData);
	    	simu.ajouteEvenement(e8);
        }

        this.i = i+1;
	}

	@Override
	public void signaleSuccessEvent(Evenement e) {
		return;
	}

	@Override
	public void signaleFailEvent(Evenement e) {
		System.out.println("[ERR] Erreur de simulation du Scenario 1");
		return;
	}
}
