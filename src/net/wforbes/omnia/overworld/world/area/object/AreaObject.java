package net.wforbes.omnia.overworld.world.area.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import net.wforbes.omnia.gameFX.rendering.Renderable;
import net.wforbes.omnia.gameState.OverworldState;

import java.util.Objects;

import static net.wforbes.omnia.gameFX.OmniaFX.getScale;

public class AreaObject implements Renderable {

    private final OverworldState gameState;
    protected float x, y, xmap, ymap, width, height;
    protected double collisionRadius;

    protected double baseXToHeightRatio;

    protected Image spriteImg;
    private Ellipse collisionEllipse;

    public AreaObject(OverworldState gameState) {
        this.gameState = gameState;
    }

    public AreaObject(OverworldState gameState, float x, float y) {
        this.gameState = gameState;
        this.x = x;
        this.y = y;
    }

    public AreaObject(OverworldState gameState, String path, float x, float y) {
        this.gameState = gameState;
        this.loadSprite(path);
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
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
    public double getWidth() { return this.width; }
    public double getHeight() { return this.height; }

    protected void loadSprite(String path) {
        this.spriteImg = new Image(
                Objects.requireNonNull(
                        getClass().getResourceAsStream(path)
                )
        );
        this.width = (float)this.spriteImg.getWidth();
        System.out.println(width);
        this.height = (float)this.spriteImg.getHeight();
        System.out.println(height);
        //collisionRadius = 16;
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
        gc.drawImage(
                this.spriteImg,
                (x + xmap - width / 2.0) * getScale(),
                (y + ymap - height / 2.0) * getScale(),
                width * getScale(),
                height * getScale()
        );
    }
    private void renderCollisionGeometry(GraphicsContext gc) {
        gc.setStroke(Color.RED);
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
        gc.strokeOval(
                ((this.x + xmap + (width/2.0)) - ((width-collisionRadius)/2.0)-collisionRadius)*getScale(),
                ((this.y + ymap + (height/2.0))- ((height-collisionRadius)/2.0) - collisionRadius)*getScale(),
                collisionRadius * getScale(),
                collisionRadius * getScale()
        );

    }
}
