public class Date {
    private long date;
    
    // Constructeur
    public Date() {
        this.date = 0;
    }
    public Date(long date) {
        this.date = date;
    }
    
    // Accesseur
    public long getDate() {
        return this.date;
    }
    
    // Incrémente la date
    public void incrementeDate(long t) {
        this.date = date + t;
    }
    public void incrementeDate() {
        incrementeDate(1);
    }

    // Réinitialise la date courante
    public void resetDate() {
        this.date = 0;
    }
    
    // Retourne une date incrémentée de t
    public Date dateInc(long t) {
        return new Date(this.date + t);
    }

}

