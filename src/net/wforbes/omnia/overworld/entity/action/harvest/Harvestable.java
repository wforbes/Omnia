package net.wforbes.omnia.overworld.entity.action.harvest;

import net.wforbes.omnia.overworld.entity.action.Actionable;
import net.wforbes.omnia.overworld.entity.action.Lootable;

public interface Harvestable extends Actionable, Lootable {
    //void completeHarvest();
    boolean isHarvested();
}
