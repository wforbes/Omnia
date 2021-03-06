package net.wforbes.omnia.gameState;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import net.wforbes.omnia.gameFX.controls.keyboard.KeyboardController;

public abstract class GameState {

    public GameStateManager gsm;
    public int tickCount;
    public abstract KeyboardController getKeyboard();
    public abstract void handleKeyPressed(KeyEvent event);
    public abstract void handleKeyReleased(KeyEvent event);
    public abstract void handleCanvasClick(MouseEvent event);
    public abstract void handleCanvasMouseMove(MouseEvent event);
    public abstract int getTickCount();
    public abstract void init();
    public abstract void tick();
    public abstract void update();
    public abstract void render(java.awt.Graphics2D g);
    public abstract void render(javafx.scene.canvas.GraphicsContext gc);
    public abstract void reset();
    public abstract void pause();
    public abstract boolean isPaused();
    public abstract void unPause();
}
