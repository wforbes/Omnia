package net.wforbes.omnia.platformer.tileMap;

import net.wforbes.omnia.game.Game;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileMap {

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

	//map
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;

	//tileset
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[][] tiles;

	//drawing
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;

	public TileMap(int tileSize){
		this.tileSize = tileSize;
		//since the game is 240 height and tile is 30
		numRowsToDraw = Game.getHeight() / tileSize + 2;
		numColsToDraw = Game.getWidth() / tileSize + 2;
		tween = 0.07;
	}

	public void loadTiles(String path){
		try {
			//get the tileMap image
			tileset = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
			//get the number of tiles across - 20 tiles across
			numTilesAcross = tileset.getWidth() / tileSize;
			//this is a representation of the tileset
			tiles = new Tile[2][numTilesAcross];

			BufferedImage subimage;
			//import entire tileset - if it's in the first row (row 0), then the tile is marked
			//		as normal...if it's in the second row, it's marked as blocked.
			for(int col = 0; col < numTilesAcross; col++){
				subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
				tiles[0][col] = new Tile(subimage, Tile.NORMAL);
				subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
				tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void loadMap(String s){
		try{
			// to read the image..
			InputStream in = getClass().getResourceAsStream(s);
			// to read the text file..
			BufferedReader br = new BufferedReader( new InputStreamReader(in));

			//The first two integers in the text file represent the number of columns
			//		and then the number of rows, in that order. Each on separate lines
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());

			//The rest of the map is put into an int array named map
			map = new int[numRows][numCols];
			//using the number of rows and columns we got in the first two lines
			//		of the text file, we can decide the width and height of the map
			width = numCols * tileSize;
			height = numRows * tileSize;
			
			xmin = Game.getWidth() - width;
			xmax = 0;
			ymin = Game.getHeight() - height;
			ymax = 0;

			String delims = "\\s+"; //this represents white space, so.. the space
			//		between the numbers in the file we are about to read through
			// 	with our buffered reader (like regx.. whatever that is from (c++ ?))
			for(int row = 0; row < numRows; row++){//for every row in the text file
				String line = br.readLine(); // read the line
				String[] tokens = line.split(delims); // split the line into tokens, using the
				//		white space of our delimiter
				for(int col = 0; col < numCols; col++){//for every column
					map[row][col] = Integer.parseInt(tokens[col]); // go through
					//		the token array and put it into the map array
				}// end column loop
			}//end row loop
		}catch(Exception e){e.printStackTrace();}
	}//end loadMap
	//getset
	public int getTileSize(){ return tileSize;}
	public double getx(){ return x;}
	public double gety(){return y;}
	public int getWidth(){return width;}
	public int getHeight(){ return height;}
	public int getType(int row, int col){
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
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
		//include the tween to graduate the movement of the camera as
		//it follows the player to display as a smooth motion
		this.x += (x - this.x)* tween;
		this.y += (y - this.y)* tween;
	
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

	//TODO: Scaled - 1/3
	public void draw(FXGraphics2D fxg) {
		TileDraw td  = (int r, int c, int col, int row) -> {
			fxg.drawImage(tiles[r][c].getImage(), (int)(x + col * tileSize) * Game.getScale(),
					(int)(y + row * tileSize) * Game.getScale(),
					tileSize * Game.getScale(), tileSize * Game.getScale(), null);
		};
		this.draw(td);
	}

	public void draw(Graphics2D g){
		TileDraw td  = (int r, int c, int col, int row) -> {
			g.drawImage(tiles[r][c].getImage(), (int)x + col * tileSize, (int)y+ row * tileSize, null);
		};

		this.draw(td);
	}

	private void draw(TileDraw td) {
		for(int row = rowOffset; row < (rowOffset + numRowsToDraw); row++) {

			if (row >= numRows) break; //nothing else to draw, so break it

			for (int col = colOffset; col < (colOffset + numColsToDraw); col++) {

				if (col >= numCols) break; //nothing else to draw break!
				if (map[row][col] == 0) continue; //index tile is left blank. Just because swag.

				int rc = map[row][col];//find which tile to draw
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;

				td.drawTile(r, c, col, row);
			}
		}
	}

	interface TileDraw {
		void drawTile(int r, int c, int col, int row);
	}
}

