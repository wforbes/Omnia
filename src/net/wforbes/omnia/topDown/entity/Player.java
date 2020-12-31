package net.wforbes.omnia.topDown.entity;

import javafx.scene.input.KeyCode;
import net.wforbes.omnia.gameState.TopDownState;
import net.wforbes.omnia.input.InputHandler;
import net.wforbes.omnia.topDown.graphics.Colors;
import net.wforbes.omnia.topDown.graphics.Screen;
import net.wforbes.omnia.topDown.gui.Font;
import net.wforbes.omnia.topDown.level.Level;

public class Player extends Mob{
    private TopDownState gameState;
    private InputHandler inputHandler;
    private String username;
    private int color = Colors.get(-1, 100, 555, 543);
    private int scale = 1;
    public int xOffset;
    public int yOffset;
    protected boolean isSwimming = false;
    private int tickCount = 0;
    private int lastInputCommandTick = 0;
    private boolean chatInputIsOpen = false;

    public Player(Level level, int x, int y, String username, TopDownState gameState) {
        super(level, username, x, y, 1);
        this.gameState = gameState;
        this.inputHandler = gameState.gsm.inputHandler;
        this.username = username;
        this.canSwim = true;
    }

    public String getName(){
        return this.username;
    }

    @Override
    public void tick() {
        checkCommands();
        checkMovement();
        tickCount++;
    }

    private void checkCommands() {
        if (gameState.gsm.usingFx) {
            if(gameState.gsm.isKeyDown(KeyCode.ESCAPE) && pauseIsReady()) {
                gameState.pause();
            }
        } else {
            if (inputHandler.esc.isPressed() && pauseIsReady()) {
                inputHandler.resetKeys();
                gameState.pause();
            }

            if (inputHandler.enter.isPressed() && keyInputIsReady()) {
                //this.lastInputCommandTick = this.tickCount;
                //this.chatInputIsOpen = true;
                //gameState.openChatInput();
            }
        }
    }
    private boolean pauseIsReady() {
        return gameState.tickCount - gameState.lastUnpauseTick > 20;
    }

    private boolean keyInputIsReady() {
        return gameState.tickCount - this.lastInputCommandTick > 20;
    }

    private void checkMovement() {
        int xa = 0;
        int ya = 0;
        if (gameState.gsm.usingFx) {
            if (gameState.gsm.isKeyDown(KeyCode.UP) || gameState.gsm.isKeyDown(KeyCode.W)) {
                ya--;
            }
            if (gameState.gsm.isKeyDown(KeyCode.DOWN) || gameState.gsm.isKeyDown(KeyCode.S)) {
                ya++;
            }
            if (gameState.gsm.isKeyDown(KeyCode.LEFT) || gameState.gsm.isKeyDown(KeyCode.A)) {
                xa--;
            }
            if (gameState.gsm.isKeyDown(KeyCode.RIGHT) || gameState.gsm.isKeyDown(KeyCode.D)) {
                xa++;
            }
        } else {
            if (inputHandler.up.isPressed() || inputHandler.w.isPressed()) {
                ya--;
            }
            if (inputHandler.down.isPressed() || inputHandler.s.isPressed()) {
                ya++;
            }
            if (inputHandler.left.isPressed() || inputHandler.a.isPressed()) {
                xa--;
            }
            if (inputHandler.right.isPressed() || inputHandler.d.isPressed()) {
                xa++;
            }
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
            int xl = username.length() * 8;
            int unl = username.length();
            int unlr = (username.length() % 2 == 0) ? 0 : 1;
            //(username.length() /2) + username.length()+6
            Font.render(username, screen, xOffset - (xl - (xl/2) - unl - unlr), yOffset - 10, Colors.get(-1, -1, -1, 555), 1);
        }
    }
}
