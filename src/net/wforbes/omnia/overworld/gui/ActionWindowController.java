package net.wforbes.omnia.overworld.gui;

import javafx.geometry.Dimension2D;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.*;
import net.wforbes.omnia.gameFX.rendering.Renderable;
import net.wforbes.omnia.overworld.entity.action.Actionable;

import static net.wforbes.omnia.gameFX.OmniaFX.getScale;

public class ActionWindowController {

    private final GUIController gui;
    private final String windowTitle;
    private Dimension2D windowSize;

    private Node windowPanel;
    private Pane titledPane;
    private Label progressLabel;
    private ProgressIndicator progressIndicator;

    public boolean isVisible = false;
    private String currentActionGerund;
    private boolean inCompleteState;
    private int completeStateTimer;
    private int completeStateCooldown = 100;
    private double targetOffsetX;
    private double targetOffsetY;
    private Actionable actionTarget;
    private Renderable actor;

    public ActionWindowController(GUIController gui) {
        this.gui = gui;
        this.windowTitle = "";
        //this.windowSize = new Dimension2D(150, 50);
    }

    public Node getWindowPanel() {
        if(this.windowPanel == null) {
            this.windowPanel = createWindowPanel();
        }
        return windowPanel;
    }

    public Node createWindowPanel() {
        this.titledPane = this.createWindowPane();
        this.progressLabel = new Label();
        this.progressIndicator = new ProgressIndicator();
        progressIndicator.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        progressIndicator.getStyleClass().add("action-progress-indicator");
        //HBox.setHgrow(mouseDevContainer, Priority.ALWAYS); //TODO:
        //HBox.setHgrow(collisionDevContainer, Priority.ALWAYS); //TODO:

        HBox horizontalLayoutContainer = new HBox();
        //VBox verticalLayoutContainer = new VBox();
        horizontalLayoutContainer.getChildren().addAll(progressIndicator, progressLabel); //progressLabel,
        //verticalLayoutContainer.getChildren().addAll(progressLabel, progressIndicator); //progressLabel,


        //verticalLayoutContainer.setPrefSize(windowSize.getWidth(), windowSize.getHeight());
        //VBox.setVgrow(horizontalLayoutContainer, Priority.ALWAYS); //TODO:
        //verticalLayoutContainer.getChildren().addAll(horizontalLayoutContainer);
        //VBox.setVgrow(verticalLayoutContainer, Priority.ALWAYS); //TODO:
        DragResizer.makeResizable(horizontalLayoutContainer, this.gui);
        this.titledPane.getChildren().add(horizontalLayoutContainer);
        //this.titledPane.setContent(verticalLayoutContainer);
        this.titledPane.setOpacity(1.0);
        return titledPane;
    }

    private Pane createWindowPane() {
        this.titledPane = new Pane();
        titledPane.getStyleClass().add("action-pane");
        //titledPane.setText(windowTitle);
        //titledPane.setCollapsible(false);
        //titledPane.setMinWidth(windowSize.getWidth());
        //titledPane.setMinHeight(windowSize.getHeight());
        //titledPane.setOpacity(GUIController.OPACITY_MAX);
        return titledPane;
    }

    public void showWindow(boolean show) {
        if (isVisible) {
            if (!show) { // clean up any previous state
                this.clearAction();
            }
            return; // trying to open already open window... exception?
        }
        this.toggleActionWindowVisible();
    }

    public void toggleActionWindowVisible() {
        System.out.println("toggleActionWindowVisible");
        this.isVisible = !this.isVisible;
        if(this.isVisible) {
            this.relocateWindow();
            gui.panelsPane.getChildren().add(gui.actionWindowPanel);
            //W.takeFocus(getActionWindow().getXXXX()); //nothing to focus rn
            //getActionWindow().setActiveThenFadeOut(); //dont fade rn
        } else {
            gui.panelsPane.getChildren().remove(gui.actionWindowPanel);
            //refocus canvas when actionWindow is closed
            gui.gameState.getManager().getGameController().gameCanvas.requestFocus();
        }
    }

    private void relocateWindow() {
        Renderable m = this.actor;

        // reimplement with Renderable gets
        gui.actionWindowPanel.relocate(
                ((m.getX() + m.getXMap()) * getScale()) - (m.getWidth()/3.0) * getScale(), //- this.windowPanel.getLayoutBounds().getWidth() / 2.0,
                (m.getY() + m.getYMap())* getScale() - this.windowPanel.getLayoutBounds().getHeight() * 1.5
        );
    }

    public void startAction(String actionGerund, double initialProgress, Renderable actor/*Actionable target*/) {
        this.currentActionGerund = actionGerund;
        this.progressLabel.setText(currentActionGerund
                /*
                Character.toUpperCase(currentActionVerb.charAt(0))
                + currentActionVerb.substring(1, currentActionVerb.length() - 1)
                + "..."*/
        );
        //this.actionTarget = target; //TODO:Use actionTarget if window is showing over it
        this.actor = actor;
        this.progressIndicator.getStyleClass().remove("cancelled");//.setStyle("inherit");
        this.progressIndicator.setProgress(initialProgress);
        this.showWindow(true);
    }

    public void updateAction(double progress) {
        this.progressIndicator.setProgress(progress);
    }

    public void completeAction() {
        this.progressLabel.setText(
            Character.toUpperCase(currentActionGerund.charAt(0))
            + currentActionGerund.substring(1)
            + " " + "(Complete!)"
        );
        this.inCompleteState = true;
        this.completeStateTimer++;
    }
    public void cancelAction() {
        this.progressLabel.setText(
                Character.toUpperCase(currentActionGerund.charAt(0))
                + currentActionGerund.substring(1)
                + " " + "(Cancelled!)"
        );
        //this.progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        this.progressIndicator.getStyleClass().add("cancelled"); //setStyle("-fx-progress-color: red");

        this.inCompleteState = true;
        this.completeStateTimer++;
    }

    private void clearCompleteState() {
        this.completeStateTimer = 0;
        this.clearAction();
        this.inCompleteState = false;
        this.toggleActionWindowVisible();
    }

    public void clearAction() {
        this.currentActionGerund = "";
        this.progressLabel.setText("");
        this.progressIndicator.setProgress(0);
    }

    public void update() {
        if (this.inCompleteState) this.updateCompleteState();
        if (this.actor != null && this.isVisible) {
            this.relocateWindow();
        }

    }

    private void updateCompleteState() {
        if (this.completeStateTimer > 0) {
            this.completeStateTimer++;
        }
        if (this.completeStateTimer >= this.completeStateCooldown) {
            this.clearCompleteState();
        }
    }

    public void teardown() {

    }
}
