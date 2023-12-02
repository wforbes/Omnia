package net.wforbes.omnia.overworld.gui.item;

public class Item {
    private static int itemIDCounter;
    private int id;
    private String name;
    private String description;
    private ItemIcon icon;
    public Item(String itemName) {
        if (itemName.equals("Blueberry")) {
            Item.itemIDCounter++;
            this.id = Item.itemIDCounter;
            this.name = "Blueberry";
            this.description = "Delicious berries, high in antioxidants";
            this.icon = new ItemIcon(itemName);
            return;
        }
        if (itemName.equals("Leaf")) {
            Item.itemIDCounter++;
            this.id = Item.itemIDCounter;
            this.name = "Leaf";
            this.description = "Just a leaf, nothing special";
            this.icon = new ItemIcon(itemName);
        }

        if (itemName.equals("Botanical Energy")) {
            Item.itemIDCounter++;
            this.id = Item.itemIDCounter;
            this.name = "Botanical Energy";
            this.description = "The life energy of a botanical being. Useful for magic spells.";
            this.icon = new ItemIcon(itemName);
        }

        if (itemName.equals("Sticks")) {
            Item.itemIDCounter++;
            this.id = Item.itemIDCounter;
            this.name = "Sticks";
            this.description = "Just some sticks, nothing special";
            this.icon = new ItemIcon(itemName);
        }
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }
}
