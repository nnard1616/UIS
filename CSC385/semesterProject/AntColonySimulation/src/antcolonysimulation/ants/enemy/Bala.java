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
package antcolonysimulation.ants.enemy;

import antcolonysimulation.ants.Actionable;
import antcolonysimulation.ants.Lifespan;
import antcolonysimulation.ants.Movable;
import antcolonysimulation.ants.friendly.Forager;
import antcolonysimulation.ants.friendly.Friendly;
import antcolonysimulation.ants.friendly.Queen;
import antcolonysimulation.ants.friendly.Scout;
import antcolonysimulation.ants.friendly.Soldier;
import antcolonysimulation.environment.Direction;
import antcolonysimulation.environment.Space;
import antcolonysimulation.simulation.Randomizer;

/**
 *
 * @author nathan
 */
public class Bala extends Enemy implements Actionable, Movable{

    /**
     *
     * @param space
     */
    public Bala(Space space){
        super(Lifespan.OTHER, space);
        setActive(true);
    }
    
    /**
     *
     */
    @Override
    public void act() {
        
        if (!isAlive())
            return;
        
        if (isOld()){
            die();
            return;
        }
        
        if(space.containsFriendlies())
            attack();
        else
            moveTo(space.getNeighbor(chooseDirection()));
        
        incrementAge();
        
    }

    /**
     *
     * @param space
     */
    @Override
    public void moveTo(Space space) {
        //Remove self from current space, place self in next space
        space.addEnemy(this.space.popEnemy(getUID()));
        
        //Update Ant's space pointer.
        this.space = space;
    }

    /**
     *
     * @return
     */
    @Override
    public Direction chooseDirection() {
        Object[] directions = space.getNeighbors().toArray();
        int numberOfDirections = directions.length;
        
        return (Direction)directions[Randomizer.Give.nextInt(numberOfDirections)];
    }

    /**
     *
     */
    public void attack(){
        Friendly target = null;
        for (int fUID : space.getFriendliesUIDs()){
            if (space.getFriendly(fUID).getClass().equals(Queen.class)){
                target = space.getFriendly(fUID);
                break;
            }else if (space.getFriendly(fUID).getClass().equals(Soldier.class)){
                target = space.getFriendly(fUID);
                break;
            }else if (space.getFriendly(fUID).getClass().equals(Forager.class))
                target = space.getFriendly(fUID);
            else if (space.getFriendly(fUID).getClass().equals(Scout.class))
                target = space.getFriendly(fUID);
        }
        
        if (Randomizer.Give.nextDouble() <= 0.5)
            target.die();
    }
}
