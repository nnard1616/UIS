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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nathan
 */
public class Environment {
    
    private Space[][] grid;
    private List<Space> borderSpaces;
    private final int SIZE;
    private final int FOODMIN;
    private final int FOODMAX;
    
    /**************************************************************************/
    
    public Environment(){
        this(27, 500, 1000);//default grid size is 27x27
    }
    
    public Environment(int size){
        this(size, 500, 1000);
    }
    
    public Environment(int size, int foodmin, int foodmax){
        this.SIZE = size;
        this.FOODMIN = foodmin;
        this.FOODMAX = foodmax;
        
        grid = new Space[SIZE][SIZE];
        borderSpaces = new ArrayList<>();
        
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                grid[i][j] = new Space(i, j, generateFood() );
                if (i == 0 || j == 0 || i == SIZE-1 || j == SIZE-1)
                    borderSpaces.add(grid[i][j]);
            }
        }
                
        
        addNeighbors();
    }
    
    public Space getBorder(int i){
        return borderSpaces.get(i);
    }
    
    public Space[][] getGrid() {
        return grid;
    }

    public int getSIZE() {
        return SIZE;
    }
    
    public Space getSpace(int x, int y){
        try{
            return this.grid[x][y];
        }catch(ArrayIndexOutOfBoundsException oob){
            System.out.println("out of bounds indices were passed to Environment.getSpace");
            return null;
        }
    }
    
    public int borderCount(){
        return borderSpaces.size();
    }
    
    private void addNeighbors(){
        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++){
                
                String ns = "";
                if (i >= 0 && i <  SIZE-1)
                    ns += "S";
                if (i >  0 && i <= SIZE-1)
                    ns += "N";
                if (j >= 0 && j <  SIZE-1)
                    ns += "E";
                if (j >  0 && j <= SIZE-1)
                    ns += "W";
                
                int[] n  = Direction.N.getValue();
                int[] ne = Direction.NE.getValue();
                int[] e  = Direction.E.getValue();
                int[] se = Direction.SE.getValue();
                int[] s  = Direction.S.getValue();
                int[] sw = Direction.SW.getValue();
                int[] w  = Direction.W.getValue();
                int[] nw = Direction.NW.getValue();
                
                if (ns.contains("N")){
                    grid[i][j].addNeighbor(Direction.N, grid[ i+n[0] ][ j+n[1] ]);
                    if (ns.contains("E")){
                        grid[i][j].addNeighbor(Direction.E, grid[ i+e[0] ][ j+e[1] ]);
                        grid[i][j].addNeighbor(Direction.NE, grid[ i+ne[0] ][ j+ne[1] ]);
                    }
                    if (ns.contains("W")){
                        grid[i][j].addNeighbor(Direction.W, grid[ i+w[0] ][ j+w[1] ]);
                        grid[i][j].addNeighbor(Direction.NW, grid[ i+nw[0] ][ j+nw[1] ]);
                    }
                }
                if (ns.contains("S")){
                    grid[i][j].addNeighbor(Direction.S, grid[ i+s[0] ][ j+s[1] ]);
                    if (ns.contains("E")){
                        grid[i][j].addNeighbor(Direction.E, grid[ i+e[0] ][ j+e[1] ]);
                        grid[i][j].addNeighbor(Direction.SE, grid[ i+se[0] ][ j+se[1] ]);
                    }
                    if (ns.contains("W")){
                        grid[i][j].addNeighbor(Direction.W, grid[ i+w[0] ][ j+w[1] ]);
                        grid[i][j].addNeighbor(Direction.SW, grid[ i+sw[0] ][ j+sw[1] ]);
                    }
                }
            }
    }
    
    public void revealAll(){
        for (Space[] row : grid)
            for (Space s: row)
                s.setExplored(true);
    }
    
    public void setAllFood(int f){
        for (Space[] row : grid)
            for (Space s: row)
                s.setFood(f);
    }
    
    private int generateFood(){
        double roll = Randomizer.Give.nextDouble();
        if (roll <= 0.25)
            //between 500 and 1000 units of food by default
            return Randomizer.Give.nextInt(FOODMIN+1) + (FOODMAX-FOODMIN); 
        else
            return 0;
    }
    
    public void halveAllPheromone(){
        for (Space[] row : grid)
            for (Space s: row)
                s.setPheromone(s.getPheromone()/2);
    }
    
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
