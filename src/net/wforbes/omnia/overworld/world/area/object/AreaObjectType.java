package net.wforbes.omnia.overworld.world.area.object;

import net.wforbes.omnia.db.Database;
import net.wforbes.omnia.overworld.gui.loot.Loot;

public abstract class AreaObjectType {
    protected String type;
    protected String spriteFile;

    public abstract Loot getRandomLootInstance(Database db);
    public String getSpriteFile() {
        return this.spriteFile;
    }
}
