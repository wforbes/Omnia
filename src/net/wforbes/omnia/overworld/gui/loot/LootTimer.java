package net.wforbes.omnia.overworld.gui.loot;

import net.wforbes.omnia.overworld.entity.action.Lootable;

public class LootTimer {
    public Lootable owner;
    public boolean isRunning = false;
    public int lootTime = 0;
    public int lootTimeDuration = 2000;
    public LootTimer(Lootable owner) {
        this.owner = owner;
    }

    //
    public void start() {
        System.out.println("LootTimer start for " + this.owner);
        this.lootTime = this.lootTime != 0 ? 0 : this.lootTime;
        this.isRunning = true;
        this.lootTime++;
    }

    public void endEarly() {
        if (this.isRunning) {
            this.lootTime = this.lootTimeDuration;
        }
        this.owner.notifyLootTimerDone();
    }

    public void update() {
        if (!this.isRunning) return;
        if (this.lootTime > 0) {
            this.lootTime++;
            if (this.lootTime <= this.lootTimeDuration && this.lootTime % 10 == 0) {
                System.out.println(
                        "loot timer (mod10): " +
                                ((float) this.lootTime / (float) this.lootTimeDuration)
                );
            }
            if (this.lootTime >= this.lootTimeDuration) {
                this.isRunning = false;
                this.lootTime = 0;
                this.owner.notifyLootTimerDone();
                //this.owner = null;
            }
        }
    }
}
