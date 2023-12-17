package net.wforbes.omnia.overworld.world.area.object;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import net.wforbes.omnia.db.AreaObjectDBA;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameFX.rendering.Renderable;
import net.wforbes.omnia.gameState.OverworldState;

import java.util.Objects;

import static net.wforbes.omnia.gameFX.OmniaFX.getScale;

public class AreaObject implements Renderable {

    public int getCollisionXOffset() {
        return 0; //TODO: convert area objects to rectangular collision?
    }

    protected final OverworldState gameState;
    protected final AreaObjectDBA areaObjectDBA;
    protected int x, y;
    protected float xmap, ymap, width, height;
    protected double baseY;
    protected double collisionRadius;
    protected double collision_baseX;
    protected double collision_baseY;
    protected Point2D collision_baseCenterPnt;
    protected Circle collision_baseCircle;

    protected double baseXToHeightRatio;

    protected String sprite_path;
    protected Image spriteImg;
    private Ellipse collisionEllipse;

    protected boolean spawned = true;
    protected boolean flaggedForDespawn = false;
    protected ObjectSpawnTimer spawnTimer = new ObjectSpawnTimer(this);
    protected AreaObjectType areaObjectType;
    protected boolean waitingToSpawn;

    public AreaObject(OverworldState gameState, int x, int y) {
        this.gameState = gameState;
        this.x = x;
        this.y = y;
        this.areaObjectDBA = new AreaObjectDBA(this.gameState.db);
    }

    public double getX() {
        return this.x - this.width/2.0;
    }
    public double getY() {
        return this.y - this.height/2.0;
    }
    public double getBaseY() {
        return this.baseY;
    }
    public double getXMap() {
        return this.xmap;
    }
    public double getYMap() {
        return this.ymap;
    }
    public double getCollisionRadius() {
        return this.collisionRadius;
    }
    public double getCollisionBaseX() {
        return this.collision_baseX;
    }
    public double getCollisionBaseY() {
        return this.collision_baseY;
    }
    public double getWidth() { return this.width; }
    public double getHeight() { return this.height; }

    public boolean isSpawned() {
        return this.spawned;
    }
    public void despawn() {
        this.spawned = false;
        this.spawnTimer.start();
        //this.x = this.y = -100;
    }
    public void notifySpawnTimerDone() {
        System.out.println(this + " spawn timer done");
        if (this.spawnWouldCollide()) {
            this.waitingToSpawn = true;
            return;
        }
        this.waitingToSpawn = false;
        this.spawn();
    }

    private boolean spawnWouldCollide () {
        return this.gameState.getWorld().getCurrentArea().spawnSpaceIsBlocked(
            this.x, this.y, this.collision_baseX, this.collision_baseY, this.collisionRadius
        );
    }

    protected void spawn() {
        if (this.isSpawned()) return;
        this.spawned = true;
    }

    public void init(double x, double y) {}
    public void init() {}

    protected void loadSprite(String path) {
        this.spriteImg = new Image(
                Objects.requireNonNull(
                        getClass().getResourceAsStream(path)
                )
        );
        this.width = (float)this.spriteImg.getWidth();
        this.height = (float)this.spriteImg.getHeight();
        //collisionRadius = 16;
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

    public void update() {
        if (this.isSpawned()) {
            this.recalculateBaseY();
            return;
        }
        if (this.waitingToSpawn) {
            this.notifySpawnTimerDone();
            return;
        }
        this.spawnTimer.update();
    }

    private void recalculateBaseY() {
        this.baseY = ((this.getY() + ymap) + this.collision_baseY);
    }

    public void render(GraphicsContext gc) {
        this.refreshMapPosition();
        this.renderSprite(gc);
        if (gameState.collisionGeometryVisible()) this.renderCollisionGeometry(gc);
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

    private void renderSprite(GraphicsContext gc) {
        /*//render with x/y in top left
        gc.drawImage(
                this.spriteImg,
                (x + xmap) * getScale(),
                (y + ymap) * getScale(),
                width*getScale(),
                height*getScale()
        );*/
        //render with x/y in center of sprite
        if (!this.isSpawned()) return;
        gc.drawImage(
                this.spriteImg,
                (this.getX() + xmap) * getScale(),
                (this.getY() + ymap) * getScale(),
                width * getScale(),
                height * getScale()
        );
    }
    private void renderCollisionGeometry(GraphicsContext gc) {
        gc.setStroke(Color.RED);
        if(!this.offScreen() || this.isSpawned()) {
            gc.strokeOval(
                    (this.getX() + xmap) + this.collision_baseX + 8,
                    (this.getY() + ymap) + this.collision_baseY + 8,
                    collisionRadius,
                    collisionRadius - (collisionRadius / 2.5)
            );
            gc.strokeOval(
                    ((this.getX() + xmap) + this.collision_baseX) * getScale(),
                    ((this.getY() + ymap) + this.collision_baseY) * getScale(),
                    collisionRadius * getScale(),
                    (collisionRadius - (collisionRadius / 2.5)) * getScale()
            );
        }
        //render with x/y at topleft of oval xy tangents
        /*
        gc.strokeOval(
                (this.x + xmap) * getScale(),
                (this.y + ymap) * getScale(),
                //((this.x + xmap + (width/2.0)) -((width-collisionRadius)/2.0)-collisionRadius)*getScale(),
                //((this.y + ymap + (height/2.0))- ((height-collisionRadius)/2.0) - collisionRadius)*getScale(),
                collisionRadius * getScale(),
                collisionRadius * getScale()
        );*/
        //render with oval center at x/y
        /*
        gc.strokeOval(
                ((this.x + xmap + (width/2.0)) - ((width-collisionRadius)/2.0)-collisionRadius)*getScale(),
                ((this.y + ymap + (height/2.0))- ((height-collisionRadius)/2.0) - collisionRadius)*getScale(),
                collisionRadius * getScale(),
                collisionRadius * getScale()
        );*/

    }
}
