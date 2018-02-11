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
        
        
        CommonElementsBS u = new CommonElementsBS();
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
        
        CommonElements seq  = new CommonElements();
        CommonElementsBS bs = new CommonElementsBS();
        
        Comparable[] seqResults = seq.findCommonElements(largeTest);
        Comparable[] bsResults = bs.findCommonElements(largeTestCopy);
        
        System.out.println("Here are the BS results: " + bs.getComparisons());
        System.out.println(seqResults.length);
        if (seqResults.length <6)
            for (Comparable c : seqResults)
                System.out.println(c);
        
        System.out.println("Here are the seq results: " + seq.getComparisons());
        System.out.println(bsResults.length);
        if (bsResults.length < 6)
            for (Comparable c : bsResults)
                System.out.println(c);
        
        
        Statistics computation = new Statistics(largeTest[7]);
        System.out.println(computation.getVariance());
        
        int test = 4;
        System.out.println(test + ": " + ++test);
        System.out.println(test + ": " + --test);
        
    }
    
}
