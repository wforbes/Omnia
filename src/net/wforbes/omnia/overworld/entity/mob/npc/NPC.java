package net.wforbes.omnia.overworld.entity.mob.npc;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.entity.attention.NPCTargetController;
import net.wforbes.omnia.overworld.entity.mob.Mob;
import net.wforbes.omnia.overworld.entity.movement.MovementController;

public abstract class NPC extends Mob {
    protected StringBuilder chatBuilder;
    protected NPCTargetController npcTargetController;
    protected int lastChatTick = 0;
    public NPC(OverworldState gameState, String name, double speed) {
        super(gameState, name, speed, false);
        this.meleeReach = 4;
        this.nameColor = Color.LIGHTBLUE;
        this.nameFlashColor = Color.AQUA;
        this.movementController = new MovementController(this);
        this.npcTargetController = new NPCTargetController(this);
    }

    public void init(double x, double y) {
        super.init(x, y);
        System.out.println("NPC initialized");
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

    public boolean hasAttentionOnSomething() {
        return this.npcTargetController.getIsFocusing();
    }

    @Override
    public Entity getTarget() {
        return this.npcTargetController.getTarget();
    }

    @Override
    public void setTarget(Entity e) {
        this.npcTargetController.setTarget(e);
    }

    public void teardown() {
        super.teardown();
        this.movementController.teardown();
        this.movementController = null;
        if (this.npcTargetController != null) {
            this.npcTargetController.teardown();
            this.npcTargetController = null;
        }
    }
}
