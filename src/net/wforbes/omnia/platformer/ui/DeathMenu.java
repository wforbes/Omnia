package net.wforbes.omnia.platformer.ui;

import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import net.wforbes.omnia.game.Game;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameState.GameStateManager;
import net.wforbes.omnia.gameState.PlatformerState;
import org.jfree.fx.FXGraphics2D;


import java.awt.*;
import java.awt.font.FontRenderContext;

public class DeathMenu {

    private boolean visible;
    private int fxScale;
    private Font headingFont;
    private java.awt.Font awtHeadingFont;
    private String heading;
    private Font optionsFont;
    private java.awt.Font awtOptionsFont;
    private String[] options;
    private PlatformerState gameState;
    private int lastPressTick = 0;
    private int tickCount = 0;
    private int currentChoice = 0;


    public DeathMenu(PlatformerState gameState) {
        this.gameState = gameState;
        visible = false;
        this.fxScale = OmniaFX.getScale();
        headingFont = new Font("Century Gothic", 20 * fxScale);
        awtHeadingFont = new java.awt.Font("Century Gothic", java.awt.Font.PLAIN, 20);
        heading = "You died!";
        optionsFont = new Font("Century Gothic", 14 * fxScale);
        awtOptionsFont = new java.awt.Font("Century Gothic", java.awt.Font.PLAIN, 14);
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
        System.out.println("test");
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

    public void render(GraphicsContext g) {

        g.setFont(headingFont);
        g.setFill(Color.WHITE);
        double _width = computeTextWidth(headingFont, heading, OmniaFX.getScaledWidth());
        g.fillText(heading, OmniaFX.getScaledWidth() / 2 - _width/2, OmniaFX.getScaledHeight() / 2);

        g.setFont(optionsFont);
        double optionsYPosStart = OmniaFX.getScaledHeight() / 2 + headingFont.getSize();
        double optionsPadding = optionsFont.getSize();

        double padSum = 0;
        for (int i = 0; i < options.length; i++) {
            _width = computeTextWidth(optionsFont, options[i], OmniaFX.getScaledWidth());

            if(i == currentChoice){
                g.setFill(Color.YELLOW);
            }else{
                g.setFill(Color.WHITE);
            }
            g.fillText(options[i], OmniaFX.getScaledWidth() / 2 - _width / 2, optionsYPosStart + padSum);
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

    public void render(Graphics2D g) {

        FontRenderContext context = g.getFontRenderContext();

        g.setFont(awtHeadingFont);
        g.setColor(java.awt.Color.WHITE);
        int headingXPos = Game.WIDTH / 2 - (int)awtHeadingFont.getStringBounds(heading, context).getWidth() / 2;
        int headingYPos = Game.HEIGHT / 2 - (int)awtHeadingFont.getStringBounds(heading, context).getHeight() / 2;
        g.drawString(heading, headingXPos, headingYPos);

        g.setFont(awtOptionsFont);
        int optionsXPos = Game.WIDTH / 2 - (int)awtHeadingFont.getStringBounds(heading, context).getWidth() / 2;
        int optionsPadding = (int)awtOptionsFont.getStringBounds(options[0], context).getHeight();
        int optionsYPosStart = headingYPos + (int)awtOptionsFont.getStringBounds(options[0], context).getHeight();
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
}
