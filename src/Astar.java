import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Algorithme de résolution du plus court chemin utilisant la méthode Astar
 */
public class Astar implements Comparable<Astar> {
    protected Carte carte;
    protected Case arrivee;
    protected ValideCase validateur;
    protected int lastIndex = 0;
    protected Stack<Case> solution;

    public Astar(Carte carte, Case depart, Case arrivee, ValideCase validateur)
            throws SimulationException {
        this.carte = carte;
        this.arrivee = arrivee;
        this.validateur = validateur;
        initParcourt(depart);
    }

    public int getDistance() {
        return this.solution.size();
    }
    @Override
    public int compareTo(Astar o) {
        return this.getDistance() - o.getDistance();
    }

    private static class Graph {
        Carte carte;
        Node[][] map;
        public Graph(Carte carte) {
            this.carte = carte;
            map = new Node[carte.getNbColonnes()][carte.getNbLignes()];
        }

        public Node getNode(Node prev, Case case_, Case distanceToThatCase) {
            Node node = map[case_.getColonne()][case_.getLigne()];
            if ( node == null) {
                node = new Node(prev, case_, distanceToThatCase);
                map[case_.getColonne()][case_.getLigne()] = node;
            }
            if (prev != null) {
                prev.visitee = true;
            }
            return node;
        }
        
        public boolean estVisitee(Case case_) {
            return map[case_.getColonne()][case_.getLigne()] != null;
        }

        private void updateNode(Node it, Case caseVoisine) {
            // TODO update methode
        }
        
        public class Node implements Comparable<Node> {

            Node prev;
            boolean visitee;
            Case case_;
            int distance;

            private Node(Node prev, Case case_, Case distanceToThatCase) {
                this.prev = prev;
                this.case_ = case_;
                this.visitee = false;
                this.distance = abs(case_.getColonne() - distanceToThatCase.getColonne())
                      + abs(case_.getLigne() - distanceToThatCase.getLigne());
            }        

            public Case getCase() {
                return this.case_;
            }
            
            public Node getPrev() {
                return prev;
            }

            @Override
            public int compareTo(Node other) {
                if (this == other) {
                    return 0;
                }

                return this.distance - other.distance;
            }
        }
    }

    public final void initParcourt(Case depart) throws SimulationException {
        Graph graph;
        PriorityQueue<Graph.Node> eligible;
        Graph.Node it;

        graph = new Graph(this.carte);
        eligible = new PriorityQueue<>();

        eligible.add(graph.getNode(null, depart, this.arrivee));

        while(! (it = eligible.poll()).getCase().equals(this.arrivee)) {
            
            Direction[] dirVoisins = {Direction.NORD, Direction.SUD,
                Direction.EST, Direction.OUEST};
            
            for(Direction dir: dirVoisins) {
                if (this.carte.voisinExiste(it.getCase(), dir)) {
                    Case caseVoisine;
                    
                    caseVoisine = carte.getVoisin(it.getCase(), dir);
                    if (graph.estVisitee(caseVoisine)) {
                        graph.updateNode(it, caseVoisine);
                    } else {
                        Graph.Node voisin = graph.getNode(it, caseVoisine, this.arrivee);
                        if(this.validateur.estValide(voisin.getCase())) {
                            eligible.add(voisin);
                        }
                    }
                }
            }
        }
        
        // À ce point là, it == solution;

        // On calcule la solution
        this.solution = new Stack<>();
        while (it != null) {
            this.solution.add(it.getCase());
            it = it.getPrev();
        }
    }

    public Case next(Case depart) throws SimulationException {
        if (!this.solution.pop().equals(depart)) {
            throw new SimulationException("Le trajet calculé ne correspond pas");
        }
        if (solution.empty()) {
            return depart; // On à atteind l'arrivée
        } else {
            return solution.peek();
        }
    }
}
