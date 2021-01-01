package net.wforbes.omnia.menu;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameState.GameStateManager;
import net.wforbes.omnia.gameState.MenuState;
import net.wforbes.omnia.menu.background.MenuBackground;
import org.jfree.fx.FXGraphics2D;

public class MainMenu extends Menu {
    private MenuState state;
    private MenuBackground bg;
    private FXGraphics2D fxGraphics2D;

    private Font titleFont, subTitleFont, subTitleFont2, font;
    private double fxScale;
    private String[] options = {
            "Top-Down",
            "Platformer",
            "Info",
            "Quit"
    };

    public MainMenu(MenuState state) {
        this.state = state;
        this.fxScale = OmniaFX.getScale();
        try {//load the background resource .gif file
            bg = new MenuBackground("/Backgrounds/menubg.gif");
        } catch(Exception e) {
            e.printStackTrace();
        }
        bg.setVector(-0.1 * OmniaFX.getScale(), 0);//move to the left at .1 pixels

        //font for Naturalist Engine title
        titleFont = new Font("Century Gothic", 25 * fxScale);

        //font for version number
        subTitleFont = new Font("Century Gothic", 10 * fxScale);

        //font for subModern presents
        subTitleFont2 = new Font("Century Gothic", 10 * fxScale);

        //font info for everything else
        font = new Font("Arial", 12 * fxScale);
    }

    public void init() {
        this.lastPressTick = waitTicks;
        this.tickCount = 0;
        this.currentChoice = 0;
    }

    public void update() {
        bg.update();
        if (keyInputReady()) this.checkKeyInput();
        tickCount++;
    }

    @Override
    void checkKeyInput() {
        if(state.gsm.isKeyDown(KeyCode.ENTER) && keyInputReady()){
            select();
        }

        if(state.gsm.isKeyDown(KeyCode.UP) && keyInputReady()){
            currentChoice--;
            if(currentChoice == -1){
                currentChoice = options.length - 1;
            }
            lastPressTick = tickCount;
        }

        if(state.gsm.isKeyDown(KeyCode.DOWN) && keyInputReady()){
            currentChoice++;
            if(currentChoice == options.length){
                currentChoice = 0;
            }
            lastPressTick = tickCount;
        }
    }

    void select() {
        if(currentChoice == 0){

            state.gsm.setState(GameStateManager.TOPDOWNSTATE);
        }
        if(currentChoice == 1){
            state.gsm.setState(GameStateManager.PLATFORMERSTATE);
        }
        if(currentChoice == 2){
            state.gsm.setState(GameStateManager.INFOSTATE);
        }
        if(currentChoice == 3){
            System.exit(0);
        }
    }

    public void render(GraphicsContext gc) {
        bg.render(gc);
        gc.setFill(Color.BLACK);
        gc.setFont(subTitleFont2);
        gc.fillText("subModern studios presents:", 30 * fxScale, 60 * fxScale);
        gc.setFont(titleFont);
        gc.fillText("Omnia Game Engine", 30 * fxScale, 85 * fxScale);
        gc.setFont(subTitleFont);
        gc.fillText("alpha version 0.0.1", 225 * fxScale, 100 * fxScale);

        gc.setFont(font);
        for(int i = 0; i < options.length; i++){
            if(i == currentChoice){
                gc.setFill(Color.YELLOW);
            }else{
                gc.setFill(Color.WHITE);
            }
            gc.fillText(options[i], 30 * fxScale, (135 * fxScale) + (20 * fxScale * i) );
        }
    }
}
