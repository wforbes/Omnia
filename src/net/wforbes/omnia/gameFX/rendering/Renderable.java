package net.wforbes.omnia.gameFX.rendering;

import javafx.scene.canvas.GraphicsContext;

public interface Renderable {
    float renderX=0, renderY=0, renderXMap=0, renderYMap=0, renderWidth=0, renderHeight = 0;
    double getWidth();
    double getX();
    double getY();
    double getXMap();
    double getYMap();
    double getBaseY();
    double getRenderX();
    double getRenderY();
    double getRenderXMap();
    double getRenderYMap();
    double getRenderWidth();
    double getRenderHeight();


    void update();
    void render(GraphicsContext gc);
}
