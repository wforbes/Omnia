package net.wforbes.gameState;

public abstract class GameState {

    protected GameStateManager gsm;

    public abstract void init();
    public abstract void tick();
    public abstract void render(java.awt.Graphics2D g);
    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);
    public abstract void keyTyped(int k);
}
