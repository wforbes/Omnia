package net.wforbes.omnia.topDown.controls;

import javafx.scene.input.KeyEvent;
import net.wforbes.omnia.gameFX.controls.keyboard.KeyboardController;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.gameState.TopDownState;

public class TopDownKeyboardController extends KeyboardController {

    public TopDownKeyboardController(TopDownState gameState) {
        super(gameState);
    }
    @Override
    public void keyPressedHook(KeyEvent event) {

    }

    @Override
    public void keyReleasedHook(KeyEvent event) {

    }
}
