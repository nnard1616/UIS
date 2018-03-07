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
 *
 * @author nathan
 */
public class Scout extends Friendly implements Actionable, Movable{

    public Scout (Space space){
        super(Lifespan.OTHER, space);
        setActive(true);
    }
    
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

    @Override
    public Direction chooseDirection() {
        Object[] directions = space.getNeighbors().toArray();
        int numberOfDirections = directions.length;
        
        return (Direction)directions[Randomizer.Give.nextInt(numberOfDirections)];
    }
    
    public void revealSpace(){
        space.setExplored(true);
    }
    
}
