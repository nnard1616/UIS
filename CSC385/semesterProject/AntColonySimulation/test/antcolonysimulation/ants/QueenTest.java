/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.ants;

import antcolonysimulation.ants.friendly.Scout;
import antcolonysimulation.ants.friendly.Soldier;
import antcolonysimulation.ants.friendly.Queen;
import antcolonysimulation.ants.friendly.Forager;
import antcolonysimulation.environment.Environment;
import antcolonysimulation.simulation.Simulation;
import dataStructures.ArrayList;
import dataStructures.LinkedList;
import dataStructures.List;
import dataStructures.ListIterator;
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
    private static List ants = new LinkedList();
            
    @BeforeClass
    public static void prep() {
        e = new Environment(3);
        q = new Queen(e.getSpace(1, 1), (LinkedList)ants);
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
        assertEquals("Wrong number of babies made", 1000, e.getSpace(1, 1).getFriendliesUIDs().size());
        
        int foragerCount = 0;
        int scoutCount   = 0;
        int soldierCount = 0;
        ListIterator litr = e.getSpace(1,1).getFriendliesUIDs().listIterator(0);
        while(litr.hasNext()){
            Integer uid = (Integer)litr.getCurrent();
            if (e.getSpace(1, 1).getFriendly(uid).getClass().equals(Forager.class))
                foragerCount++;
            if (e.getSpace(1, 1).getFriendly(uid).getClass().equals(Scout.class))
                scoutCount++;
            if (e.getSpace(1, 1).getFriendly(uid).getClass().equals(Soldier.class))
                soldierCount++;
            litr.next();
        }
        assertEquals("Should have 50% Foragers" + foragerCount, true, foragerCount/1000.0 >= 0.45 && foragerCount/1000.0 <= 0.55);
        assertEquals("Should have 25% Scouts", true, scoutCount/1000.0 >= 0.20 && scoutCount/1000.0 <= 0.30);
        assertEquals("Should have 25% Soldiers", true, soldierCount/1000.0 >= 0.20 && soldierCount/1000.0 <= 0.30);
    }
    
    @Test
    public void testAging(){
        Queen q2 = new Queen(e.getSpace(0,0), (LinkedList)ants);
        e.getSpace(0, 0).setFood(1000000000);
        Simulation.setTurn(0);
        
        while(q2.isAlive()){
            Simulation.incrementTurn();
            q2.act();
        }
        assertEquals("Queen should be dead at turn " + Lifespan.QUEEN.getValue(), Lifespan.QUEEN.getValue(), Simulation.getTurn()-1);
        assertEquals("Queen should be this old: " + Lifespan.QUEEN.getValue(), Lifespan.QUEEN.getValue(), q2.getAge());
    }
}
