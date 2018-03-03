/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.simulation;

/**
 *
 * @author nathan
 */
public class Simulation {
    private static int turn = 0;
    private static boolean running = true;
    
    public static int getTurn(){
        return turn;
    }
    
    public static void incrementTurn(){
        turn++;
    }
    
    public static void end(){
        running = !running;
    }
}
