package net.wforbes.omnia.overworld.entity.mob.enemy;

import javafx.scene.paint.Color;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.entity.animation.MovementAnimation;
import net.wforbes.omnia.overworld.entity.attention.EnemyTargetController;
import net.wforbes.omnia.overworld.entity.mob.Mob;

public class Enemy extends Mob {

    protected final EnemyTargetController enemyTargetController;

    public Enemy(OverworldState gameState, String name, String spritePath, double width, double height) {
        super(gameState, name, 0.45, false);
        this.width = 24;
        this.height = 23;
        this.nameColor = Color.DARKRED;
        this.nameFlashColor = Color.RED;
        this.numFrames = new int[]{3, 3, 3, 3};
        this.combatNumFrames = new int[]{3, 3, 3, 3};
        this.loadSprites(spritePath);
        this.movementAnimation = new MovementAnimation(this);
        this.facingDir = FACING_S;
        this.setAnimationDirection(facingDir);
        this.enemyTargetController = new EnemyTargetController(this);
        //this.npcTargetController.setAttentionSpan(30);
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

    @Override
    public void update() {
        super.update();
    }

    private void initCollisionShape() {
        System.out.println("enemy initCollisionShape()");
    }

    @Override
    public boolean hasAttentionOnSomething() {
        return false;
    }
}
