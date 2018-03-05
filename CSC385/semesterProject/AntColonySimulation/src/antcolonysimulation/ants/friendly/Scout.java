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
        
        moveTo(space.getNeighbor(chooseDirection()));
        
        revealSpace();
        
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
        Object[] directions = space.getNeighbors().toArray();
        int numberOfDirections = directions.length;
        
        return (Direction)directions[Randomizer.Give.nextInt(numberOfDirections)];
    }
    
    public void revealSpace(){
        space.setExplored(true);
    }
    
}
