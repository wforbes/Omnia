package net.wforbes.omnia.gameState;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import net.wforbes.omnia.game.controls.keyboard.KeyboardController;
import net.wforbes.omnia.menu.MainMenu;
import net.wforbes.omnia.platformer.tileMap.Background;

import java.awt.*;

public class MenuState extends GameState {
    private MainMenu menu;

    private int waitTicks = 20;
    private int tickCount;

    private Background bg;

    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;
        this.menu = new MainMenu(this);
    }

    @Override
    public void init() {
        this.menu.init();
    }

    @Override
    public void update() {
        this.menu.update();
    }

    @Override
    public void render(GraphicsContext gc) {
        this.menu.render(gc);
    }

    @Override
    public void tick() {
        bg.update();
        tickCount++;
    }

    public int getTickCount() {
        return this.tickCount;
    }

    public boolean isPaused() { return false; }

    public void reset() {}
    public void pause() {}
    public void unPause() {}

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

    public void handleCanvasClick(MouseEvent event) {}

    @Override
    public void handleCanvasMouseMove(MouseEvent event) {

    }

    public void exit() {}
}
