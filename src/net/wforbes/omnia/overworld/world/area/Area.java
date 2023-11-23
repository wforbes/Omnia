package net.wforbes.omnia.overworld.world.area;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import net.wforbes.omnia.overworld.entity.DocNPC;
import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.entity.flora.BushFlora;
import net.wforbes.omnia.overworld.entity.flora.FloraController;
import net.wforbes.omnia.overworld.world.World;
import net.wforbes.omnia.overworld.world.area.effect.EffectController;
import net.wforbes.omnia.overworld.world.area.tile.TileMap;

import java.util.ArrayList;

public class Area {

    private World world;
    private TileMap tileMap;
    public ArrayList<Entity> entities;
    public EffectController effectController;
    private BushFlora flora;
    private FloraController floraController;

    public Area(World world) {
        this.world = world;
        this.entities = new ArrayList<>();
        this.effectController = new EffectController(this);
    }

    public World getWorld(){ return this.world; }
    public TileMap getTileMap() {
        return this.tileMap;
    }
    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }
    public ArrayList<Entity> getEntities() { return this.entities; }

    public void init() {
        this.initTileMap();
        this.effectController.init();
        this.initFlora();
        this.initEntities();
        this.world.player.setPosition(256,256);
        //this.initNPCs();
    }

    private void initFlora() {
        this.floraController = new FloraController(this);
        this.floraController.init();
    }

    private void initEntities() {
        this.addEntity(new DocNPC(world.gameState));
        for(Entity e : this.entities) {
            e.init();
        }
    }

    private void initTileMap() {
        this.tileMap = new TileMap(this, 32);
        this.tileMap.loadMapFromImageFile("/overworld/tile/maps/areamap0.gif");
        this.tileMap.loadTilesFromMapImage();
        //this.tileMap.loadTileSprites("/overworld/tile/areatiles0.gif");
        this.tileMap.loadTileSprites("/overworld/tile/tiles2.gif");
        this.tileMap.setPosition(0, 0);
        this.tileMap.setTween(0.06);
    }

    public void handleCanvasClick(MouseEvent event) {
        /*
        System.out.println("getX/Y: " + event.getX() + ", " + event.getY());
        System.out.println("sceneX/Y: " + event.getSceneX() + ", " + event.getSceneY());
         */
        System.out.println("area.handleCanvasClick event: " + event);

    }

    public void update() {
        this.tileMap.update(world.player);
        for(Entity e: this.entities) {
            e.update();
        }
    }

    public void render(GraphicsContext gc) {
        this.tileMap.render(gc);
        renderFlora(gc);
        renderEntities(gc);
        this.effectController.render(gc);
    }

    private void renderFlora(GraphicsContext gc) {
        this.floraController.render(gc);
    }

    private void renderEntities(GraphicsContext gc) {
        this.sortEntitiesByYPos();
        for (Entity e : this.entities) {
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

    public void teadown() {
        this.world = null;
        for (Entity e : this.entities) {
            e.teardown();
        }
        this.entities = null;
        this.effectController = new EffectController(this);
    }
}
