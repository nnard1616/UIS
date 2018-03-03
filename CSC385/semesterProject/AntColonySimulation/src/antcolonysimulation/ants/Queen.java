/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.ants;

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
    }
    
    @Override
    public void act(){
        
        //Can't do anything if we're dead, if dead end simulation
        if (!isAlive()){
            Simulation.end();
            return;
        }
        
        eat();
        
        //If no food, queen died, end simulation
        if (!isAlive()){
            Simulation.end();
            return;
        }
        
        //Make baby at start of day.
        if (Simulation.getTurn() % 10 == 0)
            hatchAnt();
    }
    
    public Friendly hatchAnt(){
        double roll = Randomizer.Do.nextDouble();
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
}
