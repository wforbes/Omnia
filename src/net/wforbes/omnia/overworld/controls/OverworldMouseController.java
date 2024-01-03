package net.wforbes.omnia.overworld.controls;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import net.wforbes.omnia.game.controls.mouse.MouseController;
import net.wforbes.omnia.gameState.OverworldState;

public class OverworldMouseController extends MouseController {
    public OverworldState gameState;
    public OverworldMouseController(OverworldState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void handleCanvasClick(MouseEvent event) {
        //TODO: effectController pivot point away from Area and into GUI
        gameState.world.getCurrentArea().effectController.handleCanvasClick(event);
        if ((event.getButton() == MouseButton.PRIMARY)) {
            gameState.world.player.getTargetController().handleEntityTargeting(
                event,
                gameState.world.getCurrentArea().getEntities(),
                gameState.world.getCurrentArea().effectController
            );
        }
        if ((event.getButton() == MouseButton.SECONDARY)) {
            gameState.world.player.getPathfindController().moveToClick(event);
        }


        gameState.gui.handleCanvasClick(event);
    }

    @Override
    public void handleCanvasMouseMove(MouseEvent event) {
        if (this.gameState == null) return;
        gameState.gui.handleCanvasMouseMove(event);
    }

    public void teardown() {
        this.gameState = null;
    }
}
