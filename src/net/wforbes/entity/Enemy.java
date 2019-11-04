package net.wforbes.entity;

import net.wforbes.graphics.Colors;
import net.wforbes.graphics.Screen;
import net.wforbes.level.Level;

import java.util.Random;

public class Enemy extends Mob{

    private int color = Colors.get(-1, 111, 400, 444);
    private int scale = 1;
    protected boolean isSwimming = false;
    private long waitCount = 0;
    private int tickCount = 0;
    Random gen = new Random();
    int a, xa, ya;

    public Enemy(Level level, int x, int y) {
        super(level, "Enemy", x, y, 1);
        int tickCount = 0;
    }

    private int[] getRandomMove(){
        int a = Math.abs(gen.nextInt()) % 8;
        int nxa = xa, nya = ya;
        if(a == 0)nya--;
        if(a == 1)nya++;
        if(a == 2)nxa--;
        if(a == 3)nxa++;
        if(a == 4){nxa--; nya--;}
        if(a == 5){nxa++; nya--;}
        if(a == 6){nxa--; nya++;}
        if(a == 7){ nxa++; nya++;}
        return new int[]{nxa, nya, a};
    }

    private boolean isInIntArray(int[] arr, int a){
        for(int el : arr){
            if(el == a){
                return true;
            }
        }
        return false;
    }
    //TODO: add consideration for isCollided to turn
    //  an accessible direction for a few movements
    //  to get away from the obstruction
    private int[] redirectMovement(int[] xya){
        int[] cardinals = new int[]{0, 1, 2, 3};
        int[] diagonals = new int[]{4, 5, 6, 7};
        //if cardinal, check it's opposite direction
        if(isInIntArray(cardinals, xya[2])){
            //find opposite direction
            if(xya[2] == 0 || xya[2] == 2){
                int opposite = xya[2]++;
            }
            //if(!hasCollided(opposite)){

            //}
        }
        //  if its not colliding, go the opposite direction
        //  if it is colliding, check a perpendicular direction (0/1..2/3)
        //      if perpendicular is colliding, check it's opposite
        //          if opposite perpendicular is colliding, check

        //if diagonal, check it's component directions
        //  if one is colliding, go the opposite direction of it
        //  if both are colliding, go the opposite direction of the diagonal
        //
        return new int[0];
    }

    //TODO: add interpolation for a smooth walking motion

    private void randomMovement(){
        if(waitCount == 20 || waitCount == 0){
            int[] xya = getRandomMove();
            /*
            if(this.hasCollided(xya[0], xya[1])){
                xya = redirectMovement(xya);
            }*/
            move(xya[0], xya[1]);
            isMoving = true;
            waitCount = 1;
        }else{
            isMoving = false;
        }

        if(level.getTile(this.x >>3, this.y >>3).getId() == 3){
            isSwimming = true;
        }
        if(isSwimming && level.getTile(this.x >> 3, this.y >> 3).getId() != 3){
            isSwimming = false;
        }
        waitCount++;
    }

    @Override
    public void tick() {
        randomMovement();
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
    }
}
