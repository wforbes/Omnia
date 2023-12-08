package net.wforbes.omnia.overworld.gui.item;

public class Item {
    private static int itemIDCounter;
    private int id;
    private String name;
    private String description;
    private ItemIcon icon;

    public Item(int id, String name, String description, String iconPath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = new ItemIcon(iconPath);
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public ItemIcon getIcon() { return this.icon; }
}
