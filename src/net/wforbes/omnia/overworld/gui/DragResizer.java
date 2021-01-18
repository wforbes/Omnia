package net.wforbes.omnia.overworld.gui;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

//https://gist.github.com/cannibalsticky/a3057d6e9c1f029d99e6bc95f9b3340e
public class DragResizer {

    private static final int RESIZE_MARGIN = 10;

    private final Region region;

    private double x;
    private double y;
    private boolean initMinHeight;
    private boolean initMinWidth;
    private boolean draggableZoneX, draggableZoneY, draggableZoneNegY, draggableZoneNegX;

    private double initialTranslateX, initialTranslateY;
    private double initialLayoutX;
    private double mouseAnchorX, mouseAnchorY;

    private boolean dragging;

    private DragResizer(Region region) {
        this.region = region;
    }

    public static void makeResizable(Region region) {
        final DragResizer resizer = new DragResizer(region);

        region.setOnMousePressed(event -> resizer.mousePressed(event));
        region.setOnMouseDragged(event -> resizer.mouseDragged(event));
        region.setOnMouseMoved(event -> resizer.mouseOver(event));
        region.setOnMouseReleased(event -> resizer.mouseReleased(event));
    }

    protected void mousePressed(MouseEvent event) {
        // ignore clicks outside of the draggable margin
        if (!isInDraggableZone(event)) {
            return;
        }

        dragging = true;

        // make sure that the minimum height is set to the current height once,
        // setting a min height that is smaller than the current height will
        // have no effect
        if (!initMinHeight) {
            region.setMinHeight(region.getHeight());
            initMinHeight = true;
        }

        y = event.getY();
        mouseAnchorY = event.getY();
        initialTranslateY = region.getTranslateY();

        if (!initMinWidth) {
            region.setMinWidth(region.getWidth());
            initMinWidth = true;
        }

        x = event.getX();
        mouseAnchorX = event.getX();
        initialTranslateX = region.getTranslateX();
        initialLayoutX = region.getLayoutX();
    }

    protected void mouseDragged(MouseEvent event) {
        if (!dragging) {
            return;
        }

        if (draggableZoneY) {
            double mousey = event.getY();

            double newHeight = region.getMinHeight() + (mousey - y);

            region.setMinHeight(newHeight);

            y = mousey;
        }

        if (draggableZoneX) {
            double mousex = event.getX();

            double newWidth = region.getMinWidth() + (mousex - x);

            region.setMinWidth(newWidth);

            x = mousex;

        }

        /* //TODO: fix negative axis resize
        if (draggableZoneNegY) {
            double mousey = event.getY();

            double newHeight = region.getMinHeight() + (y - mousey);

            region.setMinHeight(newHeight);
            region.setTranslateY(
                    initialTranslateY + mousey - mouseAnchorY
            );

            y = mousey;
        }

        if (draggableZoneNegX) {
            double mousex = event.getX();

            double newWidth = region.getMinWidth() + (x - mousex);

            region.setTranslateX(
                    initialTranslateX + mousex - mouseAnchorX
            );
            region.setMinWidth(newWidth);
            double gtx = region.getTranslateX();
            System.out.println(initialTranslateX + " " + mouseAnchorX + " " + initialLayoutX
                    + " | " + gtx + " " + mousex);
            //System.out.println(mousex - mouseAnchorX + " = " + mousex +" - " + mouseAnchorX);
            x = mousex;
        }
         */
    }

    protected void mouseOver(MouseEvent event) {
        if (isInDraggableZone(event) || dragging) {
            if (draggableZoneY) {//TODO: add negY cursor
                region.setCursor(Cursor.S_RESIZE);
            }

            if (draggableZoneX) {//TODO: add negX cursor
                region.setCursor(Cursor.E_RESIZE);
            }


        } else {
            region.setCursor(Cursor.DEFAULT);
        }
    }

    protected boolean isInDraggableZone(MouseEvent event) {
        draggableZoneY = (boolean)(event.getY() > (region.getHeight() - RESIZE_MARGIN));
        draggableZoneX = (boolean)(event.getX() > (region.getWidth() - RESIZE_MARGIN));
        draggableZoneNegY = (event.getY() < (RESIZE_MARGIN));
        draggableZoneNegX = (event.getX() < (RESIZE_MARGIN));
        return (draggableZoneY || draggableZoneX || draggableZoneNegY || draggableZoneNegX);
    }

    protected void mouseReleased(MouseEvent event) {
        dragging = false;
        region.setCursor(Cursor.DEFAULT);
    }

}
