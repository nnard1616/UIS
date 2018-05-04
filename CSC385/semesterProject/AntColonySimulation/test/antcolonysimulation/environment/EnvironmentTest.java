/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.environment;

import Statistics.Statistics;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import dataStructures.ArrayList;
import dataStructures.Doublet;
import dataStructures.List;
import dataStructures.ListIterator;
import java.util.Arrays;
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
        Assert.assertArrayEquals("getSpace gave unexpected results", 
                        new Integer[]{expResult.getX(), expResult.getY()}, 
                        new Integer[]{result.getX(),result.getY()});
        
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
        
        ArrayList foods = new ArrayList();
        
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
        Integer[] foodsArray = trimNulls(Arrays.copyOf(foods.getTheItems(), foods.getTheItems().length, Integer[].class));
        Statistics stat = new Statistics(foodsArray);
        Assert.assertTrue("Not getting even distribution between 500..1000", (stat.getVariance() >= 19000) && (stat.getVariance() <= 22000));
        
        System.out.println("generateFood: testing zero food ratio");
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
        
        Object[] tlds = convertListToArray(te.getSpace(0,0).getNeighborsDirections());
        Object[] trds = convertListToArray(te.getSpace(0,2).getNeighborsDirections());
        Object[] blds = convertListToArray(te.getSpace(2,0).getNeighborsDirections());
        Object[] brds = convertListToArray(te.getSpace(2,2).getNeighborsDirections());
        Object[] topds = convertListToArray(te.getSpace(0,1).getNeighborsDirections());
        Object[] botds = convertListToArray(te.getSpace(2,1).getNeighborsDirections());
        Object[] rightds = convertListToArray(te.getSpace(1,2).getNeighborsDirections());
        Object[] leftds = convertListToArray(te.getSpace(1,0).getNeighborsDirections());
        Object[] midds = convertListToArray(te.getSpace(1,1).getNeighborsDirections());
        
        Arrays.sort(tlds);
        Arrays.sort(trds);
        Arrays.sort(blds);
        Arrays.sort(brds);
        Arrays.sort(topds);
        Arrays.sort(botds);
        Arrays.sort(rightds);
        Arrays.sort(leftds);
        Arrays.sort(midds);
        
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
        
        Doublet n  = Direction.N.getValue();
        Doublet ne = Direction.NE.getValue();
        Doublet e  = Direction.E.getValue();
        Doublet se = Direction.SE.getValue();
        Doublet s  = Direction.S.getValue();
        Doublet sw = Direction.SW.getValue();
        Doublet w  = Direction.W.getValue();
        Doublet nw = Direction.NW.getValue();
        
        Assert.assertEquals("Neighbors of 0,0 are wrong.", te.getSpace(0+(Integer)e.getX(), 0+(Integer)e.getY()), te.getSpace(0, 0).getNeighbor(Direction.E));
        Assert.assertEquals("Neighbors of 0,0 are wrong.", te.getSpace(0+(Integer)se.getX(), 0+(Integer)se.getY()), te.getSpace(0, 0).getNeighbor(Direction.SE));
        Assert.assertEquals("Neighbors of 0,0 are wrong.", te.getSpace(0+(Integer)s.getX(), 0+(Integer)s.getY()), te.getSpace(0, 0).getNeighbor(Direction.S));
        
        Assert.assertEquals("Neighbors of 0,2 are wrong.", te.getSpace(0+(Integer)sw.getX(), 2+(Integer)sw.getY()), te.getSpace(0, 2).getNeighbor(Direction.SW));
        Assert.assertEquals("Neighbors of 0,2 are wrong.", te.getSpace(0+(Integer)w.getX(), 2+(Integer)w.getY()), te.getSpace(0, 2).getNeighbor(Direction.W));
        Assert.assertEquals("Neighbors of 0,2 are wrong.", te.getSpace(0+(Integer)s.getX(), 2+(Integer)s.getY()), te.getSpace(0, 2).getNeighbor(Direction.S));
        
        Assert.assertEquals("Neighbors of 2,0 are wrong.", te.getSpace(2+(Integer)e.getX(), 0+(Integer)e.getY()), te.getSpace(2, 0).getNeighbor(Direction.E));
        Assert.assertEquals("Neighbors of 2,0 are wrong.", te.getSpace(2+(Integer)ne.getX(), 0+(Integer)ne.getY()), te.getSpace(2, 0).getNeighbor(Direction.NE));
        Assert.assertEquals("Neighbors of 2,0 are wrong.", te.getSpace(2+(Integer)n.getX(), 0+(Integer)n.getY()), te.getSpace(2, 0).getNeighbor(Direction.N));
        
        Assert.assertEquals("Neighbors of 2,2 are wrong.", te.getSpace(2+(Integer)w.getX(), 2+(Integer)w.getY()), te.getSpace(2, 2).getNeighbor(Direction.W));
        Assert.assertEquals("Neighbors of 2,2 are wrong.", te.getSpace(2+(Integer)nw.getX(), 2+(Integer)nw.getY()), te.getSpace(2, 2).getNeighbor(Direction.NW));
        Assert.assertEquals("Neighbors of 2,2 are wrong.", te.getSpace(2+(Integer)n.getX(), 2+(Integer)n.getY()), te.getSpace(2, 2).getNeighbor(Direction.N));
        
        Assert.assertEquals("Neighbors of 0,1 are wrong.", te.getSpace(0+(Integer)w.getX(), 1+(Integer)w.getY()), te.getSpace(0,1).getNeighbor(Direction.W));
        Assert.assertEquals("Neighbors of 0,1 are wrong.", te.getSpace(0+(Integer)sw.getX(), 1+(Integer)sw.getY()), te.getSpace(0,1).getNeighbor(Direction.SW));
        Assert.assertEquals("Neighbors of 0,1 are wrong.", te.getSpace(0+(Integer)s.getX(), 1+(Integer)s.getY()), te.getSpace(0,1).getNeighbor(Direction.S));
        Assert.assertEquals("Neighbors of 0,1 are wrong.", te.getSpace(0+(Integer)e.getX(), 1+(Integer)e.getY()), te.getSpace(0,1).getNeighbor(Direction.E));
        Assert.assertEquals("Neighbors of 0,1 are wrong.", te.getSpace(0+(Integer)se.getX(), 1+(Integer)se.getY()), te.getSpace(0,1).getNeighbor(Direction.SE));
        
        Assert.assertEquals("Neighbors of 2,1 are wrong.", te.getSpace(2+(Integer)e.getX(), 1+(Integer)e.getY()), te.getSpace(2,1).getNeighbor(Direction.E));
        Assert.assertEquals("Neighbors of 2,1 are wrong.", te.getSpace(2+(Integer)ne.getX(), 1+(Integer)ne.getY()), te.getSpace(2,1).getNeighbor(Direction.NE));
        Assert.assertEquals("Neighbors of 2,1 are wrong.", te.getSpace(2+(Integer)nw.getX(), 1+(Integer)nw.getY()), te.getSpace(2, 1).getNeighbor(Direction.NW));
        Assert.assertEquals("Neighbors of 2,1 are wrong.", te.getSpace(2+(Integer)n.getX(), 1+(Integer)n.getY()), te.getSpace(2, 1).getNeighbor(Direction.N));
        Assert.assertEquals("Neighbors of 2,1 are wrong.", te.getSpace(2+(Integer)w.getX(), 1+(Integer)w.getY()), te.getSpace(2, 1).getNeighbor(Direction.W));
        
        Assert.assertEquals("Neighbors of 1,2 are wrong.", te.getSpace(1+(Integer)s.getX(), 2+(Integer)s.getY()), te.getSpace(1,2).getNeighbor(Direction.S));
        Assert.assertEquals("Neighbors of 1,2 are wrong.", te.getSpace(1+(Integer)sw.getX(), 2+(Integer)sw.getY()), te.getSpace(1,2).getNeighbor(Direction.SW));
        Assert.assertEquals("Neighbors of 1,2 are wrong.", te.getSpace(1+(Integer)nw.getX(), 2+(Integer)nw.getY()), te.getSpace(1,2).getNeighbor(Direction.NW));
        Assert.assertEquals("Neighbors of 1,2 are wrong.", te.getSpace(1+(Integer)n.getX(), 2+(Integer)n.getY()), te.getSpace(1,2).getNeighbor(Direction.N));
        Assert.assertEquals("Neighbors of 1,2 are wrong.", te.getSpace(1+(Integer)w.getX(), 2+(Integer)w.getY()), te.getSpace(1,2).getNeighbor(Direction.W));
        
        Assert.assertEquals("Neighbors of 1,0 are wrong.", te.getSpace(1+(Integer)e.getX(), 0+(Integer)e.getY()), te.getSpace(1,0).getNeighbor(Direction.E));
        Assert.assertEquals("Neighbors of 1,0 are wrong.", te.getSpace(1+(Integer)ne.getX(), 0+(Integer)ne.getY()), te.getSpace(1,0).getNeighbor(Direction.NE));
        Assert.assertEquals("Neighbors of 1,0 are wrong.", te.getSpace(1+(Integer)n.getX(), 0+(Integer)n.getY()), te.getSpace(1,0).getNeighbor(Direction.N));
        Assert.assertEquals("Neighbors of 1,0 are wrong.", te.getSpace(1+(Integer)se.getX(), 0+(Integer)se.getY()), te.getSpace(1,0).getNeighbor(Direction.SE));
        Assert.assertEquals("Neighbors of 1,0 are wrong.", te.getSpace(1+(Integer)s.getX(), 0+(Integer)s.getY()), te.getSpace(1,0).getNeighbor(Direction.S));
        
        Assert.assertEquals("Neighbors of 1,1 are wrong.", te.getSpace(1+(Integer)e.getX(), 1+(Integer)e.getY()), te.getSpace(1,1).getNeighbor(Direction.E));
        Assert.assertEquals("Neighbors of 1,1 are wrong.", te.getSpace(1+(Integer)ne.getX(), 1+(Integer)ne.getY()), te.getSpace(1,1).getNeighbor(Direction.NE));
        Assert.assertEquals("Neighbors of 1,1 are wrong.", te.getSpace(1+(Integer)n.getX(), 1+(Integer)n.getY()), te.getSpace(1,1).getNeighbor(Direction.N));
        Assert.assertEquals("Neighbors of 1,1 are wrong.", te.getSpace(1+(Integer)se.getX(), 1+(Integer)se.getY()), te.getSpace(1,1).getNeighbor(Direction.SE));
        Assert.assertEquals("Neighbors of 1,1 are wrong.", te.getSpace(1+(Integer)w.getX(), 1+(Integer)w.getY()), te.getSpace(1,1).getNeighbor(Direction.W));
        Assert.assertEquals("Neighbors of 1,1 are wrong.", te.getSpace(1+(Integer)nw.getX(), 1+(Integer)nw.getY()), te.getSpace(1,1).getNeighbor(Direction.NW));
        Assert.assertEquals("Neighbors of 1,1 are wrong.", te.getSpace(1+(Integer)sw.getX(), 1+(Integer)sw.getY()), te.getSpace(1,1).getNeighbor(Direction.SW));
        Assert.assertEquals("Neighbors of 1,1 are wrong.", te.getSpace(1+(Integer)s.getX(), 1+(Integer)s.getY()), te.getSpace(1,1).getNeighbor(Direction.S));
    }
    
    public Object[] convertListToArray(List l){
        Object[] r = new Object[l.size()];
        ListIterator litr = l.listIterator(0);
        while(litr.hasNext()){
            r[l.indexOf(litr.getCurrent())] = litr.getCurrent();
            litr.next();
        }
        return r;
    }
    
    public Integer[] trimNulls(Integer[] in){
        int c = 0;
        for (Integer a : in)
            if (a != null)
                c++;
        Integer[] r = new Integer[c];
        for (int i = 0; i < c; i++)
            r[i] = in[i];
        return r;
    }
}
