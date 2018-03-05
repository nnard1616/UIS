/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.ants.friendly;

import antcolonysimulation.ants.Actionable;
import antcolonysimulation.ants.Lifespan;
import antcolonysimulation.environment.Space;
import antcolonysimulation.simulation.Randomizer;
import antcolonysimulation.simulation.Simulation;

/**
 *
 * @author nathan
 */
public class Queen extends Friendly implements Actionable{
    public Queen(Space space){
        super(Lifespan.QUEEN, space);
        space.setExplored(true);
    }
    
    @Override
    public void act(){
        
        //may not need this?
        if (!isAlive())
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
    
    public Friendly hatchAnt(){
        double roll = Randomizer.Give.nextDouble();
        Friendly baby;
        
        if (roll <= 0.50){
            baby = new Forager(space);
            space.addFriendly(baby);
            return baby;
        }else if (roll <= 0.75){
            baby = new Scout(space);
            space.addFriendly(baby);
            return baby;
        }else{
            baby = new Soldier(space);
            space.addFriendly(baby);
            return baby;
        }
    }
    
    public void eat(){
        
        //try eating, if no food available
        if (!space.decrementFood())
            
            //then die
            die();
    }

//    @Override
//    public void die() {
//        super.die();
//        Simulation.end();
//    }
    
    
}
