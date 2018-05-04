/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.ants;

import antcolonysimulation.ants.friendly.Scout;
import antcolonysimulation.environment.Direction;
import antcolonysimulation.environment.Environment;
import antcolonysimulation.environment.Space;
import antcolonysimulation.simulation.Simulation;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author nathan
 */
public class ScoutTest {
    private static Environment e;
    private static Scout s;
            
    @BeforeClass
    public static void prep() {
        e = new Environment(3);
        s = new Scout(e.getSpace(1, 1));
    }
    
    /**
     * Test of moveTo method, of class Scout.
     */
    @Test
    public void testMoveTo() {
        System.out.println("moveTo");
        s = new Scout(e.getSpace(1, 1));
        Direction next = (Direction)(s.getSpace().getNeighborsDirections().get(0));
        Space nextSpace = s.getSpace().getNeighbor(next);
        Space oldSpace  = s.getSpace();
        s.moveTo(nextSpace);
        
        assertEquals("Scout did not move to correct space", nextSpace, s.getSpace());
        assertEquals("Scout should be known by space", nextSpace.getFriendly(s.getUID()), s);
        assertEquals("SCout should not be known by old space", null, oldSpace.getFriendly(s.getUID()));
        
    }
    
    /**
     * Test of act method, of class Scout.
     */
    @Test
    public void testAct() {
        System.out.println("act");
        
        while (s.isAlive()){
            s.act();
            Simulation.incrementTurn();
        }
        
        assertEquals("Scout should be this old: " + Lifespan.OTHER.getValue(), Lifespan.OTHER.getValue(), s.getAge());
        
        int exploredCount = 0;
        for (Space[] row : e.getGrid())
            for (Space n : row)
                if (n.isExplored())
                    exploredCount++;
        
        assertEquals("Scout should have explored everything", 9, exploredCount);
                    
    }

    
    
}
