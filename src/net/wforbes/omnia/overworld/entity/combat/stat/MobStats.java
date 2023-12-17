package net.wforbes.omnia.overworld.entity.combat.stat;

public class MobStats {
    public int maxHealth;
    public int maxMeleeDmg;
    public float meleeAccuracy;

    //TODO: convert maxMeleeDmg to 'baseMeleeDmg' when weapons and str are introduced
    //  convert accuracy to 'baseMeleeAccuracy' when dex/agi are introduced
    //  add avoidance as 'baseMeleeAvoidance' when agi/dex are introduced
    public MobStats(int maxHealth, int maxMeleeDmg, float meleeAccuracy) {
        this.maxHealth = maxHealth;
        this.maxMeleeDmg = maxMeleeDmg;
        this.meleeAccuracy = meleeAccuracy;
    }
}
