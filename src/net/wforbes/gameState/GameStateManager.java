package net.wforbes.gameState;

import net.wforbes.game.Game;
import net.wforbes.input.InputHandler;

import java.awt.*;
import java.util.ArrayList;

public class GameStateManager {
    private ArrayList<GameState> gameStates;
    private int currentState;
    protected static final int MENUSTATE = 0;
    public static final int TOPDOWNSTATE = 1;
    public static final int PLATFORMERSTATE = 2;
    public static final int INFOSTATE = 3;

    public InputHandler inputHandler;

    public GameStateManager(Game game) {
        this.inputHandler = new InputHandler(game);
        gameStates = new ArrayList<>();
        gameStates.add(new MenuState(this));
        gameStates.add(new TopDownState(this));
        this.setState(MENUSTATE);
    }

    public void setState(int state) {
        this.currentState = state;
        this.gameStates.get(currentState).init();
    }

    public void tick(){
        gameStates.get(currentState).tick();
    }

    public void render(Graphics2D g2D){
        gameStates.get(currentState).render(g2D);
    }

}
