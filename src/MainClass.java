import ihm.*;
import java.awt.Color;
import java.util.LinkedList;
import java.io.FileNotFoundException;

/**
 * Classe principale, chargée d'appeler toutes les autres.
 * @author Thibaud Backenstrass
 * @date 2014-11-17
 */
public class MainClass {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java AfficheSimulation <nomDeFichier>");
            System.exit(1);
        }
        try {
            String filename = args[0];
            LecteurDonnees lecteur = new LecteurDonnees(filename);
            DonneesSimulation simulation = lecteur.creeDonnees();

            Firemen firemen = new Firemen(simulation, filename); // Création de l'IHM
		} catch (FileNotFoundException e) {
			System.out.println("fichier " + args[0] + " inconnu ou illisible");
		} catch (ExceptionFormatDonnees e) {
			System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}


/**
 * Interface graphique.
 * @author Enseignants Ensimag
 * @date 2014-11-17
 */
class Firemen implements Simulable {
	private int nbLignes;
	private int nbColonnes;
    private IGSimulateur ihm;  // l'IHM associee a ce simulateur
    private Date date; // On utilise l'objet Date
    private DonneesSimulation simulation;
    private String filename;
    private Simulateur simulateur;
    private Manager manager;
   

    /**
     * Constructeur de l'interface graphique.
     * @param data Données de simulation
     * @param filename Nom du fichier de données
     */
	public Firemen(DonneesSimulation data, String filename) {
		// cree l'IHM et l'associe a ce simulateur (this), qui en tant que
        // Simulable recevra les evenements suite aux actions de l'utilisateur
        this.simulation = data;
        nbLignes = data.getNbLignes();
        nbColonnes = data.getNbColonnes();
		ihm = new IGSimulateur(nbColonnes, nbLignes, this); // Création de l'IHM
        date = new Date();
        this.filename = filename;
        this.simulateur = new Simulateur(date); // Création du simulteur
        this.manager = new ManagerScenario1(simulateur, simulation); // Création du manager
        this.simulateur.setManager(manager);
		dessine();    // mettre a jour l'affichage
	}
	

    /**
     * Avance d'un pas dans la simulation.
     */
	@Override
	public void next() {
		try {
            simulateur.incrementeDate(238); // Incrémenter la date courante et gérer les événements ; 238 correspond à un déplacement d'une case pour un drone
            dessine();
            System.out.println("[OK] Simulation avancée d'un pas à la date " + this.date.getDate());
        }
        catch (SimulationException e) {
            System.out.println("[ERR] Erreur lors de l'exécution de la simulation : " + e.getMessage());
        }
	}


    /**
     * Réinitialise la simulation dans son état initial.
     */
    @Override
    public void restart() {
        try {
            LecteurDonnees lecteur = new LecteurDonnees(filename);
            this.simulation = lecteur.creeDonnees();
            date.resetDate(); // Réinitialiser la date courante
            dessine(); // Mettre à jour l'affichage
            System.out.println("[OK] Redémarrage de la simulation depuis son état initial.");
        } catch (Exception e) {
            System.out.println("[ERR] Erreur lors de la remise à jour des données de simulation : " + e.getMessage());
        }
    }


    /**
     * Dessine les données de la simulation dans l'interface graphique.
     */
    private void dessine() {
        // Afficher les donnees 		
        try {
            // Affichage des données sur la nature du terrain
            for (int i = 0; i < this.simulation.getNbLignes(); i++) {
                for (int j = 0; j < this.simulation.getNbColonnes(); j++) {
                    Case c = (simulation.getCarte()).getCase(i, j);
                    // ATTENTION !
                    // i désigne la ligne, j la colonne de la case
                    // Il faut inverser les données pour les méthodes de ihm, qui veulent d'abord la colonne, puis la ligne
                    // Sinon problème de transposée...
                    switch (c.getTerrain()) {
                        case EAU:
                            ihm.paintImage(j, i, "images/eau.png", 1, 1);
                            break;
                        case FORET:
                            ihm.paintImage(j, i, "images/foret.png", 1, 1);
                            break;
                        case ROCHE:
                            ihm.paintImage(j, i, "images/roche.png", 1, 1);
                            break;
                        case TERRAIN_LIBRE:
                            ihm.paintImage(j, i, "images/terrain_libre.png", 1, 1);
                            break;
                        case HABITAT:
                            ihm.paintImage(j, i, "images/habitat.png", 1, 1);
                            break;
                        default:
                            ihm.paintBox(j, i, Color.GRAY); // Erreur
                            break;
                    }
                    //ihm.paintString(7, 15, Color.YELLOW, "I");
                }
            }

            // Affichage des incendies avec une taille croissante avec l'intensité
            for (Incendie i : simulation.getIncendies()) {
                if (i.getIntensite() > 0) {
                    double tailleImage;
                    if (i.getIntensite() < 1000) {
                        tailleImage = 0.5;
                    } else if (i.getIntensite() < 10000) {
                        tailleImage = 0.7;
                    } else {
                        tailleImage = 0.9;
                    }
                    ihm.paintImage((i.getPosition()).getColonne(), (i.getPosition()).getLigne(), "images/incendie.png", tailleImage, tailleImage);
                }
            }

            // Affichage des robots
            for (Robot r : simulation.getRobots()) {
                ihm.paintImage((r.getPosition()).getColonne(), (r.getPosition()).getLigne(), r.getImage(), 0.9, 0.9);
            }
        } catch (MapIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (SimulationException e) {
            System.out.println("[ERR] Echec de l'affichage de la carte sur l'IHM (parcours de cases hors-carte)");
        }
    }

}
