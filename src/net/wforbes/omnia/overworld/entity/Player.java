package net.wforbes.omnia.overworld.entity;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.animation.MovementAnimation;
import net.wforbes.omnia.overworld.entity.attention.TargetController;
import net.wforbes.omnia.overworld.entity.action.harvest.HarvestController;
import net.wforbes.omnia.overworld.entity.pathfind.PathfindController;
import net.wforbes.omnia.overworld.entity.projectile.ProjectileController;

public class Player extends Mob {
    protected String spriteSheetPath = "/overworld/sprites/player1_pokemon_v2.gif";
    private double lastInputTick = 0;
    private int lastInputCommandTick = 0;

    private int dashDistance = 4;
    private int dashDelay = 120;
    private int lastDashTick = 0;

    private double scale = 1;
    private double collisionBoxWidth;
    private double collisionBoxHeight;

    protected int currentAction;
    private TargetController targetController;
    private ProjectileController projectileController;
    private HarvestController harvestController;


    public Player(OverworldState gameState, String name) {
        super(gameState, name, 0.5, true);
        this.width = this.height = 16;
        this.nameColor = Color.BLUE;
        this.nameFlashColor = Color.LIGHTBLUE;
    }

    public void init() {
        this.numFrames = new int[]{3, 3, 3, 3, 3, 3, 3, 3};
        this.combatNumFrames = new int[]{4, 4, 4, 4, 4, 4, 4, 4};
        this.loadSprites(OverworldState.SPRITE_DIR + "player1_pokemon_v3.gif");
        this.facingDir = FACING_S;
        movementAnimation = new MovementAnimation(this);
        this.setAnimationDirection(facingDir);
        this.targetController = new TargetController();//TODO: pass this to targetController for player references
        this.projectileController = new ProjectileController(this);
        this.harvestController = new HarvestController(this);

        //TODO: user hits harvest button, gui shows gather timer bar,
        //  show loot window
        //      put item into player inventory
        //      OR keep item on node
        //  render empty gather node,
        //  start regrow timer on node,

    }

    public void init(double xPos, double yPos) {
        super.init(xPos, yPos);
        this.setPosition(xPos, yPos);
    }

    public TargetController getTargetController() {
        return this.targetController;
    }
    public ProjectileController getProjectileController() {
        return this.projectileController;
    }

    public void update() {
        super.update();
        checkCommands();
        checkMovement();
        if (this.pathfindController.isPathing()) {
            checkPathing();
        }
        movementAnimation.update();
        checkActions();
        gameState.gui.getDevWindow().setPlayerMapPos(this.x, this.y);
        gameState.gui.getDevWindow().setPlayerScreenPos(Math.floor(this.x+xmap) * OmniaFX.getScale(), Math.floor(this.y+ymap) * OmniaFX.getScale());
        this.projectileController.update();
        this.harvestController.update();
    }

    private int keydownCooldown = 50;
    private int keydownTime = 0;
    private void checkActions() {
        if (gameState.keyboardController.isKeyDown(KeyCode.DIGIT1)) {
            this.projectileController.fireProjectile();
        }
        if (gameState.keyboardController.isKeyDown(KeyCode.H)) {
            this.harvestController.harvestMaterials();
        }
        if (gameState.keyboardController.isKeyDown(KeyCode.BACK_QUOTE)) {
            if (keydownTime == 0) {
                this.attacking = !this.attacking; //TODO: Move to CombatController
                this.keydownTime++;
            }
        }
        if (keydownTime > 0 && keydownTime <= keydownCooldown) {
            keydownTime++;
        } else {
            keydownTime = 0;
        }
    }

    private boolean dashReady() {
        return gameState.getTickCount() - lastDashTick > dashDelay || lastDashTick == 0;
    }

    private void checkPathing() {
        if (!this.pathfindController.isPathing()) return;
        double[] nextMove = this.pathfindController.getNextMove(this.getXActual(), this.getYActual());
        if (nextMove == null) {
            this.pathfindController.setIsPathing(false);
            return;
        }
        this.isMoving = true;
        move(nextMove[0], nextMove[1]);
        this.cancelStationaryActions();
    }

    private void checkMovement() {
        double xa = 0;
        double ya = 0;
        if(
            gameState.keyboardController.isKeyDown(KeyCode.UP)
            || gameState.keyboardController.isKeyDown(KeyCode.W)
        ) {
            ya--;
        }
        if(
            gameState.keyboardController.isKeyDown(KeyCode.DOWN)
            || gameState.keyboardController.isKeyDown(KeyCode.S)
        ) {
            ya++;
        }
        if(
            gameState.keyboardController.isKeyDown(KeyCode.LEFT)
            || gameState.keyboardController.isKeyDown(KeyCode.A)
        ) {
            xa--;
        }
        if (
            gameState.keyboardController.isKeyDown(KeyCode.RIGHT)
            || gameState.keyboardController.isKeyDown(KeyCode.D)
        ) {
            xa++;
        }
        if(xa != 0 || ya != 0) {
            if (gameState.keyboardController.isKeyDown(KeyCode.SHIFT)) {
                this.setRunning(true);
            } else if(this.isRunning){
                this.setRunning(false);
            }
            //TODO: experimenting with dash
            if(gameState.keyboardController.isKeyDown(KeyCode.SPACE) && dashReady()) {
                int tileSize = gameState.getWorld().getCurrentArea().getTileMap().getTileSize();
                xa *= dashDistance * tileSize;
                ya *= dashDistance * tileSize;
                lastDashTick = gameState.getTickCount();
            }
            if(this.pathfindController.isPathing()) System.out.println("Canceling pathing");
            this.pathfindController.cancelPathing();
            isMoving = true;
            move(xa, ya);
            this.cancelStationaryActions();
        } else {
            isMoving = false;
            if(movementAnimation.isMoving())
                movementAnimation.setIsMoving(this.isMoving);
        }
        //TODO: Swimming checks
    }

    private void checkCommands() {
        if(gameState.keyboardController.isKeyDown(KeyCode.ESCAPE) && keyInputIsReady()) {
            gameState.keyboardController.consumeKey(KeyCode.ESCAPE);
            if (gameState.gui.hasFocus()) {
               gameState.gsm.gameController.gameCanvas.requestFocus();
            } else {
                gameState.pause();
            }
        }

        if(gameState.keyboardController.isKeyDown(KeyCode.ENTER) && keyInputIsReady()) {
            gameState.keyboardController.consumeKey(KeyCode.ENTER);
            if(!gameState.gui.chatWindowVisible) {
                gameState.gui.toggleChatWindowVisible();
            } else {
                gameState.gui.getChatWindow().focusChatField();
            }
        }
    }

    private void cancelStationaryActions() {
        this.harvestController.cancelHarvesting();
    }

    private boolean keyInputIsReady() {
        return gameState.keyboardController.keyInputIsReady();
    }

    public void render(GraphicsContext gc) {
        super.render(gc);
        this.projectileController.render(gc);
    }


    public void teardown() {
        super.teardown();
        this.width = this.height = 16;
        this.numFrames = null;
        this.spriteSheet = null;
        if (this.targetController != null) {
            this.targetController.teardown();
            this.targetController = null;
        }
        if (this.projectileController != null) {
            this.projectileController.teardown();
            this.projectileController = null;
        }
    }
}
