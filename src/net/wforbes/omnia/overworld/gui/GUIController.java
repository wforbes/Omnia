package net.wforbes.omnia.overworld.gui;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameState.OverworldState;

public class GUIController {
    public OverworldState gameState;

    private boolean guiHasFocus;
    private WindowDragger windowDragger;
    private DevWindowController devWindowController;

    public GUIController(OverworldState state) {
        //this.gameController = state.getManager().gameController;
        this.gameState = state;
        this.guiHasFocus = false;
        final Pane panelsPane = new Pane();
        panelsPane.setStyle("-fx-background-color: rgba(0,100,100,0.5);-fx-background-radius:10;");
        panelsPane.setPrefSize(0.1,0.1);

        this.windowDragger = new WindowDragger();

        this.devWindowController = new DevWindowController(this);
        Node devWindowPanel = devWindowController.getWindowPanel();
        devWindowPanel = windowDragger.makeDraggableByTitleRegion(devWindowPanel);
        devWindowPanel.relocate(0, OmniaFX.getScaledHeight() - (devWindowController.getWindowHeight()));
        panelsPane.getChildren().add(devWindowPanel);

        this.gameState.getManager().getGameController().getGameBorderPane().setTop(panelsPane);
    }

    public boolean getGUIHasFocus() {
        return this.guiHasFocus;
    }
    public void setGUIHasFocus(boolean focused) {
        this.guiHasFocus = focused;
    }
    public DevWindowController getDevWindowController() {
        return this.devWindowController;
    }
    public void handleBorderPaneClick(MouseEvent event) {
        this.devWindowController.handleBorderPaneClick(event);
    }
    public void handleBorderPaneMouseMove(MouseEvent event) {
        this.devWindowController.handleBorderPaneMouseMove(event);
    }

    public static void configureBorder(final Region region) {
        region.setStyle("-fx-background-color: white;"
                + "-fx-border-color: black;"
                + "-fx-border-width: 1;"
                + "-fx-border-radius: 6;"
                + "-fx-padding: 6;");
    }

    public void tick() {
        //chatWindowController.tick();
    }
}
