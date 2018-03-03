/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.ants;

import antcolonysimulation.environment.Environment;
import antcolonysimulation.simulation.Simulation;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author nathan
 */
public class QueenTest {
    private static Environment e;
    private static Queen q;
            
    @BeforeClass
    public static void prep() {
        e = new Environment(3);
        q = new Queen(e.getSpace(1, 1));
        e.getSpace(1, 1).addFriendly(q);
        e.getSpace(1, 1).setFood(10000);
    }

    /**
     * Test of act method, of class Queen.
     */
    @Test
    public void testAct() {
        System.out.println("act");
        assertEquals("Queen should be alive", true, q.isAlive());
        
        q.act();
        Simulation.incrementTurn();
        assertEquals("Queen did not eat properly", 9999, e.getSpace(1, 1).getFood());
        assertEquals("Queen did not produce baby", 2, e.getSpace(1, 1).getFriendliesUIDs().size());
        
        for (int i = 9999; i > 0; i--){
            q.act();
            Simulation.incrementTurn();
        }
        
        q.act();
        Simulation.incrementTurn();
        assertEquals("Queen should be dead", false, q.isAlive());
        assertEquals("Wrong number of babies made", 1001, e.getSpace(1, 1).getFriendliesUIDs().size());
        
        int foragerCount = 0;
        int scoutCount   = 0;
        int soldierCount = 0;
        for (int uid : e.getSpace(1, 1).getFriendliesUIDs()){
            if (e.getSpace(1, 1).getFriendly(uid).getClass().equals(Forager.class))
                foragerCount++;
            if (e.getSpace(1, 1).getFriendly(uid).getClass().equals(Scout.class))
                scoutCount++;
            if (e.getSpace(1, 1).getFriendly(uid).getClass().equals(Soldier.class))
                soldierCount++;
        }
        assertEquals("Should have 50% Foragers" + foragerCount, true, foragerCount/1000.0 >= 0.47 && foragerCount/1000.0 <= 0.53);
        assertEquals("Should have 25% Scouts", true, scoutCount/1000.0 >= 0.22 && scoutCount/1000.0 <= 0.28);
        assertEquals("Should have 25% Soldiers", true, soldierCount/1000.0 >= 0.22 && soldierCount/1000.0 <= 0.28);
    }
}