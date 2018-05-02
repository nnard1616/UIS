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
 * A generic enemy ant that is able to move and perform actions, namely attack
 * friendly ants, in the simulation.
 * 
 * @author nathan
 */
public class Bala extends Enemy implements Actionable, Movable{

    /**
     * Default constructor, calls Enemy's default constructor.
     * @param space
     */
    public Bala(Space space){
        super(Lifespan.OTHER, space);
    }
    
    /**
     * Moves randomly regardless if space is explored.  If any friendlies are 
     * in the same occupied space, attack one of the friendlies at random, but
     * prioritize in this order:  queen, soldier, forager, scout.  50% chance
     * to kill attack target.
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
     * Method to move the Movable actor to the specified Space.
     * @param space     Space object that the Bala will move to.
     */
    @Override
    public void moveTo(Space space) {
        //Remove self from current space, place self in next space
        space.addEnemy(this.space.popEnemy(getUID()));
        
        //Update Ant's space pointer.
        this.space = space;
    }
    
    /**
     * Picks a random direction to move in, regardless of the Space being 
     * revealed or not.
     * @return      Direction object that represents direction to move in grid.
     */
    @Override
    public Direction chooseDirection() {
        Object[] directions = space.getNeighborsDirections().toArray();
        int numberOfDirections = directions.length;
        
        return (Direction)directions[Randomizer.Give.nextInt(
                                                           numberOfDirections)];
    }

    /**
     * Method that picks a Friendly from the Friendly list on the current space
     * and 50% chance kills it.  Favors Queen, Soldier, Forager, and then Scout.
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
