package net.wforbes.omnia.overworld.entity;

import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.animation.MovementAnimation;
import net.wforbes.omnia.overworld.entity.attention.AttentionController;
import net.wforbes.omnia.overworld.entity.effect.EntityEffectController;
import net.wforbes.omnia.overworld.entity.movement.MovementController;
import net.wforbes.omnia.overworld.world.terrain.flora.Flora;

import java.util.ArrayList;

import static net.wforbes.omnia.gameFX.OmniaFX.getScale;

public abstract class Mob extends Entity {

    protected AttentionController attentionController;
    protected String name;
    protected String spriteSheetPath;
    protected Image spriteSheet;
    private ArrayList<Image[]> sprites;
    protected int width, height;

    double xmap, ymap;//current area map position
    protected double speed;
    protected double runSpeedMod = 1.5;
    protected boolean isMoving;
    protected boolean isRunning;
    protected int numSteps = 0;
    protected int facingDir; //0-north, 1-south, 2-west, 3-east,
                            //4-nw, 5-ne, 6-sw, 7-se
    //numFrames: each index is a sprite row,
    //  each value is the number of animation frames
    int[] numFrames;
    MovementAnimation movementAnimation;
    //TODO: consider an enum to store facing directions
    //TODO: consider utility class to contain directions
    public static final int FACING_N = 0;
    public static final int FACING_S = 1;
    public static final int FACING_W = 2;
    public static final int FACING_E = 3;
    public static final int FACING_NW = 4;
    public static final int FACING_NE = 5;
    public static final int FACING_SW = 6;
    public static final int FACING_SE = 7;
    public MovementController movementController;
    private EntityEffectController entityEffectController;
    public boolean isPlayer; //TODO: remove - for testing
    protected int collisionHeightOffset;
    protected int collisionRadius;
    protected int collisionBoxWidth;
    protected int collisionBoxHeight;
    public boolean isColliding;

    protected Color nameColor;
    protected Text nameText;
    protected Font nameFont;
    private Timeline nameColorTimeline;

    public Mob(OverworldState gameState, String name, double speed, boolean player) {
        super(gameState);
        this.isPlayer = player;
        this.name = name;
        this.speed = speed;
        this.collisionRadius = 12;
        this.movementController = new MovementController(this);
        this.nameFont = Font.font("Century Gothic", FontWeight.BOLD, 22);
        this.nameText = new Text(this.getName());
        this.nameText.setFont(nameFont);
        this.nameText.setFontSmoothingType(FontSmoothingType.LCD);
        this.initNameAnimation();
        this.attentionController = new AttentionController(this);
        this.entityEffectController = new EntityEffectController(this);
    }

    public Mob(OverworldState gameState, String name, Point2D startPos, double speed) {
        super(gameState);
        this.name = name;
        this.x = startPos.getX();
        this.y = startPos.getY();
        this.speed = speed;
        this.movementController = new MovementController(this);
    }

    @Override
    public String getName() {
        return this.name;
    }
    public double getX() {
        return this.x + this.width/2.0;
    }
    public double getY() {
        return this.y + this.height/2.0;
    }
    public Point2D getLocationPoint() {
        return new Point2D(this.x + this.width/2.0, this.y + this.height/2.0);
    }
    public int getWidth(){ return width; }
    public int getHeight(){ return height; }
    public double getXMap() {
        return this.xmap;
    }
    public double getYMap() {
        return this.ymap;
    }
    public int getFacingDir() {
        return this.facingDir;
    }
    public boolean getIsMoving() {
        return this.isMoving;
    }
    public MovementAnimation getMovementAnimation() {
        return this.movementAnimation;
    }
    public void setRunning(boolean run) {
        this.isRunning = run;
    }

    void loadSprites(String path) {
        this.spriteSheet = new Image(getClass().getResourceAsStream(path));
        this.sprites = new ArrayList<>();

        for(int i = 0; i < numFrames.length; i++) {
            Image[] images = new Image[numFrames[i]];
            for (int j = 0; j < numFrames[i]; j++) {
                images[j] = new WritableImage(spriteSheet.getPixelReader(), j*width, i*height, width, height);
            }
            sprites.add(images);
        }
    }

    public void move(double xa, double ya) {
        //sets sprite image directionality
        if(xa != 0 && ya != 0){
            moveDiagonal(xa, ya);
        }else{
            moveCardinal(xa, ya);
        }

        if (movementAnimation.getFacingDir() != facingDir)
            this.updateAnimationDirection();

        if (this.isMoving != movementAnimation.isMoving())
            movementAnimation.setIsMoving(this.isMoving);

        if(this.hasCollided(xa, ya)) {
            if(this.isPlayer) {
                gameState.gui.getDevWindow().setPlayerCollided(true);
            } else {
                this.isColliding = true;
            }
            if (this.isMovingDiagonally()) {
                this.attemptToSlideAgainst(xa, ya);
            }
        } else {
            if(this.isPlayer) {
                gameState.gui.getDevWindow().setPlayerCollided(false);
            } else {
                this.isColliding = false;
            }

            moveCoords(xa, ya);
            numSteps++;
        }
    }

    public boolean hasAttentionOnSomething() {
        return this.attentionController.getIsFocusing();
    }

    public boolean hasCollided(double xa, double ya) {
        return isOccupied(xa, ya);
    }
    public int getCollisionBoxWidth() { return this.collisionBoxWidth; }
    public int getCollisionRadius() { return this.collisionRadius; }

    protected boolean isOccupied(double xa, double ya) {
        for(Entity e : gameState.world.area.entities) {
            if(!e.getName().equals(this.name)) {
                /* AABB Collision
                if (
                        this.x + xa < e.getX()+collisionBoxWidth &&
                        this.x + xa + collisionBoxWidth > e.getX() &&
                        this.y + ya < e.getY()+collisionBoxHeight &&
                        this.y + ya + collisionBoxHeight >  e.getY()
                ) {
                    return true;
                }*/
                double xDist = (this.getX()+xa - e.getX());
                double yDist = (this.getY()+ya - e.getY());
                if(Math.sqrt((xDist*xDist) + (yDist*yDist)) <= (collisionRadius/2.0+e.getCollisionRadius()/2.0)) {
                    return true;
                }
            }
        }
        // LOL put this collision check somewhere with better access to new terrain items
        for (Flora f : gameState.world.area.getTerrainController().getFloraController().getBushes()) {
            double xDist = (this.getX()+xa - (f.getX() + f.getWidth()/2.0));
            double yDist = (this.getY()+ya - (f.getY() + f.getHeight()/2.0));
            if(Math.sqrt((xDist*xDist) + (yDist*yDist)) <= (collisionRadius/2.0+f.getCollisionRadius()/2.0)) {
                return true;
            }
        }
        return false;
    }

    public boolean isMovingDiagonally() {
        return this.facingDir > 3;
    }

    private void attemptToSlideAgainst(double xa, double ya) {
        for(int i = 4; i <= 7; i++) {//iterate through diagonal directions
            if(facingDir == i) {
                if(!hasCollided(xa, 0)) {
                    ya = 0;
                    moveCoords(xa, ya);
                    numSteps++;
                } else if (!hasCollided(0, ya)) {
                    xa = 0;
                    moveCoords(xa, ya);
                    numSteps++;
                }
            }
        }
    }

    public void setMoving(boolean moving) {
        this.isMoving = moving;
    }

    void setAnimationDirection(int dir) {
        movementAnimation.setFacingDir(dir);
        movementAnimation.setFrames(sprites.get(dir));
        movementAnimation.setDelay((long)(100 / this.speed));
    }
    public void updateAnimationDirection() {
        movementAnimation.setFacingDir(facingDir);
        movementAnimation.setFrames(sprites.get(facingDir));
        movementAnimation.setDelay((long)(100/this.speed));
    }

    private void moveCardinal(double xa, double ya){
        if (ya < 0)
            facingDir = FACING_N;
        if (ya > 0)
            facingDir = FACING_S;
        if (xa < 0)
            facingDir = FACING_W;
        if (xa > 0)
            facingDir = FACING_E;
    }
    private void moveDiagonal(double xa, double ya){
        if (ya < 0 && xa  < 0)
            facingDir = FACING_NW;
        if (ya < 0 && xa > 0)
            facingDir = FACING_NE;
        if (ya > 0 && xa < 0)
            facingDir = FACING_SW;
        if (ya > 0 && xa > 0)
            facingDir = FACING_SE;
    }

    private void moveCoords(double xa, double ya) {
        this.x += xa * (speed * ((isRunning)?runSpeedMod:1));
        this.y += ya * (speed * ((isRunning)?runSpeedMod:1));
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setFacingDir(int direction) {
        this.facingDir = direction;
    }

    protected void refreshMapPosition() {
        xmap = gameState.world.area.getTileMap().getX();
        ymap = gameState.world.area.getTileMap().getY();
    }

    protected boolean offScreen() {
        return x + xmap + width < 0 ||
                x + xmap - width/2.5 > OmniaFX.getWidth() ||
                y + ymap + height < 0 ||
                y + ymap - height/2.5 > OmniaFX.getHeight();
    }

    //public abstract void init();
    private boolean isTargeted;
    public boolean isTargeted() {
        return this.isTargeted;
    }
    public void setTargeted(boolean wasTargeted) {
        if (wasTargeted && !this.isTargeted) {
            this.isTargeted = true;
            this.entityEffectController.getTargetCircle().set();
            System.out.println(this.nameColorTimeline);
            //this.nameColorTimeline.play();
            return;
        }
        // otherwise
        this.isTargeted = false;
        this.entityEffectController.getTargetCircle().unset();
        //this.nameColorTimeline.stop();
    }

    public void initNameAnimation() {
        this.nameText.setX(20);
        this.nameText.setY(100);
        var color1 = Color.LIGHTBLUE;
        var color2 = Color.BLUE;
        nameText.setFill(color1);
        /*
        text.setStyle("-fx-font-family: serif; -fx-font-size: 42;"
                + "-fx-font-style: oblique; -fx-font-weight: bold");
         */
        nameColorTimeline =  new Timeline(
                new KeyFrame(new Duration(0),
                    new KeyValue(nameText.fillProperty(), color2)
                ),
                new KeyFrame(new Duration(750),
                        new KeyValue(nameText.fillProperty(), color1, Interpolator.EASE_IN)
                )
        );
        nameColorTimeline.setCycleCount(Timeline.INDEFINITE);
        nameColorTimeline.setAutoReverse(true);
        nameColorTimeline.play();
        /*
        nameFillTransition = new FillTransition(
                Duration.seconds(0.5),
                this.nameText,
                this.nameColor,
                Color.LIGHTBLUE
        );

        nameFillTransition.setCycleCount(Timeline.INDEFINITE);
        nameFillTransition.setAutoReverse(true);
        nameFillTransition.play();
        */
    }

    public void update() {
        this.attentionController.update();
    }

    public void render(GraphicsContext gc) {
        this.refreshMapPosition();
        /*
        if(gameState.collisionGeometryVisible()) {
            gc.setStroke(Color.RED);
            gc.strokeRect(
                    (x + Math.floor(xmap) - collisionBoxWidth / 2.0) * getScale(),
                    (y + Math.floor(ymap) - collisionBoxHeight / 2.0) * getScale(),
                    collisionBoxWidth * getScale(),
                    collisionBoxHeight * getScale()
            );
        }*/

        if(!offScreen()) {
            this.renderBackgroundEffects(gc);
            this.renderSprite(gc);

            if(gameState.mobNamesVisible()) {
                this.renderName(gc);
            }
            if(gameState.collisionGeometryVisible()) {
                this.renderCollisionGeometry(gc);
            }
        }
    }
    private void renderBackgroundEffects(GraphicsContext gc) {
        this.entityEffectController.renderBackground(gc);
    }
    private boolean rendered = false;
    private void renderSprite(GraphicsContext gc) {
        /* use to debug position data from first render
        if (!rendered) {
            System.out.println(this.getName() + " renderedX: " + (x + xmap - width / 2.0) * getScale());
            System.out.println(this.getName() + " renderedY: " + (y + ymap - height / 2.0) * getScale());
            System.out.println(this.getName() + " renderedWidth: " + width * getScale());
            System.out.println(this.getName() + " renderedHeight: " + height * getScale());
            this.rendered = true;
        }*/

        gc.drawImage(
                movementAnimation.getImage(),
                (x + xmap - width / 2.0) * getScale(),
                (y + ymap - height / 2.0) * getScale(),
                width * getScale(),
                height * getScale()
        );
    }


    private void renderName(GraphicsContext gc) {
        if (this.isTargeted && this.nameColorTimeline.getStatus() == Animation.Status.RUNNING) {
            gc.setFill(this.nameText.getFill());
            gc.setFont(nameText.getFont());
            gc.fillText(
                    nameText.getText(),
                    ((this.x + xmap) * getScale()) - nameText.getLayoutBounds().getWidth() / 2.0,
                    (this.y + ymap) * getScale() - nameText.getLayoutBounds().getHeight() * 1.5
            );
        } else {
            gc.setFill(this.nameColor);
            gc.setFont(nameText.getFont());
            gc.fillText(
                    nameText.getText(),
                    ((this.x + xmap) * getScale()) - nameText.getLayoutBounds().getWidth() / 2.0,
                    (this.y + ymap) * getScale() - nameText.getLayoutBounds().getHeight() * 1.5
            );
        }
    }
    private void renderCollisionGeometry(GraphicsContext gc) {
        gc.setStroke(Color.RED);
        gc.strokeOval(
                ((this.x + xmap + (width/2.0))-((width-collisionRadius)/2.0)-collisionRadius)*getScale(),
                ((this.y + ymap + (height/2.0))-((height-collisionRadius)/2.0) - collisionRadius)*getScale(),
                collisionRadius * getScale(),
                collisionRadius * getScale());
    }

    public void teardown() {
        this.name = null;
        this.movementController.teardown();
        this.movementController = null;
        this.nameFont = null;
        this.nameText = null;
        this.attentionController.teardown();
        this.attentionController = null;
        this.entityEffectController.teardown();
        this.entityEffectController = null;
        this.spriteSheet = null;
        this.sprites = null;
    }
}
