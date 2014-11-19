/**
 * Simulateur qui contrôle les événements de la simulation et les exécute.
 */

import java.util.PriorityQueue;

public class Simulateur {
	private Date dateSimulation;
	private PriorityQueue<Evenement> evenements;
	private Manager manager = null;


	/**
     * Construction d'un simulateur à la date courante.
     * @param Objet-date servant de point de départ pour la simulation
     */
	public Simulateur(Date date) {
		this.dateSimulation = date;
		this.evenements = new PriorityQueue<Evenement>();
	}


	/**
     * Accesseur sur la date courante du simulateur.
     * @return Objet-date représentant la date courante de simulation
     */
	public Date getDate() {
		return this.dateSimulation;
	}
    /**
     * Accesseur sur le manager associé au simulateur.
     * @return Manager associé à la création des événements du simulateur
     * @throws SimulationException si aucun manager n'est associé au simulateur
     */
	public Manager getManager() throws SimulationException {
        if(this.manager == null) {
            throw new SimulationException("Aucun manager associé au simulateur !");
        }

		return this.manager;
	}


	/**
     * Mutateur du manager associé au simulateur.
     * @param manager Référence sur le manager à associer au simulateur
     */
	public void setManager(Manager manager) {
		this.manager = manager;
	}


	/**
	 * Ajoute un événement à la liste des événement à exécuter.
	 * @param e Evénement à ajouter
	 */
	public void ajouteEvenement(Evenement e) {
		evenements.add(e);
	}


	/**
	 * Avance d'un pas dans la simulation.
	 * @param t Nombre de secondes à ajouter à la date courante du simulateur
     * @throws SimulationException si aucun manager n'est associé au simulateur
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
   				manager.signaleSuccessEvent(e);
    		}
	    	catch(SimulationException exc) {
                System.out.println(exc.getMessage());
		    	manager.signaleFailEvent(e); // Signal d'échec
			}
			e = evenements.peek();
        }
		// 3. Appel à manage() du Manager après la MAJ de DonneesSimulations dans execute()
		// Mise à jour de la file à priorité <evenements>
		manager.manage();
	}


	/**
     * Indique si la liste des événements est vide.
     * @return Booléen indiquant si la simulation est terminée
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

