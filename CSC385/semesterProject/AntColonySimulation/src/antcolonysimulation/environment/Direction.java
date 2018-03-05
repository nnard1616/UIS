/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.environment;

/**
 *
 * @author nathan
 */
public enum Direction {
    
    N (new int[] {-1, 0}),
    NE(new int[] {-1, 1}),
    E (new int[] { 0, 1}),
    SE(new int[] { 1, 1}),
    S (new int[] { 1, 0}),
    SW(new int[] { 1,-1}),
    W (new int[] { 0,-1}),
    NW(new int[] {-1,-1});
    
    private final int[] d;
    Direction(int[] d) { this.d = d;}
    public int[] getValue() { return d; }
    
    public Direction invert(){
        switch(this){
            case N:
                return S;
            case NE:
                return SW;
            case E:
                return W;
            case SE:
                return NW;
            case S:
                return N;
            case SW:
                return NE;
            case W:
                return E;
            case NW:
                return SE;
            default:
                return null;
        }
    }
    
    
}
