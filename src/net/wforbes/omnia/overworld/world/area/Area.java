package net.wforbes.omnia.overworld.world.area;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameFX.rendering.Renderable;
import net.wforbes.omnia.overworld.entity.EntityController;
import net.wforbes.omnia.overworld.entity.mob.enemy.Procyon;
import net.wforbes.omnia.overworld.entity.mob.npc.DocNPC;
import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.entity.mob.npc.NPC;
import net.wforbes.omnia.overworld.world.World;
import net.wforbes.omnia.overworld.world.area.effect.EffectController;
import net.wforbes.omnia.overworld.world.area.object.AreaObject;
import net.wforbes.omnia.overworld.world.area.object.AreaObjectController;
import net.wforbes.omnia.overworld.world.area.structure.Structure;
import net.wforbes.omnia.overworld.world.area.structure.StructureController;
import net.wforbes.omnia.overworld.world.area.structure.StructureDoor;
import net.wforbes.omnia.overworld.world.area.tile.TileMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Area {
    private World world;
    protected EntityController entityController;
    protected AreaObjectController areaObjectController;
    protected StructureController structureController;
    public EffectController effectController;
    private TileMap tileMap;

    public Area(World world) {
        this.world = world;
        this.entityController = new EntityController(this);
        this.areaObjectController = new AreaObjectController(this);
        this.structureController = new StructureController(this);
        this.effectController = new EffectController(this);
    }

    public World getWorld(){ return this.world; }
    public TileMap getTileMap() {
        return this.tileMap;
    }
    public int getAreaMaxX() {
        return this.tileMap.getWidth();
    }
    public int getAreaMaxY() {
        return this.tileMap.getHeight();
    }
    public void addEntity(Entity entity) {
        //this.entities.add(entity);
        this.entityController.addEntity(entity);
    }
    public List<Entity> getEntities() { return this.entityController.getEntities(); }
    public List<AreaObject> getAreaObjects() { return this.areaObjectController.getAreaObjects(); }
    public List<Structure> getStructures() { return this.structureController.getStructures(); }
    public List<StructureDoor> getStructureDoors() { return this.structureController.getStructureDoors(); }
    public List<Entity> getSpirits() { return this.entityController.getSpirits(); }
    public void handleCanvasClick(MouseEvent event) {
        System.out.println("area.handleCanvasClick event: " + event);
    }
    public void init() {
        this.initTileMap();
        this.effectController.init();
        this.structureController.init();
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
        this.entityController.init();
        this.getWorld().getGameState().gui.initEntityGUI();
    }

    public boolean spawnSpaceIsBlocked(double x, double y, double collisionBaseX, double collisionBaseY, double collisionRadius) {
        //TODO: Mostly COPYPASTA FROM MOB... combine into a better system
        //TODO: simplify this to iterate through area Renderables
        for(Entity e : this.entityController.getEntities()) {
            //System.out.println(e.getName());
            double xDist = (x - e.getX());
            double yDist = (y - e.getY());
            //System.out.println(xDist + ", " + yDist);
            //System.out.println(Math.floor(Math.sqrt((xDist*xDist) + (yDist*yDist))) + ", " + Math.floor(collisionRadius/2.0+e.getCollisionRadius()/2.0));
            if (
                Math.floor(Math.sqrt((xDist*xDist) + (yDist*yDist)))+collisionRadius <= Math.floor(collisionRadius/2.0+e.getCollisionRadius()/2.0)
                || Math.floor(Math.sqrt((xDist*xDist) + (yDist*yDist)))-collisionRadius <= Math.floor(collisionRadius/2.0+e.getCollisionRadius()/2.0)
            ) {
                return true;
            }
        }

        for (AreaObject ao : getAreaObjects()) {
            if (!ao.isSpawned()) continue;
            //System.out.println(ao);
            double xDist = (x+collisionBaseX - collisionRadius/2.0) - (ao.getX()+ao.getCollisionBaseX());
            double yDist = (y+collisionBaseY - collisionRadius/3.0) - (ao.getY()+ao.getCollisionBaseY());
            boolean collided =
                    Math.sqrt((xDist*xDist) + (yDist*yDist))+collisionRadius < ((collisionRadius-2)/2.0+(ao.getCollisionRadius()-3)/2.0)
                    || Math.sqrt((xDist*xDist) + (yDist*yDist)) < ((collisionRadius-2)/2.0+(ao.getCollisionRadius()-3)/2.0)-collisionRadius;
            if (collided) {
                return true;
            }
        }
        //System.out.println("spawnSpaceIsBlocked: false");
        return false;
    }

    public void update() {
        this.tileMap.update(world.player);
        for(Entity e: this.entityController.getEntities()) {
            e.update();
        }
        this.areaObjectController.update();
        this.structureController.update();
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
        this.structureController.renderDoorSprites(gc);
        this.renderRenderables(gc);
        this.structureController.renderAboveDoors(gc);

        this.effectController.render(gc);
    }
    private void renderRenderables(GraphicsContext gc) {
        ArrayList<Renderable> renderables = this.getSortedRenderableList();
        for (Renderable r : renderables) {
            r.render(gc);
        }
    }
    public ArrayList<Renderable> getSortedRenderableList() {
        ArrayList<Renderable> renderables = new ArrayList<>();
        renderables.addAll(this.entityController.getEntities());
        renderables.addAll(this.areaObjectController.getAreaObjects());
        renderables.addAll(this.areaObjectController.getCorpses());
        renderables.addAll(this.structureController.getStructures());
        renderables.sort(Comparator.comparingDouble(Renderable::getBaseY));
        return renderables;
    }
    public void teardown() {
        this.world = null;
        this.entityController.teardown();
        this.effectController = new EffectController(this);
    }
}
