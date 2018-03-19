package mybinarysearchtest;

/**
 *
 * @author Nathan Nard
 */

import mybinarysearch.MyBinarySearch;
import org.junit.Assert;
import org.junit.Test;

public class MyBinarySearchTest {
    private final Integer[] odd     = new Integer[] {3, 5, 6, 8, 9};
    private final Integer[] even    = new Integer[] {3, 4, 5, 6, 8, 9};
    private final Integer[] rubOdd  = new Integer[] {3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23};
    private final Integer[] rubEven = new Integer[] {3, 5, 7, 9, 11, 13, 15, 17, 19, 21};
    private final Integer[] empty   = new Integer[] {};
    private final MyBinarySearch bs = new MyBinarySearch();
    
    @Test
    public void querySmallerThanLowerBoundShouldReturnZeroForEvenArray(){
        //Given
        Integer query = 2;
        
        //Then
        Assert.assertEquals("Query smaller than even numbered array's lower bound should return zero.", 
                            0, 
                            bs.binarySearch(even, query));
    }
    
    @Test
    public void querySmallerThanLowerBoundShouldReturnZeroForOddArray(){
        //Given
        Integer query = 2;
        
        //Then
        Assert.assertEquals("Query smaller than odd numbered array's lower bound should return zero.", 
                            0, 
                            bs.binarySearch(odd, query));
    }
    
    @Test
    public void queryEqualToLowerBoundShouldReturnZeroForEvenArray(){
        Assert.assertEquals("Query equal to the even numbered array's lower bound should return zero.", 
                            0, 
                            bs.binarySearch(even, even[0]));
    }
    
    @Test
    public void queryEqualToLowerBoundShouldReturnZeroForOddArray(){
        Assert.assertEquals("Query equal to the odd numbered array's lower bound should return zero.", 
                            0, 
                            bs.binarySearch(odd, odd[0]));
    }
    
    @Test
    public void queryLargerThanUpperBoundShouldReturnArrayLengthForEvenArray(){
        //Given
        Integer query = 10;
        
        //Then
        Assert.assertEquals("Query greater than even numbered array's upper bound should return array length.", 
                            even.length, 
                            bs.binarySearch(even, query));
    }
    
    @Test
    public void queryLargerThanUpperBoundShouldReturnArrayLengthForOddArray(){
        //Given
        Integer query = 10;
        
        //Then
        Assert.assertEquals("Query greater than odd numbered array's upper bound should return array length.", 
                            odd.length, 
                            bs.binarySearch(odd, query));
    }
    
    @Test
    public void queryEqualToUpperBoundShouldReturnLastIndexForEvenArray(){
        Assert.assertEquals("Query equal to even numbered array's upper bound should return last index.", 
                            even.length-1, 
                            bs.binarySearch(even, even[even.length-1]));
    }
    
    @Test
    public void queryEqualToUpperBoundShouldReturnLastIndexForOddArray(){
        Assert.assertEquals("Query equal to odd numbered array's upper bound should return last index.", 
                            odd.length-1, 
                            bs.binarySearch(odd, odd[odd.length-1]));
    }
    
    @Test
    public void nonExistentQueryBetweenLowerAndUpperBoundShouldReturnIntermediateForEvenArray(){
        //Given
        Integer query = 12;
        
        //Then 
        Assert.assertEquals("Non existent query should return the proper index for orderly insertion into even numbered array.", 
                            5, 
                            bs.binarySearch(rubEven, query));
    }
    
    @Test
    public void nonExistentQueryBetweenLowerAndUpperBoundShouldReturnIntermediateForOddArray(){
        //Given
        Integer query = 12;
        
        //Then 
        Assert.assertEquals("Non existent query should return the proper index for orderly insertion into odd numbered array.", 
                            5, 
                            bs.binarySearch(rubOdd, query));
    }
    
    @Test
    public void queryBetweenLowerAndUpperBoundShouldReturnIntermediateForEvenArray(){
        //Given
        Integer query = 8;
        
        //Then
        Assert.assertEquals("Query should return the proper index for orderly insertion into even numbered array.", 
                            4, 
                            bs.binarySearch(even, query));
    }
    
    @Test
    public void queryBetweenLowerAndUpperBoundShouldReturnIntermediateForOddArray(){
        //Given
        Integer query = 8;
        
        //Then
        Assert.assertEquals("Query should return the proper index for orderly insertion into odd numbered array.", 
                            3, 
                            bs.binarySearch(odd, query));
    }
    
    @Test
    public void queryOfEmptyListShouldReturnZero(){
        //Given
        Integer query = 5;
        
        //Then
        Assert.assertEquals("Binary Search should return 0 for an empty list.", 
                            0, 
                            bs.binarySearch(empty, query));
    }
}
