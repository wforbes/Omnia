package net.wforbes.omnia.topDown.entity;

import javafx.geometry.Point2D;
import net.wforbes.omnia.topDown.graphics.Colors;
import net.wforbes.omnia.topDown.level.Level;

public class DocNPC extends NPC{
    public DocNPC(Level level, String name, Point2D startPos) {
        super(level, name, startPos);
        this.canSwim = true;
        this.setSpriteLoc(new Point2D(0, 17));
        this.setSpriteColor(Colors.get(-1, 111, 222, 555));
        this.setNameColor(Colors.get(-1, -1, -1, 034));
    }

    @Override
    public String receiveChat(String chatCmd, String chatMsg) {
        chatMsg = chatMsg.toUpperCase();
        if (chatMsg.contains("HELLO") || chatMsg.contains("GREETINGS")){
            return "Why, hello there young man.";
        }
        if (chatMsg.contains("SYNCHOTRONS")) {
            return "Oh, I find synchotrons fascinating! They accelerate" +
                    " charged electrons through sequences of magnets until " +
                    "they almost reach the speed of light! The electrons " +
                    "produce a very bright light, called [synchrotron light]!";
        }
        return "";
    }
}
