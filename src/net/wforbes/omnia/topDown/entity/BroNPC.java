package net.wforbes.omnia.topDown.entity;

import javafx.geometry.Point2D;
import net.wforbes.omnia.gameState.TopDownState;
import net.wforbes.omnia.topDown.graphics.Colors;
import net.wforbes.omnia.topDown.level.Level;

import java.util.Arrays;

public class BroNPC extends NPC{
    private static final String[] chatTriggers = {
        "yo", "sup", "hey"
    };

    public BroNPC(Level level, String name, Point2D startPos, TopDownState gameState) {
        super(level, name, startPos, gameState);
        this.canSwim = true;
        this.setSpriteLoc(new Point2D(0, 15));
        this.setSpriteColor(Colors.get(-1, 111, 042, 555));
        this.setNameColor(Colors.get(-1, -1, -1, 024));
        this.movementController.setMovement("paceVertical");
    }

    private boolean hasTriggerPhrase(String str) {
        return Arrays.asList(chatTriggers).contains(str.toLowerCase())
                && str.toUpperCase().contains(this.getName().toUpperCase());
    }

    protected void handleGreetings(Point2D sourceLoc, String chatMsg) {
        if (hasTriggerPhrase(chatMsg)) {
            this.movementController.standAndFace(sourceLoc);
            chatBuilder.append("Sup, my dude? Life's been [gnarly] for me over here...");
        }
    }
    protected void handleQuests(Point2D sourceLoc, String chatMsg) {
        if (chatMsg.toUpperCase().contains("GNARLY")) {
            this.movementController.standAndFace(sourceLoc);
            chatBuilder.append("Ya, it's all gnarly bro. You know what's gnarly for me right now? " +
                    "I lost my [taco sauce], my dude. I'm hella bummed on it...");
        }
    }
}
