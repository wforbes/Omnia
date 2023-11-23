package net.wforbes.omnia.overworld.world.terrain.flora;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import net.wforbes.omnia.gameState.OverworldState;

import java.util.Random;

import static net.wforbes.omnia.gameFX.OmniaFX.getScale;

public class BushFlora extends Flora {
    private Point2D point2D;
    private Image spriteImg;
    private double x, y, xmap, ymap, width, height;

    private int collisionRadius = 16;

    public BushFlora(OverworldState gameState) {
        super(gameState);

        this.loadSprite(OverworldState.TERRAIN_DIR + "flora/bushes/Bush_blue_flowers3.png");
        Random rand = new Random();
        this.x = rand.nextDouble() * 100;
        this.y = rand.nextDouble() * 100;
        this.point2D = new Point2D(this.x, this.y);
    }

    public BushFlora(OverworldState gameState, double x, double y) {
        super(gameState);
        this.loadSprite(OverworldState.TERRAIN_DIR + "flora/bushes/Bush_blue_flowers3.png");
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public int getCollisionRadius() {
        return this.collisionRadius;
    }
    public double getXMap() {
        return this.xmap;
    }
    public double getYMap() {
        return this.ymap;
    }
    public double getWidth() { return this.width; }
    public double getHeight() { return this.height; }

    private void loadSprite(String path) {
        this.spriteImg = new Image(getClass().getResourceAsStream(path));
        this.width = this.spriteImg.getWidth();
        this.height = this.spriteImg.getHeight();
    }

    public void init() {}

    public Point2D getPoint2D() {
        return this.point2D;
    }

    public void render(GraphicsContext gc) {
        this.refreshMapPosition();
        this.renderSprite(gc);
        //this.renderCollisionGeometry(gc);
    }

    protected void refreshMapPosition() {
        xmap = this.gameState.world.area.getTileMap().getX();
        ymap = this.gameState.world.area.getTileMap().getY();
    }

    private void renderCollisionGeometry(GraphicsContext gc) {
        gc.setStroke(Color.RED);
        gc.strokeOval(
                ((this.x + xmap + (width/2.0))-((width-collisionRadius)/2.0)-collisionRadius)*getScale(),
                ((this.y + ymap + (height/2.0))-((height-collisionRadius)/2.0) - collisionRadius)*getScale(),
                collisionRadius * getScale(),
                collisionRadius * getScale());
    }

    private void renderSprite(GraphicsContext gc) {
        gc.drawImage(
                this.spriteImg,
                (x + xmap - width / 2.0) * getScale(),
                (y + ymap - height / 2.0) * getScale(),
                width * getScale(),
                height * getScale()
        );
    }
}
