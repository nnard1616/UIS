package commonelements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author nathan
 */
public class CommonElements {
    private int            comparisons;
    private Comparable[][] collections;

    /**Return the number of comparisons made.
     * @return      Integer representing the number of comparisons made.*/
    public int getComparisons() {
        return comparisons;
    }
    
    //User side findCommonElements
    /**Returns an array of elements common to a series of arrays
     *  @param  collections          2D Array of arrays to search for common 
     *                               elements within.
     *  @return                      Returns array of Comparable elements that 
     *                               are common between all of the arrays.
     */
    public Comparable[] findCommonElements(Comparable[][] collections){
        this.comparisons = 0;
        this.collections = collections;
        
        sortCollections();
        
        // if collections has stuff, do work
        if (collections.length >0)
            return trimNulls(findCommonElementsRecursively(1, collections[0]));
        
        // otherwise, nothing to do. Return empty array.
        else
            return new Comparable[0];
    }
    
    /**Write current collections to a csv file.*/
    public void writeToFile(){
        try (FileWriter f = new FileWriter("/home/nathan/Documents/output.csv")) {
            for (Comparable[] collection : collections) {
                for (Comparable c : collection) {
                    f.write(c.toString() + ",");
                }
                f.write('\n');
            }
        }catch(IOException e){System.out.println(e);}
    }
    
    //Privates
    
    // Backend recursive function that finds common elements.
    private Comparable[] findCommonElementsRecursively(int indexOfNextArray, Comparable[] query){
        // BASE CASE:
        // indexOfNextArray runs from 1 to length of collections.
        // if indexOfNextArray is equal to the length of collections, then we are done.
        // or if query has zero length, (shortest array in collections was empty),
        // then there are no commons and we're done.
        if (indexOfNextArray == collections.length || query.length == 0)
            return query;
        
        // new container to store next working query
        Comparable[] revisedQuery = new Comparable[query.length];
        
        // keep track of where to add common element to revisedQuery
        int currentRevisedCommonalityIndex = 0;
        
        // keep track of where we are in the next array's elements.
        int nextArrayCurrentElementIndex = 0;
        
        // iterate through query
        for (int n = 0; n < query.length; n++){
            
            // if we've reached a null element in query, we've run out of commons to check for,
            // thus we are done checking for commons, break and move on to next array 
            if (query[ n ] == null || 
                    
                // If we've reached the end of the next array we're examining for commons,
                // then we're done looking for commons as well, break and move on.
                nextArrayCurrentElementIndex == collections[ indexOfNextArray ].length)
                break;
            
            comparisons++;
            int compResult = query[n].compareTo(collections[ indexOfNextArray ][ nextArrayCurrentElementIndex ]);
            
            // if query element is the same as the element in next array,
            // then add element to revisedQuery
            if (compResult == 0){
                revisedQuery[ currentRevisedCommonalityIndex ] = query[ n ];
                currentRevisedCommonalityIndex++; //move to next slot in revisedQuery
                nextArrayCurrentElementIndex++;   //move to next element in nextArray.
                
            // if commonality is bigger than element in next array,
            // then move on to next element in next array, but keep the 
            // current commonality that we're querying with.
            } else if (compResult > 0){
                nextArrayCurrentElementIndex++;  //move to next element in nextArray
                n--;                             //stay on this commonality.
            }  
        }
        
        // TAIL RECURSION
        return findCommonElementsRecursively(++indexOfNextArray, revisedQuery);
    }
    
    private Comparable[] trimNulls(Comparable[] in){
        return Arrays.copyOf(in, countNotNulls(in));
    }
    
    private int countNotNulls(Comparable[] in){
        int elementCount = 0;
        for (Comparable c : in){
            if (c != null)
                elementCount++;
            else
                break;
        }
        return elementCount;
    }
    
    private void sortCollections(){
        // sort each collection in ascending order
        for (Comparable[] c : collections){
            Arrays.sort(c);
        }
        
        // sort array of collections in ascending order of array length
        Arrays.sort(collections, new ArrayLengthComparator());
    }
    
    // Comparator for sorting collections in ascending order of length
    private static class ArrayLengthComparator implements Comparator<Comparable[]>{

        @Override
        public int compare(Comparable[] o1, Comparable[] o2) {
            return o1.length - o2.length;
        }
    } 
}
