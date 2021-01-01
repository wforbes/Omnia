package net.wforbes.omnia.platformer.entity.enemies;

import net.wforbes.omnia.platformer.entity.Animation;
import net.wforbes.omnia.platformer.entity.Enemy;
import net.wforbes.omnia.platformer.tileMap.TileMap;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Slugger extends Enemy{

	private BufferedImage[] sprites;
	
	public Slugger(TileMap tm ){
		super(tm);

		moveSpeed = 0.1;
		maxSpeed = 5.5;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;

		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;

		health = maxHealth = 2;
		damage = 1;
		
		facingRight = true;
		
		//load sprites
		try{
			BufferedImage spritesheet = ImageIO.read( getClass().getResourceAsStream("/Sprites/Enemies/slugger.gif"));
			
			sprites = new BufferedImage[3];
			for( int i = 0; i < sprites.length; i++){
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
			
			
			
		}catch(Exception e){ e.printStackTrace(); }
	
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(30);
		
		right = true;
	}

	private void getNextPosition(){
		//slugger is going to walk in one direction until it hits a wall, 
		//    then it will walk in another direction
		if(left){
			dx -= moveSpeed;
			if(dx < -maxSpeed){
				dx = -maxSpeed;
			}//end if dx < -maxSpeed
		}//end if left
		else if(right){
			dx += moveSpeed;
			if(dx > maxSpeed){
				dx = maxSpeed;
			}//end if dx < maxSpeed
		}//end if right
	
		if(falling){
			dy += fallSpeed;
		}
		
	}
		
	
	public void update(){
		
		//update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(flinching)
		{
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 400){
				flinching = false;
			}
		}
		
		//if it hits a wall, go other direction
		if(right && dx == 0){//if sluggers facing right and sluggers dx is zero (dx will equal zero when it hits a wall)
			right = false;//it's no longer facing right
			left = true;// it's facing left
			facingRight = false;
		}
		else if(left && dx == 0){//and the other way..
			right = true;
			left = false;
			facingRight = true;
		}
			
		animation.update();
		
	}

	public void draw(FXGraphics2D fxg){
		if(notOnScreen()) return; //if they arent in the camera view, don't render them
		setMapPosition();
		super.draw(fxg);
	}

	public void draw(Graphics2D g){
		if(notOnScreen()) return; //if they arent in the camera view, don't render them
		setMapPosition();
		super.draw(g);
		
	}
	
	
}
