package net.wforbes.omnia.overworld.entity;

import javafx.scene.paint.Color;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.animation.MovementAnimation;

public class Enemy extends Mob {
    public Enemy(OverworldState gameState, String name) {
        super(gameState, name, 0.45, false);
        this.nameColor = Color.DARKRED;
        this.nameFlashColor = Color.RED;
        this.width = 24;
        this.height = 23;
        this.numFrames = new int[]{3, 3, 3, 3};
        this.combatNumFrames = new int[]{3, 3, 3, 3};
        this.loadSprites(OverworldState.SPRITE_DIR + "attack_test1.gif");
        this.movementAnimation = new MovementAnimation(this);
        this.facingDir = FACING_S;
        this.setAnimationDirection(facingDir);
        this.attentionController.setAttentionSpan(30);
    }

    @Override
    public void init() {
        //nope
    }

    public void init(double xPos, double yPos) {
        super.init(xPos, yPos);
        this.setPosition(xPos, yPos);
    }

    @Override
    public void update() {
        super.update();
    }
}
