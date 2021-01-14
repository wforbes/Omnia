package net.wforbes.omnia.overworld.world.area;

import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.overworld.world.World;
import net.wforbes.omnia.overworld.world.area.tile.TileMap;

public class Area {

    private World world;
    private TileMap tileMap;


    public Area(World world) {
        this.world = world;
    }

    public TileMap getTileMap() {
        return this.tileMap;
    }

    public void init() {
        this.world.player.setPosition(60,60);
        this.initTileMap();

    }

    private void initTileMap() {
        this.tileMap = new TileMap(8);
        //this.tileMap.loadTiles("/overworld/tile/grass_dirt_8bit.gif");
        this.tileMap.loadMapFromImageFile("/overworld/tile/maps/areamap0.png");
        this.tileMap.loadTilesFromMapImage();
        this.tileMap.loadTileSprites("/overworld/tile/areatiles0.gif");
        this.tileMap.setPosition(0, 0);
        this.tileMap.setTween(0.07);
    }

    public void update() {
        this.tileMap.setPosition(world.player.getX(), world.player.getY());
    }

    public void render(GraphicsContext gc) {
        this.tileMap.render(gc);

    }
}
