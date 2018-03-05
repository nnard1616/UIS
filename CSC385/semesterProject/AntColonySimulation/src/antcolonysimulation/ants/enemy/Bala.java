/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.ants.enemy;

import antcolonysimulation.ants.Actionable;
import antcolonysimulation.ants.Lifespan;
import antcolonysimulation.ants.Movable;
import antcolonysimulation.ants.friendly.Forager;
import antcolonysimulation.ants.friendly.Friendly;
import antcolonysimulation.ants.friendly.Queen;
import antcolonysimulation.ants.friendly.Scout;
import antcolonysimulation.ants.friendly.Soldier;
import antcolonysimulation.environment.Direction;
import antcolonysimulation.environment.Space;
import antcolonysimulation.simulation.Randomizer;

/**
 *
 * @author nathan
 */
public class Bala extends Enemy implements Actionable, Movable{

    public Bala(Space space){
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
        
        if(space.containsFriendlies())
            attack();
        else
            moveTo(space.getNeighbor(chooseDirection()));
        
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

    public void attack(){
        Friendly target = null;
        for (int fUID : space.getFriendliesUIDs()){
            if (space.getFriendly(fUID).getClass().equals(Queen.class)){
                target = space.getFriendly(fUID);
                break;
            }else if (space.getFriendly(fUID).getClass().equals(Soldier.class)){
                target = space.getFriendly(fUID);
                break;
            }else if (space.getFriendly(fUID).getClass().equals(Forager.class))
                target = space.getFriendly(fUID);
            else if (space.getFriendly(fUID).getClass().equals(Scout.class))
                target = space.getFriendly(fUID);
        }
        
        if (Randomizer.Give.nextDouble() <= 0.5)
            target.die();
    }
    
    
}
