package net.wforbes.entity;

import net.wforbes.game.InputHandler;
import net.wforbes.graphics.Colors;
import net.wforbes.graphics.Screen;
import net.wforbes.level.Level;

public class Player extends Mob{
    private InputHandler input;
    private String username;
    private int color = Colors.get(-1, 100, 555, 543);
    private int scale = 1;
    protected boolean isSwimming = false;
    private int tickCount = 0;

    public Player(Level level, int x, int y, InputHandler input, String username) {
        super(level, "Player", x, y, 1);
        this.input = input;
        this.username = username;
    }

    @Override
    public void tick() {
        checkMovement();
        tickCount++;
    }

    private void checkMovement() {

        int xa = 0;
        int ya = 0;

        if( input.up.isPressed() ){
            ya--;
        }
        if( input.down.isPressed() ){
            System.out.println("down is pressed");
            ya++;
        }
        if( input.left.isPressed() ){
            xa--;
        }
        if( input.right.isPressed() ){
            xa++;
        }
        if(xa != 0 || ya != 0){
            move(xa, ya);
            isMoving = true;
        }else{
            isMoving = false;
        }

        if(level.getTile(this.x >>3, this.y >>3).getId() == 3){
            isSwimming = true;
        }
        if(isSwimming && level.getTile(this.x >> 3, this.y >> 3).getId() != 3){
            isSwimming = false;
        }
    }

    @Override
    public void render(Screen screen) {
        int xTile = 0;
        int yTile = 9;
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
        int xOffset = x - modifier/2;
        int yOffset = y - modifier/2 - 4;

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
        screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale);//q2
        screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, color, flipTop, scale);//q1

        //this provides the animation progression through the (0, 26) tile on sprite_sheet
        //...each progressive "if" step changes the colors so that the illusion wading waves
        //    can be see around the Player. Modify the 60 if a different time rhythm is required,
        //    15 is used as a representative 1/4 of 60 because we are working with 4 shades
        //    of color given the grey scheme of the color class and each shade get's a quarter
        //    of the animation time.
        if(!isSwimming){
            //lower body
            screen.render(xOffset + (modifier * flipBottom), yOffset + modifier,
                    xTile + (yTile + 1) * 32, color, flipBottom, scale); //q3
            screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier,
                    (1 + xTile) + (1 + yTile) * 32, color, flipBottom, scale); //q4
        }

        //username!
        if(username != null){
            //full white colored username 10 pixels above the player
            //Font.render(username, screen, xOffset - ((username.length() -1 ) /2 * 8), yOffset - 10, Colors.get(-1, -1, -1, 555), 1);
        }
    }
}
