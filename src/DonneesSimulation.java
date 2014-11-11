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
    public LinkedList<Incendie> getIncendies() {
        return this.incendies;
    }
    public int getNbIncendies() {
        return this.nbIncendies;
    }
    public LinkedList<Robot> getRobots() {
        return this.robots;
    }
    public int getNbRobots() {
        return this.nbRobots;
    }
    public int getNbLignes() {
        return carte.getNbLignes();
    }
    public int getNbColonnes() {
        return carte.getNbColonnes();
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
    public void addRobot(Case pos, String typeRobot) {
        addRobot(pos, typeRobot, -1);
    }
    public void addRobot(Case pos, String typeRobot, int vitesse) {
        // Si la position est en-dehors de la carte...
        if(pos.getLigne()<0 || pos.getLigne()>=carte.getNbLignes() || pos.getColonne()<0 || pos.getColonne()>=carte.getNbColonnes()) {
            throw new ArithmeticException("Impossible de créer un robot sur la case spécifiée !");
        }
        switch(typeRobot) {
            case "DRONE":       robots.add(new RobotDrone(pos, vitesse)); // Si la vitesse est négative, elle est ignorée par le constructeur
                                break;
            case "ROUES":       robots.add(new RobotRoues(pos, vitesse));
                                break;
            case "CHENILLES":   robots.add(new RobotChenilles(pos, vitesse));
                                break;
            case "PATTES":      robots.add(new RobotPattes(pos, vitesse));
                                break;
            default:
                                throw new ArithmeticException("Impossible de créer un robot du type spécifié !");
        }
        this.nbRobots++;
    }

}
