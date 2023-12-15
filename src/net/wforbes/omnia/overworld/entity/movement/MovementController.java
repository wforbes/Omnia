package net.wforbes.omnia.overworld.entity.movement;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import net.wforbes.omnia.overworld.entity.mob.Mob;

public class MovementController {
    protected Mob mover;
    private int[] xya = new int[3];
    private Rectangle2D movementSpace;
    private int movementType;
    public static int MOVEMENT_STAND = 0;
    public static int MOVEMENT_PACE_VERTICAL = 1;

    private long waitCount = 0;
    private boolean moveReady = false;
    private boolean waitingToPace = false;
    private boolean paceCollideWait = false;
    private boolean paceCollideTurnAround = true;

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

    protected void standStill() {
        if (this.mover.hasAttentionOnSomething()) return;

        if(waitingToPace) {
            this.tryToMoveInFacingDir();
        }

        if(!this.mover.isColliding) {
            this.resumePacing();
        }
    }

    private void tryToMoveInFacingDir() {
        if(this.mover.getFacingDir() == 0) {
            this.xya = new int[]{0, -1, 0};
        } else if(this.mover.getFacingDir() == 1){
            this.xya = new int[]{0, 1, 1};
        }
        this.mover.move(xya[0], xya[1]);
    }

    private void resumePacing() {
        this.setMovementType(1);
        this.waitingToPace = false;
        this.mover.setMoving(true);
    }

    public void standAndFace(Point2D targetLoc) {
        this.stopMovement();
        double angle = new Point2D(1,0).angle(this.mover.getLocationPoint().subtract(targetLoc));
        System.out.println(targetLoc);
        System.out.println(angle);
        if (angle > 45 && angle < 135) {//vertical facing
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

        if (this.mover.getMovementAnimation().getFacingDir() != this.mover.getFacingDir())
            this.mover.updateAnimationDirection();
    }

    private void stopMovement() {
        this.setMovementType(0);
        this.mover.setMoving(false);
        this.mover.getMovementAnimation().setIsMoving(this.mover.getIsMoving());
    }

    private void paceVertically(int waitMultiple, int waitReset, int minYBound, int maxYBound) {

        if (this.waitCount == 0 || this.waitCount == (waitMultiple + 1) * waitReset) {
            this.waitCount = 1;
            this.moveReady = false;
        } else
        if (this.waitCount % waitMultiple == 0) {

            if(!this.mover.isColliding){
                if(waitingToPace) {
                    this.waitingToPace = false;
                }

                if (this.mover.getY() < minYBound) {
                    this.xya = new int[]{0, 1, 1};
                    this.moveReady = true;
                }

                if (this.mover.getY() >= minYBound && this.mover.getY() <= maxYBound) {
                    if (this.xya[2] == 0) this.xya[1] = -1;
                    if (xya[2] == 1) this.xya[1] = 1;
                    this.moveReady = true;
                }

                if (this.mover.getY() > maxYBound) {
                    this.xya = new int[]{0, -1, 0};
                    this.moveReady = true;
                }
            } else {// if colliding, do something else
                if(this.paceCollideTurnAround) {
                    if (this.mover.getFacingDir() == 1) {
                        this.mover.setFacingDir(0);
                        this.xya = new int[]{0, -1, 0};
                        this.moveReady = true;
                    } else {
                        this.mover.setFacingDir(1);
                        this.xya = new int[]{0, 1, 1};
                        this.moveReady = true;
                    }
                } else if(this.paceCollideWait) {
                    this.waitingToPace = true;
                    this.stopMovement();
                    this.moveReady = false;
                } else {
                    this.stopMovement();
                    this.moveReady = false;
                }
            }
        }

        if(this.moveReady) {
            this.mover.setMoving(true);
            this.mover.move(xya[0], xya[1]);
        }
        this.moveReady = false;
        this.waitCount++;
    }

    public void teardown() {
        this.mover = null;
    }
}
