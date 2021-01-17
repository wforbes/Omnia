package net.wforbes.omnia.overworld.entity;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.movement.MovementController;

public abstract class NPC extends Mob {
    protected StringBuilder chatBuilder;
    protected int lastChatTick = 0;
    public NPC(OverworldState gameState, String name, double speed) {
        super(gameState, name, speed, false);
        movementController = new MovementController(this);
    }
    public NPC(OverworldState gameState, String name, Point2D startPos, double speed) {
        super(gameState, name, startPos, speed);
        movementController = new MovementController(this);
    }

    public void update() {
        this.movementController.update();
        this.movementAnimation.update();
    }

    public void render(GraphicsContext gc) {
        super.render(gc);
    }

    public String receiveChat(Point2D sourceLoc, String chatCmd, String chatMsg) {
        this.lastChatTick = gameState.getTickCount();
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
}
