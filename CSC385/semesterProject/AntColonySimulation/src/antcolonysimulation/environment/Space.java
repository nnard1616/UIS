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

import antcolonysimulation.ants.enemy.Bala;
import antcolonysimulation.ants.enemy.Enemy;
import antcolonysimulation.ants.friendly.Forager;
import antcolonysimulation.ants.friendly.Friendly;
import antcolonysimulation.ants.friendly.Scout;
import antcolonysimulation.ants.friendly.Soldier;
import dataStructures.HashMap;
import dataStructures.List;

/**
 * Space is a unit of the Environment that can contain food, pheromone, and 
 * ants.  Spaces are also aware of neighboring Spaces.
 * @author nathan
 */
public class Space implements Comparable<Space>{   
    
    /**************************************************************************/
    /*  Attributes                                                            */
    /**************************************************************************/
    
    private HashMap enemyAnts;
    private HashMap friendlyAnts;
    private int scoutCount = 0;
    private int foragerCount = 0;
    private int soldierCount = 0;
    private int queenCount   = 0;
    private int balaCount    = 0;
    private int food;
    private final int x;  //horizontal coordinate
    private final int y;  //vertical coordinate
    private int pheromone = 0;
    private boolean explored = false;
    private HashMap neighbors;

    
    /**************************************************************************/
    /*  Constructor                                                           */
    /**************************************************************************/
    
    /**
     * Creates new Space object with user provided coordinates, (x,y), and 
     * food.
     * 
     * @param x     Horizontal coordinate.
     * @param y     Vertical coordinate.
     * @param food  Amount of food to be present on space.
     */
    public Space(int x, int y, int food){
        this.enemyAnts = new HashMap();
        this.friendlyAnts = new HashMap();
        this.food = food;
        this.x = x;
        this.y = y;
        neighbors = new HashMap();
    }
    
    
    /**************************************************************************/
    /*  Getters                                                               */
    /**************************************************************************/
    
    /**
     * Returns reference to Enemy with the user provided UID, if it is on
     * this space.  
     * 
     * @param UID  Unit Identification Number, int
     * @return     Enemy if present, null if not present.
     */
    public Enemy getEnemy(int UID){
        try{
            return (Enemy)this.enemyAnts.get(UID); 
        }catch(NullPointerException n){
            System.out.println("tried getting enemy not in hashmap");
            return null;
        }
    }
    
    /**
     * Returns reference to Friendly with the user provided UID, if it is on
     * this space.  
     * 
     * @param UID  Unit Identification Number, int
     * @return     Friendly if present, null if not present.
     */
    public Friendly getFriendly(int UID){
        try{
            return (Friendly)this.friendlyAnts.get(UID); 
        }catch(NullPointerException n){
            System.out.println("tried getting friendly not in hashmap");
            return null;
        }
    }
    
    /**
     * Returns List of Integers representing the UIDs of the Enemies on this 
     * Space.
     * 
     * @return List of Integers
     */
    public List getEnemiesUIDs(){
        return enemyAnts.keyList();
    }
    
    /**
     * Returns List of Integers representing the UIDs of the Friendlies on this 
     * Space.
     * 
     * @return List of Integers
     */
    public List getFriendliesUIDs(){
        return friendlyAnts.keyList();
    }
    
    /**
     * Returns amount of food on Space.
     * @return int
     */
    public int getFood() {
        return food;
    }

    /**
     * Returns int that represents horizontal coordinate of Space in 
     * Environment.
     * @return int.
     */
    public int getX() {
        return x;
    }
    
    /**
     * Returns int that represents vertical coordinate of Space in 
     * Environment.
     * @return int.
     */
    public int getY() {
        return y;
    }

    /**
     * Returns amount of pheromone on Space.
     * @return int
     */
    public int getPheromone() {
        return pheromone;
    }

    /**
     * Returns number of Scouts on Space.
     * @return int
     */
    public int getScoutCount() {
        return scoutCount;
    }

    /**
     * Returns number of Foragers on Space.
     * @return int
     */
    public int getForagerCount() {
        return foragerCount;
    }

    /**
     * Returns number of Soldiers on Space.
     * @return int
     */
    public int getSoldierCount() {
        return soldierCount;
    }

    /**
     * Returns number of Queens on Space.
     * @return int
     */
    public int getQueenCount() {
        return queenCount;
    }

    /**
     * Returns number of Enemies on Space.
     * @return int
     */
    public int getEnemyCount() {
        return enemyAnts.size();
    }
    
    /**
     * Returns Set of Directions associated with the Neighbors of this Space.
     * @return  Set of Direction objects.
     */
    public List getNeighborsDirections(){
        return this.neighbors.keyList();
    }
    
    /**
     * Returns Space reference of neighbor located at user provided Direction
     * relative to this Space.
     * 
     * @param d     Direction, where the neighbor is located relative to this 
     *              Space.
     * @return      Space, the neighbor at that Direction.
     */
    public Space getNeighbor(Direction d){
        return (Space)this.neighbors.get(d);
    }
    
    
    /**************************************************************************/
    /*  Setters                                                               */
    /**************************************************************************/
    
    /**
     * Sets Space's food counter to user provided int.
     * @param food  int
     */
    public void setFood(int food) {
        this.food = food;
    }

    /**
     * Sets Space's pheromone counter to user provided int.
     * @param pheromone  int
     */
    public void setPheromone(int pheromone) {
        this.pheromone = pheromone;
    }
    
    /**
     * Sets Space's 'explored' attribute to the user provided boolean.
     * @param b  Explored (True) or Not Explored (False)
     */
    public void setExplored(boolean b){
        this.explored = b;
    }
    
    /**
     * Attempts to decrement Space's food counter by 1.  Returns True if 
     * successful, False otherwise.
     * @return  boolean, True or False.
     */
    public boolean decrementFood(){
        if (food == 0)
            return false;
        food--;
        return true;
    }
    
    
    /**************************************************************************/
    /*  Testers                                                               */
    /**************************************************************************/

    /**
     * Tests if Space contains Enemy units.
     * @return  boolean, True or False.
     */
    public boolean containsEnemies(){
        return !enemyAnts.isEmpty();
    }
    
    /**
     * Tests if Space contains Friendly units.
     * @return  boolean, True or False.
     */
    public boolean containsFriendlies(){
        return !friendlyAnts.isEmpty();
    }
    
    /**
     * Tests if Space is explored by Friendlies.
     * @return  boolean, True or False.
     */
    public boolean isExplored(){
        return explored;
    }
    
    
    /**************************************************************************/
    /*  HashMap Modifiers                                                     */
    /**************************************************************************/
    
    /**
     * Links Space to user provided Space object and associates it with the user
     * provided Direction object. More specifically, adds Space to a HashMap 
     * keyed by Direction objects.
     * 
     * @param direction Direction neighbor is located relative to this Space.
     * @param space     Space object that represents the neighbor of this Space. 
     */
    public void addNeighbor(Direction direction, Space space){
        neighbors.add(direction, space);
    }
    
    /**
     * Adds user provided Enemy ant object to Space.
     * @param enemy  Enemy ant object to be added.
     */
    public void addEnemy(Enemy enemy){
        this.enemyAnts.add(enemy.getUID(), enemy);
        if (enemy.getClass().equals(Bala.class))
            balaCount++;
    }
    
    /**
     * Attempts to remove Enemy ant with user provided UID, returns the 
     * Enemy ant if successfully removed from Space.
     * 
     * @param UID   Unit Identification Number of Enemy ant to be removed.
     * @return      Enemy object upon successful removal.
     */
    public Enemy popEnemy(Integer UID){
        Enemy enemy = (Enemy)this.enemyAnts.get(UID);

        if (this.enemyAnts.remove(UID)){
            if (enemy.getClass().equals(Bala.class))
                balaCount--;
            return enemy;
        } else{
            System.out.println("Tried removing enemy not on space.");
            return null;
        }
    }
    
    /**
     * Adds user provided Friendly ant object to Space.
     * @param friendly  Friendly ant object to be added.
     */
    public void addFriendly(Friendly friendly){
        this.friendlyAnts.add(friendly.getUID(), friendly);
        if (friendly.getClass().equals(Forager.class))
            foragerCount++;
        else if (friendly.getClass().equals(Scout.class))
            scoutCount++;
        else if (friendly.getClass().equals(Soldier.class))
            soldierCount++;
        else
            queenCount++;
    }
    
    /**
     * Attempts to remove Friendly ant with user provided UID, returns the 
     * Friendly ant if successfully removed from Space.
     * 
     * @param UID   Unit Identification Number of Friendly ant to be removed.
     * @return      Friendly object upon successful removal.
     */
    public Friendly popFriendly(int UID){
        Friendly friendly = (Friendly)this.friendlyAnts.get(UID);

        if (this.friendlyAnts.remove(UID)){
            if (friendly.getClass().equals(Forager.class))
                foragerCount--;
            else if (friendly.getClass().equals(Scout.class))
                scoutCount--;
            else if (friendly.getClass().equals(Soldier.class))
                soldierCount--;
            else
                queenCount--;
            return friendly;
        } else{
            System.out.println("Tried removing friendly not on space.");
            return null;
        }
    }
    
    /**
     * Clears Space's containers and counters set to 0.  Marks it unexplored as
     * well.
     */
    public void clear(){
        scoutCount   = 0;
        foragerCount = 0;
        soldierCount = 0;
        queenCount   = 0;
        balaCount    = 0;
        food         = 0;
        pheromone    = 0;
        friendlyAnts.clear();
        enemyAnts.clear();
        explored     = false;
    }
    
    /**************************************************************************/
    /*  Overrides                                                             */
    /**************************************************************************/
    
    /**
     * String representation of Space, just its coordinates:  (x,y)
     * @return  String, (x,y)
     */
    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
    
    /**
     * Determines if Space is equal to query Space.
     * @return  boolean, True or False.
     */
    @Override
    public boolean equals(Object o){
        if (o.getClass() != Space.class)
            return false;
        else{
            return ((Integer)x).equals(((Space)o).getX()) && 
                   ((Integer)y).equals(((Space)o).getY());
        }
    }

    /**
     * Determines how Space should compare to query Space, used for 
     * AVLTree use.  X coordinate trumps Y coordinate.
     * @return  int, -1, 0, or 1
     */
    @Override
    public int compareTo(Space o) {
        if (x > o.getX() )
            return 1;
        if (x == o.getX() && y > o.getY() ) 
            return 1;
        if (x == o.getX() && y == o.getY() ) 
            return 0;
        return -1;
    }
}
