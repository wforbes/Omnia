package net.wforbes.omnia.overworld.gui.loot;

import javafx.event.Event;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import net.wforbes.omnia.game.Game;
import net.wforbes.omnia.overworld.entity.action.Lootable;
import net.wforbes.omnia.overworld.gui.DragResizer;
import net.wforbes.omnia.overworld.gui.GUIController;
import net.wforbes.omnia.overworld.gui.TitledWindowController;
import net.wforbes.omnia.overworld.gui.inventory.InventoryItemSlot;
import net.wforbes.omnia.overworld.gui.item.Item;
import net.wforbes.omnia.u.W;

import java.util.ArrayList;

public class LootWindowController extends TitledWindowController {

    public Dimension2D windowSize = new Dimension2D(200, 200);
    private GridPane slotPane;
    private ArrayList<Rectangle> displaySlotArray;//TODO:rename to denote display uses

    private ArrayList<InventoryItemSlot> itemSlotArray;
    private VBox lootVerticalContainer;
    private HBox buttonHorizontalContainer;
    private Button lowerCloseBtn;
    private boolean lowerCloseBtnFocused;
    private Button lootAllBtn;
    private boolean lootAllBtnFocused;
    private Loot focusedLoot;
    private Lootable lootTarget;


    public LootWindowController(GUIController gui) {
        super(gui, "loot", "Loot");
        windowPanel.relocate(Game.getScaledWidth()
                - this.getWindowWidth() - 250, 0
        );
    }

    public double getWindowWidth() {
        return this.windowSize.getWidth();
    }
    public double getWindowHeight() {
        return this.windowSize.getHeight();
    }

    public void show(Lootable lootTarget) {
        System.out.println("LootWindowController.show");
        if (!this.visible) {
            this.visible = true;
            this.gui.getPanelsPane().getChildren().add(windowPanel);
        }

        if (this.visible) {
            if (!this.gui.getInventoryWindow().isVisible()) {
                this.gui.getInventoryWindow().toggleVisible();
            }
            if (lootTarget.getLoot() == null || lootTarget.getLoot().getItems().isEmpty()) return;
            //System.out.println("Harvested Loot:" + loot);
            //this.focusedLoot = lootTarget.getLoot();
            this.lootTarget = lootTarget;
            for (int i = 0; i < this.lootTarget.getLoot().getItems().size(); i++) {
                this.itemSlotArray.get(i).setContainedItem(this.lootTarget.getLoot().getItems().get(i));
                //this.displaySlotArray.add(this.itemSlotArray.get(i).getDisplayGraphic());
                this.displaySlotArray.get(i).setFill(
                        new ImagePattern(this.lootTarget.getLoot().getItems().get(i).getIcon().getDisplayImage())
                );
            }

            for (InventoryItemSlot inventoryItemSlot : this.itemSlotArray) {
                if (inventoryItemSlot.getContainedItem() != null) {
                    System.out.println(inventoryItemSlot.getContainedItem().toString());
                }
            }
        }
    }

    public void close() {
        //TODO: close out this looting session...whatever that means
        //this.gui.toggleLootWindowVisible();
        if (this.visible) {
            this.visible = false;
            this.endLootSession();
            this.gui.getPanelsPane().getChildren().remove(windowPanel);
            for (int i = 0; i < this.itemSlotArray.size(); i++) {
                this.itemSlotArray.get(i).setDisplayToEmpty();
            }
            if (this.gui.getInventoryWindow().isVisible()) {
                this.gui.getInventoryWindow().toggleVisible();
            }
            /*
            if (this.gui.getItemCursorController().isVisible()) {
                this.gui.getItemCursorController().toggleVisible();
            }*/
        }
    }

    @Override
    protected Node createWindow() {
        this.createTitledPane();
        this.createSlotPane();
        this.createButtonPane();
        this.createVerticalContainer();
        titledPane.setContent(lootVerticalContainer);
        titledPane.setOnMouseClicked(event -> {
            W.takeFocus(titledPane);
        });
        DragResizer.makeResizable(this.titledPane, this.gui);
        return titledPane;
    }

    /* moved to parent
    private void createTitledPane() {
        this.titledPane = new TitledPane();
        BorderPane borderPane = new BorderPane();
        Label titleLbl = new Label(this.windowTitle);
        Button closeBtn = new Button("X");
        closeBtn.setOnMouseClicked(this::handleCloseClick);
        borderPane.setCenter(titleLbl);
        borderPane.setRight(closeBtn);
        this.titledPane.setGraphic(borderPane);
        this.titledPane.setCollapsible(false);
        this.titledPane.setOpacity(GUIController.OPACITY_MAX);
    }*/

    private void createSlotPane() {
        this.slotPane = new GridPane();
        this.displaySlotArray = new ArrayList<>();
        this.itemSlotArray = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            InventoryItemSlot slot = new InventoryItemSlot(i);
            int fI = i;
            slot.getDisplayGraphic().setOnMouseClicked(event -> this.handleSlotClick(event, fI));
            this.displaySlotArray.add(slot.getDisplayGraphic());
            this.slotPane.add(this.displaySlotArray.get(i),(i%2), (i/2));
            this.itemSlotArray.add(slot);
        }
        this.slotPane.setHgap(5);
        this.slotPane.setVgap(5);
    }

    private void createButtonPane() {
        buttonHorizontalContainer = new HBox();
        HBox.setHgrow(buttonHorizontalContainer, Priority.ALWAYS);
        VBox.setVgrow(buttonHorizontalContainer, Priority.ALWAYS);
        lootAllBtn = new Button("Loot All");
        this.lootAllBtnFocused = lootAllBtn.focusedProperty().getValue();
        lootAllBtn.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            this.gui.setGUIHasFocus(newValue);
        });

        lowerCloseBtn = new Button("Close");
        this.lowerCloseBtnFocused = lowerCloseBtn.focusedProperty().getValue();
        lowerCloseBtn.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            this.toggleVisible();
            this.gui.setGUIHasFocus(newValue);
        });
        lowerCloseBtn.setOnMouseClicked(event -> {
            this.close();
            event.consume();
        });
        lowerCloseBtn.setOnMousePressed(Event::consume);

        buttonHorizontalContainer.getChildren().addAll(lootAllBtn, lowerCloseBtn);
    }

    private void handleSlotClick(MouseEvent event, int slotNum) {
        System.out.println("Clicked loot window slot #"+slotNum);
        Item slotItem = this.itemSlotArray.get(slotNum).getContainedItem();
        if (slotItem == null) {
            System.out.println("WARN: There's no item in that item slot");
            return;
        }
        System.out.println(this.itemSlotArray.get(slotNum).getContainedItem().getName());
        if (
            event.getButton() == MouseButton.PRIMARY
            && gui.gameState.keyboardController.noKeyIsPressed()
        ) {
            // put item icon on mouse cursor, if there's room
            if (!this.gui.getItemCursorController().isHoldingItem()) {
                this.gui.getItemCursorController().pickupItem(slotItem);
                this.itemSlotArray.get(slotNum).removeItemFromSlot();
                this.slotPane.getChildren().remove(this.displaySlotArray.get(slotNum));
                this.itemSlotArray.get(slotNum).getDisplayGraphic().setOnMouseClicked(
                    e -> this.handleSlotClick(e, slotNum)
                );

                this.slotPane.add(
                    this.itemSlotArray.get(slotNum).getDisplayGraphic(),
                    slotNum%2, slotNum/2
                );
                return;
            }
            System.out.println("WARN: an item is already being held on cursor");
            return;
        }

        if (
            event.getButton() == MouseButton.PRIMARY &&
            gui.gameState.keyboardController.isKeyDown(KeyCode.ALT)
        ) {
            // automatically move item to player's inventory
            boolean moved = this.gui.getInventoryWindow().autoAddItemToInventory(
                this.itemSlotArray.get(slotNum).getContainedItem()
            );
            if (moved) {
                this.itemSlotArray.get(slotNum).removeItemFromSlot();
                return;
            }
            // if there was no room, try to put the item on the cursor
            if (!this.gui.getItemCursorController().isHoldingItem()) {
                this.gui.getItemCursorController().pickupItem(slotItem);
                this.itemSlotArray.get(slotNum).removeItemFromSlot();
                return;
            }
            System.out.println("TODO: show warning that the inventory is full");
        }

        if (
            event.getButton() == MouseButton.SECONDARY &&
            gui.gameState.keyboardController.isKeyDown(KeyCode.ALT)
        ) {
            // open item window
        }

        if (
            true // hold right mouse button for 1 second
        ) {
            // open item window
            // close item window when mouse button releases
        }


    }

    private void createVerticalContainer() {
        lootVerticalContainer = new VBox();
        lootVerticalContainer.getChildren().addAll(this.slotPane, this.buttonHorizontalContainer);
    }

    private void endLootSession() {
        if (this.lootTarget == null) return;
        ArrayList<Item> items = new ArrayList<>();
        System.out.println("Returning items to loot target:");
        for (InventoryItemSlot inventoryItemSlot : this.itemSlotArray) {
            if (inventoryItemSlot.getContainedItem() != null) {
                System.out.println(inventoryItemSlot.getContainedItem().toString());
                items.add(inventoryItemSlot.getContainedItem());
            }
        }
        this.lootTarget.returnLoot(new Loot(items));
        this.lootTarget = null;
    }
}
