package commonelements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author nathan
 */
public class CommonElementsOLD {
    private int comparisons = 0;
    private Comparable[][] collections;

    
    public int getComparisons() {
        return comparisons;
    }
    
    public Comparable[] findCommonElements(Comparable[][] collections){
        this.collections = collections;
        sortCollections();
        
        Comparable[] trimmedResult = trimNulls(findCommonElementsRecursively(1, collections[0]));
//        System.out.println(comparisons);
//        if (comparisons > 152905)
//            writeToFile();
        return trimmedResult;
    }
    
    // Recursive function that does the actual computations.
    private Comparable[] findCommonElementsRecursively(int indexOfNextArray, Comparable[] commonalities){
        // BASE CASE:
        // indexOfNextArray runs from 1 to length of collections.
        // if indexOfNextArray is equal to the length of collections, then we are done.
        if (indexOfNextArray == collections.length)
            return commonalities;
        
        // new container to store working commonalities
        Comparable[] revisedCommonalities = new Comparable[commonalities.length];
        
        // keep track of where to add common element to revisedCommonalities
        int currentRevisedCommonalityIndex = 0;
        
        // keep track of where we are in the next array's elements.
        int nextArrayCurrentElementIndex = 0;
        
        // iterate through current array of potential commonalities
        for (int n = 0; n < commonalities.length; n++){
            
            // if we've reached a null element in commonalities, we've run out of commonalities to check
            // thus we are done checking for commonalities, break and move on to next array 
            if (commonalities[ n ] == null || 
                    
                // If we've reached the end of the next array we're examining for commonalities,
                // then we're done looking for commonalities as well, break and move on.
                nextArrayCurrentElementIndex == collections[ indexOfNextArray ].length - 1)
                break;
            
            comparisons++;
            int compResult = commonalities[n].compareTo(collections[ indexOfNextArray ][ nextArrayCurrentElementIndex ]);
            
            // if commonality is the same as the current element in next array,
            // then add element to revisedCommonalities
            if (compResult == 0){
                revisedCommonalities[ currentRevisedCommonalityIndex ] = commonalities[ n ];
                currentRevisedCommonalityIndex++; //move to next slot in revisedCommonalities
                nextArrayCurrentElementIndex++;   //move to next element in nextArray.
                
            // if commonality is bigger than current element in next array,
            // then move on to next element in next array, but keep the 
            // current commonality that we're querying with.
            } else if (compResult > 0){
                nextArrayCurrentElementIndex++;  //move to next element in nextArray
                n--;                             //stay on this commonality.
            }  
        }
        
        // TAIL RECURSION
        return findCommonElementsRecursively(++indexOfNextArray, revisedCommonalities);
    }
    
    private Comparable[] trimNulls(Comparable[] in){
        int elementCount = 0;
        for (Comparable c : in){
            if (c != null)
                elementCount++;
            else
                break;
        }
        Comparable[] trimmed = Arrays.copyOf(in, elementCount);
        return trimmed;
    }
    
    
    // Comparator for sorting collections in ascending order of length
    private static class ArrayLengthComparator implements Comparator<Comparable[]>{

        @Override
        public int compare(Comparable[] o1, Comparable[] o2) {
            return o1.length - o2.length;
        }
        
    } 
    
    private void sortCollections(){
        // sort each collection in ascending order
        for (Comparable[] c : collections){
            Arrays.sort(c);
        }
        
        // sort array of collections in ascending order of array length
        Arrays.sort(collections, new ArrayLengthComparator());
    }
    
//    private void writeToFile(){
//        try{
//            FileWriter f = new FileWriter("/home/nathan/Documents/output.csv");
//            for (Comparable[] collection : collections) {
//                for (Comparable collection1 : collection) {
//                    f.write(collection1 + ",");
//                }
//                f.write('\n');
//            }
//        } catch(IOException e){System.out.println(e);}
//        
//        
//    }
    
    
}
