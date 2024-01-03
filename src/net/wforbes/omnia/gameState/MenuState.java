package net.wforbes.omnia.gameState;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import net.wforbes.omnia.game.controls.keyboard.KeyboardController;
import net.wforbes.omnia.menu.mainMenu.MainMenu;

public class MenuState extends GameState {
    private MainMenu mainMenu;
    private int tickCount;

    private String playerName;

    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;
        this.mainMenu = new MainMenu(this);
    }

    @Override
    public void init() {
        this.mainMenu.init();
    }

    @Override
    public void update() {
        this.mainMenu.update();
    }

    @Override
    public void render(GraphicsContext gc) {
        this.mainMenu.render(gc);
    }

    @Override
    public void tick() {
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
