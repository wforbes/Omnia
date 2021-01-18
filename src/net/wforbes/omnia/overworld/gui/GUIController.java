package net.wforbes.omnia.overworld.gui;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.u.W;

public class GUIController {
    public OverworldState gameState;

    private boolean guiHasFocus;
    private WindowDragger windowDragger;
    private ChatWindowController chatWindowController;
    private DevWindowController devWindowController;
    private WindowDisplayController windowDisplayController;

    private Pane panelsPane;
    private Node windowDisplayPanel;
    private Node devWindowPanel;
    private Node chatWindowPanel;

    public boolean chatWindowVisible;
    public boolean devWindowVisible;


    public static double OPACITY_MAX = 0.5;

    public GUIController(OverworldState state) {
        this.gameState = state;
        this.guiHasFocus = false;

        this.windowDragger = new WindowDragger();

        this.chatWindowController = new ChatWindowController(this);
        chatWindowPanel = windowDragger.makeDraggableByTitleRegion(
                chatWindowController.getWindowPanel()
        );
        chatWindowPanel.relocate(0, OmniaFX.getScaledHeight() - chatWindowController.getWindowHeight());
        chatWindowVisible = false;

        this.devWindowController = new DevWindowController(this);
        devWindowPanel = windowDragger.makeDraggableByTitleRegion(
                devWindowController.getWindowPanel()
        );
        devWindowPanel.relocate(0, OmniaFX.getScaledHeight()
                - devWindowController.getWindowHeight() - 50
                - chatWindowController.getWindowHeight()
        );
        devWindowVisible = false;

        windowDisplayController = new WindowDisplayController(this);
        windowDisplayPanel = windowDragger.makeDraggableByTitleRegion(
                windowDisplayController.getWindowPanel()
        );

        this.panelsPane = new Pane();
        panelsPane.setStyle("-fx-background-color: rgba(0,100,100,0.5);-fx-background-radius:10;");
        panelsPane.setPrefSize(0.1,0.1);
        panelsPane.getChildren().addAll(windowDisplayPanel);

    }

    public void init() {
        this.gameState.getManager().getGameController().getGameBorderPane().setTop(panelsPane);
        Platform.runLater(()-> {
            windowDisplayPanel.relocate(
                    OmniaFX.getScaledWidth()/2.0
                            - windowDisplayPanel.getLayoutBounds().getWidth()/2.0,10);
        });
    }

    public boolean hasFocus() {
        return this.guiHasFocus;
    }
    public void setGUIHasFocus(boolean focused) {
        this.guiHasFocus = focused;
    }
    public DevWindowController getDevWindow() {
        return this.devWindowController;
    }
    public ChatWindowController getChatWindow() { return this.chatWindowController; }
    public void handleBorderPaneClick(MouseEvent event) {
        this.devWindowController.handleBorderPaneClick(event);
    }
    public void handleBorderPaneMouseMove(MouseEvent event) {
        this.devWindowController.handleBorderPaneMouseMove(event);
    }

    public void toggleDevWindowVisible() {
        devWindowVisible = !devWindowVisible;
        if(devWindowVisible) {
            panelsPane.getChildren().add(devWindowPanel);
        } else {
            panelsPane.getChildren().remove(devWindowPanel);
            //refocus canvas when devWindow is closed
            gameState.getManager().getGameController().gameCanvas.requestFocus();
        }
    }

    public void toggleChatWindowVisible() {
        chatWindowVisible = !chatWindowVisible;
        if(chatWindowVisible) {
            panelsPane.getChildren().add(chatWindowPanel);
            W.takeFocus(getChatWindow().getChatField());
            getChatWindow().setActiveThenFadeOut();
        } else {
            panelsPane.getChildren().remove(chatWindowPanel);
            //refocus canvas when chatWindow is closed
            gameState.getManager().getGameController().gameCanvas.requestFocus();
        }
    }

    public void tick() {
        //chatWindowController.tick();
    }
}
