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
        
        Integer[] avg1         = {0,0,0,0,1,3,3,4,4,4};
        Integer[] avg2         = {0,0,1,1,1,2,2,3,4,4};
        Integer[] avg3         = {0,1,2,2,2,3,3,3,3,3};
        Integer[] avg4         = {0,1,1,1,2,2,3,3,3,3};
        Integer[] avg5         = {0,0,1,1,1,2,2,3,4,4};
        
        Integer[] worst1       = {0,0,0,0,2,2,3,3,4,4};
        Integer[] worst2       = {0,0,0,0,1,2,3,4,4,4};
        Integer[] worst3       = {0,0,0,1,1,2,2,3,4,4};
        Integer[] worst4       = {0,1,2,2,2,3,3,3,4,4};
        Integer[] worst5       = {0,0,0,0,0,1,2,3,3,3};
        
        Integer[] worstJagged1 = {0,0,1,2,2,2,3,4,5,7};
        Integer[] worstJagged2 = {0,1,1,2,3,4,4,5,5,6,6,7,7};
        Integer[] worstJagged3 = {0,0,0,1,1,1,2,2,3,3,3,3,4,5,5,7};
        Integer[] worstJagged4 = {0,0,0,1,1,1,1,2,3,4,4,4,5,6,6,6,6,7,7};
        Integer[] worstJagged5 = {0,0,0,1,1,1,1,1,1,1,2,2,2,2,2,3,4,4,5,5,6,6};
        
        Integer[][] avgTest         = {avg1, avg2, avg3, avg4, avg5};
        Integer[][] worstTest       = {worst1, worst2, worst3, worst4, worst5};
        Integer[][] worstJaggedTest = {worstJagged1, worstJagged2, worstJagged3, worstJagged4, worstJagged5};
        Integer[][] emptyTest       = {};
        Integer[][] oneEmptyTest    = {avg1, {}, avg2, avg3, avg4, avg5};
        
        CommonElements seq  = new CommonElements();
        
        Comparable[] seqResults = seq.findCommonElements(largeTest);
        
        System.out.println("Here are the seq comparisons: " + seq.getComparisons());
        System.out.println("Here are the seq number of results: " + seqResults.length);
        
        if (seq.getComparisons() > 40)
            seq.writeToFile();
        if (seqResults.length <15)
            for (Comparable c : seqResults)
                System.out.println(c);
        
    }
    
}
