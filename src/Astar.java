
import java.util.logging.Level;
import java.util.logging.Logger;


public class Astar {
    private Carte carte;
    
    public Astar(Carte carte) {
        this.carte = carte;
    }
    
    public Case next(Case start, Case objectif, ValideCase validateur) {
        Case destination = start;

        try {
        switch (start.getDirection(objectif)) {
        case NORD:
            destination = this.carte.getVoisin(start, Direction.NORD);
            break;
        case SUD:
            destination = this.carte.getVoisin(start, Direction.SUD);
            break;
        case OUEST:
            destination = this.carte.getVoisin(start, Direction.OUEST);
            break;
        case EST:
            destination = this.carte.getVoisin(start, Direction.EST);
            break;
        case NORD_OUEST:
            destination = this.carte.getVoisin(start, Direction.NORD);
            break;
        case NORD_EST:
            destination = this.carte.getVoisin(start, Direction.NORD);
            break;
        case SUD_OUEST:
            destination = this.carte.getVoisin(start, Direction.SUD);
            break;
        case SUD_EST:
            destination = this.carte.getVoisin(start, Direction.SUD);
            break;
        case NONE:
            destination = start;
            break;
        default:
            // Add exception
            destination = start;
            break;
        }
        
        } catch (SimulationException ex) {
            Logger.getLogger(Astar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return destination;
    }
}
