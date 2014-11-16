public class ManagerScenario0 extends Manager {

	// Constructeur
    public ManagerScenario0(Simulateur simu, DonneesSimulation simuData) {
        super(simu, simuData);
    }

    @Override
    public void manage() {
		Robot r = simuData.getRobots().get(0);
		Carte c = simuData.getCarte();
		Date d = simu.getDate();
        
		// Ne créer les événements qu'une seule fois !
        if(d.getDate() == 238) {
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
	}

	@Override
	public void signalSuccessEvent(Evenement e) {
		return;
	}

	@Override
	public void signalFailEvent(Evenement e) {
		System.out.println("[ERR] Erreur de simulation dans le Scenario 0");
		return;
	}
}
