package net.wforbes.omnia.overworld.gui;

import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class DevWindowController {
    private GUIController gui;

    private Dimension2D windowSize;
    private String windowTitle;
    private Node windowPanel;
    private TitledPane titledPane;

    private Label scratchLabel;
    private Label clickPosLabel;
    private Label clickPosVal;
    private Label cursorPosLabel;
    private Label cursorPosVal;
    private Label playerMapPosLabel;
    private Label playerMapPosVal;
    private Label playerScreenPosLabel;
    private Label playerScreenPosVal;
    private Label playerColWithLabel;
    private Label playerColWithVal;
    private Button collisionGeometryBtn;
    private Label playerCollidedVal;
    private Label playerXClearDataVal;
    private Label playerYClearDataVal;

    public DevWindowController(GUIController gui) {
        this.gui = gui;
        this.windowTitle = "Omnia Developer Tools";
        this.windowSize = new Dimension2D(250, 150);
    }

    public Node getWindowPanel() {
        if(this.windowPanel == null) {
            this.windowPanel = createWindowPanel();
        }
        return windowPanel;
    }

    public double getWindowHeight() {
        return this.windowSize.getHeight();
    }

    public void handleBorderPaneClick(MouseEvent event) {
        this.setClickPosVal(event);
    }
    public void handleBorderPaneMouseMove(MouseEvent event) {
        this.setMovePosVal(event);
    }

    private void setClickPosVal(MouseEvent e) {
        this.clickPosVal.setText("(" + e.getX() + ", " + e.getY() + ")");
    }
    private void setMovePosVal(MouseEvent e) { this.cursorPosVal.setText("(" + e.getX() + ", " + e.getY() + ")"); }
    public void setPlayerMapPos(double x, double y) {
        this.playerMapPosVal.setText("("+x+", "+y+")");
    }
    public void setPlayerScreenPos(double x, double y) {
        this.playerScreenPosVal.setText("("+x+", "+y+")");
    }
    public void setPlayerColWith(String s) {
        this.playerColWithVal.setText(s);
    }

    private void toggleCollisionGeometry(MouseEvent event) {
        this.gui.gameState.toggleCollisionGeometry();
    }

    public void setPlayerCollided(boolean collided) {
        if(collided) {
            this.playerCollidedVal.setText("PLAYER COLLIDED");
        } else {
            this.playerCollidedVal.setText("");
        }
    }

    public void setPlayerXClearData(double thisx, double x, double xa) {
        this.playerXClearDataVal.setText("x: " + thisx + " + " + x + " + " + xa + " = " + (thisx+x+xa));
    }
    public void setPlayerYClearData(double thisy, double y, double ya) {
        this.playerYClearDataVal.setText("x: " + thisy + " + " + y + " + " + ya + " = " + (thisy+y+ya));
    }


    public Node createWindowPanel() {
        this.titledPane = this.createTitledPane();
        scratchLabel = new Label();
        VBox mouseDevContainer = this.createMouseDevContainer();
        VBox collisionDevContainer = this.createCollisionDevContainer();
        HBox.setHgrow(mouseDevContainer, Priority.ALWAYS); //TODO:
        HBox.setHgrow(collisionDevContainer, Priority.ALWAYS); //TODO:

        HBox horizontalLayoutContainer = new HBox();

        horizontalLayoutContainer.getChildren().addAll(mouseDevContainer, collisionDevContainer);

        VBox verticalLayoutContainer = new VBox();
        //verticalLayoutContainer.setPrefSize(windowSize.getWidth(), windowSize.getHeight());
        VBox.setVgrow(horizontalLayoutContainer, Priority.ALWAYS); //TODO:
        verticalLayoutContainer.getChildren().addAll(horizontalLayoutContainer);
        VBox.setVgrow(verticalLayoutContainer, Priority.ALWAYS); //TODO:
        DragResizer.makeResizable(verticalLayoutContainer);
        this.titledPane.setContent(verticalLayoutContainer);
        GUIController.configureBorder(titledPane);
        return titledPane;
    }

    private TitledPane createTitledPane() {
        TitledPane titledPane = new TitledPane();
        titledPane.setText(windowTitle);
        titledPane.setCollapsible(false);
        //titledPane.setMinWidth(windowSize.getWidth());
        //titledPane.setMinHeight(windowSize.getHeight());
        titledPane.setOpacity(0.75);
        return titledPane;
    }

    private VBox createMouseDevContainer() {
        VBox mouseDevContainer = new VBox();
        //mouseDevContainer.setPrefSize(windowSize.getWidth()/2, 250);

        HBox clickPosContainer = new HBox();
        clickPosLabel = new Label("Click Pos: ");
        clickPosVal = new Label("--");
        clickPosContainer.getChildren().addAll(clickPosLabel, clickPosVal);
        VBox mouseClicksContainer = new VBox();
        mouseClicksContainer.getChildren().addAll(clickPosContainer);

        HBox cursorPosContainer = new HBox();
        cursorPosLabel = new Label("Cursor Pos: ");
        cursorPosVal = new Label("--");
        cursorPosContainer.getChildren().addAll(cursorPosLabel, cursorPosVal);
        VBox mouseMovesContainer = new VBox();
        mouseMovesContainer.getChildren().addAll(cursorPosContainer);
        mouseDevContainer.getChildren().addAll(clickPosContainer, mouseMovesContainer);
        return mouseDevContainer;
    }

    private VBox createCollisionDevContainer() {
        VBox collisionDevContainer = new VBox();
        //collisionDevContainer.setPrefSize(windowSize.getWidth()/2, 250);

        HBox collisionGeometryContainer = new HBox();
        collisionGeometryBtn = new Button("Collision Geometry");
        collisionGeometryContainer.getChildren().add(collisionGeometryBtn);
        collisionGeometryBtn.setOnMouseClicked(this::toggleCollisionGeometry);

        HBox playerMapPosContainer = new HBox();
        playerMapPosLabel = new Label("Player Map X/Y Pos: ");
        playerMapPosVal = new Label("--");
        playerMapPosContainer.getChildren().addAll(playerMapPosLabel, playerMapPosVal);
        HBox playerScreenPosContainer = new HBox();
        playerScreenPosLabel = new Label("Player Screen X/Y Pos: ");
        playerScreenPosVal = new Label("--");
        playerScreenPosContainer.getChildren().addAll(playerScreenPosLabel, playerScreenPosVal);
        VBox entityPosContainer = new VBox();
        entityPosContainer.getChildren().addAll(playerMapPosContainer, playerScreenPosContainer);

        HBox playerCollisionContainer = new HBox();
        playerColWithLabel = new Label("Player colliding with: ");
        playerColWithVal = new Label("--");
        playerCollisionContainer.getChildren().addAll(playerColWithLabel, playerColWithVal);



        VBox playerCollidedContainer = new VBox();
        playerCollidedVal = new Label();
        playerXClearDataVal = new Label();
        playerYClearDataVal = new Label();
        playerCollidedContainer.getChildren().addAll(playerCollidedVal, playerXClearDataVal, playerYClearDataVal);


        VBox entityCollisionContainer = new VBox();
        entityCollisionContainer.getChildren().addAll(
                playerCollisionContainer, collisionGeometryBtn, playerCollidedContainer
        );

        collisionDevContainer.getChildren().addAll(entityPosContainer, entityCollisionContainer);
        return collisionDevContainer;
    }
}
