package net.wforbes.omnia.gameState;

public abstract class GameState {

    public GameStateManager gsm;
    public int tickCount;
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
