/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonelementstest;


import org.junit.Test;
import org.junit.Assert;
import commonelements.CommonElements;

/**
 *
 * @author nathan
 */
public class CommonElementsTest {
    
    
    private final Integer[] noCommons1 = {17,43,53,55,70,92,110,130,188,196};
    private final Integer[] noCommons2 = {11,25,46,56,76,113,123,128,130,196};
    private final Integer[] noCommons3 = {59,66,71,83,87,88,97,117,185,197};
    private final Integer[] noCommons4 = {6,36,47,48,52,58,73,102,138,162};
    private final Integer[] noCommons5 = {11,14,18,23,26,43,112,148,185,186};

    private final Integer[] avg1       = {0,0,0,0,1,3,3,4,4,4};
    private final Integer[] avg2       = {0,0,1,1,1,2,2,3,4,4};
    private final Integer[] avg3       = {0,1,2,2,2,3,3,3,3,3};
    private final Integer[] avg4       = {0,1,1,1,2,2,3,3,3,3};
    private final Integer[] avg5       = {0,0,1,1,1,2,2,3,4,4};

    private final Integer[] bad1       = {0,0,0,0,2,2,3,3,4,4};
    private final Integer[] bad2       = {0,0,0,0,1,2,3,4,4,4};
    private final Integer[] bad3       = {0,0,0,1,1,2,2,3,4,4};
    private final Integer[] bad4       = {0,1,2,2,2,3,3,3,4,4};
    private final Integer[] bad5       = {0,0,0,0,0,1,2,3,3,3};

    private final Integer[] badJagged1 = {0,0,1,2,2,2,3,4,5,7};
    private final Integer[] badJagged2 = {0,1,1,2,3,4,4,5,5,6,6,7,7};
    private final Integer[] badJagged3 = {0,0,0,1,1,1,2,2,3,3,3,3,4,5,5,7};
    private final Integer[] badJagged4 = {0,0,0,1,1,1,1,2,3,4,4,4,5,6,6,6,6,7,7};
    private final Integer[] badJagged5 = {0,0,0,1,1,1,1,1,1,1,2,2,2,2,2,3,4,4,5,5,6,6};
    
    private final Integer[] worst1     = {0,2,4,6,8};
    private final Integer[] worst2     = {1,3,5,7,9};

    private final Integer[][] avgColl         = {avg1, avg2, avg3, avg4, avg5};
    private final Integer[][] badColl         = {bad1, bad2, bad3, bad4, bad5};
    private final Integer[][] badJaggedColl   = {badJagged1, badJagged2, badJagged3, badJagged4, badJagged5};
    private final Integer[][] emptyColl       = {};
    private final Integer[][] oneEmptyColl    = {avg1, {}, avg2, avg3, avg4, avg5};
    private final Integer[][] worstColl       = {worst1, worst2};
    private final Integer[][] noCommonsColl   = {noCommons1, noCommons2, noCommons3, noCommons4, noCommons5};
    private final Integer[][] oneColl         = {{0,1,1,2}};
    private final Integer[][] duplicateColl   = {{0,0,1,2,3},{0,0,1,1,2},{0,0,1,2,2}};
    
    private final CommonElements ce           = new CommonElements();
    
    @Test
    public void averageCollectionOfEqualSizedCollectionsTest() {
        //expected result
        Integer[] expected = new Integer[] {0,1,3};
        
        //actual result
        Comparable[] actual   = ce.findCommonElements(avgColl);
        
        //comparison maximum
        Integer compMax = countElements(avgColl);
        
        Assert.assertArrayEquals("Expected common elements were not obtained.", expected, actual);
        Assert.assertEquals("Number of comparisons was too big." , 1, compMax.compareTo(ce.getComparisons()));
        
        System.out.println("Average Collections Comparison count: " + ce.getComparisons());
    }
    
    @Test
    public void badCollectionOfEqualSizedCollectionsTest() {
        //expected result
        Integer[] expected = new Integer[] {0,2,3};
        
        //actual result
        Comparable[] actual   = ce.findCommonElements(badColl);
        
        //comparison maximum
        Integer compMax = countElements(badColl);
        
        Assert.assertArrayEquals("Expected common elements were not obtained.", expected, actual);
        Assert.assertEquals("Number of comparisons was too big.", 1, compMax.compareTo(ce.getComparisons()));
        System.out.println("Bad Collections Comparison count: " + ce.getComparisons());
    }
    
    @Test
    public void badCollectionOfVaryingSizedCollectionsTest() {
        //expected result
        Integer[] expected = new Integer[] {0,1,2,3,4,5};
        
        //actual result
        Comparable[] actual   = ce.findCommonElements(badJaggedColl);
        
        //comparison maximum
        Integer compMax = countElements(badJaggedColl);
        
        Assert.assertArrayEquals("Expected common elements were not obtained.", expected, actual);
        Assert.assertEquals("Number of comparisons was too big.", 1, compMax.compareTo(ce.getComparisons()));
        System.out.println("Bad Jagged Collections Comparison count: " + ce.getComparisons());
    }
    
    @Test
    public void emptyCollectionsTest() {
        //expected result
        Integer[] expected = new Integer[] {};
        
        //actual result
        Comparable[] actual   = ce.findCommonElements(emptyColl);
        
        //comparison maximum
        Integer compMax = countElements(emptyColl);
        
        Assert.assertArrayEquals("Expected common elements were not obtained.", expected, actual);
        Assert.assertEquals("Number of comparisons was too big.", 0, compMax.compareTo(ce.getComparisons()));
        System.out.println("Empty collections Comparison count: " + ce.getComparisons());
    }
    
    @Test
    public void oneEmptyCollectionTest() {
        //expected result
        Integer[] expected = new Integer[] {};
        
        //actual result
        Comparable[] actual   = ce.findCommonElements(oneEmptyColl);
        
        //comparison maximum
        Integer compMax = countElements(oneEmptyColl);
        
        Assert.assertArrayEquals("Expected common elements were not obtained.", expected, actual);
        Assert.assertEquals("Number of comparisons was too big.", 1, compMax.compareTo(ce.getComparisons()));
        System.out.println("One empty collection Comparison count: " + ce.getComparisons());
    }
    
    @Test
    public void worstCollectionsTest() {
        //expected result
        Integer[] expected = new Integer[] {};
        
        //actual result
        Comparable[] actual   = ce.findCommonElements(worstColl);
        
        //comparison maximum
        Integer compMax = countElements(worstColl);
        
        Assert.assertArrayEquals("Expected common elements were not obtained.", expected, actual);
        Assert.assertEquals("Number of comparisons was too big.", 1, compMax.compareTo(ce.getComparisons()));
        System.out.println("Worst Collections Comparison count: " + ce.getComparisons());
    }
    
    @Test
    public void noCommonsAllFullCollectionsTest() {
        //expected result
        Integer[] expected = new Integer[] {};
        
        //actual result
        Comparable[] actual   = ce.findCommonElements(noCommonsColl);
        
        //comparison maximum
        Integer compMax = countElements(noCommonsColl);
        
        Assert.assertArrayEquals("Expected common elements were not obtained.", expected, actual);
        Assert.assertEquals("Number of comparisons was too big.", 1, compMax.compareTo(ce.getComparisons()));
        System.out.println("No commons, no empty Collections Comparison count: " + ce.getComparisons());
    }
    
    @Test
    public void oneCollectionTest() {
        //expected result
        Integer[] expected = new Integer[] {0,1,1,2};
        
        //actual result
        Comparable[] actual   = ce.findCommonElements(oneColl);
        
        //comparison maximum
        Integer compMax = countElements(oneColl);
        
        Assert.assertArrayEquals("Expected common elements were not obtained.", expected, actual);
        Assert.assertEquals("Number of comparisons was too big.", 1, compMax.compareTo(ce.getComparisons()));
        System.out.println("One collection Comparison count: " + ce.getComparisons());
    }
    
    @Test
    public void duplicateCommonsTest() {
        //expected result
        Integer[] expected = new Integer[] {0,0,1,2};
        
        //actual result
        Comparable[] actual   = ce.findCommonElements(duplicateColl);
        
        //comparison maximum
        Integer compMax = countElements(duplicateColl);
        
        Assert.assertArrayEquals("Expected common elements were not obtained.", expected, actual);
        Assert.assertEquals("Number of comparisons was too big.", 1, compMax.compareTo(ce.getComparisons()));
        System.out.println("Duplicate commons Collections Comparison count: " + ce.getComparisons());
    }
    
    
    private int countElements(Comparable[][] input){
        int compMax = 0;
        for (Comparable[] c : input)
            for (Comparable cc: c)
                compMax++;
        return compMax;
    }

}
