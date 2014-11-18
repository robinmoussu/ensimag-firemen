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
    static protected ValideCase parcourtTerrainLibre = new ParcourtTerrainLibre();
    static protected ValideCase parcourtToutTerrain = new ParcourtToutTerrain();

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
            Logger.getLogger(testAstar.class.getName()).log(
                    Level.SEVERE, null, ex);
            fail();
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void samePosition() {
        Astar astar;
        Case depart, objectif;
        try {
            objectif = this.carte.getCase(10, 10);
            depart   = objectif;
            
            astar = new Astar(this.carte, depart, objectif, parcourtToutTerrain);
            
            assertEquals("do not move", objectif, astar.next(objectif));
            assertThat("Position Finale atteinte", true, is(astar.finished()));
        } catch (SimulationException ex) {
            Logger.getLogger(testAstar.class.getName()).log(
                    Level.SEVERE, null, ex);
            fail();
        }
    }

    @Test
    public void voisin() {
        Astar astar;
        Case depart, objectif;
        try {
            objectif = this.carte.getCase(10, 10);
            
            depart = this.carte.getVoisin(objectif, Direction.OUEST);
            astar = new Astar(this.carte, depart, objectif, parcourtToutTerrain);
            assertThat("Position Finale not atteinte", false,
                    is(astar.finished()));
            assertEquals("must go right", objectif, astar.next(depart));
            assertTrue("Position Finale atteinte", astar.finished());
            
            depart = this.carte.getVoisin(objectif, Direction.EST);
            astar = new Astar(this.carte, depart, objectif, parcourtToutTerrain);
            assertEquals("must go left", objectif, astar.next(depart));
            
            depart = this.carte.getVoisin(objectif, Direction.NORD);
            astar = new Astar(this.carte, depart, objectif, parcourtToutTerrain);
            assertEquals("must go down", objectif, astar.next(depart));
            
            depart = this.carte.getVoisin(objectif, Direction.SUD);
            astar = new Astar(this.carte, depart, objectif, parcourtToutTerrain);
            assertEquals("must go up", objectif, astar.next(depart));
        } catch (SimulationException ex) {
            Logger.getLogger(testAstar.class.getName()).log(
                    Level.SEVERE, null, ex);
            fail();
        }
    }

    @Test
    public void distantSansObstacle() {
        Astar astar;
        Case depart, objectif, it;
        int i;
        try {
            objectif = this.carte.getCase(10, 10);
            
            depart = this.carte.getVoisin(this.carte.getVoisin(objectif,
                                Direction.NORD),
                                Direction.NORD);
            astar = new Astar(this.carte, depart, objectif, parcourtToutTerrain);
            // 2 times in same direction
            assertEquals("start 2 time top", objectif,
                    astar.next(astar.next(depart)));

            depart = this.carte.getVoisin(this.carte.getVoisin(objectif,
                                Direction.SUD),
                                Direction.SUD);
            astar = new Astar(this.carte, depart, objectif, parcourtToutTerrain);
            assertEquals("start 2 time bottom", objectif,
                    astar.next(astar.next(depart)));

            depart = this.carte.getVoisin(this.carte.getVoisin(objectif,
                                Direction.OUEST),
                                Direction.OUEST);
            astar = new Astar(this.carte, depart, objectif, parcourtToutTerrain);
            assertEquals("start 2 time right", objectif,
                    astar.next(astar.next(depart)));

            depart = this.carte.getVoisin(this.carte.getVoisin(objectif,
                                Direction.EST),
                                Direction.EST);
            astar = new Astar(this.carte, depart, objectif, parcourtToutTerrain);
            assertEquals("start 2 time left", objectif,
                    astar.next(astar.next(depart)));

            // corner

            depart = this.carte.getVoisin(this.carte.getVoisin(objectif,
                                Direction.NORD),
                                Direction.OUEST);
            astar = new Astar(this.carte, depart, objectif, parcourtToutTerrain);
            assertEquals("start corner top left", objectif,
                    astar.next(astar.next(depart)));

            depart = this.carte.getVoisin(this.carte.getVoisin(objectif,
                                Direction.NORD),
                                Direction.EST);
            astar = new Astar(this.carte, depart, objectif, parcourtToutTerrain);
            assertEquals("start corner top right", objectif,
                    astar.next(astar.next(depart)));

            depart = this.carte.getVoisin(this.carte.getVoisin(objectif,
                                Direction.SUD),
                                Direction.OUEST);
            astar = new Astar(this.carte, depart, objectif, parcourtToutTerrain);
            assertEquals("start corner bottom left", objectif,
                    astar.next(astar.next(depart)));

            depart = this.carte.getVoisin(this.carte.getVoisin(objectif,
                                Direction.SUD),
                                Direction.EST);
            astar = new Astar(this.carte, depart, objectif, parcourtToutTerrain);
            assertEquals("start corner bottom right", objectif,
                    astar.next(astar.next(depart)));

            // far away
            depart = this.carte.getVoisin(this.carte.getVoisin(
                     this.carte.getVoisin(this.carte.getVoisin(
                     this.carte.getVoisin(this.carte.getVoisin(objectif,
                                Direction.NORD),
                                Direction.NORD),
                                Direction.NORD),
                                Direction.NORD),
                                Direction.NORD),
                                Direction.EST);
            astar = new Astar(this.carte, depart, objectif, parcourtToutTerrain);
            assertThat("Position Finale not atteinte", false,
                    is(astar.finished()));
            // Pour atteindre la case, il faut faire 6 mouvements maximum
            it = depart;
            for (i = 0; (i < 6) && (it != objectif); i++) {
                it = astar.next(it);
            }
            assertThat("Il faut faire 6 mouvements pour atteindre l'arrivée",
                    i, is(6));
            assertThat("Position Finale atteinte", true, is(astar.finished()));

            depart = this.carte.getVoisin(this.carte.getVoisin(
                     this.carte.getVoisin(this.carte.getVoisin(
                     this.carte.getVoisin(this.carte.getVoisin(objectif,
                                Direction.SUD),
                                Direction.EST),
                                Direction.SUD),
                                Direction.EST),
                                Direction.SUD),
                                Direction.EST);
            astar = new Astar(this.carte, depart, objectif, parcourtToutTerrain);
            // Pour atteindre la case, il faut faire 6 mouvements maximum
            it = depart;
            for (i = 0; (i < 6) && (it != objectif); i++) {
                it = astar.next(it);
            }
            assertThat("Il faut faire 6 mouvements pour atteindre l'arrivée",
                    i, is(6));
            assertThat("Doit atteindre la case de fin", it, is(objectif));
        } catch (SimulationException ex) {
            Logger.getLogger(testAstar.class.getName()).log(
                    Level.SEVERE, null, ex);
            fail();
        }
    }

    @Test
    public void avecObstacle() {
        Astar astar;
        Case obstacle;
        Case objectif;
        Case depart;
        try {
            obstacle = new Case(10, 9, NatureTerrain.ROCHE);
            carte.setCase(obstacle);
            objectif = this.carte.getCase(10, 10);
            depart = this.carte.getCase(10, 8);
            
            astar = new Astar(this.carte, depart, objectif,parcourtTerrainLibre);

            // Pour atteindre la case, il faut faire 4 mouvements maximum
            Case it = depart;
            int i;
            for (i = 0; (i < 4) && (it != objectif); i++) {
                it = astar.next(it);
                System.out.println(it);
                assertThat("Une case invalide à été franchie", it,
                        is(not(obstacle)));
            }
            assertThat("Il faut faire 4 mouvements pour atteindre l'arrivée",
                    i, is(4));
            assertThat("Doit atteindre la case de fin", it, is(objectif));

        } catch (SimulationException | ConstructionException ex) {
            Logger.getLogger(testAstar.class.getName()).log(
                    Level.SEVERE, null, ex);
            fail();
        }
    }
}
