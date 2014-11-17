/**
 * Incendie.
 * @author Thibaud Backenstrass
 * @date 2014-11-17
 */
public class Incendie {
    private Case position;
    private int eauNecessaire; // Nombre de litres d'eau nécessaires pour l'éteindre

    
    /**
     * Constructeur d'un incendie.
     * @param c Case sur laquelle créer l'incendie
     * @param eau Quantité d'eau (en litres) nécessaire pour l'éteindre
     * @throws ConstructionException si la quantité d'eau est négative
     */
    public Incendie(Case c, int eau) throws ConstructionException {
        if(eau<0) {
            throw new ConstructionException("Quantité d'eau négative !");
        }
        
        this.eauNecessaire = eau;
        this.position = c;
    }

    
    /**
     * Accesseur sur la position de l'intensité.
     * @return Case sur laquelle l'incendie est localisé
     */
    public Case getPosition() {
        return this.position;
    }
    /**
     * Accesseur sur l'intensité de l'incendie.
     * @return Nombre de litres d'eau nécessaires pour éteindre l'incendie
     */
    public int getIntensite() {
        return this.eauNecessaire;
    }


    /**
     * Décrémente la quantité d'eau nécessaire pour éteindre l'incendie.
     * @param eau Nombre de litres d'eau jetés sur l'incendie
     */
    public void decrementeIntensite(int eau) {
        if(eau<this.eauNecessaire) {
            this.eauNecessaire -= eau;
        }
        else {
            this.eauNecessaire = 0;
        }
    }

}
