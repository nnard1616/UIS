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

/**
 * Enumeration containing the different lifespans of the different types of 
 * ants.
 * QUEEN --> 73000 turns (20 years)
 * OTHER --> 3650  turns (1 year)
 * 
 * @author nathan
 */
public enum Lifespan {
    QUEEN(73000),
    OTHER(3650);
    
    private final int ls;
    Lifespan(int ls) { this.ls = ls;}

    /**
     * Returns the value that corresponds to the Lifespan identifier.
     * @return int
     */
    public int getValue() { return ls; }
}
