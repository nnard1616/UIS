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
 *
 * @author nathan
 */
public class Space {
    
    private Map<Integer, Enemy> enemyAnts;
    private Map<Integer, Friendly> friendlyAnts;
    private int scoutCount = 0;
    private int foragerCount = 0;
    private int soldierCount = 0;
    private int queenCount   = 0;
    private int balaCount    = 0;
    private int food;
    private final int[] coordinates;
    private int pheromone = 0;
    private boolean explored = false;
    private HashMap<Direction, Space> neighbors;
    
    
    /**************************************************************************/
    
    public Space(int x, int y, int food){
        this.enemyAnts = new HashMap<>();
        this.friendlyAnts = new HashMap<>();
        this.food = food;
        this.coordinates = new int[] {x,y};
        neighbors = new HashMap<>();
    }
    
    public void addNeighbor(Direction direction, Space space){
        neighbors.put(direction, space);
    }
    
    public boolean containsEnemies(){
        return !enemyAnts.isEmpty();
    }
    
    public boolean containsFriendlies(){
        return !friendlyAnts.isEmpty();
    }
    
    public boolean isExplored(){
        return explored;
    }
    
    public void addEnemy(Enemy enemy){
        this.enemyAnts.put(enemy.getUID(), enemy);
        if (enemy.getClass().equals(Bala.class))
            balaCount++;
    }
    
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
    
    public Enemy popEnemy(Integer UID){
        try{
            Enemy enemy = this.enemyAnts.remove(UID);
            
            if (enemy.getClass().equals(Bala.class))
                balaCount--;
            
            return enemy;
        }catch(NullPointerException n){
            System.out.println("tried popping enemy not in hashmap");
            return null;
        }
    }
    
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

    public Enemy getEnemy(int UID){
        try{
            return this.enemyAnts.get(UID); 
        }catch(NullPointerException n){
            System.out.println("tried getting enemy not in hashmap");
            return null;
        }
    }
    
    public Friendly getFriendly(int UID){
        try{
            return this.friendlyAnts.get(UID); 
        }catch(NullPointerException n){
            System.out.println("tried getting friendly not in hashmap");
            return null;
        }
    }
    
    public Set<Integer> getEnemiesUIDs(){
        return enemyAnts.keySet();
    }
    
    public Set<Integer> getFriendliesUIDs(){
        return friendlyAnts.keySet();
    }
    
    public int getFood() {
        return food;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public int getPheromone() {
        return pheromone;
    }

    public int getScoutCount() {
        return scoutCount;
    }

    public int getForagerCount() {
        return foragerCount;
    }

    public int getSoldierCount() {
        return soldierCount;
    }

    public int getQueenCount() {
        return queenCount;
    }

    public int getBalaCount() {
        return balaCount;
    }
    
    public Set<Direction> getNeighbors(){
        return this.neighbors.keySet();
    }
    
    public Space getNeighbor(Direction d){
        return this.neighbors.get(d);
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void setPheromone(int pheromone) {
        this.pheromone = pheromone;
    }
    
    public void setExplored(boolean b){
        this.explored = b;
    }
    
    public boolean decrementFood(){
        if (food == 0)
            return false;
        food--;
        return true;
    }

    public Map<Integer, Enemy> getEnemyAnts() {
        return enemyAnts;
    }
    
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
    
    @Override
    public String toString(){
        return "(" + coordinates[0] + ", " + coordinates[1] + ")";
    }
}
