package net.wforbes.omnia.overworld.world.area.tile;

import javafx.geometry.Point2D;

public abstract class Tile {
    public static final Tile[] tiles = new Tile[256];
    public static final Tile VOID = new BasicSolidTile(0, -1, -1, 0xFF000000);
    public static final Tile GRASS = new BasicTile(1, 0, 0, 0xFF00FF00);
    public static final Tile GRASS2 = new BasicTile(2, 1, 0, 0xFF00EE00);
    public static final Tile GRASS3 = new BasicTile(3, 2, 0, 0xFF00DD00);
    public static final Tile GRASS4 = new BasicTile(4, 3, 0, 0xFF00CC00);

    protected byte id;
    //protected int tileId;
    protected Point2D spritePos;
    protected int type;
    private int mapColor;

    //types
    public static final int NORMAL = 0;
    public static final int BLOCKED = 1;
    public static final int WATER = 2;
    public static final int LAVA = 1;

    public Tile(int id, int type, int mapColor) {
        this.id = (byte) id;
        if(tiles[id] != null)
            throw new RuntimeException("Duplicate tile id on " + id);
        this.type = type;
        this.mapColor = mapColor;
        tiles[id] = this;
    }

    public byte getId(){ return id;}
    public Point2D getSpritePos(){ return spritePos; }
    public int getType(){return type;}
    public int getMapColor(){ return mapColor; }

    public abstract void update();
}

