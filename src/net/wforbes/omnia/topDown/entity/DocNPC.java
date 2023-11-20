package net.wforbes.omnia.topDown.entity;

import javafx.geometry.Point2D;
import net.wforbes.omnia.gameState.TopDownState;
import net.wforbes.omnia.topDown.graphics.Colors;
import net.wforbes.omnia.topDown.level.Level;

import java.util.*;

public class DocNPC extends NPC{

    TopDownState gameState;

    private static final String[] chatTriggers = {
            "hello", "greetings"
    };
    private static final Map<String, String> DIALOG = new HashMap<>();
    static {
        DIALOG.put("__$trigger",
            "Why, hello there young man. I've been studying " +
            "[synchotrons] lately in my lab."
        );
        DIALOG.put(
            "synchrotrons",
            "Oh, synchrotrons are fascinating! They accelerate " +
            "charged electrons through sequences of magnets until " +
            "they almost reach the speed of light! The electrons " +
            "produce a very bright light, called [synchrotron light]!"
        );
    }

    public DocNPC(Level level, String name, Point2D startPos, TopDownState gameState) {
        super(level, name, startPos, gameState);
        this.gameState = gameState;
        this.canSwim = true;
        this.setSpriteLoc(new Point2D(0, 17));
        this.setSpriteColor(Colors.get(-1, 111, 505, 543));
        this.setNameColor(Colors.get(-1, -1, -1, 244));
        this.movementController.setMovement("paceVertical");
    }

    public void tick() {

        super.tick();
        if(gameState.isDebugging()) {
            gameState.gui.devWindowController.setDocPos(this.x, this.y);
            gameState.gui.devWindowController.setDocMovePos(this.xa, this.ya);
        }
    }

    private boolean hasTriggerPhrase(String str) {
        return Arrays.stream(chatTriggers).anyMatch(s -> str.toUpperCase().contains(s.toUpperCase()))
                && str.toUpperCase().contains(this.getName().toUpperCase());
    }
    private boolean hasConversationPhrase(String str) {
        return false;
    }

    @Override
    protected void handleGreetings(Point2D sourceLoc, String chatMsg) {
        if (hasTriggerPhrase(chatMsg)){
            this.movementController.standAndFace(sourceLoc);
            chatBuilder.append(DIALOG.get("__$trigger"));
        }
    }

    @Override
    protected void handleQuests(Point2D sourceLoc, String chatMsg) {
        String response = DIALOG.get(chatMsg.toLowerCase());
        try { // using a map here kinda sucks...
            // this just needs an actual quest/dialog system
            System.out.println(response);
        } catch (Exception ex) {
            System.out.println("handleQuests exception: " + ex.getMessage());
        }

        if (response != null && !response.isEmpty()) {
            this.movementController.standAndFace(sourceLoc);
            chatBuilder.append(response);
        }
    }
}
