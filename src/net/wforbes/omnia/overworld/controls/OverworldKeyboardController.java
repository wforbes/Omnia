package net.wforbes.omnia.overworld.controls;

import javafx.scene.input.KeyEvent;
import net.wforbes.omnia.gameFX.controls.keyboard.KeyboardController;
import net.wforbes.omnia.gameState.OverworldState;

public class OverworldKeyboardController extends KeyboardController {

    public OverworldKeyboardController(OverworldState gameState) {
        super(gameState);
    }

    public void keyPressedHook(KeyEvent event) {
        //System.out.println(event.getCode());
    }

    public void keyReleasedHook(KeyEvent event) {
        //System.out.println(event.getCode());
    }

}