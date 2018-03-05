/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.ants;

import antcolonysimulation.ants.friendly.Forager;
import antcolonysimulation.environment.Environment;
import antcolonysimulation.environment.Space;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nathan
 */
public class ForagerTest {
    
    public ForagerTest() {
    }

    /**
     * Test of movement methods, of class Forager.
     */
    @Test
    public void testAvoidLooping() {
        System.out.println("avoidLooping");
        Environment e = new Environment(5);
        e.revealAll();
        e.getSpace(0, 1).setPheromone(10);
        e.getSpace(0, 2).setPheromone(20);
        e.getSpace(0, 3).setPheromone(30);
        e.getSpace(1, 4).setPheromone(40);
        e.getSpace(2, 4).setPheromone(50);
        e.getSpace(3, 4).setPheromone(60);
        e.getSpace(4, 3).setPheromone(70);
        e.getSpace(4, 2).setPheromone(80);
        e.getSpace(4, 1).setPheromone(90);
        e.getSpace(3, 0).setPheromone(100);
        e.getSpace(2, 0).setPheromone(110);
        e.getSpace(1, 0).setPheromone(2);
        e.getSpace(1, 1).setPheromone(1);
        
        //endspace
        e.getSpace(0, 0).setPheromone(1);
        
        System.out.println("pheromones:");
        System.out.println(e.pheromonesToString());
        System.out.println("");
        e.setAllFood(0);
        System.out.println("foods:");
        System.out.println(e.foodsToString());
        
        Forager f = new Forager(e.getSpace(0, 0), 15);
        
        assertEquals("wrong maxPheromone", 10, f.maxNeighborPheromoneCount());
        f.act();
        
        assertEquals("Forager should have moved to 0, 1",  e.getSpace(0, 1), f.getSpace());
        assertEquals("0,0 Space should be considered recently visited", true, f.recentlyVisited(e.getSpace(0, 0)));
        for (int i = 0; i < 12; i++)
            f.act();
        
        assertEquals("Forager is not avoiding recent visited", e.getSpace(1, 1), f.getSpace());
        
        f.act();
        assertEquals("Should move to random 0 space, moved here though: " + f.getSpace(),
                     true,
                     f.getSpace() == e.getSpace(1, 2) ||
                     f.getSpace() == e.getSpace(2, 1) ||
                     f.getSpace() == e.getSpace(2, 2));
        
        
    }
    
    /**
     * Test of movement methods, of class Forager.
     */
    @Test
    public void testAvoidHinderingAvoidLooping() {
        System.out.println("avoidHinderingAvoidLooping");
        Environment e = new Environment(2);
        e.revealAll();
        e.setAllFood(0);
        
        e.getSpace(0, 0).setPheromone(1);
        e.getSpace(0, 1).setPheromone(2);
        e.getSpace(1, 0).setPheromone(3);
        e.getSpace(1, 1).setPheromone(4);
        
        System.out.println(e.pheromonesToString());
        Forager f = new Forager(e.getSpace(0, 0));
        
        f.act();
        f.act();
        f.act();
        
        assertEquals("A) Should be at 0,1", e.getSpace(0, 1), f.getSpace());
        f.act();
        assertEquals("B) Should be at 1,1", e.getSpace(1, 1), f.getSpace());
        assertEquals("Travelhistory should be trimmed", 2, f.getTravelHistory().size());
        f.act();
        assertEquals("C) Should be at 1,0", e.getSpace(1, 0), f.getSpace());
        f.act();
        assertEquals("D) Should be at 0,0", e.getSpace(0, 0), f.getSpace());
        f.act();
        assertEquals("E) Should be at 1,1", e.getSpace(1, 1), f.getSpace());
        assertEquals("Travelhistory should be trimmed", 2, f.getTravelHistory().size());
        f.act();
        f.act();
        assertEquals("E) Should be at 0,1", e.getSpace(0, 1), f.getSpace());
        assertEquals("Travelhistory should be trimmed", 4, f.getTravelHistory().size());
    }

    /**
     * Test of addPairs method, of class Forager.
     */
    @Test
    public void testAddPairs() {
        System.out.println("addPairs");
        int[] receiver = new int[] {0,0};
        int[] giver = new int[]{1,-1};
        Forager instance = new Forager(new Space(5,5,5));
        instance.addPairs(receiver, giver);
        assertArrayEquals("receiver should be 1,-1", giver, receiver);
        instance.addPairs(receiver, giver);
        assertArrayEquals("receiver should be 2, -2", new int[] {2,-2}, receiver);
    }

    /**
     * Test of backtrack method, of class Forager.
     */
    @Test
    public void testBacktrack() {
        System.out.println("backtrack");
        Environment e = new Environment(3);
        e.revealAll();
        e.setAllFood(0);
        
        e.getSpace(0, 0).setPheromone(1);
        e.getSpace(0, 1).setPheromone(2);
        e.getSpace(0, 2).setPheromone(3);
        e.getSpace(1, 2).setPheromone(4);
        e.getSpace(2, 2).setPheromone(5);
        e.getSpace(2, 2).setFood(1);
        
        Forager f = new Forager(e.getSpace(0, 0));
        
        f.act();
        f.act();
        f.act();
        assertEquals("Forager should be at 2,2", e.getSpace(2, 2), f.getSpace());
        assertEquals("Forager should be done foraging", false, f.isForaging());
        f.act();
        assertEquals("A) Forager should be at 1,2", e.getSpace(1, 2), f.getSpace());
        f.act();
        assertEquals("B) Forager should be at 0,1", e.getSpace(0, 1), f.getSpace());
        f.act();
        assertEquals("C) Forager should be at 0,0", e.getSpace(0, 0), f.getSpace());
        assertEquals("Forager should be back to foraging", true, f.isForaging());
        assertEquals("Food should have been deposited", 1, f.getSpace().getFood());
        System.out.println(e.pheromonesToString());
        f.act();
        assertEquals("D) Forager should be at 0,1", e.getSpace(0, 1), f.getSpace());
        assertEquals("Should be foraging", true, f.isForaging());
        System.out.println(e.foodsToString());
    }
}