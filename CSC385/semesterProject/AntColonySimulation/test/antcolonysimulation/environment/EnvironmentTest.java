/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.environment;

import Statistics.Statistics;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author nathan
 */
public class EnvironmentTest {
    /**
     * Test of getSpace method, of class Environment.
     */
    @Test
    public void testGetSpace() {
        System.out.println("getSpace: in bounds");
        int x = 0;
        int y = 0;
        Environment instance = new Environment();
        Space expResult = new Space(0,0,0);
        Space result = instance.getSpace(x, y);
        Assert.assertArrayEquals("getSpace gave unexpected results", expResult.getCoordinates(), result.getCoordinates());
        
        //Out of bounds test
        System.out.println("getSpace: out of bounds");
        x = 500;
        y = 500;
        expResult = null;
        result = instance.getSpace(x, y);
        Assert.assertEquals("getSpace gave unexpected results", expResult, result);
    }
    
    /**
     * Test of generateFood method, of class Environment.
     */
    @Test
    public void testGenerateFood() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        System.out.println("generateFood: testing legal values");
        int reps = 0;
        int zeroes = 0;
        int nonzeroes = 0;
        
        Environment e = new Environment();
        Method generateFood = Environment.class.getDeclaredMethod("generateFood");
        generateFood.setAccessible(true);
        
        List<Integer> foods = new ArrayList<>();
        
        while(reps < 10000){
            Integer food = (Integer)generateFood.invoke(e);
            
            if (food.equals(0))
                zeroes++;
            else{
                Assert.assertTrue("Food generation should be 0 or 500..1000, got this instead: " + food, (food >= 500) && (food <= 1000));
                nonzeroes++;
                foods.add(food);
            }
            reps++;
        }
        
        System.out.println("generateFood: testing nonzero food variance");
        int[] foodsArray = foods.stream().mapToInt(i -> i).toArray();
        Statistics stat = new Statistics(foodsArray);
        Assert.assertTrue("Not getting even distribution between 500..1000", (stat.getVariance() >= 19000) && (stat.getVariance() <= 22000));
        
        
        System.out.println("generateFood: testing zero food ratio");
        System.out.println(zeroes);
        double zeroesRatio = zeroes / (zeroes + nonzeroes + 0.0);
        Assert.assertTrue("Not the right amount of zeroes: " + zeroesRatio, (zeroesRatio >= 0.73) && (zeroesRatio <= 0.77));
    }
    
    /**
     * Test of addNeighbors method, of class Environment.
     */
    @Test
    public void testAddNeighbors(){
        System.out.println("testNeighbors");
        Environment te = new Environment(3);
        
        Object[] tlds = te.getSpace(0,0).getNeighbors().keySet().stream().sorted().toArray();
        Object[] trds = te.getSpace(0,2).getNeighbors().keySet().stream().sorted().toArray();
        Object[] blds = te.getSpace(2,0).getNeighbors().keySet().stream().sorted().toArray();
        Object[] brds = te.getSpace(2,2).getNeighbors().keySet().stream().sorted().toArray();
        Object[] topds = te.getSpace(0,1).getNeighbors().keySet().stream().sorted().toArray();
        Object[] botds = te.getSpace(2,1).getNeighbors().keySet().stream().sorted().toArray();
        Object[] rightds = te.getSpace(1,2).getNeighbors().keySet().stream().sorted().toArray();
        Object[] leftds = te.getSpace(1,0).getNeighbors().keySet().stream().sorted().toArray();
        Object[] midds = te.getSpace(1,1).getNeighbors().keySet().stream().sorted().toArray();
        
        Object[] tlcorner = new Direction[] {Direction.E, Direction.SE, Direction.S};
        Object[] trcorner = new Direction[] {Direction.SW, Direction.S, Direction.W};
        Object[] blcorner = new Direction[] {Direction.E, Direction.NE, Direction.N};
        Object[] brcorner = new Direction[] {Direction.NW, Direction.N, Direction.W};
        Object[] top      = new Direction[] {Direction.E, Direction.SE, Direction.SW, Direction.S, Direction.W};
        Object[] bot      = new Direction[] {Direction.E, Direction.NE, Direction.NW, Direction.N, Direction.W};
        Object[] right    = new Direction[] {Direction.NW, Direction.N, Direction.SW, Direction.S, Direction.W};
        Object[] left     = new Direction[] {Direction.E, Direction.NE, Direction.N, Direction.SE, Direction.S};
        Object[] middle   = new Direction[] {Direction.E, Direction.NE, Direction.NW, Direction.N, Direction.SE, Direction.SW, Direction.S, Direction.W};
        
        Assert.assertArrayEquals("Top left corner should have E, SE, S", tlcorner, tlds);
        Assert.assertArrayEquals("Top right corner should have SW, S, W", tlcorner, tlds);
        Assert.assertArrayEquals("Bot left corner should have E, NE, N", tlcorner, tlds);
        Assert.assertArrayEquals("Bot right corner should have NW, N, W", tlcorner, tlds);
        Assert.assertArrayEquals("Top should have E, SE, SW, S, W", tlcorner, tlds);
        Assert.assertArrayEquals("Bot should have E, NE, NW, N, W", tlcorner, tlds);
        Assert.assertArrayEquals("Right should have NW, N, SW, S, W", tlcorner, tlds);
        Assert.assertArrayEquals("Left should have E, NE, N, SE, S, W", tlcorner, tlds);
        Assert.assertArrayEquals("Middle should have E, NE, NW, N, SE, SW, S, W", tlcorner, tlds);
        
        int[] n  = Direction.N.getValue();
        int[] ne = Direction.NE.getValue();
        int[] e  = Direction.E.getValue();
        int[] se = Direction.SE.getValue();
        int[] s  = Direction.S.getValue();
        int[] sw = Direction.SW.getValue();
        int[] w  = Direction.W.getValue();
        int[] nw = Direction.NW.getValue();
        
        Assert.assertEquals("Neighbors of 0,0 are wrong.", te.getSpace(0+e[0], 0+e[1]), te.getSpace(0, 0).getNeighbor(Direction.E));
        Assert.assertEquals("Neighbors of 0,0 are wrong.", te.getSpace(0+se[0], 0+se[1]), te.getSpace(0, 0).getNeighbor(Direction.SE));
        Assert.assertEquals("Neighbors of 0,0 are wrong.", te.getSpace(0+s[0], 0+s[1]), te.getSpace(0, 0).getNeighbor(Direction.S));
        
        Assert.assertEquals("Neighbors of 0,2 are wrong.", te.getSpace(0+sw[0], 2+sw[1]), te.getSpace(0, 2).getNeighbor(Direction.SW));
        Assert.assertEquals("Neighbors of 0,2 are wrong.", te.getSpace(0+w[0], 2+w[1]), te.getSpace(0, 2).getNeighbor(Direction.W));
        Assert.assertEquals("Neighbors of 0,2 are wrong.", te.getSpace(0+s[0], 2+s[1]), te.getSpace(0, 2).getNeighbor(Direction.S));
        
        Assert.assertEquals("Neighbors of 2,0 are wrong.", te.getSpace(2+e[0], 0+e[1]), te.getSpace(2, 0).getNeighbor(Direction.E));
        Assert.assertEquals("Neighbors of 2,0 are wrong.", te.getSpace(2+ne[0], 0+ne[1]), te.getSpace(2, 0).getNeighbor(Direction.NE));
        Assert.assertEquals("Neighbors of 2,0 are wrong.", te.getSpace(2+n[0], 0+n[1]), te.getSpace(2, 0).getNeighbor(Direction.N));
        
        Assert.assertEquals("Neighbors of 2,2 are wrong.", te.getSpace(2+w[0], 2+w[1]), te.getSpace(2, 2).getNeighbor(Direction.W));
        Assert.assertEquals("Neighbors of 2,2 are wrong.", te.getSpace(2+nw[0], 2+nw[1]), te.getSpace(2, 2).getNeighbor(Direction.NW));
        Assert.assertEquals("Neighbors of 2,2 are wrong.", te.getSpace(2+n[0], 2+n[1]), te.getSpace(2, 2).getNeighbor(Direction.N));
        
        Assert.assertEquals("Neighbors of 0,1 are wrong.", te.getSpace(0+w[0], 1+w[1]), te.getSpace(0,1).getNeighbor(Direction.W));
        Assert.assertEquals("Neighbors of 0,1 are wrong.", te.getSpace(0+sw[0], 1+sw[1]), te.getSpace(0,1).getNeighbor(Direction.SW));
        Assert.assertEquals("Neighbors of 0,1 are wrong.", te.getSpace(0+s[0], 1+s[1]), te.getSpace(0,1).getNeighbor(Direction.S));
        Assert.assertEquals("Neighbors of 0,1 are wrong.", te.getSpace(0+e[0], 1+e[1]), te.getSpace(0,1).getNeighbor(Direction.E));
        Assert.assertEquals("Neighbors of 0,1 are wrong.", te.getSpace(0+se[0], 1+se[1]), te.getSpace(0,1).getNeighbor(Direction.SE));
        
        Assert.assertEquals("Neighbors of 2,1 are wrong.", te.getSpace(2+e[0], 1+e[1]), te.getSpace(2,1).getNeighbor(Direction.E));
        Assert.assertEquals("Neighbors of 2,1 are wrong.", te.getSpace(2+ne[0], 1+ne[1]), te.getSpace(2,1).getNeighbor(Direction.NE));
        Assert.assertEquals("Neighbors of 2,1 are wrong.", te.getSpace(2+nw[0], 1+nw[1]), te.getSpace(2, 1).getNeighbor(Direction.NW));
        Assert.assertEquals("Neighbors of 2,1 are wrong.", te.getSpace(2+n[0], 1+n[1]), te.getSpace(2, 1).getNeighbor(Direction.N));
        Assert.assertEquals("Neighbors of 2,1 are wrong.", te.getSpace(2+w[0], 1+w[1]), te.getSpace(2, 1).getNeighbor(Direction.W));
        
        Assert.assertEquals("Neighbors of 1,2 are wrong.", te.getSpace(1+s[0], 2+s[1]), te.getSpace(1,2).getNeighbor(Direction.S));
        Assert.assertEquals("Neighbors of 1,2 are wrong.", te.getSpace(1+sw[0], 2+sw[1]), te.getSpace(1,2).getNeighbor(Direction.SW));
        Assert.assertEquals("Neighbors of 1,2 are wrong.", te.getSpace(1+nw[0], 2+nw[1]), te.getSpace(1,2).getNeighbor(Direction.NW));
        Assert.assertEquals("Neighbors of 1,2 are wrong.", te.getSpace(1+n[0], 2+n[1]), te.getSpace(1,2).getNeighbor(Direction.N));
        Assert.assertEquals("Neighbors of 1,2 are wrong.", te.getSpace(1+w[0], 2+w[1]), te.getSpace(1,2).getNeighbor(Direction.W));
        
        Assert.assertEquals("Neighbors of 1,0 are wrong.", te.getSpace(1+e[0], 0+e[1]), te.getSpace(1,0).getNeighbor(Direction.E));
        Assert.assertEquals("Neighbors of 1,0 are wrong.", te.getSpace(1+ne[0], 0+ne[1]), te.getSpace(1,0).getNeighbor(Direction.NE));
        Assert.assertEquals("Neighbors of 1,0 are wrong.", te.getSpace(1+n[0], 0+n[1]), te.getSpace(1,0).getNeighbor(Direction.N));
        Assert.assertEquals("Neighbors of 1,0 are wrong.", te.getSpace(1+se[0], 0+se[1]), te.getSpace(1,0).getNeighbor(Direction.SE));
        Assert.assertEquals("Neighbors of 1,0 are wrong.", te.getSpace(1+w[0], 0+w[1]), te.getSpace(1,0).getNeighbor(Direction.W));
        
        Assert.assertEquals("Neighbors of 1,1 are wrong.", te.getSpace(1+e[0], 1+e[1]), te.getSpace(1,1).getNeighbor(Direction.E));
        Assert.assertEquals("Neighbors of 1,1 are wrong.", te.getSpace(1+ne[0], 1+ne[1]), te.getSpace(1,1).getNeighbor(Direction.NE));
        Assert.assertEquals("Neighbors of 1,1 are wrong.", te.getSpace(1+n[0], 1+n[1]), te.getSpace(1,1).getNeighbor(Direction.N));
        Assert.assertEquals("Neighbors of 1,1 are wrong.", te.getSpace(1+se[0], 1+se[1]), te.getSpace(1,1).getNeighbor(Direction.SE));
        Assert.assertEquals("Neighbors of 1,1 are wrong.", te.getSpace(1+w[0], 1+w[1]), te.getSpace(1,1).getNeighbor(Direction.W));
        Assert.assertEquals("Neighbors of 1,1 are wrong.", te.getSpace(1+nw[0], 1+nw[1]), te.getSpace(1,1).getNeighbor(Direction.NW));
        Assert.assertEquals("Neighbors of 1,1 are wrong.", te.getSpace(1+sw[0], 1+sw[1]), te.getSpace(1,1).getNeighbor(Direction.SW));
        Assert.assertEquals("Neighbors of 1,1 are wrong.", te.getSpace(1+s[0], 1+s[1]), te.getSpace(1,1).getNeighbor(Direction.S));
    }
}
