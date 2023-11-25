package net.wforbes.omnia.overworld.entity;

import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.gameFX.rendering.Renderable;
import net.wforbes.omnia.gameState.OverworldState;

public abstract class Entity implements Renderable {
    public OverworldState gameState;

    public Entity(OverworldState gameState) {
        this.gameState = gameState;
    }

    public abstract String getName();
    public abstract int getCollisionBoxWidth();
    public abstract double getCollisionRadius();
    public abstract double getX();
    public abstract double getY();
    public abstract double getBaseY();
    public abstract void init();
    protected abstract void init(double xPos, double yPos);
    public abstract void update();
    public abstract void render(GraphicsContext gc);
    public abstract void teardown();
}
