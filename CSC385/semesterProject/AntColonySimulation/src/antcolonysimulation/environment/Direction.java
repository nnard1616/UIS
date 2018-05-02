/* 
 * Copyright (C) 2018 Nathan Nard
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package antcolonysimulation.environment;

/**
 * A Direction is associated with a doublet that represents the change in 
 * position relative to a coordinate point on a grid. 
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

    /**
     * Returns the doublet that corresponds to the Direction identifier.
     * The doublet returned represents the change in position relative to a 
     * coordinate point on a grid.
     * @return {int, int} --> {change in x, change in y}
     */
    public int[] getValue() { return d; }
    
    /**
     * Returns the Direction that is 180 degrees opposite of the current 
     * Direction.
     * @return Direction
     */
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
