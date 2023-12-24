package net.wforbes.omnia.overworld.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import net.wforbes.omnia.gameFX.rendering.Renderable;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.combat.CombatController;
import net.wforbes.omnia.overworld.entity.combat.stat.StatController;

public abstract class Entity implements Renderable {
    public OverworldState gameState;
    protected boolean spawned;
    public Entity(OverworldState gameState) {
        this.gameState = gameState;
    }
    public abstract Entity getCollidingEntity();
    public abstract Entity getTarget();
    public abstract void setTarget(Entity e);
    public abstract boolean isInMeleeRange(Entity e);
    public abstract int getMeleeReach();
    public abstract boolean isOnScreen();
    public StatController statController;
    public abstract StatController getStatController();
    public abstract int getMaxMeleeDamage();
    public abstract float getMeleeAccuracy();
    protected CombatController combatController;
    public abstract CombatController getCombatController();
    public abstract void notifyEnemyAggro(Entity source);
    public abstract void notifyCombatKill(Entity source);
    public abstract void receiveMeleeDamage(int dmg, Entity dealer);
    public abstract void addStatChange(StatController.StatChange change);
    public abstract int getCurrentHealth();
    public abstract int getMaxHealth();
    public abstract void kill(Entity killer);
    public abstract boolean isDead();
    public abstract String getName();
    public abstract Text getNameText();
    protected int collisionXOffset;
    protected int collisionYOffset;
    public abstract int getCollisionXOffset();
    public abstract int getCollisionYOffset();
    protected int collisionBoxWidth;
    protected int collisionBoxHeight;
    public abstract int getCollisionBoxWidth();
    public abstract int getCollisionBoxHeight();
    public abstract double getCollisionRadius();
    public abstract double getX();
    public abstract double getXActual();
    public abstract double getY();
    public abstract double getYActual();
    public abstract double getCollisionBaseX();
    public abstract double getCollisionBaseY();
    public abstract double getBaseY();
    public abstract void init();
    protected abstract void init(double xPos, double yPos);
    public abstract void update();
    public abstract void render(GraphicsContext gc);
    public abstract void teardown();
    public String lastMethod;
}
