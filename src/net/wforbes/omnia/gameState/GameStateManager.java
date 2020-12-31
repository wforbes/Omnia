package net.wforbes.omnia.gameState;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import net.wforbes.omnia.game.Game;
import net.wforbes.omnia.gameFX.controllers.GameController;
import net.wforbes.omnia.input.InputHandler;

import java.awt.*;
import java.util.ArrayList;

public class GameStateManager {
    public boolean usingFx;
    public InputHandler inputHandler;
    public GameController gameController;
    private ArrayList<GameState> gameStates;
    private int currentState;
    public static final int MENUSTATE = 0;
    public static final int TOPDOWNSTATE = 1;
    public static final int PLATFORMERSTATE = 2;
    public static final int INFOSTATE = 3;

    public GameStateManager(Game game) {
        this.inputHandler = new InputHandler(game);
        gameStates = new ArrayList<>();
        gameStates.add(new MenuState(this));
        gameStates.add(new TopDownState(this));
        gameStates.add(new PlatformerState(this));
        this.setState(MENUSTATE);
    }

    public GameStateManager(GameController gameController) {
        //this.inputHandler = new InputHandler(gameController);
        this.usingFx = true;
        this.gameController = gameController;
        gameStates = new ArrayList<>();
        gameStates.add(new MenuState(this, "fx"));
        gameStates.add(new TopDownState(this));
        gameStates.add(new PlatformerState(this));
        this.setState(MENUSTATE);
    }
    public boolean isKeyDown(KeyCode keyCode) {
        return this.gameController.keys.isDown(keyCode);
    }

    public void setState(int state) {
        this.currentState = state;
        this.gameStates.get(currentState).init();
    }
    public void resetState(int state) {
        this.gameStates.get(state).reset();
    }

    public void tick(){
        gameStates.get(currentState).tick();
    }

    public void update() {
        gameStates.get(currentState).update();
    }

    public void render(GraphicsContext gc) {
        gameStates.get(currentState).render(gc);
    }

    public void render(Graphics2D g2D){
        gameStates.get(currentState).render(g2D);
    }

}
