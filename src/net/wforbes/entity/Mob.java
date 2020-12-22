package net.wforbes.entity;

import net.wforbes.entity.movement.MovementController;
import net.wforbes.level.Level;
import net.wforbes.level.tile.Tile;

public abstract class Mob extends Entity{

    protected String name;
    protected double speed;
    protected int numSteps = 0;
    MovementController movementController;
    public boolean isMoving;
    protected int movingDir = 1; //0=north, 1=south, 2=west, 3=east
    protected int scale = 1;
    public boolean isSwimming = false;
    protected boolean canSwim;

    public Mob(Level level, String name, int x, int y, int speed){
        super(level);
        this.name = name;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.movementController = new MovementController(this);
    }

    public String getName(){return name;}

    public int getMovingDir(){return movingDir;}

    public void move(int xa, int ya){
        if(xa != 0 && ya != 0){
            moveDiagonal(xa, ya);
        }else{
            moveCardinal(xa, ya);
        }

        numSteps++;

        if(!hasCollided(xa, ya)){
            moveCoords(xa, ya);
        }
    }

    private void moveCardinal(int xa, int ya){
        if (ya < 0)
            movingDir = 0;
        if (ya > 0)
            movingDir = 1;
        if (xa < 0)
            movingDir = 2;
        if (xa > 0)
            movingDir = 3;
    }

    private void moveDiagonal(int xa, int ya){
        if (ya < 0 && xa  < 0)
            movingDir = 4;
        if (ya < 0 && xa > 0)
            movingDir = 5;
        if (ya > 0 && xa < 0)
            movingDir = 6;
        if (ya > 0 && xa > 0)
            movingDir = 7;
    }

    private void moveCoords(int xa, int ya){
        x += xa * speed;
        y += ya * speed;
    }

    public boolean hasCollided(int xa, int ya) {
        //collision box function
        int xMin = 0;
        int xMax = 7;
        int yMin = 0;
        int yMax = 7;

        //check top and bottom
        //System.out.println(this.getName() + " is checking top/bottom");
        for(int x = xMin; x < xMax; x++){
            if(isSolidTile(xa, ya, x, yMin) || isSolidTile(xa, ya, x, yMax)){
                return true;
            }
            if((!canSwim && isWaterTile(xa, ya, x, yMin))
                    || (!canSwim && isWaterTile(xa, ya, x, yMax))) {
                return true;
            }
            //System.out.println("top/bottom");
            if(isOccupied(xa, ya, x, yMin) || isOccupied(xa, ya, x, yMax)) {
                return true;
            }
        }
        //System.out.println("====================");
        //System.out.println();
        //check left and right
        for(int y = yMin; y < yMax; y++){
            if(isSolidTile(xa, ya, xMin, y) || isSolidTile(xa, ya, xMax, y)){
                return true;
            }
            if((!canSwim && isWaterTile(xa, ya, xMin, y))
                    || !canSwim && isWaterTile(xa, ya, xMax, y)) {
                return true;
            }
            //System.out.println("left/right");
            if(isOccupied(xa, ya, xMin, y) || isOccupied(xa, ya, xMax, y)) {
                return true;
            }
        }
        return false;
    }

    protected boolean isOccupied(int xa, int ya, int x, int y) {
        //System.out.println(xa);
        //System.out.println(ya);
        //System.out.println(x);
        //System.out.println(y);
        int xMin = 0;
        int xMax = 7;
        int yMin = 0;
        int yMax = 7;
        //System.out.println("Number of entities in level: " + level.entities.size());
        //System.out.println("this.name: " + this.name);
        //System.out.println("xa+x+this.x:"+(xa+x+this.x));
        //System.out.println("ya+y+this.y:"+(ya+y+this.y));
        int w = 0;
        for(Entity e : level.entities) {
            //System.out.println("===================");
            //System.out.println("Level Entity #" + w);

            if(e.getName() != this.name){
                //System.out.println("you = " + e.getName());
                //System.out.println("Your X: " + e.x);
                //System.out.println("Your Y: " + e.y);
                //System.out.println("me = " + this.name);
                //System.out.println("My X coord: " + this.x);
                //System.out.println("My Y coord: " + this.y);

                for(int xi = xMin; xi < xMax; xi++) {//top/bottom
                    for(int yi = yMin; yi < yMax; yi++) {//left/right
                        //System.out.println("xa+x+this.x:"+(xa+x+this.x));
                        //System.out.println("ya+y+this.y:"+(ya+y+this.y));
                        if ((e.x + xi) == (xa + x + this.x) && (e.y + yi) == (ya + y + this.y)) {
                            /*System.out.println("this.name = " + this.name);
                            if(this.name == "ghosty") {
                                System.out.println("colliding with: " + e.getName());
                            }*/
                            //System.out.println(xi + " = X test coord iterator (xI)");
                            //System.out.println(x + " = X test coord bound (x)");
                            //System.out.println(e.x + " = X enemy coord (ex)");
                            //System.out.println(xa + " = X advancement coord (xa)");
                            //System.out.println(this.x + " = my X coord (this.x)");
                            //System.out.println("Checking enemy (x,y) value: " +
                            //        (e.x + xi) + "/"+ (e.y + yi));

                            //System.out.println(yi + "= Y test coord iterator (yI)");
                            //System.out.println(y + " = Y test coord bound (y)");
                            //System.out.println(e.y + " = Y enemy coord (ey)");
                            //System.out.println(ya + " = Y advancement coord (ya)");
                            //System.out.println(this.y + " = my Y coord (this.y)");
                            //System.out.println("Against my (x,y) value: " +
                            //        (xa + x + this.x) + "/" + (ya + y + this.y));

                            return true;
                        } else {
                            if ((e.x + xi) == (xa + x + this.x)) {
                                //System.out.println("Same X");
                            }
                            if ((e.y + yi) == (ya + y + this.y)) {
                                //System.out.println("Same Y");
                            }
                        }
                    }
                }
                //System.out.println("===================");
            } else {
                //System.out.println("me = " + e.getName());
                //System.out.println("My X coord: " + this.x);
                //System.out.println("My Y coord: " + this.y);
                //System.out.println("My XA: " + xa);
                //System.out.println("My YA: " + ya);
                //System.out.println("My X test coord bound: " + xa);
                //System.out.println("My Y test coord bound: " + ya);
            }
            w++;
        }
        return false;
    }

    protected boolean isSolidTile(int xa, int ya, int x, int y)
    {
        if(level == null) return false;

        Tile lastTile = level.getTile((this.x + x ) >> 3, (this.y + y) >> 3);
        Tile newTile = level.getTile((this.x + x + xa) >> 3, (this.y + y + ya) >> 3);

        if(!lastTile.equals(newTile) && newTile.isSolid()){
            return true;
        }
        return false;
    }

    protected boolean isWaterTile(int xa, int ya, int x, int y) {
        if(level == null) return false;

        Tile lastTile = level.getTile((this.x + x ) >> 3, (this.y + y) >> 3);
        Tile newTile = level.getTile((this.x + x + xa) >> 3, (this.y + y + ya) >> 3);

        if(!lastTile.equals(newTile) && newTile.isWater()){
            return true;
        }
        return false;
    }
}
