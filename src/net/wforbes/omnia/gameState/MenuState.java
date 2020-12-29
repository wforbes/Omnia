package net.wforbes.omnia.gameState;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.platformer.tileMap.Background;

import java.awt.*;

public class MenuState extends GameState {

    private GameStateManager gsm;
    private int lastPressTick;
    private int tickCount;
    private int currentChoice;
    private String[] options = {
            "Top-Down",
            "Platformer",
            "Info",
            "Quit"
    };
    private java.awt.Font titleFont, subTitleFont, subTitleFont2, font;
    private Font fxTitleFont, fxSubTitleFont, fxSubTitleFont2, fxFont;
    private Background bg;

    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;

        try {//load the background resource .gif file
            bg = new Background("/Backgrounds/menubg.gif", 1);
            bg.setVector(-0.1, 0);//move to the left at .1 pixels

            //font for Naturalist Engine title
            titleFont = new java.awt.Font("Century Gothic", java.awt.Font.PLAIN, 25);

            //font for version number
            subTitleFont = new java.awt.Font("Century Gothic", java.awt.Font.PLAIN, 10);

            //font for subModern presents
            subTitleFont2 = new java.awt.Font("Century Gothic", java.awt.Font.PLAIN, 10);

            //font info for everything else
            font = new java.awt.Font("Arial", java.awt.Font.PLAIN, 12);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public MenuState(GameStateManager gsm, String type) {
        if (!type.equals("fx"))
            return;

        this.gsm = gsm;

        try {//load the background resource .gif file
            bg = new Background("/Backgrounds/menubg.gif", 1, "fx");
            bg.setVector(-0.1 * OmniaFX.getScale(), 0);//move to the left at .1 pixels

            //font for Naturalist Engine title
            fxTitleFont = new Font("Century Gothic", 25);

            //font for version number
            fxSubTitleFont = new Font("Century Gothic", 10);

            //font for subModern presents
            fxSubTitleFont2 = new Font("Century Gothic", 10);

            //font info for everything else
            fxFont = new Font("Arial", 12);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        this.lastPressTick = 0;
        this.tickCount = 0;
        this.currentChoice = 0;
    }

    @Override
    public void tick() {
        bg.update();
        if (keyInputReady()) this.checkKeyInput();
        tickCount++;
    }

    @Override
    public void update() {
        bg.update();
        //if (keyInputReady()) this.checkKeyInput();
        //tickCount++;
    }

    private boolean keyInputReady() {
        int waitTicks = 20;
        return tickCount - lastPressTick > waitTicks || lastPressTick == 0;
    }

    private void checkKeyInput() {

        if(this.gsm.inputHandler.enter.isPressed() && keyInputReady()){
            select();
        }

        if(this.gsm.inputHandler.up.isPressed() && keyInputReady()){
            currentChoice--;
            if(currentChoice == -1){
                currentChoice = options.length - 1;
            }
            lastPressTick = tickCount;
        }

        if(this.gsm.inputHandler.down.isPressed() && keyInputReady()){
            currentChoice++;
            if(currentChoice == options.length){
                currentChoice = 0;
            }
            lastPressTick = tickCount;
        }

    }

    @Override
    public void render(GraphicsContext gc) {
        bg.render(gc);
        gc.setFill(Color.BLACK);
        gc.setFont(fxSubTitleFont2);
        gc.fillText("subModern studios presents:", 30, 60);
        gc.setFont(fxTitleFont);
        gc.fillText("Omnia Game Engine", 30, 85);
        gc.setFont(fxSubTitleFont);
        gc.fillText("alpha version 0.0.1", 225, 100);

        gc.setFont(fxFont);
        for(int i = 0; i < options.length; i++){
            if(i == currentChoice){
                gc.setFill(Color.YELLOW);
            }else{
                gc.setFill(Color.WHITE);
            }
            gc.fillText(options[i], 30, 135 + (20 * i));
        }
    }

    @Override
    public void render(Graphics2D graphics2D) {
        bg.draw(graphics2D);
        graphics2D.setColor(java.awt.Color.BLACK);
        graphics2D.setFont(subTitleFont2);
        graphics2D.drawString("subModern studios presents:", 30, 60);
        graphics2D.setFont(titleFont);
        graphics2D.drawString("Omnia Game Engine", 30, 85);
        graphics2D.setFont(subTitleFont);
        graphics2D.drawString("alpha version 0.0.1", 225, 100);

        graphics2D.setFont(font);
        for(int i = 0; i < options.length; i++){
            if(i == currentChoice){
                graphics2D.setColor(java.awt.Color.YELLOW);
            }else{
                graphics2D.setColor(java.awt.Color.WHITE);
            }
            graphics2D.drawString(options[i], 30, 135 + (20 * i));
        }
    }

    private void select() {
        if(currentChoice == 0){
            gsm.setState(GameStateManager.TOPDOWNSTATE);
        }
        if(currentChoice == 1){
            gsm.setState(GameStateManager.PLATFORMERSTATE);
        }
        if(currentChoice == 2){
            gsm.setState(GameStateManager.INFOSTATE);
        }
        if(currentChoice == 3){
            System.exit(0);
        }
    }
    public void reset() {}
    public void pause() {}
}
