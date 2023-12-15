package net.wforbes.omnia.overworld.entity.pathfind;

import javafx.geometry.Point2D;
import net.wforbes.omnia.overworld.entity.mob.Mob;

import javafx.scene.input.MouseEvent;

import java.awt.geom.Line2D;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static net.wforbes.omnia.gameFX.OmniaFX.getScale;

public class PathfindController {
    private final Mob owner;
    //private Path path;
    private Line2D pathLine;
    public boolean pathing;
    private int collisionCount = 0;


    public PathfindController(Mob owner) {
        this.owner = owner;
    }

    public void movePlayer(MouseEvent event) {
        //System.out.println("Currently at: "+owner.getXActual()+", "+owner.getYActual());
        //System.out.println("Moving to: " + event.getX()+", "+event.getY());
        Point2D target = this.getMouseEventMapPosition(event);
        if (
            Math.abs(target.getX() - owner.getXActual()) < owner.getWidth()
            && Math.abs(target.getY() - owner.getYActual()) < owner.getHeight()
        ) {
            System.out.println("Move to click is too close!");
            return;
        }
        /* can use Line2D..
        double slope = (target.getY() - owner.getYActual()) / (target.getX() - owner.getXActual());
        double b = owner.getYActual()-(slope * owner.getXActual());
         */
        this.pathLine = new Line2D.Double(Math.floor(owner.getXActual()), Math.floor(owner.getYActual()), target.getX(), target.getY());
        this.pathing = true;
    }

    private Point2D getMouseEventMapPosition(MouseEvent event) {
        //System.out.println("players x/y: "+owner.getXActual()+","+owner.getYActual());
        //System.out.println("tilemap x/y: "+owner.gameState.world.area.getTileMap().getX()+","+owner.gameState.world.area.getTileMap().getY());
        double x = (event.getX()/getScale()) - owner.gameState.world.area.getTileMap().getX();
        double y = (event.getY()/getScale()) - owner.gameState.world.area.getTileMap().getY();
        BigDecimal xBD = new BigDecimal(x).setScale(2, RoundingMode.HALF_UP);
        BigDecimal yBD = new BigDecimal(y).setScale(2, RoundingMode.HALF_UP);
        System.out.println("Figuring click's map position to be: "+xBD.doubleValue()+","+yBD.doubleValue());
        return new Point2D(xBD.doubleValue(),yBD.doubleValue());
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

    public double[] getNextMove(double currX, double currY) {
        if (!this.pathing) return null;

        double xDiff = pathLine.getX2() - currX;
        double yDiff = pathLine.getY2() - currY;
        BigDecimal xBD = new BigDecimal(xDiff).setScale(2, RoundingMode.HALF_UP);
        BigDecimal yBD = new BigDecimal(yDiff).setScale(2, RoundingMode.HALF_UP);
        if (
            (xBD.doubleValue() == 0 && yBD.doubleValue() == 0)
            || collisionCount > 120
        ) {
            if (collisionCount > 120) {
                System.out.println("Stuck in collision!");
                collisionCount = 0;
            }
            System.out.println("PATH IS DONE!");
            this.pathLine = null;
            this.pathing = false;
            return null;
        }

        double xa = xDiff > 0 ? 1 : -1;
        double ya = yDiff > 0 ? 1 : -1;
        if (Math.abs(xDiff) < 1) xa = xBD.doubleValue();
        if (Math.abs(yDiff) < 1) ya = yBD.doubleValue();
        if (owner.hasCollided(xa, ya)) {
            System.out.println("collision!");
            collisionCount++;
        }
        return new double[]{xa, ya};
    }
}
