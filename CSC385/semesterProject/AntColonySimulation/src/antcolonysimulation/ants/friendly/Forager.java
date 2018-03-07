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
 *
 * @author nathan
 */
public class Forager extends Friendly implements Actionable, Movable{

    private int food = 0;
    private Stack<Space> travelHistory = new Stack<>();
    private Queue<Space> recentVisits;
    private int visitMemoryCapacity;
    
    /**
     * @param space************************************************************************/
    
    public Forager(Space space){
        this(space, 5);
    }
    
    /**
     *
     * @param space
     * @param visitMemoryCapacity
     */
    public Forager(Space space, int visitMemoryCapacity){
        super(Lifespan.OTHER, space);
        setActive(true);
        this.visitMemoryCapacity = visitMemoryCapacity;
        recentVisits =  new ArrayBlockingQueue<Space>(visitMemoryCapacity);
        travelHistory.add(space);
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
     *
     * @param space
     */
    @Override
    public void moveTo(Space space) {
        if (travelHistory.contains(space))
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
     *
     * @return
     */
    @Override
    public Direction chooseDirection() {
        //Get array of potential directions
        Object[] directions = space.getNeighbors().stream().sorted().toArray();
        
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
        
            
        return exploredAndTopPheromonicDirections.get(Randomizer.Give.nextInt(numberOfDirections));
    }
    
    /**
     *
     * @param receiver
     * @param giver
     */
    public void addPairs(int[] receiver, int[] giver){
        receiver[0] += giver[0];
        receiver[1] += giver[1];
    }
    
    /**
     *
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
     *
     * @return
     */
    public boolean isForaging(){
        return food == 0;
    }
    
    /**
     *
     * @return
     */
    public boolean foodPresent(){
        return space.getFood() > 0 && space.getQueenCount() == 0;
    }
    
    /**
     *
     */
    public void pickUpFood(){
        food++;
        space.setFood(space.getFood()-1);
    }
    
    /**
     *
     */
    public void depositPheromone(){
        int pheromoneLevel = space.getPheromone();
        int depositAmount = 10;
        
        if (pheromoneLevel < 1000)
            space.setPheromone(pheromoneLevel+depositAmount);
    }
    
    /**
     *
     */
    public void depositFood(){
        space.setFood(space.getFood()+1);
        food--;
    }
    
    /**
     *
     * @return
     */
    public int maxNeighborPheromoneCount(){
        
        Object[] directions = space.getNeighbors().stream().sorted().toArray();
        
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
    
    /**
     *
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
     *
     * @param s
     * @return
     */
    public boolean recentlyVisited(Space s){
        return recentVisits.contains(s);
    }
    
    /**
     *
     * @param space
     */
    public void trimTravelHistory(Space space){
        if (travelHistory.contains(space) && travelHistory.size() > 1){
            while (travelHistory.peek() != space)
                travelHistory.pop();
            travelHistory.pop();
        }
    }

    /**
     *
     * @return
     */
    public int getFood() {
        return food;
    }

    /**
     *
     * @return
     */
    public Stack<Space> getTravelHistory() {
        return travelHistory;
    }
    
    /**
     *
     */
    @Override
    public void die(){
        super.die();
        depositFood();
    }
}
