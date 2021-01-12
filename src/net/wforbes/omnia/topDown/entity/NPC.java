package net.wforbes.omnia.topDown.entity;

import javafx.geometry.Point2D;
import net.wforbes.omnia.gameState.TopDownState;
import net.wforbes.omnia.topDown.graphics.Screen;
import net.wforbes.omnia.topDown.level.Level;

public abstract class NPC extends Mob {
    protected StringBuilder chatBuilder;
    protected int lastChatTick = 0;
    /*
    public NPC(Level level, String name, int x, int y) {
        super(level, name, x, y, 1);
        this.canSwim = true;
        this.dialogController = new DialogController();
    }*/

    public NPC(Level level, String name, Point2D startPos, TopDownState gameState) {
        super(level, name, startPos, 1, gameState);
    }

    public String receiveChat(Point2D sourceLoc, String chatCmd, String chatMsg) {
        this.lastChatTick = tickCount;
        this.chatBuilder = new StringBuilder("");
        chatMsg = chatMsg.toUpperCase();
        String response = "";

        this.handleGreetings(sourceLoc, chatMsg);
        if(chatBuilder.length() == 0) {
            this.handleQuests(sourceLoc, chatMsg);
        }
        response = chatBuilder.toString();
        chatBuilder = null;
        return response;
    }
    protected abstract void handleGreetings(Point2D sourceLoc, String chatMsg);
    protected abstract void handleQuests(Point2D sourceLoc, String chatMsg);


    @Override
    public String simpleChatResponse(String type) {
        return null;
    }

    @Override
    public void tick() {
        this.movementController.tick();
        super.tick();
    }

    @Override
    public void render(Screen screen) {
        super.render(screen);
    }
}
