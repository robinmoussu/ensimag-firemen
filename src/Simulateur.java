import java.util.PriorityQueue;

public class Simulateur {

	private Date dateSimulation;
	private PriorityQueue<Evenement> evenements;
	private Manager manager = null;

	// Constructeur
	public Simulateur(Date date) {
		this.dateSimulation = date;
		this.evenements = new PriorityQueue<Evenement>();
	}

	// Accesseurs
	public Date getDate() {
		return this.dateSimulation;
	}
	public PriorityQueue<Evenement> getEvents() {
		return this.evenements;
	}
	public Manager getManager() throws SimulationException {
        if(this.manager == null) {
            throw new SimulationException("Aucun manager associé au simulateur !");
        }

		return this.manager;
	}

	// Mutateur
	public void setManager(Manager manager) {
		this.manager = manager;
	}

	/**
      * ajouteEvement(e)
	  * Ajoute un evenement à la file prioritaire des evenements à realiser
	  *
	  * @param Evenement e : evenement a ajouter aux evenements a executer
	  */
	public void ajouteEvenement(Evenement e) {
		evenements.add(e);
	}

	/**
      * incrementeDate(t)
	  * Mise à jour de la date
	  *
	  * @param long t : nombres de tics a ajouter a la date courrante du Simulateur
	  */
	public void incrementeDate(long t) throws SimulationException {
        if(this.manager == null) {
            throw new SimulationException("Aucun manager associé au simulateur !");
        }

		// 1. Incrémentation de la date courante de simulation
		dateSimulation.incrementeDate(t);
		// 2. Execution des évènements dont dateFin < dateSimulation
   		Evenement e = evenements.peek();
    	while (e!=null && e.getDateFin().getDate() <= this.dateSimulation.getDate()) {
	    	try {
		    	evenements.poll().execute();
			    // Le simulateur doit envoyer un signal de succès au manager
   				manager.signalSuccessEvent(e);
    		}
	    	catch(SimulationException exc) {
		    	manager.signalFailEvent(e); // Signal d'échec
			}
			e = evenements.peek();
        }
		// 3. Appel à manage() du Manager après la MAJ de DonneesSimulations dans execute()
		// Mise à jour de la file à priorité <evenements>
		manager.manage();
	}

	/** simulationTermine()
	  * Retourne true si la liste d'évenement est vide
	  *
	  * @return boolean : retourne true si la simulation est terminee, ie. si la liste des evenements a executer est vide
	  */
	private boolean simulationTermine() {
		if (evenements.size() == 0) {
			return true;
		}
		else {
            return false;
        }
	}

}

