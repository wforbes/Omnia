package net.wforbes.omnia.overworld.entity.pathfind;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import net.wforbes.omnia.overworld.entity.mob.Mob;

import java.awt.geom.Line2D;

public abstract class PathfindController {
    protected final Mob owner;
    //private Path path;
    protected Line2D pathLine;
    public boolean pathing;
    protected int collisionCount = 0;

    public PathfindController(Mob owner) {
        this.owner = owner;
    }
    public boolean isPathing() {
        return this.pathing;
    }
    public void setIsPathing(boolean is) {
        this.pathing = is;
    }
    public void cancelPathing() {
        this.pathing = false;
        this.pathLine = null;
    }
    public abstract void moveToClick(MouseEvent event);
    public abstract void moveToPoint(Point2D point);

    public abstract double[] getNextMove(double currX, double currY);
    public abstract double[] getLastNextMove();

}
