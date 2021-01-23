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

public class PauseMenu extends Menu {

    private GameStateManager gsm;
    private boolean visible;
    private Font headingFont;
    private String heading = "Pause Menu";
    private Font optionsFont;
    private String[]  options = {
        "Resume",
        "Return to Main Menu",
        "Quit Game"
    };
    private VBox vbox = new VBox(10);
    private ArrayList<Button> buttons = new ArrayList<>();

    public PauseMenu(GameStateManager gsm) {
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
            btn.getStyleClass().add("pause-button");
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
        //this.tickCount++;
    }

    public void update() {
        if(!isVisible()) {
            show();
        }
        this.checkKeyInput();
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
        return gsm.getCurrentState().getKeyboard().keyInputIsReady();
    }

    @Override
    void checkKeyInput() {
        if(gsm.getCurrentState().getKeyboard().isKeyDown(KeyCode.ESCAPE) && keyInputReady()){
            gsm.getCurrentState().getKeyboard().consumeKey(KeyCode.ESCAPE);
            gsm.getCurrentState().unPause();
        }
    }

    @Override
    void select(String option) {
        if (option.equals(options[0])) {
            gsm.getCurrentState().unPause();
        } else if (option.equals(options[1])) {
            gsm.getCurrentState().unPause();
            gsm.setState(GameStateManager.MENUSTATE);
        } else if (option.equals(options[2])) {
            System.exit(0);
        }
    }
}
