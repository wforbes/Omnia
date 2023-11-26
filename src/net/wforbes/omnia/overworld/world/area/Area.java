package net.wforbes.omnia.overworld.world.area;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameFX.rendering.Renderable;
import net.wforbes.omnia.overworld.entity.DocNPC;
import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.entity.NPC;
import net.wforbes.omnia.overworld.world.World;
import net.wforbes.omnia.overworld.world.area.effect.EffectController;
import net.wforbes.omnia.overworld.world.area.object.AreaObject;
import net.wforbes.omnia.overworld.world.area.object.AreaObjectController;
import net.wforbes.omnia.overworld.world.area.tile.TileMap;

import java.util.ArrayList;
import java.util.List;

import static net.wforbes.omnia.gameFX.OmniaFX.getScale;

public class Area {
    private final AreaObjectController areaObjectController;
    private World world;
    private TileMap tileMap;
    public List<Entity> entities;
    public double TEST_NPC_XPOS = 200;
    public double TEST_NPC_YPOS = 200;
    public EffectController effectController;
    public List<AreaObject> areaObjects;

    public Area(World world) {
        this.world = world;
        this.entities = new ArrayList<>();
        this.areaObjectController = new AreaObjectController(this);
        this.effectController = new EffectController(this);
    }

    public World getWorld(){ return this.world; }
    public TileMap getTileMap() {
        return this.tileMap;
    }
    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }
    public List<Entity> getEntities() { return this.entities; }

    public List<AreaObject> getAreaObjects() { return this.areaObjectController.getAreaObjects(); }

    public void init() {
        this.initTileMap();
        this.effectController.init();
        this.areaObjectController.init();
        //this.initAreaObjects();
        this.initEntities();
        this.world.player.init(200,150);
        //this.initNPCs();
    }

    private void initAreaObjects() {}

    private void initEntities() {
        NPC testNPC = new DocNPC(world.gameState);
        System.out.println("testNPC: " + testNPC.movementController);
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
        System.out.println("area.handleCanvasClick event: " + event);

    }

    public void update() {
        this.tileMap.update(world.player);
        for(Entity e: this.entities) {
            e.update();
        }
        this.areaObjectController.update();
    }

    public void render(GraphicsContext gc) {
        this.tileMap.render(gc);
        if (world.gameState.collisionGeometryVisible()) {
            gc.setFill(Color.BLACK);
            gc.setGlobalAlpha(0.5);
            double miniMapWidth = (double) this.getTileMap().getWidth() / (getScale()*2);
            double miniMapHeight = (double) this.getTileMap().getWidth() / (getScale()*2);
            gc.fillRect(
                    0,
                    0,
                    OmniaFX.getWidth() + OmniaFX.getWidth()/8.25,
                    OmniaFX.getHeight() + OmniaFX.getHeight()/8.25
            );
            gc.setGlobalAlpha(1);
        }
        this.renderRenderables(gc);
        this.effectController.render(gc);
    }

    private void renderRenderables(GraphicsContext gc) {
        //this.sortEntitiesByYPos();
        ArrayList<Renderable> renderables = this.getSortedRenderableList();
        for (Renderable r : renderables) {
            r.render(gc);
        }
    }
    private ArrayList<Renderable> getSortedRenderableList() {
        ArrayList<Renderable> renderables = new ArrayList<>();
        renderables.addAll(this.entities);
        renderables.addAll(this.areaObjectController.getAreaObjects());
        renderables.sort((e1, e2) -> {
            if (e1.getBaseY() == e2.getBaseY()) {
                return 0;
            } else if (e1.getBaseY() > e2.getBaseY()) {
                return 1;
            } else {
                return -1;
            }
        });
        return renderables;
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
