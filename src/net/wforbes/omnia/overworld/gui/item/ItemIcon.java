package net.wforbes.omnia.overworld.gui.item;

import javafx.scene.image.Image;

import java.util.Objects;

public class ItemIcon {
    private Image displayImage;
    public ItemIcon(String itemName) {
        if (itemName.equals("Blueberry")) {
            this.displayImage = new Image(getClass().getResource("/overworld/inventory/icons/ingredients/1.png").toExternalForm());
        }
        if (itemName.equals("Leaf")) {
            this.displayImage = new Image(getClass().getResource("/overworld/inventory/icons/plants/leaf.png").toExternalForm());
        }
        if (itemName.equals("Botanical Energy")) {
            this.displayImage = new Image(getClass().getResource("/overworld/inventory/icons/plants/botanical_energy.png").toExternalForm());
        }
        if (itemName.equals("Sticks")) {
            this.displayImage = new Image(getClass().getResource("/overworld/inventory/icons/plants/sticks.png").toExternalForm());
        }
    }
}
