package net.wforbes.omnia.gameFX.controls.mouse;

import javafx.scene.input.MouseEvent;
import net.wforbes.omnia.gameState.OverworldState;

public class OverworldMouseController extends MouseController {
    public OverworldState gameState;
    public OverworldMouseController(OverworldState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void handleCanvasClick(MouseEvent event) {
        gameState.world.area.handleCanvasClick(event);
        gameState.gui.handleCanvasClick(event);
    }

    @Override
    public void handleCanvasMouseMove(MouseEvent event) {
        gameState.gui.handleCanvasMouseMove(event);
    }
}
