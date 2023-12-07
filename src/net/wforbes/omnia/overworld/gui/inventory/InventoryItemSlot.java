package net.wforbes.omnia.overworld.gui.inventory;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import net.wforbes.omnia.overworld.gui.item.Item;

public class InventoryItemSlot {
    private final int slotNum;
    private final Rectangle slotRect;
    private Image displayImage;
    private Item containedItem;

    public InventoryItemSlot(int slotNum) {
        this.slotNum = slotNum;
        this.slotRect = new Rectangle(50, 50);
        this.slotRect.setArcWidth(10.0);
        this.slotRect.setArcHeight(10.0);
        InnerShadow innerShadow = new InnerShadow(BlurType.THREE_PASS_BOX, Color.DARKGRAY, 5.0, 0.1, -1, -1);
        this.slotRect.setEffect(innerShadow);
        this.slotRect.getStyleClass().add("inv-slot");
    }

    public Rectangle getDisplayGraphic() {
        return this.slotRect;
    }

    public void setContainedItem(Item item) {
        System.out.println("setContainedItem: " + item);
        this.containedItem = item;
        //TODO: figure out displayGraphic situation
        this.displayImage = this.containedItem.getIcon().getDisplayImage();
        this.slotRect.setFill(new ImagePattern(this.displayImage));
        System.out.println("Results...");
        System.out.println(this.containedItem);
        System.out.println(this.displayImage);
        System.out.println(this.slotRect);
    }

    public Item getContainedItem() {
        return this.containedItem;
    }

    public void removeItemFromSlot() {
        this.containedItem = null;
        this.setDisplayToEmpty();
    }

    public void setDisplayToEmpty() {
        this.displayImage = null;
        this.slotRect.setFill(null);
    }
}
