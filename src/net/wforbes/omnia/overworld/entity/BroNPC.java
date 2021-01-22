package net.wforbes.omnia.overworld.entity;

import javafx.geometry.Point2D;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.animation.MovementAnimation;
import net.wforbes.omnia.overworld.entity.movement.MovementController;

public class BroNPC extends NPC {
    public BroNPC(OverworldState gameState, String name, double speed) {
        super(gameState, name, speed);
        this.width = this.height = 16;
        this.width = this.height = 16;
        this.numFrames = new int[]{3,3,3,3};
        //this.loadSprites(OverworldState.SPRITE_DIR + "bro_pokemon.gif");
        this.facingDir = FACING_S;
        movementAnimation = new MovementAnimation(this);
        //npcDialog = new BroDialog();
        this.setAnimationDirection(facingDir);
    }

    @Override
    public void init() {
        this.setPosition(100, 200);
        this.movementController.setMovementSpace(this.x-20, this.y-20, this.x+20, this.y+20);
        this.movementController.setMovementType(MovementController.MOVEMENT_PACE_VERTICAL);
    }

    public void update() { super.update(); }

    @Override
    protected void handleGreetings(Point2D sourceLoc, String chatMsg) {

    }

    @Override
    protected void handleQuests(Point2D sourceLoc, String chatMsg) {

    }
}
