package net.wforbes.omnia.overworld.world.area.object.flora.shrub;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.gui.item.Item;
import net.wforbes.omnia.overworld.gui.loot.Loot;
import net.wforbes.omnia.overworld.gui.loot.LootTimer;
import net.wforbes.omnia.overworld.world.area.object.flora.Flora;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Shrub extends Flora {

    public Shrub(OverworldState gameState, ShrubType.TYPES type, float x, float y) {
        super(gameState, x, y);
        String sprite_dir = this.getSpriteDirFromDB("shrub");
        this.areaObjectType = new ShrubType(type, this.gameState.db);
        this.generateAndSetLoot();
        this.lootTimer = new LootTimer(this);
        //TODO: update db to include trailing slash in sprite_dir
        this.loadSprite(sprite_dir+"/"+areaObjectType.getSpriteFile());
        this.x = x;
        this.y = y;
        this.collisionRadius = 16;
    }

    protected void initCollisionShape() {
        this.collision_baseX = -2; //(w/2)-??
        //System.out.println("shrub init h: " + this.height);
        this.collision_baseY = 7; //h-spriteOffsetY-??
        this.baseY = this.collision_baseY;
        this.collisionRadius = 17;
        this.collision_baseCenterPnt = new Point2D(collision_baseX, collision_baseY);
        this.collision_baseCircle = new Circle(collision_baseX, collision_baseY, collisionRadius);
    }

    @Override
    public void setLoot(Loot loot) {
        this.loot = loot;
    }

    @Override
    protected void spawn() {
        super.spawn();
        this.resetHarvestState();
        this.generateAndSetLoot();
    }


}
