package net.wforbes.omnia.overworld.entity.projectile;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.entity.Mob;
import net.wforbes.omnia.overworld.entity.Player;
import net.wforbes.omnia.overworld.world.area.tile.TileMap;
import net.wforbes.omnia.u.W;

import static net.wforbes.omnia.gameFX.OmniaFX.getScale;

public class Projectile {

    private String id;
    private Mob owner;
    private int direction;
    //private Point2D location;
    private double x;
    private double y;
    private double xmap;
    private double ymap;
    private double speed;
    private boolean outOfBounds = false;
    private boolean isColliding = false;
    private Entity collidedWith;
    private int collisionRadius;

    private int width = 4;
    private int height = 4;
    private boolean updated;
    private boolean isOffscreen;
    private boolean markedForTeardown;

    public Projectile(Mob owner) {
        this.owner = owner;
        this.speed = 2;
    }

    public Projectile(Mob owner, int width, int height) {
        this.owner = owner;
        this.width = width;
        this.height = height;
    }

    public void launch() {
        this.setPosition(
            this.owner.getLocationPoint().getX() - (this.owner.getWidth()/2.0),
            this.owner.getLocationPoint().getY() - (this.owner.getHeight()/2.0)
        );
        this.direction = this.owner.getFacingDir();
        this.update();
        //0-north, 1-south, 2-west, 3-east, 4-nw, 5-ne, 6-sw, 7-se

    }
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void update() {
        double xa = 0;
        double ya = 0;
        switch (this.direction) {
            case 0://north
                --ya;
                break;
            case 1://south
                ++ya;
                break;
            case 2://west
                --xa;
                break;
            case 3://east
                ++xa;
                break;
            case 4://northwest
                --ya;
                --xa;
                break;
            case 5://northeast
                --ya;
                ++xa;
                break;
            case 6://southwest
                ++ya;
                --xa;
                break;
            case 7://southeast
                ++ya;
                ++xa;
                break;
        }
        if (
            this.checkCollision(xa, ya)
            || this.checkOffMap()
        ) {
            this.markedForTeardown = true;
            return;
        }
        this.isOffscreen = this.offScreen();
        this.move(xa, ya);
    }

    private boolean checkCollision(double xa, double ya) {
        for(Entity e : owner.gameState.world.area.entities) {
            if(!e.getName().equals(this.owner.getName())) {
                double xDist = (this.x+xa - (e.getX() - ((Mob)e).getWidth()/2.0));
                double yDist = (this.y+ya - (e.getY() - ((Mob)e).getHeight()/2.0));
                /* debuggggg
                if (e.getName().equals("SOME NAME")) {
                    System.out.println("dist circle: " + Math.sqrt((xDist*xDist) + (yDist*yDist)));
                    System.out.println("colradi: " + (collisionRadius*2.0+e.getCollisionRadius()*2.0));
                }*/
                if(Math.sqrt((xDist*xDist) + (yDist*yDist)) <= (collisionRadius/2.0+e.getCollisionRadius()/2.0)) {
                    System.out.println("Collided with " + e.getName());
                    //TODO: notify entity (or area?) about collision
                    return true;
                }
            }
        }
        //No collision found
        return false;
    }

    private boolean checkOffMap() {
        TileMap tileMap = this.owner.gameState.world.area.getTileMap();
        boolean offmap = x + xmap < 0;
        if (x+xmap > tileMap.getWidth()) offmap = true;
        if (y+ymap < 0) offmap = true;
        if (y+ymap > tileMap.getHeight()) offmap = true;
        if (offmap) this.markedForTeardown = true;
        return offmap;
    }

    private boolean offScreen() {
        return x + xmap + width < 0 ||
                x + xmap - width/2.5 > OmniaFX.getWidth() ||
                y + ymap + height < 0 ||
                y + ymap - height/2.5 > OmniaFX.getHeight();
    }

    private void move(double xa, double ya) {
        x += xa * speed;
        y += ya * speed;
    }

    public void teardown() {
        this.speed = 0;
    }

    public int getCollisionRadius() { return this.collisionRadius; }
    public void render(GraphicsContext gc) {
        this.refreshMapPosition();
        if(isOffscreen) return;
        //o("rendering at: " + x + " " + y);
        gc.setFill(Color.RED);
        gc.fillRect(
            (x + xmap - width / 2.0) * getScale(),
            (y + ymap - height / 2.0) * getScale(),
            width * getScale(),
            height * getScale()
        );
        gc.setFill(null);
    }

    private void refreshMapPosition() {
        xmap = owner.gameState.world.area.getTileMap().getX();
        ymap = owner.gameState.world.area.getTileMap().getY();
    }

    public boolean isMarkedForTeardown() {
        return markedForTeardown;
    }
}
