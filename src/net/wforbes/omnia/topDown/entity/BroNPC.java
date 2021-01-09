package net.wforbes.omnia.topDown.entity;

import javafx.geometry.Point2D;
import net.wforbes.omnia.topDown.graphics.Colors;
import net.wforbes.omnia.topDown.level.Level;

public class BroNPC extends NPC{
    public BroNPC(Level level, String name, Point2D startPos) {
        super(level, name, startPos);
        this.canSwim = true;
        this.setSpriteLoc(new Point2D(0, 15));
        this.setSpriteColor(Colors.get(-1, 111, 042, 555));
        this.setNameColor(Colors.get(-1, -1, -1, 024));
        this.movementController.setMovement("paceVertical");
    }

    protected void handleGreetings(Point2D sourceLoc, String chatMsg) {
        if (chatMsg.contains("HELLO") || chatMsg.contains("YO") || chatMsg.contains("SUP")
            && chatMsg.contains(this.getName().toUpperCase())
        ){
            this.movementController.standAndFace(sourceLoc);
            chatBuilder.append("Sup, my dude? Life's been [gnarly] for me over here...");
        }
    }
    protected void handleQuests(Point2D sourceLoc, String chatMsg) {
        if (chatMsg.contains("GNARLY")) {
            this.movementController.standAndFace(sourceLoc);
            chatBuilder.append("Ya, it's all gnarly bro. You know what's gnarly for me right now? " +
                    "I lost my [taco sauce], my dude. I'm hella bummed on it...");
        }
    }
}
