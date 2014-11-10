import ihm.*;
import java.awt.Color;
import java.io.FileNotFoundException;

public class AfficheSimulation {
	public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Syntaxe: java AfficheSimulation <nomDeFichier>");
            System.exit(1);
        }
        try {
            DonneesSimulation simulation = LecteurDonnees.creeDonnees(args[0]);
            Firemen firement = new Firemen(simulation);
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
    
	public Firemen(DonneesSimulation data) {
		// cree l'IHM et l'associe a ce simulateur (this), qui en tant que
		// Simulable recevra les evenements suite aux actions de l'utilisateur
        nbLignes = data.getNbLignes();
        nbColonnes = data.getNbColonnes();
        simulation = data;
		ihm = new IGSimulateur(nbColonnes, nbLignes, this);
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
		System.out.println("TODO: remettre le simulateur dans son état initial");
		date = 0;
		dessine();    // mettre a jour l'affichage
	}

	private void dessine() {
        // Afficher les donnees 		
		try {
            for(int i=0; i<nbLignes; i++) {
                for(int j=0; j<nbColonnes; j++) {
                    Case c = (simulation.getCarte()).getCase(i, j);
                    switch(c.getTerrain()) {
                        case EAU:             ihm.paintImage(i, j, "images/eau.png", 1, 1);
                                              break;
                        case FORET:           ihm.paintImage(i, j, "images/foret.png", 1, 1);
                                              break;
                        case ROCHE:           ihm.paintImage(i, j, "images/roche.png", 1, 1);
                                              break;
                        case TERRAIN_LIBRE:   ihm.paintImage(i, j, "images/terrain_libre.png", 1, 1);
                                              break;
                        case HABITAT:         ihm.paintImage(i, j, "images/habitat.png", 1, 1);
                                              break;
                        default:    ihm.paintBox(i, j, Color.GRAY); // Erreur
                                    break;
                    }
			        //ihm.paintImage(4, 15, "images/feu.png", 0.8, 0.8);
        			//ihm.paintString(7, 15, Color.YELLOW, "I");
                }
            }
		} catch (MapIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

}

