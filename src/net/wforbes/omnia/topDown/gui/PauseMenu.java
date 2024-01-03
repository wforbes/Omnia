package net.wforbes.omnia.topDown.gui;

import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import net.wforbes.omnia.game.Game;
import net.wforbes.omnia.gameState.GameStateManager;
import net.wforbes.omnia.gameState.TopDownState;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.font.FontRenderContext;

public class PauseMenu {

    private boolean visible;
    private java.awt.Font headingFont;
    private Font fxHeadingFont;
    private String heading;
    private java.awt.Font optionsFont;
    private String[] options;
    private TopDownState gameState;
    private int waitTicks = 20;
    private int lastPressTick = waitTicks;
    private int tickCount = 0;
    private int currentChoice = 0;

    public PauseMenu(TopDownState gameState) {
        this.gameState = gameState;
        visible = false;
        headingFont = new java.awt.Font("Century Gothic", java.awt.Font.PLAIN, 20);
        heading = "Pause Menu";

        optionsFont = new java.awt.Font("Century Gothic", java.awt.Font.PLAIN, 14);
        options = new String[]{"Resume", "Return to Menu", "Quit Game"};
    }

    public PauseMenu(TopDownState gameState, String type) {
        if(!type.equals("fx"))
            return;

        this.gameState = gameState;
        visible = false;

        headingFont = new java.awt.Font("Century Gothic", java.awt.Font.PLAIN, (20 * Game.getScale()));
        heading = "Pause Menu";

        optionsFont = new java.awt.Font("Century Gothic", java.awt.Font.PLAIN, (14 * Game.getScale()));
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
        if(gameState.gsm.isKeyDown(KeyCode.ESCAPE) && keyInputReady()){
            gameState.unPause();
            lastPressTick = tickCount;
        }

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
    }

    private void select() {
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

    public void render(FXGraphics2D g) {

        FontRenderContext context = g.getFontRenderContext();

        //g.setFont(headingFont);
        g.setFont(new java.awt.Font("Century Gothic", java.awt.Font.PLAIN, 20 * Game.getScale()));
        g.setColor(java.awt.Color.WHITE);
        int headingXPos = Game.getScaledWidth() / 2 - (int)headingFont.getStringBounds(heading, context).getWidth() / 2;
        int headingYPos = Game.getScaledHeight() / 2 - (int)headingFont.getStringBounds(heading, context).getHeight() / 2;
        g.drawString(heading, headingXPos, headingYPos);

        g.setFont(optionsFont);
        int optionsXPos = Game.getScaledWidth() / 2 - (int)headingFont.getStringBounds(heading, context).getWidth() / 2;
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
    private double computeTextWidth(Font font, String text, double wrappingWidth) {
        Text helper = new Text();
        helper.setFont(font);
        helper.setText(text);
        // Note that the wrapping width needs to be set to zero before
        // getting the text's real preferred width.
        helper.setWrappingWidth(0);
        helper.setLineSpacing(0);
        double w = Math.min(helper.prefWidth(-1), wrappingWidth);
        helper.setWrappingWidth((int)Math.ceil(w));
        double textWidth = Math.ceil(helper.getLayoutBounds().getWidth());
        return textWidth;
    }
}
