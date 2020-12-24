package net.wforbes.platformer.entity;

import net.wforbes.platformer.tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FireBall extends MapObject{
	
	private boolean hit;
	private boolean remove;
	private BufferedImage spriteSheet;
	private BufferedImage[] sprites; 
	private BufferedImage[] hitSprites; //the images that animate when the fireball hits something
	private Player player;
	
	public FireBall(TileMap tilemap, boolean right, Player player){
		super(tilemap);
		this.player = player;
		facingRight = right;
		moveSpeed = 3.8;
		if(facingRight){
			dx = moveSpeed;
		}else{
			dx = -moveSpeed;
		}
		
		width = 30; 
		height = 30;
		cwidth = 14; 
		cheight = 14;
		
		//load sprites
		try{
			spriteSheet = ImageIO.read(getClass().getResourceAsStream( "/Sprites/Player/spellfire.gif"));
			sprites = new BufferedImage[4];
			for(int i = 0; i < sprites.length; i++)
				sprites[i] = spriteSheet.getSubimage( i*width, 0, width, height);
			
			hitSprites = new BufferedImage[3];
			for(int i = 0; i < hitSprites.length; i++)
				hitSprites[i] = spriteSheet.getSubimage( i * width, height, width, height);
			
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(70);
			
		}catch(Exception e){ e.printStackTrace();}
	}
	
	public void setHit(){ //gets called to figure out whether or not the fireball has hit something
		
		if(hit) return;
		hit = true;
		animation.setFrames(hitSprites);
		animation.setDelay(70);
		dx = 0;
	}
	
	public boolean shouldRemove(){
		return remove;
	}
	
	public void update(){
		
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		animation.update();
		
		if(hit && animation.hasPlayedOnce()){
			remove = true;
		}
		
		//if the fireball is no longer moving, by way of hitting object or wall...
		//		and it's not hit already, set it to hit and call the hit animation!
		if(dx == 0 && !hit){
			setHit();
		}
		
		
	}
	
	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}
}
