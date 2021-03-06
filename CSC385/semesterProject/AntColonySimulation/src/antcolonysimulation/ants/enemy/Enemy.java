/* 
 * Copyright (C) 2018 Nathan Nard
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package antcolonysimulation.ants.enemy;

import antcolonysimulation.ants.Ant;
import antcolonysimulation.ants.Lifespan;
import antcolonysimulation.environment.Space;

/**
 * Sub class of Ant that represents enemies of the friendly ants in the 
 * simulation.
 * 
 * @author nathan
 */
public class Enemy extends Ant{
    
    /**
     * Default constructor of Enemy object.  Calls the Ant constructor and 
     * also updates the Space's list of contained Enemies to include this newly
     * made Enemy.
     * @param lifespan
     * @param space
     */
    public Enemy(Lifespan lifespan, Space space){
        super(lifespan, space);
        space.addEnemy(this);
    }
    
    /**
     * Same as Ant's die method, but also updates the Space's list of contained
     * Enemies to remove this Enemy.
     */
    @Override
    public void die(){
        super.die();
        this.space.popEnemy(getUID());
    }
}
