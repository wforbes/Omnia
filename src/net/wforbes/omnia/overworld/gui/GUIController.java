package net.wforbes.omnia.overworld.gui;

import javafx.event.EventTarget;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameState.OverworldState;

public class GUIController {
    public OverworldState gameState;

    private boolean guiHasFocus;
    private DevWindowController devWindowController;

    public GUIController(OverworldState state) {
        //this.gameController = state.getManager().gameController;
        this.gameState = state;
        this.guiHasFocus = false;
        final Pane panelsPane = new Pane();
        panelsPane.setStyle("-fx-background-color: rgba(0,100,100,0.5);-fx-background-radius:10;");
        panelsPane.setPrefSize(0.1,0.1);

        this.devWindowController = new DevWindowController(this);
        Node devWindowPanel = devWindowController.getWindowPanel();
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

    private static final class DragContext {
        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }

    //https://docs.oracle.com/javase/8/javafx/events-tutorial/draggablepanelsexamplejava.htm
    public Node makeDraggableByTitleRegion(final Node node) {
        final DragContext dragContext = new DragContext();
        final Group wrapGroup = new Group(node);
        wrapGroup.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            System.out.println("UI got a mouse press..");
            if (targetIsTitleRegion(event.getTarget())) {
                System.out.println("UI title was pressed");
                //if (dragModeActiveProperty.get()) {//TODO: add toggle feature for locking windows
                // remember initial mouse cursor coordinates
                // and node position
                dragContext.mouseAnchorX = event.getX();
                dragContext.mouseAnchorY = event.getY();
                dragContext.initialTranslateX =
                        node.getTranslateX();
                dragContext.initialTranslateY =
                        node.getTranslateY();
                //}
            }
        });

        wrapGroup.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
            System.out.println("UI got a mouse drag..");
            if (targetIsTitleRegion(event.getTarget())) {
                System.out.println("UI title is being dragged");
                //if (dragModeActiveProperty.get()) {//TODO: add toggle feature for locking windows
                // shift node from its initial position by delta
                // calculated from mouse cursor movement
                node.setTranslateX(
                        dragContext.initialTranslateX
                                + event.getX()
                                - dragContext.mouseAnchorX);
                node.setTranslateY(
                        dragContext.initialTranslateY
                                + event.getY()
                                - dragContext.mouseAnchorY);
                //}
            }
        });
        return wrapGroup;
    }

    //https://stackoverflow.com/questions/41252958/how-to-detect-when-javafx-mouse-event-occurs-in-the-label-are-of-a-titledpane
    //TODO: find better implementation that doesnt rely on literal class name
    private boolean targetIsTitleRegion(EventTarget target) {
        String titleClass = "class javafx.scene.control.skin.TitledPaneSkin$TitleRegion";
        return target.getClass().toString().equals(titleClass) || // anywhere on title region except title Text
                (target instanceof Node && ((Node) target).getParent().getClass().toString().equals(titleClass));// title Text
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
