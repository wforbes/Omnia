package net.wforbes.omnia.overworld.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameState.OverworldState;

import java.util.ArrayList;

public class Player extends Mob {
    private OverworldState gameState;
    private Image spriteSheet;

    private String name;
    private double lastInputTick = 0;
    private int width, height;
    private double xmap, ymap;

    //TODO: inheritable
    private int tickCount = 0;
    private double x, y;
    private double xa, ya;
    private double speed = 1.25;
    private double xOffset, yOffset;
    private int numSteps = 0;
    private boolean isMoving;
    private int facingDir;
    private double scale = 1;
    private double collisionBoxWidth;
    private double collisionBoxHeight;

    protected int currentAction;
    private ArrayList<Image[]> sprites;
    private final int[] numFrames = {3, 3, 3, 3, 3, 3, 3, 3};
    private static final int FACING_N = 0;
    private static final int FACING_S = 1;
    private static final int FACING_W = 2;
    private static final int FACING_E = 3;
    private static final int FACING_NW = 4;
    private static final int FACING_NE = 5;
    private static final int FACING_SW = 6;
    private static final int FACING_SE = 7;
    private MovementAnimation movementAnimation;


    public Player(OverworldState gameState) {
        this.gameState = gameState;
    }

    //TODO: replace this with Point2D
    public double getX(){ return x; }
    public double getY(){ return y; }
    public int getWidth(){ return width; }
    public int getHeight(){ return height; }

    public void init() {
        this.width = this.height = 16;
        this.spriteSheet = new Image(getClass().getResourceAsStream("/overworld/sprites/player1_pokemon.gif"));
        this.sprites = new ArrayList<Image[]>();
        this.facingDir = FACING_S;

        for(int i = 0; i < numFrames.length; i++) {
            Image[] images = new Image[numFrames[i]];
            for (int j = 0; j < numFrames[i]; j++) {
                images[j] = new WritableImage(spriteSheet.getPixelReader(), j*width, i*height, width, height);
            }
            sprites.add(images);
        }

        movementAnimation = new MovementAnimation();
        this.setAnimationDirection(facingDir);
    }

    private void setAnimationDirection(int dir) {
        movementAnimation.setFacingDir(dir);
        movementAnimation.setFrames(sprites.get(dir));
        movementAnimation.setDelay((long)(100 / this.speed));
    }
    private void updateAnimationDirection() {
        movementAnimation.setFacingDir(facingDir);
        movementAnimation.setFrames(sprites.get(facingDir));
        movementAnimation.setDelay((long)(100/this.speed));
    }

    private void checkMovement() {
        double xa = 0;
        double ya = 0;
        if(gameState.gsm.isKeyDown(KeyCode.UP) || gameState.gsm.isKeyDown(KeyCode.W)) {
            ya--;
        }
        if(gameState.gsm.isKeyDown(KeyCode.DOWN) || gameState.gsm.isKeyDown(KeyCode.S)) {
            ya++;
        }
        if (gameState.gsm.isKeyDown(KeyCode.LEFT) || gameState.gsm.isKeyDown(KeyCode.A)) {
            xa--;
        }
        if (gameState.gsm.isKeyDown(KeyCode.RIGHT) || gameState.gsm.isKeyDown(KeyCode.D)) {
            xa++;
        }

        if(xa != 0 || ya != 0) {
            move(xa, ya);
            isMoving = true;
        } else {
            isMoving = false;
            if(movementAnimation.isMoving())
                movementAnimation.setIsMoving(this.isMoving);
        }
        //TODO: Swimming checks
    }

    public void move(double xa, double ya) {
        //this.xa = xa;
        //this.ya = ya;

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
        x += xa * speed;
        y += ya * speed;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    private void setMapPosition() {
        xmap = gameState.world.area.getTileMap().getx();
        ymap = gameState.world.area.getTileMap().gety();
    }

    public void update() {
        checkMovement();
        movementAnimation.update();
        tickCount++;
    }

    public void render(GraphicsContext gc) {
        //TODO: Get facingDir and set sprite image
        this.setMapPosition();

        gc.drawImage(
                movementAnimation.getImage(),
                (x + xmap - width / 2.0)*OmniaFX.getScale(),
                (y + ymap - height / 2.0)*OmniaFX.getScale(),
                width * OmniaFX.getScale(),
                height * OmniaFX.getScale());
        //gc.drawImage(sprites.get(0)[0], 50, 50, width* OmniaFX.getScale(), height * OmniaFX.getScale());
    }
}
