package net.wforbes.omnia.overworld.gui;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class WindowDisplayController {
    private GUIController gui;
    private TitledPane titledPane;
    private Node windowPanel;
    private Font buttonFont;
    public WindowDisplayController(GUIController gui) {
        this.gui = gui;
        this.buttonFont = new Font("Century Gothic", 12);
    }

    public Node getWindowPanel() {
        if(windowPanel == null) {
            this.windowPanel = createWindowPanel();
        }
        return windowPanel;
    }

    public Region getTitledPane() {
        return titledPane;
    }

    private Node createWindowPanel() {
        this.titledPane = new TitledPane();
        this.titledPane.setText("UI Windows");
        this.titledPane.setCollapsible(false);
        this.titledPane.setOpacity(GUIController.OPACITY_MAX);
        this.setWindowFadeTransitions();
        HBox buttonContainer = new HBox();

        ToggleButton chatBtn = new ToggleButton("Chat");
        chatBtn.setFont(buttonFont);
        chatBtn.setFocusTraversable(true);
        chatBtn.setOnMouseClicked(event -> {
            this.gui.toggleChatWindowVisible();
            event.consume();
        });
        chatBtn.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            this.gui.setGUIHasFocus(newValue);
        });

        ToggleButton devBtn = new ToggleButton("Dev");
        devBtn.setFont(buttonFont);
        devBtn.setFocusTraversable(true);
        devBtn.setOnMouseClicked(event -> {
            this.gui.toggleDevWindowVisible();
            event.consume();
        });
        devBtn.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            this.gui.setGUIHasFocus(newValue);
        });

        ToggleButton settingsBtn = new ToggleButton("Settings");
        settingsBtn.setFont(buttonFont);
        settingsBtn.setFocusTraversable(true);
        settingsBtn.setOnMouseClicked(event -> {
            //this.gui.toggleSettingsWindowVisible();
            event.consume();
        });
        settingsBtn.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            this.gui.setGUIHasFocus(newValue);
        });

        buttonContainer.getChildren().addAll(chatBtn, devBtn);
        //GUIController.configureBorder(titledPane);
        titledPane.setContent(buttonContainer);
        return titledPane;
    }

    //TODO: make generic and add to GUIController
    private void setWindowFadeTransitions() {
        FadeTransition titledPaneFadeIn = new FadeTransition(Duration.millis(500), titledPane);
        titledPaneFadeIn.setFromValue(GUIController.OPACITY_MAX);
        titledPaneFadeIn.setToValue(1.0);

        FadeTransition titledPaneFadeOut = new FadeTransition(Duration.millis(500), titledPane);
        titledPaneFadeOut.setFromValue(1.0);
        titledPaneFadeOut.setToValue(GUIController.OPACITY_MAX);

        titledPane.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            titledPaneFadeIn.play();
        });
        titledPane.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            titledPaneFadeOut.play();
        });
    }

    public void teardown() {
        this.gui = null;
        this.buttonFont = null;
        this.titledPane.setVisible(false);
        this.titledPane = null;
    }
}
