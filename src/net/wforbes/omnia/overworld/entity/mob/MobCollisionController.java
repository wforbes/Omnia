package net.wforbes.omnia.overworld.entity.mob;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.world.area.object.AreaObject;
import net.wforbes.omnia.overworld.world.area.structure.Structure;
import net.wforbes.omnia.overworld.world.area.structure.StructureDoor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static net.wforbes.omnia.game.Game.getScale;

/**
 * Owns the geometry used by a mob when checking structures and doors.
 */
public class MobCollisionController {
    private final Mob mob;
    private Rectangle collisionRectangle;
    private Entity collidingEntity;
    private AreaObject collidingAreaObject;
    private Structure collidingStructure;
    private StructureDoor collidingDoor;

    public MobCollisionController(Mob mob) {
        this.mob = mob;
    }

    Entity getCollidingEntity() {
        return this.collidingEntity;
    }

    AreaObject getCollidingAreaObject() {
        return this.collidingAreaObject;
    }

    boolean isOccupied(double xa, double ya) {
        //TODO: simplify this to iterate through area Renderables
        for(Entity e : mob.gameState.world.getCurrentArea().getEntities()) {
            if(!e.getName().equals(mob.name)) {
                /* AABB Collision */
                if (
                    (mob.getX()) + xa + mob.getCollisionXOffset() < e.getX()+e.getCollisionBoxWidth()+e.getCollisionXOffset() &&
                    mob.getX() + xa + mob.getCollisionBoxWidth() + mob.getCollisionXOffset() > e.getX() + e.getCollisionXOffset() &&
                    mob.getY() + ya + mob.getCollisionYOffset() < e.getY()+e.getCollisionBoxHeight()+e.getCollisionYOffset() &&
                    mob.getY() + ya + mob.getCollisionBoxHeight() + mob.getCollisionYOffset() >  e.getY() + e.getCollisionYOffset()
                ) {
                    this.collidingEntity = e;
                    return true;
                }
            }
        }
        this.collidingEntity = null;

        //TODO: convert AO collision to similar to structure line-based collision
        for (AreaObject ao : mob.gameState.world.getCurrentArea().getAreaObjects()) {
            if (!ao.isSpawned()) continue;
            double xDist = (mob.getX()+xa+mob.getCollisionBaseX() - mob.getCollisionRadius()/2.0) - (ao.getX()+ao.getCollisionBaseX());
            double yDist = (mob.getY()+ya+mob.getCollisionBaseY() - mob.getCollisionRadius()/3.0) - (ao.getY()+ao.getCollisionBaseY());
            boolean collided = Math.sqrt((xDist*xDist) + (yDist*yDist)) < ((mob.getCollisionRadius()-2)/2.0+(ao.getCollisionRadius()-3)/2.0);
            if(collided) {
                this.collidingAreaObject = ao;
                return true;
            }
        }
        this.collidingAreaObject = null;

        for (Structure s: mob.gameState.world.getCurrentArea().getStructures()) {
            boolean result = this.withinStructure(s, xa, ya);
            if (result) {
                this.collidingStructure = s;
                return true;
            }
        }
        this.collidingStructure = null;

        if (mob.isPlayer) {
            boolean onAnyDoor = false;
            for (StructureDoor sd : mob.gameState.world.getCurrentArea().getStructureDoors()) {
                boolean result = this.withinDoor(sd, xa, ya);
                sd.setOpen(result);
                if (result) {
                    onAnyDoor = true;
                    this.collidingDoor = sd;
                    if (this.collidingWithAboveDoor(sd, xa, ya)) {
                        //TODO: move to this structure's area
                    }
                }
            }
            if (!onAnyDoor) this.collidingDoor = null;
        }

        return false;
    }

    void initCollisionShape() {
        this.collisionRectangle = new Rectangle(
            mob.getCollisionBoxWidth(),
            mob.getCollisionBoxHeight()
        );
    }

    boolean withinStructure(Structure s, double xa, double ya) {
        Rectangle rect = this.collisionRectangle;
        rect.setX(rect.getX() + xa); // predict next move
        rect.setY(rect.getY() + ya);
        ArrayList<Line> mobLines = getMobLines(rect);
        for (Line sl : s.getShapeLines()) {
            for (Line ml: mobLines) {
                if (sl.getBoundsInParent().intersects(ml.getBoundsInParent())) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean withinDoor(StructureDoor sd, double xa, double ya) {
        Rectangle rect = this.collisionRectangle;
        rect.setX(rect.getX() + xa); // predict next move
        rect.setY(rect.getY() + ya);
        ArrayList<Line> mobLines = getMobLines(rect);
        if (sd.getDoorShapeLines() == null) {
            //System.out.println("Door shape lines is NULL, u dun goofed");
            return false;
        }
        for (Line sl : sd.getDoorShapeLines()) {
            for (Line ml: mobLines) {
                if (sl.getBoundsInParent().intersects(ml.getBoundsInParent())) {
                    //System.out.println("colliding with door");
                    return true;
                }
            }
        }
        return false;
    }

    boolean collidingWithAboveDoor(StructureDoor sd, double xa, double ya) {
        Rectangle rect = this.collisionRectangle;
        rect.setX(rect.getX() + xa); // predict next move
        rect.setY(rect.getY() + ya);
        ArrayList<Line> mobLines = getMobLines(rect);
        if (sd.getAboveDoorLine() == null) {
            //System.out.println("Above Door Live is NULL, u dun goofed");
            return false;
        }
        for (Line ml: mobLines) {
            if (sd.getAboveDoorLine().getBoundsInParent().intersects(ml.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }

    private static @NotNull ArrayList<Line> getMobLines(Rectangle rect) {
        ArrayList<Line> mobLines = new ArrayList<>();

        Line line1 = new Line();
        line1.setStartX(rect.getX());
        line1.setEndX(rect.getX()+ rect.getWidth());
        line1.setStartY(rect.getY());
        line1.setEndY(rect.getY());
        mobLines.add(line1);

        Line line2 = new Line();
        line2.setStartX(rect.getX()+ rect.getWidth());
        line2.setEndX(rect.getX()+ rect.getWidth());
        line2.setStartY(rect.getY());
        line2.setEndY(rect.getY()+ rect.getHeight());
        mobLines.add(line2);

        Line line3 = new Line();
        line3.setStartX(rect.getX()+ rect.getWidth());
        line3.setEndX(rect.getX());
        line3.setStartY(rect.getY());
        line3.setEndY(rect.getY()+ rect.getHeight());
        mobLines.add(line3);

        Line line4 = new Line();
        line4.setStartX(rect.getX());
        line4.setEndX(rect.getX());
        line4.setStartY(rect.getY()+ rect.getHeight());
        line4.setEndY(rect.getY());
        mobLines.add(line4);

        return mobLines;
    }

    void refreshCollisionRectanglePosition() {
        this.collisionRectangle.setX(mob.getX() + mob.getCollisionXOffset());
        this.collisionRectangle.setY(mob.getY() + mob.getCollisionYOffset());
    }

    void renderCollisionGeometry(GraphicsContext gc) {
        gc.setStroke(javafx.scene.paint.Color.RED);
        gc.strokeRect(
            (collisionRectangle.getX()+mob.getXMap())*getScale(),
            (collisionRectangle.getY()+mob.getYMap())*getScale(),
            collisionRectangle.getWidth()*getScale(),
            collisionRectangle.getHeight()*getScale()
        );
        gc.strokeRect(
            (collisionRectangle.getX()+mob.getXMap())*getScale(),
            (collisionRectangle.getY()+mob.getYMap())*getScale(),
            2,
            2
        );
    }
}
