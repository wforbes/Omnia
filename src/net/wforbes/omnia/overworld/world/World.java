package net.wforbes.omnia.overworld.world;

import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.Player;
import net.wforbes.omnia.overworld.entity.dialog.DialogController;
import net.wforbes.omnia.overworld.world.area.Area;

public class World {
    public OverworldState gameState;
    public Area area;
    private DialogController dialogController;
    public Player player;


    public World(OverworldState gameState) {
        this.gameState = gameState;
        //TODO: set up areaGrid that comprises the World once
        //  multiple areas have been created
        this.dialogController = new DialogController(this);
        this.area = new Area(this);
    }

    public OverworldState getGameState() { return this.gameState; }
    public DialogController getDialogController() { return this.dialogController; }
    public void setPlayer(Player player) {
        this.player = player;
    }

    public void init() {
        area.addEntity(this.player);
        area.init();
    }

    public void update() {
        area.update();
    }

    public void render(GraphicsContext gc) {
        area.render(gc);
    }

}
