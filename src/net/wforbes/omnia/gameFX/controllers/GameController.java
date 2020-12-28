package net.wforbes.omnia.gameFX.controllers;

import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameFX.animation.GameLoopTimer;
import net.wforbes.omnia.gameFX.controls.KeyPolling;
import net.wforbes.omnia.gameFX.rendering.Renderer;
import net.wforbes.omnia.gameState.GameStateManager;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    public Stage stage;
    public Canvas gameCanvas;
    public AnchorPane gameAnchor;
    public KeyPolling keys  = KeyPolling.getInstance();

    private int shouldRenderCount = 0;
    private double delta = 0;
    private long lastTime = System.nanoTime();
    private long lastTimer = System.currentTimeMillis();
    private int fps = 0;
    private boolean shouldRender = true;
    private double nsPerTick = 1000000000D/60D; //how many nano seconds are in one tick
    private int secondCount = 0;
    private int lastFPS = 0;

    public int tickCount = 1;

    public GameStateManager gsm;
    public Renderer testRenderer;
    public GraphicsContext gc;
    public Image img;

    //private Entity player = new Entity(new Image(getClass().getResourceAsStream("/img/player.png")));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCanvas();
        stage = OmniaFX.getPrimaryStage();
        stage.setTitle("Omnia - Testing JavaFX framework");
        //this.gsm = new GameStateManager(this);
        this.testRenderer = new Renderer(this.gameCanvas);
        this.gc = testRenderer.getContext();

        // player, level, gui, etc. construct their Image spritesheet
        try {
            //this.testSpritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/testPlayer.png"));
            this.img = new Image(getClass().getResourceAsStream("/sprite_sheet.png"));
        } catch(Exception e) {
            e.printStackTrace();
        }

        PixelWriter pixelWriter = gc.getPixelWriter();
        PixelReader pixelReader = img.getPixelReader();
        PixelFormat pixelFormat = pixelReader.getPixelFormat();
        //System.out.println(pixelFormat);
        int w = (int)img.getWidth();
        int h = (int)img.getHeight();
        int[] pixels = new int[w * h];
        pixelReader.getPixels(0, 0, w, h, PixelFormat.getIntArgbInstance(), pixels, 0, w);
        for(int x = 0; x < w; x++) {
            System.out.println(pixels[x]);
            //Left off here 12/28 2am - reading pixels from spritesheet
            // need to test to make sure these pixels read like Screen class from tiles on sheet
        }

        //int[] pixels = pixelReader.getPixels()


        //player.setDrawPosition(350, 200);
        //player.setScale(1.5f);
        //renderer.addEntity(player);
        //renderer.setBackground(new Image(getClass().getResourceAsStream("/img/SpaceBackground.jpg")));

        GameLoopTimer timer = new GameLoopTimer() {
            @Override
            public void tick(float secondsSinceLastFrame) {
                //testRenderer.prepare();
                gc.setFill( new Color(0, 0, 0, 1.0) );
                gc.fillRect(0,0, gameCanvas.getWidth(), gameCanvas.getHeight());

                gc.save();
                //gsm.update();
                //gsm.render(gc);
                //testing manual pixel render





                gc.restore();
                //manual fps and render threshold logic
                /*
                long n = System.nanoTime();
                delta += (n - lastTime) / nsPerTick;
                lastTime = n;

                while (delta >= 1) {
                    tickCount++;
                    //update();

                    shouldRenderCount++;
                    delta -= 1;
                    shouldRender = true;
                }

                //sleep thread here?

                if (shouldRender) {
                    fps++;
                    //render();
                }

                if(System.currentTimeMillis() - lastTimer >= 1000) {
                    lastTimer += 1000;
                    lastFPS = fps;

                    stage.setTitle("FPS: " + fps);
                    //gc.fillText("FPS: " + (fps), 5, 215);
                    fps = 0;
                } else {
                    stage.setTitle("FPS: " + lastFPS);
                    //gc.fillText("FPS: " + (lastFPS), 5, 215);
                }
                //gc.fillText("lastTimer: " + lastTimer, 5, 235);
                if(fps == 0) {
                    secondCount++;
                }
                //render here..
                shouldRender = false;
                */
                //System.out.println("Tick: " + tickCount);

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
