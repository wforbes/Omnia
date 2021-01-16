package net.wforbes.omnia.gameState;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import net.wforbes.omnia.overworld.entity.Player;
import net.wforbes.omnia.overworld.world.World;
import net.wforbes.omnia.overworld.gui.GUIController;

import java.awt.*;

public class OverworldState extends GameState {
    public GameStateManager gsm;
    public World world;
    public Player player;
    public GUIController gui;
    public static final String SPRITE_DIR = "/overworld/sprites/";

    private boolean showCollisionGeometry = true;

    public OverworldState(GameStateManager gsm) {
        this.gsm = gsm;
        this.world = new World(this);
        this.player = new Player(this, "Will");
        this.world.setPlayer(player);
    }

    public GameStateManager getManager() {
        return gsm;
    }

    public World getWorld() {
        return world;
    }

    public void toggleCollisionGeometry() {
        showCollisionGeometry = !showCollisionGeometry;
    }
    public boolean collisionGeometryVisible() {
        return showCollisionGeometry;
    }

    @Override
    public void handleBorderPaneClick(MouseEvent event) {
        this.gui.handleBorderPaneClick(event);
    }

    @Override
    public void handleBorderPaneMouseMove(MouseEvent event) {
        this.gui.handleBorderPaneMouseMove(event);
    }

    @Override
    public int getTickCount() {
        return 0;
    }

    @Override
    public void init() {
        this.world.init();
        this.player.init();
        this.gui = new GUIController(this);
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
