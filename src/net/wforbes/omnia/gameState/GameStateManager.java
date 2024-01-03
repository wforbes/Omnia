package net.wforbes.omnia.gameState;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import net.wforbes.omnia.game.controllers.GameController;

import java.awt.*;
import java.util.ArrayList;

public class GameStateManager {
    public GameController gameController;
    private ArrayList<GameState> gameStates;
    private int currentState;
    private int tickCount = 0;
    public static final int MENUSTATE = 0;
    public static final int TOPDOWNSTATE = 1;
    public static final int PLATFORMERSTATE = 2;
    public static final int OVERWORLDSTATE = 3;

    public GameStateManager(GameController gameController) {
        this.gameController = gameController;
        gameStates = new ArrayList<>();
        gameStates.add(new MenuState(this));
        gameStates.add(new TopDownState(this));
        gameStates.add(new PlatformerState(this));
        gameStates.add(new OverworldState(this));
        this.setState(MENUSTATE);
    }

    public GameController getGameController() {
        return gameController;
    }

    public boolean isKeyDown(KeyCode keyCode) {
        return this.gameController.keys.isDown(keyCode);
    }
    public void clearKeys() {
        this.gameController.keys.clearKeys();
    }

    public void setState(int state) {
        this.currentState = state;
        this.clearKeys();
        this.gameStates.get(currentState).init();

    }
    public void resetState(int state) {
        this.gameStates.get(state).reset();
    }

    public GameState getCurrentState() {
        return this.gameStates.get(currentState);
    }
    public int getTickCount() { return this.tickCount; }

    //Used for Game.java logic updates
    public void tick(){
        gameStates.get(currentState).tick();
    }

    //Used for GameFX.java logic updates
    public void update() {
        this.tickCount++;
        //System.out.println(gameController.getStageWidth());
        gameStates.get(currentState).update();
    }

    //Used for GameFX.java renders
    public void render(GraphicsContext gc) {
        gameStates.get(currentState).render(gc);
    }

}
