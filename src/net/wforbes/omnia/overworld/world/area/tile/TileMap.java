package net.wforbes.omnia.overworld.world.area.tile;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.overworld.entity.Entity;

public class TileMap {

    //tiles
    private byte[][] areaTileIds;
    private int tileColCount;
    private int tileRowCount;

    //imageMap
    private Image tileSpriteSheet;
    private Image[][] tileSprites;
    private Image areaMapImage;
    private int mapWidth;
    private int mapHeight;
    private Dimension2D areaMapDimensions;

    //map
    private int[][] map;
    private int tileSize;
    private int numRows;
    private int numCols;
    private int width;
    private int height;

    //position
    private double x;
    private double y;

    //bounds
    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;

    //used to smoothly scroll the camera with the player
    private double tween;

    //drawing
    private int rowOffset;
    private int colOffset;
    private int numRowsToDraw;
    private int numColsToDraw;

    public TileMap(int tileSize) {
        this.tileSize = tileSize;
        this.numColsToDraw = OmniaFX.getWidth() / tileSize + tileSize * 2; //320
        this.numRowsToDraw = OmniaFX.getHeight() / tileSize + 2; //240
    }

    public void loadTileSprites(String path) {
        try {
            tileSpriteSheet = new Image(getClass().getResourceAsStream(path));
            tileColCount = (int) tileSpriteSheet.getWidth() / tileSize;
            tileRowCount = (int) tileSpriteSheet.getHeight() / tileSize;
            tileSprites = new Image[tileColCount][tileRowCount];
            Image tileImage;
            for(int row = 0; row < tileRowCount; row++) {
                for(int col = 0; col < tileColCount; col++) {
                    tileImage = new WritableImage(tileSpriteSheet.getPixelReader(), col * tileSize, row * tileSize, tileSize, tileSize);
                    tileSprites[col][row] = tileImage;
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadMapFromImageFile(String s) {
        try {
            areaMapImage = new Image(getClass().getResourceAsStream(s));
            areaMapDimensions = new Dimension2D(areaMapImage.getWidth(),areaMapImage.getHeight());
            mapWidth = (int)areaMapDimensions.getWidth();
            mapHeight = (int)areaMapDimensions.getHeight();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void loadTilesFromMapImage() {
        areaTileIds = new byte[(int)areaMapDimensions.getWidth()][(int)areaMapDimensions.getHeight()];

        PixelReader pixelReader = this.areaMapImage.getPixelReader();
        int[] tileColors = new int[mapWidth * mapHeight];
        pixelReader.getPixels(0, 0,
            mapWidth, mapHeight,
            PixelFormat.getIntArgbInstance(),
            tileColors, 0, mapWidth
        );

        width = (int)areaMapDimensions.getWidth() * tileSize;
        height = (int)areaMapDimensions.getHeight() * tileSize;

        xmin = OmniaFX.getWidth() - width;
        xmax = 0;
        ymin = OmniaFX.getHeight() - height;
        ymax = 0;

        for(int y = 0; y < mapHeight; y++){
            for(int x = 0; x < mapWidth; x++){

                tileCheck:
                    for (Tile t : Tile.tiles) {
                        if (t != null && t.getMapColor() == tileColors[x + y * mapWidth]) {
                            this.areaTileIds[x][y] = t.getId();
                            break tileCheck;
                        }
                    }
            }
        }
    }

    public int getTileSize(){ return tileSize;}
    public double getx(){ return x;}
    public double gety(){return y;}
    public int getWidth(){return width;}
    public int getHeight(){ return height;}
    /*//TODO: rewrite this for new tileSprites if needed
    public int getType(int row, int col){
        int rc = map[row][col];
        int r = rc / tileColCount;
        int c = rc % tileColCount;
        return tiles[r][c].getType();
    }*/
    public int getMapRowUpperBound() {
        return map.length;
    }
    public int getMapColUpperBound(int row) {
        return map[row].length;
    }
    public void setTween(double tween){
        this.tween = tween;
    }
    public void setPosition(double x, double y){
        double centerX = (OmniaFX.getWidth() / 2.0);
        double centerY = (OmniaFX.getHeight() / 2.0);

        //move the x/y position of the tilemap as
        //  the difference of screen center and
        //  the player position, reduced by the
        //  tilemaps previous position, scaled by
        //  the tween rate
        this.x += (centerX - x - this.x) * tween;
        this.y += (centerY - y - this.y) * tween;

        fixBounds();

        colOffset = (int) -(this.x) / tileSize;
        rowOffset = (int) -(this.y) / tileSize;
    }

    //helper method, explain later
    private void fixBounds(){
        if(x < xmin) x = xmin;
        if(y < ymin) y = ymin;
        if(x > xmax) x = xmax;
        if(y > ymax) y = ymax;
    }

    public void update(Entity e) {
        this.setPosition(e.getX(), e.getY());
    }

    private Image getTileMapSprite(int mapX, int mapY) {
        Tile tile = Tile.tiles[areaTileIds[mapX][mapY]];
        //return the sprite image of the tile
        int sheetX = (int)tile.getSpritePos().getX();
        int sheetY = (int)tile.getSpritePos().getY();
        return this.tileSprites[sheetX][sheetY];
    }
    public Tile getTile(int mapX, int mapY) {
        if(mapX < 0 || mapX >= width || mapY < 0 || mapY >= height)
            return Tile.VOID;
        return Tile.tiles[areaTileIds[mapX][mapY]];
    }

    public void render(GraphicsContext gc) {
        for(int row = rowOffset; row < (rowOffset + numRowsToDraw); row++) {

            if (row >= areaMapDimensions.getHeight()) break; //nothing else to draw

            for (int col = colOffset; col < (colOffset + numColsToDraw); col++) {
                if (col >= areaMapDimensions.getWidth()) break; //nothing else to draw

                gc.drawImage(this.getTileMapSprite(col, row),
                        (x + col * tileSize) * OmniaFX.getScale(),
                        (y + row * tileSize) * OmniaFX.getScale(),
                        (double)tileSize * OmniaFX.getScale(),
                        (double)tileSize * OmniaFX.getScale()
                );

            }
        }
    }
}
