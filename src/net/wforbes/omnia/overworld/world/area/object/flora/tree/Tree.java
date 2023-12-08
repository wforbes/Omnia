package net.wforbes.omnia.overworld.world.area.object.flora.tree;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.gui.loot.Loot;
import net.wforbes.omnia.overworld.world.area.object.flora.Flora;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Tree extends Flora {

    protected static final String TREES_SPRITE_DIR = FLORA_SPRITE_DIR + "trees/";

    //fixed_Tree1
    //31,66
    //30x15
    private Loot loot;

    public Tree(OverworldState gameState) {
        super(gameState);
    }

    public Tree(OverworldState gameState, float x, float y) {
        super(gameState, x, y);
        this.loadSprite(TREES_SPRITE_DIR+"fixed_Tree1.png");
        this.x = x;
        this.y = y;
        //this.collisionRadius = 60;
    }

    public Tree(OverworldState gameState, TreeType.GENERA type, float x, float y) {
        super(gameState, x, y);
        String sprite_dir = this.getSpriteDirFromDB();
        TreeType treeType = new TreeType(type, this.gameState.db);
        this.loot = treeType.getRandomLootInstance(this.gameState.db);
        //TODO: update db to include trailing slash in sprite_dir
        this.loadSprite(sprite_dir+"/"+treeType.getSpriteFile());
        this.x = x;
        this.y = y;
    }

    public Tree(OverworldState gameState, String spriteName, float x, float y) {
        super(gameState, TREES_SPRITE_DIR+spriteName+".png", x, y);
        System.out.println(spriteName);
        this.x = x;
        this.y = y;
    }

    @Override
    public void init() {
        System.out.println("Tree initialized");
        this.initCollisionShape();
    }
    @Override
    public void init(double x, double y) {
        this.initCollisionShape();
    }

    private void initCollisionShape() {
        this.collision_baseX = 21; //(w/2)-??
        System.out.println("tree init h: " + this.height);
        this.collision_baseY = this.height-14-1; //h-spriteOffsetY-??
        this.baseY = this.collision_baseY;
        this.collisionRadius = 21;
        this.collision_baseCenterPnt = new Point2D(collision_baseX, collision_baseY);
        this.collision_baseCircle = new Circle(collision_baseX, collision_baseY, collisionRadius);
    }

    private String getSpriteDirFromDB() {
        String sql = "SELECT sprite_dir FROM flora WHERE name='tree' LIMIT 1;";
        try {
            Statement statement = this.gameState.db.connection.createStatement();
            ResultSet results = statement.executeQuery(sql);
            results.next();
            System.out.println(results.getString("sprite_dir"));
            return results.getString("sprite_dir");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return "";
    }

    public void completeHarvest() {
        System.out.println("Tree.completeHarvest");
        this.completeAction();
    }

    public void completeAction() {
        System.out.println("Tree.completeAction");
    }

    public Loot getLoot() {
        System.out.println("Tree.getLoot");
        return this.loot;
    }
}
