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
