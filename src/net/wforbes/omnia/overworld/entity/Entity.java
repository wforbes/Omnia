package net.wforbes.omnia.overworld.entity;

import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.gameFX.rendering.Renderable;
import net.wforbes.omnia.gameState.OverworldState;

public abstract class Entity implements Renderable {
    public OverworldState gameState;
    protected double x, y;

    public Entity(OverworldState gameState) {
        this.gameState = gameState;
    }

    public abstract double getX();
    public abstract double getY();
    public abstract String getName();
    public abstract int getCollisionBoxWidth();
    public abstract int getCollisionRadius();

    public abstract void init();
    public abstract void init(int xPos, int yPos);
    public abstract void update();
    public abstract void render(GraphicsContext gc);
    public abstract void teardown();
}
