package net.wforbes.gameState;

import net.wforbes.game.Game;
import net.wforbes.platformer.entity.Enemy;
import net.wforbes.platformer.entity.Player;
import net.wforbes.platformer.entity.enemies.Slugger;
import net.wforbes.platformer.tileMap.Background;
import net.wforbes.platformer.tileMap.TileMap;
import net.wforbes.platformer.ui.HUD;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class PlatformerState extends GameState {
    public GameStateManager gsm;
    private TileMap tileMap;
    private Background bg;
    private Player player;
    private HUD hud;
    private ArrayList<Enemy> enemies;

    public PlatformerState(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    @Override
    public void init() {
        bg = new Background("/Backgrounds/level1bg.gif", 0.1);

        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/blahgrasstileset.gif");
        tileMap.loadMap("/Maps/level1-1.map");
        tileMap.setPosition(0, 0);
        tileMap.setTween(0.06);

        player = new Player(tileMap, this);
        //set starting position
        player.setPosition(100,100);//set starting position

        enemies = new ArrayList<Enemy>();
        Slugger s = new Slugger(tileMap);
        s.setPosition(200, 100);
        enemies.add(s);

        hud = new HUD(player);
        //bgMusic = new AudioPlayer("/Music/bgMusic1.mp3");
        //bgMusic.play();
    }

    @Override
    public void tick() {
        this.checkKeyInput();
        player.update();
        tileMap.setPosition( Game.WIDTH / 2 - player.getx(), Game.HEIGHT / 2 - player.gety() );

        //set background
        bg.setPosition(tileMap.getx(), tileMap.gety());

        //attack enemies
        player.checkAttack(enemies);

        //update enemies
        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).update();
            if(enemies.get(i).isDead()){
                enemies.remove(i);
                player.increaseKillCount();
                i--;
            }
        }
    }

    private void checkKeyInput() {
        if(gsm.inputHandler.a.isPressed()) player.setLeft(true);
        if(gsm.inputHandler.d.isPressed()) player.setRight(true);
        if(gsm.inputHandler.space.isPressed()) player.setJumping(true);
        if(gsm.inputHandler.w.isPressed()) player.setGliding(true);
        if(gsm.inputHandler.q.isPressed()) player.setScratching();
        if(gsm.inputHandler.e.isPressed()) player.setFiring();
        if(gsm.inputHandler.shift.isPressed()) player.setPhasing(true);
        if(gsm.inputHandler.m.isPressed()) spawnEnemy();
    }

    @Override
    public void render(Graphics2D g) {
        //draw background
        bg.draw(g);

        //draw tilemap
        tileMap.draw(g);

        //draw player
        player.draw(g);

        //draw enemies
        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).draw(g);
        }
        //draw hud
        hud.draw(g);

        //draw dev disp
        g.drawString("player coords: " + "( " +  player.getx() + ")"+ "( " +  player.gety() + ")", 100, 100);
        g.drawString("scratch count: " + player.getScratchCount(), 100, 110);
        g.drawString("kill count: " + player.getKillCount() , 100, 120);
        g.drawString("enemy count: " + enemies.size(), 100, 130);
        g.drawString("player move speed: " + player.getMoveSpeed(), 100, 80);
        g.drawString("player max speed: " + player.getMaxSpeed(), 100, 90);
    }

    public void spawnEnemy(){
        Slugger s;
        for(int i = 0; i < 1; i++){
            s = new Slugger(tileMap);
            s.setPosition(100, 50);
            enemies.add(s);
        }
    }

    //Developer Display methods
    public Player getPlayer(){ return player; }
    public Enemy getEnemy(int i){ return enemies.get(i); }
    public ArrayList<Enemy> getEnemyArray(){ return enemies; }

    public void keyPressed(int k) {
        if( k == KeyEvent.VK_A) player.setLeft(true);
        if( k == KeyEvent.VK_D) player.setRight(true);
        if( k == KeyEvent.VK_SPACE) player.setJumping(true);
        if( k == KeyEvent.VK_W) player.setGliding(true);
        if( k == KeyEvent.VK_Q) player.setScratching();
        if( k == KeyEvent.VK_E) player.setFiring();
        if( k == KeyEvent.VK_SHIFT) player.setPhasing(true);
        if( k == KeyEvent.VK_M) spawnEnemy();
    }


    public void keyReleased(int k) {
        if( k == KeyEvent.VK_A) player.setLeft(false);
        if( k == KeyEvent.VK_D) player.setRight(false);
        if( k == KeyEvent.VK_SPACE) player.setJumping(false);
        if( k == KeyEvent.VK_W || k == KeyEvent.VK_NUMPAD0) player.setGliding(false);
        if( k == KeyEvent.VK_SHIFT || k == KeyEvent.VK_NUMPAD3) player.setPhasing(false);
    }


    public void keyTyped(int k) {
    }
}
