/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.ants;

/**
 *
 * @author nathan
 */
public enum Lifespan {
    QUEEN(73000),
    OTHER(3650);
    
    private final int ls;
    Lifespan(int ls) { this.ls = ls;}
    public int getValue() { return ls; }
}
