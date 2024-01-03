package net.wforbes.omnia.overworld.entity.mob.player;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import net.wforbes.omnia.game.Game;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.entity.animation.MovementAnimation;
import net.wforbes.omnia.overworld.entity.attention.PlayerTargetController;
import net.wforbes.omnia.overworld.entity.action.harvest.HarvestController;
import net.wforbes.omnia.overworld.entity.combat.stat.MobStats;
import net.wforbes.omnia.overworld.entity.mob.Mob;
import net.wforbes.omnia.overworld.entity.pathfind.PlayerPathfindController;
import net.wforbes.omnia.overworld.entity.projectile.ProjectileController;

public class Player extends Mob {
    protected String spriteSheetPath = "/overworld/sprites/player1_pokemon_v2.gif";
    private double lastInputTick = 0;
    private int lastInputCommandTick = 0;

    private int dashDistance = 4;
    private int dashDelay = 120;
    private int lastDashTick = 0;

    protected int currentAction;
    private PlayerTargetController targetController;
    private ProjectileController projectileController;
    private HarvestController harvestController;

    public Player(OverworldState gameState, String name) {
        super(gameState, name, 0.5, true, new MobStats(
                1000, 0.01F, 50, 0.75F, 1.75F
        ));
        this.width = this.height = 16;
        this.meleeReach = 4;
        this.nameColor = Color.BLUE;
        this.nameFlashColor = Color.LIGHTBLUE;
        this.mobType = "player";
        this.pathfindController = new PlayerPathfindController(this);
    }

    @Override
    public Entity getTarget() {
        return this.targetController.getTarget();
    }

    @Override
    public void setTarget(Entity e) {
        this.targetController.setTarget(e);
    }

    public void init() {
        this.numFrames = new int[]{3, 3, 3, 3, 3, 3, 3, 3};
        this.combatNumFrames = new int[]{4, 4, 4, 4, 4, 4, 4, 4};
        this.loadSprites(OverworldState.SPRITE_DIR + "player1_pokemon_v3.gif");
        this.facingDir = FACING_S;
        movementAnimation = new MovementAnimation(this);
        this.setAnimationDirection(facingDir);
        this.targetController = new PlayerTargetController(this);
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

    @Override
    public boolean hasAttentionOnSomething() {
        return false;
    }

    public PlayerTargetController getTargetController() {
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
        gameState.gui.getDevWindow().setPlayerScreenPos(Math.floor(this.x+xmap) * Game.getScale(), Math.floor(this.y+ymap) * Game.getScale());
        this.projectileController.update();
        this.harvestController.update();
    }

    private int keydownCooldown = 50;
    private int keydownTime = 0;
    private boolean lastUpdateHandled = true;
    private int updateCount = 0;
    private int lastUpdateCounted = 0;
    private void checkActions() {
        if (gameState.keyboardController.isKeyDown(KeyCode.DIGIT1)) {
            this.projectileController.fireProjectile();
        }
        if (gameState.keyboardController.isKeyDown(KeyCode.H)) {
            this.harvestController.harvestMaterials();
        }
    }

    public void handleAttackKeyPress() {
        this.combatController.toggleAttacking();
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
