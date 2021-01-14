package net.wforbes.omnia.overworld.world.area.tile;

import javafx.geometry.Point2D;

public class BasicTile extends Tile {

    public BasicTile(int id, int spriteX, int spriteY, int mapColor) {
        super(id, Tile.NORMAL, mapColor);
        this.spritePos = new Point2D(spriteX, spriteY);
    }

    @Override
    public void update() { }
}
