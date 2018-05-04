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
import antcolonysimulation.ants.enemy.Enemy;
import antcolonysimulation.ants.Lifespan;
import antcolonysimulation.ants.Movable;
import antcolonysimulation.environment.Direction;
import antcolonysimulation.environment.Space;
import antcolonysimulation.simulation.Randomizer;
import dataStructures.ArrayList;
import dataStructures.LinkedList;
import dataStructures.List;
import dataStructures.ListIterator;

/**
 * Subclass of Friendly ants that scout for and attempt to kill Enemy ants.
 * 
 * @author nathan
 */
public class Soldier extends Friendly implements Actionable, Movable{

    private boolean scouting = true; //true --> scouting, false --> attacking
    
    /**************************************************************************/
    
    /**
     * Default constructor of Soldier object. Additional side effects 
     * include setting the Unit Identification Number (UID) and its age 
     * initialized to 0.
     * 
     * @param space    The space on which the Soldier starts.
    */
    public Soldier(Space space){
        super(Lifespan.OTHER, space);
        setActive(true);
    }
    
    /**
     * When in scout mode, the soldier chooses a random explored space to move 
     * to.  If there's an adjacent enemy, soldier enters attack mode and will
     * choose the direction with the most enemies.  When on the same space as a
     * enemy, it will attempt to attack the enemy with 50% success rate.
     */
    @Override
    public void act() {
        if (isOld()){
            die();
            return;
        }
        
        if (!isAlive())
            return;
        
        //If enemies present, switch to attack mode
        if (space.getEnemyCount() > 0 && isScouting())
            toggleScouting();
        
        //if enemies are not present, switch to scout mode
        if (space.getEnemyCount() == 0 && !isScouting())
            toggleScouting();
        
        if (isScouting())
            scout();
        else
            attack();
        
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
     * When in scout mode, the soldier chooses a random explored space to move 
     * to.  If there's an adjacent enemy, soldier enters attack mode and will
     * choose the direction with the most enemies.
     * @return
     */
    @Override
    public Direction chooseDirection() {
        //Get array of potential directions
        LinkedList directions = (LinkedList)space.getNeighborsDirections();
        
        int maxEnemyCount = 0;
        int neighborEnemyCount = 0;
        Direction nextD = null;
        List exploredDirections = new ArrayList();
        
        ListIterator litr = directions.listIterator(0);
        
        
        //determine which explored neighbor has largest number of enemies
        while (litr.hasNext()){
            //only look at areas that are explored.
            if (space.getNeighbor((Direction)litr.getCurrent()).isExplored()){
                exploredDirections.add((Direction)litr.getCurrent());
                neighborEnemyCount = space.getNeighbor(
                                  (Direction)litr.getCurrent()).getEnemyCount();
                if (neighborEnemyCount > maxEnemyCount ){
                    maxEnemyCount = neighborEnemyCount;
                    nextD = (Direction)litr.getCurrent();
                }
            }
            litr.next();
        }
        
        int numberOfDirections = exploredDirections.size();
        
        if (numberOfDirections == 0)
            return null;
        
        //if enemies are present, choose space with largest number
        if (nextD != null)
            return nextD;
        
        //otherwise no enemies present, select next space randomly
        return (Direction)exploredDirections.get(
                                   Randomizer.Give.nextInt(numberOfDirections));
    }

    /**
     * Determines if Soldier is in scout mode.
     * @return  boolean, True or False.
     */
    public boolean isScouting() {
        return scouting;
    }
    
    /**
     * Toggles scout/attack mode.
     */
    private void toggleScouting(){
        scouting = !scouting;
    }
    
    /**
     * Performs scout action.
     */
    public void scout(){
        Direction nextDirection = chooseDirection();
        //null means nowhere to move, so don't move
        if (nextDirection == null)
            return;
        
        moveTo(space.getNeighbor(nextDirection));
    }
    
    /**
     * Performs attack action, 50% success rate.
     */
    public void attack(){
        //Pick an enemy
        Enemy enemy = space.getEnemy((Integer)space.getEnemiesUIDs()
                                                   .get(0));
        
        // Try to attack the enemy, if successful the enemy dies.
        if (Randomizer.Give.nextDouble() <= 0.5)
            enemy.die();
    }
}
