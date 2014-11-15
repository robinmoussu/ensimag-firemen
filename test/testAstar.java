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


/**
 *
 * @author robin
 */
public class testAstar {
    
    private Carte carte;
    
    public testAstar() {
        try {
            this.carte = new Carte(10, 10, 1000);
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
        Astar astar     = new Astar(this.carte);
        Case objectif   = new Case(1, 1);
        NatureTerrain[] terrains = {NatureTerrain.TERRAIN_LIBRE};
        
        assertEquals("do not move",  objectif,
                astar.next(objectif, objectif, terrains));
    }
    
    @Test
    public void voisin() {
        Astar astar     = new Astar(this.carte);
        Case objectif   = new Case(1, 1);
        NatureTerrain[] terrains = {NatureTerrain.TERRAIN_LIBRE};

        assertEquals("must go right",  objectif,
                astar.next(objectif.left(),
                        objectif, terrains));
        assertEquals("must go left", objectif,
                astar.next(objectif.right(),
                        objectif, terrains));
        assertEquals("must go down",    objectif,
                astar.next(objectif.up(),
                        objectif, terrains));
        assertEquals("must go up",  objectif,
                astar.next(objectif.down(),
                        objectif, terrains));
    }
    
    @Test
    public void distantSansObstacle() {
        Astar astar     = new Astar(this.carte);
        Case objectif   = new Case(10, 10);
        NatureTerrain[] terrains = {NatureTerrain.TERRAIN_LIBRE};
        Case depart;
        
        
        // 2 times in same direction
        assertEquals("start 2 time top",  objectif,
                astar.next(astar.next(objectif.up().up()
                , objectif, terrains), objectif, terrains));
        
        assertEquals("start 2 time bottom",  objectif,
                astar.next(astar.next(objectif.down().down()
                , objectif, terrains), objectif, terrains));
                
        assertEquals("start 2 time right",  objectif,
                astar.next(astar.next(objectif.right().right()
                , objectif, terrains), objectif, terrains));
                
        assertEquals("start 2 time left",  objectif,
                astar.next(astar.next(objectif.left().left()
                , objectif, terrains), objectif, terrains));
        
        
        // corner
        depart = objectif.up().left();
        assertEquals("start corner top left",  objectif,
                astar.next(astar.next(
                depart
                , objectif, terrains), objectif, terrains));
        
        assertEquals("start corner top right", objectif,
                astar.next(astar.next(objectif.up().right()
                , objectif, terrains), objectif, terrains));
                
        assertEquals("start corner bottom left",    objectif,
                astar.next(astar.next(objectif.down().left()
                , objectif, terrains), objectif, terrains));
                
        assertEquals("start corner bottom right",  objectif,
                astar.next(astar.next(objectif.down().right()
                , objectif, terrains), objectif, terrains));
        
        
        // far away
        assertEquals("far away",  objectif,
                astar.next(astar.next(astar.next(astar.next(astar.next(astar.next(
                objectif.up().up().up().left().left().left()
                , objectif, terrains), objectif, terrains)
                , objectif, terrains), objectif, terrains)
                , objectif, terrains), objectif, terrains)
        );
        
        assertEquals("far away",  objectif,
                astar.next(astar.next(astar.next(astar.next(astar.next(astar.next(
                objectif.down().down().down().down().left().left()
                , objectif, terrains), objectif, terrains)
                , objectif, terrains), objectif, terrains)
                , objectif, terrains), objectif, terrains)
        );
    }
}
