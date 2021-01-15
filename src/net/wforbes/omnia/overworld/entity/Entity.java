package net.wforbes.omnia.overworld.entity;

import net.wforbes.omnia.gameState.OverworldState;

public abstract class Entity {
    public OverworldState gameState;
    protected double x, y;

    public Entity(OverworldState gameState) {
        this.gameState = gameState;
    }

    public double getX(){ return x; }
    public double getY(){ return y; }
    public abstract String getName();

}
