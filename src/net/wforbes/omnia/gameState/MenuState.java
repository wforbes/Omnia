package net.wforbes.omnia.gameState;

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
    private Font titleFont, subTitleFont, subTitleFont2, font;
    private Background bg;

    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;

        try{//load the background resource .gif file
            bg = new Background("/Backgrounds/menubg.gif", 1);
            bg.setVector(-0.1, 0);//move to the left at .1 pixels

            //font for Naturalist Engine title
            titleFont = new Font("Century Gothic", Font.PLAIN, 25);

            //font for version number
            subTitleFont = new Font("Century Gothic", Font.PLAIN, 10);

            //font for subModern presents
            subTitleFont2 = new Font("Century Gothic", Font.PLAIN, 10);

            //font info for everything else
            font = new Font("Arial", Font.PLAIN, 12);

        }catch(Exception e){
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
    public void render(Graphics2D graphics2D) {
        bg.draw(graphics2D);
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(subTitleFont2);
        graphics2D.drawString("subModern studios presents:", 30, 60);
        graphics2D.setFont(titleFont);
        graphics2D.drawString("Omnia Game Engine", 30, 85);
        graphics2D.setFont(subTitleFont);
        graphics2D.drawString("alpha version 0.0.1", 225, 100);

        graphics2D.setFont(font);
        for(int i = 0; i < options.length; i++){
            if(i == currentChoice){
                graphics2D.setColor(Color.YELLOW);
            }else{
                graphics2D.setColor(Color.WHITE);
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
