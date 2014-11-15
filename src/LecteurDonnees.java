// Lecteur de cartes au format spécifié dans le sujet
// Dernière modification : Thibaud BACKENSTRASS, 9 novembre
import java.io.*;
import java.util.*;

public class LecteurDonnees {

    // Attributs: la simulation à créer

    private DonneesSimulation simulation;
    private String fichierDonnees;
    
    /**
     * Lit un fichier de données et crée la structure de données nécessaire au
     * stockage. Méthode de classe, utilisation:
     * LecteurDonnees.creeDonnees(fichierDonnees).
     *
     * @param fichierDonnees Nom du fichier à lire
     * @return Objet DonneesSimulation contenant toutes les données de la
     * simulation
     */
    public DonneesSimulation creeDonnees()
            throws ExceptionFormatDonnees {
        System.out.println("[OK] Lecture du fichier " + this.fichierDonnees);
        lireCarte();
        lireIncendies();
        lireRobots();
        scanner.close();
        return simulation;
    }

    // Tout le reste de la classe est privé !
    private static Scanner scanner;

    /**
     * @param fichierDonnees nom du fichier a lire
     */
    public LecteurDonnees(String fichierDonnees)
            throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
        fichierDonnees = fichierDonnees;
    }

    /**
     * Lit et affiche les donnees de la carte.
     *
     * @throws ExceptionFormatDonnees
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
            throw new ExceptionFormatDonnees("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        } catch (ConstructionException e) {
            throw new ExceptionFormatDonnees("Valeur(s) invalide(s) pour la carte");
        }
    }

    /**
     * Lit et affiche les donnees d'une case.
     */
    private void lireCase(int lig, int col) throws ExceptionFormatDonnees {
        ignorerCommentaires();
        String chaineNature = new String();
//		NatureTerrain nature;

        try {
            chaineNature = scanner.next();
            // Transformer string en Enum NatureTerrain 
            NatureTerrain nature = NatureTerrain.valueOf(chaineNature);
            verifieLigneTerminee();
            this.simulation.addCase(lig, col, nature);
            //System.out.println(lig + " " + col + " " + chaineNature);

        } catch (NoSuchElementException e) {
            throw new ExceptionFormatDonnees("Format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        } catch (ConstructionException e) {
            throw new ExceptionFormatDonnees("Valeur(s) invalide(s) pour les cases");
        }
    }

    /**
     * Lit et affiche les donnees des incendies.
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
            throw new ExceptionFormatDonnees("Format invalide. "
                    + "Attendu: nbIncendies");
        } catch (ConstructionException e) {
            System.out.println("[ERR] Erreur d'initialisation des incendies !");
        }
    }

    /**
     * Lit et affiche les donnees du i-eme incendie.
     *
     * @param i
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
     * Lit et affiche les donnees des robots.
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
            throw new ExceptionFormatDonnees("Format invalide. "
                    + "Attendu: nbRobots");
        } catch (ConstructionException e) {
            System.out.println("[ERR] Erreur d'initialisation des robots");
        }
    }

    /**
     * Lit et affiche les donnees du i-eme robot.
     *
     * @param i
     */
    private void lireRobot(int i) throws ExceptionFormatDonnees, ConstructionException {
        ignorerCommentaires();
        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            Case c = (this.simulation.getCarte()).getCase(lig, col);
            String type = scanner.next();
            // Lecture éventuelle d'une vitesse du robot (entier)
            String s = scanner.findInLine("(\\d+)");	// 1 or more digit(s) ?

            if (s == null) {
                this.simulation.addRobot(c, type);
            } else {
                int vitesse = Integer.parseInt(s);
                this.simulation.addRobot(c, type, vitesse);
            }
            verifieLigneTerminee();

        } catch (NoSuchElementException e) {
            throw new ExceptionFormatDonnees("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        } catch (SimulationException e) {
            throw new ExceptionFormatDonnees("Valeur(s) invalide(s) pour un robot");
        }
    }

    /**
     * Ignore toute (fin de) ligne commencant par '#'
     */
    private void ignorerCommentaires() {
        while (scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
     *
     * @throws ExceptionFormatDonnees
     */
    private void verifieLigneTerminee() throws ExceptionFormatDonnees {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new ExceptionFormatDonnees("Format du fichier invalide, trop de données sur une même ligne !");
        }
    }

}
