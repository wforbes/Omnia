package net.wforbes.omnia.overworld.level;

import javafx.scene.canvas.GraphicsContext;

public class Level {
    private TileMap tileMap;

    public Level() {}

    public TileMap getTileMap() {
        return this.tileMap;
    }

    public void init() {
        this.tileMap = new TileMap(8);
        this.tileMap.loadTiles("/overworld/tile/grass_dirt_8bit.gif");
        this.tileMap.loadMap("/overworld/tile/maps/lrg_level.map");
        this.tileMap.setPosition(0, 0);
        this.tileMap.setTween(0.07);
    }

    public void update() {

    }

    public void render(GraphicsContext gc) {
        this.tileMap.render(gc);

    }
}
