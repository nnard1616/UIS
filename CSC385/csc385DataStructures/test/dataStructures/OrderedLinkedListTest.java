/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataStructures;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nathan
 */
public class OrderedLinkedListTest {
    
    public OrderedLinkedListTest() {
    }

    /**
     * Test of add method, of class OrderedLinkedList.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Integer i = 1;
        OrderedLinkedList instance = new OrderedLinkedList();
        
        assertEquals("add should have returned true", true, instance.add(i));
        assertEquals("orderedlinkedlist should have size of 1", 1, instance.size());
        assertEquals("orderedlinkedlist does not contain what is expected", "1", instance.toString());
        
        instance.add(10);
        instance.add(7);
        assertEquals("orderedlinkedlist did not add items in proper order", "1, 7, 10", instance.toString());
        assertEquals("add should increase list size", 3, instance.size());
        
        instance.add(7);
        assertEquals("orderedlinkedlist did not add items in proper order", "1, 7, 7, 10", instance.toString());
        assertEquals("add should increase list size", 4, instance.size());
    }

    /**
     * Test of remove method, of class OrderedLinkedList.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        OrderedLinkedList instance = new OrderedLinkedList();
        
        instance.add(4);
        instance.add(7);
        instance.add(7);
        instance.add(18);
        
        assertEquals("orderedlinkedlist remove did not return true", true, instance.remove(7));
        assertEquals("orderedlinkedlist did not remove properly", "4, 7, 18", instance.toString());
        assertEquals("remove should decrease list size by 1", 3, instance.size());
        assertEquals("orderedlinkedlist remove should return false when object not present", false, instance.remove(10));
        
        instance.remove(18);
        instance.add(10);
        instance.add(1);
        assertEquals("orderedlinkedlist did not remove properly", "1, 4, 7, 10", instance.toString());
    }

    /**
     * Test of clear method, of class OrderedLinkedList.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        OrderedLinkedList instance = new OrderedLinkedList();
        
        instance.add(4);
        instance.add(5);
        
        instance.clear();
        
        assertEquals("orderedlinkedlist clear should result in empty list", "", instance.toString());
        assertEquals("orderedlinkedlist clear should result in empty list", true, instance.isEmpty());
        assertEquals("orderedlinkedlist clear should result in list size of 0", 0, instance.size());
    }

    /**
     * Test of isEmpty method, of class OrderedLinkedList.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        OrderedLinkedList instance = new OrderedLinkedList();
        boolean expResult = true;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
    }

    /**
     * Test of size method, of class OrderedLinkedList.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        OrderedLinkedList instance = new OrderedLinkedList();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class OrderedLinkedList.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        OrderedLinkedList instance = new OrderedLinkedList();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
