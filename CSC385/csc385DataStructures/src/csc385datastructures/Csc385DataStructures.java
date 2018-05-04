/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc385datastructures;

import dataStructures.AVLTree;
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
        AVLTree t = new AVLTree();
        t.add(5);
        t.add(5);
    }
    
}
