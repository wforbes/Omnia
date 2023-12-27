package net.wforbes.omnia.overworld.world.area.structure;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Objects;

import static net.wforbes.omnia.gameFX.OmniaFX.getScale;

public class StructureDoor {
    private final Structure structure;
    private final StructureType.TYPES structureType;
    private final Point2D doorXY;
    private final Point2D aboveDoorXY;
    private Image doorSpriteImg;
    private Image openDoorSpriteImg;
    private int doorWidth;
    private int doorHeight;
    private Image aboveDoorSpriteImg;
    private int aboveDoorWidth;
    private int aboveDoorHeight;
    private float xmap;
    private float ymap;
    private boolean open = false;
    private ArrayList<Line> doorShapeLines;
    private Line aboveDoorLine;

    public StructureDoor(Structure owner, StructureType.TYPES type, Point2D doorXY, Point2D aboveDoorXY) {
        this.structure = owner;
        this.structureType = type;
        this.doorXY = doorXY;
        this.aboveDoorXY = aboveDoorXY;
        this.loadDoorSprite("/overworld/structures/buildings/house_wide1_door.png");
        this.loadOpenDoorSprite("/overworld/structures/buildings/house_wide1_door_open.png");
        this.loadAboveDoorSprite("/overworld/structures/buildings/house_wide1_aboveDoor.png");
    }

    private Image loadSprite(String path) {
        return new Image(
            Objects.requireNonNull(
                getClass().getResourceAsStream(path)
            )
        );
    }

    private void loadDoorSprite(String path) {
        this.doorSpriteImg = this.loadSprite(path);
        this.doorWidth = (int) this.doorSpriteImg.getWidth();
        this.doorHeight = (int) this.doorSpriteImg.getHeight();
    }

    private void loadOpenDoorSprite(String path) {
        this.openDoorSpriteImg = this.loadSprite(path);
    }

    private void loadAboveDoorSprite(String path) {
        this.aboveDoorSpriteImg = this.loadSprite(path);
        this.aboveDoorWidth = (int) this.doorSpriteImg.getWidth();
        this.aboveDoorHeight = (int) this.doorSpriteImg.getHeight();
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void init() {
        this.refreshShapeMapping();
        this.refreshAboveDoorLine();
    }

    public void update() {
        this.refreshShapeMapping();
        this.refreshAboveDoorLine();
    }

    public ArrayList<Line> getDoorShapeLines() {
        return this.doorShapeLines;
    }

    public Line getAboveDoorLine() {
        return this.aboveDoorLine;
    }

    private void refreshAboveDoorLine() {
        this.aboveDoorLine = new Line();
        this.aboveDoorLine.setStartX(aboveDoorXY.getX()+aboveDoorWidth);
        this.aboveDoorLine.setStartY(aboveDoorXY.getY());
        this.aboveDoorLine.setEndX(aboveDoorXY.getX());
        this.aboveDoorLine.setEndY(aboveDoorXY.getY()+aboveDoorHeight);
    }

    private void refreshShapeMapping() {
        this.doorShapeLines = new ArrayList<>();
        this.refreshShapeSegment(
            doorXY.getX(), doorXY.getY(),
            doorXY.getX()+doorWidth, doorXY.getY()
        );
        this.refreshShapeSegment(
            doorXY.getX()+doorWidth, doorXY.getY(),
            doorXY.getX()+doorWidth, doorXY.getY()+doorHeight
        );
        this.refreshShapeSegment(
            doorXY.getX()+doorWidth, doorXY.getY(),
            doorXY.getX(), doorXY.getY()+doorHeight
        );
        this.refreshShapeSegment(
            doorXY.getX(), doorXY.getY()+doorHeight,
            doorXY.getX(), doorXY.getY()
        );
    }
    private void refreshShapeSegment(double x1, double y1, double x2, double y2) {
        Line line = new Line();
        line.setStartX(x1);
        line.setStartY(y1);
        //polyPoints.add(new Point2D(x1+this.doorXY.getX(), y1+this.doorXY.getY()));
        line.setEndX(x2);
        line.setEndY(y2);
        //polyPoints.add(new Point2D(x2+this.getXActual(), y2+this.getYActual()));
        doorShapeLines.add(line);
    }

    public Image getDoorSprite() {
        return this.doorSpriteImg;
    }

    public Image getAboveDoorSprite() {
        return this.aboveDoorSpriteImg;
    }

    public void renderDoor(GraphicsContext gc) {
        this.refreshMapPosition();
        this.myRenderDoor(gc);
    }
    public void renderAboveDoor(GraphicsContext gc) {
        this.refreshMapPosition();
        this.myRenderAboveDoor(gc);
    }

    protected void refreshMapPosition() {
        xmap = (float)this.structure.getGameState().world.area.getTileMap().getX();
        ymap = (float)this.structure.getGameState().world.area.getTileMap().getY();
    }
    public void myRenderDoor(GraphicsContext gc) {
        gc.drawImage(
            open ? this.openDoorSpriteImg : this.doorSpriteImg,
            (this.doorXY.getX() + xmap) * getScale(),
            (this.doorXY.getY() + ymap) * getScale(),
            doorWidth * getScale(),
            doorHeight * getScale()
        );
    }

    public void myRenderAboveDoor(GraphicsContext gc) {
        gc.drawImage(
            this.aboveDoorSpriteImg,
            (this.aboveDoorXY.getX() + xmap) * getScale(),
            (this.aboveDoorXY.getY() + ymap) * getScale(),
            aboveDoorWidth * getScale(),
            aboveDoorHeight * getScale()
        );
    }
}
