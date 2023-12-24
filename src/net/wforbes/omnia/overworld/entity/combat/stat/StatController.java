package net.wforbes.omnia.overworld.entity.combat.stat;

import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.gui.HealthbarController;

public class StatController {
    private final Entity owner;
    private int maxHealth;
    private float healthRegen;
    private static final int COMBAT_REGEN_MOD = 10;
    private int currentHealth;
    private HealthbarController healthbarController;
    private int maxMeleeDmg;
    private float meleeAccuracy = 0.75F;

    private float meleeSpeed;

    public enum STAT_CHANGE_TYPE {
        HEALTH, MANA, EXP, HP_REGEN
    }

    public class StatChange {
        public STAT_CHANGE_TYPE type;
        public int amount;
        public int renderProgress;
        public double opacity;
        public boolean readyToRemove;
        public boolean floatDir = false;
        public StatChange(int amount, STAT_CHANGE_TYPE type) {
            this.amount = amount;
            this.type = type;
            this.renderProgress = 0;
            this.opacity = 1;
            this.readyToRemove = false;
        }
    }

    //TODO: Removal candidate
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
        this.healthRegen = stats.healthRegen;
        this.maxMeleeDmg = stats.maxMeleeDmg;
        this.meleeAccuracy = stats.meleeAccuracy;
        this.meleeSpeed = stats.meleeSpeed;
    }

    public void receiveMeleeDamage(int dmg, Entity source) {
        this.currentHealth -= dmg;

        System.out.println(this.owner.getName() + " took " + dmg + " points of damage!");
        this.owner.addStatChange(
            new StatChange(-dmg, STAT_CHANGE_TYPE.HEALTH)
        );

        this.healthbarController.updateHealth(
            this.getCurrentHealthPct()
        );
        if (this.currentHealth <= 0) {
            System.out.println(this.owner.getName() + " was slain by " + source.getName() + "!");
            source.notifyCombatKill(this.owner);
            this.owner.kill(source);
        }
    }

    public void notifyCombatKill(Entity source) {
        //TODO: award experience based on source data
        //  maybe compare owner's level with source level and award based on diff?
        this.owner.addStatChange(new StatChange(100, STAT_CHANGE_TYPE.EXP));
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
    public float getMeleeSpeed() {
        return this.meleeSpeed;
    }

    private int tickCounter = 0;
    public void update() {
        tickCounter++;
        if (tickCounter > 360) {
            this.handleHealthRegen();
            tickCounter = 0;
        }

        if (this.healthbarController != null) this.healthbarController.update();
    }

    private float pendingRegenFraction = 0.0F;

    private void handleHealthRegen() {
        if (this.currentHealth == this.maxHealth) return;
        if (this.currentHealth > this.maxHealth) {
            this.currentHealth = this.maxHealth;
            return;
        }
        int regenAmount = (int) Math.ceil(this.maxHealth * (this.healthRegen /
                ((this.owner.getCombatController().isInCombat()) ?
                        COMBAT_REGEN_MOD : 1
                )
        ));
        System.out.println("attempted regen: " + Math.ceil((this.maxHealth * (this.healthRegen /
                        ((this.owner.getCombatController().isInCombat()) ?
                                COMBAT_REGEN_MOD : 1
                        )
                ))));
        System.out.println("regenAmount: " + regenAmount);
        //TODO: capture regen amounts less than 1 and accumulate them
        //  until they are equal or greater than 1, at which point
        //  1 should be added to regenAmount
        /*
        double roundedOff = regenAmount - Math.floor(regenAmount);
        System.out.println("Regen roundedOff: " + roundedOff);
        if (roundedOff > 0 && roundedOff < 1) {
            pendingRegenFraction += roundedOff;
        }
        double pendingAdditional = 0;
        if (pendingRegenFraction > 1) {
            pendingAdditional = pendingRegenFraction - Math.floor(pendingRegenFraction);
            pendingRegenFraction = pendingRegenFraction - 1;
        }
         */
        this.currentHealth += regenAmount;
        System.out.println(this.owner + " regenerated " + regenAmount + " health");
        this.owner.addStatChange(new StatChange(
            regenAmount, STAT_CHANGE_TYPE.HP_REGEN
        ));
        this.healthbarController.updateHealth(
                this.getCurrentHealthPct()
        );
    }
}
