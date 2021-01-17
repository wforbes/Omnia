package net.wforbes.omnia.overworld.gui;

import javafx.event.EventTarget;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class WindowDragger {

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
            //System.out.println("UI got a mouse drag..");
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
}
