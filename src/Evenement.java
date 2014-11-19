/**
 * Description abstraite d'une événement.
 */

import java.util.* ;

public abstract class Evenement implements Comparable<Evenement> {
	protected Date dateDebut;	
	protected Date dateFin;

	/**
     * Constructeur d'événement à une date fixée.
     * @param date Objet-date indiquant la date de début de l'événement
     */
	public Evenement(Date date) {
		this.dateDebut = date;
	}
	

	/**
     * Accesseur sur la date de début d'un événement.
     * @return Objet-date indiquant la date de début de l'événement
     */
	public Date getDateDebut() {
		return this.dateDebut;
	}
    /**
     * Accesseur sur la date de fin d'un événement.
     * @return Objet-date indiquant la date de fin de l'événement
     */
	public Date getDateFin() {
		return this.dateFin;
	}


	/**
	  * Méthode abstraite invoquée pour l'exécution de l'événement.
      * @throws SimulationException si l'exécution de l'événement a échoué.
	  */
	public abstract void execute() throws SimulationException;


	/**
	  * Redéfinition de la méthode de comparaison de deux événements en fonction de leur date de fin.
	  * @param e Evénement à comparer
	  * @return Entier dont le signe indique le degré de priorité entre les deux événements
	  */
	@Override
	public int compareTo(Evenement e) {
		if(e == null) {
			throw new NullPointerException();
		}
		if (this.getDateFin().getDate() < e.getDateFin().getDate()) {
			return -1;
		}
		else if (this.getDateFin().getDate() > e.getDateFin().getDate()) {
			return 1;
		}
		else return 0;
	}

} 
