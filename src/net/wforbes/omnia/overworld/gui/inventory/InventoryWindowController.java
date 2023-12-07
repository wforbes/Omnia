package net.wforbes.omnia.overworld.gui.inventory;

import javafx.event.EventType;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.overworld.gui.DragResizer;
import net.wforbes.omnia.overworld.gui.GUIController;
import net.wforbes.omnia.overworld.gui.TitledWindowController;
import net.wforbes.omnia.overworld.gui.item.Item;
import net.wforbes.omnia.u.W;

import java.util.ArrayList;

public class InventoryWindowController extends TitledWindowController {

    public Dimension2D windowSize = new Dimension2D(200, 200);
    private static final int SLOTAREA_FLOW_WIDTH = 200;
    private static final int SLOTAREA_FLOW_HEIGHT = 200;
    private final Font tooltipFont;
    private VBox inventoryVerticalContainer;
    private GridPane slotPane;
    private ArrayList<Rectangle> displaySlotArray;
    private ArrayList<InventoryItemSlot> itemSlotArray;

    public InventoryWindowController(GUIController gui) {
        super(gui, "inventory", "Inventory");
        this.tooltipFont = new Font("Century Gothic", 18);
        this.windowPanel.relocate(OmniaFX.getScaledWidth()
                - this.getWindowWidth() - 50, 0
        );
    }

    public double getWindowWidth() {
        return this.windowSize.getWidth();
    }
    public double getWindowHeight() {
        return this.windowSize.getHeight();
    }

    @Override
    protected Node createWindow() {
        this.createTitledPane();
        this.createSlotPane();
        this.createVerticalContainer(this.slotPane);
        titledPane.setContent(inventoryVerticalContainer);
        titledPane.setOnMouseClicked(event -> {
            W.takeFocus(titledPane);
        });
        DragResizer.makeResizable(this.titledPane, this.gui);
        return titledPane;
    }

    private void createVerticalContainer(GridPane slotPane) {
        inventoryVerticalContainer = new VBox();
        inventoryVerticalContainer.getChildren().addAll(slotPane);
    }

    private void createSlotPane() {
        this.slotPane = new GridPane();
        this.displaySlotArray = new ArrayList<>();
        this.itemSlotArray = new ArrayList<>();
        for(int i = 0; i < 16; i++) {
            InventoryItemSlot slot = new InventoryItemSlot(i);
            int fI = i;
            slot.getDisplayGraphic().setOnMouseClicked(event -> this.handleSlotClick(event, fI));
            this.displaySlotArray.add(slot.getDisplayGraphic());
            this.slotPane.add(this.displaySlotArray.get(i),(i%4), (i/4));
            this.itemSlotArray.add(i, new InventoryItemSlot(i));
        }
        this.slotPane.setHgap(5);
        this.slotPane.setVgap(5);
    }

    private void handleSlotClick(MouseEvent event, int slotNum) {
        System.out.println("Clicked inventory slot #"+slotNum);
        System.out.println("displaySlotArray contents: " + this.displaySlotArray.get(slotNum));
        System.out.println("itemSlotArray contents: " + this.itemSlotArray.get(slotNum).getContainedItem());
        //TODO: handle possible error state where itemSlotArray doesn't have this index populated
        if (this.itemSlotArray.get(slotNum) == null) return;
        System.out.println("No key is pressed: " + gui.gameState.keyboardController.noKeyIsPressed());
        if (
            event.getButton() == MouseButton.PRIMARY
            && gui.gameState.keyboardController.noKeyIsPressed()
        ) {
            if (this.gui.getItemCursorController().isHoldingItem()) {
                this.itemSlotArray.get(slotNum).setContainedItem(
                        this.gui.getItemCursorController().putdownHeldItem()
                );
                this.setAndRefreshDisplay(slotNum);
                return;
            }
        }

        //if () {

        //}
    }

    private void setAndRefreshDisplay(int slotNum) {
        // remove display for this slot using graphic rectangle reference array
        this.slotPane.getChildren().remove(this.displaySlotArray.get(slotNum));
        // add new display graphic at correct col/row for slotNum
        this.slotPane.add(
            this.itemSlotArray.get(slotNum).getDisplayGraphic(),
            slotNum%4, slotNum/4
        );
        // set new display graphic rectangle in array to reference later
        this.displaySlotArray.set(slotNum, itemSlotArray.get(slotNum).getDisplayGraphic());

    }

    public boolean autoAddItemToInventory(Item item) {
        int emptySlot = -1;
        for (int i = 0; i < this.itemSlotArray.size(); i++) {
            if (this.itemSlotArray.get(i).getContainedItem() == null) {
                emptySlot = i;
                this.itemSlotArray.get(emptySlot).setContainedItem(item);
                this.setAndRefreshDisplay(emptySlot);
                break;
            }
        }
        return emptySlot != -1;
    }


}
