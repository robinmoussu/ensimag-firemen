/**
 * Classe abstraite de description d'un manager.
 * @author Amanda Sambath
 * @date 2014-11-15
 */
public abstract class Manager {
    protected Simulateur simu;
    protected DonneesSimulation simuData;


    /**
     * Constructeur de manager.
     * @param simu Simulateur associé au manager
     * @param simuData Données de simulation
     */
    public Manager(Simulateur simu, DonneesSimulation simuData) {
        this.simu = simu;
        this.simuData = simuData;
    }


    /**
     * Méthode abstraite qui implémente le choix des événements à executer.
     */
    public abstract void manage();

    
    /**
     * Signaler à un manager le succès de l'exécution d'un événement.
     * @param e Evénement exécuté
     */
    public abstract void signaleSuccessEvent(Evenement e);


    /**
     * Signaler à un manager l'échec de l'exécution d'un événement.
     * @param e Evénement exécuté
     */
    public abstract void signaleFailEvent(Evenement e);
}
