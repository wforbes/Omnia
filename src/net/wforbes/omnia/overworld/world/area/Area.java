package net.wforbes.omnia.overworld.world.area;

import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.overworld.entity.DocNPC;
import net.wforbes.omnia.overworld.entity.NPC;
import net.wforbes.omnia.overworld.world.World;
import net.wforbes.omnia.overworld.world.area.tile.TileMap;

import java.util.ArrayList;

public class Area {

    private World world;
    private TileMap tileMap;
    public ArrayList<NPC> npcs;

    public Area(World world) {
        this.world = world;
        this.npcs = new ArrayList<>();
    }

    public TileMap getTileMap() {
        return this.tileMap;
    }

    public void init() {
        this.initTileMap();
        this.world.player.setPosition(60,60);
        this.initNPCs();
    }

    private void initNPCs() {
        //TODO: find a better way to init many NPCs in one generic step
        npcs.add(new DocNPC(world.gameState));
        npcs.get(0).setPosition(80, 80);
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
        this.tileMap.update(world.player);
    }

    public void render(GraphicsContext gc) {
        this.tileMap.render(gc);
        renderNPCs(gc);
    }

    private void renderNPCs(GraphicsContext gc) {
        for(NPC npc:this.npcs) {
            npc.render(gc);
        }
    }
}
