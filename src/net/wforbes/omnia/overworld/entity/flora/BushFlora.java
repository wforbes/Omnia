package net.wforbes.omnia.overworld.entity.flora;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import net.wforbes.omnia.gameState.OverworldState;

import java.util.ArrayList;
import java.util.Random;

import static net.wforbes.omnia.gameFX.OmniaFX.getScale;

public class BushFlora extends Flora {
    private final OverworldState gameState;
    private Image spriteImg;
    private double x, y, xmap, ymap, width, height;

    public BushFlora(OverworldState gameState) {
        this.gameState = gameState;
        this.loadSprite(OverworldState.SPRITE_DIR + "bushes/Bush_blue_flowers3.png");
        Random rand = new Random();
        this.x = rand.nextDouble() * 100;
        this.y = rand.nextDouble() * 100;
    }

    private void loadSprite(String path) {
        this.spriteImg = new Image(getClass().getResourceAsStream(path));
        this.width = this.spriteImg.getWidth();
        this.height = this.spriteImg.getHeight();
    }

    public void init() {}

    public void render(GraphicsContext gc) {
        this.refreshMapPosition();
        this.renderSprite(gc);
    }

    protected void refreshMapPosition() {
        xmap = gameState.world.area.getTileMap().getX();
        ymap = gameState.world.area.getTileMap().getY();
    }

    private void renderSprite(GraphicsContext gc) {
        gc.drawImage(
                this.spriteImg,
                (x + xmap - width / 2.0) * getScale(),
                (y + ymap - height / 2.0) * getScale(),
                width * getScale(),
                height * getScale()
        );
    }
}
