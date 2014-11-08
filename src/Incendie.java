// Description d'un incendie
// Dernière modification : Thibaud BACKENSTRASS, 8 novembre
public class Incendie {
    // Attributs
    private Case position;
    private int eauNecessaire; // Nombre de litres d'eau nécessaires pour l'éteindre

    // Constructeur
    public Incendie(Case c, int eau) {
        if(eau<0) {
            throw new ArithmeticException("Quantité d'eau négative !");
        }
        else {
            this.eauNecessaire = eau;
        }
        this.position = c;
    }

    // Accesseurs
    public Case getPosition() {
        return this.position;
    }
    public int getEauNecessaire() {
        return this.eauNecessaire;
    }

    // Mutateurs
    // Pas de déplacement de l'incendie, on mute seulement l'eau
    public void setEauNecessaire(int eau) {
        if(eau<0) {
            throw new ArithmeticException("Quantité d'eau négative !");
        }
        else {
            this.eauNecessaire = eau;
        }
    }

}
