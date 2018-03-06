/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolonysimulation.simulation;

import antcolonysimulation.ants.Actionable;
import antcolonysimulation.ants.Ant;
import antcolonysimulation.ants.friendly.Queen;
import antcolonysimulation.environment.Environment;
import antcolonysimulation.environment.Space;
import antsimgui.AntSimGUI;
import antsimgui.ColonyNodeView;
import antsimgui.ColonyView;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author nathan
 */
public class Simulation {
    private static int turn = 0;
    private static boolean running = true;
    private Environment environment;
    private List<Actionable> ants;
    private AntSimGUI gui;
    private ColonyNodeView[][] guiNodes;
    private Queen q;
    
    public Simulation(int boardSize){
        this.environment = new Environment(boardSize);
        
        this.ants = new ArrayList<>();
        Space start = environment.getSpace(0, 0);
        environment.revealAll();
        q = new Queen(start, ants);
        
        q.hatchForager(1);
//        q.hatchScout(4);
//        q.hatchSoldier(10);
        start.setFood(0);
        
        
 
        this.gui = new AntSimGUI();
        this.guiNodes = new ColonyNodeView[boardSize][boardSize];
        ColonyView cView = new ColonyView(boardSize,boardSize);
        
        for( int i = 0; i < boardSize; i++){
            for( int j = 0; j < boardSize; j++){
                guiNodes[i][j] = readSpaceIntoGUI(environment.getSpace(i, j));
                cView.addColonyNodeView(guiNodes[i][j], i, j);
            }
        }
        
        this.gui.initGUI(cView);
        
        
        turn = 0;
        running = true;
    }
    
    public Simulation(){
        this(27);
    }
    
    public ColonyNodeView readSpaceIntoGUI(Space space){
        ColonyNodeView cnv = new ColonyNodeView();
        
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
        return cnv;
    }
    
    public void updateColonyNodeViewFromSpace(ColonyNodeView cnv, Space space){
        cnv.hideBalaIcon();
        cnv.hideForagerIcon();
        cnv.hideScoutIcon();
        cnv.hideSoldierIcon();
        
        if (space.isExplored())
            cnv.showNode();
        
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
    
    public static int getTurn(){
        return turn;
    }
    
    public static void incrementTurn(){
        turn++;
    }
    
    public static void end(){
        running = !running;
    }
    
    public static void setTurn(int i){
        turn = i;
    }
    
    public void playTurn(){
//        q.act();
        ListIterator<Actionable> litr = ants.listIterator();
        while (litr.hasNext()){
            if ( Simulation.running){
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
                    
            }else
                break;
        }
        
        if (this.running){
            incrementTurn();
            environment.halveAllPheromone();
            playTurn();
        }else{
            System.out.println("DONE in " + turn + " turns!");
            return;
        }
        
    }
    
    
}
