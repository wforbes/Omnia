package net.wforbes.omnia.overworld.world.area.object.flora.tree;

import net.wforbes.omnia.overworld.gui.item.Item;
import net.wforbes.omnia.overworld.gui.loot.Loot;

import java.util.ArrayList;

public class TreeType {
    private String spriteFile;

    public static enum SIZES { SMALL, MEDIUM, LARGE };

    public static enum GENERA { OAK };

    public TreeType(TreeType.GENERA type) {
        if (type == TreeType.GENERA.OAK) {
            this.spriteFile = "fixed_Tree1.png";
        }
    }

    public String getSpriteFile() { return spriteFile; }

    Loot getLootInstance() {
        ArrayList<Item> lootItems = new ArrayList<>();
        lootItems.add(new Item("Sticks"));
        return new Loot(lootItems);
    }

}
