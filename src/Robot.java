// Classe abstraite de description d'un robot
// Dernière modification : Thibaud BACKENSTRASS, 10 novembre
abstract class Robot {
    // Attributs
    private Case position;
    protected int volumeEau = 0; // en litres, 0 par défaut
    // Attributs à définir dans les sous-classes
    private int vitesse; // en km/h
    private int volumeMax; // en litres
    private int tempsRemplissage; // en secondes
    private int volumeIntervention; // en litres
    private int dureeIntervention; // en secondes
    

    // Constructeur
    public Robot(Case pos, int vitesse, int volumeMax, int tempsRemplissage, int volumeIntervention, int dureeIntervention) {
        this.position = pos;
        this.vitesse = vitesse;
        this.volumeMax = volumeMax;
        this.tempsRemplissage = tempsRemplissage;
        this.volumeIntervention = volumeIntervention;
        this.dureeIntervention = dureeIntervention;
    }
    
    // Accesseurs
    public Case getPosition() {
        return this.position;
    }
    public int getVolumeEau() {
        return this.volumeEau;
    }
    abstract public int getVitesse(NatureTerrain terrain);
    public int getTempsRemplissage() {
        return this.tempsRemplissage;
    }
    public int getVolumeIntervention() {
        return this.volumeIntervention;
    }
    public int getDureeIntervention() {
        return this.dureeIntervention;
    }
    abstract public String getImage();


    // Mutateurs
    public void setPosition(Case c) {
        this.position = c;
    }
    
    // Déverser le réservoir d'eau
    public void deverserEau(int nbInterventions) {
        if(nbInterventions>volumeIntervention/volumeMax) {
            throw new ArithmeticException("Pas assez d'eau dans le réservoir !");
        }
        else {
            this.volumeEau = this.volumeEau - nbInterventions*volumeIntervention;
        }
    }

    // Méthodes abstraites
    abstract public void deplacer(Case c);
    abstract public void remplirReservoir(Carte carte);

}