package net.wforbes.omnia.topDown.entity;

import javafx.geometry.Point2D;
import net.wforbes.omnia.gameState.TopDownState;
import net.wforbes.omnia.topDown.graphics.Colors;
import net.wforbes.omnia.topDown.level.Level;

public class DocNPC extends NPC{

    TopDownState gameState;

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

    @Override
    protected void handleGreetings(Point2D sourceLoc, String chatMsg) {
        if ((chatMsg.contains("HELLO") || chatMsg.contains("GREETINGS"))
                && chatMsg.contains(this.getName().toUpperCase())
        ){
            this.movementController.standAndFace(sourceLoc);
            chatBuilder.append( "Why, hello there young man. I've been " +
                    "studying [synchotrons] lately in my lab.");
        }
    }

    @Override
    protected void handleQuests(Point2D sourceLoc, String chatMsg) {
        if (chatMsg.contains("SYNCHOTRONS")) {
            this.movementController.standAndFace(sourceLoc);
            chatBuilder.append("Oh, synchotrons are fascinating! They accelerate " +
                    "charged electrons through sequences of magnets until " +
                    "they almost reach the speed of light! The electrons " +
                    "produce a very bright light, called [synchrotron light]!");
        }
    }
}
