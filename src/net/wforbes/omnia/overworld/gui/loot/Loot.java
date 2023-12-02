package net.wforbes.omnia.overworld.gui.loot;

import net.wforbes.omnia.overworld.gui.item.Item;

import java.util.ArrayList;

public class Loot {
    private ArrayList<Item> items;
    public Loot() {
        this.items = new ArrayList<>();
    }

    public Loot(ArrayList<Item> items) {
        this.items = items;
    }

    public String toString() {
        if (this.items.isEmpty()) {
            return super.toString();
        }
        StringBuilder rStr = new StringBuilder();
        rStr.append("------------\n");
        rStr.append(super.toString()).append(" Loot:\n");
        for(Item i: this.items) {
            rStr.append(i.getName()).append("#").append(i.getId());
            rStr.append("\n");
        }
        rStr.append("------------\n");
        return rStr.toString();
    }
}
