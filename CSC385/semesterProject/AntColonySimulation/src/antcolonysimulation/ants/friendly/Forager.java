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
import dataStructures.AVLTree;
import dataStructures.ArrayList;
import dataStructures.DuplicateItemException;
import dataStructures.LinkedList;
import dataStructures.LinkedStack;
import dataStructures.List;
import dataStructures.Queue;
import dataStructures.LinkedQueue;
import dataStructures.ListIterator;
import dataStructures.Stack;

/**
 * Foragers are a sub class of Friendly ants that scout for food and carry 
 * it back to the nest in the simulation. 
 * 
 * @author nathan
 */
public class Forager extends Friendly implements Actionable, Movable{

    /**************************************************************************/
    /*  Attributes                                                            */
    /**************************************************************************/
    private int food = 0; // 0 --> forage mode, >0 --> return-to-nest mode.
    
    /** Long Term Memory.
     * travelHistoryStack records all spaces traveled to in Linked Stack,
     * periodically has loops cut out of it.  
     */
    private Stack travelHistoryStack = new LinkedStack(); 
    
    /** Long Term Memory.
     * travelHistoryTree records all spaces traveled to in AVL Tree.  Used to
     * quickly look up past visited spaces and to keep track of potential loops.
     */
    private AVLTree travelHistoryTree = new AVLTree();
    
    /** Short Term Memory. 
     * recentVisits records only most recently visited spaces up to memory 
     * capacity, used to prevent local infinite loops.
     */
    private Queue recentVisits;
    
    //Short Term Memory Capacity.
    private int visitMemoryCapacity; 
    
    
    /**************************************************************************/
    /*  Constructors                                                          */
    /**************************************************************************/
    
    /**
     * Constructor of Forager with default visited space memory capacity of 5.
     * 
     * @param space    The space on which the forager starts.
    */
    public Forager(Space space){
        this(space, 5);
    }
    
    /**
     * Constructor of Forager with user-provided visited space memory capacity.
     * 
     * @param space                 The space on which the forager starts.
     * @param visitMemoryCapacity   The memory capacity of the forager.
     */
    public Forager(Space space, int visitMemoryCapacity){
        super(Lifespan.OTHER, space);
        setActive(true);
        this.visitMemoryCapacity = visitMemoryCapacity;
        recentVisits =  new LinkedQueue();
        travelHistoryStack.add(space);
        travelHistoryTree.add(space);
        recentVisits.add(space);
    }
    

    /**************************************************************************/
    /*  Overrides                                                             */
    /**************************************************************************/
    
    /**
     * Forager will explore randomly in explored spaces, but favors spaces with
     * highest level of pheromone.  This continues until it finds a space with 
     * food that is not the nest.  Once it finds food, it picks up 1 unit of 
     * food and backtracks its travel history to return to the nest where
     * it drops the 1 unit of food.
     */
    @Override
    public void act() {
        
        if (!isAlive())
            return;
        
        if (isOld()){
            die();
            return;
        }
        
        if (isForaging()){
            
            moveTo(space.getNeighbor(chooseDirection()));
            
            //if food present, pick it up
            if (foodPresent()){
                
                pickUpFood();

                //forget last few steps
                recentVisits.clear();
                travelHistoryStack.pop();
            }
        }
        else
            //take food back to nest
            backtrack();
        
        incrementAge();
    }

    /**
     * Records movement in travelHistory (Long Term Memory) and recentVisits
     * (Short Term Memory), then moves Forager to next space.
     * 
     * @param space     Space object that the Forager will move to.
     */
    @Override
    public void moveTo(Space space) {
        
        //record movement if foraging
        if (isForaging()){
            //check if we've been to this space yet, long term memory
            try{
                travelHistoryTree.add(space);
                travelHistoryStack.push(space);  
            }catch(DuplicateItemException die){
                // we've already been to this space before
                if (travelHistoryStack.size() > 1)
                    trimTravelHistory(space); //trim loop
            }
            addRecentVisit(space);//update short term memory
        }
        
        //Remove self from current space, place self in next space
        space.addFriendly(this.space.popFriendly(getUID()));
        
        //Update Ant's space pointer.
        this.space = space;
    }

    /**
     * Selects direction that has space with most pheromone, if tie then chooses
     * randomly.  Only selects space not in recent memory.
     * 
     * @return      Direction object that represents direction to move in grid.
     */
    @Override
    public Direction chooseDirection() {
        //Get array of potential directions
        LinkedList directions = (LinkedList)space.getNeighborsDirections();
        
        int maxPheromone = maxNeighborPheromoneCount();
        List exploredAndTopPheromonicDirections = new ArrayList();
        
        ListIterator litr = directions.listIterator(0);
        
        //determine which explored neighbors have largest pheromone count
        while (litr.hasNext()){
            
            Space neighbor = space.getNeighbor((Direction)litr.getCurrent());
            
            //only look at areas that are explored.
            if (neighbor.isExplored() ){
                
                //Only look at areas we haven't been to recently
                if (!recentlyVisited(neighbor))
                
                    if (neighbor.getPheromone() == maxPheromone)
                        exploredAndTopPheromonicDirections.add(
                                                  (Direction)litr.getCurrent());
            }
            
            litr.next();
        }
        
        int numberOfDirections = exploredAndTopPheromonicDirections.size();
        
        //nowhere to move and we aren't at base
        if (numberOfDirections == 0 ){
            recentVisits.clear();
            recentVisits.add(this.space);
            return chooseDirection();
        }//this will allow us to move now.
        
            
        return (Direction)exploredAndTopPheromonicDirections.get(
                                   Randomizer.Give.nextInt(numberOfDirections));
    }
    
    /**
     * Kills the Forager, dropping food if any.
     */
    @Override
    public void die(){
        super.die();
        if(this.getFood() > 0)
            this.depositFood();
    }
    
    
    /**************************************************************************/
    /*  Memory Interactives                                                   */
    /**************************************************************************/
    
    /**
     * Moves Forager through their travelHistory of spaces in reverse.
     * Eventually ends up back at the nest where food is deposited.
     */
    public void backtrack(){
        if (travelHistoryStack.size() > 1){
            //we're not at base yet, backtrack
            Space backStep = (Space)travelHistoryStack.pop();
                    
            depositPheromone();
            moveTo(backStep);
        }else if (travelHistoryStack.size() == 1){
            Space backStep = (Space)travelHistoryStack.peek();
            
            depositPheromone();
            moveTo(backStep);
            
            //we're home, deposit food
            depositFood();
            recentVisits.clear();
            recentVisits.add(backStep);//add back the nest node.
            travelHistoryTree.clear();
            travelHistoryTree.add(backStep);//add back the nest node.
        }
    }
    
    /**
     * Adds current space to Forager's recently visited space memory.  If full, 
     * removes oldest memory to make space for new memory.
     * @param space  Space object to add to short term memory.
     */
    public void addRecentVisit(Space space){
        if (recentVisits.size() < visitMemoryCapacity)
            recentVisits.enqueue(space);
        else{
            //remove the head
            recentVisits.dequeue();
            
            //add the space
            recentVisits.add(space);
        }
    }
    
    /**
     * Determines whether query space has been visited recently in short term
     * memory.  Used to prevent local infinite loops.
     * @param space     Space object to query for.
     * @return      boolean, True or False.
     */
    public boolean recentlyVisited(Space space){
        boolean b = false;
        if (recentVisits.size() > 0){
            for (int i = 0; i < visitMemoryCapacity; i++){
                if (recentVisits.getFront().equals(space))
                    b = true;
                recentVisits.enqueue(recentVisits.dequeue());
            }
        }
            
        return b;
    }
    
    /**
     * Cuts out large loops in travel history.
     * @param space     Space that
     */
    public void trimTravelHistory(Space space){
        while (travelHistoryStack.peek() != space)
            travelHistoryTree.remove((Comparable)travelHistoryStack.pop());
    }
    
    
    /**************************************************************************/
    /*  Getters                                                               */
    /**************************************************************************/
    
    /**
     * Determines if Forager is in Foraging mode.
     * @return      boolean, True or False.
     */
    public boolean isForaging(){
        return food == 0;
    }
    
    /**
     * Determines if current space has food and does not contain the queen.
     * @return      boolean, True or False.
     */
    public boolean foodPresent(){
        return space.getFood() > 0 && space.getQueenCount() == 0;
    }
    
    /**
     * Getter that returns Forager's food counter.
     * @return  int, amount of food carried by Forager.
     */
    public int getFood() {
        return food;
    }

    /**
     * Getter that returns Forager's travelHistory
     * @return  Stack, history of spaces visited since the nest.
     */
    public Stack getTravelHistory() {
        return travelHistoryStack;
    }
    
    /**
     * Determines the maximum pheromone counter of the neighboring spaces.
     * @return  int, max pheromone counter.
     */
    public int maxNeighborPheromoneCount(){
        
        LinkedList directions = (LinkedList)space.getNeighborsDirections();
        
        int maxPheromone = 0;
        ListIterator litr = directions.listIterator(0);
        
        //determine which explored neighbors have largest pheromone count
        while (litr.hasNext()){
            
            Space neighbor = space.getNeighbor((Direction)litr.getCurrent());
            
            //only look at areas that are explored.
            if (neighbor.isExplored() ){
                
                //Only look at areas we haven't been to recently
                if (!recentlyVisited(neighbor))
                
                    if (neighbor.getPheromone() > maxPheromone ){
                        maxPheromone = neighbor.getPheromone();
                    }
            }
            litr.next();
        }
        return maxPheromone;
    }
    
    
    /**************************************************************************/
    /*  Setters                                                               */
    /**************************************************************************/
    
    /**
     * Increments Forager's Food counter, decrements occupied Space's food 
     * counter by 1.
     */
    public void pickUpFood(){
        food++;
        space.setFood(space.getFood()-1);
    }
    
    /**
     * Increments occupied Space's pheromone counter by 10.
     */
    public void depositPheromone(){
        int pheromoneLevel = space.getPheromone();
        int depositAmount = 10;
        
        if (pheromoneLevel < 1000)
            space.setPheromone(pheromoneLevel+depositAmount);
    }
    
    /**
     * Decrements Forager's Food counter, increments occupied Space's food 
     * counter by 1.
     */
    public void depositFood(){
        space.setFood(space.getFood()+1);
        food--;
    }
}
