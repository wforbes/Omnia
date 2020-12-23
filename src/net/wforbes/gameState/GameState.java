package net.wforbes.gameState;

public abstract class GameState {

    protected GameStateManager gsm;
    public abstract void init();
    public abstract void tick();
    public abstract void render(java.awt.Graphics2D g);
}
