package net.wforbes.omnia.overworld.entity;

import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.animation.MovementAnimation;

public class DocNPC extends NPC {
    public DocNPC(OverworldState gameState) {
        super(gameState, "Doc",0.75);
        this.width = this.height = 16;
        this.numFrames = new int[]{3,3,3,3};
        this.loadSprites(OverworldState.SPRITE_DIR + "doc_pokemon.gif");
        this.facingDir = FACING_S;
        movementAnimation = new MovementAnimation();
        this.setAnimationDirection(facingDir);
    }


    public void update() {
        super.update();
    }

    //TODO: handleGreetings()
    //  handleQuests()
}
