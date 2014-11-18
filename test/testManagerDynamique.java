import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class testManagerDynamique {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void chercheEau1() {
        try {
            Robot robot;
            ManagerDynamique manager;
            Simulateur simu;
            DonneesSimulation data;
            Case depart, eau;
            
            simu = new Simulateur(new Date());
            data = new DonneesSimulation(1000, 1000, 1000);
            
            // initalisation de la carte
            int distanceEau = 10;
            int x = 1;
            int y = 1;
            depart = data.getCarte().getCase(x, y);
            eau = new Case(x, y + distanceEau, NatureTerrain.EAU);
            data.addCase(eau);
            
            // initialisation du robot
            robot  = new RobotRoues(depart);
            data.addRobot(depart, robot);
            
            manager = new ManagerDynamique(simu, data);
            
            ///////////////////////////////////////////////////////////////////
            
            scenarioChercherEau(data, robot, distanceEau, manager);
        } catch (ConstructionException | SimulationException ex) {
            Logger.getLogger(testManagerDynamique.class.getName()).log(
                    Level.SEVERE, null, ex);
            fail(ex.toString());
        }
    }
    
    @Test
    public void chercheEauMultiple() {
        try {
            Robot robot;
            ManagerDynamique manager;
            Simulateur simu;
            DonneesSimulation data;
            Case depart, eau1, eau2, eau3, eau4;
            
            simu = new Simulateur(new Date());
            data = new DonneesSimulation(1000, 1000, 1000);
            
            // initalisation de la carte
            int distanceEau = 10;
            int x = 1;
            int y = 1;
            depart = data.getCarte().getCase(x, y);
            eau1 = new Case(x, y + distanceEau, NatureTerrain.EAU);
            eau2 = new Case(x + 1, y + distanceEau, NatureTerrain.EAU);
            eau3 = new Case(x + distanceEau, y+distanceEau, NatureTerrain.EAU);
            eau4 = new Case(x + distanceEau, y, NatureTerrain.EAU);
            data.addCase(eau1);
            data.addCase(eau2);
            data.addCase(eau3);
            data.addCase(eau4);
            
            // initialisation d'un robot vide
            robot  = new RobotRoues(depart);
            data.addRobot(depart, robot);
            
            manager = new ManagerDynamique(simu, data);
            
            ///////////////////////////////////////////////////////////////////
            
            scenarioChercherEau(data, robot, distanceEau, manager);
        } catch (ConstructionException | SimulationException ex) {
            Logger.getLogger(testManagerDynamique.class.getName()).log(
                    Level.SEVERE, null, ex);
            fail(ex.toString());
        }
    }
    
    @Test
    public void eteindreFeu() {
        try {
            Robot robot;
            ManagerDynamique manager;
            Simulateur simu;
            DonneesSimulation data;
            Case depart, eau, feu;
            int distanceEau, distanceFeu;
            
            simu = new Simulateur(new Date());
            data = new DonneesSimulation(1000, 1000, 1000);
            
            // initalisation de la carte
            distanceEau = 10;
            distanceFeu = 15;
            int x = 1;
            int y = 1;
            depart = data.getCarte().getCase(x, y);
            eau = new Case(x, y + distanceEau, NatureTerrain.EAU);
            data.addCase(eau);
            feu = new Case(x + distanceFeu, y, NatureTerrain.HABITAT);
            data.addCase(feu);
            data.addIncendie(feu, 1); // On initialise un feu simple à éteindre
            
            // initialisation du robot
            robot  = new RobotRoues(depart);
            data.addRobot(depart, robot);
            
            manager = new ManagerDynamique(simu, data);
            
            //////////////////////////////////////////////////////////////////
            
            scenarioChercherFeu(data, robot, distanceFeu, manager);

        } catch (ConstructionException | SimulationException ex) {
            Logger.getLogger(testManagerDynamique.class.getName()).log(
                    Level.SEVERE, null, ex);
            fail(ex.toString());
        }

    }
    
    //////////////////////////////////////////////////////////////////////////
    
    private void scenarioChercherEau(DonneesSimulation data, Robot robot,
            int distanceEau, Manager manager)
            throws SimulationException
    {
        while (!robot.estVide()) {
            robot.deverserEau(data, 10);
        }
        assertThat("Le robot doit initialement être initialement vide",
                true, is(robot.estVide()));

        // Le robot est situé à une distance de distanceEau -1 unitées du
        // bord d'eau
        for (int i = 0; i < distanceEau - 1; i++) {
            Case prev = robot.getPosition();
            manager.manage();
            assertThat("Le robot doit se deplacer", prev,
                    is(not(robot.getPosition())));
            System.out.println(robot.getPosition());
        }

        assertThat("Le robot doit être à proximitée de l'eau. "
                + "Position courante " + robot.getPosition() + ". ", true,
                is(robot.estRemplissable(data.getCarte())));

        manager.manage();
        assertThat("Le robot doit être désormais remplis", true,
                is(robot.estPlein()));
    }

    private void scenarioChercherFeu(DonneesSimulation data, Robot robot,
            int distanceFeu, Manager manager) throws SimulationException {
            // Le robot est situé à une distance de distanceFeu unitées du
        // bord d'eau
        for (int i = 0; i <= distanceFeu - 1; i++) {
            Case prev = robot.getPosition();
            manager.manage();
            assertThat("Le robot doit se deplacer", prev,
                    is(not(robot.getPosition())));
            System.out.println(robot.getPosition());
        }

        assertThat("Le robot doit être sur l'incendie. "
                + "Position courante " + robot.getPosition() + ". ", true,
                is(robot.peutEteindreFeu(data)));

        manager.manage();
        assertThat("Le feu doit désormais être éteind", 0,
                is(data.getIncendies().get(0).getIntensite()));
    }
}
