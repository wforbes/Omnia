package net.wforbes.omnia.overworld.world.terrain.flora;

import net.wforbes.omnia.gameState.OverworldState;

public abstract class Flora {
    protected OverworldState gameState;

    public Flora(OverworldState gameState) {
        this.gameState = gameState;
    }

    public abstract double getX();
    public abstract double getY();
    public abstract int getCollisionRadius();
    public abstract double getWidth();
    public abstract double getHeight();
}
