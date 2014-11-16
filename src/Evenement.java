import java.util.* ;

public abstract class Evenement implements Comparable<Evenement> {

	protected Date dateDebut;	
	protected Date dateFin;

	// Constructeur
	public Evenement(Date date) {
		this.dateDebut = date;
	}
	// Constructeur de copie abstrait à implémenter dans les sous-classes concrètes
	//public Evenement(Evenement e) {
	//	this.dateDebExe = e.dateDebExe;
	//}
	
	// Accesseurs
	public Date getDateDebut() {
		return this.dateDebut;
	}
	public Date getDateFin() {
		return this.dateFin;
	}

	/**
      * execute()
	  * Méthode abstraite d'execution d'un évenement
	  * (déplacement, intervention et remplissage du reservoir d'un robot)
	  */
	public abstract void execute() throws SimulationException;

	/**
      * compareTo(e)
	  * Redefinition de la methode compareTo pour la classe Evenement
	  * necessaire pour l'utilisation de la collection java PriorityQueue
	  * Genere une execption NullPointerException si l'evenement a comparer est null
	  *
	  * @param Evenement e : auquel est compare l'evenement sur lequel est appelee la methode
	  * @return int : retourne -1 si l'evenement est plus prioritaire que e, 1 dans le cas inverse, 0 si les deux evenements sont de meme priorite
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

	// Redéfinition de equals et hashCode nécessaires ?
} 
