/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author robin
 */
public class testAstar {

    static class ParcourtTerrainLibre implements ValideCase {

        @Override
        public boolean estValide(Case case_) {
            return case_.getTerrain().equals(NatureTerrain.TERRAIN_LIBRE);
        }
    }

    static class ParcourtToutTerrain implements ValideCase {

        @Override
        public boolean estValide(Case case_) {
            return true;
        }
    }

    private Carte carte;
    static private ValideCase parcourtTerrainLibre = new ParcourtTerrainLibre();
    static private ValideCase parcourtToutTerrain = new ParcourtToutTerrain();

    public testAstar() {
        try {
            carte = new Carte(1000, 1000, 1000);
        } catch (ConstructionException ex) {
            Logger.getLogger(testAstar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
    public void samePosition() {
        Astar astar = new Astar(this.carte);
        Case objectif;
        try {
            objectif = this.carte.getCase(10, 10);
            assertEquals("do not move", objectif,
                    astar.next(objectif, objectif, parcourtToutTerrain));
        } catch (SimulationException ex) {
            Logger.getLogger(testAstar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void voisin() {
        Astar astar = new Astar(this.carte);
        Case objectif;
        try {
            objectif = this.carte.getCase(10, 10);

            assertEquals("must go right", objectif,
                    astar.next(this.carte.getVoisin(objectif, Direction.OUEST),
                            objectif, parcourtToutTerrain));
            assertEquals("must go left", objectif,
                    astar.next(this.carte.getVoisin(objectif, Direction.EST),
                            objectif, parcourtToutTerrain));
            assertEquals("must go down", objectif,
                    astar.next(this.carte.getVoisin(objectif, Direction.NORD),
                            objectif, parcourtToutTerrain));
            assertEquals("must go up", objectif,
                    astar.next(this.carte.getVoisin(objectif, Direction.SUD),
                            objectif, parcourtToutTerrain));
        } catch (SimulationException ex) {
            Logger.getLogger(testAstar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void distantSansObstacle() {
        Astar astar = new Astar(this.carte);
        Case depart;
        Case objectif;
        try {
            objectif = this.carte.getCase(10, 10);

            // 2 times in same direction
            assertEquals("start 2 time top", objectif,
                    astar.next(astar.next(
                            this.carte.getVoisin(
                            this.carte.getVoisin(objectif,
                                    Direction.NORD),
                                    Direction.NORD),
                            objectif, parcourtToutTerrain),
                            objectif, parcourtToutTerrain));

            assertEquals("start 2 time bottom", objectif,
                    astar.next(astar.next(
                            this.carte.getVoisin(
                            this.carte.getVoisin(objectif,
                                    Direction.SUD),
                                    Direction.SUD),
                            objectif, parcourtToutTerrain),
                            objectif, parcourtToutTerrain));

            assertEquals("start 2 time right", objectif,
                    astar.next(astar.next(
                            this.carte.getVoisin(
                            this.carte.getVoisin(objectif,
                                    Direction.EST),
                                    Direction.EST),
                            objectif, parcourtToutTerrain),
                            objectif, parcourtToutTerrain));

            assertEquals("start 2 time left", objectif,
                    astar.next(astar.next(
                            this.carte.getVoisin(
                            this.carte.getVoisin(objectif,
                                    Direction.OUEST),
                                    Direction.OUEST),
                            objectif, parcourtToutTerrain),
                            objectif, parcourtToutTerrain));

            // corner
            assertEquals("start corner top left", objectif,
                    astar.next(astar.next(
                            this.carte.getVoisin(
                            this.carte.getVoisin(objectif,
                                    Direction.NORD),
                                    Direction.OUEST),
                            objectif, parcourtToutTerrain),
                            objectif, parcourtToutTerrain));

            assertEquals("start corner top right", objectif,
                    astar.next(astar.next(
                            this.carte.getVoisin(
                            this.carte.getVoisin(objectif,
                                    Direction.EST),
                                    Direction.NORD),
                            objectif, parcourtToutTerrain),
                            objectif, parcourtToutTerrain));

            assertEquals("start corner bottom left", objectif,
                    astar.next(astar.next(
                            this.carte.getVoisin(
                            this.carte.getVoisin(objectif,
                                    Direction.OUEST),
                                    Direction.SUD),
                            objectif, parcourtToutTerrain),
                            objectif, parcourtToutTerrain));

            assertEquals("start corner bottom right", objectif,
                    astar.next(astar.next(
                            this.carte.getVoisin(
                            this.carte.getVoisin(objectif,
                                    Direction.SUD),
                                    Direction.EST),
                            objectif, parcourtToutTerrain),
                            objectif, parcourtToutTerrain));

            // far away
            assertEquals("far away", objectif,
                    astar.next(astar.next(
                    astar.next(astar.next(
                    astar.next(astar.next(
                            this.carte.getVoisin(
                            this.carte.getVoisin(
                            this.carte.getVoisin(
                            this.carte.getVoisin(
                            this.carte.getVoisin(
                            this.carte.getVoisin(objectif,
                                    Direction.NORD),
                                    Direction.NORD),
                                    Direction.NORD),
                                    Direction.NORD),
                                    Direction.EST),
                                    Direction.EST),
                            objectif, parcourtToutTerrain),
                            objectif, parcourtToutTerrain),
                            objectif, parcourtToutTerrain),
                            objectif, parcourtToutTerrain),
                            objectif, parcourtToutTerrain),
                            objectif, parcourtToutTerrain));

            assertEquals("far away", objectif,
                    astar.next(astar.next(
                    astar.next(astar.next(
                    astar.next(astar.next(
                            this.carte.getVoisin(
                            this.carte.getVoisin(
                            this.carte.getVoisin(
                            this.carte.getVoisin(
                            this.carte.getVoisin(
                            this.carte.getVoisin(objectif,
                                    Direction.NORD),
                                    Direction.OUEST),
                                    Direction.NORD),
                                    Direction.OUEST),
                                    Direction.NORD),
                                    Direction.OUEST),
                            objectif, parcourtToutTerrain),
                            objectif, parcourtToutTerrain),
                            objectif, parcourtToutTerrain),
                            objectif, parcourtToutTerrain),
                            objectif, parcourtToutTerrain),
                            objectif, parcourtToutTerrain));
        } catch (SimulationException ex) {
            Logger.getLogger(testAstar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void avecObstacle() {
        Astar astar = new Astar(this.carte);
        Case obstacle;
        Case depart;

        Case objectif;
        try {
            obstacle = new Case(10, 9, NatureTerrain.ROCHE);
            carte.setCase(obstacle);

            objectif = this.carte.getCase(10, 10);
            depart = this.carte.getCase(10, 8);

            // Pour atteindre la case, il faut faire 4 mouvements maximum
            Case it = depart;
            for (int i = 0; (i < 4) && (it != objectif); i++) {
                it = astar.next(it, objectif, parcourtTerrainLibre);
                assertThat("Une case invalide à été franchit", it, is(not(obstacle)));
            }
            assertThat("objectif non atteind", it, is(objectif));

        } catch (SimulationException | ConstructionException ex) {
            Logger.getLogger(testAstar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
