package net.wforbes.omnia.overworld.entity.mob.enemy;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.combat.stat.MobStats;
import net.wforbes.omnia.overworld.entity.mob.enemy.Enemy;

public class Procyon extends Enemy {
    public Procyon(OverworldState gameState, String name) {
        super(gameState, name,
            OverworldState.SPRITE_DIR + "attack_test1.gif", 24, 23,
            new MobStats(500, 0.01F, 25, 0.65F)
        );
        //this.width = 24; this.height = 23;
        this.nameColor = Color.DARKRED;
        this.nameFlashColor = Color.RED;
        this.enemyTargetController.setAttentionSpan(30);
        this.mobType = "procyon";
        this.movementController.setMovementSpace(this.x-10, this.y-10, this.x+10, this.y+10);
    }
    @Override
    public void init(double xPos, double yPos) {
        super.init(xPos, yPos);
        this.initCollisionShape();
    }
    protected void initCollisionShape() {
        //entity/entity collision
        this.setCollisionXOffset(6);
        this.setCollisionYOffset(14);
        this.setCollisionBoxWidth(12);
        this.setCollisionBoxHeight(8);
        this.meleeReach = 4; //TODO: COMBAT - where should this really go?

        //entity/areaobject collision
        this.setCollisionBaseX(7);
        this.setCollisionBaseY(this.height - 6);
        this.setBaseY(this.getCollisionBaseY());
        this.setCollisionRadius(10);
        this.setCollisionBaseCenterPnt(new Point2D(this.getCollisionBaseX(), this.getCollisionBaseY()));
        this.setCollisionBaseCircle(new Circle(this.getCollisionBaseX(), this.getCollisionBaseY(), collisionRadius));
    }
}
