package net.wforbes.entity;

import net.wforbes.graphics.Colors;
import net.wforbes.graphics.Screen;
import net.wforbes.gui.Font;
import net.wforbes.level.Level;

public class Enemy extends Mob{

    private int color = Colors.get(-1, 111, 400, 444);
    private int scale = 1;
    private int tickCount = 0;


    public Enemy(Level level, int x, int y, String name) {
        super(level, name, x, y, 1);
        int tickCount = 0;
        this.canSwim = false;
    }

    @Override
    public void tick() {
        this.movementController.tick();
        this.getMovingDir();
    }

    private boolean isInIntArray(int[] arr, int a){
        for(int el : arr){
            if(el == a){
                return true;
            }
        }
        return false;
    }

    //TODO: This is copy/pasted from Player, find a solution without code duplication
    @Override
    public void render(Screen screen) {
        int xTile = 0;
        int yTile = 7;
        int walkingSpeed = 3;
        //flipTop/Bottom variables: 0 or 1, used to decide whether or not to flip
        int flipTop = (numSteps >> walkingSpeed) & 1;
        int flipBottom = (numSteps >> walkingSpeed) & 1;

        //judging by which way the player is facing,
        //set xTile in order to move the renderer's focus to the correct sprite
        //set flipTop in order to flip the top half of the sprite's
        if(movingDir == 1){
            xTile += 2;
        }else if(1 < movingDir && movingDir <= 3){
            xTile += 4 + ((numSteps >> walkingSpeed) & 1) * 2;
            flipTop = flipBottom = (movingDir - 1) % 2;
        }else if(movingDir == 4 || movingDir == 5)
        {
            xTile += 8 + ((numSteps >> walkingSpeed) & 1) * 2;
            flipTop = flipBottom = (movingDir - 3) % 2;
        }else if(movingDir == 6 || movingDir ==7)
        {
            xTile += 12 + ((numSteps >> walkingSpeed) & 1) * 2;
            flipTop = flipBottom = (movingDir - 5) % 2;
        }

        int modifier = 8 * scale;
        int xOffset = x - modifier/2;
        int yOffset = y - modifier/2 - 4;

        //this provides the animation progression through the (0, 26) tile on sprite_sheet
        //...each progressive "if" step changes the colors so that the illusion wading waves
        //    can be see around the Player. Modify the 60 if a different time rhythm is required,
        //    15 is used as a representative 1/4 of 60 because we are working with 4 shades
        //    of color given the grey scheme of the color class and each shade get's a quarter
        //    of the animation time.
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

        if(!isSwimming){
            //lower body
            screen.render(xOffset + (modifier * flipBottom), yOffset + modifier,
                    xTile + (yTile + 1) * 32, color, flipBottom, scale); //q3
            screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier,
                    (1+ xTile) + (1 +yTile) * 32, color, flipBottom, scale); //q4
        }

        //username!
        if(this.name != null){

            int xl = name.length() * 8;
            int nl = name.length();
            int nlr = (name.length() % 2 == 0) ? 0 : 1; //

            //full white colored name 10 pixels above the enemy
            Font.render(name, screen, xOffset - (xl - (xl/2) - nl - nlr), yOffset - 10, Colors.get(-1, -1, -1, 300), 1);
        }
    }
}
