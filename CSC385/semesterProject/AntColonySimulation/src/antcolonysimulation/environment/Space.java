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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Space is a unit of the Environment that can contain food, pheromone, and 
 * ants.  Spaces are also aware of neighboring Spaces.
 * @author nathan
 */
public class Space {   
    
    /**************************************************************************/
    /*  Attributes                                                            */
    /**************************************************************************/
    
    private Map<Integer, Enemy> enemyAnts;
    private Map<Integer, Friendly> friendlyAnts;
    private int scoutCount = 0;
    private int foragerCount = 0;
    private int soldierCount = 0;
    private int queenCount   = 0;
    private int enemyCount    = 0;
    private int food;
    private final int[] coordinates;
    private int pheromone = 0;
    private boolean explored = false;
    private Map<Direction, Space> neighbors;

    
    /**************************************************************************/
    /*  Constructor                                                           */
    /**************************************************************************/
    
    /**
     * Creates new Space object with user provided cooredinates, (x,y), and 
     * food.
     * 
     * @param x     Horizontal coordinate.
     * @param y     Vertical coordinate.
     * @param food  Amount of food to be present on space.
     */
    public Space(int x, int y, int food){
        this.enemyAnts = new HashMap<>();
        this.friendlyAnts = new HashMap<>();
        this.food = food;
        this.coordinates = new int[] {x,y};
        neighbors = new HashMap<>();
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
            return this.enemyAnts.get(UID); 
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
            return this.friendlyAnts.get(UID); 
        }catch(NullPointerException n){
            System.out.println("tried getting friendly not in hashmap");
            return null;
        }
    }
    
    /**
     * Returns Set of Integers representing the UIDs of the Enemies on this 
     * Space.
     * 
     * @return Set of Integers
     */
    public Set<Integer> getEnemiesUIDs(){
        return enemyAnts.keySet();
    }
    
    /**
     * Returns Set of Integers representing the UIDs of the Friendlies on this 
     * Space.
     * 
     * @return Set of Integers
     */
    public Set<Integer> getFriendliesUIDs(){
        return friendlyAnts.keySet();
    }
    
    /**
     * Returns amount of food on Space.
     * @return int
     */
    public int getFood() {
        return food;
    }

    /**
     * Returns doublet representing the coordinates of Space in Environment.
     * @return doublet of ints.
     */
    public int[] getCoordinates() {
        return coordinates;
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
        return enemyCount;
    }
    
    /**
     * Returns Set of Directions associated with the Neighbors of this Space.
     * @return  Set of Direction objects.
     */
    public Set<Direction> getNeighborsDirections(){
        return this.neighbors.keySet();
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
        return this.neighbors.get(d);
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
        neighbors.put(direction, space);
    }
    
    /**
     * Adds user provided Enemy ant object to Space.
     * @param enemy  Enemy ant object to be added.
     */
    public void addEnemy(Enemy enemy){
        this.enemyAnts.put(enemy.getUID(), enemy);
        if (enemy.getClass().equals(Bala.class))
            enemyCount++;
    }
    
    /**
     * Attempts to remove Enemy ant with user provided UID, returns the 
     * Enemy ant if successfully removed from Space.
     * 
     * @param UID   Unit Identification Number of Enemy ant to be removed.
     * @return      Enemy object upon successful removal.
     */
    public Enemy popEnemy(Integer UID){
        try{
            Enemy enemy = this.enemyAnts.remove(UID);
            
            if (enemy.getClass().equals(Bala.class))
                enemyCount--;
            
            return enemy;
        }catch(NullPointerException n){
            System.out.println("tried popping enemy not in hashmap");
            return null;
        }
    }
    
    /**
     * Adds user provided Friendly ant object to Space.
     * @param friendly  Friendly ant object to be added.
     */
    public void addFriendly(Friendly friendly){
        this.friendlyAnts.put(friendly.getUID(), friendly);
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
        try{
            Friendly friendly = this.friendlyAnts.remove(UID);
            
            if (friendly.getClass().equals(Forager.class))
                foragerCount--;
            else if (friendly.getClass().equals(Scout.class))
                scoutCount--;
            else if (friendly.getClass().equals(Soldier.class))
                soldierCount--;
            else
                queenCount--;
            
            return friendly;
        }catch(NullPointerException n){
            System.out.println("tried popping friendly not in hashmap");
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
        enemyCount    = 0;
        food         = 0;
        pheromone    = 0;
        friendlyAnts.clear();
        enemyAnts.clear();
        explored     = false;
    }
    
    /**
     * String representation of Space, just its coordinates:  (x,y)
     * @return  String, (x,y)
     */
    @Override
    public String toString(){
        return "(" + coordinates[0] + ", " + coordinates[1] + ")";
    }
}
