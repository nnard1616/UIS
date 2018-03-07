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
    
    /**
     *
     */
    protected int[] coordinates;

    /**
     *
     */
    protected Space space;
    
    /**
     * @param lifespan
     * @param lifespan
     * @param space
     * @param space************************************************************************/
    
    
    public Ant(Lifespan lifespan, Space space){
        this.UID = this.antCount++;
        this.LIFESPAN = lifespan.getValue();
        this.age = 0;
        this.space = space;
    }
    
    /**
     *
     */
    public void incrementAge(){
        this.age++;
    }

    /**
     *
     * @return
     */
    public int getUID() {
        return UID;
    }

    /**
     *
     * @return
     */
    public int getLIFESPAN() {
        return LIFESPAN;
    }

    /**
     *
     * @return
     */
    public int getAge() {
        return age;
    }

    /**
     *
     * @return
     */
    public static int getAntCount() {
        return antCount;
    }

    /**
     *
     * @return
     */
    public int[] getCoordinates() {
        return space.getCoordinates();
    }

    /**
     *
     * @return
     */
    public Space getSpace() {
        return space;
    }
    
    /**
     *
     * @return
     */
    public boolean isOld(){
        if (this.age > this.LIFESPAN)
            throw new IllegalStateException("Ants should not age past their lifespan!");
        return this.age == this.LIFESPAN;
    }
    
    /**
     *
     * @return
     */
    public boolean isActive(){
        return active;
    }
    
    /**
     *
     * @return
     */
    public boolean isAlive() {
        return this.alive;
    }

    /**
     *
     * @param b
     */
    public void setActive(boolean b) {
        this.active = b;
    }
    
    /**
     *
     * @param space
     */
    public void setSpace(Space space) {
        this.space = space;
    }
    
    /**
     *
     */
    public void die(){
        this.alive = false;
    }
}
