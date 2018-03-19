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
package antcolonysimulation.simulation;

import antcolonysimulation.ants.Actionable;
import antcolonysimulation.ants.Ant;
import antcolonysimulation.ants.enemy.Bala;
import antcolonysimulation.ants.friendly.Queen;
import antcolonysimulation.environment.Environment;
import antcolonysimulation.environment.Space;
import antsimgui.AntSimGUI;
import antsimgui.ColonyNodeView;
import antsimgui.ColonyView;
import antsimgui.SimulationEvent;
import antsimgui.SimulationEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author nathan
 */
public class Simulation implements SimulationEventListener, ActionListener{
    
    private static int turn = 0;
    private Environment environment;
    private List<Actionable> ants;
    private AntSimGUI gui;
    private ColonyNodeView[][] guiNodes;
    private Queen q;
    private int BOARDSIZE;
    private Timer timer;
    private boolean makeBalas = true;
    
    /**************************************************************************/
    /*  Constructors                                                          */
    /**************************************************************************/
    
    /**
     * Default constructor that makes a Simulation with a board size of 27.
     * Instantiating a Simulation object has the side effect of running the 
     * simulation GUI, but does not start the simulation until the appropriate
     * button is pressed in the GUI.
     */
    public Simulation(){
        this(27);
    }
    
    /**
     * Constructor that makes a Simulation with user supplied board size. 
     * Instantiating a Simulation object has the side effect of running the 
     * simulation GUI, but does not start the simulation until the appropriate
     * button is pressed in the GUI.
     * @param boardSize     Size of the simulation square board.
     */
    public Simulation(int boardSize){
        this.BOARDSIZE = boardSize;
        this.environment = new Environment(BOARDSIZE);
        this.ants = new LinkedList<>();
        this.gui = new AntSimGUI();
        gui.addSimulationEventListener(this);
        this.guiNodes = new ColonyNodeView[BOARDSIZE][BOARDSIZE];
        
        ColonyView cView = new ColonyView(BOARDSIZE,BOARDSIZE);
        for( int i = 0; i < BOARDSIZE; i++){
            for( int j = 0; j < BOARDSIZE; j++){
                guiNodes[i][j] = new ColonyNodeView();
                cView.addColonyNodeView(guiNodes[i][j], i, j);
            }
        }
        
        this.gui.initGUI(cView);
    }
    
    /**************************************************************************/
    /*  Overrides                                                             */
    /**************************************************************************/
    
    @Override
    public void simulationEventOccurred(SimulationEvent simEvent) {
        switch (simEvent.getEventType()){
            case SimulationEvent.NORMAL_SETUP_EVENT:
                normalSetup();
                break;
            case SimulationEvent.QUEEN_TEST_EVENT:
                queenTest();
                break;
            case SimulationEvent.SCOUT_TEST_EVENT:
                break;
            case SimulationEvent.FORAGER_TEST_EVENT:
                foragerTest();
                break;
            case SimulationEvent.SOLDIER_TEST_EVENT:
                soldierTest();
                break;
            case SimulationEvent.RUN_EVENT:
                runContinuously();
                break;
            case SimulationEvent.STEP_EVENT:
                runOnce();
                break;
            default:
                throw new IllegalStateException("Unexpected event occurred!");
        }
    }
    
    /**
     * Method required for implementing ActionListener interface.  Fixes
     * timings of GUI animations.  Associated Timer object, timer, will call 
     * this method.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!q.isAlive())
            timer.stop();
        else
            runOnce();
    }
    
    /**************************************************************************/
    /*  Simulation Setup & Test Methods                                       */
    /**************************************************************************/
    
    /**
     * Method for setting up the normal simulation environment and starting 
     * conditions.
     */
    private void normalSetup(){
        clear();
        this.makeBalas = true;
        this.environment = new Environment(BOARDSIZE);
        Space start = environment.getSpace(BOARDSIZE/2, BOARDSIZE/2);
        q = new Queen(start, ants);
        
        q.hatchForager(50);
        q.hatchScout(4);
        q.hatchSoldier(10);
        start.setFood(1000);
        
        updateBoard();
        
        turn = 0;
    }
    
    /**
     * Test method for testing Queen ant.
     */
    private void queenTest(){
        clear();
        this.BOARDSIZE = 5;
        this.makeBalas = false;
        this.environment = new Environment(BOARDSIZE);
        Space start = environment.getSpace(BOARDSIZE/2, BOARDSIZE/2);
        q = new Queen(start, ants);
        
        environment.setAllFood(0);
        environment.revealAll();
        start.setFood(1000);
        
        updateBoard();
        
        turn = 0;
    }
    
    /**
     * Test method for testing Forager ants.
     */
    private void foragerTest(){
        clear();
        this.makeBalas = false;
        this.BOARDSIZE = 3;
        this.environment = new Environment(BOARDSIZE);
        Space start = environment.getSpace(0, 0);
        q = new Queen(start, ants);
        
        q.hatchForager(1);
        q.setActive(false);
        environment.setAllFood(0);
        environment.revealAll();
        start.setFood(1000);
        environment.getSpace(2, 2).setFood(50);
        
        updateBoard();
        
        turn = 0;
    }
    
    /**
     * Test method for testing Soldier & Bala ants.
     */
    private void soldierTest(){
        clear();
        this.BOARDSIZE = 5;
        this.makeBalas = true;
        this.environment = new Environment(BOARDSIZE);
        Space start = environment.getSpace(BOARDSIZE/2, BOARDSIZE/2);
        q = new Queen(start, ants);
        
        q.hatchSoldier(2);
        q.setActive(false);
        generateBala();
        environment.setAllFood(0);
        environment.revealAll();
        start.setFood(1000);
        
        this.makeBalas = false;
        
        updateBoard();
        
        turn = 0;
    }
    
    /**************************************************************************/
    /*  Methods for Updating the GUI Environment                              */
    /**************************************************************************/
    
    /**
     * Clears all actors and resources from current simulation and makes all 
     * spaces hidden.
     */
    private void clear(){
        for( int i = 0; i < BOARDSIZE; i++){
            for( int j = 0; j < BOARDSIZE; j++){
                environment.getSpace(i, j).clear();
                ants.clear();
                updateColonyNodeViewFromSpace(guiNodes[i][j], 
                                              environment.getSpace(i, j));
            }
        }
    }
    
    /**
     * Updates the GUI's view by reading the information from each space on 
     * the grid in the Environment object.
     */
    private void updateBoard(){
        for( int i = 0; i < BOARDSIZE; i++){
            for( int j = 0; j < BOARDSIZE; j++){
                updateColonyNodeViewFromSpace(guiNodes[i][j], 
                                              environment.getSpace(i, j));
            }
        }
    }
    
    /**
     * Updates ColonyNodeView object with information from corresponding Space 
     * object from the associated Environment object, environment.
     * @param cnv           ColonyNodeView object to update.
     * @param space         Space object to read from.
     */
    private void updateColonyNodeViewFromSpace(ColonyNodeView cnv, Space space){
        
        if (space.isExplored())
            cnv.showNode();
        else
            cnv.hideNode();
        
        cnv.setID(space.toString());
        
        if (space.isExplored())
            cnv.showNode();
        
        if (space.getQueenCount() == 1){
            cnv.setQueen(true);
            cnv.showQueenIcon();
        }else{
            cnv.setQueen(false);
            cnv.hideQueenIcon();
        }
        if (space.getBalaCount() > 0){
            cnv.showBalaIcon();
        }else{
            cnv.hideBalaIcon();
        }
        if (space.getScoutCount() > 0){
            cnv.showScoutIcon();
        }else{
            cnv.hideScoutIcon();
        }
        if (space.getSoldierCount() > 0){
            cnv.showSoldierIcon();
        }else{
            cnv.hideSoldierIcon();
        }
        if (space.getForagerCount() > 0){
            cnv.showForagerIcon();
        }else{
            cnv.hideForagerIcon();
        }
        
        cnv.setForagerCount(space.getForagerCount());
        cnv.setScoutCount(space.getScoutCount());
        cnv.setSoldierCount(space.getSoldierCount());
        cnv.setBalaCount(space.getBalaCount());
        cnv.setFoodAmount(space.getFood());
        cnv.setPheromoneLevel(space.getPheromone());
    }
    
    /**
     * Generates Bala ants in a random border Space object.
     */
    private void generateBala(){
        if (makeBalas){
            int numberOfBorders = environment.borderCount();
            int i = Randomizer.Give.nextInt(numberOfBorders);
            ants.add(new Bala(environment.getBorder(i)));
        }
    }
    
    /**************************************************************************/
    /*  Static Methods for Interacting with Static Attribute: turn            */
    /**************************************************************************/
    
    /**
     * Returns the current turn value of the Simulation.
     * @return          Current integer value of the attribute: turn.
     */
    public static int getTurn(){
        return turn;
    }
    
    /**
     * Increments the integer attribute: turn, by 1.
     */
    public static void incrementTurn(){
        turn++;
    }
    
    /**
     * Sets the current turn value to the user provided integer.
     * @param i         Integer value to give the attribute: turn.
     */
    public static void setTurn(int i){
        turn = i;
    }
    
    /**************************************************************************/
    /*  Methods for Running the Simulation                                    */
    /**************************************************************************/
    
    /**
     * Runs the simulation continuously.
     */
    private void runContinuously(){
        this.timer = new Timer(100, this);
        timer.start();
    }
    
    /**
     * Runs only one turn of the simulation.
     */
    private void runOnce(){
        
        if (q.isAlive()){
            
            q.act();
            
            //update Queen's ColonyNodeView after acting her turn.
            int row = q.getCoordinates()[0];
            int col = q.getCoordinates()[1];
            ColonyNodeView cnv = guiNodes[row][col];
            updateColonyNodeViewFromSpace(cnv, q.getSpace());

            //Make the bala
            if (Randomizer.Give.nextDouble() <= 0.03)
                generateBala();

            ListIterator<Actionable> litr = ants.listIterator();
            
            while (litr.hasNext()){
                if ( q.isAlive()){
                    
                    Actionable curr = litr.next();
                    
                    //Current space occupied before acting the turn.
                    Space oldSpace = ((Ant)curr).getSpace();

                    curr.act();
                    
                    //Space occupied after acting the turn.
                    Space newSpace = ((Ant)curr).getSpace();
                    
                    //Update the space(s)
                    if (oldSpace == newSpace){
                        row = newSpace.getCoordinates()[0];
                        col = newSpace.getCoordinates()[1];

                        cnv = guiNodes[row][col];
                        updateColonyNodeViewFromSpace(cnv, newSpace);
                    }else{
                        int orow = oldSpace.getCoordinates()[0];
                        int ocol = oldSpace.getCoordinates()[1];
                        int nrow = newSpace.getCoordinates()[0];
                        int ncol = newSpace.getCoordinates()[1];

                        ColonyNodeView ocnv = guiNodes[orow][ocol];
                        ColonyNodeView ncnv = guiNodes[nrow][ncol];
                        updateColonyNodeViewFromSpace(ocnv, oldSpace);
                        updateColonyNodeViewFromSpace(ncnv, newSpace);
                    }
                    
                    //remove the ant from the registry if it died.
                    if (!((Ant)curr).isAlive())
                        litr.remove();

                }else
                    break; //queen is dead
            }// end while

            if (q.isAlive()){
                incrementTurn();
                if (this.turn % 10 == 0 ){
                    environment.halveAllPheromone();
                    updateBoard();
                }
            }else{
                //simulation is over
                
                String endMessage = "Queen has died after " + turn 
                                  + " turns to the Balas' attack!";
                if (q.getSpace().getFood() == 0)
                    endMessage = "Queen has died after " + turn 
                               + " turns to starvation!";
                if (q.isOld())
                    endMessage = "Queen has died of old age with no heir, "
                               + "or dear!";
                
                JOptionPane.showMessageDialog(null, 
                                              endMessage, 
                                              "Simulation Over", 
                                              JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        gui.setTime("Day: " + turn/10 + " Turn: " + turn + " Ants: " 
                                                         + (ants.size()+1));
    }
}
