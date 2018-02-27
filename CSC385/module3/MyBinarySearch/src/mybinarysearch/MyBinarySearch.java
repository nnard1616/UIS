package mybinarysearch;

/**
 *
 * @author Nathan Nard
 */
public class MyBinarySearch {
    public int binarySearch(Comparable[] objArray, Comparable searchObj){
        int low = 0;
        int high = objArray.length - 1;
        int mid = 0;
        
        // if array is empty
        if (objArray.length == 0)
            return 0;
        
        // if query is smaller than first item in array
        if (searchObj.compareTo(objArray[0]) < 0) 
            return 0;
        
        // if query is greater than last item in array
        if (searchObj.compareTo( objArray[objArray.length - 1] ) > 0)
            return objArray.length;
        
        // query is between the bounds, see if query is in array
        while (low <= high){
            mid = (low + high)/2;
            
            if (objArray[mid].compareTo(searchObj) < 0)
                low = mid + 1;
            else if (objArray[mid].compareTo(searchObj) > 0)
                high = mid - 1;
            else
                return mid;
        }
        
        // query was not present in array, and is between the bounds.
        return (high+low)/2+1;
    }
}
