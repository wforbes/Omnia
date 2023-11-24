package net.wforbes.omnia.overworld.world.area.object.flora.tree;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.world.area.object.flora.Flora;

import java.util.HashMap;

public class Tree extends Flora {

    protected static final String TREES_SPRITE_DIR = FLORA_SPRITE_DIR + "trees/";

    public HashMap<String, TreeType> treeMap;

    private class TreeType {
        String spritePath;
        Point2D collisionCenter;
        Dimension2D collisionDimension;
        public TreeType() {}
    }
    //fixed_Tree1
    //31,66
    //30x15

    public Tree(OverworldState gameState) {
        super(gameState);
    }

    public Tree(OverworldState gameState, float x, float y) {
        super(gameState, x, y);
        this.loadSprite(TREES_SPRITE_DIR+"fixed_Tree1.png");
        this.x = x;
        this.y = y;
        this.collisionRadius = 60;
    }

    public Tree(OverworldState gameState, String spriteName, float x, float y) {
        super(gameState, TREES_SPRITE_DIR+"/"+spriteName+".png", x, y);
        this.x = x;
        this.y = y;
    }
}
