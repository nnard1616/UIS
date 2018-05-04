package dataStructures;

/**
 *	Class OrderedLinkedList.
 *
 *	This class functions as a linked list, but ensures items are stored in ascending order.
 *
 */
public class OrderedLinkedList
{
	
	/**************************************************************************
	 * Constants
	 *************************************************************************/
	
	/** return value for unsuccessful searches */
	private static final OrderedListNode NOT_FOUND = null;
	

	/**************************************************************************
	 * Attributes
	 *************************************************************************/

	/** current number of items in list */
	private int theSize;
	
	/** reference to list header node */
	private OrderedListNode head;
	
	/** reference to list tail node */
	private OrderedListNode tail;
	
	/** current number of modifications to list */
	private int modCount;
	
	
	/**************************************************************************
	 * Constructors
	 *************************************************************************/

	
	/**
	 *	Create an instance of OrderedLinkedList.
	 *
	 */
	public OrderedLinkedList()
	{
		// empty this OrderedLinkedList
		clear();
	}
	
	
	/**************************************************************************
	 * Methods
	 *************************************************************************/


	/*
	 *	Add the specified theItem to this OrderedLinkedList.
	 *
	 *	@param	obj		the theItem to be added
	 */
	public boolean add(Comparable obj)
	{
            OrderedListNode pointer = head.next;
            OrderedListNode newItem = new OrderedListNode(obj, null, null);
            
            //while pointer is not the tail and pointer is less than input object
            while (pointer.next != null && pointer.theItem.compareTo(obj) < 0)
                
                //update pointer
                pointer = pointer.next;
            
            //set input object's prev to pointer's prev
            newItem.prev = pointer.prev;
            
            //set input object's next to pointer
            newItem.next = pointer;
            
            //set pointer's prev's next to input object
            pointer.prev.next = newItem;
            
            //set pointer's prev to input object
            pointer.prev = newItem;
            
            //increase size counter
            theSize++;
            
            //increase mod counter
            modCount++;
            
            return true;
	}
	
	/*
	 *	Remove the first occurrence of the specified theItem from this OrderedLinkedList.
	 *
	 *	@param	obj		the theItem to be removed
	 */
	public boolean remove(Comparable obj)
	{
            OrderedListNode pointer = head.next;
            
            //while pointer is not the tail and pointer is less than or equal to input object
            while (pointer.next != null && pointer.theItem.compareTo(obj) <= 0){
            
                //if pointer equals input object
                if (pointer.theItem.compareTo(obj) == 0){
                
                    //set pointer's prev's next to pointer's next
                    pointer.prev.next = pointer.next;
            
                    //set pointer's next's prev to pointer's prev
                    pointer.next.prev = pointer.prev;
            
                    //decrement list size
                    theSize--;
                    
                    //increase mod counter;
                    modCount++;
                    
                    return true;
                }
                    
                //update pointer
                pointer = pointer.next;
            }
            
            return false;
	}
	
	/**
	 *	Empty this OrderedLinkedList.
	 */
	public void clear()
	{
                // reset header node
                head = new OrderedListNode("HEAD", null, null);

                // reset tail node
                tail = new OrderedListNode("TAIL", head, null);

                // header references tail in an empty LinkedList
                head.next = tail;

                // reset size to 0
                theSize = 0;

                // emptying list counts as a modification
                modCount++;
	}


	/**
	 *	Return true if this OrderedLinkedList contains 0 items.
	 */
	public boolean isEmpty()
	{
		return theSize == 0;
	}


	/**
	 *	Return the number of items in this OrderedLinkedList.
	 */
	public int size()
	{
		return theSize;
	}
	

	/*	
	 *	Return a String representation of this OrderedLinkedList.
	 *
	 *	(non-Javadoc)
     *	@see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
    	String s = "";
    	
    	OrderedListNode currentNode = head.next;
    	
    	while (currentNode != tail)
    	{
    		s += currentNode.theItem.toString();
    		
    		if (currentNode.next != tail)
    		{
    			s += ", ";
    		}
    		
    		currentNode = currentNode.next;
    	}
    	
    	return s;
    }

	
	/**************************************************************************
	 * Inner Classes
	 *************************************************************************/
	

	/**
	 *	Nested class OrderedListNode.
	 *
	 *	Encapsulates the fundamental building block of an OrderedLinkedList
	contains a data theItem, and references to both the next and previous nodes
	in the list
	 */
    
        private static class OrderedListNode{
            Comparable theItem;
            OrderedListNode prev;
            OrderedListNode next;

            OrderedListNode(Comparable item, OrderedListNode prev, OrderedListNode next){
                this.theItem = item;
                this.prev = prev;
                this.next = next;
            }
        }
}
