package net.wforbes.platformer.entity;

import net.wforbes.game.Game;
import net.wforbes.platformer.tileMap.Tile;
import net.wforbes.platformer.tileMap.TileMap;

import java.awt.*;

//Super class for all kinds of shit!
//		the root of all gameplay objects: players, enemies, projectiles, all that.
public abstract class MapObject {

	protected TileMap tileMap;
	protected int tileSize;
	protected double xmap, ymap;
	
	//position and vector
	protected double x, y, dx, dy;
	
	//dimensions
	protected int width, height;
	
	//collision box
	protected int cwidth, cheight;
	
	//collision
	protected int currRow;
	protected int currCol;
	protected double xdest, ydest; //destinations
	protected double xtemp, ytemp;
	protected boolean topLeft, topRight, bottomLeft, bottomRight;
	
	//animation
	protected Animation animation;
	protected int currentAction, previousAction;
	protected boolean facingRight;
	
	//movement
	protected boolean left, right, up, down, jumping, falling;
	
	//movement attributes
	protected double moveSpeed, maxSpeed, stopSpeed, fallSpeed;
	protected double maxFallSpeed, jumpStart, stopJumpSpeed;
	
	public MapObject(TileMap tm){
		tileMap = tm;
		tileSize = tm.getTileSize();
	}
	
	public boolean intersects(MapObject o){
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}
	
	public Rectangle getRectangle(){
		return new Rectangle( (int) x - cwidth, (int)y - cheight, cwidth, cheight);
	}
	
	public void calculateCorners(double x, double y){
		
		int leftTile = (int)(x - cwidth / 2) / tileSize;
		int rightTile = (int)(x + cwidth / 2 - 1) / tileSize;
		int topTile =(int)(y - cheight / 2) / tileSize;
		int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;

		System.out.println(topTile);
		System.out.println(leftTile);
		System.out.println(rightTile);
		System.out.println(bottomTile);

		int tl = tileMap.getType(topTile, leftTile);
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);
		
		topLeft = tl == Tile.BLOCKED;
		topRight = tr == Tile.BLOCKED;
		bottomLeft = bl == Tile.BLOCKED;
		bottomRight = br == Tile.BLOCKED;
	}
	
	public void checkTileMapCollision(){
		
		currCol = (int)x / tileSize;
		currRow = (int)y / tileSize;
		
		xdest = x + dx;
		ydest = y + dy;
		
		xtemp = x;
		ytemp = y;
		
		calculateCorners(x, ydest);
		
		//NOTE 1: Break each of these 'if(){if(){}}' type expressions into helper methods called
		//		checkCollisionSide() or something, they all use: dx/dy; top/bottomRight/Left,
		//		currRow/Col, x/ytemp tileSize, cheight/width in generally the same format.
		//		-It would just seem that currRow gets incremented on checks that involve the
		//			dx or dy > versions, so perhaps these will need to be two sister methods.
		//			Think on it.
		//(see NOTE 1)
		if(dy < 0){
			if(topLeft || topRight){
				dy = 0;
				ytemp = currRow * tileSize + cheight / 2;
			}
			else{
				ytemp += dy;
			}
		}//end if dy is less than 0
		//(see NOTE 1)
		if(dy > 0 ){
			if(bottomLeft || bottomRight){
				dy = 0;
				falling = false;
				ytemp = (currRow + 1) * tileSize - cheight / 2;
				
			}
			else{
				ytemp += dy;
			}	
		}//end if dy is greater than zero
		
		
		calculateCorners(xdest, y);
		//see NOTE 1
		if(dx < 0){
			if(topLeft || bottomLeft){
				dx = 0;
				xtemp = currCol * tileSize + cwidth / 2;
			}
			else {
				xtemp += dx;
			}
		}//end if dx is less than zero
		//see NOTE 1
		if(dx > 0 ){
			if(topRight || bottomRight){
				dx = 0;
				xtemp = (currCol + 1) * tileSize - cwidth / 2;
			}
			else {
				xtemp += dx;
			}
		}//end of dx is greater than zero
		
		if(!falling){
			calculateCorners(x, ydest + 1);
			if(!bottomLeft && !bottomRight){
				falling = true;
			}
		}
	}
	
	public int getx(){ return (int)x; }
	public int gety(){ return (int)y; }
	public double getDoublex() { return x; }
	public double getDoubley(){ return y; }
	public int getWidth(){ return width; }
	public int getHeight(){ return height; }
	public int getCWidth(){ return cwidth; }
	public int getCHeight(){ return cheight; }
	public double getMoveSpeed(){return moveSpeed;}
	public double getMaxSpeed(){return maxSpeed;}
	
	public void setPosition( double x, double y){
		this.x = x;
		this.y = y;
	}
	public void setVector( double dx, double dy)
	{
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setMapPosition(){
		xmap = tileMap.getx();
		ymap = tileMap.gety();
	}
	
	public void setLeft(boolean b){ left = b; }
	public void setRight(boolean b){ right = b; }
	public void setUp(boolean b){ up = b; }
	public void setDown(boolean b){ down = b; }
	public void setJumping(boolean b){ jumping = b;}
		
	
	public boolean notOnScreen(){
		return x + xmap + width < 0 ||
				x + xmap - width > Game.WIDTH ||
				y + ymap + height < 0 ||
				y + ymap - height > Game.HEIGHT;
	}
	
	public void draw(Graphics2D g){
		if(!notOnScreen()){
		if(facingRight){
			g.drawImage( animation.getImage(), (int)(x + xmap - width / 2), (int)(y + ymap - height / 2), null ); 
		}else{
			g.drawImage(animation.getImage(), (int)(x + xmap - width / 2 + width), (int)(y + ymap - height / 2), -width, height, null );
		}//if facing right, else left
	}//end if not not on screen lawl
	}//end draw method
}



















