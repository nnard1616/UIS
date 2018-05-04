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
import antcolonysimulation.environment.Direction;
import antcolonysimulation.environment.Space;
import antcolonysimulation.simulation.Randomizer;
import antcolonysimulation.simulation.Simulation;
import dataStructures.LinkedList;
import dataStructures.ListIterator;

/**
 *  Sub class of Friendly ants that consumes food every turn and produces new 
 * ants every 10 turns.
 * 
 * @author nathan
 */
public class Queen extends Friendly implements Actionable{
    
    private LinkedList ants;
    
    /**************************************************************************/
    
    /**
     * Default constructor of Queen object. Additional side effects include set-
     * -ting the Unit Identification Number (UID) and its age initialized to 0.
     * 
     * @param space     The space on which the ant starts.
     * @param ants      Reference to the simulation's container of ant actors.
    */
    public Queen(Space space, LinkedList ants){
        super(Lifespan.QUEEN, space);
        setActive(true);
        this.ants = ants;
        space.setExplored(true);
        
        LinkedList nDirections = (LinkedList)space.getNeighborsDirections();
        ListIterator litr = nDirections.listIterator(0);
        
        while(litr.hasNext()){
            space.getNeighbor((Direction)litr.getCurrent()).setExplored(true);
            litr.next();
        }
    }
    
    /**
     * Every turn Queen consumes 1 food.  If no food, the Queen dies.
     * Every 10 turns the Queen will hatch a new friendly ant, at these rates:
     * 50% Forager
     * 25% Scout
     * 25% Soldier
     */
    @Override
    public void act(){
        
        //may not need this?
        if (!isAlive() || !isActive())
            return;
        
        if (isOld()){
            die();
            return;
        }
        
        eat();
        
        //If no food, queen died, end turn
        if (!isAlive())
            return;
        
        //Make baby at start of day.
        if (Simulation.getTurn() % 10 == 0)
            hatchAnt();
        
        incrementAge();
    }
    
    /**
     * Randomly creates a friendly ant with these probabilities:
     * 50% Forager
     * 25% Scout
     * 25% Soldier
     */
    public void hatchAnt(){
        double roll = Randomizer.Give.nextDouble();
        
        if (roll <= 0.50){
            hatchForager(1);
        }else if (roll <= 0.75){
            hatchScout(1);
        }else{
            hatchSoldier(1);
        }
    }
    
    /**
     * Creates a variable number of friendly forager ants.
     * @param n     number of friendly foragers to make.
     */
    public void hatchForager(int n){
        for (int i = 0; i < n; i++ )
            ants.add( new Forager(space));
    }
    
    /**
     * Creates a variable number of friendly scout ants.
     * @param n     number of friendly scouts to make.
     */
    public void hatchScout(int n){
        for (int i = 0; i < n; i++ )
            ants.add( new Scout(space));
    }
    
    /**
     * Creates a variable number of friendly soldier ants.
     * @param n     number of friendly soldiers to make.
     */
    public void hatchSoldier(int n){
        for (int i = 0; i < n; i++ )
            ants.add( new Soldier(space));
    }
    
    /**
     *  Attempts to eat from food storage, if nothing there then die.
     */
    public void eat(){
        
        //try eating, if no food available
        if (!space.decrementFood())
            
            //then die
            die();
    }
}
