// Classe de description des données de la simulation
// Dernière modification : Thibaud BACKENSTRASS, 9 novembre
import java.util.LinkedList;
public class DonneesSimulation {
    // Attributs
    private Carte carte;
    private LinkedList<Incendie> incendies;
    private int nbIncendies;
    private LinkedList<Robot> robots;
    private int nbRobots;

    // Constructeur
    public DonneesSimulation(int nbLignes, int nbColonnes, int tailleCases) {
        carte = new Carte(nbLignes, nbColonnes, tailleCases);
        incendies = new LinkedList<Incendie>();
        nbIncendies = 0;
        robots = new LinkedList<Robot>();
        nbRobots = 0;
    }

    // Accesseurs
    public Carte getCarte() {
        return this.carte;
    }
    public int getNbIncendies() {
        return this.nbIncendies;
    }
    public int getNbRobots() {
        return this.nbRobots;
    }

    // Méthodes pour ajouter des éléments à la simulation
    public void addCase(int ligne, int colonne, NatureTerrain t) {
        carte.setCase(ligne, colonne, t);
    }
    public void addIncendie(Case pos, int eau) {
        // Si la position est en-dehors de la carte...
        if(pos.getLigne()<0 || pos.getLigne()>=carte.getNbLignes() || pos.getColonne()<0 || pos.getColonne()>=carte.getNbColonnes()) {
            throw new ArithmeticException("Impossible de créer un incendie sur la case spécifiée !");
        }
        else {
            incendies.add(new Incendie(pos, eau));
            this.nbIncendies++;
        }
    }
    public void addRobot(Case pos, TypeRobot type) {
        // Si la position est en-dehors de la carte...
        if(pos.getLigne()<0 || pos.getLigne()>=carte.getNbLignes() || pos.getColonne()<0 || pos.getColonne()>=carte.getNbColonnes()) {
            throw new ArithmeticException("Impossible de créer un robot sur la case spécifiée !");
        }
        switch(type) {
            case DRONE:     robots.add(new RobotDrone(pos));
                            break;
            case ROUES:     robots.add(new RobotRoues(pos));
                            break;
            case CHENILLES: robots.add(new RobotChenilles(pos));
                            break;
            case PATTES:    robots.add(new RobotPattes(pos));
                            break;
        }
        this.nbRobots++;
    }
    public void addRobot(Case pos, TypeRobot type, int vitesse) {
        // Si la position est en-dehors de la carte...
        if(pos.getLigne()<0 || pos.getLigne()>=carte.getNbLignes() || pos.getColonne()<0 || pos.getColonne()>=carte.getNbColonnes()) {
            throw new ArithmeticException("Impossible de créer un robot sur la case spécifiée !");
        }
        switch(type) {
            case DRONE:     robots.add(new RobotDrone(pos, vitesse));
                            break;
            case ROUES:     robots.add(new RobotRoues(pos, vitesse));
                            break;
            case CHENILLES: robots.add(new RobotChenilles(pos, vitesse));
                            break;
            case PATTES:    robots.add(new RobotPattes(pos, vitesse));
                            break;
        }
        this.nbRobots++;
    }

}
