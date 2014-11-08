// Description de la carte
// Dernière modification : Thibaud BACKENSTRASS, 8 novembre
public class Carte {
    // Attributs
    private int nbLignes; // >=0
    private int nbColonnes; // >=0
    private int tailleCases; // >=0

    private Case[][] carte;

    // Constructeur
    public Carte(int nbLignes, int nbColonnes, int tailleCases) {
        if(nbLignes<0 || nbColonnes<0 || tailleCases<0) {
            throw new ArithmeticException("Les paramètres de la carte doivent être positifs !");
        }
        else {
            this.nbLignes = nbLignes;
            this.nbColonnes = nbColonnes;
            this.tailleCases = tailleCases;
            this.carte = new Case[nbLignes][nbColonnes];
            // On n'initialise pas les cases ici, on dispose d'une méthode plus bas pour le faire
        }
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
    public void setCase(int i, int j, NatureTerrain t) {
        if(i<0 || i>=nbLignes || j<0 || j>=nbColonnes) {
            throw new ArithmeticException("Création d'une case en-dehors des limites de la carte !");
        }
        else {
            carte[i][j] = new Case(i, j, t);
        }
    }

    // Récupérer une référence sur une case à partir de ses coordonnées
    public Case getCase(int i, int j) {
        if(i<0 || i>=nbLignes || j<0 || j>=nbColonnes) {
            throw new ArithmeticException("Accès à une case en-dehors des limites de la carte !");
        }
        else {
            return carte[i][j];
        }
    }

    // Rechercher si un voisin existe
    public boolean voisinExiste(Case src, Direction dir) {
        switch(dir) {
            case NORD:  // Il faut pouvoir atteindre la ligne 0
                        if(src.getLigne()>0 && src.getLigne()<nbLignes && src.getColonne()>=0 && src.getColonne()<nbColonnes) {
                           return true;
                        }
                        break;
            case SUD:   // Il faut pouvoir atteindre la ligne nbLignes-1
                        if(src.getLigne()>=0 && src.getLigne()<nbLignes-1 && src.getColonne()>=0 && src.getColonne()<nbColonnes) {
                            return true;
                        }
                        break;
            case EST:   // Il faut pouvoir atteindre la colonne nbColonnes-1
                        if(src.getLigne()>=0 && src.getLigne()<nbLignes && src.getColonne()>=0 && src.getColonne()<nbColonnes-1) {
                            return true;
                        }
                        break;
            case OUEST: // Il faut pouvoir atteindre la colonne 0
                        if(src.getLigne()>=0 && src.getLigne()<nbLignes && src.getColonne()>0 && src.getColonne()<nbColonnes) {
                            return true;
                        }
                        break;
            default: return false;
        }
        return false;
    }
    
    // Renvoyer une référence sur la case du voisin
    public Case getVoisin(Case src, Direction dir) {
        if(this.voisinExiste(src, dir) == true) {
            switch(dir) {
                case NORD:  return carte[src.getLigne()-1][src.getColonne()];
                case SUD:   return carte[src.getLigne()+1][src.getColonne()];
                case EST:   return carte[src.getLigne()][src.getColonne()+1];
                case OUEST: return carte[src.getLigne()][src.getColonne()+1];
            }
        }
        else {
            throw new ArithmeticException("Pas de voisin existant !");
        }
        return null; // Pour le compilateur seulement, on ne retournera jamais null en réalité...
    }

}
