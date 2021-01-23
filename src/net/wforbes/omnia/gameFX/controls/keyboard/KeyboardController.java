package net.wforbes.omnia.gameFX.controls.keyboard;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import net.wforbes.omnia.gameState.OverworldState;

import java.util.HashMap;

public abstract class KeyboardController {
    public OverworldState gameState;
    public Keyboard keyboard;
    int lastPressTick = 0;
    int tickDelay = 10;

    public KeyboardController(OverworldState gameState) {
        this.gameState = gameState;
        this.keyboard = new Keyboard();
    }

    protected static final class Keyboard {
        public KeyCode[] keys;
        public HashMap<KeyCode, Boolean> keysDown;
        public Keyboard() {
            this.keys = KeyCode.values();
            this.keysDown = new HashMap<>();
            for(KeyCode k : keys) {
                this.keysDown.put(k, false);
            }

        }
    }

    public void consumeKey(KeyCode key) {
        this.lastPressTick = gameState.getTickCount();
        keyboard.keysDown.replace(key, false);
    }

    public boolean keyInputIsReady() {
        return gameState.getTickCount() - lastPressTick > tickDelay;
    }

    public boolean isKeyDown(KeyCode key) {
        return keyboard.keysDown.get(key);
    }

    public void handleKeyPressed(KeyEvent event) {
        if(!keyboard.keysDown.get(event.getCode())) {
            keyboard.keysDown.replace(event.getCode(), true);
        }
        this.keyPressedHook(event);
    }

    public void handleKeyReleased(KeyEvent event) {
        if(keyboard.keysDown.get(event.getCode())) {
            keyboard.keysDown.replace(event.getCode(), false);
        }
        this.keyReleasedHook(event);
    }

    //TODO: hook calls into gamestate to handle the event actively
    public abstract void keyPressedHook(KeyEvent event);
    public abstract void keyReleasedHook(KeyEvent event);
}
