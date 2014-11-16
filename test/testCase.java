/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    public testCase() {
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
        Case case1   = new Case(1, 1);
        
        org.junit.Assert.assertEquals("must be same case",  case1,
                case1.left().right()
        );
        org.junit.Assert.assertEquals("must be same case",  case1,
                case1.right().left()
        );
        org.junit.Assert.assertEquals("must be same case",  case1,
                case1.down().up()
        );
        org.junit.Assert.assertEquals("must be same case",  case1,
                case1.up().down()
        );
        
        
        org.junit.Assert.assertEquals("must be same case",  case1,
                case1.up().right().down().left()
        );
        org.junit.Assert.assertEquals("must be same case",  case1,
                case1.up().right().left().down()
        );
        
        //////////////////////////////////////////////////////////
        
        case1   = new Case(1000000, 20000);
        
        org.junit.Assert.assertEquals("must be same case",  case1,
                case1.left().right()
        );
        org.junit.Assert.assertEquals("must be same case",  case1,
                case1.right().left()
        );
        org.junit.Assert.assertEquals("must be same case",  case1,
                case1.down().up()
        );
        org.junit.Assert.assertEquals("must be same case",  case1,
                case1.up().down()
        );
        
        
        org.junit.Assert.assertEquals("must be same case",  case1,
                case1.up().right().down().left()
        );
        org.junit.Assert.assertEquals("must be same case",  case1,
                case1.up().right().left().down()
        );
    }
}
