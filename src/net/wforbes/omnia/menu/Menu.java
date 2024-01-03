package net.wforbes.omnia.menu;

import javafx.scene.canvas.GraphicsContext;

public abstract class Menu {

    protected int currentChoice;

    public abstract void checkKeyInput();
    public abstract boolean keyInputReady();
    public abstract void select(String option);
    public abstract void update();
    public abstract void render(GraphicsContext gc);
}
