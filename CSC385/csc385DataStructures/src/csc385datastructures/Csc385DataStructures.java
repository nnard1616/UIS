/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc385datastructures;

import dataStructures.ArrayQueue;
import dataStructures.HashFunction;

/**
 *
 * @author nathan
 */
public class Csc385DataStructures {
    static String[] values =  new String[] {"Patriots",
                                            "Steelers",
                                            "Chargers",
                                            "Texans",
                                            "Packers",
                                            "49ers",
                                            "Saints",
                                            "Giants"};
    static Integer[] hashes = new Integer[]    {1342415383,
                                                700056533,
                                                330628742,
                                                532139483,
                                                217142585,
                                                2112979549,
                                                207265348,
                                                1631149803};
    
    private static class IdentityHashFunction extends HashFunction{

        @Override
        public int hashValue(Object key) {
            if (key.getClass() != Integer.class)
                throw new IllegalArgumentException("IdentityHashFunction only receives Integer objects");   
            return (Integer)key;
        }
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayQueue aq = new ArrayQueue();
        aq.enqueue("Adrian Peterson");
        aq.dequeue();
        aq.enqueue("Devonta Freeman");
        aq.enqueue("Chris Johnson");
        aq.dequeue();
        aq.dequeue();
        aq.enqueue("Todd Gurley");
        aq.enqueue("Doug Martin");
        aq.enqueue("Latavius Murray");
        aq.enqueue("Frank Gore");
        aq.dequeue();
        aq.enqueue("Mark Ingram");
        aq.dequeue();
        aq.enqueue("Jonathan Stewart");
        aq.enqueue("Justin Forsett");
        aq.dequeue();
        aq.enqueue("Le'Veon Bell");
        Object[] arr = aq.getTheItems();
        
        for (Object o : arr)
            System.out.println(o);
        System.out.println(arr.length);
        
        System.out.println(aq.getFront());
        System.out.println("");
        while (!aq.isEmpty()){
            System.out.println(aq.dequeue());
        }
    }
    
}
