package net.wforbes.omnia.overworld.entity.mob.enemy;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
//import javafx.scene.shape.Line;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.entity.animation.MovementAnimation;
import net.wforbes.omnia.overworld.entity.attention.EnemyTargetController;
import net.wforbes.omnia.overworld.entity.combat.stat.MobStats;
import net.wforbes.omnia.overworld.entity.mob.Mob;
import net.wforbes.omnia.overworld.entity.movement.FollowTargetBehavior;
import net.wforbes.omnia.overworld.entity.movement.StandBehavior;
import net.wforbes.omnia.overworld.entity.pathfind.EnemyPathfindController;

import static net.wforbes.omnia.game.Game.getScale;

public class Enemy extends Mob {

    private boolean isAggroed = false;

    protected final EnemyTargetController enemyTargetController;
    

    private boolean shouldRenderFovPoly = false;
    private boolean shouldRenderFovLines = false;

    //refatoramento
    private final EnemyFieldOfView fieldOfView;

    public Enemy(OverworldState gameState, String name, String spritePath, int width, int height, MobStats stats) {
        super(gameState, name, 0.45, false, stats);
        this.width = width;
        this.height = height;
        this.nameColor = Color.DARKRED;
        this.nameFlashColor = Color.RED;
        this.numFrames = new int[]{3, 3, 3, 3};
        this.combatNumFrames = new int[]{3, 3, 3, 3};
        this.loadSprites(spritePath);
        this.movementAnimation = new MovementAnimation(this);
        this.facingDir = FACING_W;
        // nova classe do refatoramento
        this.fieldOfView = new EnemyFieldOfView(75.0);
        this.speed = 0.3;
        this.setAnimationDirection(facingDir);
        this.enemyTargetController = new EnemyTargetController(this);
        this.pathfindController = new EnemyPathfindController(this);
        this.enemyTargetController.setAttentionSpan(720);
    }

    /*
    @Override
    public Entity getCollidingEntity() {
        return super.getCollidingEntity();
    }*/

    @Override
    public Entity getTarget() {
        return this.enemyTargetController.getTarget();
    }

    @Override
    public void setTarget(Entity e) {
        this.enemyTargetController.setTarget(e);
    }

    @Override
    public void init() {
        //nope
    }

    public void init(double xPos, double yPos) {
        super.init(xPos, yPos);
        this.setPosition(xPos, yPos);
    }

    //refatoracao
    @Override
    public void update() {
        super.update();
        this.movementController.update();
        this.movementAnimation.update();


        if (!isAggroed) {
            this.checkAggroTriggers();
        } else {
            //refatoracao
            this.fieldOfView.setAggroFOV(
            getFacingDir(),
            getXActual(),
            getYActual(),
            xmap,
            ymap
            );
        }
    }

    public void receiveMeleeDamage(int dmg, Entity dealer) {
        super.receiveMeleeDamage(dmg, dealer);
        if (!this.isAggroed) {
            this.startAggro(dealer);
        }
        //TODO: Consider multiple attackers, managing aggro list
        //  'having aggro' related to being at the top of aggro list
    }

    private void checkAggroTriggers() {
        this.checkAggroFOV();
    }

    private void checkAggroFOV() {
        
        this.fieldOfView.setAggroFOV(
        getFacingDir(),
        getXActual(),
        getYActual(),
        xmap,
        ymap
        );

        Point2D playerPosition =
        gameState.getWorld().getPlayerLocation();

        if (fieldOfView.contains(playerPosition) && !isAggroed) {
        startAggro(gameState.getPlayer());
        }
    }

    private void startAggro(Entity target) {
        this.isAggroed = true;
        this.setTarget(target);
        this.enemyTargetController.getTarget().notifyEnemyAggro(this);
        this.movementController.setMovementBehavior(new FollowTargetBehavior());
        this.pathfindController.moveToPoint(
                new Point2D(target.getXActual(), target.getYActual())
        );
        if (!this.combatController.isAttacking())
            this.combatController.toggleAttacking();
    }

    private void stopAggro() {
        this.isAggroed = false;
        this.movementController.setMovementBehavior(new StandBehavior());
    }


    @Override
    public boolean hasAttentionOnSomething() {
        return this.isAggroed;
    }

    //refatoramento, trocando atributos por get da nova classe
    public void render(GraphicsContext gc) {
        super.render(gc);

        if (this.shouldRenderFovLines) {
        gc.setStroke(Color.LIME);
        gc.strokeLine(
                (this.getXActual() + xmap) * getScale(),
                (this.getYActual() + ymap) * getScale(),
                (this.getXActual() + xmap + fieldOfView.getXFP() + fieldOfView.getFovQEdge(0) + fieldOfView.getFovQEdge(1) + fieldOfView.getFovQEdge(2) + fieldOfView.getFovQEdge(3)) * getScale(),
                (this.getYActual() + ymap + fieldOfView.getYFP() + fieldOfView.getFovQEdge(0) + fieldOfView.getFovQEdge(1) + fieldOfView.getFovQEdge(2) + fieldOfView.getFovQEdge(3)) * getScale()
        );
        gc.strokeOval(fieldOfView.getFovLine().getEndX() * getScale(), fieldOfView.getFovLine().getEndY() * getScale(), 2, 2);
        gc.setStroke(Color.LIGHTBLUE);
        gc.strokeLine(
                (this.getXActual() + xmap + fieldOfView.getXFP() + fieldOfView.getFovQEdge(0) + fieldOfView.getFovQEdge(1) + fieldOfView.getFovQEdge(2) + fieldOfView.getFovQEdge(3)) * getScale(),
                (this.getYActual() + ymap + fieldOfView.getYFP() + fieldOfView.getFovQEdge(0) + fieldOfView.getFovQEdge(1) + fieldOfView.getFovQEdge(2) + fieldOfView.getFovQEdge(3)) * getScale(),
                (fieldOfView.getFovEdgePoint(0).getX() + xmap) * getScale(),
                (fieldOfView.getFovEdgePoint(0).getY() + ymap) * getScale()
        );

        gc.setStroke(Color.gray(1));
        gc.strokeLine(
                (this.getXActual() + xmap) * getScale(),
                (this.getYActual() + ymap) * getScale(),
                (fieldOfView.getFovEdgePoint(0).getX() + xmap) * getScale(),
                (fieldOfView.getFovEdgePoint(0).getY() + ymap) * getScale()
        );

        gc.setStroke(Color.RED);
        gc.strokeLine(
                (this.getXActual() + xmap) * getScale(),
                (this.getYActual() + ymap) * getScale(),
                (fieldOfView.getFovEdgePoint(1).getX() + xmap) * getScale(),
                (fieldOfView.getFovEdgePoint(1).getY() + ymap) * getScale()
        );

        gc.setStroke(Color.PINK);
        gc.strokeLine(
                (this.getXActual() + xmap + fieldOfView.getXFP() + fieldOfView.getFovQEdge(0) + fieldOfView.getFovQEdge(1) + fieldOfView.getFovQEdge(2) + fieldOfView.getFovQEdge(3)) * getScale(),
                (this.getYActual() + ymap + fieldOfView.getYFP() + fieldOfView.getFovQEdge(0) + fieldOfView.getFovQEdge(1) + fieldOfView.getFovQEdge(2) + fieldOfView.getFovQEdge(3)) * getScale(),
                (fieldOfView.getFovEdgePoint(1).getX() + xmap) * getScale(),
                (fieldOfView.getFovEdgePoint(1).getY() + ymap) * getScale()
        );
    }
    if (this.shouldRenderFovPoly) {
        gc.setStroke(Color.POWDERBLUE);
        gc.strokeLine(
                (fieldOfView.getFovPolyPoint(0).getX() + xmap) * getScale(),
                (fieldOfView.getFovPolyPoint(0).getY() + ymap) * getScale(),
                (fieldOfView.getFovPolyPoint(1).getX() + xmap) * getScale(),
                (fieldOfView.getFovPolyPoint(1).getY() + ymap) * getScale()
        );
        gc.strokeLine(
                (fieldOfView.getFovPolyPoint(1).getX() + xmap) * getScale(),
                (fieldOfView.getFovPolyPoint(1).getY() + ymap) * getScale(),
                (fieldOfView.getFovPolyPoint(2).getX() + xmap) * getScale(),
                (fieldOfView.getFovPolyPoint(2).getY() + ymap) * getScale()
        );
        gc.strokeLine(
                (fieldOfView.getFovPolyPoint(2).getX() + xmap) * getScale(),
                (fieldOfView.getFovPolyPoint(2).getY() + ymap) * getScale(),
                (fieldOfView.getFovPolyPoint(3).getX() + xmap) * getScale(),
                (fieldOfView.getFovPolyPoint(3).getY() + ymap) * getScale()
        );
        gc.strokeLine(
                (fieldOfView.getFovPolyPoint(3).getX() + xmap) * getScale(),
                (fieldOfView.getFovPolyPoint(3).getY() + ymap) * getScale(),
                (fieldOfView.getFovPolyPoint(0).getX() + xmap) * getScale(),
                (fieldOfView.getFovPolyPoint(0).getY() + ymap) * getScale()
        );
    }
    
    }

    @Override
    public void teardown() {
        super.teardown();
    }

}
