package net.wforbes.omnia.overworld.world.area.object.flora.tree;

import javafx.geometry.Dimension2D;
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
import java.util.HashMap;

public class Tree extends Flora {

    //fixed_Tree1
    //31,66
    //30x15

    public Tree(OverworldState gameState, TreeType.TYPES type, int x, int y) {
        super(gameState, x, y);
        String sprite_dir = this.getSpriteDirFromDB("tree");
        this.areaObjectType = new TreeType(type, this.gameState.db);
        this.loot = this.areaObjectType.getRandomLootInstance(this.gameState.db);
        this.lootTimer = new LootTimer(this);
        //TODO: update db to include trailing slash in sprite_dir
        this.loadSprite(sprite_dir+"/"+areaObjectType.getSpriteFile());
        this.x = x;
        this.y = y;
    }

    protected void initCollisionShape() {
        this.collision_baseX = 21; //(w/2)-??
        //System.out.println("tree init h: " + this.height);
        this.collision_baseY = this.height-14-1; //h-spriteOffsetY-??
        this.baseY = this.collision_baseY;
        this.collisionRadius = 21;
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
        this.generateAndSetLoot();
    }
}
