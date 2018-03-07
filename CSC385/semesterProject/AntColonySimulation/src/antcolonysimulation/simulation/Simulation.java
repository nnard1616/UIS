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
    private final int BOARDSIZE;
    private Timer timer;
    
    /**************************************************************************/
    
    public Simulation(){
        this(27);
    }
    
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
    
    public void normalSetup(){
        clear();
        this.environment = new Environment(BOARDSIZE);
        Space start = environment.getSpace(BOARDSIZE/2, BOARDSIZE/2);
        q = new Queen(start, ants);
        
        q.hatchForager(50);
        q.hatchScout(4);
        q.hatchSoldier(10);
        start.setFood(1000);

        
        for( int i = 0; i < BOARDSIZE; i++){
            for( int j = 0; j < BOARDSIZE; j++){
                readSpaceIntoGUI(guiNodes[i][j], environment.getSpace(i, j));
            }
        }
        turn = 0;
    }
    
    public void foragerTest(){
        clear();
        this.environment = new Environment(BOARDSIZE);
        Space start = environment.getSpace(0, 0);
        q = new Queen(start, ants);
        
        q.hatchForager(1);
        q.setActive(false);
        environment.setAllFood(0);
        environment.revealAll();
        start.setFood(1000);
        environment.getSpace(2, 2).setFood(50);
 
        
        ColonyView cView = new ColonyView(BOARDSIZE,BOARDSIZE);
        
        for( int i = 0; i < BOARDSIZE; i++){
            for( int j = 0; j < BOARDSIZE; j++){
                readSpaceIntoGUI(guiNodes[i][j], environment.getSpace(i, j));
            }
        }
        turn = 0;
    }
    
    public void soldierTest(){
        clear();
        this.environment = new Environment(BOARDSIZE);
        Space start = environment.getSpace(BOARDSIZE/2, BOARDSIZE/2);
        q = new Queen(start, ants);
        
        q.hatchSoldier(2);
        q.setActive(false);
        generateBala();
        environment.setAllFood(0);
        environment.revealAll();
        start.setFood(1000);
 
        
        ColonyView cView = new ColonyView(BOARDSIZE,BOARDSIZE);
        
        for( int i = 0; i < BOARDSIZE; i++){
            for( int j = 0; j < BOARDSIZE; j++){
                readSpaceIntoGUI(guiNodes[i][j], environment.getSpace(i, j));
            }
        }
        turn = 0;
    }
    
    public void clear(){
        for( int i = 0; i < BOARDSIZE; i++){
            for( int j = 0; j < BOARDSIZE; j++){
                environment.getSpace(i, j).clear();
                ants.clear();
                updateColonyNodeViewFromSpace(guiNodes[i][j], environment.getSpace(i, j));
            }
        }
    }
    
    public void updateBoard(){
        for( int i = 0; i < BOARDSIZE; i++){
            for( int j = 0; j < BOARDSIZE; j++){
                updateColonyNodeViewFromSpace(guiNodes[i][j], environment.getSpace(i, j));
            }
        }
    }

    @Override
    public void simulationEventOccurred(SimulationEvent simEvent) {
        switch (simEvent.getEventType()){
            case SimulationEvent.NORMAL_SETUP_EVENT:
                normalSetup();
                break;
            case SimulationEvent.QUEEN_TEST_EVENT:
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
    
    public void readSpaceIntoGUI(ColonyNodeView cnv, Space space){
        
        if (space.isExplored())
            cnv.showNode();
        else
            cnv.hideNode();
        
        cnv.setID(space.toString());
        
        if (space.getQueenCount() == 1){
            cnv.setQueen(true);
            cnv.showQueenIcon();
        }
        if (space.getBalaCount() > 0){
            cnv.showBalaIcon();
        }
        if (space.getScoutCount() > 0){
            cnv.showScoutIcon();
        }
        if (space.getSoldierCount() > 0){
            cnv.showSoldierIcon();
        }
        if (space.getForagerCount() > 0){
            cnv.showForagerIcon();
        }
        
        cnv.setForagerCount(space.getForagerCount());
        cnv.setScoutCount(space.getScoutCount());
        cnv.setSoldierCount(space.getSoldierCount());
        cnv.setBalaCount(space.getBalaCount());
        cnv.setFoodAmount(space.getFood());
        cnv.setPheromoneLevel(space.getPheromone());
    }
    
    public void updateColonyNodeViewFromSpace(ColonyNodeView cnv, Space space){
        
        if (space.isExplored())
            cnv.showNode();
        
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
    
    public static int getTurn(){
        return turn;
    }
    
    public static void incrementTurn(){
        turn++;
    }
    
    public static void setTurn(int i){
        turn = i;
    }
    
    public void generateBala(){
        int numberOfBorders = environment.borderCount();
        int i = Randomizer.Give.nextInt(numberOfBorders);
        ants.add(new Bala(environment.getBorder(i)));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!q.isAlive())
            timer.stop();
        else
            runOnce();
    }
    
    public void runContinuously(){
        this.timer = new Timer(100, this);
        timer.start();
    }
    
    public void runOnce(){
        
        if (q.isAlive()){
            q.act();

            if (Randomizer.Give.nextDouble() <= 0.03)
                generateBala();

            ListIterator<Actionable> litr = ants.listIterator();
            while (litr.hasNext()){
                if ( q.isAlive()){
                    Actionable curr = litr.next();
                    Space oldSpace = ((Ant)curr).getSpace();

                    curr.act();
                    Space newSpace = ((Ant)curr).getSpace();
                    if (oldSpace == newSpace){
                        int row = newSpace.getCoordinates()[0];
                        int col = newSpace.getCoordinates()[1];

                        ColonyNodeView cnv = guiNodes[row][col];
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

                    if (!((Ant)curr).isAlive())
                        litr.remove();


                }else
                    break;
            }

            if (q.isAlive()){
                incrementTurn();
                if (this.turn % 10 == 0 ){
                    environment.halveAllPheromone();
                    updateBoard();
                }
            }else{
                //simulation is over
                
                String endMessage = "Queen has died after " + turn + " turns to the Balas' attack!";
                if (q.getSpace().getFood() == 0)
                    endMessage = "Queen has died after " + turn + " turns to starvation!";
                if (q.isOld())
                    endMessage = "Queen has died of old age with no heir, or dear!";
                
                JOptionPane.showMessageDialog(null, 
                                              endMessage, 
                                              "Simulation Over", 
                                              JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        gui.setTime("Day: " + turn/10 + " Turn: " + turn + " Ants: " + (ants.size()+1));
    }
}
