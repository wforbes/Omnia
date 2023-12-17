package net.wforbes.omnia.overworld.entity.combat.vital;

import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.gui.HealthbarController;

public class VitalController {
    private final Entity owner;
    private int maxHealth;
    private int currentHealth;
    private HealthbarController healthbarController;

    public VitalController(Entity owner, int maxHealth) {
        this.owner = owner;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public void receiveMeleeDamage(int dmg) {
        this.currentHealth -= dmg;
        System.out.println(this.owner.getName() + " took " + dmg + " points of damage!");
        this.healthbarController.updateHealth(
            this.getCurrentHealthPct()
        );
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

    public void setHealthbarController(HealthbarController hc) {
        System.out.println("setHealthbarController");
        this.healthbarController = hc;
    }

    public void update() {
        if (this.healthbarController != null) this.healthbarController.update();
    }
}
