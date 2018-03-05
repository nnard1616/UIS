/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.ants.friendly;

import antcolonysimulation.ants.Actionable;
import antcolonysimulation.ants.Lifespan;
import antcolonysimulation.ants.Movable;
import antcolonysimulation.environment.Direction;
import antcolonysimulation.environment.Space;
import antcolonysimulation.simulation.Randomizer;
import java.util.ArrayList;
import java.util.EmptyStackException;
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
    private int visitMemory;
    
    public Forager(Space space){
        this(space, 5);
    }
    
    public Forager(Space space, int visitMemory){
        super(Lifespan.OTHER, space);
        space.addFriendly(this);
        this.visitMemory = visitMemory;
        recentVisits =  new ArrayBlockingQueue<Space>(visitMemory);
    }
    
    @Override
    public void act() {
        
        if (!isAlive())
            return;
        
        if (isOld()){
            die();
            return;
        }
        
        if (isForaging()){
            //if no food present, move to next space
            if (!foodPresent())
                moveTo(null);
            else{
                //else pick up 1 food.
                pickUpFood();

                //forget last few steps
                recentVisits.clear();

                //TODO optimize return path
            }
        }
        else
            //take food back to nest
            backtrack();
        
        incrementAge();
    }

    @Override
    public void moveTo(Space space) {
        //null means nowhere to move, don't move anywhere.
        if (next == null)
            return;
        
        //record movement
        travelHistory.push(next);
        addRecentVisit();
        
        //Remove self from current space, place self in next space
        space.getNeighbor(next).addFriendly(space.popFriendly(getUID()));
        
        //Update Ant's space pointer.
        space = space.getNeighbor(next);
    }

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
        if (numberOfDirections == 0 && space.getQueenCount() == 0){
            recentVisits.clear();
            return chooseDirection();
        }
        
        //nowhere to move and at base:
        if (numberOfDirections == 0 && space.getQueenCount() == 1){
            return null;
        }
            
        return exploredAndTopPheromonicDirections.get(Randomizer.Give.nextInt(numberOfDirections));
    }
    
    public void addPairs(int[] receiver, int[] giver){
        receiver[0] += giver[0];
        receiver[1] += giver[1];
    }
    
    public void backtrack(){
        try{
            //if there's backstepping to do, do it, otherwise we're done backtracking (throw exception)
            Direction backStep = travelHistory.pop().invert();
                    
            depositPheromone();
            moveTo(null);
        }catch(EmptyStackException ese){
            depositFood();
        }
    }
    
    public boolean isForaging(){
        return food == 0;
    }
    
    public boolean foodPresent(){
        return space.getFood() > 0 && space.getQueenCount() == 0;
    }
    
    public void pickUpFood(){
        food++;
        space.setFood(space.getFood()-1);
    }
    
    public void depositPheromone(){
        int pheromoneLevel = space.getPheromone();
        int depositAmount = 10;
        
        if (pheromoneLevel < 1000)
            space.setPheromone(pheromoneLevel+depositAmount);
    }
    
    public void depositFood(){
        space.setFood(space.getFood()+1);
        food--;
    }
    
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
    
    public void addRecentVisit(){
        //if unsuccessful adding the space to the queue,
        if (!recentVisits.offer(space)){
            
            //remove the head
            recentVisits.remove();
            
            //add the space
            recentVisits.add(space);
        }
    }
    
    public boolean recentlyVisited(Space s){
        return recentVisits.contains(s);
    }
    
    public void trimTravelHistory(){
        if (travelHistory.size() > visitMemory)
            for (int i = )
    }
    @Override
    public void die(){
        super.die();
        depositFood();
    }
    
    
    
    
    
    
}
