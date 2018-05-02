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
package antcolonysimulation.ants.friendly;

import antcolonysimulation.ants.Ant;
import antcolonysimulation.ants.Lifespan;
import antcolonysimulation.environment.Space;

/**
 *  Subclass of Ants that are friendly in the simulation.
 * @author nathan
 */
public class Friendly extends Ant{

    /**
     * Default constructor of Friendly object. Additional side effects include 
     * setting the Unit Identification Number (UID) and its age initialized 
     * to 0.
     * 
     * @param lifespan How old the ant can live, see Lifespan enumeration.
     * @param space    The space on which the ant starts.
    */
    public Friendly(Lifespan lifespan, Space space){
        super(lifespan, space);
        space.addFriendly(this);
    }
    
    /**
     * Kills the Friendly object.
     */
    @Override
    public void die(){
        super.die();
        this.space.popFriendly(getUID());
    }
}
