package net.wforbes.omnia.overworld.entity.mob;

import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.entity.animation.MovementAnimation;
import net.wforbes.omnia.overworld.entity.combat.CombatController;
import net.wforbes.omnia.overworld.entity.combat.stat.MobStats;
import net.wforbes.omnia.overworld.entity.combat.stat.StatController;
import net.wforbes.omnia.overworld.entity.effect.EntityEffectController;
import net.wforbes.omnia.overworld.entity.movement.MovementController;
import net.wforbes.omnia.overworld.entity.pathfind.PathfindController;
import net.wforbes.omnia.overworld.world.area.object.AreaObject;
import net.wforbes.omnia.overworld.world.area.object.flora.Flora;

import java.util.ArrayList;

import static net.wforbes.omnia.gameFX.OmniaFX.getScale;

public abstract class Mob extends Entity {
    //protected AttentionController attentionController;
    protected String name;
    protected Image spriteSheet;
    private ArrayList<Image[]> sprites;
    protected int width, height;
    protected double x, y, baseY;
    protected double xmap, ymap;//current area map position
    protected double speed;
    protected double combatSpeed; //TODO: Move to CombatController
    protected double runSpeedMod = 1.5;
    protected boolean isMoving;
    protected boolean isRunning;
    protected int numSteps = 0;
    protected int facingDir; //0-north, 1-south, 2-west, 3-east,
                            //4-nw, 5-ne, 6-sw, 7-se
    //numFrames: each index is a sprite row,
    //  each value is the number of animation frames
    protected int[] numFrames;
    protected int[] combatNumFrames;
    protected MovementAnimation movementAnimation;
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
    protected double collisionRadius;
    public boolean isColliding;
    private AreaObject collidingAreaObject;
    protected Color nameColor;
    protected Color nameFlashColor;
    protected Text nameText;
    protected Font nameFont;
    private Timeline nameColorTimeline;
    public OverworldState gameState;
    private double collision_baseX;
    private double collision_baseY;
    protected PathfindController pathfindController;
    private ArrayList<Image[]> combatSprites;
    private Circle collision_baseCircle;
    private Point2D collision_baseCenterPnt;
    public PathfindController getPathfindController() {
        return this.pathfindController;
    }

    protected CombatController combatController;
    private Entity collidingEntity;
    protected int meleeReach;

    public Mob(OverworldState gameState, String name, double speed, boolean player, MobStats stats) {
        super(gameState);
        this.gameState = gameState;
        this.isPlayer = player;
        this.name = name;
        this.speed = speed;
        this.combatSpeed = 0.6; //TODO: Move to CombatController
        this.movementController = new MovementController(this);
        this.nameFont = Font.font("Century Gothic", FontWeight.BOLD, 22);
        this.nameText = new Text(this.getName());
        this.nameText.setFont(nameFont);
        this.nameText.setFontSmoothingType(FontSmoothingType.LCD);
        this.initNameAnimation();
        this.entityEffectController = new EntityEffectController(this);
        this.pathfindController = new PathfindController(this);
        this.combatController = new CombatController(this);
        this.statController = new StatController(
            this, stats);
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
        return this.x - this.width/2.0;
    }
    public double getXActual() { return this.x; }
    public double getYActual() { return this.y; }
    public double getY() {
        return this.y - this.height/2.0;
    }
    public Point2D getLocationPoint() {
        return new Point2D(this.x + this.width/2.0, this.y + this.height/2.0);
    }
    public double getWidth(){ return width; }
    public double getHeight(){ return height; }
    public double getXMap() {
        return this.xmap;
    }
    public double getYMap() {
        return this.ymap;
    }
    public double getBaseY() { return this.baseY; }
    public double getRenderX() {
        return this.renderX;
    }
    public double getRenderY() {
        return this.renderY;
    }
    public double getRenderXMap() {
        return this.renderXMap;
    }
    public double getRenderYMap() {
        return this.renderYMap;
    }
    public double getRenderWidth() {
        return this.renderWidth;
    }
    public double getRenderHeight() {
        return this.renderHeight;
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
    public int getCollisionBoxWidth() { return this.collisionBoxWidth; }
    public int getCollisionBoxHeight() { return this.collisionBoxHeight; }
    public double getCollisionRadius() { return this.collisionRadius; }
    public void setCollisionRadius(int radius) { this.collisionRadius = radius; }
    public double getCollisionBaseX() { return this.collision_baseX; }
    public void setCollisionBaseX(double x) { this.collision_baseX = x; }
    public double getCollisionBaseY() { return this.collision_baseY; }
    public void setCollisionBaseY(double y) { this.collision_baseY = y; }
    public void setCollisionBaseCenterPnt(Point2D centerPnt) { this.collision_baseCenterPnt = centerPnt; }
    public void setCollisionBaseCircle(Circle baseCircle) { this.collision_baseCircle = baseCircle; }
    public int getCollisionXOffset() {
        return this.collisionXOffset;
    }
    public void setCollisionXOffset(int offset) {
        this.collisionXOffset = offset;
    }
    public int getCollisionYOffset() {
        return this.collisionYOffset;
    }
    public void setCollisionYOffset(int offset) {
        this.collisionYOffset = offset;
    }
    public void setCollisionBoxWidth(int boxWidth) {
        this.collisionBoxWidth = boxWidth;
    }
    public void setCollisionBoxHeight(int boxHeight) {
        this.collisionBoxHeight = boxHeight;
    }
    public void setBaseY(double y) { this.baseY = y; }
    public AreaObject getCollidingAreaObject() {
        return this.collidingAreaObject;
    }
    public Entity getCollidingEntity() { return this.collidingEntity; }
    public CombatController getCombatController() { return this.combatController; }
    public int getMeleeReach() {
        return this.meleeReach;
    }

    public boolean isInMeleeRange(Entity e) {
        if (
            this.getX() - this.meleeReach < e.getX() + e.getWidth() &&
            this.getX() + this.meleeReach + this.getWidth() > e.getX() &&
            this.getY() - this.meleeReach < e.getY() + e.getHeight() &&
            this.getY() + this.meleeReach + this.getHeight() >  e.getY()
        ) {
            return true;
        }
        return false;
    }

    public StatController getStatController() {
        return this.statController;
    }
    public float getMeleeAccuracy() {
        return this.statController.getMeleeAccuracy();
    }
    public int getMaxMeleeDamage() {
        return this.statController.getMaxMeleeDmg();
    }
    public void receiveMeleeDamage(int dmg, Entity dealer) {
        this.statController.receiveMeleeDamage(dmg);
    }
    public int getCurrentHealth() {
        return this.statController.getCurrentHealth();
    }
    public int getMaxHealth() {
        return this.statController.getMaxHealth();
    }
    public void addHarvestMaterialsToInventory(Flora flora) {
        System.out.println("TODO: add harvest materials to inventory");
    }
    public void setRunning(boolean run) {
        this.isRunning = run;
    }

    protected void init(double xPos, double yPos) {
        this.initCollisionShape();

    }
    private void initCollisionShape() {
        //entity/areaobject collision
        //this.collisionBoxWidth = this.width/2;
        //this.collisionBoxHeight = this.height/2;
        this.collision_baseX = 3;//3;
        this.collision_baseY = this.height-5;//this.height - 5;
        this.baseY = this.collision_baseY;
        this.collisionRadius = 10;
        this.collision_baseCenterPnt = new Point2D(collision_baseX, collision_baseY);
        this.collision_baseCircle = new Circle(collision_baseX, collision_baseY, collisionRadius);

        //entity/entity collision
        this.setCollisionXOffset(2);
        this.setCollisionYOffset(8);
        this.setCollisionBoxWidth(12);
        this.setCollisionBoxHeight(8);
    }

    protected void loadSprites(String path) {
        this.spriteSheet = new Image(getClass().getResourceAsStream(path));
        this.sprites = new ArrayList<>();
        for(int i = 0; i < numFrames.length; i++) {
            Image[] images = new Image[numFrames[i]];
            for (int j = 0; j < numFrames[i]; j++) {
                images[j] = new WritableImage(spriteSheet.getPixelReader(), j*width, i*height, width, height);
            }
            sprites.add(images);
        }
        this.combatSprites = new ArrayList<>();
        for (int i = 0; i < combatNumFrames.length; i++) { //sprite image sheet row?
            Image[] c_images = new Image[combatNumFrames[i]];
            for (int j = 0; j < combatNumFrames[i]; j++) {//sprite image sheet column?
                c_images[j] = new WritableImage(spriteSheet.getPixelReader(), (j+2)*width, i*height, width, height);
            }
            combatSprites.add(c_images);
        }
    }

    public void move(double xa, double ya) {
        //sets sprite image directionality
        if(xa != 0 && ya != 0){
            //this.speed = 0.375; //TODO: find a clever way to compensate for diagonal speed increase
            moveDiagonal(xa, ya);
        }else{
            //this.speed = 0.5;
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

    public abstract boolean hasAttentionOnSomething();

    public boolean hasCollided(double xa, double ya) {
        return isOccupied(xa, ya);
    }

    protected boolean isOccupied(double xa, double ya) {
        //TODO: simplify this to iterate through area Renderables
        for(Entity e : gameState.world.area.entities) {
            if(!e.getName().equals(this.name)) {
                /* AABB Collision */
                if (
                    (this.getX()) + xa + collisionXOffset < e.getX()+e.getCollisionBoxWidth()+e.getCollisionXOffset() &&
                    this.getX() + xa + collisionBoxWidth + collisionXOffset > e.getX() + e.getCollisionXOffset() &&
                    this.getY() + ya + collisionYOffset < e.getY()+e.getCollisionBoxHeight()+e.getCollisionYOffset() &&
                    this.getY() + ya + collisionBoxHeight + collisionYOffset >  e.getY() + e.getCollisionYOffset()
                ) {
                    this.collidingEntity = e;
                    return true;
                }
                /*
                double xDist = (this.getX()+xa+collision_baseX - this.collisionRadius/2.0) - (e.getX()+collision_baseX - this.collisionRadius/2.0);
                double yDist = (this.getY()+ya+collision_baseY - this.collisionRadius/2.0) - (e.getY()+collision_baseY - this.collisionRadius/2.0);
                if(Math.sqrt((xDist*xDist) + (yDist*yDist)) < ((e.getCollisionRadius())/2.0)+(this.getCollisionRadius())/2.0) {
                    return true;
                }*/
            }
        }
        this.collidingEntity = null;

        for (AreaObject ao : gameState.world.area.getAreaObjects()) {
            if (!ao.isSpawned()) continue;
            double xDist = (this.getX()+xa+collision_baseX - this.collisionRadius/2.0) - (ao.getX()+ao.getCollisionBaseX());
            double yDist = (this.getY()+ya+collision_baseY - this.collisionRadius/3.0) - (ao.getY()+ao.getCollisionBaseY());
            boolean collided = Math.sqrt((xDist*xDist) + (yDist*yDist)) < ((collisionRadius-2)/2.0+(ao.getCollisionRadius()-3)/2.0);
            if(collided) {
                this.collidingAreaObject = ao;
                return true;
            }
        }
        this.collidingAreaObject = null;
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

    protected void setAnimationDirection(int dir) {
        movementAnimation.setFacingDir(dir);
        movementAnimation.setFrames(sprites.get(dir));
        movementAnimation.setCombatFrames(combatSprites.get(dir));
        movementAnimation.setDelay((long)(100 / this.speed));
        movementAnimation.setCombatDelay((long)(100 / this.combatSpeed));
    }
    public void updateAnimationDirection() {
        movementAnimation.setFacingDir(facingDir);
        movementAnimation.setFrames(sprites.get(facingDir));
        movementAnimation.setCombatFrames(combatSprites.get(facingDir));
        movementAnimation.setDelay((long)(100/this.speed));
        movementAnimation.setCombatDelay((long)(100 / this.combatSpeed));
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
        Color color1 = Color.WHITE;
        Color color2 = Color.BLACK;
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
        this.statController.update();
        this.combatController.update();
        this.recalculateBaseY();
    }
    private void recalculateBaseY() {
        this.baseY = ((this.getY() + ymap) + this.collision_baseY);
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

        if(!this.offScreen()) {
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
        /*//render with x/y in topleft of sprite
        gc.drawImage(
                movementAnimation.getImage(),
                (x + xmap) * getScale(),
                (y + ymap) * getScale(),
                width * getScale(),
                height * getScale()
        );*/
        if (this.combatController.isAttacking()) {
            gc.drawImage(
                    movementAnimation.getCombatImage(),
                    (x + xmap - width / 2.0) * getScale(),
                    (y + ymap - height / 2.0) * getScale(),
                    width * getScale(),
                    height * getScale()
            );
            return;
        }
        //render with x/y in center of sprite
        gc.drawImage(
                movementAnimation.getImage(),
                (x + xmap - width / 2.0) * getScale(),
                (y + ymap - height / 2.0) * getScale(),
                width * getScale(),
                height * getScale()
        );
    }

    public Text getNameText() {
        return this.nameText;
    }

    private void renderName(GraphicsContext gc) {
        //TODO: move name text into healthbar controller
        //  and convert into a more general namePlate class
        if (this.isTargeted && this.nameColorTimeline.getStatus() == Animation.Status.RUNNING) {
            gc.setFill(this.nameText.getFill());
            gc.setFont(this.nameText.getFont());
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

        //render new mini-map style collision
        if(!this.offScreen()) {
            gc.strokeOval(//this is kinda broken on entities right now
                    (this.x+ xmap) + this.collision_baseX,
                    (this.y + ymap) + this.collision_baseY,
                    collisionRadius,
                    collisionRadius-4
            );
            gc.strokeRect(
                    ((this.getX() + xmap + collisionXOffset)),
                    ((this.getY() + ymap) + collisionYOffset),
                    (this.collisionBoxWidth),
                    (this.collisionBoxHeight)
            );
        }
        gc.strokeRect(
                ((this.getX() + xmap + collisionXOffset))*getScale(),
            ((this.getY() + ymap + collisionYOffset))*getScale(),
            (this.collisionBoxWidth)*getScale(),
            (this.collisionBoxHeight)*getScale());
        gc.setStroke(Color.WHITE);
        gc.strokeRect(
                ((this.getXActual() + xmap - (this.width/2.0)))*getScale(),
                ((this.getYActual() + ymap - (this.height/2.0)))*getScale(),
                (this.width)*getScale(),
                (this.height)*getScale());
        /*
        gc.strokeRect( //show actual x/y point
                ((this.getX() + xmap))*getScale(),
                ((this.getY() + ymap))*getScale(),
                (2)*getScale(),
                (2)*getScale());
        // I don't remember what this is...
        gc.strokeOval(
                ((this.getX() + xmap) + this.collision_baseX)*getScale(),
                ((this.getY() + ymap) + this.collision_baseY)*getScale(),
                collisionRadius * getScale(),
                (collisionRadius-4)*getScale()
        );*/

        //render with x/y at topleft of oval xy tangents
        /*gc.strokeOval(
            (this.x + xmap + (this.width/2.0)-(collisionRadius/2.0))*getScale(),
            (this.y + ymap + (this.height/2.0)-(collisionRadius/2.0))*getScale(),
            collisionRadius * getScale(),
            collisionRadius * getScale()
        );*/
        //render with x/y at oval center
        /*
        gc.strokeOval(
                ((this.x + xmap + (width/2.0))-((width-collisionRadius)/2.0)-collisionRadius)*getScale(),
                ((this.y + ymap + (height/2.0))-((height-collisionRadius)/2.0) - collisionRadius)*getScale(),
                collisionRadius * getScale(),
                collisionRadius * getScale()
        );*/
    }

    public void teardown() {
        this.name = null;
        this.nameFont = null;
        this.nameText = null;
        this.spriteSheet = null;
        this.sprites = null;
        if (this.entityEffectController != null) {
            this.entityEffectController.teardown();
            this.entityEffectController = null;
        }

    }
}
