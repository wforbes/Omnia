package net.wforbes.omnia.overworld.entity;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import net.wforbes.omnia.gameState.OverworldState;

import java.util.ArrayList;

public abstract class Mob extends Entity {

    protected String name;
    protected String spriteSheetPath;
    protected Image spriteSheet;
    private ArrayList<Image[]> sprites;
    protected int width, height;

    double xmap, ymap;//current area map position
    protected double speed;
    protected boolean isMoving;
    protected int numSteps = 0;
    protected int facingDir; //0-north, 1-south, 2-west, 3-east,
                            //4-nw, 5-ne, 6-sw, 7-se

    //numFrames: each index is a sprite row,
    //  each value is the number of animation frames
    int[] numFrames;
    MovementAnimation movementAnimation;
    //TODO: consider an enum to store facing directions
    static final int FACING_N = 0;
    static final int FACING_S = 1;
    static final int FACING_W = 2;
    static final int FACING_E = 3;
    static final int FACING_NW = 4;
    static final int FACING_NE = 5;
    static final int FACING_SW = 6;
    static final int FACING_SE = 7;

    public Mob(OverworldState gameState, String name, double speed) {
        super(gameState);
        this.name = name;
        this.speed = speed;
    }

    public Mob(OverworldState gameState, String name, Point2D startPos, double speed) {
        super(gameState);
        this.name = name;
        this.x = startPos.getX();
        this.y = startPos.getY();
        this.speed = speed;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int getWidth(){ return width; }
    public int getHeight(){ return height; }

    void loadSprites(String path) {
        this.spriteSheet = new Image(getClass().getResourceAsStream(path));
        this.sprites = new ArrayList<>();

        for(int i = 0; i < numFrames.length; i++) {
            Image[] images = new Image[numFrames[i]];
            for (int j = 0; j < numFrames[i]; j++) {
                images[j] = new WritableImage(spriteSheet.getPixelReader(), j*width, i*height, width, height);
            }
            sprites.add(images);
        }
    }

    void move(double xa, double ya) {
        //sets sprite image directionality
        if(xa != 0 && ya != 0){
            moveDiagonal(xa, ya);
        }else{
            moveCardinal(xa, ya);
        }

        if(movementAnimation.getFacingDir() != facingDir)
            this.updateAnimationDirection();

        if(this.isMoving != movementAnimation.isMoving())
            movementAnimation.setIsMoving(this.isMoving);


        //TODO: check for collision

        moveCoords(xa, ya);
        numSteps++;
    }

    void setAnimationDirection(int dir) {
        movementAnimation.setFacingDir(dir);
        movementAnimation.setFrames(sprites.get(dir));
        movementAnimation.setDelay((long)(100 / this.speed));
    }
    void updateAnimationDirection() {
        movementAnimation.setFacingDir(facingDir);
        movementAnimation.setFrames(sprites.get(facingDir));
        movementAnimation.setDelay((long)(100/this.speed));
    }

    private void moveCardinal(double xa, double ya){
        if (ya < 0)
            facingDir = FACING_N;
        if (ya > 0)
            facingDir = FACING_S;
        if (xa < 0)
            facingDir = FACING_W;
        if (xa > 0)
            facingDir = FACING_E;
    }
    private void moveDiagonal(double xa, double ya){
        if (ya < 0 && xa  < 0)
            facingDir = FACING_NW;
        if (ya < 0 && xa > 0)
            facingDir = FACING_NE;
        if (ya > 0 && xa < 0)
            facingDir = FACING_SW;
        if (ya > 0 && xa > 0)
            facingDir = FACING_SE;
    }

    private void moveCoords(double xa, double ya) {
        this.x += xa * speed;
        this.y += ya * speed;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    protected void refreshMapPosition() {
        xmap = gameState.world.area.getTileMap().getx();
        ymap = gameState.world.area.getTileMap().gety();
    }
}
