package commonelements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author nathan
 */
public class CommonElementsBS {
    private int comparisons = 0;
    private Comparable[][] collections;

    
    public int getComparisons() {
        return comparisons;
    }
    
    public Comparable[] findCommonElements(Comparable[][] collections){
        this.collections = collections;
        sortCollections();
        
        Comparable[] trimmedResult = trimNulls(findCommonElementsRecursively(1, collections[0]));
        return trimmedResult;
    }
    
    // Recursive function that does the actual computations.
    private Comparable[] findCommonElementsRecursively(int indexOfNextArray, Comparable[] commonalities){
        // BASE CASE:
        // indexOfNextArray runs from 1 to length of collections.
        // if indexOfNextArray is equal to the length of collections, then we are done.
        // or if commonalities has zero length, (shortest array in collections was empty),
        // then there are no commonalities and we're done.
        if (indexOfNextArray == collections.length || commonalities.length == 0)
            return commonalities;
        
        // new container to store working commonalities
        Comparable[] revisedCommonalities = new Comparable[commonalities.length];
        
        // keep track of where to add common element to revisedCommonalities
        int currentRevisedCommonalityIndex = 0;
        
        // keep track of where we are in the next array's elements.
        int nextArrayCurrentElementIndex = 0;
        
        Comparable[] nextArray = collections[indexOfNextArray];
        
        
        
        // iterate through current array of potential commonalities
        for (int n = 0; n < commonalities.length; n++){
            // if we've reached a null element in commonalities, we've run out of commonalities to check
            // thus we are done checking for commonalities, break and move on to next array 
            if (commonalities[ n ] == null || 
                    
                // If we've reached the end of the next array we're examining for commonalities,
                // then we're done looking for commonalities as well, break and move on to next array.
                nextArrayCurrentElementIndex == nextArray.length)
                break;
            
            comparisons++;
            int compResult = commonalities[n].compareTo(nextArray[ nextArrayCurrentElementIndex ]);
            
            int commonalityCount = countNotNulls(commonalities);
            
            int remainingToSearchThrough = (nextArray.length - nextArrayCurrentElementIndex);
            
            int remainingToSearchFor =(commonalityCount - n);
            
            // Ratio of elements left to search through in next array to number of commonalities left to search for
            // This ratio is used to decide when to use sequential over binary search.
            int arrayElementsToCommonalityRatio = remainingToSearchThrough /  remainingToSearchFor;
            
            
            // Threshold for sequential search over binary search.
            int cutoff = 7;

            // if commonality is smaller than current element in next array, then move on to the next
            // commonality to check.
            if (compResult < 0) 
                continue;
            
            // if commonality is the same as the current element in next array,
            // then add element to revisedCommonalities
            else if (compResult == 0){
                revisedCommonalities[ currentRevisedCommonalityIndex ] = commonalities[ n ];
                currentRevisedCommonalityIndex++; //move to next slot in revisedCommonalities
                nextArrayCurrentElementIndex++;   //move to next element in nextArray.
            
            // if commonality is bigger than current element in next array,
            // then search for commonality in next array, 
            } else if (compResult > 0){
                
                //Cutoff for deciding between sequential and binary search.
                if (arrayElementsToCommonalityRatio> cutoff){ 
                    //sequential search
                    nextArrayCurrentElementIndex++;  //move to next element in nextArray
                    n--;                             //stay on this commonality.
                } else{
                    //binary search
                    nextArrayCurrentElementIndex = binarySearch(nextArray, commonalities[n], nextArrayCurrentElementIndex);
                    
                    //negative return value means commonality query is not in the array
                    if (nextArrayCurrentElementIndex < 0){
                        nextArrayCurrentElementIndex *= -1; //set back to positive, move to next commonality 
                        
                    //positive return value means commonality query is in the array
                    } else{
                        revisedCommonalities[ currentRevisedCommonalityIndex ] = commonalities[ n ];
                        currentRevisedCommonalityIndex++; //move to next slot in revisedCommonalities
                        nextArrayCurrentElementIndex++;   //move to next element in nextArray.
                    }
                    //Note:  return value will never be 0, that case is handled earlier.
                }
            }
        }
        
        // TAIL RECURSION
        return findCommonElementsRecursively(++indexOfNextArray, revisedCommonalities);
    }
    
    private Comparable[] trimNulls(Comparable[] in){
        Comparable[] trimmed = Arrays.copyOf(in, countNotNulls(in));
        return trimmed;
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
    
    private Integer binarySearch(Comparable[] objArray, Comparable searchObj, Integer low){
        int high = objArray.length-1;
        int mid = (low + high)/2;
        
        // query is between the bounds, see if query is in array
        while (low <= high){
            mid = (low + high)/2;
            
            
            
            comparisons++;
            int compResult = objArray[mid].compareTo(searchObj);
            if (compResult < 0)
                low = mid + 1;
            else if (compResult > 0)
                high = mid - 1;
            else if (low != mid){ //necessary for finding first occurrence
                high = mid;
//                do{
//                    comparisons++;
//                    compResult = objArray[--mid].compareTo(searchObj);
//                } while (compResult == 0);
//                return ++mid;
            }
            else
                return mid;
        }
        
        // query was not present in array, and is between the bounds.
        //    even length array, add 1 to result
        //    odd  length array, return result as is
        return (objArray.length-low % 2 == 0) ? -1*(++mid) : -1*mid;
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
