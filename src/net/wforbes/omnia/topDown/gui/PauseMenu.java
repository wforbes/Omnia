package net.wforbes.omnia.topDown.gui;

import net.wforbes.omnia.game.Game;
import net.wforbes.omnia.gameState.GameStateManager;
import net.wforbes.omnia.gameState.TopDownState;

import java.awt.Font;
import java.awt.*;
import java.awt.font.FontRenderContext;

public class PauseMenu {

    private boolean visible;
    private java.awt.Font headingFont;
    private String heading;
    private Font optionsFont;
    private String[] options;
    private TopDownState gameState;
    private int lastPressTick = 0;
    private int tickCount = 0;
    private int currentChoice = 0;

    public PauseMenu(TopDownState gameState) {
        this.gameState = gameState;
        visible = false;
        headingFont = new Font("Century Gothic", Font.PLAIN, 20);
        heading = "Pause Menu";

        optionsFont = new Font("Century Gothic", Font.PLAIN, 14);
        options = new String[]{"Resume", "Return to Menu", "Quit Game"};
    }

    public int getTickCount() {
        return tickCount;
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

    public boolean keyInputReady() {
        int waitTicks = 20;
        return tickCount - lastPressTick > waitTicks || lastPressTick == 0;
    }

    private void checkKeyInput() {
        if(gameState.gsm.inputHandler.esc.isPressed() && keyInputReady()){
            gameState.unPause();
        }

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

    private void select() {
        gameState.gsm.inputHandler.resetKeys(); //To avoid double presses
        if(currentChoice == 0){
            gameState.unPause();
        }
        if(currentChoice == 1){
            gameState.gsm.setState(GameStateManager.MENUSTATE);
        }
        if(currentChoice == 2){
            System.exit(0);
        }
    }

    private void reset() {
        lastPressTick = 0;
        tickCount = 0;
        currentChoice = 0;
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
