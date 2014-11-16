public abstract class Manager {

	protected Simulateur simu;
	protected DonneesSimulation simuData;

	// Constructeur
	public Manager(Simulateur simu, DonneesSimulation simuData) {
		this.simu = simu;
		this.simuData = simuData;
	}

	/** 
      * manage()
	  * Methode abstraite qui dans les sous-classes concretes de Manager
	  * implemente le choix des evenements a executer
	  * (predefini a l'avance ou calcule selon le plus court chemin)
	  */
	public abstract void manage();

	/**
      * signalSuccessEvent(e)
	  * Est appelee sur un manager concret lorsque l'evenement e a reussi son execution
	  * Realise ce qu'il faut apres la reussite de l'evenement (par ex. deverouiller un robot)
	  *
	  * @param Evenement e : evenement que le simulateur a realise avec succes
	  */
	public abstract void signalSuccessEvent(Evenement e);

	/** 
      * signalFailEvent(e)
	  * Est appelee sur le manager lorsque l'evenement e n'a pas reussi son execution
	  * pour que le manager realise ce qu'il faut (par ex. ignorer)
	  *
	  * @param Evenement e : evenement qui a genere une exception (erreur d'execution)
	  */
	public abstract void signalFailEvent(Evenement e);
}
