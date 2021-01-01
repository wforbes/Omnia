package net.wforbes.omnia.gameState;

import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.menu.MainMenu;
import net.wforbes.omnia.platformer.tileMap.Background;

import java.awt.*;

public class MenuState extends GameState {
    private MainMenu menu;

    private int fxScale;
    private int waitTicks = 20;
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

    private Background bg;

    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;
        if (this.gsm.usingFx) {
            this.menu = new MainMenu(this);
        } else {
            this.setup();
        }
    }

    private void setup() {
        try {//load the background resource .gif file
            bg = new Background(this, "/Backgrounds/menubg.gif", 1);
        } catch(Exception e) {
            e.printStackTrace();
        }
        bg.setVector(-0.1, 0);//move to the left at .1 pixels

        //font for Naturalist Engine title
        titleFont = new java.awt.Font("Century Gothic", java.awt.Font.PLAIN, 25);

        //font for version number
        subTitleFont = new java.awt.Font("Century Gothic", java.awt.Font.PLAIN, 10);

        //font for subModern presents
        subTitleFont2 = new java.awt.Font("Century Gothic", java.awt.Font.PLAIN, 10);

        //font info for everything else
        font = new java.awt.Font("Arial", java.awt.Font.PLAIN, 12);
    }

    @Override
    public void init() {
        if (this.gsm.usingFx) {
            this.menu.init();
        } else {
            this.lastPressTick = waitTicks;
            this.tickCount = 0;
            this.currentChoice = 0;
        }
    }

    @Override
    public void update() {
        this.menu.update();
    }

    @Override
    public void render(GraphicsContext gc) {
        this.menu.render(gc);
    }

    @Override
    public void tick() {
        bg.update();
        if (keyInputReady()) this.checkKeyInput();
        tickCount++;
    }

    private boolean keyInputReady() {
        return tickCount - lastPressTick > waitTicks || lastPressTick == 0;
    }

    private void checkKeyInput() {
        if (this.gsm.usingFx) {

        } else {
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
