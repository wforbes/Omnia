package net.wforbes.omnia.gameState;

import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.overworld.entity.Player;
import net.wforbes.omnia.overworld.world.World;

import java.awt.*;

public class OverworldState extends GameState {
    public GameStateManager gsm;
    public World world;
    public Player player;


    public OverworldState(GameStateManager gsm) {
        this.gsm = gsm;
        this.world = new World();
        this.player = new Player(this);
        this.world.setPlayer(player);
    }

    @Override
    public int getTickCount() {
        return 0;
    }

    @Override
    public void init() {
        this.world.init();
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
        world.update();
        //TODO: attack enemies
        //TODO: update NPCS
        tickCount++;
    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public void render(GraphicsContext gc) {
        world.render(gc);
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
