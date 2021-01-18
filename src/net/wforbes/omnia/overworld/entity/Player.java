package net.wforbes.omnia.overworld.entity;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.animation.MovementAnimation;

public class Player extends Mob {
    protected String spriteSheetPath = "/overworld/sprites/player1_pokemon.gif";
    private double lastInputTick = 0;
    private int lastInputCommandTick = 0;

    //TODO: inheritable

    private double scale = 1;
    private double collisionBoxWidth;
    private double collisionBoxHeight;

    protected int currentAction;

    public Player(OverworldState gameState, String name) {

        super(gameState, name, 0.5, true);
        this.nameColor = Color.BLUE;
    }

    public Player(OverworldState gameState, String name, Point2D startPos) {
        super(gameState, name, startPos, 0.5);
    }

    public void init() {
        this.width = this.height = 16;
        this.numFrames = new int[]{3, 3, 3, 3, 3, 3, 3, 3};
        this.loadSprites(OverworldState.SPRITE_DIR + "player1_pokemon.gif");
        this.facingDir = FACING_S;
        movementAnimation = new MovementAnimation(this);
        this.setAnimationDirection(facingDir);
    }

    public void update() {
        checkMovement();
        checkCommands();
        movementAnimation.update();
        gameState.gui.getDevWindow().setPlayerMapPos(this.x, this.y);
        gameState.gui.getDevWindow().setPlayerScreenPos(Math.floor(this.x+xmap) * OmniaFX.getScale(), Math.floor(this.y+ymap) * OmniaFX.getScale());
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

    private void checkCommands() {
        if(gameState.gsm.isKeyDown(KeyCode.ESCAPE) && keyInputIsReady()) {
            lastInputCommandTick = gameState.getTickCount();
            if (gameState.gui.hasFocus()) {
               gameState.gsm.gameController.gameCanvas.requestFocus();
            } else {
                //TODO: implement pause
            }
        }

        if(gameState.gsm.isKeyDown(KeyCode.ENTER) && keyInputIsReady()) {
            lastInputCommandTick = gameState.getTickCount();
            if(!gameState.gui.chatWindowVisible) {
                gameState.gui.toggleChatWindowVisible();
            }
        }
    }

    private boolean keyInputIsReady() {
        return gameState.getTickCount() - lastInputCommandTick > 30;
    }

    public void render(GraphicsContext gc) {
        super.render(gc);
    }
}
