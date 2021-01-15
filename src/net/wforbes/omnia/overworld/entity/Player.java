package net.wforbes.omnia.overworld.entity;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import net.wforbes.omnia.gameState.OverworldState;

public class Player extends Mob {
    protected String spriteSheetPath = "/overworld/sprites/player1_pokemon.gif";
    private double lastInputTick = 0;

    //TODO: inheritable

    private double scale = 1;
    private double collisionBoxWidth;
    private double collisionBoxHeight;

    protected int currentAction;

    public Player(OverworldState gameState, String name) {
        super(gameState, name, 1.25);
    }

    public Player(OverworldState gameState, String name, Point2D startPos) {
        super(gameState, name, startPos, 1);
    }

    public void init() {
        this.width = this.height = 16;
        this.numFrames = new int[]{3, 3, 3, 3, 3, 3, 3, 3};
        this.loadSprites(OverworldState.SPRITE_DIR + "player1_pokemon.gif");
        this.facingDir = FACING_S;
        movementAnimation = new MovementAnimation();
        this.setAnimationDirection(facingDir);
    }

    public void update() {
        checkMovement();
        movementAnimation.update();
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

    public void render(GraphicsContext gc) {
        super.render(gc);
    }
}
