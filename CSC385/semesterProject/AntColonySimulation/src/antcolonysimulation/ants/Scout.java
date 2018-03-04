/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.ants;

import antcolonysimulation.environment.Direction;
import antcolonysimulation.environment.Space;
import antcolonysimulation.simulation.Randomizer;

/**
 *
 * @author nathan
 */
public class Scout extends Friendly implements Actionable, Movable{

    public Scout (Space space){
        super(Lifespan.OTHER, space);
    }
    
    @Override
    public void act() {
        
        if (!isAlive())
            return;
        
        if (isOld()){
            die();
            return;
        }
        
        moveTo(chooseDirection());
        
        revealSpace();
        
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
        Object[] directions = space.getNeighbors().toArray();
        int numberOfDirections = directions.length;
        
        return (Direction)directions[Randomizer.Give.nextInt(numberOfDirections)];
    }
    
    public void revealSpace(){
        space.setExplored(true);
    }
    
}
