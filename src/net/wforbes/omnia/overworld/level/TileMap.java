package net.wforbes.omnia.overworld.level;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import net.wforbes.omnia.gameFX.OmniaFX;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileMap {

    private Image tileSet;
    private Image tileImage;

    //tiles
    private Tile[][] tiles;
    private int tileColCount;
    private int tileRowCount;

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

    public void loadTiles(String path) {
        try {
            tileSet = new Image(getClass().getResourceAsStream(path));
            tileColCount = (int)tileSet.getWidth() / tileSize;
            tileRowCount = (int)tileSet.getHeight() / tileSize;
            tiles = new Tile[tileColCount][tileRowCount];
            this.loadTileSet();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    private void loadTileSet() {
        for(int row = 0; row < tileRowCount; row++) {
            for(int col = 0; col < tileColCount; col++) {
                tileImage = new WritableImage(tileSet.getPixelReader(), col * tileSize, row * tileSize, tileSize, tileSize);
                tiles[col][row] = new Tile(tileImage, Tile.NORMAL);
            }
        }
    }
    public void loadMap(String s) {
        try{
            // to load the file
            InputStream in = getClass().getResourceAsStream(s);
            // to read the file..
            BufferedReader br = new BufferedReader( new InputStreamReader(in));
            //The first two integers in the text file represent the number of columns
            //		and then the number of rows, in that order. Each on separate lines
            numCols = Integer.parseInt(br.readLine());
            numRows = Integer.parseInt(br.readLine());
            //The rest of the map is put into an int array named map
            map = new int[numCols][numRows];
            //using the number of rows and columns we got in the first two lines
            //		of the text file, we can decide the width and height of the map
            width = numCols * tileSize;
            height = numRows * tileSize;

            xmin = OmniaFX.getWidth() - width;
            xmax = 0;
            ymin = OmniaFX.getHeight() - height;
            ymax = 0;

            String delims = "\\s+"; //this represents white space, so.. the space
            //		between the numbers in the file we are about to read through
            // 	with our buffered reader (like regx.. whatever that is from (c++ ?))
            for(int row = 0; row < numRows; row++){//for every row in the text file
                String line = br.readLine(); // read the line
                String[] tokens = line.split(delims); // split the line into tokens,
                //  using the white space of our delimiter
                for(int col = 0; col < numCols; col++){//for every column
                    map[col][row] = Integer.parseInt(tokens[col]); // go through
                    //		the token array and put it into the map array
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public int getTileSize(){ return tileSize;}
    public double getx(){ return x;}
    public double gety(){return y;}
    public int getWidth(){return width;}
    public int getHeight(){ return height;}
    public int getType(int row, int col){
        int rc = map[row][col];
        int r = rc / tileColCount;
        int c = rc % tileColCount; //TODO: figure out which is row and which is col
        return tiles[r][c].getType();
    }
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

    public void render(GraphicsContext gc) {
        for(int row = rowOffset; row < (rowOffset + numRowsToDraw); row++) {

            if (row >= numRows) break; //nothing else to draw, so break it

            for (int col = colOffset; col < (colOffset + numColsToDraw); col++) {
                if (col >= numCols) break; //nothing else to draw break!
                //if (map[row][col] == 0) continue; //For transparent tiles, tileSet must have trans tile in 0

                int rc = map[col][row];//find which tile to draw
                int r = rc / tileRowCount;
                int c = rc % tileColCount;
                gc.drawImage(tiles[c][r].getImage(), (x + col * tileSize) * OmniaFX.getScale(),
                        (y + row * tileSize) * OmniaFX.getScale(),
                        (double)tileSize * OmniaFX.getScale(), (double)tileSize * OmniaFX.getScale());
            }
        }
    }
}
