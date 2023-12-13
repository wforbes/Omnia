package net.wforbes.omnia.overworld.entity.action;

import net.wforbes.omnia.overworld.gui.loot.Loot;

public interface Lootable {
    void setLoot(Loot loot);
    Loot getLoot();
    void returnLoot(Loot loot);
    void notifyLootTimerDone();
    void generateAndSetLoot();
}
