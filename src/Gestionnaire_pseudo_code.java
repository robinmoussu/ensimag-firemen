public class GestionnaireEvenement {
    public GestionnaireEvenement(Carte c);      // La carte ne change pas et est donnée à la création
    public Ordre getOrdre(Robot r);             // Demande le nouvel ordre pour le Robot r
    public void newEvents(Event e);             // Signale qu'un nouvel évènement vient de se passer
}


// ton code
main {
    // initialisation
    Carte carte = new Carte();
    GestionnaireEvenement gestionnaire = new GestionnaireEvenement(carte);
    Simulation simu = new Simulation();

    // création des robots
    Robot r1 = new RobotRoues();
    Robot r2 = new RobotChenilles();
    Robot r3 = new RobotDrone();
    Robot r4 = new RobotPattes();
    Carte.addRobot(r1);
    Carte.addRobot(r2);
    Carte.addRobot(r3);
    Carte.addRobot(r4);

    // création des incendis (les seuls évènement au début)
    Incendie i1 = new Incendie();
    Incendie i2 = new Incendie();
    Incendie i3 = new Incendie();
    Incendie i4 = new Incendie();
    Carte.addIncendie(i1);
    Carte.addIncendie(i2);
    Carte.addIncendie(i3);
    Carte.addIncendie(i4);

    // on signale les évènements du début de la map
    LinkedList<Events> newEvents = new LinkedList<Events>();
    newEvents.pushBack(i1);
    newEvents.pushBack(i2);
    newEvents.pushBack(i3);
    newEvents.pushBack(i4);

    // Au début tout les robots sont en attente d'ordre
    LinkedList<Robot> robotEnAttente = new LinkedList<Robot>();
    robotEnAttente.pushBack(r1);
    robotEnAttente.pushBack(r2);
    robotEnAttente.pushBack(r3);
    robotEnAttente.pushBack(r4);

    // On prépare la liste des ordres en cour (vide au début de la simulation)
    LinkedList<Ordre> ordreEnCour = new LinkedList<Ordre>();

    // début de la simulation
    while(1) {

        // on signale tout les nouveaux évènement qui sont survenu depuis le temps t-1 au gestionnaire
        for(Event e: newEvents) {
            gestionnaire.newEvents(e);
        }

        // on demande les ordres au gestionnaire pour chacun des robots en attente d'ordre
        for(Robot r: robotEnAttente) {
            ordreEnCour.pushBack(gestionnaire.getOrdre(r));
        }

        // on calcule un pas de simulation(certain ordre peuvent se terminer)
        newEvents = simu.compute(ordreEnCour);
    }
}
