package net.wforbes.omnia.topDown.gui;

import javafx.animation.FadeTransition;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class DevWindowController {
    private GUIController gui;

    private Label playerPosLbl;
    private Label playerPosVal;
    private HBox playerPosCont;
    private Label playerMovePosLbl;
    private Label playerMovePosVal;
    private HBox playerMovePosCont;

    private Label docPosLbl;
    private Label docPosVal;
    private HBox docPosCont;
    private Label docMovePosLbl;
    private Label docMovePosVal;
    private HBox docMovePosCont;

    private Label playerColWithLbl;
    private Label playerColWithVal;
    private HBox playerColWithCont;

    private VBox colHContainer;

    private Label scratchVal;
    private VBox scratchContainer;

    private VBox posHContainer;
    private VBox moveHContainer;

    private HBox positionContainer;

    //TODO: Window inheritable
    private String windowTitle;
    private Node windowPanel;
    private TitledPane titledPane;

    private VBox layoutVerticalContainer;
    private VBox mobCollisionContainer;

    public DevWindowController(GUIController gui) {
        this.gui = gui;
        this.windowTitle = "Chat Window";
    }

    public void setPlayerPos(int x, int y) {
        this.playerPosVal.setText(x + " " + y);
    }
    public void setPlayerMovePos(int xa, int ya) {
        this.playerMovePosVal.setText(xa + " " + ya);
    }

    public void setDocPos(int x, int y) {
        this.docPosVal.setText(x + " " + y);
    }

    public void setDocMovePos(int xa, int ya) {
        this.docMovePosVal.setText(xa + " " + ya);
    }

    public void setPlayerColWith(String s) {
        this.playerColWithVal.setText(s);
    }

    public void setScratch(String s) {
        this.scratchVal.setText(s);
    }

    //TODO: Window inheritable
    public Node getWindowPanel() {
        if(windowPanel == null) {
            this.windowPanel =
                    gui.makeDraggableByTitleRegion(
                            createWindowPanel()
                    );
        }
        return windowPanel;
    }

    //TODO: Window inheritable
    private void setWindowFadeTransitions() {
        FadeTransition titledPaneFadeIn = new FadeTransition(Duration.millis(500), titledPane);
        titledPaneFadeIn.setFromValue(0.75);
        titledPaneFadeIn.setToValue(1.0);

        FadeTransition titledPaneFadeOut = new FadeTransition(Duration.millis(500), titledPane);
        titledPaneFadeOut.setFromValue(1.0);
        titledPaneFadeOut.setToValue(0.75);

        titledPane.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            titledPaneFadeIn.play();
        });
        titledPane.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            titledPaneFadeOut.play();
        });
    }

    private Node createWindowPanel() {
        Dimension2D windowSize = new Dimension2D(500, 200);
        this.titledPane = new TitledPane();
        this.titledPane.setText("Dev Window");
        this.titledPane.setCollapsible(false);
        this.titledPane.setPrefWidth(windowSize.getWidth());
        this.titledPane.setOpacity(0.75);

        playerPosLbl = new Label("Player Pos: ");
        playerPosVal = new Label();
        playerPosCont = new HBox();
        playerPosCont.setPrefSize(windowSize.getWidth()/2, 20);
        playerPosCont.getChildren().addAll(playerPosLbl, playerPosVal);

        docPosLbl = new Label("Doc Pos: ");
        docPosVal = new Label();
        docPosCont = new HBox();
        docPosCont.setPrefSize(windowSize.getWidth()/2, 20);
        docPosCont.getChildren().addAll(docPosLbl, docPosVal);

        posHContainer = new VBox();
        posHContainer.getChildren().addAll(playerPosCont, docPosCont);


        playerMovePosLbl = new Label("Player MovePos: ");
        playerMovePosVal = new Label();
        playerMovePosCont = new HBox();
        playerMovePosCont.setPrefSize(windowSize.getWidth()/2, 20);
        playerMovePosCont.getChildren().addAll(playerMovePosLbl, playerMovePosVal);

        docMovePosLbl = new Label("Doc MovePos: ");
        docMovePosVal = new Label();
        docMovePosCont = new HBox();
        docMovePosCont.setPrefSize(windowSize.getWidth()/2, 20);
        docMovePosCont.getChildren().addAll(docMovePosLbl, docMovePosVal);

        moveHContainer = new VBox();
        moveHContainer.getChildren().addAll(playerMovePosCont, docMovePosCont);

        playerColWithLbl = new Label("Player Colliding with: ");
        playerColWithVal = new Label();
        playerColWithCont = new HBox();
        playerColWithCont.setPrefSize(windowSize.getWidth()/2, 20);
        playerColWithCont.getChildren().addAll(playerColWithLbl, playerColWithVal);

        colHContainer = new VBox();
        colHContainer.getChildren().addAll(playerColWithCont);

        positionContainer = new HBox();
        positionContainer.getChildren().addAll(posHContainer, moveHContainer);

        mobCollisionContainer = new VBox();
        mobCollisionContainer.setPrefSize(windowSize.getWidth(), windowSize.getHeight());
        mobCollisionContainer.getChildren().addAll(positionContainer);

        scratchVal = new Label();
        scratchContainer = new VBox();
        scratchContainer.setPrefSize(windowSize.getWidth(), windowSize.getHeight());
        scratchContainer.getChildren().addAll(scratchVal, colHContainer);

        layoutVerticalContainer = new VBox();
        layoutVerticalContainer.setPrefSize(windowSize.getWidth(), windowSize.getHeight());
        //chatVerticalContainer.getChildren().addAll(chatArea, chatLowerContainer);
        layoutVerticalContainer.getChildren().addAll(mobCollisionContainer, scratchContainer);

        titledPane.setContent(layoutVerticalContainer);
        GUIController.configureBorder(titledPane);
        return titledPane;
    }
}
