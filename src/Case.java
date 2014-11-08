// Description d'une case
// Dernière modification : Thibaud BACKENSTRASS, 8 novembre
public class Case {
    // Coordonnées (entières, positives) de la case
    private int ligne;
    private int colonne;
    private NatureTerrain terrain;
    
    // Constructeur de case
    public Case(int ligne, int colonne, NatureTerrain terrain) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.terrain = terrain;
    }

    // Accesseurs
    public int getLigne() {
        return this.ligne;
    }
    public int getColonne() {
        return this.colonne;
    }
    public NatureTerrain getTerrain() {
        return this.terrain;
    }

    // Mutateurs
    public void setLigne(int ligne) {
        if(ligne<0) { // Invariant de classe
            throw new ArithmeticException("Les coordonnées d'une case ne peuvent pas être négatives !");
        }
        else {
            this.ligne = ligne;
        }
    }
    public void setColonne(int colonne) {
        if(colonne<0) { // Invariant de classe
            throw new ArithmeticException("Les coordonnées d'une case ne peuvent pas être négatives !");
        }
        else {
            this.colonne = colonne;
        }
    }
    public void setTerrain(NatureTerrain terrain) {
        this.terrain = terrain;
    }

}
