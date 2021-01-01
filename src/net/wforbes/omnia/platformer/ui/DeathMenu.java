package net.wforbes.omnia.platformer.ui;

import javafx.scene.input.KeyCode;
import net.wforbes.omnia.game.Game;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameState.GameStateManager;
import net.wforbes.omnia.gameState.PlatformerState;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.font.FontRenderContext;

public class DeathMenu {

    private boolean visible;
    private Font headingFont;
    private String heading;
    private Font optionsFont;
    private String[] options;
    private PlatformerState gameState;
    private int lastPressTick = 0;
    private int tickCount = 0;
    private int currentChoice = 0;


    public DeathMenu(PlatformerState gameState) {
        this.gameState = gameState;
        visible = false;
        headingFont = new Font("Century Gothic", Font.PLAIN, 20);
        heading = "You died!";

        optionsFont = new Font("Century Gothic", Font.PLAIN, 14);
        options = new String[]{"Try Again", "Return to Menu", "Quit Game"};
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void show() {
        this.visible = true;
    }

    public void hide() {
        this.visible = false;
    }

    public void tick() {
        if (keyInputReady()) this.checkKeyInput();
        tickCount++;
    }

    private boolean keyInputReady() {
        int waitTicks = 20;
        return tickCount - lastPressTick > waitTicks || lastPressTick == 0;
    }

    private void checkKeyInput() {
        if (gameState.gsm.usingFx) {
            if(gameState.gsm.isKeyDown(KeyCode.ENTER) && keyInputReady()){
                select();
                lastPressTick = tickCount;
            }

            if(gameState.gsm.isKeyDown(KeyCode.UP) && keyInputReady()){
                currentChoice--;
                if(currentChoice == -1){
                    currentChoice = options.length - 1;
                }
                lastPressTick = tickCount;
            }

            if(gameState.gsm.isKeyDown(KeyCode.DOWN) && keyInputReady()){
                currentChoice++;
                if(currentChoice == options.length){
                    currentChoice = 0;
                }
                lastPressTick = tickCount;
            }
        } else {
            if(gameState.gsm.inputHandler.enter.isPressed() && keyInputReady()){
                select();
                lastPressTick = tickCount;
            }

            if(gameState.gsm.inputHandler.up.isPressed() && keyInputReady()){
                currentChoice--;
                if(currentChoice == -1){
                    currentChoice = options.length - 1;
                }
                lastPressTick = tickCount;
            }

            if(gameState.gsm.inputHandler.down.isPressed() && keyInputReady()){
                currentChoice++;
                if(currentChoice == options.length){
                    currentChoice = 0;
                }
                lastPressTick = tickCount;
            }
        }

    }

    private void select() {
        //gameState.gsm.inputHandler.resetKeys(); //To avoid double presses
        if(currentChoice == 0){
            gameState.gsm.resetState(GameStateManager.PLATFORMERSTATE);
        }
        if(currentChoice == 1){
            gameState.gsm.setState(GameStateManager.MENUSTATE);
        }
        if(currentChoice == 2){
            System.exit(0);
        }
    }

    public void render(FXGraphics2D g) {

        FontRenderContext context = g.getFontRenderContext();

        g.setFont(headingFont);
        g.setFont(new java.awt.Font("Century Gothic", java.awt.Font.PLAIN, 20));
        g.setColor(java.awt.Color.WHITE);
        int headingXPos = OmniaFX.getWidth() / 2 - (int)headingFont.getStringBounds(heading, context).getWidth() / 2;
        int headingYPos = OmniaFX.getHeight() / 2 - (int)headingFont.getStringBounds(heading, context).getHeight() / 2;
        g.drawString(heading, headingXPos, headingYPos);

        g.setFont(optionsFont);
        int optionsXPos = OmniaFX.getWidth() / 2 - (int)headingFont.getStringBounds(heading, context).getWidth() / 2;
        int optionsPadding = (int)optionsFont.getStringBounds(options[0], context).getHeight();
        int optionsYPosStart = headingYPos + (int)optionsFont.getStringBounds(options[0], context).getHeight();
        int padSum = 0;
        for (int i = 0; i < options.length; i++) {
            if(i == currentChoice){
                g.setColor(java.awt.Color.YELLOW);
            }else{
                g.setColor(java.awt.Color.WHITE);
            }
            g.drawString(options[i], optionsXPos, optionsYPosStart + padSum);
            padSum = optionsPadding + ( optionsPadding * i+1);
        }
    }

    public void render(Graphics2D g) {
        FontRenderContext context = g.getFontRenderContext();

        g.setFont(headingFont);
        g.setColor(Color.WHITE);
        int headingXPos = Game.WIDTH / 2 - (int)headingFont.getStringBounds(heading, context).getWidth() / 2;
        int headingYPos = Game.HEIGHT / 2 - (int)headingFont.getStringBounds(heading, context).getHeight() / 2;
        g.drawString(heading, headingXPos, headingYPos);

        g.setFont(optionsFont);
        int optionsXPos = Game.WIDTH / 2 - (int)headingFont.getStringBounds(heading, context).getWidth() / 2;
        int optionsPadding = (int)optionsFont.getStringBounds(options[0], context).getHeight();
        int optionsYPosStart = headingYPos + (int)optionsFont.getStringBounds(options[0], context).getHeight();
        int padSum = 0;
        for (int i = 0; i < options.length; i++) {
            if(i == currentChoice){
                g.setColor(Color.YELLOW);
            }else{
                g.setColor(Color.WHITE);
            }
            g.drawString(options[i], optionsXPos, optionsYPosStart + padSum);
            padSum = optionsPadding + ( optionsPadding * i+1);
        }
    }
}
