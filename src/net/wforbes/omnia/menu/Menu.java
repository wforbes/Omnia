package net.wforbes.omnia.menu;

import javafx.scene.canvas.GraphicsContext;

public abstract class Menu {

    protected int currentChoice;

    abstract void checkKeyInput();
    abstract boolean keyInputReady();
    abstract void select(String option);
    abstract void update();
    abstract void render(GraphicsContext gc);
}
