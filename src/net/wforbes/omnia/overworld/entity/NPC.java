package net.wforbes.omnia.overworld.entity;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.gameState.OverworldState;

public abstract class NPC extends Mob {

    //MovementController movementController;

    public NPC(OverworldState gameState, String name, double speed) {
        super(gameState, name, speed);
        //movementController = new MovementController(this);
    }
    public NPC(OverworldState gameState, String name, Point2D startPos, double speed) {
        super(gameState, name, startPos, speed);
        //movementController = new MovementController((Mob)this);
    }

    public void update() {
        //this.movementController.update();
        //super.update();
    }

    public void render(GraphicsContext gc) {
        super.render(gc);
    }

    //TODO: public String receiveChat(Point2D sourceLoc, String chatCmd, String chatMsg)
    //      protected abstract void handleGreetings(Point2D sourceLoc, String chatMsg);
    //      protected abstract void handleQuests(Point2D sourceLoc, String chatMsg);
}
