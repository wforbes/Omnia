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
    private ArrayList<Image[]> sprites;

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
    private double movingDir = 1;
    private double scale = 1;
    private double collisionBoxWidth;
    private double collisionBoxHeight;


    public Player(OverworldState gameState) {
        this.gameState = gameState;
    }

    //TODO: replace this with Point2D
    public double getX(){ return x; }
    public double getY(){ return y; }
    public int getWidth(){ return width; }
    public int getHeight(){ return height; }

    public void init() {
        this.width = this.height = 30;
        this.spriteSheet = new Image(getClass().getResourceAsStream("/overworld/sprites/player_sprites.gif"));
        this.sprites = new ArrayList<Image[]>();

        Image[] images = new Image[1];
        images[0] = new WritableImage(spriteSheet.getPixelReader(), 0, 0,30,30);
        sprites.add(images);

        this.setPosition(60, 60);
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
        //TODO: check for collision

        moveCoords(xa, ya);
        numSteps++;
    }
    private void moveCardinal(double xa, double ya){
        if (ya < 0)
            movingDir = 0;
        if (ya > 0)
            movingDir = 1;
        if (xa < 0)
            movingDir = 2;
        if (xa > 0)
            movingDir = 3;
    }
    private void moveDiagonal(double xa, double ya){
        if (ya < 0 && xa  < 0)
            movingDir = 4;
        if (ya < 0 && xa > 0)
            movingDir = 5;
        if (ya > 0 && xa < 0)
            movingDir = 6;
        if (ya > 0 && xa > 0)
            movingDir = 7;
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
        xmap = gameState.level.getTileMap().getx();
        ymap = gameState.level.getTileMap().gety();
    }

    public void update() {
        checkMovement();
        tickCount++;
    }

    public void render(GraphicsContext gc) {
        //TODO: Get movingDir and set sprite image
        this.setMapPosition();
        gc.drawImage(
                sprites.get(0)[0],
                (x + xmap - width / 2.0)*OmniaFX.getScale(),
                (y + ymap - height / 2.0)*OmniaFX.getScale(),
                width * OmniaFX.getScale(),
                height * OmniaFX.getScale());
        //gc.drawImage(sprites.get(0)[0], 50, 50, width* OmniaFX.getScale(), height * OmniaFX.getScale());
    }
}
