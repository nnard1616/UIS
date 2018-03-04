/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.ants;

import antcolonysimulation.environment.Direction;
import antcolonysimulation.environment.Environment;
import antcolonysimulation.environment.Space;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author nathan
 */
public class SoldierTest {
    private static Environment e;
    @BeforeClass
    public static void prep() {
        e = new Environment(3);
    }

    /**
     * Test of act method, of class Soldier.
     */
    @Test
    public void testAct() {
        System.out.println("act");
        Soldier instance = null;
        instance.act();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of moveTo method, of class Soldier.
     */
    @Test
    public void testMoveTo() {
        System.out.println("moveTo");
        
        Space center = e.getSpace(1,1);
        Soldier s = new Soldier(center);
        center.addFriendly(s);
        center.setExplored(true);
        
        e.getSpace(0, 0).addEnemy(new Bala(e.getSpace(0, 0)));
        e.getSpace(0, 0).setExplored(true);
        
        e.getSpace(0, 1).addEnemy(new Bala(e.getSpace(0, 1)));
        e.getSpace(0, 1).addEnemy(new Bala(e.getSpace(0, 1)));
        e.getSpace(0, 1).setExplored(true);
        
        e.getSpace(0, 2).addEnemy(new Bala(e.getSpace(0, 2)));
        e.getSpace(0, 2).addEnemy(new Bala(e.getSpace(0, 2)));
        e.getSpace(0, 2).addEnemy(new Bala(e.getSpace(0, 2)));
        
        
        s.act();
        assertEquals("Soldier should have moved up", e.getSpace(0, 1), s.getSpace());
        assertEquals("Top space should have soldier", s, e.getSpace(0, 1).getFriendly(s.getUID()));
        assertEquals("Middle space should have no soldier", 0, e.getSpace(1, 1).getSoldierCount());
        assertEquals("Soldier should still be in scout mode", true, s.isScouting());
        
        s.act();
        assertEquals("Soldier should be in attack mode", false, s.isScouting());
        
        while (!s.isScouting())
            s.act();
        
        System.out.println(s.getSpace());
        assertEquals("Soldier should have killed enemies at " + e.getSpace(0, 1), 0, e.getSpace(0, 1).getBalaCount());
        assertEquals("Soldier should not have killed enemies at " + e.getSpace(0, 0), 0, e.getSpace(0, 1).getBalaCount());
        assertEquals("Soldier should have moved to top left", e.getSpace(0, 0), s.getSpace());
        assertEquals("Soldier should be back in scout mode", true, s.isScouting());
        
        s.act();
        assertEquals("Soldier should be in attack mode", false, s.isScouting());
        
        while (!s.isScouting())
            s.act();
        
        System.out.println(s.getSpace());
        assertEquals("Soldier should have killed enemies at " + e.getSpace(0, 0), 0, e.getSpace(0, 0).getBalaCount());
        
        while (s.isAlive()){
            s.act();
            assertEquals("Soldier should not be stepping in unexplored areas", true, s.getSpace().isExplored());
            System.out.println(s.getSpace());
        }
        
        assertEquals("Soldier should be dead", false, s.isAlive());
        assertEquals("Soldier should not be on board", null, s.getSpace().getFriendly(s.getUID()));
        assertEquals("Space should have 0 soldierCount", 0, s.getSpace().getSoldierCount());
        assertEquals("Top right corner should still have enemies", 3, e.getSpace(0, 2).getBalaCount());
    }

    /**
     * Test of scout method, of class Soldier.
     */
    @Test
    public void testScout() {
        System.out.println("scout");
        Soldier instance = null;
        instance.scout();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of attack method, of class Soldier.
     */
    @Test
    public void testAttack() {
        System.out.println("attack");
        Soldier instance = null;
        instance.attack();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
