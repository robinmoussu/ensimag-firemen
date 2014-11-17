// Description d'un robot CHENILLES
// Dernière modification : Thibaud BACKENSTRASS, 10 novembre
public class RobotChenilles extends Robot {
    // Attributs
    static private int vitesse = 60; // <=80
    static final int volumeMax = 2000;
    static final int tempsRemplissage = 300;
    static final int volumeIntervention = 100;
    static final int dureeIntervention = 8;

    // Constructeurs
    public RobotChenilles(Case pos) {
        super(pos, vitesse, volumeMax, tempsRemplissage, volumeIntervention, dureeIntervention);
        volumeEau = volumeMax;
    }
    public RobotChenilles(Case pos, int vitesse) {
        this(pos);
        if(vitesse>0 && vitesse<=80) {
            this.vitesse = vitesse;
        }
    }
    
    // Pas de mutateurs : la vitesse ne change pas en cours de route !

    // Renvoie la vitesse du robot en fonction du terrain
    @Override
    public int getVitesse(NatureTerrain terrain) {
        if(terrain == NatureTerrain.EAU || terrain == NatureTerrain.ROCHE) {
            return 0;
        }
        else if(terrain == NatureTerrain.FORET) {
            return this.vitesse/2;
        }
        else {
            return this.vitesse;
        }
    }

    // Renvoie l'image du robot
    public String getImage() {
        return "images/chenilles.png";
    }

    // Déplacer le robot sur une case
    // On doit vérifier que les cases sont voisines, et que la nature du terrain soit compatible
    @Override
    public void deplacer(Case c) throws SimulationException {
        if(c.estVoisine(this.getPosition())==false || c.getTerrain()==NatureTerrain.EAU || c.getTerrain()==NatureTerrain.ROCHE)  {
            throw new SimulationException("Case inaccessible pour le robot sélectionné !");
        }
        
        this.setPosition(c);
    }

    // Remplir le réservoir d'eau si la case le permet
    @Override
    public void remplirReservoir(Carte carte) throws SimulationException {
        if(carte.estBordEau(this.getPosition()) != true) {
            throw new SimulationException("Impossible de remplir le réservoir sur la case actuelle !");
        }
        
        this.volumeEau = this.volumeMax;
    } 

    @Override
    public ValideCase getValidateur() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
