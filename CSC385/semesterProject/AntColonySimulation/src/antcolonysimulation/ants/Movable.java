/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.ants;

import antcolonysimulation.environment.Direction;
/**
 *
 * @author nathan
 */
public interface Movable {
    public void moveTo(Direction next);
    public Direction chooseDirection();
}