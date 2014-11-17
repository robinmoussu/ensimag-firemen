import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Calcul du plus court chemin selon l'algorithme Astar.
 * @author Robin Moussu
 * @date 2014-11-17
 */
public class Astar {
    protected Carte carte;
    protected Noeud arrivee;

    public Astar(Carte carte, Case arrivee) {
        this.carte = carte;
        this.arrivee = new Noeud(arrivee);
    }

    protected class Noeud implements Comparable<Noeud> {
        protected ArrayList<Noeud> voisins;
        protected Noeud precedant;
        protected int  cost;
        protected Case case_;

        public Noeud(Case c) {
            case_  = c;
            precedant = null;
            cost      = 0;
            voisins   = new ArrayList<>();
        }

        public void set_voisins(ArrayList<Noeud> voisins) {
            this.voisins = voisins;
        }

        public ArrayList<Noeud> getVoisins() {
            return this.voisins;
        }

        public void setPrecedant(Noeud precedant) {
            this.precedant = precedant;
        }

        public boolean isExplored() {
            return this.cost != 0;
        }

        public int getCost() {
            return cost;
        }
        
        public Case getCase() {
            return case_;
        }

        public int coup(Noeud voisin) {
            assert this.cost != 0;
            return 0;//vit_max(voisin.position.getTerrain()) + this.cost;
        }

        @Override
        public int compareTo(Noeud other) {
            if (this == other) {
                return 0;
            }
            
            int other_distance = 
                    abs(other.case_.getColonne() - arrivee.getCase().getColonne())
                  + abs(other.case_.getLigne() - arrivee.getCase().getLigne());
            
            int self_distance = 
                    abs(this.case_.getColonne() - arrivee.getCase().getColonne())
                  + abs(this.case_.getLigne() - arrivee.getCase().getLigne());
            
            return self_distance - other_distance;
        }
    }
    
    private Case parcourt(Case start, ValideCase validateur) throws SimulationException {
        // to do a mettre en cache
        Noeud it;
        PriorityQueue<Noeud> eligible;
        Stack<Noeud> solution;

        eligible = new PriorityQueue<>();
        solution = new Stack<>();
        it = new Noeud(start);

        do {
            Case current, next;
            Case nord, sud, est, ouest;
            
            current = it.getCase();

            nord  = this.carte.getVoisin(current, Direction.NORD);
            sud   = this.carte.getVoisin(current, Direction.SUD);
            est   = this.carte.getVoisin(current, Direction.OUEST);
            ouest = this.carte.getVoisin(current, Direction.EST);
            
            if (nord != null) {
                eligible.add(new Noeud(nord));
            }
            if (sud != null) {
                eligible.add(new Noeud(sud));
            }
            if (est != null) 
                eligible.add(new Noeud(est));
            
            if (ouest != null) {
                eligible.add(new Noeud(ouest));
            }

            switch (start.getDirection(this.arrivee.getCase())) {
            case NORD:       next = nord;    break;
            case SUD:        next = sud;     break;
            case OUEST:      next = ouest;   break;
            case EST:        next = est;     break;
            case NORD_OUEST: next = nord;    break;
            case NORD_EST:   next = nord;    break;
            case SUD_OUEST:  next = sud;     break;
            case SUD_EST:    next = sud;     break;
            case NONE:       next = current; break;
            default:
                // Add exception
                next = current;
                break;
            }
            
            if((next != null) && (validateur.estValide(next))) {
                solution.add(new Noeud(next));
            }
            
            it = eligible.poll();
        } while ((it != null) &&
                (solution.peek().getCase() != this.arrivee.getCase()));
    
        return solution.peek().getCase();
    }

    public Case next(Case start, ValideCase validateur) {
        Case destination;

        try {
            destination = parcourt(start, validateur);
        } catch (SimulationException ex) {
            Logger.getLogger(Astar.class.getName()).log(Level.SEVERE, null, ex);
            destination = null;
        }
        
        return destination;
    }
}
