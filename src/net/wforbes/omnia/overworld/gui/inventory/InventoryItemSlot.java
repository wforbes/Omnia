package net.wforbes.omnia.overworld.gui.inventory;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import net.wforbes.omnia.overworld.gui.item.Item;

public class InventoryItemSlot {
    private final int slotNum;
    private final Rectangle slotRect;
    //private Image displayImage;
    private Item containedItem;

    public InventoryItemSlot(int slotNum) {
        this.slotNum = slotNum;
        this.slotRect = new Rectangle(50, 50);
        this.slotRect.setArcWidth(10.0);
        this.slotRect.setArcHeight(10.0);
        InnerShadow innerShadow = new InnerShadow(BlurType.THREE_PASS_BOX, Color.DARKGRAY, 5.0, 0.1, -1, -1);
        this.slotRect.setEffect(innerShadow);
        this.slotRect.getStyleClass().add("inv-slot");

        //this.displayImage = new Image(getClass().getResource("/overworld/inventory/icons/ingredients/1.png").toExternalForm());
        //this.slotRect.setFill(new ImagePattern(this.displayImage));
    }

    public Rectangle getDisplayGraphic() {
        return this.slotRect;
    }

    public void setContainedItem(Item item) {
        this.containedItem = item;
    }

    public Item getContainedItem() {
        return this.containedItem;
    }

    public void setDisplayToEmpty() {
        this.slotRect.setFill(null);
    }
}
