package net.wforbes.omnia.overworld.entity.mob.enemy;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.entity.animation.MovementAnimation;
import net.wforbes.omnia.overworld.entity.attention.EnemyTargetController;
import net.wforbes.omnia.overworld.entity.combat.stat.MobStats;
import net.wforbes.omnia.overworld.entity.mob.Mob;
import net.wforbes.omnia.overworld.entity.mob.player.Player;

import java.awt.*;

import static net.wforbes.omnia.gameFX.OmniaFX.getScale;

public class Enemy extends Mob {

    private boolean isAggroed = false;

    protected final EnemyTargetController enemyTargetController;
    private int fd;
    private double xFP;
    private double yFP;
    private double[] fovQEdges = new double[4];
    private int[] fovDEdges = new int[2];
    private double fovRange;
    private double[] fovEdgeRadians = new double[2];
    private Point2D[] fovEdgePoints = new Point2D[2];
    private Line fovLine;
    private Line[] fovEdgeLines = new Line[2];
    private Point2D[] fovPolyPoints = new Point2D[4];

    public Enemy(OverworldState gameState, String name, String spritePath, int width, int height, MobStats stats) {
        super(gameState, name, 0.45, false, stats);
        this.width = width;
        this.height = height;
        this.nameColor = Color.DARKRED;
        this.nameFlashColor = Color.RED;
        this.numFrames = new int[]{3, 3, 3, 3};
        this.combatNumFrames = new int[]{3, 3, 3, 3};
        this.loadSprites(spritePath);
        this.movementAnimation = new MovementAnimation(this);
        this.facingDir = FACING_S;
        this.fovRange = 75.0;
        this.setAnimationDirection(facingDir);
        this.enemyTargetController = new EnemyTargetController(this);
        //this.npcTargetController.setAttentionSpan(30);
    }

    /*
    @Override
    public Entity getCollidingEntity() {
        return super.getCollidingEntity();
    }*/

    @Override
    public Entity getTarget() {
        return this.enemyTargetController.getTarget();
    }

    @Override
    public void setTarget(Entity e) {
        this.enemyTargetController.setTarget(e);
    }

    @Override
    public void init() {
        //nope
    }

    public void init(double xPos, double yPos) {
        super.init(xPos, yPos);
        this.setPosition(xPos, yPos);
    }

    @Override
    public void update() {
        super.update();
        if (!isAggroed) {
            this.checkAggroTriggers();
        }
    }

    private void checkAggroTriggers() {
        this.checkAggroFOV();
    }
    private boolean printed2 = false;
    private void checkAggroFOV() {
        this.setAggroFOV();
        Point2D p = this.gameState.getWorld().getPlayerLocation();
        if (!this.printed2) {
            System.out.println("polyPoints length: " + fovPolyPoints.length);
            this.printed2 = true;
        }
        //Player p = this.gameState.getPlayer();
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = fovPolyPoints.length - 1; i < fovPolyPoints.length; j = i++) {
            if (
                (fovPolyPoints[i].getY() > p.getY()) != (fovPolyPoints[j].getY() > p.getY()) &&
                (p.getX() < (fovPolyPoints[j].getX() - fovPolyPoints[i].getX()) * (p.getY() - fovPolyPoints[i].getY()) / (fovPolyPoints[j].getY()-fovPolyPoints[i].getY()) + fovPolyPoints[i].getX())
            ) {
                //System.out.println("toggling result");
                result = !result;
            }
        }
        if (result) {
            System.out.println("Within aggro fov");
        }
    }

    private void setAggroFOV() {
        this.fd = this.getFacingDir();
        this.clearFOVVars();

        switch (this.fd) {
            case 0://n
                yFP = -fovRange;
                fovDEdges[0] = 150;
                fovDEdges[1] = 210;
                break;
            case 1://s
                yFP = fovRange;
                fovDEdges[0] = 30;
                fovDEdges[1] = 330;
                break;
            case 2://w
                xFP = -fovRange;
                fovDEdges[0] = 240;
                fovDEdges[1] = 300;
                break;
            case 3://e
                xFP = fovRange;
                fovDEdges[0] = 60;
                fovDEdges[1] = 120;
                break;
            default:
                System.out.println("error on fd switch");
                break;
        }
        this.fovLine = new Line(
            (this.getXActual()+xmap),
            (this.getYActual()+ymap),
            (this.getXActual()+xmap+xFP+fovQEdges[0]+fovQEdges[1]+fovQEdges[2]+fovQEdges[3]),
            (this.getYActual()+ymap+yFP+fovQEdges[0]+fovQEdges[1]+fovQEdges[2]+fovQEdges[3])
        );
        this.fovEdgeRadians[0] = fovDEdges[0]*(Math.PI/180);
        this.fovEdgePoints[0] = new Point2D(
                this.getXActual() +
                        (Math.sin(fovEdgeRadians[0])*fovRange),
                this.getYActual() +
                        (Math.cos(fovEdgeRadians[0])*fovRange)
        );
        this.fovEdgeLines[0] = new Line(
            (this.getXActual()+xmap),
            (this.getYActual()+ymap),
            fovEdgePoints[0].getX(),
            fovEdgePoints[0].getY()
        );
        this.fovEdgeRadians[1] = fovDEdges[1]*(Math.PI/180);
        this.fovEdgePoints[1] = new Point2D(
                this.getXActual() +
                        (Math.sin(fovEdgeRadians[1])*fovRange),
                this.getYActual() +
                        (Math.cos(fovEdgeRadians[1])*fovRange)
        );
        this.fovEdgeLines[1] = new Line(
                (this.getXActual()+xmap),
                (this.getYActual()+ymap),
                fovEdgePoints[1].getX(),
                fovEdgePoints[1].getY()
        );

        this.fovPolyPoints = new Point2D[4];
        this.fovPolyPoints[0] = new Point2D(
            this.fovLine.getStartX()-xmap,
            this.fovLine.getStartY()-ymap
        );
        this.fovPolyPoints[1] = new Point2D(
            this.fovEdgePoints[0].getX(),
            this.fovEdgePoints[0].getY()
        );
        this.fovPolyPoints[2] = new Point2D(
            this.fovLine.getEndX()-xmap,
            this.fovLine.getEndY()-ymap
        );
        this.fovPolyPoints[3] = new Point2D(
                this.fovEdgePoints[1].getX(),
                this.fovEdgePoints[1].getY()
        );
        if (!this.printed1) {
            System.out.println("FOV points: \n" +
                "(" + this.fovPolyPoints[0].getX() + ", " + this.fovPolyPoints[0].getY() + ")\n" +
                "(" + this.fovPolyPoints[1].getX() + ", " + this.fovPolyPoints[1].getY() + ")\n" +
                "(" + this.fovPolyPoints[2].getX() + ", " + this.fovPolyPoints[2].getY() + ")\n" +
                "(" + this.fovPolyPoints[3].getX() + ", " + this.fovPolyPoints[3].getY() + ")"
            );
            this.printed1 = true;
        }
    }
    private boolean printed1 = false;

    private void clearFOVVars() {
        this.xFP = 0.0;
        this.yFP = 0.0;
        this.fovQEdges[0] = 0.0;
        this.fovQEdges[1] = 0.0;
        this.fovQEdges[2] = 0.0;
        this.fovQEdges[3] = 0.0;
        this.fovDEdges[0] = 0;
        this.fovDEdges[1] = 0;
        this.fovLine = new Line();
        this.fovEdgeRadians[0] = 0;
        this.fovEdgeRadians[1] = 0;
        this.fovEdgePoints[0] = new Point2D(0,0);
        this.fovEdgePoints[1] = new Point2D(0,0);
        this.fovEdgeLines[0] = new Line();
        this.fovEdgeLines[1] = new Line();
    }

    private void initCollisionShape() {
        System.out.println("enemy initCollisionShape()");
    }

    @Override
    public boolean hasAttentionOnSomething() {
        return false;
    }

    public void render(GraphicsContext gc) {
        super.render(gc);

        gc.setStroke(Color.LIME);
        gc.strokeLine(
                (this.getXActual()+xmap)* getScale(),
                (this.getYActual()+ymap)* getScale(),
                (this.getXActual()+xmap+xFP+fovQEdges[0]+fovQEdges[1]+fovQEdges[2]+fovQEdges[3])* getScale(),
                (this.getYActual()+ymap+yFP+fovQEdges[0]+fovQEdges[1]+fovQEdges[2]+fovQEdges[3])* getScale()
        );
        gc.strokeOval(fovLine.getEndX()*getScale(), fovLine.getEndY()*getScale(), 2, 2);
        gc.setStroke(Color.LIGHTBLUE);
        gc.strokeLine(
            (this.getXActual()+xmap+xFP+fovQEdges[0]+fovQEdges[1]+fovQEdges[2]+fovQEdges[3])* getScale(),
            (this.getYActual()+ymap+yFP+fovQEdges[0]+fovQEdges[1]+fovQEdges[2]+fovQEdges[3])* getScale(),
            (fovEdgePoints[0].getX()+xmap)* getScale(),
            (fovEdgePoints[0].getY()+ymap)* getScale()
        );


        gc.setStroke(Color.gray(1));
        gc.strokeLine(
                (this.getXActual()+xmap)* getScale(),
                (this.getYActual()+ymap)* getScale(),
                (fovEdgePoints[0].getX()+xmap)* getScale(),
                (fovEdgePoints[0].getY()+ymap)* getScale()
        );

        gc.setStroke(Color.RED);
        gc.strokeLine(
            (this.getXActual()+xmap)* getScale(),
            (this.getYActual()+ymap)* getScale(),
            (fovEdgePoints[1].getX()+xmap)* getScale(),
            (fovEdgePoints[1].getY()+ymap)* getScale()
        );

        gc.setStroke(Color.PINK);
        gc.strokeLine(
                (this.getXActual()+xmap+xFP+fovQEdges[0]+fovQEdges[1]+fovQEdges[2]+fovQEdges[3])* getScale(),
                (this.getYActual()+ymap+yFP+fovQEdges[0]+fovQEdges[1]+fovQEdges[2]+fovQEdges[3])* getScale(),
                (fovEdgePoints[1].getX()+xmap)* getScale(),
                (fovEdgePoints[1].getY()+ymap)* getScale()
        );

        gc.setStroke(Color.POWDERBLUE);
        gc.strokeLine(
                (fovPolyPoints[0].getX()+xmap)* getScale(),
                (fovPolyPoints[0].getY()+ymap)* getScale(),
                (fovPolyPoints[1].getX()+xmap)* getScale(),
                (fovPolyPoints[1].getY()+ymap)* getScale()
        );
        gc.strokeLine(
                (fovPolyPoints[1].getX()+xmap)* getScale(),
                (fovPolyPoints[1].getY()+ymap)* getScale(),
                (fovPolyPoints[2].getX()+xmap)* getScale(),
                (fovPolyPoints[2].getY()+ymap)* getScale()
        );
        gc.strokeLine(
                (fovPolyPoints[2].getX()+xmap)* getScale(),
                (fovPolyPoints[2].getY()+ymap)* getScale(),
                (fovPolyPoints[3].getX()+xmap)* getScale(),
                (fovPolyPoints[3].getY()+ymap)* getScale()
        );
        gc.strokeLine(
                (fovPolyPoints[3].getX()+xmap)* getScale(),
                (fovPolyPoints[3].getY()+ymap)* getScale(),
                (fovPolyPoints[0].getX()+xmap)* getScale(),
                (fovPolyPoints[0].getY()+ymap)* getScale()
        );

    }
}
