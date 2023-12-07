package net.wforbes.omnia.overworld.gui.item;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import net.wforbes.omnia.overworld.gui.GUIController;

public class ItemCursorController {
    private boolean visible;
    private final GUIController gui;
    //private Pane pane;
    //private Node windowPanel;
    private Image itemIcon;
    private Item heldItem;

    public ItemCursorController(GUIController gui) {
        this.visible = false;
        this.gui = gui;
        //this.windowPanel = this.getWindowPanel();
    }

    public void toggleVisible() {
        System.out.println("icc visible: " + this.visible);
        if (this.visible) {
            this.close();
        } else {
            this.show();
        }
    }

    public boolean isHoldingItem() {
        return this.heldItem != null;
    }

    public void pickupItem(Item item) {
        this.heldItem = item;
        this.itemIcon = item.getIcon().getDisplayImage();
        this.visible = true;
        // display itemIcon as the cursor on GameController.Scene
        this.gui.getPanelsPane().getScene().setCursor(
                new ImageCursor(this.itemIcon)
        );
        // display the itemIcon as the cursor on each GUI window
        this.gui.getPanelsPane().getChildren().forEach(node -> {
            node.setCursor(
                    new ImageCursor(itemIcon)
            );
        });
    }

    public void show() {
        System.out.println("ItemCursorController.show: " + this.visible);
        if (!this.visible) {
            this.itemIcon = new Image(getClass().getResource("/overworld/inventory/icons/plants/leaf.png").toExternalForm());
            this.visible = true;
            // display itemIcon as the cursor on GameController.Scene
            this.gui.getPanelsPane().getScene().setCursor(
                    new ImageCursor(this.itemIcon)
            );
            // display the itemIcon as the cursor on each GUI window
            this.gui.getPanelsPane().getChildren().forEach(node -> {
                node.setCursor(
                        new ImageCursor(itemIcon)
                );
            });
        }
    }

    public Item putdownHeldItem() {
        this.gui.getPanelsPane().getScene().setCursor(Cursor.DEFAULT);
        this.gui.getPanelsPane().getChildren().forEach(node -> {
            node.setCursor(Cursor.DEFAULT);
        });
        this.visible = false;
        this.itemIcon = null;
        Item returnItem = this.heldItem;
        this.heldItem = null;
        return returnItem;
    }

    public void close() {
        if (this.visible) {
            this.visible = false;
            this.gui.getPanelsPane().getScene().setCursor(Cursor.DEFAULT);
            this.gui.getPanelsPane().getChildren().forEach(node -> {
                node.setCursor(Cursor.DEFAULT);
            });
            this.itemIcon = null;
        }
    }

    public Image getItemIcon() {
        return this.itemIcon;
    }

    /*
    //TODO: showing the item icon in a pane that follows the mouse
        // will require accurately getting x/y mouse positions when
        // over a GUIController.panelsPane child. I spent a whole night
        // struggling with this and the best path now is to use this more
        // simple setCursor strat. Come back to this later.
    private Node getWindowPanel() {
        if (windowPanel == null) {
            this.windowPanel = this.createWindow();
        }
        return windowPanel;
    }

    private Node createWindow() {
        this.pane = new Pane();
        //this.pane.setStyle("-fx-background-color: black;");
        this.pane.setPrefSize(50, 50);
        this.pane.relocate(0,0);
        Rectangle testRect = new Rectangle(50,50);
        testRect.getStyleClass().add("inv-slot");
        testRect.setArcWidth(10.0);
        testRect.setArcHeight(10.0);
        testRect.setFill(
                new ImagePattern(
                        new Image(getClass().getResource("/overworld/inventory/icons/plants/leaf.png").toExternalForm())
                )
        );
        this.pane.getChildren().add(testRect);
        this.pane.setOpacity(1.0);
        return pane;
    }

    public boolean isVisible() {
        return this.visible;
    }

    */

}
