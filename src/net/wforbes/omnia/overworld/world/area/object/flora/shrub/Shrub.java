package net.wforbes.omnia.overworld.world.area.object.flora.shrub;

import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.world.area.object.flora.Flora;

public class Shrub extends Flora {

    //blue_flowers3
    //13x6
    //8x8
    protected static final String SHRUBS_SPRITE_DIR = FLORA_SPRITE_DIR + "shrubs/";

    public Shrub(OverworldState gameState, float x, float y) {
        super(gameState, x, y);
        this.loadSprite(SHRUBS_SPRITE_DIR+"Bush_blue_flowers3.png");
        this.x = x;
        this.y = y;
    }

    public Shrub(OverworldState gameState, String spriteName, float x, float y) {
        super(gameState, SHRUBS_SPRITE_DIR+"/"+spriteName+".png", x, y);
        this.x = x;
        this.y = y;
    }

    public void init() {}

}
