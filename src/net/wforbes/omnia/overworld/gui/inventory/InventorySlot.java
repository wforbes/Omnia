package net.wforbes.omnia.overworld.gui.inventory;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class InventorySlot {
    private final int slotNum;
    private final Rectangle slotRect;
    //private Image displayImage;

    public InventorySlot(int slotNum) {
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
}
