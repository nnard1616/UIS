/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.ants.friendly;

import antcolonysimulation.ants.Actionable;
import antcolonysimulation.ants.enemy.Enemy;
import antcolonysimulation.ants.Lifespan;
import antcolonysimulation.ants.Movable;
import antcolonysimulation.environment.Direction;
import antcolonysimulation.environment.Space;
import antcolonysimulation.simulation.Randomizer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nathan
 */
public class Soldier extends Friendly implements Actionable, Movable{

    private boolean scouting = true;
    
    public Soldier(Space space){
        super(Lifespan.OTHER, space);
        setActive(true);
    }
    
    @Override
    public void act() {
        if (isOld()){
            die();
            return;
        }
        
        if (!isAlive())
            return;
        
        //If enemies present, switch to attack mode
        if (space.getBalaCount() > 0 && isScouting())
            toggleScouting();
        
        //if enemies are not present, switch to scout mode
        if (space.getBalaCount() == 0 && !isScouting())
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

    @Override
    public Direction chooseDirection() {
        //Get array of potential directions
        Object[] directions = space.getNeighbors().stream().sorted().toArray();
        
        int maxEnemyCount = 0;
        int neighborEnemyCount = 0;
        Direction nextD = null;
        List<Direction> exploredDirections = new ArrayList<>();
        
        
        
        //determine which explored neighbor has largest number of enemies
        for (Object d : directions){
            //only look at areas that are explored.
            if (space.getNeighbor((Direction)d).isExplored()){
                exploredDirections.add((Direction)d);
                neighborEnemyCount = space.getNeighbor((Direction)d).getBalaCount();
                if (neighborEnemyCount > maxEnemyCount ){
                    maxEnemyCount = neighborEnemyCount;
                    nextD = (Direction)d;
                }
            }
        }
        
        int numberOfDirections = exploredDirections.size();
        
        if (numberOfDirections == 0)
            return null;
        
        //if enemies are present, choose space with largest number
        if (nextD != null)
            return nextD;
        
        //otherwise no enemies present, select next space randomly
        return exploredDirections.get(Randomizer.Give.nextInt(numberOfDirections));
    }

    public boolean isScouting() {
        return scouting;
    }
    
    private void toggleScouting(){
        scouting = !scouting;
    }
    
    public void scout(){
        Direction nextDirection = chooseDirection();
        //null means nowhere to move, so don't move
        if (nextDirection == null)
            return;
        
        moveTo(space.getNeighbor(nextDirection));
    }
    
    public void attack(){
        //Pick an enemy
        Enemy enemy = space.getEnemy((Integer)space.getEnemiesUIDs().toArray()[0]);
        
        // Try to attack the enemy, if successful the enemy dies.
        if (Randomizer.Give.nextDouble() <= 0.5)
            enemy.die();
    }
    
}
