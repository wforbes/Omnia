package net.wforbes.omnia.overworld.entity;

import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.animation.MovementAnimation;
import net.wforbes.omnia.overworld.entity.movement.MovementController;

public class DocNPC extends NPC {
    public DocNPC(OverworldState gameState) {
        super(gameState, "Doc",0.25);
        this.width = this.height = 16;
        this.numFrames = new int[]{3,3,3,3};
        this.loadSprites(OverworldState.SPRITE_DIR + "doc_pokemon.gif");
        this.facingDir = FACING_S;
        movementAnimation = new MovementAnimation(this);
        this.setAnimationDirection(facingDir);
    }

    public void init() {
        this.setPosition(80, 80);
        this.movementController.setMovementSpace(this.x+40, this.y+40, this.x+40, this.y+40);
        this.movementController.setMovementType(MovementController.MOVEMENT_PACE_VERTICAL);
    }

    public void update() {
        super.update();
    }

    //TODO: handleGreetings()
    //  handleQuests()
}
