package net.wforbes.omnia.overworld.controls;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import net.wforbes.omnia.game.controls.keyboard.KeyboardController;
import net.wforbes.omnia.gameState.OverworldState;

public class OverworldKeyboardController extends KeyboardController {
    //private final OverworldState overworldGameState;

    public OverworldKeyboardController(OverworldState gameState) {
        super(gameState);
    }

    public void keyPressedHook(KeyEvent event) {
        //TODO: migrate to an event bus system
        //TODO: pull keybind preferences from settings
        if (event.getCode() == KeyCode.BACK_QUOTE) {
            ((OverworldState)gameState).getPlayer().handleAttackKeyPress();
        }
    }

    public void keyReleasedHook(KeyEvent event) {
        //System.out.println(event.getCode());
    }

    public void teardown() {
        this.gameState = null;
    }
}