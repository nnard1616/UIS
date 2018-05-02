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
package antcolonysimulation.ants;

import antcolonysimulation.environment.Direction;
import antcolonysimulation.environment.Space;

/**
 * Interface that represents objects that are able to move in the simulation.
 * @author nathan
 */
public interface Movable {

    /**
     * Method to move the Movable actor to the specified Space.
     * @param space     Space object that the Movable will move to.
     */
    public void moveTo(Space space);

    /**
     * Determines which Direction to move in.
     * @return      Direction object that represents direction to move in grid.
     */
    public Direction chooseDirection();
}
