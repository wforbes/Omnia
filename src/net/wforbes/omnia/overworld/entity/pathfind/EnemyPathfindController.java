package net.wforbes.omnia.overworld.entity.pathfind;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import net.wforbes.omnia.overworld.entity.mob.Mob;

import java.awt.geom.Line2D;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class EnemyPathfindController extends PathfindController {

    public EnemyPathfindController(Mob owner) {
        super(owner);
    }

    @Override
    public void moveToClick(MouseEvent event) {

    }

    public void moveToPoint(Point2D goal) {
        if (
            Math.abs(goal.getX() - this.owner.getXActual()) < this.owner.getWidth()
            && Math.abs(goal.getY() - this.owner.getYActual()) < this.owner.getHeight()
        ) {
            //System.out.println("Enemy "+owner.getName()+" is at the goal");
            this.owner.setMoving(false);
            this.pathing = false;
            return;
        }
        this.pathLine = new Line2D.Double(Math.floor(owner.getXActual()), Math.floor(owner.getYActual()), goal.getX(), goal.getY());
        this.pathing = true;

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
                System.out.println(this.owner + " is stuck in collision!");
                collisionCount = 0;
            }
            System.out.println(this.owner + " PATH IS DONE!");
            this.pathLine = null;
            this.pathing = false;
            return null;
        }

        double xa = xDiff > 0 ? 1 : -1;
        double ya = yDiff > 0 ? 1 : -1;
        if (Math.abs(xDiff) < 1) xa = xBD.doubleValue();
        if (Math.abs(yDiff) < 1) ya = yBD.doubleValue();
        if (owner.hasCollided(xa, ya)) {
            System.out.println(this.owner + " collision!");
            collisionCount++;
        }
        this.lastNextMove = new double[]{xa, ya};
        return new double[]{xa, ya};
    }

    public double[] lastNextMove = new double[2];
    public double[] getLastNextMove() {
        return this.lastNextMove;
    }
}
