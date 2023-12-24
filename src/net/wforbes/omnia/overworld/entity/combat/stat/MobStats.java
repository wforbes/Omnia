package net.wforbes.omnia.overworld.entity.combat.stat;

public class MobStats {
    public int maxHealth;
    public float healthRegen;
    public int maxMeleeDmg;
    public float meleeAccuracy;
    public float meleeSpeed = 1.0F;

    //TODO: convert maxMeleeDmg to 'baseMeleeDmg' when weapons and str are introduced
    //  convert accuracy to 'baseMeleeAccuracy' when dex/agi are introduced
    //  add avoidance as 'baseMeleeAvoidance' when agi/dex are introduced
    public MobStats(int maxHealth, float healthRegen, int maxMeleeDmg, float meleeAccuracy) {
        this.maxHealth = maxHealth;
        this.maxMeleeDmg = maxMeleeDmg;
        this.meleeAccuracy = meleeAccuracy;
        this.healthRegen = healthRegen;
    }

    public MobStats(int maxHealth, float healthRegen, int maxMeleeDmg, float meleeAccuracy, float meleeSpeed) {
        this.maxHealth = maxHealth;
        this.maxMeleeDmg = maxMeleeDmg;
        this.meleeAccuracy = meleeAccuracy;
        this.healthRegen = healthRegen;
        this.meleeSpeed = meleeSpeed;
    }
}
