package net.wforbes.omnia.overworld.entity.combat.stat;

import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.gui.HealthbarController;

public class StatController {
    private final Entity owner;
    private int maxHealth;
    private int currentHealth;
    private HealthbarController healthbarController;

    private int maxMeleeDmg;
    private float meleeAccuracy = 0.75F;

    public StatController(Entity owner, MobStats stats, HealthbarController hc) {
        this.owner = owner;
        this.maxHealth = stats.maxHealth;
        this.currentHealth = stats.maxHealth;
        this.maxMeleeDmg = stats.maxMeleeDmg;
        this.meleeAccuracy = stats.meleeAccuracy;
        this.healthbarController = hc;
    }

    public StatController(Entity owner, MobStats stats) {
        this.owner = owner;
        this.maxHealth = stats.maxHealth;
        this.currentHealth = stats.maxHealth;
        this.maxMeleeDmg = stats.maxMeleeDmg;
        this.meleeAccuracy = stats.meleeAccuracy;
    }

    public void receiveMeleeDamage(int dmg, Entity source) {
        this.currentHealth -= dmg;
        if (dmg != 0) {
            System.out.println(this.owner.getName() + " took " + dmg + " points of damage!");
        }
        this.healthbarController.updateHealth(
            this.getCurrentHealthPct()
        );
        if (this.currentHealth <= 0) {
            System.out.println(this.owner.getName() + " was slain by " + source.getName() + "!");
            this.owner.kill(source);
        }
    }
    public int getCurrentHealth() {
        return this.currentHealth;
    }
    public int getMaxHealth() {
        return this.maxHealth;
    }

    public double getCurrentHealthPct() {
        return (double)this.currentHealth / (double)this.maxHealth;
    }
    public HealthbarController getHealthbarController() {
        return this.healthbarController;
    }
    public void setHealthbarController(HealthbarController hc) {
        System.out.println("setHealthbarController");
        this.healthbarController = hc;
        //this.healthbarController.initStartingHealth();
    }

    public int getMaxMeleeDmg() {
        return this.maxMeleeDmg;
    }

    public float getMeleeAccuracy() {
        return this.meleeAccuracy;
    }

    public void update() {
        if (this.healthbarController != null) this.healthbarController.update();
    }
}
