package net.wforbes.omnia.overworld.entity;

import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.gameFX.rendering.Renderable;
import net.wforbes.omnia.gameState.OverworldState;

public abstract class Entity implements Renderable {
    public OverworldState gameState;

    public Entity(OverworldState gameState) {
        this.gameState = gameState;
    }
    public abstract Entity getCollidingEntity();
    public abstract Entity getTarget();
    public abstract void setTarget(Entity e);
    public abstract boolean isInMeleeRange(Entity e);
    public abstract int getMeleeReach();
    public abstract String getName();
    protected int collisionXOffset;
    protected int collisionYOffset;
    public abstract int getCollisionXOffset();
    public abstract int getCollisionYOffset();
    protected int collisionBoxWidth;
    protected int collisionBoxHeight;
    public abstract int getCollisionBoxWidth();
    public abstract int getCollisionBoxHeight();
    public abstract double getCollisionRadius();
    public abstract double getX();
    public abstract double getY();
    public abstract double getCollisionBaseX();
    public abstract double getCollisionBaseY();
    public abstract double getBaseY();
    public abstract void init();
    protected abstract void init(double xPos, double yPos);
    public abstract void update();
    public abstract void render(GraphicsContext gc);
    public abstract void teardown();
}
