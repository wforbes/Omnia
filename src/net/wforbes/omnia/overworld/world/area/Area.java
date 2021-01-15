package net.wforbes.omnia.overworld.world.area;

import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.overworld.entity.DocNPC;
import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.world.World;
import net.wforbes.omnia.overworld.world.area.tile.TileMap;

import java.util.ArrayList;

public class Area {

    private World world;
    private TileMap tileMap;
    public ArrayList<Entity> entities;

    public Area(World world) {
        this.world = world;
        this.entities = new ArrayList<>();
    }

    public TileMap getTileMap() {
        return this.tileMap;
    }
    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    public void init() {
        this.initTileMap();
        this.initEntities();
        this.world.player.setPosition(60,60);
        //this.initNPCs();
    }

    private void initEntities() {
        this.addEntity(new DocNPC(world.gameState));

        for(Entity e : this.entities) {
            e.init();
        }
    }

    private void initTileMap() {
        this.tileMap = new TileMap(8);
        this.tileMap.loadMapFromImageFile("/overworld/tile/maps/areamap0.gif");
        this.tileMap.loadTilesFromMapImage();
        this.tileMap.loadTileSprites("/overworld/tile/areatiles0.gif");
        this.tileMap.setPosition(0, 0);
        this.tileMap.setTween(0.07);
    }

    public void update() {
        this.tileMap.update(world.player);
        for(Entity e: this.entities) {
            e.update();
        }
    }

    public void render(GraphicsContext gc) {
        this.tileMap.render(gc);
        renderEntities(gc);
    }

    private void renderEntities(GraphicsContext gc) {
        this.sortEntitiesByYPos();
        for(Entity e : this.entities) {
            e.render(gc);
        }
    }

    private void sortEntitiesByYPos() {
        entities.sort((e1, e2) -> {
            if (e1.getY() == e2.getY()) {
                return 0;
            } else if (e1.getY() > e2.getY()) {
                return 1;
            } else {
                return -1;
            }
        });
    }
}
