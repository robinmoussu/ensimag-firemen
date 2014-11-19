import static java.lang.Math.abs;
import java.util.PriorityQueue;
import java.util.Stack;

/** Algorithme de résolution du plus court chemin utilisant la méthode Astar
 * 
 * exemple d'utilisation :
 * On veut aller d'une case depart à une case arrivee
 * 
 * Carte c;
 * Case depart, arrivee;
 * Astar astar;
 * ValideCase validateur;
 * 
 * astar = new Astar(carte, depart, arrivee, validateur);
 * 
 * // On se déplace vers l'arrivee
 * Case case_courante = depart;
 * while(case_courante != arrivee) {
 *      case_courante = astar.next(case_courante);
 * }
 */
public class Astar implements Comparable<Astar> {
    protected Carte carte;
    protected Case arrivee;
    protected ValideCase validateur;
    protected int lastIndex = 0;
    protected Stack<Case> solution;
    protected boolean pasDeCheminTrouve;

    /**
     * 
     * @param carte Carte de référence pour le calcul de plus court chemin
     * @param depart Case de départ
     * @param arrivee Case de depart
     * @param validateur Foncteur permettant d'évaluer la meilleure case à
     * choisir
     * @throws SimulationException En cas de trajet invalide
     */
    public Astar(Carte carte, Case depart, Case arrivee, ValideCase validateur)
            throws SimulationException {
        this.carte = carte;
        this.arrivee = arrivee;
        this.validateur = validateur;
        initParcourt(depart);
    }

    /** Donne la distance necessaire pour parcourir le plus court chemin
     * calculé par cet Astar en unité arbitraire
     * 
     * @return 0 distance nulle (le point de départ et d'arrivée se confondent);
     *         -1 en cas d'erreur;
     *         valeur positive : la distance
     */
    public int getDistance() {
        return this.solution.size();
    }
    
    public boolean finished() {
        return this.solution.size() <= 1;
    }
    
    /** Renvoie le plus court chemin entre deux Astar.
     * 
     * Permet de comparer la distance séparant un point A de deux point B et C,
     * ou de voir l'influence que peut avoir le changement du validateur
     */
    @Override
    public int compareTo(Astar o) {
        return this.getDistance() - o.getDistance();
    }

    /** Class utilisée en interne pour calculer le plus court chemin
     */
    private static class Graph {
        Carte carte;
        Node[][] map;
        
        /** 
         * @param carte Carte servant lors de l'exploration
         */
        public Graph(Carte carte) {
            this.carte = carte;
            map = new Node[carte.getNbColonnes()][carte.getNbLignes()];
        }

        /**
         * @param prev Noeud précédant dans le graph. Est null uniquement pour
         * le premier noeud du graph
         * @param case_ La case associé au nouveau noeud
         * @param distanceToThatCase distance séparant le noeud associé à la
         * case case_ de l'arrivée.
         * @return Le noeud associé à la Case case_
         */
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
        
        /**
         * @return true si le noeud associé à la case case_ existe déjà dans le
         * graph
         */
        public boolean estVisitee(Case case_) {
            return map[case_.getColonne()][case_.getLigne()] != null;
        }

        /** Permet de mettre à jour le cout d'un noeud
         */
        private void updateNode(Node it, Case caseVoisine) {
            // TODO update methode
        }
        
        /** À chaque case de la carte est associé un noeud présent dans le graph
         * Ne doit être manipulé qu'au travers de la class Graph
         */
        public class Node implements Comparable<Node> {

            Node prev;
            boolean visitee;
            Case case_;
            int distance;

            /** cf Graph.getNode
             */
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
            
            /**
             * @return Le noeud précédant dans le graph. this est le meilleur
             * chemin connu qui relie le noeud précédant de l'arrivée.
             */
            public Node getPrev() {
                return prev;
            }

            /** Les noeuds sont comparable par la distance qui les séparent de
             * l'arrivée.
             */
            @Override
            public int compareTo(Node other) {
                if (this == other) {
                    return 0;
                }

                return this.distance - other.distance;
            }
        }
    }

    /** Calcule le meilleur parcourt entre la case départ et l'arrivée
     * Doit être appelé dans le constructeur.
     * @param depart case de départ de l'algoritme
     */
    public final void initParcourt(Case depart) {
        Graph graph;
        PriorityQueue<Graph.Node> eligible;
        Graph.Node it;

        graph = new Graph(this.carte);
        eligible = new PriorityQueue<>();

        eligible.add(graph.getNode(null, depart, this.arrivee));

        for (it = eligible.poll(); it != null
                && ! it.getCase().equals(this.arrivee); it = eligible.poll()) {
            
            Direction[] dirVoisins = {Direction.NORD, Direction.SUD,
                Direction.EST, Direction.OUEST};
            
            for(Direction dir: dirVoisins) {
                if (this.carte.voisinExiste(it.getCase(), dir)) {
                    Case caseVoisine;
                    
                    try {
                        caseVoisine = carte.getVoisin(it.getCase(), dir);
                        if (graph.estVisitee(caseVoisine)) {
                            graph.updateNode(it, caseVoisine);
                        } else {
                            Graph.Node voisin = graph.getNode(it, caseVoisine,
                                    this.arrivee);
                            if ((this.validateur.estValide(voisin.getCase()) > 0)
                                    || voisin.getCase().equals(this.arrivee)) {
                                eligible.add(voisin);
                            }
                        }
                    } catch (SimulationException ex) {
                        // On ignore le fait qu'il n'y ai pas de case voisine
                    }
                }
            }
        }
        
        this.pasDeCheminTrouve = (eligible.size() == 0);
        System.out.println("pas de chemin trouvé");
        
        // À ce point là, it == solution;

        // On calcule la solution
        this.solution = new Stack<>();
        while (it != null) {
            this.solution.add(it.getCase());
            it = it.getPrev();
        }
    }

    /** Retourne la case suivante du parcourt menant à l'arrivée.
     * cf l'explication de la class Astar pour un exemple d'utilisation.
     * 
     * @param depart La case courante
     * @return La case suivante du parcourt
     * @throws SimulationException Si le trajet ne correspond pas
     */
    public Case next(Case depart) throws SimulationException {
        if (this.solution.empty()) {
            return depart; // On à atteind l'arrivée
        }
        
        if (!this.solution.pop().equals(depart)) {
            throw new SimulationException("Le trajet calculé ne correspond pas");
        } else {
            if (this.solution.empty()) {
                return depart; // On à atteind l'arrivée
            } else {
                return solution.peek();
            }
        }
    }
}
