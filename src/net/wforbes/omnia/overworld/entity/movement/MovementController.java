package net.wforbes.omnia.overworld.entity.movement;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import net.wforbes.omnia.overworld.entity.Mob;

public class MovementController {
    protected Mob mover;
    private int[] xya = new int[3];
    private Rectangle2D movementSpace;
    private int movementType;
    public static int MOVEMENT_STAND = 0;
    public static int MOVEMENT_PACE_VERTICAL = 1;

    private long waitCount = 0;
    private boolean moveReady = false;

    public MovementController(Mob mover) {
        this.mover = mover;
    }

    public void setMovementType(int moveType) {
        this.movementType = moveType;
    }

    public void setMovementSpace(Rectangle2D space) {
        this.movementSpace = space;
    }

    public void setMovementSpace(double minX, double minY, double width, double height) {
        this.movementSpace = new Rectangle2D(minX, minY, width, height);
    }

    public void update() {
        switch(movementType) {
            case 0:
                this.standStill();
                break;
            case 1:
                this.paceVertically(5, 10, (int)movementSpace.getMinY(), (int)movementSpace.getMaxY());
        }
    }

    protected void standStill() { }

    public void standAndFace(Point2D targetLoc) {
        this.setMovementType(0);
        double angle = new Point2D(this.mover.getX(), this.mover.getY()).angle(targetLoc);
        //TODO: refactor this to test for diagonal, 45, and 30>x<60,
        //  then apply booleans in one line
        if (angle > 0.45) {//vertical facing
            if (targetLoc.getY() < this.mover.getY()) {//facing north
                this.mover.setFacingDir(Mob.FACING_N);
            } else {//facing south
                this.mover.setFacingDir(Mob.FACING_S);
            }
        } else {//horizontal facing
            if (targetLoc.getX() < this.mover.getX()) {
                //facing west
                this.mover.setFacingDir(Mob.FACING_W);
            } else {
                //facing east
                this.mover.setFacingDir(Mob.FACING_E);
            }
        }
    }

    private void paceVertically(int waitMultiple, int waitReset, int minYBound, int maxYBound) {
        if (this.waitCount == 0 || this.waitCount == (waitMultiple + 1) * waitReset) {
            this.waitCount = 1;
            this.moveReady = false;
        } else if (this.waitCount % waitMultiple == 0) {
            if (this.mover.getY() < minYBound) {
                this.xya = new int[]{0, 1, 1};
                this.moveReady = true;
            }

            if (this.mover.getY() >= minYBound && this.mover.getY() <= maxYBound) {
                if(this.xya[2] == 0) this.xya[1] = -1;
                if(xya[2] == 1) this.xya[1] = 1;
                this.moveReady = true;
            }

            if (this.mover.getY() > maxYBound) {
                this.xya = new int[]{0, -1, 0};
                this.moveReady = true;
            }
        }

        if(this.moveReady) {
            this.mover.setMoving(true);
            this.mover.move(xya[0], xya[1]);
        }
        this.moveReady = false;
        this.waitCount++;
    }

}
