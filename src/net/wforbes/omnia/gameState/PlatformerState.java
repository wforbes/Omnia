package net.wforbes.omnia.gameState;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import net.wforbes.omnia.game.Game;
import net.wforbes.omnia.game.controls.keyboard.KeyboardController;
import net.wforbes.omnia.menu.DeathMenu;
import net.wforbes.omnia.menu.PauseMenu;
import net.wforbes.omnia.platformer.entity.Enemy;
import net.wforbes.omnia.platformer.entity.Player;
import net.wforbes.omnia.platformer.entity.enemies.Slugger;
import net.wforbes.omnia.platformer.tileMap.Background;
import net.wforbes.omnia.platformer.tileMap.TileMap;
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
    private PauseMenu pauseMenu;
    private ArrayList<Enemy> enemies;
    private boolean isPaused;
    public int tickCount = 0;
    public int lastUnpauseTick = 0;

    public PlatformerState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public Player getPlayer(){ return player; }
    public Enemy getEnemy(int i){ return enemies.get(i); }
    public ArrayList<Enemy> getEnemyArray(){ return enemies; }

    @Override
    public KeyboardController getKeyboard() {
        return null;
    }

    @Override
    public void handleKeyPressed(KeyEvent event) {

    }

    @Override
    public void handleKeyReleased(KeyEvent event) {

    }

    @Override
    public void handleCanvasClick(MouseEvent event) {

    }

    @Override
    public void handleCanvasMouseMove(MouseEvent event) {

    }

    public int getTickCount() { return this.tickCount; }

    @Override
    public void init() {
        bg = new Background(this,"/Backgrounds/level1bg.gif", 0.1);
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/blahgrasstileset.gif");
        tileMap.loadMap("/Maps/level1-1.map");
        tileMap.setPosition(0, 0);
        tileMap.setTween(0.06);

        player = new Player(tileMap, this);
        player.setPosition(100,100);//set starting position

        enemies = new ArrayList<Enemy>();
        Slugger s = new Slugger(tileMap);
        s.setPosition(200, 100);
        enemies.add(s);

        hud = new HUD(player, "fx");
        deathMenu = new DeathMenu(gsm);
        pauseMenu = new PauseMenu(gsm);
        //bgMusic = new AudioPlayer("/Music/bgMusic1.mp3");
        //bgMusic.play();
    }

    @Override
    public void tick() {
        //this.checkKeyInput();

        player.update();
        if (player.isDead) {
            if (!deathMenu.isVisible()) {
                this.deathMenu.show();
            } else {
                deathMenu.tick();
            }
        }

        tileMap.setPosition( (double) Game.getWidth() / 2 - player.getx(), (double) Game.getHeight() / 2 - player.gety() );

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
    public void update() {
        //check death status
        if (player.isDead) {
            if (!deathMenu.isVisible()) {
                this.deathMenu.show();
            } else {
                deathMenu.tick();
                return;
            }
        }
        //check pause status
        if (this.isPaused) {
            if (!pauseMenu.isVisible()) {
                this.pauseMenu.show();
            }
            pauseMenu.tick();
            return;
        }
        player.update();
        tileMap.setPosition( Game.getWidth() / 2 - player.getx(), Game.getHeight() / 2 - player.gety() );

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
        tickCount++;
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
        hud.draw(gc);
        if(deathMenu.isVisible()) {
            deathMenu.render(gc);
        }
        if (pauseMenu.isVisible()){
            pauseMenu.render(gc);
        }
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

    public boolean isPaused() {
        return this.isPaused;
    }

    @Override
    public void pause() {
        this.isPaused = true;
        this.pauseMenu.show();
    }

    public void unPause() {
        this.isPaused = false;
        this.lastUnpauseTick = this.tickCount;
        this.pauseMenu.hide();
    }

    public void exit() {}
}
