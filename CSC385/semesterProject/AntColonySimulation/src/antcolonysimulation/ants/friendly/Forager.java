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
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

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
     * travelHistory records all spaces traveled to, periodically has loops
     * cut out of it.  
     */
    private Stack<Space> travelHistory = new Stack<>(); 
    
    /** Short Term Memory. 
     * recentVisits records only most recently visited spaces up to memory 
     * capacity, used to prevent local infinite loops.
     */
    private Queue<Space> recentVisits;
    
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
        recentVisits =  new ArrayBlockingQueue<Space>(visitMemoryCapacity);
        travelHistory.add(space);
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
            
            Direction nextDirection = chooseDirection();
            
            moveTo(space.getNeighbor(chooseDirection()));
            
            //if food present, pick it up
            if (foodPresent()){
                
                pickUpFood();

                //forget last few steps
                recentVisits.clear();
                travelHistory.pop();
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
        if (travelHistory.contains(space) && travelHistory.size() > 1)
            trimTravelHistory(space);
        
        //record movement
        if (isForaging()){
            travelHistory.push(space);
            addRecentVisit();
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
        Object[] directions = space.getNeighborsDirections().stream()
                                                            .sorted()
                                                            .toArray();
        
        int maxPheromone = maxNeighborPheromoneCount();
        List<Direction> exploredAndTopPheromonicDirections = new ArrayList<>();
        
        
        
        //determine which explored neighbors have largest pheromone count
        for (Object d : directions){
            
            Space neighbor = space.getNeighbor((Direction)d);
            
            //only look at areas that are explored.
            if (neighbor.isExplored() ){
                
                //Only look at areas we haven't been to recently
                if (!recentlyVisited(neighbor))
                
                    if (neighbor.getPheromone() == maxPheromone)
                        exploredAndTopPheromonicDirections.add((Direction)d);
            }
        }
        
        int numberOfDirections = exploredAndTopPheromonicDirections.size();
        
        //nowhere to move and we aren't at base
        if (numberOfDirections == 0 ){
            recentVisits.clear();
            return chooseDirection();
        }
        
            
        return exploredAndTopPheromonicDirections.get(Randomizer.Give.nextInt(
                                                           numberOfDirections));
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
        if (travelHistory.size() > 1){
            //we're not at base yet, backtrack
            Space backStep = travelHistory.pop();
                    
            depositPheromone();
            moveTo(backStep);
        }else if (travelHistory.size() == 1){
            Space backStep = travelHistory.peek();
            
            depositPheromone();
            moveTo(backStep);
            
            //we're home, deposit food
            depositFood();
            recentVisits.clear();
        }
    }
    
    /**
     * Adds current space to Forager's recently visited space memory.  If full, 
     * removes oldest memory to make space for new memory.
     */
    public void addRecentVisit(){
        //if unsuccessful adding the space to the queue,
        if (!recentVisits.offer(space)){
            
            //remove the head
            recentVisits.remove();
            
            //add the space
            recentVisits.add(space);
        }
    }
    
    /**
     * Determines whether query space has been visited recently.  Used to 
     * prevent local loops.
     * @param s     Space object to query for.
     * @return      boolean, True or False.
     */
    public boolean recentlyVisited(Space s){
        return recentVisits.contains(s);
    }
    
    /**
     * Cuts out large loops in travel history.
     * @param space     Space that
     */
    public void trimTravelHistory(Space space){
        while (travelHistory.peek() != space)
            travelHistory.pop();
        travelHistory.pop();
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
     * @return  Stack<Space>, history of spaces visited since the nest.
     */
    public Stack<Space> getTravelHistory() {
        return travelHistory;
    }
    
    /**
     * Determines the maximum pheromone counter of the neighboring spaces.
     * @return  int, max pheromone counter.
     */
    public int maxNeighborPheromoneCount(){
        
        Object[] directions = space.getNeighborsDirections().stream()
                                                            .sorted()
                                                            .toArray();
        
        int maxPheromone = 0;
        
        //determine which explored neighbors have largest pheromone count
        for (Object d : directions){
            
            Space neighbor = space.getNeighbor((Direction)d);
            
            //only look at areas that are explored.
            if (neighbor.isExplored() ){
                
                //Only look at areas we haven't been to recently
                if (!recentlyVisited(neighbor))
                
                    if (neighbor.getPheromone() > maxPheromone ){
                        maxPheromone = neighbor.getPheromone();
                    }
            }
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
