package net.wforbes.omnia.menu.mainMenu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import net.wforbes.omnia.game.Game;
import net.wforbes.omnia.gameState.GameStateManager;
import net.wforbes.omnia.gameState.MenuState;
import net.wforbes.omnia.menu.Menu;
import net.wforbes.omnia.menu.background.MenuBackground;

public class MainMenu extends Menu {
    private MenuState state;
    private MenuBackground bg;

    private final Font titleFont, subTitleFont, subTitleFont2, font;
    private double scale;

    public MainMenu(MenuState state) {
        this.state = state;
        this.scale = Game.getScale();
        try {//load the background resource .gif file
            bg = new MenuBackground("/Backgrounds/menubg.gif");
        } catch(Exception e) {
            e.printStackTrace();
        }
        bg.setVector(-0.1 * Game.getScale(), 0);//move to the left at .1 pixels

        //font for Naturalist Engine title
        titleFont = new Font("Century Gothic", 25 * scale);

        //font for version number
        subTitleFont = new Font("Century Gothic", 10 * scale);

        //font for subModern presents
        subTitleFont2 = new Font("Century Gothic", 5 * scale);

        //font info for everything else
        font = new Font("Arial", 12 * scale);
    }

    public MenuState getState() {
        return this.state;
    }

    public void init() {
        this.showMainMenuPage();
    }

    private void showMainMenuPage() {
        this.setMainTitleButtons();
        this.setDevButtons();
    }

    private void setMainTitleButtons() {
        VBox vbox = new VBox(8);
        vbox.setPadding(new Insets((125 * scale), 0,0,28* scale));
        vbox.getChildren().addAll(
            this.createGameTitleText(),
            this.createNewGameButton(),
            this.createLoadGameButton(),
            this.createInfoButton(),
            this.createQuitButton()
        );

        state.gsm.gameController.gameBorder.setLeft(vbox);
    }

    private Text createGameTitleText() {
        Text titleTxt = new Text("Omnia");
        titleTxt.setFont(titleFont);
        return titleTxt;
    }

    private Button createNewGameButton() {
        Button newGameBtn = new Button("New Game");
        newGameBtn.getStyleClass().add("main-button");
        newGameBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            newGameBtn.requestFocus();
        });
        newGameBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            this.showNewGamePage();
        });
        newGameBtn.setOnAction(event -> {
            this.showNewGamePage();
        });
        return newGameBtn;
    }
    private Button createLoadGameButton() {
        Button loadGameBtn = new Button("Load Game");
        loadGameBtn.getStyleClass().add("main-button");
        loadGameBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            loadGameBtn.requestFocus();
        });
        loadGameBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            this.showLoadGamePage();
        });
        loadGameBtn.setOnAction(event -> {
            this.showLoadGamePage();
        });
        return loadGameBtn;
    }
    private Button createInfoButton() {
        Button infoBtn = new Button("Info");
        infoBtn.getStyleClass().add("main-button");
        infoBtn.addEventHandler(MouseEvent.MOUSE_ENTERED,event -> {
            infoBtn.requestFocus();
        });
        infoBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            this.showInfoPage();
        });
        infoBtn.setOnAction(event -> {
            this.showInfoPage();
        });
        return infoBtn;
    }
    private Button createQuitButton() {
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
        return quitBtn;
    }

    private void showNewGamePage() {
        this.clearGameBorder();
        NewGamePage page = new NewGamePage();
        state.gsm.gameController.gameBorder.setCenter(page.getDisplayUI(this));
    }

    private void showLoadGamePage() {
        this.clearGameBorder();
    }

    private void showInfoPage() {
        this.clearGameBorder();
    }

    public void clearGameBorder() {
        state.gsm.gameController.gameBorder.setTop(null);
        state.gsm.gameController.gameBorder.setLeft(null);
        state.gsm.gameController.gameBorder.setRight(null);
        state.gsm.gameController.gameBorder.setCenter(null);
        state.gsm.gameController.gameBorder.setBottom(null);
    }

    private void setDevButtons() {
        Button overworldBtn = new Button("(dev) Overworld");
        overworldBtn.getStyleClass().add("dev-button");
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

        Button topDownBtn = new Button("(dev) Top-Down");
        topDownBtn.getStyleClass().add("dev-button");
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

        Button platformerBtn = new Button("(dev) Platformer");
        platformerBtn.getStyleClass().add("dev-button");
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

        VBox vbox = new VBox(18);
        vbox.setPadding(new Insets((160 * scale), 28* scale,0,0));
        vbox.getChildren().addAll(overworldBtn, topDownBtn, platformerBtn);
        state.gsm.gameController.gameBorder.setRight(vbox);
    }

    public void update() {
        bg.update();
    }

    @Override
    public void checkKeyInput() {}

    @Override
    public boolean keyInputReady() {
        return false;
    }

    public void select(String option) {}


    public void render(GraphicsContext gc) {
        bg.render(gc);
        /*
        gc.setFill(Color.BLACK);

        Text presentsTxt = new Text("wforbes presents:");
        presentsTxt.setFont(subTitleFont2);
        double presentsX = ((Game.getScaledWidth() - presentsTxt.getLayoutBounds().getWidth())/2);
        gc.setFont(subTitleFont2);
        gc.fillText("wforbes presents:", presentsX, 60 * scale);


        Text titleTxt = new Text("Omnia");
        titleTxt.setFont(titleFont);
        gc.setFont(titleFont);
        double centerX = ((Game.getScaledWidth() - titleTxt.getLayoutBounds().getWidth())/2);
        //gc.fillText("Omnia", ((OmniaFX.getScaledWidth() - titleTxt.getLayoutBounds().getWidth())/2), 85 * fxScale);
        gc.fillText("Omnia", centerX, 85 * scale);

        Text versionTxt = new Text("prototype v0.0.2");
        versionTxt.setFont(subTitleFont);
        double versionX = ((Game.getScaledWidth() - versionTxt.getLayoutBounds().getWidth())/2);
        gc.setFont(subTitleFont);
        gc.fillText("prototype v0.0.2", versionX, 100 * scale);
         */
    }
}
