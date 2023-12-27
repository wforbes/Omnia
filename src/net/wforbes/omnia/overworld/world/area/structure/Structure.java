package net.wforbes.omnia.overworld.world.area.structure;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameFX.rendering.Renderable;
import net.wforbes.omnia.gameState.OverworldState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static net.wforbes.omnia.gameFX.OmniaFX.getScale;

public class Structure implements Renderable {

    private final OverworldState gameState;
    private final StructureType.TYPES type;
    private final int x;
    private final int y;
    private final StructureDoor structureDoor;
    private Image spriteImg;
    private int width;
    private int height;
    private float xmap;
    private float ymap;
    private ArrayList<CollisionShape> collisionSets;
    private CollisionShape collisionShape;
    private Polygon collisionPolygon;

    private List<Point2D> polyPoints;
    private ArrayList<Line> shapeLines;

    private int doorX = 33;
    private int doorY = 76;

    private int aboveDoorX = 33;
    private int aboveDoorY = 64;

    public class CollisionShape {
        public List<Point2D> polyPoints = new ArrayList<>();

        public CollisionShape() {}

        public void setPolyPoints(List<Point2D> points) {
            this.polyPoints = points;
        }
        public List<Point2D> getPolyPoints() {
            return this.polyPoints;
        }
    }

    public Structure(OverworldState gameState, StructureType.TYPES type, int x, int y) {
        this.gameState = gameState;
        this.type = type;
        this.x = x;
        this.y = y;
        this.loadSprite("/overworld/structures/buildings/house_wide1.png");
        this.structureDoor = new StructureDoor(
            this,
            type,
            new Point2D(doorX+x, doorY+y),
            new Point2D(aboveDoorX+x, aboveDoorY+y)
        );
    }

    public OverworldState getGameState() {
        return this.gameState;
    }

    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public double getHeight() {
        return this.height;
    }

    public double getX() {
        return this.x - width/2.0;
    }

    public double getXActual() {
        return this.x;
    }
    public double getY() {
        return this.y - height/2.0;
    }
    public double getYActual() {
        return this.y;
    }
    @Override
    public double getXMap() {
        return this.xmap;
    }

    @Override
    public double getYMap() {
        return this.ymap;
    }

    @Override
    public double getBaseY() {
        return 0;
    }

    @Override
    public int getCollisionXOffset() {
        return 0;
    }

    @Override
    public double getRenderX() {
        return 0;
    }

    @Override
    public double getRenderY() {
        return 0;
    }

    @Override
    public double getRenderXMap() {
        return 0;
    }

    @Override
    public double getRenderYMap() {
        return 0;
    }

    @Override
    public double getRenderWidth() {
        return 0;
    }

    @Override
    public double getRenderHeight() {
        return 0;
    }

    @Override
    public int getHealthbarXOffset() {
        return 0;//artifact of Renderable
    }

    @Override
    public void setHealthbarXOffset(int offset) {
        //artifact of Renderable
    }


    public Polygon getCollisionPolygon() {
        return this.collisionPolygon;
    }

    public List<Line> getShapeLines() {
        return this.shapeLines;
    }

    @Override
    public void update() {
        this.refreshShapeMapping();
    }

    public void init() {
        this.initCollisionShape();
    }

    private void initCollisionShape() {
        this.collisionShape = new CollisionShape();
        this.refreshShapeMapping();
    }

    private void refreshShapeSegment(double x1, double y1, double x2, double y2) {
        Line line = new Line();
        line.setStartX(x1+this.getXActual());
        line.setStartY(y1+this.getYActual());
        polyPoints.add(new Point2D(x1+this.getXActual(), y1+this.getYActual()));
        line.setEndX(x2+this.getXActual());
        line.setEndY(y2+this.getYActual());
        polyPoints.add(new Point2D(x2+this.getXActual(), y2+this.getYActual()));
        shapeLines.add(line);
    }

    private void refreshShapeMapping() {
        this.polyPoints = new ArrayList<>();
        this.collisionShape.getPolyPoints().clear();
        this.shapeLines = new ArrayList<>();
        double xmod = this.getXActual();
        double ymod = this.getYActual();
        this.refreshShapeSegment(
            0, 0,
            79, 0
        );
        this.refreshShapeSegment(
                79, 0,
                79, 6
        );
        this.refreshShapeSegment(
                79, 6,
                121, 6
        );
        this.refreshShapeSegment(
                121, 6,
                121, 59
        );
        this.refreshShapeSegment(
                121, 59,
                118, 59
        );
        this.refreshShapeSegment(
                118, 59,
                118, 73
        );
        this.refreshShapeSegment(
                118, 73,
                76, 73
        );
        this.refreshShapeSegment(
                76, 73,
                76, 89
        );
        this.refreshShapeSegment(
                76, 89,
                71, 89
        );
        this.refreshShapeSegment(
                71, 88,
                52, 88

        );
        this.refreshShapeSegment(
                52, 88,
                52, 89
        );
        this.refreshShapeSegment(
                52, 89,
                47, 89
        );
        this.refreshShapeSegment(
                47, 89,
                47, 75
        );
        //entry point begin
        this.refreshShapeSegment(
                47, 75,
                32, 75
        );
        //entry point end
        this.refreshShapeSegment(
                32, 75,
                32, 89
        );
        this.refreshShapeSegment(
                32, 89,
                27, 89
        );
        this.refreshShapeSegment(
                27, 89,
                27, 88
        );
        this.refreshShapeSegment(
                27, 88,
                9, 88
        );
        this.refreshShapeSegment(
                27, 88,
                9, 88
        );
        this.refreshShapeSegment(
                9, 88,
                9, 89
        );
        this.refreshShapeSegment(
                9, 89,
                4, 89
        );
        this.refreshShapeSegment(
                4, 89,
                4, 63
        );
        this.refreshShapeSegment(
                4, 63,
                0, 63
        );
        this.refreshShapeSegment(
                0, 63,
                0, 0
        );

        Polygon p = new Polygon();
        p.getPoints().addAll(0+xmod, 0+ymod,
            79+xmod, 0+ymod,
            79+xmod, 6+ymod,
            121+xmod, 6+ymod,
            121+xmod, 59+ymod,
            118+xmod, 59+ymod,
            118+xmod, 73+ymod,
            76+xmod, 73+ymod,
            76+xmod, 89+ymod,
            71+xmod, 89+ymod,
            71+xmod, 88+ymod,
            52+xmod, 88+ymod,
            52+xmod, 89+ymod,
            47+xmod, 89+ymod,
            47+xmod, 75+ymod,
            32+xmod, 75+ymod,
            32+xmod, 89+ymod,
            27+xmod, 89+ymod,
            27+xmod, 88+ymod,
            9+xmod, 88+ymod,
            9+xmod, 89+ymod,
            4+xmod, 89+ymod,
            4+xmod, 63+ymod,
            0+xmod, 63+ymod
        );
        this.collisionPolygon = p;
    }

    public CollisionShape getCollisionShape() {
        return this.collisionShape;
    }

    public void loadSprite(String path) {
        this.spriteImg = new Image(
            Objects.requireNonNull(
                getClass().getResourceAsStream(path)
            )
        );
        this.width = (int) this.spriteImg.getWidth();
        this.height = (int) this.spriteImg.getHeight();
        System.out.println("loaded structure sprite: " + "("+width+"x"+height+")");
    }
    public StructureDoor getStructureDoor() {
        return this.structureDoor;
    }
    public Image getDoorSprite() {
        if (this.structureDoor == null) return null;
        return this.structureDoor.getDoorSprite();
    }

    public Image getAboveDoorSprite() {
        if (this.structureDoor == null) return null;
        return this.structureDoor.getAboveDoorSprite();
    }

    public void render(GraphicsContext gc) {
        this.refreshMapPosition();
        this.renderSprite(gc);
        this.renderCollisionShape(gc);
    }

    protected void refreshMapPosition() {
        xmap = (float)this.gameState.world.area.getTileMap().getX();
        ymap = (float)this.gameState.world.area.getTileMap().getY();
    }

    protected boolean offScreen() {
        return x + xmap + width < 0 ||
                x + xmap - width/2.5 > OmniaFX.getWidth() ||
                y + ymap + height < 0 ||
                y + ymap - height/2.5 > OmniaFX.getHeight();
    }

    public void renderSprite(GraphicsContext gc) {
        gc.drawImage(
            this.spriteImg,
            (this.getXActual() + xmap) * getScale(),
            (this.getYActual() + ymap) * getScale(),
            width * getScale(),
            height * getScale()
        );
    }

    public void renderCollisionShape(GraphicsContext gc) {
        if (this.offScreen()) return;
        gc.setStroke(Color.POWDERBLUE);
        List<Point2D> points = this.getCollisionShape().getPolyPoints();
        if (points.isEmpty() || !gameState.collisionGeometryVisible()) return;
        //System.out.println("rendering col shape");

        for (int i = 0; i < points.size(); i++) {
            if (i == points.size()-1) {
                gc.strokeLine(
                        (points.get(i).getX()+xmap) * getScale(),
                        (points.get(i).getY()+ymap) * getScale(),
                        (points.get(0).getX()+xmap) * getScale(),
                        (points.get(0).getY()+ymap) * getScale()
                );
                continue;
            }
            gc.strokeLine(
                    (points.get(i).getX()+xmap) * getScale(),
                    (points.get(i).getY()+ymap) * getScale(),
                    (points.get(i+1).getX()+xmap) * getScale(),
                    (points.get(i+1).getY()+ymap) * getScale()
            );
        }
    }
}
