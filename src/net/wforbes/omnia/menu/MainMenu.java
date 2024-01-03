package net.wforbes.omnia.menu;

import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import net.wforbes.omnia.game.Game;
import net.wforbes.omnia.gameState.GameStateManager;
import net.wforbes.omnia.gameState.MenuState;
import net.wforbes.omnia.menu.background.MenuBackground;

public class MainMenu extends Menu {
    private MenuState state;
    private MenuBackground bg;

    private final Font titleFont, subTitleFont, subTitleFont2, font;
    private double fxScale;
    private String[] options = {
            "Top-Down",
            "Platformer",
            "Info",
            "Quit"
    };

    public MainMenu(MenuState state) {
        this.state = state;
        this.fxScale = Game.getScale();
        try {//load the background resource .gif file
            bg = new MenuBackground("/Backgrounds/menubg.gif");
        } catch(Exception e) {
            e.printStackTrace();
        }
        bg.setVector(-0.1 * Game.getScale(), 0);//move to the left at .1 pixels

        //font for Naturalist Engine title
        titleFont = new Font("Century Gothic", 25 * fxScale);

        //font for version number
        subTitleFont = new Font("Century Gothic", 10 * fxScale);

        //font for subModern presents
        subTitleFont2 = new Font("Century Gothic", 5 * fxScale);

        //font info for everything else
        font = new Font("Arial", 12 * fxScale);
    }

    public void init() {
        this.setButtons();
        //this.setTitles();
        /*
        this.lastPressTick = waitTicks;
        this.tickCount = 0;
        this.currentChoice = 0;
         */
    }

    private void setTitles() {
        Pane titlePane = new Pane();
        titlePane.setStyle("-fx-background-color: rgba(0,0,0,0)");
        VBox titlesBox = new VBox();
        titlesBox.setPadding(new Insets(500, 100, 100, 100));
        Text titleTxt = new Text("Omnia");
        titleTxt.getStyleClass().add("main-title");
        titlePane.setLayoutX(100);
        titlePane.setLayoutY(100);
        titlePane.setPrefSize(titleTxt.getLayoutBounds().getWidth(), titleTxt.getLayoutBounds().getHeight());
        titlesBox.getChildren().add(titleTxt);
        this.state.gsm.gameController.gameBorder.setCenter(titlesBox);
    }

    private void setButtons() {

        Button overworldBtn = new Button("Overworld (32-bit)");
        overworldBtn.getStyleClass().add("main-button");
        overworldBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            overworldBtn.requestFocus();
        });
        overworldBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            state.gsm.gameController.gameBorder.setLeft(null);
            state.gsm.setState(GameStateManager.OVERWORLDSTATE);
        });
        overworldBtn.setOnAction(event -> {
            state.gsm.gameController.gameBorder.setLeft(null);
            state.gsm.setState(GameStateManager.OVERWORLDSTATE);
        });

        Button topDownBtn = new Button("Top-Down (OLD/BROKEN)");
        topDownBtn.getStyleClass().add("main-button");
        topDownBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            topDownBtn.requestFocus();
        });
        topDownBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            state.gsm.gameController.gameBorder.setLeft(null);
            state.gsm.setState(GameStateManager.TOPDOWNSTATE);
        });
        topDownBtn.setOnAction(event -> {
            state.gsm.gameController.gameBorder.setLeft(null);
            state.gsm.setState(GameStateManager.TOPDOWNSTATE);
        });

        Button platformerBtn = new Button("Platformer (OLD/BROKEN)");
        platformerBtn.getStyleClass().add("main-button");
        platformerBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            platformerBtn.requestFocus();
        });
        platformerBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            state.gsm.gameController.gameBorder.setLeft(null);
            state.gsm.setState(GameStateManager.PLATFORMERSTATE);
        });
        platformerBtn.setOnAction(event -> {
            state.gsm.gameController.gameBorder.setLeft(null);
            state.gsm.setState(GameStateManager.PLATFORMERSTATE);
        });

        Button infoBtn = new Button("Info");
        infoBtn.getStyleClass().add("main-button");
        infoBtn.addEventHandler(MouseEvent.MOUSE_ENTERED,event -> {
            infoBtn.requestFocus();
        });
        infoBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {});
        infoBtn.setOnAction(event -> {});

        Button quitBtn = new Button("Quit");
        quitBtn.getStyleClass().add("main-button");
        quitBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            quitBtn.requestFocus();
        });
        quitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.exit(0);
        });
        quitBtn.setOnAction(event -> {
            System.exit(0);
        });

        VBox vbox = new VBox(18);
        vbox.setPadding(new Insets((123 * fxScale), 0,0,28*fxScale));
        vbox.getChildren().addAll(overworldBtn, topDownBtn, platformerBtn, infoBtn, quitBtn);
        state.gsm.gameController.gameBorder.setLeft(vbox);
    }

    public void update() {
        bg.update();
        /*
        if (keyInputReady()) this.checkKeyInput();
        tickCount++;

         */
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
            //lastPressTick = tickCount;
        }

        if(state.gsm.isKeyDown(KeyCode.DOWN) && keyInputReady()){
            currentChoice++;
            if(currentChoice == options.length){
                currentChoice = 0;
            }
            //lastPressTick = tickCount;
        }
    }

    @Override
    boolean keyInputReady() {
        return false;
    }

    void select(String option) {}

    void select() {
        if(currentChoice == 0){
            state.gsm.setState(GameStateManager.TOPDOWNSTATE);
        }
        if(currentChoice == 1){
            state.gsm.setState(GameStateManager.PLATFORMERSTATE);
        }
        if(currentChoice == 2){
            //state.gsm.setState(GameStateManager.INFOSTATE);
        }
        if(currentChoice == 3){
            System.exit(0);
        }
    }

    public void render(GraphicsContext gc) {
        bg.render(gc);
        gc.setFill(Color.BLACK);

        Text presentsTxt = new Text("wforbes presents:");
        presentsTxt.setFont(subTitleFont2);
        double presentsX = ((Game.getScaledWidth() - presentsTxt.getLayoutBounds().getWidth())/2);
        gc.setFont(subTitleFont2);
        gc.fillText("wforbes presents:", presentsX, 60 * fxScale);


        Text titleTxt = new Text("Omnia");
        titleTxt.setFont(titleFont);
        gc.setFont(titleFont);
        double centerX = ((Game.getScaledWidth() - titleTxt.getLayoutBounds().getWidth())/2);
        //gc.fillText("Omnia", ((OmniaFX.getScaledWidth() - titleTxt.getLayoutBounds().getWidth())/2), 85 * fxScale);
        gc.fillText("Omnia", centerX, 85 * fxScale);

        Text versionTxt = new Text("prototype v0.0.2");
        versionTxt.setFont(subTitleFont);
        double versionX = ((Game.getScaledWidth() - versionTxt.getLayoutBounds().getWidth())/2);
        gc.setFont(subTitleFont);
        gc.fillText("prototype v0.0.2", versionX, 100 * fxScale);
        //gc.fillText("prototype v0.0.2", 225 * fxScale, 100 * fxScale);
/*
        gc.setFont(font);
        for(int i = 0; i < options.length; i++){
            if(i == currentChoice){
                gc.setFill(Color.YELLOW);
            }else{
                gc.setFill(Color.WHITE);
            }
            gc.fillText(options[i], 30 * fxScale, (135 * fxScale) + (20 * fxScale * i) );
        }

 */
    }
}
