package net.wforbes.omnia.overworld.entity;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.movement.MovementController;

public abstract class NPC extends Mob {
    protected StringBuilder chatBuilder;
    protected int lastChatTick = 0;
    public NPC(OverworldState gameState, String name, double speed) {
        super(gameState, name, speed, false);
        this.nameColor = Color.LIGHTBLUE;
        movementController = new MovementController(this);
    }
    public NPC(OverworldState gameState, String name, Point2D startPos, double speed) {
        super(gameState, name, startPos, speed);
        movementController = new MovementController(this);
    }

    public void update() {
        super.update();
        this.movementController.update();
        this.movementAnimation.update();
    }

    public void render(GraphicsContext gc) {
        super.render(gc);
    }

    public String receiveChat(Mob senderMob, String chatCmd, String chatMsg) {
        this.lastChatTick = gameState.getTickCount();
        this.chatBuilder = new StringBuilder();
        chatMsg = chatMsg.toUpperCase();
        String response = "";

        this.handleGreetings(senderMob, chatMsg);
        if(chatBuilder.length() == 0) {
            this.handleQuests(senderMob, chatMsg);
        }
        response = chatBuilder.toString();
        chatBuilder = null;
        return response;
    }
    protected abstract void handleGreetings(Mob senderMob, String chatMsg);
    protected abstract void handleQuests(Mob senderMob, String chatMsg);
}
