package net.wforbes.omnia.gameFX.controls;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//TODO: Bug found when moving from Main Menu with button ui, to platformer, then dying.. the death menu registers
//  and Enter press immediately and restarts the level if the user doesnt press enter during the platformer level
//  This happens with an enter key press in the main menu OR mouse click.
public class KeyPolling {
    private static Scene scene;
    private static final Set<KeyCode> keysCurrentlyDown = new HashSet<>();
    private static final Map<KeyCode, Boolean> keyboard = new HashMap<>() {{
        put(KeyCode.UP, false); put(KeyCode.DOWN, false); put(KeyCode.LEFT, false); put(KeyCode.RIGHT, false);
        put(KeyCode.ENTER, false); put(KeyCode.SHIFT, false); put(KeyCode.SPACE, false);
        put(KeyCode.Q, false); put(KeyCode.W, false); put(KeyCode.E, false);
        put(KeyCode.A, false); put(KeyCode.S, false); put(KeyCode.D, false);
        put(KeyCode.M, false);
    }};

    private KeyPolling() {}

    public static KeyPolling getInstance() {
        return new KeyPolling();
    }

    public void pollScene(Scene scene) {
        clearKeys();
        removeCurrentKeyHandlers();
        setScene(scene);
    }

    private void clearKeys() { keysCurrentlyDown.clear(); }

    private void removeCurrentKeyHandlers() {
        if (scene != null) {
            KeyPolling.scene.setOnKeyPressed(null);
            KeyPolling.scene.setOnKeyReleased(null);
        }
    }

    private void setScene(Scene scene) {
        KeyPolling.scene = scene;
        KeyPolling.scene.setOnKeyPressed((keyEvent -> {
            keysCurrentlyDown.add(keyEvent.getCode());
            keyboard.put(keyEvent.getCode(), true);
        }));
        KeyPolling.scene.setOnKeyReleased((keyEvent -> {
            keysCurrentlyDown.remove(keyEvent.getCode());
            keyboard.put(keyEvent.getCode(), false);
        }));
    }

    public boolean isDown(KeyCode keyCode) {
        return keysCurrentlyDown.contains(keyCode);
    }
    /*
    public boolean isDown(KeyCode keyCode) {
        return keyboard.get(keyCode);
    }
    */
    @Override
    public String toString() {
        StringBuilder keysDown = new StringBuilder("KeyPolling on scene (").append(scene).append(")");
        for (KeyCode code : keysCurrentlyDown) {
            keysDown.append(code.getName()).append(" ");
        }
        return keysDown.toString();
    }
}
