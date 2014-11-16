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
public class testCase {
    
    private Carte carte;
    
    public testCase() {
        try {
            carte = new Carte(1000,1000,1000);
        } catch (ConstructionException ex) {
            Logger.getLogger(testCase.class.getName()).log(Level.SEVERE, null, ex);
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
    public void sameCase() {
        try {
            Case case1   = this.carte.getCase(900, 900);
            
            org.junit.Assert.assertEquals("must be same case",  case1,
                    this.carte.getVoisin(this.carte.getVoisin(case1, Direction.OUEST), Direction.EST)
            );
            org.junit.Assert.assertEquals("must be same case",  case1,
                    this.carte.getVoisin(this.carte.getVoisin(case1, Direction.EST), Direction.OUEST)
            );
            org.junit.Assert.assertEquals("must be same case",  case1,
                    this.carte.getVoisin(this.carte.getVoisin(case1, Direction.NORD), Direction.SUD)
            );
            org.junit.Assert.assertEquals("must be same case",  case1,
                    this.carte.getVoisin(this.carte.getVoisin(case1, Direction.SUD), Direction.NORD)
            );
            
            
            org.junit.Assert.assertEquals("must be same case",  case1,
                    this.carte.getVoisin(this.carte.getVoisin(
                    this.carte.getVoisin(this.carte.getVoisin( case1, 
                            Direction.SUD),
                            Direction.NORD),
                            Direction.EST),
                            Direction.OUEST)
            );
            org.junit.Assert.assertEquals("must be same case",  case1,
                    this.carte.getVoisin(this.carte.getVoisin(
                    this.carte.getVoisin(this.carte.getVoisin( case1, 
                            Direction.SUD),
                            Direction.OUEST),
                            Direction.EST),
                            Direction.NORD)
            );
            
            //////////////////////////////////////////////////////////
            
            org.junit.Assert.assertEquals("must be same case",  case1,
                    this.carte.getVoisin(this.carte.getVoisin(
                    this.carte.getVoisin(this.carte.getVoisin( case1, 
                            Direction.SUD),
                            Direction.SUD),
                            Direction.NORD),
                            Direction.NORD)
            );
            
            org.junit.Assert.assertEquals("must be same case",  case1,
                    this.carte.getVoisin(this.carte.getVoisin(
                    this.carte.getVoisin(this.carte.getVoisin( case1, 
                            Direction.EST),
                            Direction.OUEST),
                            Direction.EST),
                            Direction.OUEST)
            );
            
        } catch (SimulationException ex) {
            Logger.getLogger(testCase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
