package net.wforbes.omnia.overworld.level;

import javafx.scene.canvas.GraphicsContext;

public class Level {
    private TileMap tileMap;

    public Level() {}

    public TileMap getTileMap() {
        return this.tileMap;
    }

    public void init() {
        this.tileMap = new TileMap(30);
        this.tileMap.loadTiles("/overworld/tile/tiles.png");
        this.tileMap.loadMap("/overworld/tile/maps/level1.map");
        this.tileMap.setPosition(0, 0);
        this.tileMap.setTween(0.07);
    }

    public void update() {

    }

    public void render(GraphicsContext gc) {
        this.tileMap.render(gc);

    }
}
