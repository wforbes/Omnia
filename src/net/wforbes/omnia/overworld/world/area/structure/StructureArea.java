package net.wforbes.omnia.overworld.world.area.structure;

import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.gameFX.rendering.Renderable;
import net.wforbes.omnia.overworld.world.World;
import net.wforbes.omnia.overworld.world.area.Area;

import java.util.ArrayList;
import java.util.Comparator;

public class StructureArea extends Area {
    public StructureArea(World world) {
        super(world);
        this.areaObjectController = new StructureAreaObjectController(this);
    }

    //TODO: adding StructureArea
    public void init() {
        //this.initTileMap();
        //this.effectController.init();
        //this.areaObjectController.init()
        //this.initEntities();
        //this.world.player.... setNewPlayerPosition ??
    }

    //TODO: adding StructureArea
    private void initTileMap() {
        //this.tileMap = new TileMap(this, 32);
        //this.tileMap.loadMapFromImageFile("/overworld/tile/maps/areamap0_housewide1.gif");
        //this.tileMap.loadTilesFromMapImage();
        //this.tileMap.loadTileSprites("/overworld/tile/tiles2_housewide1.gif");
        //this.tileMap.setPosition(0, 0);
        //this.tileMap.setTween(0.06);
    }

    //TODO: adding StructureArea
    private void initEntities() {
        // instantiate/add a couple npcs
        // TODO: figure out switching entityGUI to new set of entities
        // this.getWorld().getGameState().gui.initEntityGUI();
    }

    //TODO: adding StructureArea
    public void update() {
        //this.tileMap.update(world.player)
        //update each entity
        //areaObjectController.update()
    }

    //TODO: adding StructureArea
    public void render(GraphicsContext gc) {
        //this.tileMap.render(gc);
        //this.renderRenderables(gc);
        //this.effectController.render(gc); ??
    }

    @Override
    public ArrayList<Renderable> getSortedRenderableList() {
        ArrayList<Renderable> renderables = new ArrayList<>();
        renderables.addAll(this.entityController.getEntities());
        renderables.addAll(this.areaObjectController.getAreaObjects());
        renderables.addAll(this.areaObjectController.getCorpses());
        renderables.sort(Comparator.comparingDouble(Renderable::getBaseY));
        return renderables;
    }
}
