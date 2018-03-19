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
package antcolonysimulation.ants;

import antcolonysimulation.environment.Space;

/**
 *
 * @author nathan
 */
public class Ant {
    private final int UID;
    private final int LIFESPAN;
    private int age;
    private boolean active = false;
    private boolean alive = true;
    
    private static int antCount = 0;
    protected Space space;
    
    /**************************************************************************/
    
    /**
     * Default constructor of Ant object. Additional side effects include set-
     * -ting the Unit Identification Number (UID) and its age initialized to 0.
     * 
     * @param lifespan How old the ant can live, see Lifespan enumeration.
     * @param space    The space on which the ant starts.
    */
    public Ant(Lifespan lifespan, Space space){
        this.UID = this.antCount++;
        this.LIFESPAN = lifespan.getValue();
        this.age = 0;
        this.space = space;
    }
    
    /**************************************************************************/
    /*  Getters                                                               */
    /**************************************************************************/

    /**
     * Returns the Unit Identification Number of the Ant object.
     * @return UID
     */
    public int getUID() {
        return UID;
    }

    /**
     * Returns the lifespan of the Ant object.
     * @return LIFESPAN
     */
    public int getLIFESPAN() {
        return LIFESPAN;
    }

    /**
     * Returns the Ant object's current age.
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns the number of Ants created so far.
     * @return antCount
     */
    public static int getAntCount() {
        return antCount;
    }

    /**
     * Returns the coordinates of the Space object that the Ant occupies.
     * @return Doublet of ints.
     */
    public int[] getCoordinates() {
        return space.getCoordinates();
    }

    /**
     * Returns reference of the Space object currently occupied.
     * @return Space reference
     */
    public Space getSpace() {
        return space;
    }
    
    /**
     * Returns true when the Ant's age equals its lifespan, false otherwise.
     * Throws an IllegalStateException when age exceeds lifespan.
     * @return Boolean
     */
    public boolean isOld(){
        if (this.age > this.LIFESPAN)
            throw new IllegalStateException("Ants should not age past their "
                                            + "lifespan!");
        return this.age == this.LIFESPAN;
    }
    
    /**
     * Returns true when the Ant is able to act, false otherwise.
     * @return Boolean
     */
    public boolean isActive(){
        return active;
    }
    
    /**
     * Returns true when the Ant is alive, false otherwise.
     * @return Boolean
     */
    public boolean isAlive() {
        return alive;
    }
    
    /**************************************************************************/
    /*  Setters                                                               */
    /**************************************************************************/

    /**
     * Sets the active state of the Ant
     * @param b True or False.
     */
    public void setActive(boolean b) {
        this.active = b;
    }
    
    /**
     * Set the Space reference on which the Ant occupies.
     * @param space
     */
    public void setSpace(Space space) {
        this.space = space;
    }
    
    /**
     * Increases Ant's age by 1.
     */
    public void incrementAge(){
        this.age++;
    }
    
    /**
     * Sets alive attribute to false.
     */
    public void die(){
        this.alive = false;
    }
}
