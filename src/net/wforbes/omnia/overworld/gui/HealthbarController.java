package net.wforbes.omnia.overworld.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
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

import java.text.DecimalFormat;

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
    private ProgressIndicator progressIndicator;
    private Label progressLabel;

    public HealthbarController(GUIController gui, Entity owner) {
        this.gui = gui;
        this.owner = owner;
        this.getWindowPanel(); //create window for the first time
        this.initStartingHealth();
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
        this.progressLabel = new Label();
        this.progressBar.progressProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                double value = newVal.doubleValue()*100;
                DecimalFormat df = new DecimalFormat(
                        (value > 99) ? "###.#"
                            : (value > 9.9999999999) ? "##.#"
                            : "#.#"
                );
                progressLabel.setText(df.format(value)+"%");
            }
        });
        HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(this.progressBar, this.progressLabel);

        VBox verticalLayoutContainer = new VBox();
        verticalLayoutContainer.getChildren().addAll(this.nameLabel, hb);
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
        System.out.println("isVisible: " + this.isVisible);
        this.isVisible = !this.isVisible;
        if (this.isVisible) {
            this.relocateWindow();
            if (!gui.getPanelsPane().getChildren().contains(this.getWindowPanel())) {
                gui.panelsPane.getChildren().add(this.getWindowPanel());
            }
            //gui.addHealthbarPane(this);
        } else {
            if (gui.panelsPane.getChildren().contains(this.windowPanel)) {
                gui.panelsPane.getChildren().remove(this.windowPanel);
            }
            //gui.removeHealthbarPane(this);
            //refocus canvas when actionWindow is closed
           // gui.gameState.getManager().getGameController().gameCanvas.requestFocus();
        }
    }

    private boolean isFullHealth() {
        return 1 == (this.owner.getCurrentHealth() / this.owner.getMaxHealth());
    }

    public void update() {
        if (
            ((!this.owner.isOnScreen() && this.isVisible)
            || (this.owner.isOnScreen() && !this.isVisible))
            && this.gui != null
        ) {
            this.toggleHealthbarVisible();
        }
        /*
        if (
            ( this.isVisible && this.isFullHealth() )
            || (!this.isVisible && !this.isFullHealth())
        ) {
            this.toggleHealthbarVisible();
        }*/
        //if (this.owner != null && this.isVisible) {
        if (this.owner != null && this.owner.isOnScreen()) {
            this.relocateWindow();
        }
    }

    public void initStartingHealth() {
        this.progressBar.setProgress(
            (double)this.owner.getCurrentHealth() / (double)this.owner.getMaxHealth()
        );
    }

    public void updateHealth(double progress) {
        //System.out.println(progress);
        if (progress > 0.0) {
            this.progressBar.setProgress(progress);
            return;
        }
        this.progressBar.setProgress(0);
    }

    private void relocateWindow() {
        Renderable m = this.owner;

        // TODO: find best location
        this.windowPanel.relocate(
            (
                    ((m.getX() + m.getXMap()) + m.getHealthbarXOffset()*1.25) * getScale()
                )
                - (m.getWidth()/3.0) * getScale(), //- this.windowPanel.getLayoutBounds().getWidth() / 2.0,
                (m.getY() + m.getYMap()) * getScale()
                        - this.windowPanel.getLayoutBounds().getHeight() * 1.5
                        //- this.owner.getNameText().getLayoutBounds().getHeight() * 0.5
        );
    }
}
