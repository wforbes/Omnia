package net.wforbes.omnia.game.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import net.wforbes.omnia.game.Game;
import net.wforbes.omnia.game.animation.GameLoopTimer;
import net.wforbes.omnia.game.controls.keyboard.KeyPolling;
import net.wforbes.omnia.game.rendering.Renderer;
import net.wforbes.omnia.gameState.GameStateManager;
import net.wforbes.omnia.u.W;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    public Scene scene;
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
    public GameLoopTimer timer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initializeCanvas();
        stage = Game.getPrimaryStage();
        stage.setTitle("Omnia");
        this.gsm = new GameStateManager(this);
        this.renderer = new Renderer(this.gameCanvas);
        this.gc = renderer.getContext();

        this.gameAnchor.setOnKeyPressed(keyEvent -> {
            //System.out.println(keyEvent.getEventType() + " " + KeyEvent.KEY_PRESSED);
            if(keyEvent.getEventType() == KeyEvent.KEY_PRESSED)
                gsm.getCurrentState().handleKeyPressed(keyEvent);
        });
        this.gameAnchor.setOnKeyReleased(keyEvent -> {
            //System.out.println(keyEvent.getEventType() + " " + KeyEvent.KEY_RELEASED);
            if(keyEvent.getEventType() == KeyEvent.KEY_RELEASED)
                gsm.getCurrentState().handleKeyReleased(keyEvent);
        });


        this.gameBorder.setPickOnBounds(false);
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

        timer = new GameLoopTimer() {

            private int displayFPS = 0;
            private int displayTPS = 0;
            private int displayUpTime = 0;

            @Override
            public void render() {
                renderer.prepare();
                gc.save();
                gsm.render(gc);
                gc.restore();
                stage.setTitle("OmniaFX: { "
                    + "FPS: " + this.displayFPS + ", " + "TPS: " + this.displayTPS + ", "
                    + "Up Time: " + this.displayUpTime
                + " }");
            }

            @Override
            public void tick() {
                gsm.update();
            }

            @Override
            public void output(int _FPS, int _TPS, int _upTime) {
                this.displayFPS = _FPS;
                this.displayTPS = _TPS;
                this.displayUpTime = _upTime;
            }
        };
        timer.start();
    }

    //TODO: set game to scale on window resize
    public void setupScaling(Scene scene) {
        W.out("Got Scene: " + scene.toString());
        this.scene = scene;
        initializeCanvas();
        this.setScalable(scene, gameAnchor);
    }

    private void setScalable(final Scene scene, final Pane gameAnchor) {
        final double initWidth = scene.getWidth();
        final double initHeight = scene.getHeight();
        final double ratio = initWidth/initHeight;
        SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth, gameAnchor);
        scene.widthProperty().addListener(sizeListener);
        scene.heightProperty().addListener(sizeListener);
    }
    //https://stackoverflow.com/questions/16606162/javafx-fullscreen-resizing-elements-based-upon-screen-size
    private static class SceneSizeChangeListener implements ChangeListener<Number> {
        private final Scene scene;
        private final double ratio;
        private final double initHeight;
        private final double initWidth;
        private final Pane contentPane;
        public SceneSizeChangeListener(Scene scene, double ratio, double initHeight, double initWidth, Pane contentPane) {
            this.scene = scene;
            this.ratio = ratio;
            this.initHeight = initHeight;
            this.initWidth = initWidth;
            this.contentPane = contentPane;
        }
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            final double newWidth  = scene.getWidth();
            final double newHeight = scene.getHeight();

            double scaleFactor =
                    newWidth / newHeight > ratio
                            ? newHeight / initHeight
                            : newWidth / initWidth;
            System.out.println("Scale factor: " + scaleFactor);
            if (scaleFactor >= 1) {
                Scale scale = new Scale(scaleFactor, scaleFactor);
                scale.setPivotX(0);
                scale.setPivotY(0);
                scene.getRoot().getTransforms().setAll(scale);
            //if (scaleFactor >= 1) {
                contentPane.setPrefWidth (newWidth  / scaleFactor);
                contentPane.setPrefHeight(newHeight / scaleFactor);
            } else {
                contentPane.setPrefWidth (Math.max(initWidth,  newWidth));
                contentPane.setPrefHeight(Math.max(initHeight, newHeight));
            }
        }
    }

    public double getStageWidth() {
        return this.stage.getWidth();
    }

    public double getStageHeight() {
        return this.stage.getHeight();
    }

    public BorderPane getGameBorderPane() {
        return this.gameBorder;
    }

    public void initializeCanvas() {
        gameCanvas.widthProperty().bind(gameAnchor.widthProperty());
        gameCanvas.heightProperty().bind(gameAnchor.heightProperty());
    }
}
