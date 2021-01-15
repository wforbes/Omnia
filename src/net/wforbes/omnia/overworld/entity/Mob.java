package net.wforbes.omnia.overworld.entity;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.animation.MovementAnimation;
import net.wforbes.omnia.overworld.entity.movement.MovementController;
import net.wforbes.omnia.overworld.world.area.tile.Tile;

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
    //TODO: consider utility class to contain directions
    public static final int FACING_N = 0;
    public static final int FACING_S = 1;
    public static final int FACING_W = 2;
    public static final int FACING_E = 3;
    public static final int FACING_NW = 4;
    public static final int FACING_NE = 5;
    public static final int FACING_SW = 6;
    public static final int FACING_SE = 7;
    public MovementController movementController;

    protected boolean isPlayer; //TODO: remove - for testing
    protected int collisionBoxWidth;
    protected int collisionBoxHeight;

    public Mob(OverworldState gameState, String name, double speed, boolean player) {
        super(gameState);
        this.isPlayer = player;
        this.name = name;
        this.speed = speed;
        this.collisionBoxWidth = this.collisionBoxHeight = 8;
        this.movementController = new MovementController(this);
    }

    public Mob(OverworldState gameState, String name, Point2D startPos, double speed) {
        super(gameState);
        this.name = name;
        this.x = startPos.getX();
        this.y = startPos.getY();
        this.speed = speed;
        this.movementController = new MovementController(this);
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

    public void move(double xa, double ya) {
        //sets sprite image directionality
        if(xa != 0 && ya != 0){
            moveDiagonal(xa, ya);
        }else{
            moveCardinal(xa, ya);
        }
        if (movementAnimation.getFacingDir() != facingDir)
            this.updateAnimationDirection();

        if (this.isMoving != movementAnimation.isMoving())
            movementAnimation.setIsMoving(this.isMoving);

        if(this.hasCollided(xa, ya)) {
            if (this.isMovingDiagonally()) {

            }
        } else {
            //moveCoords(xa, ya);
            //numSteps++;
        }
        //TODO: check for collision
        moveCoords(xa, ya);
        numSteps++;
    }

    public boolean hasCollided(double xa, double ya) {
        int xMin = 0;
        int xMax = collisionBoxWidth;
        int yMin = 0;
        int yMax = collisionBoxHeight;
        for(int x = xMin; x < xMax; x++){
            if(isSolidTile(xa, ya, x, yMin) || isSolidTile(xa, ya, x, yMax)){
                return true;
            }
            /*
            if((!canSwim && isWaterTile(xa, ya, x, yMin))
                    || (!canSwim && isWaterTile(xa, ya, x, yMax))) {
                return true;
            }
            //System.out.println("top/bottom");
            if(isOccupied(xa, ya, x, yMin) || isOccupied(xa, ya, x, yMax)) {
                return true;
            }*/
        }
        for(int y = yMin; y < yMax; y++){
            if(isSolidTile((int)xa, (int)ya, xMin, y) || isSolidTile((int)xa, (int)ya, xMax, y)){
                return true;
            }/*
            if((!canSwim && isWaterTile(xa, ya, xMin, y))
                    || !canSwim && isWaterTile(xa, ya, xMax, y)) {
                return true;
            }
            //System.out.println("left/right");
            if(isOccupied(xa, ya, xMin, y) || isOccupied(xa, ya, xMax, y)) {
                return true;
            }*/
        }
        return false;
    }

    //TODO: wait to finish this for when GUI can draw shapes and illustrate where things are
    protected boolean isSolidTile(double xa, double ya, double x, double y)
    {
        if(gameState.world.area == null) return false; //TODO: is required?
        int tileSize = gameState.world.area.getTileMap().getTileSize();
        if(isPlayer){
            if(xa < 0) {
                int currentTileFloor = (int)Math.floor((this.x+x)/tileSize)-1;
                int currentTileCeil = (int)Math.ceil((this.x+x)/tileSize)-1;
                int nextTileFloor = (int)Math.floor((this.x+x+xa)/tileSize)-1;
                int nextTileCeil = (int)Math.ceil((this.x+x+xa)/tileSize)-1;
            }

            int currentTileFloor = (int)Math.floor((this.x+x)/tileSize)+1;
            int currentTileCeil = (int)Math.ceil((this.x+x)/tileSize)+1;
            int nextTileFloor = (int)Math.floor((this.x+x+xa)/tileSize)+1;
            int nextTileCeil = (int)Math.ceil((this.x+x+xa)/tileSize)+1;
            //System.out.println("Current: " + currentTileFloor + " " + currentTileCeil + " / Next: " + nextTileFloor + " " + nextTileCeil);
            //System.out.println("isSolidTile floor" + (int)(Math.floor((this.x+x)/tileSize)) + " " + (this.x) + " " + xmap + " " + (x) + " " + (xa));
            //System.out.println("isSolidTile ceil" + (int)(Math.ceil((this.x+x)/tileSize)) + " " + (this.x) + " " + xmap + " " + (x) + " " + (xa));
        }

        //System.out.println((this.x + xmap + x) + " " + (this.x + xmap + x + xa));
        //TODO: may cause rounding issues - check on this and try flooring the ints before getting the tiles
        Tile lastTile = gameState.world.area.getTileMap().getTile((int)(Math.floor((this.x + x)/tileSize)), (int)(Math.floor((this.y + y)/tileSize)));
        Tile newTile = gameState.world.area.getTileMap().getTile((int)(Math.floor((this.x + x + xa)/tileSize)), (int)(Math.floor((this.y + y + ya)/tileSize)));

        if(!lastTile.equals(newTile) && newTile.isSolid()){
            return true;
        }
        return false;
    }

    public boolean isMovingDiagonally() {
        return this.facingDir > 3;
    }

    public void setMoving(boolean moving) {
        this.isMoving = moving;
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

    public void setFacingDir(int direction) {
        this.facingDir = direction;
    }

    protected void refreshMapPosition() {
        xmap = gameState.world.area.getTileMap().getx();
        ymap = gameState.world.area.getTileMap().gety();
    }

    protected boolean offScreen() {
        return x + xmap + width < 0 ||
                x + xmap - width/2.5 > OmniaFX.getWidth() ||
                y + ymap + height < 0 ||
                y + ymap - height/2.5 > OmniaFX.getHeight();
    }

    //public abstract void init();
    //public abstract void update();
    public void render(GraphicsContext gc) {
        this.refreshMapPosition();
        if(!offScreen()) {
            gc.drawImage(
                    movementAnimation.getImage(),
                    (x + xmap - width / 2.0) * OmniaFX.getScale(),
                    (y + ymap - height / 2.0) * OmniaFX.getScale(),
                    width * OmniaFX.getScale(),
                    height * OmniaFX.getScale()
            );
        }
    }
}
