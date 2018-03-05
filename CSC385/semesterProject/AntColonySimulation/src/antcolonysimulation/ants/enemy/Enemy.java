/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.ants.enemy;

import antcolonysimulation.ants.Ant;
import antcolonysimulation.ants.Lifespan;
import antcolonysimulation.environment.Space;

/**
 *
 * @author nathan
 */
public class Enemy extends Ant{
    public Enemy(Lifespan lifespan, Space space){
        super(lifespan, space);
        space.addEnemy(this);
    }
    
    @Override
    public void die(){
        super.die();
        this.space.popEnemy(getUID());
    }
}