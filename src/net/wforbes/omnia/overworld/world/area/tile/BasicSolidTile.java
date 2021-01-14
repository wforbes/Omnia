package net.wforbes.omnia.overworld.world.area.tile;

public class BasicSolidTile extends BasicTile{
    public BasicSolidTile(int id, int x, int y, int mapColor) {
        super(id, x, y, mapColor);
        this.type = Tile.BLOCKED;
    }
}
