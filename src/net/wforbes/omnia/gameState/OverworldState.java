package net.wforbes.omnia.gameState;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import net.wforbes.omnia.gameFX.controls.keyboard.KeyboardController;
import net.wforbes.omnia.overworld.controls.OverworldKeyboardController;
import net.wforbes.omnia.gameFX.controls.mouse.MouseController;
import net.wforbes.omnia.overworld.controls.OverworldMouseController;
import net.wforbes.omnia.overworld.entity.Player;
import net.wforbes.omnia.overworld.gui.GUIController;
import net.wforbes.omnia.overworld.gui.menu.MenuController;
import net.wforbes.omnia.overworld.world.World;

import java.awt.*;

public class OverworldState extends GameState {
    public GameStateManager gsm;
    public KeyboardController keyboardController;
    public MouseController mouseController;
    public MenuController menuController;
    public World world;
    public Player player;
    public GUIController gui;
    public static final String SPRITE_DIR = "/overworld/sprites/";

    private boolean showCollisionGeometry = false;
    private boolean showMobNames = true;

    private boolean isPaused = false;
    private boolean isExiting = false;
    private boolean previouslyExited;

    public OverworldState(GameStateManager gsm) {
        this.setup(gsm);
    }

    private void setup(GameStateManager gsm) {
        if (gsm != null) this.gsm = gsm;
        System.out.println("Building Overworld game state");
        this.mouseController = new OverworldMouseController(this);
        this.keyboardController = new OverworldKeyboardController(this);
        this.world = new World(this);
        this.player = new Player(this, "Will");
        this.world.setPlayer(player);
        this.gui = new GUIController(this);
        this.menuController = new MenuController(gsm);

    }

    private void resurrect(GameStateManager gsm) {
        this.setup(gsm);
        this.previouslyExited = false;
    }

    public GameStateManager getManager() {
        return gsm;
    }

    public World getWorld() {
        return world;
    }
    public Player getPlayer() { return player; }

    public void toggleCollisionGeometry() {
        showCollisionGeometry = !showCollisionGeometry;
    }
    public boolean collisionGeometryVisible() {
        return showCollisionGeometry;
    }
    public boolean mobNamesVisible() { return showMobNames; }

    @Override
    public KeyboardController getKeyboard() {
        return keyboardController;
    }

    @Override
    public void handleKeyPressed(KeyEvent event) {
        this.keyboardController.handleKeyPressed(event);
    }

    @Override
    public void handleKeyReleased(KeyEvent event) {
        this.keyboardController.handleKeyReleased(event);
    }

    @Override
    public void handleCanvasClick(MouseEvent event) {
        this.mouseController.handleCanvasClick(event);
    }

    @Override
    public void handleCanvasMouseMove(MouseEvent event) {
        if (this.isExiting) return;
        this.mouseController.handleCanvasMouseMove(event);
    }

    @Override
    public int getTickCount() {
        return gsm.getTickCount();
    }

    @Override
    public void init() {
        if (previouslyExited && this.gsm != null) {
            this.resurrect(this.gsm);
        }
        this.world.init();
        this.player.init();
        this.gui.init();
    }

    @Override
    public void tick() {

    }

    @Override
    public void update() {
        if (isExiting) return;
        //TODO: check death status
        if(isPaused) {
            menuController.getPauseMenu().update();
            return;
        }
        player.update();
        world.update();
        //TODO: attack enemies
        //TODO: update NPCS
        tickCount++;
    }

    @Override
    public void render(Graphics2D g) { }

    @Override
    public void render(GraphicsContext gc) {
        if (isExiting) return;
        world.render(gc);
        if(isPaused) {
            menuController.getPauseMenu().render(gc);
        }
    }

    @Override
    public void reset() { }

    @Override
    public boolean isPaused() {
        return isPaused;
    }

    @Override
    public void pause() {
        this.isPaused = true;
        this.menuController.getPauseMenu().show();
    }

    @Override
    public void unPause() {
        this.isPaused = false;
        //this.menuController.getPauseMenu().setLastUnpauseTick(this.tickCount);
        this.menuController.getPauseMenu().hide();
    }

    @Override
    public void exit() {
        this.isExiting = true;
        // TODO: step 1 - start saving the game state implicitly
        // TODO: step 2 - present the user with options on saving game
        //this.gsm = null; // we gonna need that on resurrect
        this.mouseController.teardown();
        this.keyboardController.teardown();
        this.world.teardown();
        this.world = null;
        this.player.teardown();
        this.player = null;
        this.gui.teardown();
        this.gui = null;
        this.menuController.teardown();
        this.menuController = null;
        this.previouslyExited = true;
        this.isExiting = false;
    }
}
