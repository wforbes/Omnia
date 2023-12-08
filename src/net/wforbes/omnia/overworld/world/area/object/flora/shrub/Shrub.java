package net.wforbes.omnia.overworld.world.area.object.flora.shrub;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.gui.loot.Loot;
import net.wforbes.omnia.overworld.world.area.object.flora.Flora;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Shrub extends Flora {

    private Loot loot;
    protected static final String SHRUBS_SPRITE_DIR = FLORA_SPRITE_DIR + "shrubs/";

    public Shrub(OverworldState gameState, ShrubType.GENERA type, float x, float y) {
        super(gameState, x, y);
        String sprite_dir = this.getSpriteDirFromDB();
        ShrubType shrubType = new ShrubType(type, this.gameState.db);
        this.loot = shrubType.getRandomLootInstance(this.gameState.db);
        //TODO: update db to include trailing slash in sprite_dir
        this.loadSprite(sprite_dir+"/"+shrubType.getSpriteFile());
        this.x = x;
        this.y = y;
        this.collisionRadius = 16;
    }

    private String getSpriteDirFromDB() {
        String sql = "SELECT sprite_dir FROM flora WHERE name='shrub' LIMIT 1;";
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

    public void init() {
        this.initCollisionShape();
    }

    @Override
    public double getCollisionRadius() {
        return this.collisionRadius;
    }

    public void init(double x, double y) {
        this.initCollisionShape();
    }

    private void initCollisionShape() {
        this.collision_baseX = -2; //(w/2)-??
        System.out.println("shrub init h: " + this.height);
        this.collision_baseY = 7; //h-spriteOffsetY-??
        this.baseY = this.collision_baseY;
        this.collisionRadius = 17;
        this.collision_baseCenterPnt = new Point2D(collision_baseX, collision_baseY);
        this.collision_baseCircle = new Circle(collision_baseX, collision_baseY, collisionRadius);
    }

    public void completeHarvest() {
        System.out.println("Shrub.completeHarvest");
        this.completeAction();
    }

    public void completeAction() {
        System.out.println("Shrub.completeAction");
    }
    public Loot getLoot() {
        System.out.println("Shrub.getLoot");
        return this.loot;
    }
}
