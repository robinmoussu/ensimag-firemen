// Description de la carte
// Dernière modification : Thibaud BACKENSTRASS, 9 novembre
public class Carte {
    // Attributs
    private int nbLignes; // >=0
    private int nbColonnes; // >=0
    private int tailleCases; // >=0

    private Case[][] carte;

    /**
     * @param nbLignes      Nombre de ligne de la carte (doit être > 0)
     * @param nbColonnes    Nombre de colonne de la carte (doit être > 0)
     * @param tailleCases   Taille d'une case dans la simulation (doit être > 0)
     * @throws ConstructionException Si un des paramètre est <= 0
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

    // Accesseurs
    public int getNbLignes() {
        return this.nbLignes;
    }
    public int getNbColonnes() {
        return this.nbColonnes;
    }
    public int getTailleCases() {
        return this.tailleCases;
    }

    // Pas de mutateurs : la carte est chargée une fois pour toutes en mémoire

    // Initialisation d'une case à la place (i, j)
    public void setCase(int ligne, int colonne, NatureTerrain t) throws ConstructionException {
        setCase(new Case(ligne, colonne, t)); // On crée une case (composition) qui sera détruite à la destruction de la Carte
    }
    public void setCase(Case case_) throws ConstructionException {
        if (case_.getLigne()<0 || case_.getLigne()>=nbLignes ||
                case_.getColonne()<0 || case_.getColonne()>=nbColonnes) {
            throw new ConstructionException(
                    "Création d'une case en-dehors des limites de la carte !");
        }
        
        if (carte[case_.getLigne()][case_.getColonne()] != null) {
            throw new ConstructionException("Case déjà existante");
        }
        
        carte[case_.getLigne()][case_.getColonne()] = case_;
    }

    // Récupérer une référence sur une case à partir de ses coordonnées
    public Case getCase(int ligne, int colonne) throws SimulationException {
        if (ligne<0 || ligne>=nbLignes || colonne<0 || colonne>=nbColonnes) {
            throw new SimulationException("Accès à une case en-dehors des limites de la carte !");
        }
        
        if (carte[ligne][colonne] == null) {
            carte[ligne][colonne] = new Case(ligne, colonne);
        }
        
        return carte[ligne][colonne];
    }

    // Rechercher si un voisin existe
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
    
    /** Renvoyer une référence sur la case du voisin
     * 
     * @param src Case de départ
     * @param dir Direction du déplacement.
     * @return
     * @throws SimulationException 
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

    // Savoir si une case est en bordure de l'eau ou non
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
