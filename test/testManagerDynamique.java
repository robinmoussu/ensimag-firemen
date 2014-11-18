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
            
            // initialisation d'un robot vide
            robot  = new RobotRoues(depart);
            data.addRobot(depart, robot);
            
            while(!robot.estVide()) {
                robot.deverserEau(data, 10);
            }
            
            manager = new ManagerDynamique(simu, data);
            
            ///////////////////////////////////////////////////////////////////
            
            
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
                    + "Position courante " + robot.getPosition() + ". "
                    + "Position de l'eau " + eau, true,
                    is(data.getCarte().estBordEau(robot.getPosition())));
            
            manager.manage();
            assertThat("Le robot doit être désormais remplis", true,
                    is(robot.estPlein()));
        } catch (ConstructionException | SimulationException ex) {
            Logger.getLogger(testManagerDynamique.class.getName()).log(
                    Level.SEVERE, null, ex);
            fail(ex.toString());
        }

    }
}
