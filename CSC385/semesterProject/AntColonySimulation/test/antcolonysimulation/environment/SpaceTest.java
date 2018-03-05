/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.environment;

import antcolonysimulation.ants.enemy.Bala;
import antcolonysimulation.ants.enemy.Enemy;
import antcolonysimulation.ants.friendly.Friendly;
import antcolonysimulation.ants.friendly.Queen;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author nathan
 */
public class SpaceTest {
    private static Environment te = new Environment();
    private static Enemy ant0;
    private static Enemy ant1;
    private static Friendly ant2;
    private static Friendly ant3;
    
    @BeforeClass
    public static void prep(){
        ant0 = new Bala(te.getSpace(0, 0));
        ant1 = new Bala(te.getSpace(0, 0));
        ant2 = new Queen(te.getSpace(0, 0));
        ant3 = new Queen(te.getSpace(0, 0));
        
        te.getSpace(0, 0).addEnemy(ant0);     //UID 0
        te.getSpace(0, 0).addEnemy(ant1);     //UID 1
        te.getSpace(0, 0).addFriendly(ant2);  //UID 2
        te.getSpace(0, 0).addFriendly(ant3);  //UID 3
    }
    /**
     * Test of popEnemy method, of class Space.
     */
    @Test
    public void testPopEnemy() {
        System.out.println("popEnemy");
        int UID = 0;
        Space instance = te.getSpace(0, 0);
        Enemy expResult = ant0;
        Enemy result = instance.popEnemy(UID);
        assertEquals("Enemy was not in map", expResult, result);
        assertEquals("Non-existing enemy did not return null from map", instance.popEnemy(UID), null);
    }

    /**
     * Test of popFriendly method, of class Space.
     */
    @Test
    public void testPopFriendly() {
        System.out.println("popFriendly");
        int UID = 2;
        Space instance = te.getSpace(0, 0);
        Friendly expResult = ant2;
        Friendly result = instance.popFriendly(UID);
        assertEquals("Friendly was not in map", expResult, result);
        assertEquals("Non-existing friendly did not return null from map", instance.popFriendly(UID), null);
    }

    /**
     * Test of getEnemy method, of class Space.
     */
    @Test
    public void testGetEnemy() {
        System.out.println("getEnemy");
        int UID = 0;
        Space instance = te.getSpace(0,0);
        Enemy expResult = ant0;
        Enemy result = instance.getEnemy(UID);
        assertEquals("Enemy was not in map", expResult, result);
        assertEquals("Non-existing enemy did not return null from map", instance.getEnemy(2), null);
    }

    /**
     * Test of getFriendly method, of class Space.
     */
    @Test
    public void testGetFriendly() {
        System.out.println("getFriendly");
        int UID = 2;
        Space instance = te.getSpace(0, 0);
        Friendly expResult = ant2;
        Friendly result = instance.getFriendly(UID);
        assertEquals("Friendly was not in map", expResult, result);
        assertEquals("Non-existing friendly did not return null from map", instance.getFriendly(0), null);
    }  
    
    /**
     * Test of decrementFood method, of class Space.
     */
    @Test
    public void testDecrementFood() {
        System.out.println("decrementFood");
        Space instance = te.getSpace(0, 0);
        instance.setFood(1);
        
        boolean result  = instance.decrementFood();
        assertEquals("decrementFood did not decrease food properly", 0, instance.getFood());
        assertEquals("decrementFood does not return true when it's supposed to", true, result);
        
        result = instance.decrementFood();
        assertEquals("decrementFood decreased food when it shouldn't", 0, instance.getFood());
        assertEquals("decrementFood does not return false when it's supposed to", false, result);
    }   
}
