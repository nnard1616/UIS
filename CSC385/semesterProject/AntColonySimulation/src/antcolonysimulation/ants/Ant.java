/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.ants;

import antcolonysimulation.environment.Space;

/**
 *
 * @author nathan
 */
public class Ant {
    private final int UID;
    private final int LIFESPAN;
    private int age;
    private int actions;
    private boolean alive = true;
    
    private static int antCount = 0;
    
    protected int[] coordinates;
    protected Space space;
    
    /**************************************************************************/
    
    
    public Ant(Lifespan lifespan, Space space){
        this.UID = this.antCount++;
        this.LIFESPAN = lifespan.getValue();
        this.age = 0;
        //TODO:  figure out the number of actions assignment.
        this.actions = 1;
        this.space = space;
    }
    
    public void incrementAge(){
        this.age++;
    }

    public int getUID() {
        return UID;
    }

    public int getLIFESPAN() {
        return LIFESPAN;
    }

    public int getAge() {
        return age;
    }

    public int getActions() {
        return actions;
    }

    public static int getAntCount() {
        return antCount;
    }

    public int[] getCoordinates() {
        return space.getCoordinates();
    }

    public Space getSpace() {
        return space;
    }
    
    public boolean isOld(){
        if (this.age > this.LIFESPAN)
            throw new IllegalStateException("Ants should not age past their lifespan!");
        return this.age == this.LIFESPAN;
    }
    
    public boolean isActive(){
        return this.actions > 0;
    }
    
    public boolean isAlive() {
        return this.alive;
    }

    public void setActions(int actions) {
        this.actions = actions;
    }
    
    public void setSpace(Space space) {
        this.space = space;
    }
    
    public void die(){
        this.alive = false;
        this.space.popFriendly(UID);
    }
}
