package net.wforbes.omnia.overworld.world.area.object.flora.shrub;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
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
        this.collisionRadius = 16;
    }

    public Shrub(OverworldState gameState, String spriteName, float x, float y) {
        super(gameState, SHRUBS_SPRITE_DIR+"/"+spriteName+".png", x, y);
        this.x = x;
        this.y = y;
    }

    public void init() {
        this.initCollisionShape();
    }

    @Override
    public double getCollisionRadius() {
        return this.collisionRadius;
    }

    public void init(double x, double y) {
        this.initCollisionShape();
    }

    private void initCollisionShape() {
        this.collision_baseX = -2; //(w/2)-??
        System.out.println("shrub init h: " + this.height);
        this.collision_baseY = 7; //h-spriteOffsetY-??
        this.baseY = this.collision_baseY;
        this.collisionRadius = 17;
        this.collision_baseCenterPnt = new Point2D(collision_baseX, collision_baseY);
        this.collision_baseCircle = new Circle(collision_baseX, collision_baseY, collisionRadius);
    }

}