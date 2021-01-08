package net.wforbes.omnia.topDown.entity;

import javafx.geometry.Point2D;
import net.wforbes.omnia.topDown.graphics.Colors;
import net.wforbes.omnia.topDown.level.Level;

public class DocNPC extends NPC{
    private int lastChatTick = 0;
    public DocNPC(Level level, String name, Point2D startPos) {
        super(level, name, startPos);
        this.canSwim = true;
        this.setSpriteLoc(new Point2D(0, 11));
        this.setSpriteColor(Colors.get(-1, 111, 222, 555));
        this.setNameColor(Colors.get(-1, -1, -1, 244));
        this.movementController.setMovement("paceVertical");
    }

    public void tick() {
        super.tick();
    }

    @Override
    public String receiveChat(Point2D sourceLoc, String chatCmd, String chatMsg) {
        this.lastChatTick = tickCount;
        chatMsg = chatMsg.toUpperCase();
        if (chatMsg.contains("HELLO") || chatMsg.contains("GREETINGS")){
            this.movementController.standAndFace(sourceLoc);
            return "Why, hello there young man.";
        }
        if (chatMsg.contains("SYNCHOTRONS")) {
            this.movementController.standAndFace(sourceLoc);
            return "Oh, I find synchotrons fascinating! They accelerate" +
                    " charged electrons through sequences of magnets until " +
                    "they almost reach the speed of light! The electrons " +
                    "produce a very bright light, called [synchrotron light]!";
        }
        return "";
    }
}
