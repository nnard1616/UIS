/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.environment;

import antcolonysimulation.ants.Enemy;
import antcolonysimulation.ants.Friendly;
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
    
    public void toggleExplored(){
        this.explored = !explored;
    }
    
    public void addEnemy(Enemy enemy){
        this.enemyAnts.put(enemy.getUID(), enemy);
    }
    
    public void addFriendly(Friendly friendly){
        this.friendlyAnts.put(friendly.getUID(), friendly);
    }
    
    public Enemy popEnemy(Integer UID){
        try{
            return this.enemyAnts.remove(UID);
        }catch(NullPointerException n){
            System.out.println("tried popping enemy not in hashmap");
            return null;
        }
    }
    
    public Friendly popFriendly(int UID){
        try{
            return this.friendlyAnts.remove(UID); 
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
    
    public HashMap<Direction, Space> getNeighbors(){
        return this.neighbors;
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
    
    public boolean decrementFood(){
        if (food == 0)
            return false;
        food--;
        return true;
    }
    
    //TODO implement and test
    public void decrementPheromone(){
        ;
    }

    public Map<Integer, Enemy> getEnemyAnts() {
        return enemyAnts;
    }
    
    
}