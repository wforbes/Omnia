package net.wforbes.omnia.gameState;

import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.overworld.entity.Player;
import net.wforbes.omnia.overworld.level.Level;

import java.awt.*;

public class OverworldState extends GameState {
    public GameStateManager gsm;
    public Level level;
    public Player player;


    public OverworldState(GameStateManager gsm) {
        this.gsm = gsm;
        this.level = new Level();
        this.player = new Player(this);
        this.player.setPosition(60, 60);
    }

    @Override
    public int getTickCount() {
        return 0;
    }

    @Override
    public void init() {
        this.level.init();
        this.player.init();
    }

    @Override
    public void tick() {

    }

    @Override
    public void update() {
        //TODO: check death status
        //TODO: check pause status
        player.update();
        level.getTileMap().setPosition(player.getX(), player.getY());

        //TODO: attack enemies
        //TODO: update NPCS
        tickCount++;
    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public void render(GraphicsContext gc) {
        level.render(gc);
        player.render(gc);
    }

    @Override
    public void reset() {

    }

    @Override
    public void pause() {

    }

    @Override
    public boolean isPaused() {
        return false;
    }

    @Override
    public void unPause() {

    }
}
