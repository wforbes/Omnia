package net.wforbes.omnia.overworld.world.area;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameFX.rendering.Renderable;
import net.wforbes.omnia.overworld.entity.Procyon;
import net.wforbes.omnia.overworld.entity.DocNPC;
import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.entity.NPC;
import net.wforbes.omnia.overworld.world.World;
import net.wforbes.omnia.overworld.world.area.effect.EffectController;
import net.wforbes.omnia.overworld.world.area.object.AreaObject;
import net.wforbes.omnia.overworld.world.area.object.AreaObjectController;
import net.wforbes.omnia.overworld.world.area.tile.TileMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Area {
    private final AreaObjectController areaObjectController;
    private World world;
    private TileMap tileMap;
    public List<Entity> entities;
    public double TEST_NPC_XPOS = 200;
    public double TEST_NPC_YPOS = 200;
    public double TEST_ENEMY_XPOS = 140;
    public double TEST_ENEMY_YPOS = 140;
    public EffectController effectController;

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
    public void handleCanvasClick(MouseEvent event) {
        System.out.println("area.handleCanvasClick event: " + event);
    }
    public void init() {
        this.initTileMap();
        this.effectController.init();
        this.areaObjectController.init();
        this.initEntities();
        this.world.player.init(200,150);
    }
    private void initTileMap() {
        this.tileMap = new TileMap(this, 32);
        this.tileMap.loadMapFromImageFile("/overworld/tile/maps/areamap0.gif");
        this.tileMap.loadTilesFromMapImage();
        this.tileMap.loadTileSprites("/overworld/tile/tiles2.gif");
        this.tileMap.setPosition(0, 0);
        this.tileMap.setTween(0.06);
    }
    private void initEntities() {
        NPC testNPC = new DocNPC(world.gameState);
        testNPC.init(TEST_NPC_XPOS, TEST_NPC_YPOS);
        this.addEntity(testNPC);

        Procyon testEnemy = new Procyon(world.gameState, "A Procyon");
        testEnemy.init(TEST_ENEMY_XPOS, TEST_ENEMY_YPOS);
        this.addEntity(testEnemy);
    }

    public boolean spawnSpaceIsBlocked(double x, double y, double collisionBaseX, double collisionBaseY, double collisionRadius) {
        //TODO: Mostly COPYPASTA FROM MOB... combine into a better system
        //TODO: simplify this to iterate through area Renderables
        for(Entity e : entities) {
            System.out.println(e.getName());
            double xDist = (x - e.getX());
            double yDist = (y - e.getY());
            System.out.println(xDist + ", " + yDist);
            System.out.println(Math.floor(Math.sqrt((xDist*xDist) + (yDist*yDist))) + ", " + Math.floor(collisionRadius/2.0+e.getCollisionRadius()/2.0));
            if (
                Math.floor(Math.sqrt((xDist*xDist) + (yDist*yDist)))+collisionRadius <= Math.floor(collisionRadius/2.0+e.getCollisionRadius()/2.0)
                || Math.floor(Math.sqrt((xDist*xDist) + (yDist*yDist)))-collisionRadius <= Math.floor(collisionRadius/2.0+e.getCollisionRadius()/2.0)
            ) {
                return true;
            }
        }

        for (AreaObject ao : getAreaObjects()) {
            if (!ao.isSpawned()) continue;
            System.out.println(ao);
            double xDist = (x+collisionBaseX - collisionRadius/2.0) - (ao.getX()+ao.getCollisionBaseX());
            double yDist = (y+collisionBaseY - collisionRadius/3.0) - (ao.getY()+ao.getCollisionBaseY());
            boolean collided =
                    Math.sqrt((xDist*xDist) + (yDist*yDist))+collisionRadius < ((collisionRadius-2)/2.0+(ao.getCollisionRadius()-3)/2.0)
                    || Math.sqrt((xDist*xDist) + (yDist*yDist)) < ((collisionRadius-2)/2.0+(ao.getCollisionRadius()-3)/2.0)-collisionRadius;
            if (collided) {
                return true;
            }
        }
        System.out.println("spawnSpaceIsBlocked: false");
        return false;
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
            //TODO: idea = create designated minimap controller and pass it data to render
            //  double miniMapWidth = (double) this.getTileMap().getWidth() / (getScale()*2);
            //  double miniMapHeight = (double) this.getTileMap().getWidth() / (getScale()*2);
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
        ArrayList<Renderable> renderables = this.getSortedRenderableList();
        for (Renderable r : renderables) {
            r.render(gc);
        }
    }
    private ArrayList<Renderable> getSortedRenderableList() {
        ArrayList<Renderable> renderables = new ArrayList<>();
        renderables.addAll(this.entities);
        renderables.addAll(this.areaObjectController.getAreaObjects());
        renderables.sort(Comparator.comparingDouble(Renderable::getBaseY));
        return renderables;
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
