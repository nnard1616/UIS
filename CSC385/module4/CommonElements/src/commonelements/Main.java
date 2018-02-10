/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonelements;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author nathan
 */
public class Main{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Random randomizer = new Random();
        
        String[] fruits1 = new String[] {"banana","apple","pear",
                                         "banana","pomegranate","pineapple",
                                         "cherry","jujube","cherry","orange"};
        String[] fruits2 = new String[] {"quince","raspberry","banana",
                                         "lemon","apple","banana","cherry",
                                         "blueberry","jujube","mango"};
        String[] fruits3 = new String[] {"plum","pomegranate","lime",
                                         "banana","jujube","blueberry",
                                         "apple","cherry","grape","banana"};
        String[][] fruits = new String[][] {fruits1, fruits2, fruits3};
        
        
        CommonElements u = new CommonElements();
        Comparable[] result = u.findCommonElements(fruits);
        
        for (Comparable r : result)
            System.out.println(r);
        System.out.println(u.getComparisons());
        
        
        int N = 1000;
        int K = 100;
        int P = 200; //range of numbers to randomize over
        
        Integer[][] largeTest = new Integer[K][];
        Integer[][] largeTestCopy = new Integer[K][];
        for (int k = 0; k<K; k++){
            Integer[] collection = new Integer[N];
            for (int n = 0; n < (N); n++){
                collection[n] = randomizer.nextInt(P);
            }
            largeTest[k] = collection;
            largeTestCopy[k] = collection.clone();
        }
        
        CommonElementsOLD old = new CommonElementsOLD();
        CommonElements neo = new CommonElements();
        
        Comparable[] oldResults = old.findCommonElements(largeTest);
        Comparable[] newResults = neo.findCommonElements(largeTestCopy);
        
        System.out.println("Here are the old results: " + old.getComparisons());
        System.out.println(oldResults.length);
        if (oldResults.length <6)
            for (Comparable c : oldResults)
                System.out.println(c);
        
        System.out.println("Here are the new results: " + neo.getComparisons());
        System.out.println(newResults.length);
        if (newResults.length < 6)
            for (Comparable c : newResults)
                System.out.println(c);
        
        
        Statistics computation = new Statistics(largeTest[7]);
        System.out.println(computation.getVariance());
        
    }
    
}
