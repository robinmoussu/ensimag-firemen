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
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        try {
            carte = new Carte(1000, 1000, 1000);
        } catch (ConstructionException ex) {
            Logger.getLogger(testAstar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void samePosition() {
        Astar astar;
        Case objectif;
        try {
            objectif = this.carte.getCase(10, 10);
            astar = new Astar(this.carte, objectif);
            
            assertEquals("do not move", objectif,
                    astar.next(objectif, parcourtToutTerrain));
        } catch (SimulationException ex) {
            Logger.getLogger(testAstar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void voisin() {
        Astar astar;
        Case objectif;
        try {
            objectif = this.carte.getCase(10, 10);
            astar = new Astar(this.carte, objectif);

            assertEquals("must go right", objectif,
                    astar.next(this.carte.getVoisin(objectif, Direction.OUEST),
                            parcourtToutTerrain));
            
            assertEquals("must go left", objectif,
                    astar.next(this.carte.getVoisin(objectif, Direction.EST),
                            parcourtToutTerrain));
            assertEquals("must go down", objectif,
                    astar.next(this.carte.getVoisin(objectif, Direction.NORD),
                            parcourtToutTerrain));
            assertEquals("must go up", objectif,
                    astar.next(this.carte.getVoisin(objectif, Direction.SUD),
                            parcourtToutTerrain));
        } catch (SimulationException ex) {
            Logger.getLogger(testAstar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void distantSansObstacle() {
        Astar astar;
        Case objectif;
        try {
            objectif = this.carte.getCase(10, 10);
            astar = new Astar(this.carte, objectif);

            // 2 times in same direction
            assertEquals("start 2 time top", objectif,
                    astar.next(astar.next(
                            this.carte.getVoisin(
                            this.carte.getVoisin(objectif,
                                    Direction.NORD),
                                    Direction.NORD),
                            parcourtToutTerrain),
                            parcourtToutTerrain));

            assertEquals("start 2 time bottom", objectif,
                    astar.next(astar.next(
                            this.carte.getVoisin(
                            this.carte.getVoisin(objectif,
                                    Direction.SUD),
                                    Direction.SUD),
                            parcourtToutTerrain),
                            parcourtToutTerrain));

            assertEquals("start 2 time right", objectif,
                    astar.next(astar.next(
                            this.carte.getVoisin(
                            this.carte.getVoisin(objectif,
                                    Direction.EST),
                                    Direction.EST),
                            parcourtToutTerrain),
                            parcourtToutTerrain));

            assertEquals("start 2 time left", objectif,
                    astar.next(astar.next(
                            this.carte.getVoisin(
                            this.carte.getVoisin(objectif,
                                    Direction.OUEST),
                                    Direction.OUEST),
                            parcourtToutTerrain),
                            parcourtToutTerrain));

            // corner
            assertEquals("start corner top left", objectif,
                    astar.next(astar.next(
                            this.carte.getVoisin(
                            this.carte.getVoisin(objectif,
                                    Direction.NORD),
                                    Direction.OUEST),
                            parcourtToutTerrain),
                            parcourtToutTerrain));

            assertEquals("start corner top right", objectif,
                    astar.next(astar.next(
                            this.carte.getVoisin(
                            this.carte.getVoisin(objectif,
                                    Direction.EST),
                                    Direction.NORD),
                            parcourtToutTerrain),
                            parcourtToutTerrain));

            assertEquals("start corner bottom left", objectif,
                    astar.next(astar.next(
                            this.carte.getVoisin(
                            this.carte.getVoisin(objectif,
                                    Direction.OUEST),
                                    Direction.SUD),
                            parcourtToutTerrain),
                            parcourtToutTerrain));

            assertEquals("start corner bottom right", objectif,
                    astar.next(astar.next(
                            this.carte.getVoisin(
                            this.carte.getVoisin(objectif,
                                    Direction.SUD),
                                    Direction.EST),
                            parcourtToutTerrain),
                            parcourtToutTerrain));

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
                            parcourtToutTerrain),
                            parcourtToutTerrain),
                            parcourtToutTerrain),
                            parcourtToutTerrain),
                            parcourtToutTerrain),
                            parcourtToutTerrain));

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
                            parcourtToutTerrain),
                            parcourtToutTerrain),
                            parcourtToutTerrain),
                            parcourtToutTerrain),
                            parcourtToutTerrain),
                            parcourtToutTerrain));
        } catch (SimulationException ex) {
            Logger.getLogger(testAstar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void avecObstacle() {
        Astar astar;
        Case obstacle;
        Case objectif;
        Case depart;
        try {
            objectif = this.carte.getCase(10, 10);
            astar = new Astar(this.carte, objectif);
            obstacle = new Case(10, 9, NatureTerrain.ROCHE);
            carte.setCase(obstacle);

            objectif = this.carte.getCase(10, 10);
            depart = this.carte.getCase(10, 8);

            // Pour atteindre la case, il faut faire 4 mouvements maximum
            Case it = depart;
            for (int i = 0; (i < 4) && (it != objectif); i++) {
                it = astar.next(it, parcourtTerrainLibre);
                assertThat("Une case invalide à été franchit", it, is(not(obstacle)));
            }
            assertThat("objectif non atteind", it, is(objectif));

        } catch (SimulationException | ConstructionException ex) {
            Logger.getLogger(testAstar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
