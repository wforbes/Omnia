package net.wforbes.omnia.topDown.level.tile;

import net.wforbes.omnia.topDown.graphics.Colors;
import net.wforbes.omnia.topDown.graphics.Screen;
import net.wforbes.omnia.topDown.level.Level;

public abstract class Tile {
    public static final Tile[] tiles = new Tile[256];
    public static final Tile VOID = new BasicSolidTile
            (0, 0, 0, Colors.get(000, -1, -1, -1), 0xFF000000);
    public static final Tile STONE = new BasicSolidTile
            (1, 1, 0, Colors.get(-1, 333, -1, -1), 0xFF555555);
    public static final Tile GRASS = new BasicTile
            (2, 2, 0, Colors.get(-1, 131, 141, -1), 0xFF00FF00);
    public static final Tile WATER = new AnimatedTile
            (3, new int[][] {{0, 5}, {1, 5}, {2, 5}, {1, 5}}, Colors.get(-1, 004, 115, -1), 0xFF0000FF, 500);

    protected byte id;
    protected int tileId;
    protected boolean solid;
    protected boolean water;
    protected boolean emitter;
    private int levelColor;

    public Tile(int id, boolean isSolid, boolean isEmitter, int levelColor)
    {
        this.id = (byte) id;
        if(tiles[id] != null)
            throw new RuntimeException("Duplicate tile id on " + id);
        this.solid = isSolid;
        this.water = (id == 3);
        this.emitter = isEmitter;
        this.levelColor = levelColor;
        tiles[id] = this;
    }
    public byte getId(){ return id;}
    public int getTileId(){ return tileId; }
    public boolean isSolid(){ return solid;}
    public boolean isWater() { return water; }
    public boolean isEmitter(){ return emitter; }
    public int getLevelColor(){ return levelColor; }

    public abstract void tick();
    public abstract void render(Screen screen, Level level, int x, int y);

}
