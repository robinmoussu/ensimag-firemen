// Description de la carte
// Dernière modification : Thibaud BACKENSTRASS, 9 novembre
public class Carte {
    // Attributs
    private int nbLignes; // >=0
    private int nbColonnes; // >=0
    private int tailleCases; // >=0
    private Case[][] carte;


    /**
     * Constructeur de carte.
     * @param nbLignes      Nombre de lignes de la carte
     * @param nbColonnes    Nombre de colonnes de la carte
     * @param tailleCases   Taille (en mètres) d'une case de la carte
     * @throws ConstructionException Si l'un des paramètres est négatif ou nul
     */
    public Carte(int nbLignes, int nbColonnes, int tailleCases) throws ConstructionException {
        if(nbLignes<0 || nbColonnes<0 || tailleCases<0) {
            throw new ConstructionException("Les paramètres de la carte doivent être positifs !");
        }
        
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.tailleCases = tailleCases;
        this.carte = new Case[nbLignes][nbColonnes];
        // On n'initialise pas les cases ici, on dispose d'une méthode plus bas pour le faire
    }


    /**
     * Accesseur sur le nombre de lignes de la carte.
     * @return Nombre de lignes de la carte
     */
    public int getNbLignes() {
        return this.nbLignes;
    }
    /**
     * Accesseur sur le nombre de colonnes de la carte.
     * @return Nombre de colonnes de la carte
     */
    public int getNbColonnes() {
        return this.nbColonnes;
    }
    /**
     * Accesseur sur la taille des cases.
     * @return Taille (en mètres) d'une case de la carte
     */
    public int getTailleCases() {
        return this.tailleCases;
    }


    // Pas de mutateurs : la carte est chargée une fois pour toutes en mémoire

    /**
     * Initialisation d'une case à la place (i, j) à partir du coin supérieur gauche de la carte.
     * @param i Numéro de la ligne, en partant de zéro
     * @param j Numéro de la colonne, en partant de zéro
     * @param t Nature du terrain
     * @throws ConstructionException si les coordonnées sont à l'extérieur des limites de la carte ou si la case a déja été initialisée
     */
    public void setCase(int ligne, int colonne, NatureTerrain t) throws ConstructionException {
        setCase(new Case(ligne, colonne, t)); // On crée une case (composition) qui sera détruite à la destruction de la Carte
    }
    /**
     * Initialisation d'une case.
     * @param c Case à initialiser
     * @throws ConstructionException si les coordonnées sont à l'extérieur des limites de la carte ou si la case a déja été initialisée
     */
    public void setCase(Case c) throws ConstructionException {
        if (c.getLigne()<0 || c.getLigne()>=nbLignes || c.getColonne()<0 || c.getColonne()>=nbColonnes) {
            throw new ConstructionException("Création d'une case en-dehors des limites de la carte !");
        }
        
        if (carte[c.getLigne()][c.getColonne()] != null) {
            throw new ConstructionException("Case déjà initialisée !");
        }
        
        carte[c.getLigne()][c.getColonne()] = c;
    }


    /**
     * Récupérer une référence sur la case à la place (i,j) à partir du coin supérieur gauche de la carte.
     * @param i Numéro de la ligne, en partant de zéro
     * @param j Numéro de la colonne, en partant de zéro
     * @return Référence sur la case à la place (i,j)
     * @throws SimulationException si les coordonnées sont à l'extérieur des limites de la carte
     */
    public Case getCase(int ligne, int colonne) throws SimulationException {
        if (ligne<0 || ligne>=nbLignes || colonne<0 || colonne>=nbColonnes) {
            throw new SimulationException("Accès à une case en-dehors des limites de la carte !");
        }
        
        if (carte[ligne][colonne] == null) {
            carte[ligne][colonne] = new Case(ligne, colonne);
        }
        
        return carte[ligne][colonne];
    }

    /**
     * Rechercher si le voisin d'une case dans une direction donnée existe.
     * @param src Référence sur la case d'origine
     * @param dir Direction du déplacement
     * @return Booléen indiquant si un voisin existe dans la direction donnée
     */
    public boolean voisinExiste(Case src, Direction dir) {
        switch(dir) {
        case NORD:  // Il faut pouvoir atteindre la ligne 0
            return (src.getLigne()>0 && src.getLigne()<nbLignes
                    && src.getColonne()>=0 && src.getColonne()<nbColonnes);
        case SUD:   // Il faut pouvoir atteindre la ligne nbLignes-1
            return (src.getLigne()>=0 && src.getLigne()<nbLignes-1
                    && src.getColonne()>=0 && src.getColonne()<nbColonnes);
        case EST:   // Il faut pouvoir atteindre la colonne nbColonnes-1
            return (src.getLigne()>=0 && src.getLigne()<nbLignes
                    && src.getColonne()>=0 && src.getColonne()<nbColonnes-1);
        case OUEST: // Il faut pouvoir atteindre la colonne 0
            return (src.getLigne()>=0 && src.getLigne()<nbLignes
                    && src.getColonne()>0 && src.getColonne()<nbColonnes);
        default: return false;
        }
    }
    

    /**
     * Renvoyer une référence sur la case voisine dans une direction donnée.
     * @param src Référence sur la case d'origine
     * @param dir Direction du déplacement
     * @return Référence sur la case voisine dans la direction donnée
     * @throws SimulationException si la case ne possède pas de voisin dans la direction spécifiée
     */
    public Case getVoisin(Case src, Direction dir) throws SimulationException {
        Case ret = null;
        
        if(this.voisinExiste(src, dir) != true) {
			throw new SimulationException("Pas de voisin dans la direction spécifiée !");
		}
	
        switch(dir) {
            case NORD:  ret = getCase(src.getLigne()-1, src.getColonne()); break;
            case SUD:   ret = getCase(src.getLigne()+1, src.getColonne()); break;
            case EST:   ret = getCase(src.getLigne(), src.getColonne()+1); break;
            case OUEST: ret = getCase(src.getLigne(), src.getColonne()-1); break;
        }

        return ret;
    }

    /**
     * Rechercher si une case est voisine d'une case d'eau.
     * @param c Référence sur la case d'origine
     * @return Booléen indiquant si la case est voisine d'une case d'eau
     */
    public boolean estBordEau(Case c) {
        try {
            if(     (voisinExiste(c, Direction.NORD)==true && getVoisin(c, Direction.NORD).getTerrain() == NatureTerrain.EAU)
                 || (voisinExiste(c, Direction.SUD)==true && getVoisin(c, Direction.SUD).getTerrain() == NatureTerrain.EAU)
                 || (voisinExiste(c, Direction.EST)==true && getVoisin(c, Direction.EST).getTerrain() == NatureTerrain.EAU)
                 || (voisinExiste(c, Direction.OUEST)==true && getVoisin(c, Direction.OUEST).getTerrain() == NatureTerrain.EAU)
            ) {
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
