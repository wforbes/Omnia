package net.wforbes.omnia.overworld.entity;

import net.wforbes.omnia.gameState.OverworldState;

public class Enemy extends Mob {
    public Enemy(OverworldState gameState, String name) {
        super(gameState, name, 0.45, false);

    }

    @Override
    public void init() {

    }
    public void init(double xPos, double yPos) {}

    @Override
    public void update() {

    }
}
