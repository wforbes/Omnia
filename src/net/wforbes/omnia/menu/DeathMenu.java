package net.wforbes.omnia.menu;

import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameState.GameStateManager;

import java.util.ArrayList;

public class DeathMenu extends Menu{

    private GameStateManager gsm;
    private boolean visible;
    private Font headingFont;
    private String heading = "You Died!";
    private Font optionsFont;
    private String[]  options = {
        "Try Again", "Return to Main Menu", "Quit Game"
    };
    private VBox vbox = new VBox(10);
    private ArrayList<Button> buttons = new ArrayList<>();
    private final int waitTicks = 20;
    private int lastPressTick = waitTicks;
    private int tickCount = 0;

    public DeathMenu(GameStateManager gsm) {
        this.gsm = gsm;
        this.visible = false;
        this.headingFont = new Font("Century Gothic", 20 * OmniaFX.getScale());
        this.optionsFont = new Font("Century Gothic",14 * OmniaFX.getScale());
        this.setButtons();
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void show() {
        this.visible = true;
        gsm.gameController.gameBorder.setCenter(vbox);
    }

    public void hide() {
        this.visible = false;
        gsm.gameController.gameBorder.setCenter(null);
    }

    private void setButtons() {
        vbox.setPadding(new Insets(
                OmniaFX.getScaledHeight()/2, 0,0,
                (OmniaFX.getScaledWidth()/2 - 55 * OmniaFX.getScale())));
        for(int i = 0; i < options.length; i++) {
            Button btn = new Button(options[i]);
            btn.getStyleClass().add("death-button");
            btn.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                btn.requestFocus();
            });
            String option = options[i];
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                this.select(option);
            });
            btn.setOnAction(event -> {
                this.select(option);
            });
            this.buttons.add(btn);
            vbox.getChildren().add(btn);
        }
    }
    public void tick() {
        this.checkKeyInput();
        this.tickCount++;
    }

    public void render(GraphicsContext gc) {
        if(this.visible) {
            gc.setFont(headingFont);
            gc.setFill(Color.WHITE);
            gc.fillText(this.heading,
                    (double)OmniaFX.getScaledWidth()/2 - this.calcStrWidth(headingFont, heading)/2,
                    (double)OmniaFX.getScaledHeight()/2 - 10 * OmniaFX.getScale());
            gc.setFont(optionsFont);
        }
    }
    private double calcStrWidth(Font font, String str) {
        Text txt = new Text(str);
        txt.setFont(font);
        return txt.getLayoutBounds().getWidth();
    }


    public boolean keyInputReady() {
        int waitTicks = 20;
        return tickCount - lastPressTick > waitTicks || lastPressTick == 0;
    }

    @Override
    void checkKeyInput() {
        if(gsm.isKeyDown(KeyCode.ESCAPE) && keyInputReady()){
            System.out.println("new menu got escape");
            gsm.getCurrentState().unPause();
            lastPressTick = tickCount;
        }
    }

    @Override
    void select(String option) {
        if (option == options[0]) {
            this.hide();
            gsm.resetState(GameStateManager.PLATFORMERSTATE);
        } else if (option == options[1]) {
            this.hide();
            gsm.setState(GameStateManager.MENUSTATE);
        } else if (option == options[2]) {
            System.exit(0);
        }
    }

    @Override
    void update() {
        //todo: implement when overworld starts killing you
    }
}
