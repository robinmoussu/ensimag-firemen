import java.util.ArrayList;

/**
 * Description d'une simulation.
 * @author Thibaud Backenstrass
 * @date 2014-11-17
 */
public class DonneesSimulation {
    private Carte carte;
    private ArrayList<Incendie> incendies;
    private ArrayList<Robot> robots;
    protected ArrayList<Case> caseEau;

    /**
     * Constructeur des données de la simulation.
     * @param nbLignes Nombre de lignes de la simulation
     * @param nbColonnes Nombre de colonnes de la simulation
     * @param tailleCases Taille (en mètres) des cases de la simulation
     * @throws ConstructionException si l'un des paramètres est négatif ou nul
     */
    public DonneesSimulation(int nbLignes, int nbColonnes, int tailleCases)
            throws ConstructionException {
        carte = new Carte(nbLignes, nbColonnes, tailleCases);
        incendies = new ArrayList<>();
        robots = new ArrayList<>();
    }


    /**
     * Accesseur sur la carte.
     * @return Référence sur la carte de la simulation
     */
    public Carte getCarte() {
        return this.carte;
    }
    /**
     * Accesseursur la liste des incendies.
     * @return ArrayList contenant les incendies
     */
    public ArrayList<Incendie> getIncendies() {
        return this.incendies;
    }
    /**
     * Accesseur sur le nombre d'incendies.
     * @return Nombre d'incendies de la simulation
     */
    public int getNbIncendies() {
        return this.incendies.size();
    }
    /**
     * Accesseur sur la liste des robots.
     * @return ArrayList contenant les robots
     */
    public ArrayList<Robot> getRobots() {
        return this.robots;
    }
    /**
     * Accesseur sur le nombre de robots.
     * @return Nombre de robots de la simulation
     */
    public int getNbRobots() {
        return this.robots.size();
    }
    /**
     * Accesseur sur le nombre de lignes de la simulation.
     * @return Nombre de lignes de la simulation
     */
    public int getNbLignes() {
        return carte.getNbLignes();
    }
    /**
     * Accesseur sur le nombre de colonnes de la simulation.
     * @return Nombre de colonnes de la simulation
     */
    public int getNbColonnes() {
        return carte.getNbColonnes();
    }
    public ArrayList<Case> getCaseEau() {
        return this.caseEau;
    }


    /** Ajouter une case à la simulation, à la place (ligne,colonne) à partir du
     * coin supérieur gauche.
     * @param ligne Numéro de la ligne où créer la case, à partir de zéro
     * @param colonne Numéro de la colonne où créer la case, à partir de zéro
     * @param t Nature du terrain sur la case à créer
     * @throws ConstructionException si les coordonnées sont à l'extérieur des
     * limites de la carte
     */
    public void addCase(int ligne, int colonne, NatureTerrain t)
            throws ConstructionException {
        this.carte.setCase(ligne, colonne, t);
    }
    
    /**
     * Ajouter une case à la simulation.
     * @param c Référence sur la case à ajouter à la simulation
     * @throws ConstructionException si la case est en-dehors des limites de la
     * carte
     */
    public void addCase(Case c) throws ConstructionException {
        this.carte.setCase(c);
        if (c.getTerrain() == NatureTerrain.EAU) {
            this.caseEau.add(c);
        }
    }
    

    /**
     * Indique si une case est en-dehors de la carte.
     * @param pos Référence sur la case à tester
     * @return vrai si la case est en-dehors des limites de la carte
     */
    
    protected boolean estHorsCarte(Case pos) {
        int ligne   = pos.getLigne();
        int colonne = pos.getColonne();
        
        return ligne<0 || ligne>=carte.getNbLignes()
                || colonne<0 || colonne>=carte.getNbColonnes();
                
    }
    
    /**
     * Ajouter un incendie à la simulation.
     * @param pos Référence sur une case indiquant la position de l'incendie
     * @param eau Intensité de l'incendie, en litres d'eau nécessaires pour
     * l'éteindre
     * @throws ConstructionException si la position de l'incendie est hors des
     * limites de la carte
     */
    public void addIncendie(Case pos, int eau) throws ConstructionException {
        // Si la position est en-dehors de la carte...
        if (estHorsCarte(pos)) {
            throw new ConstructionException("Impossible de créer un incendie "
                    + "sur la case spécifiée !");
        }

        incendies.add(new Incendie(pos, eau));
    }

    /**
     * Ajouter un robot à la simulation.
     * @param pos Référence sur une case indiquant la position initiale du robot
     * @param robot Référence sur le robot à créer
     * @throws ConstructionException si la position du robot est hors des
     * limites de la carte
     */
    public void addRobot(Case pos, Robot robot) throws ConstructionException {
        // Si la position est en-dehors de la carte...
        if (estHorsCarte(pos)) {
            throw new ConstructionException("Impossible de créer un robot sur "
                    + "la case spécifiée !");
        }

        robot.setPosition(pos);
        robots.add(robot);
    }

}
