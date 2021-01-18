package net.wforbes.omnia.gameFX.controllers;

import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameFX.animation.GameLoopTimer;
import net.wforbes.omnia.gameFX.controls.KeyPolling;
import net.wforbes.omnia.gameFX.controls.mouse.MouseController;
import net.wforbes.omnia.gameFX.rendering.Renderer;
import net.wforbes.omnia.gameState.GameStateManager;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    public Stage stage;
    public Canvas gameCanvas;
    public AnchorPane gameAnchor;
    public StackPane gameStack;
    public BorderPane gameBorder;
    public KeyPolling keys  = KeyPolling.getInstance();
    private double time;

    public GameStateManager gsm;
    public Renderer renderer;
    public GraphicsContext gc;
    public MouseController mouseController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCanvas();
        stage = OmniaFX.getPrimaryStage();
        stage.setTitle("Omnia");
        this.gsm = new GameStateManager(this);
        this.renderer = new Renderer(this.gameCanvas);
        this.gc = renderer.getContext();

        this.gameBorder.setPickOnBounds(false);
        this.gameBorder.setOnMouseMoved(event -> {
            //gsm.getCurrentState().handleCanvasMouseMove(event);
        });
        this.gameBorder.setOnMouseClicked(event -> {
            System.out.println("Border Got A Click! " + event.toString());
            gameCanvas.requestFocus();//regain canvas focus when clicking outside of UI windows

        });

        this.gameStack.setPickOnBounds(false);
        this.gameStack.setOnMouseClicked(event -> {
            System.out.println("Stack Got A Click!");
            gsm.getCurrentState().handleCanvasClick(event);
        });
        this.gameStack.setOnMouseMoved(event -> {
            gsm.getCurrentState().handleCanvasMouseMove(event);
        });

        this.gameCanvas.requestFocus();
        this.gameCanvas.setFocusTraversable(true);
        this.gameCanvas.setOnMouseClicked(event -> {
            System.out.println("Canvas Got A Click! " + event.toString());
            gameCanvas.requestFocus();//regain canvas focus when clicking outside of UI windows
        });


        int i = 0;
        GameLoopTimer timer = new GameLoopTimer() {
            @Override
            public void tick(float secondsSinceLastFrame) {
                time += 0.017;
                if (time >= 0.017) {
                    renderer.prepare();
                    gc.save();
                    gsm.update();
                    gsm.render(gc);
                    gc.restore();
                }
            }
        };
        timer.start();
    }

    public BorderPane getGameBorderPane() {
        return this.gameBorder;
    }

    public void initializeCanvas() {
        gameCanvas.widthProperty().bind(gameAnchor.widthProperty());
        gameCanvas.heightProperty().bind(gameAnchor.heightProperty());
    }
}
