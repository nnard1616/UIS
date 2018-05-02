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
package antcolonysimulation.ants.friendly;

import antcolonysimulation.ants.Actionable;
import antcolonysimulation.ants.Lifespan;
import antcolonysimulation.ants.Movable;
import antcolonysimulation.environment.Direction;
import antcolonysimulation.environment.Space;
import antcolonysimulation.simulation.Randomizer;

/**
 * Subclass of Friendly ants that explore the Environment of the simulation.
 * 
 * @author nathan
 */
public class Scout extends Friendly implements Actionable, Movable{

    /**
     * Default constructor of Scout object. Additional side effects include set-
     * -ting the Unit Identification Number (UID) and its age initialized to 0.
     * 
     * @param space    The space on which the Scout starts.
    */
    public Scout (Space space){
        super(Lifespan.OTHER, space);
        setActive(true);
    }
    
    /**
     * Randomly chooses a space to move to, regardless if it is explored or not.
     * If the space is unexplored, it reveals that space.
     */
    @Override
    public void act() {
        
        if (!isAlive())
            return;
        
        if (isOld()){
            die();
            return;
        }
        
        moveTo(space.getNeighbor(chooseDirection()));
        
        revealSpace();
        
        incrementAge();
        
    }

    @Override
    public void moveTo(Space space) {
        //Remove self from current space, place self in next space
        space.addFriendly(this.space.popFriendly(getUID()));
        
        //Update Ant's space pointer.
        this.space = space;
    }

    /**
     *  Scouts move randomly, regardless if the area is explored or not.
     * @return  Random Direction to move in.
     */
    @Override
    public Direction chooseDirection() {
        Object[] directions = space.getNeighborsDirections().toArray();
        int numberOfDirections = directions.length;
        
        return (Direction)directions[Randomizer.Give.nextInt(
                                                           numberOfDirections)];
    }
    
    /**
     *  If the space occupied by the scout is unexplored, then reveal it.
     */
    public void revealSpace(){
        space.setExplored(true);
    }
    
}
