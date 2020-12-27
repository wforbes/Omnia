package net.wforbes.gameFX.controllers;

import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import net.wforbes.gameFX.animation.GameLoopTimer;
import net.wforbes.gameFX.controls.KeyPolling;
import net.wforbes.gameFX.rendering.Renderer;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    public Canvas gameCanvas;
    public AnchorPane gameAnchor;
    KeyPolling keys  = KeyPolling.getInstance();

    public int tickCount = 1;

    //private Entity player = new Entity(new Image(getClass().getResourceAsStream("/img/player.png")));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCanvas();

        //player.setDrawPosition(350, 200);
        //player.setScale(1.5f);

        Renderer renderer = new Renderer(this.gameCanvas);
        //renderer.addEntity(player);
        //renderer.setBackground(new Image(getClass().getResourceAsStream("/img/SpaceBackground.jpg")));

        GameLoopTimer timer = new GameLoopTimer() {
            @Override
            public void tick(float secondsSinceLastFrame) {
                renderer.prepare();
                renderer.render(tickCount, secondsSinceLastFrame);



                //System.out.println("Tick: " + tickCount);
                tickCount++;
                //
                //updatePlayerMovement(secondsSinceLastFrame);
                //renderer.render();

            }
        };
        timer.start();
    }

    public void initializeCanvas() {
        gameCanvas.widthProperty().bind(gameAnchor.widthProperty());
        gameCanvas.heightProperty().bind(gameAnchor.heightProperty());
    }

    /*
    private void updatePlayerMovement(float frameDuration) {
        if (keys.isDown(KeyCode.UP)) {
            player.addThrust(20 * frameDuration);
        } else if (keys.isDown(KeyCode.DOWN)) {
            player.addThrust(-20 * frameDuration);
        }

        if (keys.isDown(KeyCode.RIGHT)) {
            player.addTorque(120f * frameDuration);
        } else if (keys.isDown(KeyCode.LEFT)) {
            player.addTorque(-120f * frameDuration);
        }

        if (keys.isDown(KeyCode.UP) || keys.isDown(KeyCode.W)) {

        }
        if (keys.isDown(KeyCode.DOWN) || keys.isDown(KeyCode.S)) {

        }
        if (keys.isDown(KeyCode.LEFT) || keys.isDown(KeyCode.A)) {

        }
        if (keys.isDown(KeyCode.RIGHT) || keys.isDown(KeyCode.D)) {

        }
        player.update();
    }*/
}
