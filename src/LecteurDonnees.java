import java.io.*;
import java.util.*;

/**
 * Lecteur de cartes au format spécifié dans le sujet.
 * @author Enseignants Ensimag
 * @date -
 */
public class LecteurDonnees {
    private DonneesSimulation simulation;
    private String fichierDonnees;
    private Scanner scanner;
    

    /**
     * Constructeur du lecteur de données à partir du fichier passé en paramètre.
     * @param fichierDonnees Nom du fichier à lire
     * @throws FileNotFoundException si le fichier n'a pas été trouvé
     */
    public LecteurDonnees(String fichierDonnees) throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
        this.fichierDonnees = fichierDonnees;
    }


    /**
     * Lit le fichier de données et crée la structure de données nécessaire au stockage.
     * Méthode de classe, utilisation: LecteurDonnees.creeDonnees().
     * @return Objet DonneesSimulation contenant toutes les données de la simulation
     * @throws ExceptionFormatDonnees en cas d'erreur de syntaxe dans le fichier de données
     */
    public DonneesSimulation creeDonnees() throws ExceptionFormatDonnees {
        System.out.println("[OK] Lecture du fichier " + this.fichierDonnees);
        lireCarte();
        lireIncendies();
        lireRobots();
        scanner.close();
        return simulation;
    }

    
    /**
     * Lit et stocker les donnees de la carte.
     * @throws ExceptionFormatDonnees en cas d'erreur de syntaxe dans le fichier de données
     */
    private void lireCarte() throws ExceptionFormatDonnees {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();	// en m
            this.simulation = new DonneesSimulation(nbLignes, nbColonnes, tailleCases);
            System.out.println("[OK] Nouvelle carte de " + nbLignes + "x" + nbColonnes + " cases");

            // Pour chaque case
            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    lireCase(lig, col);
                }
            }

        } catch (NoSuchElementException e) {
            throw new ExceptionFormatDonnees("Format invalide. Attendu: nbLignes nbColonnes tailleCases");
        } catch (ConstructionException e) {
            throw new ExceptionFormatDonnees("Valeur(s) invalide(s) pour la carte");
        }
    }


    /**
     * Lit et stocker les donnees d'une case.
     * @param lig Numéro de la ligne de la case, à partir de zéro
     * @param col Numéro de la colonne de la case, à partir de zéro
     * @throws ExceptionFormatDonnees en cas d'erreur de syntaxe dans le fichier de données
     */
    private void lireCase(int lig, int col) throws ExceptionFormatDonnees {
        ignorerCommentaires();
        String chaineNature = new String();

        try {
            chaineNature = scanner.next();
            // Transformer string en Enum NatureTerrain 
            NatureTerrain nature = NatureTerrain.valueOf(chaineNature);
            verifieLigneTerminee();
            this.simulation.addCase(lig, col, nature);

        } catch (NoSuchElementException e) {
            throw new ExceptionFormatDonnees("Format de case invalide. Attendu: nature altitude [valeur_specifique]");
        } catch (ConstructionException e) {
            throw new ExceptionFormatDonnees("Valeur(s) invalide(s) pour les cases");
        }
    }


    /**
     * Lit et stocker les donnees des incendies.
     * @throws ExceptionFormatDonnees en cas d'erreur de syntaxe dans le fichier de données
     */
    private void lireIncendies() throws ExceptionFormatDonnees {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            for (int i = 0; i < nbIncendies; i++) {
                lireIncendie(i);
            }
            System.out.println("[OK] Incendies initialisés : " + nbIncendies);

        } catch (NoSuchElementException e) {
            throw new ExceptionFormatDonnees("Format invalide. Attendu: nbIncendies");
        } catch (ConstructionException e) {
            System.out.println("[ERR] Erreur d'initialisation des incendies !");
        }
    }


    /**
     * Lit et stocker les donnees du i-eme incendie.
     * @param i Numéro de l'incendie à lire, à partir de zéro
     * @throws ExceptionFormatDonnees en cas d'erreur de syntaxe dans le fichier de données
     * @throws ConstructionException si les données du fichier sont invalides
     */
    private void lireIncendie(int i) throws ExceptionFormatDonnees, ConstructionException {
        ignorerCommentaires();
        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            Case c = (this.simulation.getCarte()).getCase(lig, col);
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new ExceptionFormatDonnees("Incendie " + i
                        + "Nécessite un nombre de litres d'eau positif !");
            }
            verifieLigneTerminee();

            this.simulation.addIncendie(c, intensite);

        } catch (NoSuchElementException e) {
            throw new ExceptionFormatDonnees("Format d'incendie invalide. "
                    + "Attendu: ligne colonne intensité");
        } catch (SimulationException e) {
            throw new ExceptionFormatDonnees("Valeur(s) invalide(s) pour un robot");
        }
    }

    /**
     * Lit et stocker les donnees des robots.
     * @throws ExceptionFormatDonnees en cas d'erreur de syntaxe dans le fichier de données
     */
    private void lireRobots() throws ExceptionFormatDonnees {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            for (int i = 0; i < nbRobots; i++) {
                lireRobot(i);
            }
            System.out.println("[OK] Robots initialisés : " + nbRobots);

        } catch (NoSuchElementException e) {
            throw new ExceptionFormatDonnees("Format invalide. Attendu: nbRobots");
        } catch (ConstructionException e) {
            System.out.println("[ERR] Erreur d'initialisation des robots");
        }
    }

    /**
     * Lit et stocker les donnees du i-eme robot.
     * @param i Numéro du robot à lire, à partir de zéro
     * @throws ExceptionFormatDonnees en cas d'erreur de syntaxe dans le fichier de données
     * @throws ConstructionException si les données du fichier sont invalides
     */
    private void lireRobot(int i) throws ExceptionFormatDonnees, ConstructionException {
        ignorerCommentaires();
        try {
            int lig, col;
            Case pos;
            String typeRobot;
            String vitesse_str;
            int vitesse;
            Robot robot;
            
            
            lig = scanner.nextInt();
            col = scanner.nextInt();
            typeRobot = scanner.next();
            vitesse_str = scanner.findInLine("(\\d+)");
            
            pos = this.simulation.getCarte().getCase(lig, col);
            
            // Si la vitesse est négative, elle est ignorée par le constructeur
            if (vitesse_str == null) {
                vitesse = -1;
            } else {
                vitesse = Integer.parseInt(vitesse_str);
            }
            
            switch (typeRobot) {
            case "DRONE":
                robot = new RobotDrone(pos, vitesse);
                break;
            case "ROUES":
                robot = new RobotRoues(pos, vitesse);
                break;
            case "CHENILLES":
                robot = new RobotChenilles(pos, vitesse);
                break;
            case "PATTES":
                robot = new RobotPattes(pos, vitesse);
                break;
            default:
                throw new ConstructionException("Impossible de créer un robot du type spécifié !");
            }
            
            this.simulation.addRobot(pos, robot);

            
            verifieLigneTerminee();

        } catch (NoSuchElementException e) {
            throw new ExceptionFormatDonnees("format de robot invalide. Attendu: ligne colonne type [valeur_specifique]");
        } catch (SimulationException e) {
            throw new ExceptionFormatDonnees("Valeur(s) invalide(s) pour un robot");
        }
    }

    /**
     * Ignore toute (fin de) ligne commencant par '#'.
     */
    private void ignorerCommentaires() {
        while (scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien à lire sur cette ligne (int ou float).
     * @throws ExceptionFormatDonnees en cas d'erreur de syntaxe dans le fichier de données
     */
    private void verifieLigneTerminee() throws ExceptionFormatDonnees {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new ExceptionFormatDonnees("Format du fichier invalide, trop de données sur une même ligne !");
        }
    }

}
