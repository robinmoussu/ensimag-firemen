// Classe de description des données de la simulation
// Dernière modification : Thibaud BACKENSTRASS, 9 novembre
import java.util.LinkedList;
public class DonneesSimulation {
    // Attributs
    private Carte carte;
    private LinkedList<Incendie> incendies;
    private LinkedList<Robot> robots;

    // Constructeur
    public DonneesSimulation(int nbLignes, int nbColonnes, int tailleCases) throws ConstructionException {
        carte = new Carte(nbLignes, nbColonnes, tailleCases);
        incendies = new LinkedList<Incendie>();
        robots = new LinkedList<Robot>();
    }

    // Accesseurs
    public Carte getCarte() {
        return this.carte;
    }
    public LinkedList<Incendie> getIncendies() {
        return this.incendies;
    }
    public int getNbIncendies() {
        return this.incendies.size();
    }
    public LinkedList<Robot> getRobots() {
        return this.robots;
    }
    public int getNbRobots() {
        return this.robots.size();
    }
    public int getNbLignes() {
        return carte.getNbLignes();
    }
    public int getNbColonnes() {
        return carte.getNbColonnes();
    }

    // Méthodes pour ajouter des éléments à la simulation
    public void addCase(int ligne, int colonne, NatureTerrain t) throws ConstructionException {
        carte.setCase(ligne, colonne, t);
    }
    public void addIncendie(Case pos, int eau) throws ConstructionException {
        // Si la position est en-dehors de la carte...
        if(pos.getLigne()<0 || pos.getLigne()>=carte.getNbLignes() || pos.getColonne()<0 || pos.getColonne()>=carte.getNbColonnes()) {
            throw new ConstructionException("Impossible de créer un incendie sur la case spécifiée !");
        }

        incendies.add(new Incendie(pos, eau));
    }
    public void addRobot(Case pos, String typeRobot) throws ConstructionException {
        addRobot(pos, typeRobot, -1);
    }
    public void addRobot(Case pos, String typeRobot, int vitesse) throws ConstructionException {
        // Si la position est en-dehors de la carte...
        if(pos.getLigne()<0 || pos.getLigne()>=carte.getNbLignes() || pos.getColonne()<0 || pos.getColonne()>=carte.getNbColonnes()) {
            throw new ConstructionException("Impossible de créer un robot sur la case spécifiée !");
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
                                throw new ConstructionException("Impossible de créer un robot du type spécifié !");
        }
    }

}
