package net.wforbes.omnia.topDown.entity;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import net.wforbes.omnia.gameState.TopDownState;
import net.wforbes.omnia.input.InputHandler;
import net.wforbes.omnia.topDown.graphics.Colors;
import net.wforbes.omnia.topDown.graphics.Screen;
import net.wforbes.omnia.topDown.level.Level;

public class Player extends Mob{
    private TopDownState gameState;
    private InputHandler inputHandler;
    private String name;
    private int scale = 1;
    protected boolean isSwimming = false;
    private int lastInputCommandTick = 0;

    /*
    public Player(Level level, String username, int x, int y, TopDownState gameState) {
        super(level, username, x, y, 1);
        this.gameState = gameState;
        this.inputHandler = gameState.gsm.inputHandler;
        this.username = username;
        this.canSwim = true;
    }*/

    public Player(Level level, String name, Point2D startPos, TopDownState gameState) {
        super(level, name, startPos,1);
        this.gameState = gameState;
        this.inputHandler = gameState.gsm.inputHandler;
        this.name = name;
        this.canSwim = true;
        this.setSpriteLoc(new Point2D(0, 13));
        this.setSpriteColor(Colors.get(-1, 100, 005, 543));
        this.setNameColor(Colors.get(-1, -1, -1, 005));
    }

    public String getName(){
        return this.name;
    }

    private void checkCommands() {
        if (gameState.gsm.usingFx) {
            /*
            if (gameState.gsm.isKeyDown(KeyCode.ESCAPE) && chatWindowIsOpen() && !gameState.isPaused()) {
                this.lastInputCommandTick = this.tickCount;
                //gameState.gui.closeChatWindow();
                //TODO: unfocus chat window?
            }*/

            if (gameState.gsm.isKeyDown(KeyCode.ESCAPE) && pauseIsReady() && keyInputIsReady()) {
                this.lastInputCommandTick = this.tickCount;

                if(gameState.gui.getComponentHasFocus()) {
                    gameState.gsm.gameController.gameCanvas.requestFocus();
                } else {
                    gameState.pause();
                }
            }

            if (gameState.gsm.isKeyDown(KeyCode.ENTER) && !gameState.isPaused()
                && this.keyInputIsReady()) {
                this.lastInputCommandTick = this.tickCount;
                gameState.gui.chatWindowController.getChatField().requestFocus();
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

    public String simpleChatResponse(String type) {
        return "";
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
    public void tick() {
        checkCommands();
        checkMovement();
        super.tick();
    }

    @Override
    public void render(Screen screen) {
        super.render(screen);
    }
}
