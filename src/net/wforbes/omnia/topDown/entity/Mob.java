package net.wforbes.omnia.topDown.entity;

import javafx.geometry.Point2D;
import net.wforbes.omnia.topDown.entity.dialog.DialogController;
import net.wforbes.omnia.topDown.entity.movement.MovementController;
import net.wforbes.omnia.topDown.graphics.Colors;
import net.wforbes.omnia.topDown.graphics.Screen;
import net.wforbes.omnia.topDown.gui.Font;
import net.wforbes.omnia.topDown.level.Level;
import net.wforbes.omnia.topDown.level.tile.Tile;

public abstract class Mob extends Entity{

    protected String name;
    private Point2D spriteLoc;
    public int xOffset;
    public int yOffset;
    protected int spriteColor;
    protected int nameColor;
    protected int tickCount = 0;
    protected double speed;
    protected int numSteps = 0;
    MovementController movementController;
    public boolean isMoving;
    protected int movingDir = 1; //0=north, 1=south, 2=west, 3=east
    protected int scale = 1;
    public boolean isSwimming = false;
    protected boolean canSwim;

    /*
    public Mob(Level level, String name, int x, int y, int speed){
        super(level);
        this.name = name;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.movementController = new MovementController(this);
    }
    */

    public Mob(Level level, String name, Point2D startPos, int speed){
        super(level);
        this.name = name;
        this.x = (int)startPos.getX();
        this.y = (int)startPos.getY();
        this.speed = speed;
        this.movementController = new MovementController(this);
    }

    //public abstract String chat(String type);

    protected void setSpriteLoc(Point2D spriteLoc) {
        this.spriteLoc = spriteLoc;
    }

    protected void setSpriteColor(int color) {
        this.spriteColor = color;
    }

    protected void setNameColor(int color) {
        this.nameColor = color;
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

    public void tick() {
        tickCount++;
    }

    public void render(Screen screen) {
        int xTile = (int)this.spriteLoc.getX();
        int yTile = (int)this.spriteLoc.getY();
        int walkingSpeed = 3;
        //flipTop/Bottom variables: 0 or 1,
        // used to decide whether or not to flip the sprite to emulate walking
        int flipTop = (numSteps >> walkingSpeed) & 1;
        int flipBottom = (numSteps >> walkingSpeed) & 1;

        if(movingDir == 1){//down
            xTile += 2;
        }else if(1 < movingDir && movingDir <= 3){//left2, right3
            xTile += 4 + ((numSteps >> walkingSpeed) & 1) * 2;
            flipTop = flipBottom = (movingDir - 1) % 2;
        }else if(movingDir == 4 || movingDir == 5)//upleft, upright
        {
            xTile += 8 + ((numSteps >> walkingSpeed) & 1) * 2;
            flipTop = flipBottom = (movingDir - 3) % 2;
        }else if(movingDir == 6 || movingDir ==7)//downleft, downright
        {
            xTile += 12 + ((numSteps >> walkingSpeed) & 1) * 2;
            flipTop = flipBottom = (movingDir - 5) % 2;
        }

        int modifier = 8 * scale;
        xOffset = x - modifier/2;
        yOffset = y - modifier/2 - 4;

        if(isSwimming){
            int waterColor = 0;
            yOffset += 4; //half the size of body height (8)
            if(tickCount % 60 < 15){
                yOffset -= 1; //to implement bobbing within the water
                waterColor = Colors.get(-1, -1, 225, -1);
            }else if(15 <= tickCount % 60 && tickCount % 60 < 30){
                waterColor = Colors.get( -1, 225, 115, -1);
            }else if(30 <= tickCount % 60 && tickCount % 60 < 45){
                waterColor = Colors.get( -1, 115, -1, 225);
            }else{
                waterColor = Colors.get( -1, 225, 115, -1);
            }
            screen.render(xOffset, yOffset + 3, 0 + 26 * 32, waterColor, 0x00, 1);
            screen.render(xOffset + 8, yOffset + 3, 0 + 26 * 32, waterColor, 0x01, 1);
        }
        //upper body
        screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, spriteColor, flipTop, scale);//q2
        screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, spriteColor, flipTop, scale);//q1

        //this provides the animation progression through the (0, 26) tile on sprite_sheet
        //...each progressive "if" step changes the colors so that the illusion wading waves
        //    can be see around the Player. Modify the 60 if a different time rhythm is required,
        //    15 is used as a representative 1/4 of 60 because we are working with 4 shades
        //    of color given the grey scheme of the color class and each shade get's a quarter
        //    of the animation time.
        if(!isSwimming){
            //lower body
            screen.render(xOffset + (modifier * flipBottom), yOffset + modifier,
                    xTile + (yTile + 1) * 32, spriteColor, flipBottom, scale); //q3
            screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier,
                    (1 + xTile) + (1 + yTile) * 32, spriteColor, flipBottom, scale); //q4
        }

        //username!
        if(name != null){
            //full white colored username 10 pixels above the player
            int xl = name.length() * 8;
            int unl = name.length();
            int unlr = (name.length() % 2 == 0) ? 0 : 1;
            //(username.length() /2) + username.length()+6
            Font.render(name, screen, xOffset - (xl - (xl/2) - unl - unlr), yOffset - 10, nameColor, 1);
        }
    }
}
