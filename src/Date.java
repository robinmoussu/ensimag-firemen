/**
 * Objet-date permettant de représenter une date commune à toute la simulation.
 * @author Amanda Sambath
 * @date 2014-11-15
 */
public class Date {
    private long date;
    
    /**
     * Constructeur de date nulle.
     */
    public Date() {
        this.date = 0;
    }
    /**
     * Constructeur de date.
     * @param date Date à laquelle initialiser l'objet-date
     */
    public Date(long date) {
        this.date = date;
    }

    
    /**
     * Accesseur sur la date.
     * @return Date courante (en secondes)
     */
    public long getDate() {
        return this.date;
    }

    
    /**
     * Incrémente la date d'un certain nombre de secondes.
     * @param t Nombre d'unités à ajouter à la date courante
     */
    public void incrementeDate(long t) {
        this.date = date + t;
    }
    /**
     * Incrémente la date d'une seconde.
     */
    public void incrementeDate() {
        incrementeDate(1);
    }


    /**
     * Réinitialiser la date courante à la date nulle.
     */
    public void resetDate() {
        this.date = 0;
    }
    

    /**
     * Retourne la date courante incrémentée d'un certain nombre de secondes, mais sans modifier la date courante.
     * @param t Nombre d'unités à ajouter à la date courante
     * @return Date courante incrémentée de t secondes
     */
    public Date dateInc(long t) {
        return new Date(this.date + t);
    }

}

