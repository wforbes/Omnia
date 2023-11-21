package net.wforbes.omnia.overworld.controls;

import javafx.scene.input.MouseEvent;
import net.wforbes.omnia.gameFX.controls.mouse.MouseController;
import net.wforbes.omnia.gameState.OverworldState;

public class OverworldMouseController extends MouseController {
    public OverworldState gameState;
    public OverworldMouseController(OverworldState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void handleCanvasClick(MouseEvent event) {
        gameState.world.player.getTargetController().handleEntityTargeting(
            event,
            gameState.world.area.entities,
            gameState.world.area.effectController
        );
        gameState.gui.handleCanvasClick(event);
    }

    @Override
    public void handleCanvasMouseMove(MouseEvent event) {
        gameState.gui.handleCanvasMouseMove(event);
    }
}
