
public class Astar {
    private Carte carte;
    
    public Astar(Carte carte) {
        this.carte = carte;
    }
    
    public Case next(Case start, Case objectif, ValideCase validateur) {
        switch (start.getDirection(objectif)) {
        case up:
            return start.up();
        case down:
            return start.down();
        case left:
            return start.left();
        case right:
            return start.right();
        case topLeft:
            return start.up();
        case topRight:
            return start.up();
        case bottomLeft:
            return start.down();
        case bottomRight:
            return start.down();
        case same:
            return start;
        default:
            // Add exception
            return start;
        }
    }
}
