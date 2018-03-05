/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author nathan
 */
public class AntColonySimulation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Queue<Integer> fuck = new ArrayBlockingQueue<>(10);
        if (!fuck.offer(10)){
            System.out.println("shit");
        }
        System.out.println(fuck.peek());
    }
    
}
