package net.wforbes.omnia.gameFX.controls.mouse;

import javafx.scene.input.MouseEvent;

public abstract class MouseController {
    //public GameState gameState;
    public abstract void handleCanvasClick(MouseEvent event);
    public abstract void handleCanvasMouseMove(MouseEvent event);

    public abstract void teardown();
}
