package net.wforbes.omnia.overworld.world.area;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import net.wforbes.omnia.overworld.entity.DocNPC;
import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.entity.NPC;
import net.wforbes.omnia.overworld.world.terrain.TerrainController;
import net.wforbes.omnia.overworld.world.terrain.flora.BushFlora;
import net.wforbes.omnia.overworld.world.terrain.flora.FloraController;
import net.wforbes.omnia.overworld.world.World;
import net.wforbes.omnia.overworld.world.area.effect.EffectController;
import net.wforbes.omnia.overworld.world.area.tile.TileMap;

import java.util.ArrayList;

public class Area {

    private World world;
    private TileMap tileMap;
    public ArrayList<Entity> entities;
    public int TEST_NPC_XPOS = 200;
    public int TEST_NPC_YPOS = 200;
    public EffectController effectController;
    private TerrainController terrainController;

    public Area(World world) {
        this.world = world;
        this.entities = new ArrayList<>();
        this.effectController = new EffectController(this);
        this.terrainController = new TerrainController(this);
    }

    public World getWorld(){ return this.world; }
    public TileMap getTileMap() {
        return this.tileMap;
    }
    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }
    public ArrayList<Entity> getEntities() { return this.entities; }

    public TerrainController getTerrainController() {
        return this.terrainController;
    }

    public void init() {
        this.initTileMap();
        this.effectController.init();
        this.terrainController.init();
        this.initEntities();
        this.world.player.setPosition(256,256);
        //this.initNPCs();
    }

    private void initEntities() {
        NPC testNPC = new DocNPC(world.gameState);
        testNPC.init(TEST_NPC_XPOS, TEST_NPC_YPOS);

        this.addEntity(testNPC);
        /*
        for(Entity e : this.entities) {
            e.init();
        }*/
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
        renderTerrain(gc);
        renderEntities(gc);
        this.effectController.render(gc);
    }

    private void renderTerrain(GraphicsContext gc) {
        this.terrainController.render(gc);
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

    public void teardown() {
        this.world = null;
        for (Entity e : this.entities) {
            e.teardown();
        }
        this.entities = null;
        this.effectController = new EffectController(this);
    }
}
