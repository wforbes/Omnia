package net.wforbes.omnia.overworld.world.area.object.flora;

import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.action.harvest.Harvestable;
import net.wforbes.omnia.overworld.gui.item.Item;
import net.wforbes.omnia.overworld.gui.loot.Loot;
import net.wforbes.omnia.overworld.gui.loot.LootTimer;
import net.wforbes.omnia.overworld.world.area.object.AreaObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Flora extends AreaObject implements Harvestable {
    protected static final String FLORA_SPRITE_DIR = "/overworld/terrain/flora/";
    protected boolean harvested = false;
    protected float harvestProgress = 0;
    protected LootTimer lootTimer;
    protected Loot loot;

    public Flora(OverworldState gameState, int x, int y) {
        super(gameState, x, y);
    }

    public void init() {
        //System.out.println(this + " initialized");
        this.initCollisionShape();
    }
    protected abstract void initCollisionShape();

    public void update() {
        super.update();
        this.lootTimer.update();
    }

    protected String getSpriteDirFromDB(String name) {
        String sql = "SELECT sprite_dir FROM flora WHERE name='" + name + "' LIMIT 1;";
        try {
            Statement statement = this.gameState.db.connection.createStatement();
            ResultSet results = statement.executeQuery(sql);
            results.next();
            return results.getString("sprite_dir");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return "";
    }

    //TODO: removal candidate
    public void completeHarvest() {
        System.out.println(this + ".completeHarvest");
        this.completeAction();
    }

    @Override
    public boolean isHarvested() {
        return this.harvested;
    }

    @Override
    public void resetHarvestState() {
        this.harvested = false;
    }

    public void completeAction() {
        System.out.println(this + ".completeAction");
        this.harvested = true;
    }

    public void generateAndSetLoot() {
        this.setLoot(areaObjectType.getRandomLootInstance(this.gameState.db));
    }

    public Loot getLoot() {
        System.out.println(this + ".getLoot");
        for (Item item : loot.getItems()) {
            System.out.println(item.getName());
        }
        return this.loot;
    }

    public void returnLoot(Loot loot) {
        if (loot.getItems().isEmpty()) {
            System.out.println("No loot returned to " + this + ".");
            this.lootTimer.endEarly();
            return;
        }
        System.out.println("Setting " + this + " loot: ");
        for (Item item : loot.getItems()) {
            System.out.println(item.getName());
        }
        this.lootTimer.start();
        this.loot = loot;
    }
    public void notifyLootTimerDone() {
        System.out.println(this + " loot timer done");
        //this.flaggedForDespawn = true;
        this.despawn();
    }
}
