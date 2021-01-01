package net.wforbes.omnia.gameState;

import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.game.Game;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.platformer.entity.Enemy;
import net.wforbes.omnia.platformer.entity.Player;
import net.wforbes.omnia.platformer.entity.enemies.Slugger;
import net.wforbes.omnia.platformer.tileMap.Background;
import net.wforbes.omnia.platformer.tileMap.TileMap;
import net.wforbes.omnia.platformer.ui.DeathMenu;
import net.wforbes.omnia.platformer.ui.HUD;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.util.ArrayList;

public class PlatformerState extends GameState {
    public GameStateManager gsm;
    private FXGraphics2D fxg;
    private TileMap tileMap;
    private Background bg;
    private Player player;
    private HUD hud;
    private DeathMenu deathMenu;
    private ArrayList<Enemy> enemies;

    public PlatformerState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public Player getPlayer(){ return player; }
    public Enemy getEnemy(int i){ return enemies.get(i); }
    public ArrayList<Enemy> getEnemyArray(){ return enemies; }

    @Override
    public void init() {
        bg = new Background(this,"/Backgrounds/level1bg.gif", 0.1);
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/blahgrasstileset.gif");
        tileMap.loadMap("/Maps/level1-1.map");
        tileMap.setPosition(0, 0);
        tileMap.setTween(0.06);

        player = new Player(tileMap, this);
        if(gsm.usingFx)
            player.setPosition(100,100);//set starting position
        else
            player.setPosition(100,100);//set starting position

        enemies = new ArrayList<Enemy>();
        Slugger s = new Slugger(tileMap);
        if(gsm.usingFx)
            s.setPosition(200, 100);
        else
            s.setPosition(200, 100);
        enemies.add(s);

        if(gsm.usingFx) {
            hud = new HUD(player, "fx");
        } else {
            hud = new HUD(player);
        }
        deathMenu = new DeathMenu(this);
        //bgMusic = new AudioPlayer("/Music/bgMusic1.mp3");
        //bgMusic.play();
    }

    @Override
    public void tick() {
        this.checkKeyInput();

        player.update();
        if (player.isDead) {
            if (!deathMenu.isVisible()) {
                this.deathMenu.show();
            } else {
                deathMenu.tick();
            }
        }

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
    public void update() {
        //check Key Input
        player.update();
        if (player.isDead) {
            if (!deathMenu.isVisible()) {
                this.deathMenu.show();
            } else {
                deathMenu.tick();
            }
        }
        tileMap.setPosition( OmniaFX.getWidth() / 2 - player.getx(), OmniaFX.getHeight() / 2 - player.gety() );

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

    @Override
    public void render(GraphicsContext gc) {
        if (fxg == null) {
            fxg = new FXGraphics2D(gc);
        }
        bg.draw(fxg);
        tileMap.draw(fxg);
        player.draw(fxg);
        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).draw(fxg);
        }
        hud.draw(fxg);
        if(deathMenu.isVisible()) {
            deathMenu.render(fxg);
        }
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

        if(deathMenu.isVisible()) {
            deathMenu.render(g);
        }

        //draw dev disp
        //g.drawString("player coords: " + "( " +  player.getx() + ")"+ "( " +  player.gety() + ")", 100, 100);
        //g.drawString("scratch count: " + player.getScratchCount(), 100, 110);
        //g.drawString("kill count: " + player.getKillCount() , 100, 120);
        //g.drawString("enemy count: " + enemies.size(), 100, 130);
        //g.drawString("player move speed: " + player.getMoveSpeed(), 100, 80);
        //g.drawString("player max speed: " + player.getMaxSpeed(), 100, 90);
    }

    public void spawnEnemy(){
        Slugger s;
        for(int i = 0; i < 1; i++){
            s = new Slugger(tileMap);
            s.setPosition(100, 50);
            enemies.add(s);
        }
    }

    public void reset() {
        this.init();
    }

    @Override
    public void pause() {
        //TODO: implement expanded abstract menu and add pause feature
    }
}
