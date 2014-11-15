// Classe d'affichage des données de la simulation depuis le fichier
// Dernière modification : Thibaud BACKENSTRASS, 10 novembre
import ihm.*;
import java.awt.Color;
import java.util.LinkedList;
import java.io.FileNotFoundException;

public class AfficheSimulation {
	public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Syntaxe: java AfficheSimulation <nomDeFichier>");
            System.exit(1);
        }
        try {
            LecteurDonnees lecteur;
            DonneesSimulation simulation;
            Firemen firemen;
            String filename = args[0];
            
            lecteur    = new LecteurDonnees(filename);
            simulation = lecteur.creeDonnees();
            firemen    = new Firemen(simulation, filename, lecteur);
		} catch (FileNotFoundException e) {
			System.out.println("fichier " + args[0] + " inconnu ou illisible");
		} catch (ExceptionFormatDonnees e) {
			System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
	}
}

class Firemen implements Simulable {
    private DonneesSimulation simulation;
	private int nbLignes;
	private int nbColonnes;
    private IGSimulateur ihm;  // l'IHM associee a ce simulateur
    private long date = 0;
    private String fichierSimulation;
    private LecteurDonnees lecteur;
    
	public Firemen(DonneesSimulation data, String fichier, LecteurDonnees lecteur) {
		// cree l'IHM et l'associe a ce simulateur (this), qui en tant que
		// Simulable recevra les evenements suite aux actions de l'utilisateur
        nbLignes = data.getNbLignes();
        nbColonnes = data.getNbColonnes();
        simulation = data;
        fichierSimulation = fichier;
		ihm = new IGSimulateur(nbColonnes, nbLignes, this);
                lecteur = lecteur;
		dessine();    // mettre a jour l'affichage
	}
	
	@Override
	public void next() {
		date++;
		System.out.println("TODO: avancer la simulation \"d'un pas de temps\": " + date);
		dessine();    // mettre a jour l'affichage
	}

	@Override
	public void restart() {
        try {
            this.simulation = this.lecteur.creeDonnees();
            date = 0;
            dessine(); // Mettre à jour l'affichage
            System.out.println("[OK] Redémarrage de la simulation depuis son état initial.");
        } catch (Exception e) {
            System.out.println("Erreur lors de la remise à jour des données de simulation");
        }
	}

	private void dessine() {
        // Afficher les donnees 		
		try {
            // Affichage des données sur la nature du terrain
            for(int i=0; i<nbLignes; i++) {
                for(int j=0; j<nbColonnes; j++) {
                    Case c = (simulation.getCarte()).getCase(i, j);
                    // ATTENTION !
                    // i désigne la ligne, j la colonne de la case
                    // Il faut inverser les données pour les méthodes de ihm, qui veulent d'abord la colonne, puis la ligne
                    // Sinon problème de transposée...
                    switch(c.getTerrain()) {
                        case EAU:             ihm.paintImage(j, i, "images/eau.png", 1, 1);
                                              break;
                        case FORET:           ihm.paintImage(j, i, "images/foret.png", 1, 1);
                                              break;
                        case ROCHE:           ihm.paintImage(j, i, "images/roche.png", 1, 1);
                                              break;
                        case TERRAIN_LIBRE:   ihm.paintImage(j, i, "images/terrain_libre.png", 1, 1);
                                              break;
                        case HABITAT:         ihm.paintImage(j, i, "images/habitat.png", 1, 1);
                                              break;
                        default:    ihm.paintBox(j, i, Color.GRAY); // Erreur
                                    break;
                    }
        			//ihm.paintString(7, 15, Color.YELLOW, "I");
                }
            }

            // Affichage des incendies avec une taille croissante avec l'intensité
            for(Incendie i : simulation.getIncendies()) {
                if(i.getIntensite()>0) {
                    double tailleImage;
                    if(i.getIntensite()<1000) {
                        tailleImage = 0.5;
                    }
                    else if(i.getIntensite()<10000) {
                        tailleImage = 0.7;
                    }
                    else {
                        tailleImage = 0.9;
                    }
                    ihm.paintImage((i.getPosition()).getColonne(), (i.getPosition()).getLigne(), "images/incendie.png", tailleImage, tailleImage);
                }
            }

            // Affichage des robots
            for(Robot r : simulation.getRobots()) {
                ihm.paintImage((r.getPosition()).getColonne(), (r.getPosition()).getLigne(), r.getImage(), 0.9, 0.9);
            }
		} catch (MapIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (SimulationException e) {
            System.out.println("[ERR] Echec de l'affichage de la carte sur l'IHM (parcours de cases hors-carte)");
        }
	}

}

