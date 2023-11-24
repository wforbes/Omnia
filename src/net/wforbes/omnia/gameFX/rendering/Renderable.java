package net.wforbes.omnia.gameFX.rendering;

import javafx.scene.canvas.GraphicsContext;

public interface Renderable {
    double getX();
    double getY();
    double getXMap();
    double getYMap();
    double getWidth();
    double getHeight();
    void render(GraphicsContext gc);
}
