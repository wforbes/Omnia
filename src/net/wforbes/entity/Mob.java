package net.wforbes.entity;

import net.wforbes.level.Level;
import net.wforbes.level.tile.Tile;

public abstract class Mob extends Entity{

    protected String name;
    protected double speed;
    protected int numSteps = 0;
    protected boolean isMoving;
    protected int movingDir = 1; //0=north, 1=south, 2=west, 3=east
    protected int scale = 1;

    public Mob(Level level, String name, int x, int y, int speed){
        super(level);
        this.name = name;
        this.x = x;
        this.y = y;
        this.speed = speed;
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

    //TODO: Double check the logic of these repeated loops. Are they correct? Can they be combined?
    public boolean hasCollided(int xa, int ya) {
        //collision box function
        int xMin = 0;
        int xMax = 7;
        int yMin = 3;
        int yMax = 7;
        for(int x = xMin; x < xMax; x++){//loop from top left to top right collision point
            if(isSolidTile(xa, ya, x, yMin)){
                return true;
            }
        }
        for(int x = xMin; x < xMax; x++){//loop from bottom left to bottom right collision point
            if(isSolidTile(xa, ya, x, yMax)){
                return true;
            }
        }
        for(int y = yMin; y < yMax; y++){//loop from top left to bottom left collision point
            if(isSolidTile(xa, ya, xMin, y)){
                return true;
            }

        }
        for(int y = yMin; y < yMax; y++){//loop from top right to bottom right collision point
            if(isSolidTile(xa, ya, xMax, y)){
                return true;
            }
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
}
