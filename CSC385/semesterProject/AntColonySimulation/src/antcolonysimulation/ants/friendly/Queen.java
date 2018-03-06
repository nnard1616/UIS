/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.ants.friendly;

import antcolonysimulation.ants.Actionable;
import antcolonysimulation.ants.Lifespan;
import antcolonysimulation.environment.Direction;
import antcolonysimulation.environment.Space;
import antcolonysimulation.simulation.Randomizer;
import antcolonysimulation.simulation.Simulation;
import java.util.List;

/**
 *
 * @author nathan
 */
public class Queen extends Friendly implements Actionable{
    private List<Actionable> ants;
    
    public Queen(Space space, List<Actionable> ants){
        super(Lifespan.QUEEN, space);
        setActive(true);
        this.ants = ants;
        space.setExplored(true);
        for ( Direction d : space.getNeighbors())
            space.getNeighbor(d).setExplored(true);
        
    }
    
    @Override
    public void act(){
        
        //may not need this?
        if (!isAlive() || !isActive())
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
    
    public void hatchAnt(){
        double roll = Randomizer.Give.nextDouble();
        
        if (roll <= 0.50){
            hatchForager(1);
        }else if (roll <= 0.75){
            hatchScout(1);
        }else{
            hatchSoldier(1);
        }
    }
    
    public void hatchForager(int n){
        for (int i = 0; i < n; i++ )
            ants.add( new Forager(space));
    }
    
    public void hatchScout(int n){
        for (int i = 0; i < n; i++ )
            ants.add( new Scout(space));
    }
    
    public void hatchSoldier(int n){
        for (int i = 0; i < n; i++ )
            ants.add( new Soldier(space));
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
