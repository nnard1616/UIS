/*
 * Copyright (C) 2018 Nathan
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
package dataStructures;

/**
 * Extended ArrayList that has exactly 2 elements, x and y.
 * @author Nathan
 */
public class Doublet extends ArrayList{
    
    /**
     * Constructor of Doublet, takes two elements.
     * @param x  First element.
     * @param y  Second element.
     */
    public Doublet(Object x, Object y){
        super.add(x);
        super.add(y);
    }

    /**
     * Returns the first element in Doublet.
     * @return First element.
     */
    public Object getX() {
        return super.get(0);
    }

    /**
     * Returns the second element in Doublet.
     * @return Second element.
     */
    public Object getY() {
        return super.get(1);
    }
    
    
    @Override
    public boolean equals(Object o){
        if (o.getClass() != Doublet.class)
            return false;
        else{
            return super.get(0).equals(((Doublet)o).getX()) && 
                   super.get(1).equals(((Doublet)o).getY());
        }
    }
}
