package net.wforbes.gameState;

import java.util.ArrayList;

public class GameStateManager {
    private ArrayList<GameState> gameStates;
    private int currentState;
    public static final int MENUSTATE = 0;
    public static final int TOPDOWNSTATE = 1;
    public static final int PLATFORMERSTATE = 2;
    public static final int INFOSTATE = 3;

    public GameStateManager() {
        gameStates = new ArrayList<>();
        currentState = MENUSTATE;
        gameStates.add(new MenuState(this));
    }

    public void setState(int state) {
        currentState = state;
        gameStates.get(currentState).init();
    }

    public void tick(){
        gameStates.get(currentState).tick();
    }

    public void render( java.awt.Graphics2D g2D){
        gameStates.get(currentState).render(g2D);
    }

    public void keyPressed(int k){
        gameStates.get(currentState).keyPressed(k);
    }

    public void keyReleased(int k){
        gameStates.get(currentState).keyReleased(k);
    }

    public void keyTyped(int k){
        gameStates.get(currentState).keyTyped(k);
    }

}
