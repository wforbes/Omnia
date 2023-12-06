package net.wforbes.omnia.overworld.gui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class TitledWindowController {
    protected final GUIController gui;
    protected final String windowTitle;
    private final String windowId;
    protected Node windowPanel;
    protected TitledPane titledPane;
    protected boolean visible;
    public TitledWindowController(GUIController gui, String windowId, String windowTitle) {
        this.visible = false;
        this.gui = gui;
        this.windowTitle = windowTitle;
        this.windowId = windowId;
        this.windowPanel = gui.getWindowDragger().makeDraggableByTitleRegion(
                this.getWindowPanel()
        );
    }

    public Node getWindowPanel() {
        if(windowPanel == null) {
            this.windowPanel = this.createWindow();
        }
        return windowPanel;
    }

    protected Node createWindow() {
        System.out.println("ERROR: can't directly create TitledWindowController");
        return null;
    }

    protected void createTitledPane() {
        this.titledPane = new TitledPane();
        BorderPane borderPane = new BorderPane();
        Label titleLbl = new Label(this.windowTitle);
        Button closeBtn = new Button("X");
        closeBtn.setOnMouseClicked(event -> {
            this.toggleVisible();
            event.consume();
        });
        borderPane.setCenter(titleLbl);
        borderPane.setRight(closeBtn);
        borderPane.prefWidthProperty().bind(this.titledPane.widthProperty().subtract(20));
        this.titledPane.setGraphic(borderPane);
        //this.titledPane.setText(this.windowTitle);
        this.titledPane.setCollapsible(false);
        //this.titledPane.setMinSize(SLOTAREA_FLOW_WIDTH, SLOTAREA_FLOW_HEIGHT);
        this.titledPane.setOpacity(GUIController.OPACITY_MAX);
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void toggleVisible() {
        if (this.visible) {
            this.close();
        } else {
            this.show();
        }
    }

    public void show() {
        if (!this.visible) {
            this.visible = true;
            this.gui.getPanelsPane().getChildren().add(windowPanel);
        }
    }

    public void close() {
        //TODO: close out this looting session...whatever that means
        //this.gui.toggleLootWindowVisible();
        if (this.visible) {
            this.visible = false;
            this.gui.getPanelsPane().getChildren().remove(windowPanel);
            //TODO: make it easier to get gameCanvas from GUIController
            this.gui.gameState.getManager().getGameController().gameCanvas.requestFocus();
        }
    }

    private void handleCloseClick(MouseEvent event) {
        this.close();
        event.consume();
    }
}
