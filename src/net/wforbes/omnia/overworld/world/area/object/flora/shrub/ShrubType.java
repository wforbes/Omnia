package net.wforbes.omnia.overworld.world.area.object.flora.shrub;

import net.wforbes.omnia.overworld.gui.item.Item;
import net.wforbes.omnia.overworld.gui.loot.Loot;

import java.util.ArrayList;

public class ShrubType {
    private String spriteFile;

    public static enum SIZES { SMALL, MEDIUM, LARGE };

    public static enum GENERA { BLUEBERRY };

    public ShrubType(GENERA type) {
        if (type == GENERA.BLUEBERRY) {
            this.spriteFile = "Bush_blue_flowers3.png";
        }
    }

    public String getSpriteFile() { return spriteFile; }

    Loot getLootInstance() {
        ArrayList<Item> lootItems = new ArrayList<>();
        lootItems.add(new Item("Blueberry"));
        lootItems.add(new Item("Leaf"));
        lootItems.add(new Item("Botanical Energy"));
        return new Loot(lootItems);
    }

}
