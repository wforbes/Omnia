package net.wforbes.omnia.platformer.entity;

import javafx.scene.input.KeyCode;
import net.wforbes.omnia.gameState.PlatformerState;
//import net.wforbes.omnia.input.InputHandler;
import net.wforbes.omnia.platformer.audio.AudioPlayer;
import net.wforbes.omnia.platformer.tileMap.TileMap;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Player extends MapObject{

	private PlatformerState gs;
	private int scale;
	//private InputHandler inputHandler;
	private int tickCount = 0;
	private int lastInputCommandTick = 0;

	//player stuff
	private int killCount;
	private int health;
	private int maxHealth;
	private int fire;
	private int maxFire;
	public boolean isDead;
	private boolean flinching;
	private int flinchTimer;

	//fireball attack
	private boolean firing;//for keyboard input
	private int fireCost;
	private int fireBallDamage;
	private ArrayList<FireBall> fireBalls;
	private int fireCount;
	
	//scratch attack
	private boolean scratching;
	private int scratchDamage;
	private int scratchRange;
	private int scratchCount;

	//gliding
	private boolean gliding;
	private int glideCount;
	
	//phasing
	private boolean phasing;
	private int phaseCount;

	//interaction stuff
	private Enemy e;

	//animations
	private ArrayList<BufferedImage[]> sprites;//this holds all the animation arrays
	private final int[] numFrames = {//number of frames in each of the animation actions
			4, 8, 1, 2, 4, 7, 5, 8
	};

	//animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int GLIDING = 4;
	private static final int FIREBALL = 5;
	private static final int SCRATCHING = 6;
	private static final int PHASING = 7;

	private HashMap<String, AudioPlayer> sfx;

	public Player(TileMap tm, PlatformerState gameState){
		super(tm);//sets tileMap and tileSize
		this.gs = gameState;
		//this.inputHandler = gameState.gsm.inputHandler;

		this.scale = 1;

		//find a way to save these variables elsewhere or find
		//		math functions to determine them
		width = 30;
		height = 30;
		cwidth = 20 ;
		cheight = 20;
		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.8;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;

		isDead = false;
		facingRight = true;//TODO:apparently this isn't important ...
		health = maxHealth = 5;
		//fire (spells)
		fire = maxFire = 25000;
		fireCost = 200;
		fireBallDamage = 5;
		fireBalls = new ArrayList<FireBall>();
		//scratch (physical damage)
		scratchDamage = 8;
		scratchRange = 40;

		//load spritesheet
		try{

			//read image information from the sprite image file
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/playersprites1.gif"));

			//not sure if I should move this to the constructor or not. I'm thinking I should...
			sprites = new ArrayList<BufferedImage[]>();

			//extract sprites from the sprite sheet and put them into an array, and then an array of an array
			for(int i = 0; i < 8; i++){//since there are 8 animation actions
				//create new buffered image array, sized by how many frames are involved with each animation
				BufferedImage[] bi = new BufferedImage[numFrames[i]];

				for(int j = 0; j < numFrames[i]; j++){
					if(i != 6){//if it isn't the last sprite
						bi[j] = spritesheet.getSubimage(j*width, i*height, width, height);

					}else{//if it is the last sprite... we need twice the width (slash attack, i think)
						bi[j] = spritesheet.getSubimage(j*width*2, i*height, width * 2, height);
					}
				}
				sprites.add(bi);//add this buffered images array list
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

		//set starting animation
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(20);
	}

	public int getHealth(){ return health; }//for HUD
	public int getMaxHealth (){ return maxHealth; }//for HUD
	public int getFire(){ return fire; }
	public int getMaxFire(){ return maxFire; }
	public void setFiring(){ firing = true; fireCount++; }//for keyboard input
	public void setScratching (){ scratching = true; scratchCount++; }//for keyboard input
	public void setGliding(boolean b){ gliding = b; glideCount++;} //giving this a boolean so that you can stop it at any time
	public void setPhasing(boolean b){ phasing = b; phaseCount++;}

	private void getNextPosition(){
		//movement
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
		else{
			if(dx > 0){
				dx -= stopSpeed;
				if(dx < 0){
					dx = 0;
				}//end if dx < 0
			}//end if dx > 0
			else if (dx < 0){
				dx += stopSpeed;
				if(dx > 0){
					dx = 0;
				}//end if dx > 0
			}//end if dx < 0
		}

		//cannot move while attacking, unless in the air
		if((currentAction == SCRATCHING || currentAction == FIREBALL)
				&& !(jumping || falling)){
			dx = 0;
		}
		//jumping
		if(jumping && !falling){
			dy = jumpStart;
			falling = true;
		}

		//falling
		if(falling){
			if(dy > 0 && gliding) dy+= fallSpeed * 0.1;//gliding has you falling at 10% of normal speed
			else dy += fallSpeed;

			if(dy > 0) jumping = false;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;// makes it so that the long you hold the jump button, the higher you jump
			if(dy > maxFallSpeed) dy = maxFallSpeed; //cap it out at max

		}

	}

	private void regenResources(){
		fire += 50;
	}

	public void update(){
		this.checkKeyInput();

		//update position
		getNextPosition();
		if(checkTileMapCollision() == 'y') {
			this.isDead = true;
			return;
		}
		setPosition(xtemp, ytemp);

		//stop scratching if scratching animation has played once
		if(currentAction == SCRATCHING){
			if(animation.hasPlayedOnce()) scratching = false;
		}
		//stop shooting fireballs if fireballing animation has played once
		if(currentAction == FIREBALL){
			if(animation.hasPlayedOnce()) firing = false;
		}

		regenResources();
		//fireball attack
		if(fire > maxFire) fire = maxFire;
		if(firing && currentAction != FIREBALL){
			if(fire > fireCost){
				fire -= fireCost;
				FireBall fb = new FireBall(tileMap, facingRight, this);
				fb.setPosition(x, y);
				fireBalls.add(fb);
			}
		}

		//update fireballs
		for(int i = 0; i <  fireBalls.size(); i++){
			fireBalls.get(i).update();
			if(fireBalls.get(i).shouldRemove()){
				fireBalls.remove(i);
				i--;
			}
		}

		//set animation
		if(scratching){
			if(currentAction != SCRATCHING){
				currentAction = SCRATCHING;
				animation.setFrames(sprites.get(SCRATCHING));
				animation.setDelay(40);
				width = 60;
			}// end if currently not scratching
		}//end if scratching
		else if(firing){
			if(currentAction != FIREBALL){
				currentAction = FIREBALL;
				animation.setFrames(sprites.get(FIREBALL));
				animation.setDelay(35);
				width = 30;
			}//end if currently not fireballing
		}//end firing
		else if(phasing){
			if(currentAction != PHASING){
				currentAction = PHASING;
				animation.setFrames(sprites.get(PHASING));
				animation.setDelay(35);
				width = 30;
			}
		}
		else if(dy > 0){
			if(gliding){
				if(currentAction != GLIDING){
					currentAction = GLIDING;
					animation.setFrames(sprites.get(GLIDING));
					animation.setDelay(100);
					width = 30;
				}//end if currently not falling
			}//end if gliding
			else if(currentAction != FALLING){
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(100);
				width = 30;
			}//end if currently not falling
		}//end if the change in y is greater than 0
		else if(dy < 0){
			if(currentAction != JUMPING){
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);//no animation needed for jumping
				width = 30;
			}
		}
		else if( left || right ){
			if(currentAction != WALKING){
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(40);
				width = 30;
			}
		}
		else{
			if(currentAction != IDLE){
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 30;
			}
		}

		animation.update();

		//set direction
		if(currentAction != SCRATCHING && currentAction != FIREBALL){// so we arent moving while attacking <change later maybe?>
			if(right) facingRight = true;
			if(left) facingRight = false;
		}//end if not attacking


	}// end update method

	public void draw(FXGraphics2D fxg) {
		setMapPosition();
		//draw fireballs
		for(int i = 0; i < fireBalls.size(); i++){
			fireBalls.get(i).draw(fxg);
		}

		// draw player
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 100 % 2 == 0){//this will give player appearance of blinking every 100 msecs
				return;
			}
		}
		super.draw(fxg);
	}

	public void draw(Graphics2D g){

		setMapPosition();

		//draw fireballs
		for(int i = 0; i < fireBalls.size(); i++){
			fireBalls.get(i).draw(g);
		}

		// draw player
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 100 % 2 == 0){//this will give player appearance of blinking every 100 msecs
				return;
			}
		}
		super.draw(g);
	}

	private void checkKeyInput() {
		if (gs.gsm.isKeyDown(KeyCode.ESCAPE) && pauseIsReady() && keyInputIsReady()){
			this.lastInputCommandTick = this.tickCount;
			gs.pause();
		}
		if (gs.gsm.isKeyDown(KeyCode.A)) this.setLeft(true);
		if (gs.gsm.isKeyDown(KeyCode.D)) this.setRight(true);
		if (gs.gsm.isKeyDown(KeyCode.SPACE)) this.setJumping(true);
		if (gs.gsm.isKeyDown(KeyCode.W)) this.setGliding(true);
		if (gs.gsm.isKeyDown(KeyCode.Q)) this.setScratching();
		if (gs.gsm.isKeyDown(KeyCode.E)) this.setFiring();
		if (gs.gsm.isKeyDown(KeyCode.SHIFT)) this.setPhasing(true);
		if (gs.gsm.isKeyDown(KeyCode.M)) gs.spawnEnemy();
		if (!gs.gsm.isKeyDown(KeyCode.A)) this.setLeft(false);
		if (!gs.gsm.isKeyDown(KeyCode.D)) this.setRight(false);
		if (!gs.gsm.isKeyDown(KeyCode.SPACE)) this.setJumping(false);
		if (!gs.gsm.isKeyDown(KeyCode.W)) this.setGliding(false);
		if (!gs.gsm.isKeyDown(KeyCode.SHIFT)) this.setPhasing(false);
	}
	private boolean pauseIsReady() {
		return gs.tickCount - gs.lastUnpauseTick > 20;
	}
	private boolean keyInputIsReady() {
		return gs.tickCount - this.lastInputCommandTick > 20;
	}


	public void checkAttack(ArrayList<Enemy> enemies) {

		//loop through all the enemies
		for(int i =0; i < enemies.size(); i++){
			//put the pointer for the address of this iterations corresponding enemy in the enemies array list into Enemy e
			e = enemies.get(i);
			//check to see if the player is scratching, IF it is then check to see if its facingRight, 
			//		IF it is then IF the enemy is further right on the screen from the player AND the 
			//		enemy is within scratching range AND the enemy is within reach above and 
			//		below the player ELSE (then its facing left).. so check to see if the enemy is in
			//		scratching range AND the enemy is within reach above and below the player...
			//			then hit the enemy with scratch damage
			if(scratching){
				if(facingRight){
					if(e.getx() > x && e.getx() < x + scratchRange &&
							e.gety() > y - height / 2 && e.gety() < y + height / 2){
						e.hit(scratchDamage);
					}
				}else{//then its facing left
					if(e.getx() < x && e.getx() > x - scratchRange &&
							e.gety() > y - height / 2 && e.gety() < y + height / 2){
						e.hit(scratchDamage);
					}
				}
			}
			try{
			//	check to see if any of the fireballs in the level are going to hit the enemy...
			//		loop through all the fireballs in the fireball array
			for(int j = 0; j < fireBalls.size(); j++){
				if(fireBalls.get(j).intersects(e)){//if this iterations fireball is going to intersect the enemy
					//then hit the enemy, set this fireball to hit, break from this loop structure
					e.hit(fireBallDamage);
					fireBalls.get(i).setHit(); //TODO: this is pulling an exception
					break;
				}
			}
			}catch(Exception e){ e.printStackTrace(); }
		}
	}
	
	public void increaseKillCount(){
		killCount++;
	}
	public int getKillCount(){
		return killCount;
	}
	public int getFireCount(){
		return fireCount;
	}
	public int getScratchCount(){
		return scratchCount;
	}
}

