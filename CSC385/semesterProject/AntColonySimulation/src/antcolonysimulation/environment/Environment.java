/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.environment;

import antcolonysimulation.simulation.Randomizer;

/**
 *
 * @author nathan
 */
public class Environment {
    private Space[][] grid;
    private final int SIZE;
    private final int FOODMIN;
    private final int FOODMAX;
    
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
        
        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++)
                grid[i][j] = new Space(i, j, generateFood() );
        
        addNeighbors();
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
    
    private int generateFood(){
        double roll = Randomizer.Do.nextDouble();
        if (roll <= 0.25)
            //between 500 and 1000 units of food
            return Randomizer.Do.nextInt(FOODMIN+1) + (FOODMAX-FOODMIN); 
        else
            return 0;
    }
    
}
