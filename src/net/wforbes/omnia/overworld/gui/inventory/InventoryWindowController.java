package net.wforbes.omnia.overworld.gui.inventory;

import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import net.wforbes.omnia.overworld.gui.DragResizer;
import net.wforbes.omnia.overworld.gui.GUIController;
import net.wforbes.omnia.u.W;

import java.util.ArrayList;

public class InventoryWindowController {

    public Dimension2D windowSize = new Dimension2D(200, 200);
    private static final int SLOTAREA_FLOW_WIDTH = 200;
    private static final int SLOTAREA_FLOW_HEIGHT = 200;
    private final GUIController gui;
    private final String windowTitle;
    private final Font tooltipFont;
    private TitledPane titledPane;
    private Node inventoryPanel;
    private VBox inventoryVerticalContainer;
    private GridPane slotPane;
    private ArrayList<Rectangle> slotArray;

    public InventoryWindowController(GUIController gui) {
        this.gui = gui;
        this.windowTitle = "Inventory";
        this.tooltipFont = new Font("Century Gothic", 18);
    }

    public double getWindowWidth() {
        return this.windowSize.getWidth();
    }
    public double getWindowHeight() {
        return this.windowSize.getHeight();
    }

    public Node getWindowPanel() {
        if(inventoryPanel == null) {
            this.inventoryPanel = createInventoryPanel();
        }
        return inventoryPanel;
    }

    private Node createInventoryPanel() {
        this.createTitledPane();
        this.createSlotPane();
        this.createInventoryVerticalContainer();
        titledPane.setContent(inventoryVerticalContainer);
        DragResizer.makeResizable(this.titledPane);
        return titledPane;
    }

    private void createTitledPane() {
        this.titledPane = new TitledPane();
        this.titledPane.setText(this.windowTitle);
        this.titledPane.setCollapsible(false);
        this.titledPane.setMinSize(SLOTAREA_FLOW_WIDTH, SLOTAREA_FLOW_HEIGHT);
        this.titledPane.setOpacity(GUIController.OPACITY_MAX);
    }

    private void createSlotPane() {
        this.slotPane = new GridPane();
        this.slotArray = new ArrayList<>();
        for(int i = 0; i < 16; i++) {
            Rectangle r = new Rectangle(50, 50, Color.BLUE);
            int finalI = i;
            r.setOnMouseClicked(event -> {
               System.out.println("Clicked slot #" + finalI);
            });
            this.slotArray.add(r);
            this.slotPane.add(this.slotArray.get(i),(i%4), (i/4));
        }
        this.slotPane.setHgap(5);
        this.slotPane.setVgap(5);
        //this.slotPane.setPadding(new Insets(10, 10, 10, 10));
    }

    private void createInventoryVerticalContainer() {
        inventoryVerticalContainer = new VBox();
        inventoryVerticalContainer.getChildren().addAll(slotPane);
    }
}
