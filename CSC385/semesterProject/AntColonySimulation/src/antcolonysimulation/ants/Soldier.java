/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.ants;

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
    }
    
    @Override
    public void act() {
        if (isOld()){
            die();
            return;
        }
        
        if (!isAlive())
            return;
        
        if (space.getBalaCount() > 0 && isScouting())
            toggleScouting();
        
        if (space.getBalaCount() == 0 && !isScouting())
            toggleScouting();
        
        if (isScouting())
            scout();
        else
            attack();
        
        incrementAge();
    }

    @Override
    public void moveTo(Direction next) {
        //Remove self from current space, place self in next space
        space.getNeighbor(next).addFriendly(space.popFriendly(getUID()));
        
        //Update Ant's space pointer.
        space = space.getNeighbor(next);
    }

    @Override
    public Direction chooseDirection() {
        //Get array of potential directions
        Object[] directions = space.getNeighbors().toArray();
        
        int maxEnemyCount = 0;
        int neighborEnemyCount = 0;
        Direction nextD = null;
        List<Direction> exploredDirections = new ArrayList<>();
        
        
        
        //determine which neighbor has largest number of enemies
        for (Object d : directions){
            if (space.getNeighbor((Direction)d).isExplored()){
                exploredDirections.add((Direction)d);
                neighborEnemyCount = space.getBalaCount();
                if (neighborEnemyCount > maxEnemyCount ){
                    maxEnemyCount = neighborEnemyCount;
                    nextD = (Direction)d;
                }
            }
        }
        
        int numberOfDirections = exploredDirections.size();
        
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
        moveTo(chooseDirection());
    }
    
    public void attack(){
        //Pick an enemy
        Enemy enemy = space.getEnemy((Integer)space.getEnemiesUIDs().toArray()[0]);
        
        // Try to attack the enemy, if successful the enemy dies.
        if (Randomizer.Give.nextDouble() <= 0.5)
            enemy.die();
    }
    
}
