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
        this.carte.setCase(ligne, colonne, t);
    }
    public void addCase(Case case_) throws ConstructionException {
        this.carte.setCase(case_);
    }
    
    protected boolean estHorsCarte(Case pos) {
        int ligne   = pos.getLigne();
        int colonne = pos.getColonne();
        
        return ligne<0 || ligne>=carte.getNbLignes()
                || colonne<0 || colonne>=carte.getNbColonnes();
                
    }
    
    public void addIncendie(Case pos, int eau) throws ConstructionException {
        // Si la position est en-dehors de la carte...
        if (estHorsCarte(pos)) {
            throw new ConstructionException("Impossible de créer un incendie"
                    + "sur la case spécifiée !");
        }

        incendies.add(new Incendie(pos, eau));
    }
    public void addRobot(Case pos, Robot robot) throws ConstructionException {
        // Si la position est en-dehors de la carte...
        if (estHorsCarte(pos)) {
            throw new ConstructionException("Impossible de créer un robot sur"
                    + "la case spécifiée !");
        }
        robot.setPosition(pos);
        robots.add(robot);
    }

}
