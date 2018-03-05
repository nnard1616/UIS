/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.ants.enemy;

import antcolonysimulation.ants.Actionable;
import antcolonysimulation.ants.Lifespan;
import antcolonysimulation.ants.Movable;
import antcolonysimulation.environment.Direction;
import antcolonysimulation.environment.Space;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void moveTo(Space space) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Direction chooseDirection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void attack(){
        throw new UnsupportedOperationException("not supported yet.");
    }
    
    
}
