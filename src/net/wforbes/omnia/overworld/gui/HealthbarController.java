package net.wforbes.omnia.overworld.gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.wforbes.omnia.gameFX.rendering.Renderable;
import net.wforbes.omnia.overworld.entity.Entity;

import static net.wforbes.omnia.gameFX.OmniaFX.getScale;

public class HealthbarController {
    private final GUIController gui;
    private final Entity owner;
    private Node windowPanel;
    private Pane pane;
    private ProgressBar progressBar;
    private boolean isVisible = false;
    private Text nameText;
    private Label nameLabel;

    public HealthbarController(GUIController gui, Entity owner) {
        this.gui = gui;
        this.owner = owner;
        this.getWindowPanel();
    }

    public Node getWindowPanel() {
        System.out.println("healthBar getWindowPanel");
        if(this.windowPanel == null) {
            this.windowPanel = createWindowPanel();
        }
        return windowPanel;
    }

    public Node createWindowPanel() {
        this.pane = this.createWindowPane();
        this.nameLabel = new Label(this.owner.getName());
        this.nameLabel.setStyle("-fx-font-weight: 700");
        this.progressBar = new ProgressBar();
        this.progressBar.getStyleClass().add("health-progress-bar");
        VBox verticalLayoutContainer = new VBox();
        verticalLayoutContainer.getChildren().addAll(this.nameLabel, this.progressBar);
        this.pane.getChildren().add(verticalLayoutContainer);
        this.pane.setOpacity(1.0);
        return pane;
    }

    private Pane createWindowPane() {
        this.pane = new Pane();
        pane.getStyleClass().add("health-pane");
        //titledPane.setText(windowTitle);
        //titledPane.setCollapsible(false);
        //titledPane.setMinWidth(windowSize.getWidth());
        //titledPane.setMinHeight(windowSize.getHeight());
        //titledPane.setOpacity(GUIController.OPACITY_MAX);
        return pane;
    }

    public void showWindow(boolean show) {
        if (isVisible) {
            if (!show) { // clean up any previous state
                //
            }
            return; // trying to open already open window... exception?
        }
        this.toggleHealthbarVisible();
    }

    public void toggleHealthbarVisible() {
        System.out.println("toggleHealthbarVisible");
        this.isVisible = !this.isVisible;
        if(this.isVisible) {
            this.relocateWindow();
            //gui.panelsPane.getChildren().add(gui.actionWindowPanel);
            gui.addHealthbarPane(this);
        } else {
            //gui.panelsPane.getChildren().remove(gui.actionWindowPanel);
            gui.removeHealthbarPane(this);
            //refocus canvas when actionWindow is closed
           // gui.gameState.getManager().getGameController().gameCanvas.requestFocus();
        }
    }

    private boolean isFullHealth() {
        return 1 == (this.owner.getCurrentHealth() / this.owner.getMaxHealth());
    }

    public void update() {
        if (
            ( this.isVisible && this.isFullHealth() )
            || (!this.isVisible && !this.isFullHealth())
        ) {
            this.toggleHealthbarVisible();
        }
        if (this.owner != null && this.isVisible) {
            this.relocateWindow();
        }
    }

    public void updateHealth(double progress) {

        this.progressBar.setProgress(progress);
    }

    private void relocateWindow() {
        Renderable m = this.owner;

        // TODO: find best location
        this.windowPanel.relocate(
            (
                    ((m.getX() + m.getXMap()) + m.getCollisionXOffset()*1.25) * getScale()
                )
                - (m.getWidth()/3.0) * getScale(), //- this.windowPanel.getLayoutBounds().getWidth() / 2.0,
                (m.getY() + m.getYMap()) * getScale()
                        - this.windowPanel.getLayoutBounds().getHeight() * 1.5
                        //- this.owner.getNameText().getLayoutBounds().getHeight() * 0.5
        );
    }
}
