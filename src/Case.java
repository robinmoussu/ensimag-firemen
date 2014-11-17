import java.util.Objects;

public class Case {
    private int ligne; // >0
    private int colonne; // >0
    private NatureTerrain terrain;
    
    /**
     * Constructeur de la case aux coordonnées (i,j) par rapport au coin supérieur droit d'une carte, et de terrain spécifié.
     * @param ligne Numéro de la ligne de la case à créer, à partir de zéro
     * @param colonne Numéro de la colonne de la case à créer, à partir de zéro
     * @param terrain Nature du terrain sur la case à créer
     */
    public Case(int ligne, int colonne, NatureTerrain terrain) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.terrain = terrain;
    }
    /**
     * Constructeur de la case aux coordonnées (i,j) par rapport au coin supérieur droit d'une carte, et de Terrain Libre.
     * @param ligne Numéro de la ligne de la case à créer, à partir de zéro
     * @param colonne Numéro de la colonne de la case à créer, à partir de zéro
     */
    public Case(int ligne, int colonne) {
        this(ligne, colonne, NatureTerrain.TERRAIN_LIBRE);
    }


    /**
     * Accesseur sur la ligne de la case.
     * @return Numéro de la ligne de la case, à partir de zéro
     */
    public int getLigne() {
        return this.ligne;
    }
    /**
     * Accesseur sur la colonne de la case.
     * @return Numéro de la colonne de la case, à partir de zéro
     */
    public int getColonne() {
        return this.colonne;
    }
    /**
     * Accesseur sur la nature du terrain de la case.
     * @return Nature du terrain de la case
     */
    public NatureTerrain getTerrain() {
        return this.terrain;
    }


    /**
     * Méthode d'égalisation de deux cases.
     * @param o Objet à comparer
     * @return Booléen indiquant si les deux cases ont mêmes coordonnées
     */
    @Override
    public boolean equals(Object o) {
        if(this==o) {
            return true;
        }
        if(!(o instanceof Case)) {
            return false;
        }

        Case c = (Case) o; // downcasting
        // Deux cases sont égales si et seulement si elles sont à la même place sur la carte
        return this.ligne == c.getLigne() && this.colonne == c.getColonne();
    }


    /**
     * Méthode de calcul d'un hashCode sur une case.
     * @return Clé de hachage de la case
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.ligne;
        hash = 97 * hash + this.colonne;
        hash = 97 * hash + Objects.hashCode(this.terrain);
        return hash;
    }
    
    /**
     * Rechercher si deux cases sont voisines.
     * @param c Case à comparer avec la case courante
     * @return Booléen indiquant si les deux cases sont voisines
     */
    public boolean estVoisine(Case c) {
        if(    (c.getLigne()-this.ligne==1 && c.getColonne()-this.colonne==0)
            || (c.getLigne()-this.ligne==-1 && c.getColonne()-this.colonne==0)
            || (c.getLigne()-this.ligne==0 && c.getColonne()-this.colonne==1)
            || (c.getLigne()-this.ligne==0 && c.getColonne()-this.colonne==-1)
          ) {
            return true;
        }
        else {
            return false;
        }
    }
    
    
    /**
     * Représentation chaînée d'une case.
     * @return Chaîne de caractères représentant les coordonnées de la case
     */
    public String toString() {
        return "Case " + this.ligne + ", " + this.colonne;
    }
    

    /**
     * Retourne la direction globale permettant d'atteindre une autre case.
     * @param other Case de destination
     * @return Direction pour se diriger vers la case de destination
     */
    public Direction getDirection(Case other) {
        if ((this.colonne <  other.colonne) && (this.ligne == other.ligne)) {
            return Direction.EST;
        } else if ((this.colonne >  other.colonne) && (this.ligne == other.ligne)) {
            return Direction.OUEST;
        } else if ((this.colonne == other.colonne) && (this.ligne >  other.ligne)) {
            return Direction.NORD;
        } else if ((this.colonne == other.colonne) && (this.ligne <  other.ligne)) {
            return Direction.SUD;
        } else if ((this.colonne <  other.colonne) && (this.ligne >  other.ligne)) {
            return Direction.NORD_EST;
        } else if ((this.colonne <  other.colonne) && (this.ligne <  other.ligne)) {
            return Direction.SUD_EST;
        } else if ((this.colonne >  other.colonne) && (this.ligne >  other.ligne)) {
            return Direction.NORD_OUEST;
        } else if ((this.colonne >  other.colonne) && (this.ligne <  other.ligne)) {
            return Direction.SUD_OUEST;
        } else {
            return Direction.NONE;
        }
    }

}
