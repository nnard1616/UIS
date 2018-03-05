/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.ants.friendly;

import antcolonysimulation.ants.Ant;
import antcolonysimulation.ants.Lifespan;
import antcolonysimulation.environment.Space;

/**
 *
 * @author nathan
 */
public class Friendly extends Ant{
    public Friendly(Lifespan lifespan, Space space){
        super(lifespan, space);
        space.addFriendly(this);
    }
    
    @Override
    public void die(){
        super.die();
        this.space.popFriendly(getUID());
    }
}
