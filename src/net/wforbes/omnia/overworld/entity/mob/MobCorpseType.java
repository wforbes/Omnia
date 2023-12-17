package net.wforbes.omnia.overworld.entity.mob;

import net.wforbes.omnia.db.Database;
import net.wforbes.omnia.overworld.gui.loot.Loot;
import net.wforbes.omnia.overworld.world.area.object.AreaObjectType;

public class MobCorpseType extends AreaObjectType {
    public String type;

    public MobCorpseType(String type) {
        this.type = type;
    }

    @Override
    public Loot getRandomLootInstance(Database db) {
        //TODO: restructure database to handle mob loot
        return new Loot();
    }
}
