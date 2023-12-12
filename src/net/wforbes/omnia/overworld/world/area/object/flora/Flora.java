package net.wforbes.omnia.overworld.world.area.object.flora;

import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.action.harvest.Harvestable;
import net.wforbes.omnia.overworld.gui.loot.LootTimer;
import net.wforbes.omnia.overworld.world.area.object.AreaObject;

public abstract class Flora extends AreaObject implements Harvestable {
    protected static final String FLORA_SPRITE_DIR = "/overworld/terrain/flora/";
    public boolean harvested = false;
    protected LootTimer lootTimer;

    /*public Flora(OverworldState gameState) {
        super(gameState);
    }*/
    public Flora(OverworldState gameState, float x, float y) {
        super(gameState, x, y);
    }

    /*public Flora(OverworldState gameState, String path, float x, float y) {
        super(gameState, path, x, y);
    }*/

    //public void init(double x, double y) {}

    public void update() {
        super.update();
    }
}
