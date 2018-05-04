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
package antcolonysimulation.environment;

import antcolonysimulation.simulation.Randomizer;
import dataStructures.ArrayList;
import dataStructures.Doublet;

/**
 *  Environment class is the abstract physical world that the simulation ants
 * exist in.  Backed by a 2D array of Space objects.
 * 
 * @author nathan
 */
public class Environment {
    
    /**************************************************************************/
    /*  Attributes                                                            */
    /**************************************************************************/
    
    private Space[][] grid;
    
    //Contains references to Spaces on the border, for Bala ant generation.
    private ArrayList borderSpaces; 
    
    private final int SIZE;
    private final int FOODMIN;
    private final int FOODMAX;
    
    
    /**************************************************************************/
    /*  Constructors                                                          */
    /**************************************************************************/
    
    /**
     * Default constructor, creates Environment object that is 27x27 spaces,
     * min food on space of 500, max food on space of 1000.  When spaces are 
     * created, there's a 25% chance food will be on it, and it will be a random
     * number between foodmin and foodmax.
     */
    public Environment(){
        this(27, 500, 1000);//default grid size is 27x27
    }
    
    /**
     * Constructor with user defined size, creates Environment object that is 
     * size x size spaces, with min food on space of 500, max food on space of 
     * 1000. When spaces are created, there's a 25% chance food will be on it, 
     * and it will be a random number between foodmin and foodmax.
     * 
     * @param size  Width and Height of the Environment grid of spaces.
     */
    public Environment(int size){
        this(size, 500, 1000);
    }
    
    /**
     * Constructor with user defined size, foodmin, and foodmax. Creates 
     * Environment object that is size x size spaces.  When spaces are created, 
     * there's a 25% chance food will be on it, and it will be a random number 
     * between foodmin and foodmax.
     * 
     * @param size      Width and Height of the Environment grid of spaces.
     * @param foodmin   Minimum food that can appear on a space.
     * @param foodmax   Maximum food that can appear on a space.
     */
    public Environment(int size, int foodmin, int foodmax){
        this.SIZE = size;
        this.FOODMIN = foodmin;
        this.FOODMAX = foodmax;
        
        grid = new Space[SIZE][SIZE];
        borderSpaces = new ArrayList();
        
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                grid[i][j] = new Space(i, j, generateFood() ); //create
                if (i == 0 || j == 0 || i == SIZE-1 || j == SIZE-1)
                    borderSpaces.add(grid[i][j]); //store reference if border
            }
        }
                
        // Connect neighbors to each other.
        addNeighbors();
    }
    
    
    /**************************************************************************/
    /*  Private Initialization Helpers                                        */
    /**************************************************************************/
    
    /**
     * Method that populates each Space object's neighbor hashmaps with 
     * references to their adjacent neighboring Spaces.
     */
    private void addNeighbors(){
        
        //cycle through the coordinates in the grid.
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                
                /* poleNeighbors contains N, S, E, or W if that neighbor exists
                 * at the current location in the grid.
                 *
                 * EG: at (0,0), there are only neighbors to the S and E.
                 *     therefore, poleNeighbors = "SE".  
                 *
                 * Later, this will be used to connect Space (0,0) to 
                 * S, E, and SE neighbors via the neighbor hashmap.
                 */
                String poleNeighbors = "";
                if (i >= 0 && i <  SIZE-1)
                    poleNeighbors += "S";
                if (i >  0 && i <= SIZE-1)
                    poleNeighbors += "N";
                if (j >= 0 && j <  SIZE-1)
                    poleNeighbors += "E";
                if (j >  0 && j <= SIZE-1)
                    poleNeighbors += "W";
                
                Doublet n  = Direction.N.getValue();
                Doublet ne = Direction.NE.getValue();
                Doublet e  = Direction.E.getValue();
                Doublet se = Direction.SE.getValue();
                Doublet s  = Direction.S.getValue();
                Doublet sw = Direction.SW.getValue();
                Doublet w  = Direction.W.getValue();
                Doublet nw = Direction.NW.getValue();
                
                /* This mess uses the stored doublets in the Direction 
                 * objects/enumeration to compute the coordinates
                 * of the neighboring Spaces, and then add those neighbors
                 * to the neighbor hashmap keyed by the corresponding Direction.
                 *
                 * Why?  To enable easy refactoring in the future should I 
                 * decide to change what the Directions N, W, etc mean.
                 * Simply change the values in Direction enumeration.
                 */
                if (poleNeighbors.contains("N")){
                    grid[i][j].addNeighbor(Direction.N, 
                            grid[ i+(Integer)n.getX() ][ j+(Integer)n.getY() ]);
                    
                    if (poleNeighbors.contains("E")){
                        grid[i][j].addNeighbor(Direction.E, 
                            grid[ i+(Integer)e.getX() ][ j+(Integer)e.getY() ]);
                        
                        grid[i][j].addNeighbor(Direction.NE, 
                          grid[ i+(Integer)ne.getX() ][ j+(Integer)ne.getY() ]);
                    }
                    if (poleNeighbors.contains("W")){
                        grid[i][j].addNeighbor(Direction.W, 
                            grid[ i+(Integer)w.getX() ][ j+(Integer)w.getY() ]);
                        
                        grid[i][j].addNeighbor(Direction.NW, 
                          grid[ i+(Integer)nw.getX() ][ j+(Integer)nw.getY() ]);
                    }
                }
                if (poleNeighbors.contains("S")){
                    grid[i][j].addNeighbor(Direction.S, 
                            grid[ i+(Integer)s.getX() ][ j+(Integer)s.getY() ]);
                    
                    if (poleNeighbors.contains("E")){
                        grid[i][j].addNeighbor(Direction.E, 
                            grid[ i+(Integer)e.getX() ][ j+(Integer)e.getY() ]);
                        
                        grid[i][j].addNeighbor(Direction.SE, 
                          grid[ i+(Integer)se.getX() ][ j+(Integer)se.getY() ]);
                    }
                    if (poleNeighbors.contains("W")){
                        grid[i][j].addNeighbor(Direction.W, 
                            grid[ i+(Integer)w.getX() ][ j+(Integer)w.getY() ]);
                        
                        grid[i][j].addNeighbor(Direction.SW, 
                          grid[ i+(Integer)sw.getX() ][ j+(Integer)sw.getY() ]);
                    }
                }
            }
        }
    }
    
    /**
     * Method used to determine if food should appear on a space or not.  If so,
     * randomly choose a value between FOODMIN and FOODMAX.
     * 
     * @return  int, 0 or random integer between FOODMIN and FOODMAX.
     */
    private int generateFood(){
        double roll = Randomizer.Give.nextDouble();
        if (roll <= 0.25)
            //between 500 and 1000 units of food by default
            return Randomizer.Give.nextInt(FOODMIN+1) + (FOODMAX-FOODMIN); 
        else
            return 0;
    }
  
    /**************************************************************************/
    /*  Getters                                                               */
    /**************************************************************************/
    
    /**
     * Returns reference to a Space on the border of the Environment at the user
     * provided index of the Environment's ArrayList borderSpaces attribute.
     * 
     * @param i     index of the Border Space in ArrayList attribute.
     * @return      Space on the border at given index.
     */
    public Space getBorder(int i){
        if (i < borderCount())
            return (Space)borderSpaces.get(i);
        else
            throw new IndexOutOfBoundsException("There are not that many border"
                    + "spaces! Given: " + i + " Actual: " + borderCount());
    }
    
    /**
     * Returns reference to the 2D array of Space references.
     * @return  Space[][]
     */
    public Space[][] getGrid() {
        return grid;
    }

    /**
     * Returns the SIZE attribute, which represents length of one side of the 
     * Environment grid.
     * 
     * @return  int
     */
    public int getSIZE() {
        return SIZE;
    }
    
    /**
     * Returns Space reference at the user provided (x,y) coordinates in the 
     * 2D Array of Space references.
     * 
     * @param x     Horizontal coordinate.
     * @param y     Vertical coordinate.
     * @return      Space at (x,y).
     */
    public Space getSpace(int x, int y){
        try{
            return this.grid[x][y];
        }catch(ArrayIndexOutOfBoundsException oob){
            System.out.println("out of bounds indices were passed to "
                             + "Environment.getSpace: " + x +", " + y );
            return null;
        }
    }
    
    /**
     * Returns the number of border Spaces in the Environment.
     * @return int
     */
    public int borderCount(){
        return borderSpaces.size();
    }
    

    /**************************************************************************/
    /*  Setters                                                               */
    /**************************************************************************/
    
    /**
     * Makes all spaces revealed, mostly used for debugging purposes.
     */
    public void revealAll(){
        for (Space[] row : grid)
            for (Space s: row)
                s.setExplored(true);
    }
    
    /**
     * Sets the food counters of all Spaces to the user provided int, f.
     * Mostly used for debugging purposes.
     * 
     * @param f  Value to set all food counters to.
     */
    public void setAllFood(int f){
        for (Space[] row : grid)
            for (Space s: row)
                s.setFood(f);
    }
    
    /**
     * Reduces the pheromone counters of all Spaces in the Environment by half.
     */
    public void halveAllPheromone(){
        for (Space[] row : grid)
            for (Space s: row)
                s.setPheromone(s.getPheromone()/2);
    }
    
    
    /**************************************************************************/
    /*  String Representation Functions (Debugging)                           */
    /**************************************************************************/
    
    /**
     * Returns a string representation of Environment that displays Spaces
     * as their pheromone counters.  Mostly used for debugging.
     * 
     * @return  String of Pheromone counters.
     */
    public String pheromonesToString(){
        String result = "";
        for (Space[] row : grid){
            for (Space s : row){
                result += s.getPheromone() + "\t";
            }
            result += "\n";
        }
        return result;
    }
    
    /**
     * Returns a string representation of Environment that displays Spaces
     * as their food counters.  Mostly used for debugging.
     * 
     * @return  String of Food counters.
     */
    public String foodsToString(){
        String result = "";
        for (Space[] row : grid){
            for (Space s : row){
                result += s.getFood()+ "\t";
            }
            result += "\n";
        }
        return result;
    }
}
