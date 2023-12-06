package net.wforbes.omnia.overworld.gui.inventory;

import javafx.event.EventType;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
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
import net.wforbes.omnia.u.W;

import java.util.ArrayList;

public class InventoryWindowController extends TitledWindowController {

    public Dimension2D windowSize = new Dimension2D(200, 200);
    private static final int SLOTAREA_FLOW_WIDTH = 200;
    private static final int SLOTAREA_FLOW_HEIGHT = 200;
    private final Font tooltipFont;
    private VBox inventoryVerticalContainer;
    private GridPane slotPane;
    private ArrayList<Rectangle> slotArray;

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

    private void createSlotPane() {
        this.slotPane = new GridPane();
        this.slotArray = new ArrayList<>();
        for(int i = 0; i < 16; i++) {
            InventoryItemSlot slot = new InventoryItemSlot(i);
            int fI = i;
            slot.getDisplayGraphic().setOnMouseClicked(event -> this.handleSlotClick(event, fI));
            this.slotArray.add(slot.getDisplayGraphic());
            this.slotPane.add(this.slotArray.get(i),(i%4), (i/4));
        }
        this.slotPane.setHgap(5);
        this.slotPane.setVgap(5);
        //this.slotPane.setPadding(new Insets(10, 10, 10, 10));
    }

    private void handleSlotClick(MouseEvent event, int slotNum) {
        System.out.println("Clicked inventory slot #"+slotNum);
    }

    private void createVerticalContainer(GridPane slotPane) {
        inventoryVerticalContainer = new VBox();
        inventoryVerticalContainer.getChildren().addAll(slotPane);
    }


}
