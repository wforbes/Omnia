package net.wforbes.omnia.overworld.world;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.mob.player.Player;
import net.wforbes.omnia.overworld.entity.dialog.DialogController;
import net.wforbes.omnia.overworld.world.area.Area;

import java.util.ArrayList;
import java.util.LinkedList;

public class World {
    public OverworldState gameState;
    public Area currentArea;
    public ArrayList<Area> previousAreas;
    private DialogController dialogController;
    public Player player;


    public World(OverworldState gameState) {
        this.gameState = gameState;
        //TODO: set up areaGrid that comprises the World once
        //  multiple areas have been created
        this.dialogController = new DialogController(this);
        this.currentArea = new Area(this);
    }

    public OverworldState getGameState() { return this.gameState; }
    public DialogController getDialogController() { return this.dialogController; }
    public void setPlayer(Player player) {
        this.player = player;
    }

    public Point2D getPlayerLocation() { return new Point2D(this.player.getXActual(), this.player.getYActual()); }
    public Area getCurrentArea() { return this.currentArea; }

    public void init() {
        currentArea.addEntity(this.player);
        currentArea.init();
    }

    public void moveIntoArea(Area nextArea) {
        Area a = this.currentArea;
        this.previousAreas.add(a);
        this.currentArea = nextArea;
        //nextArea.addEntity(this.player);
        //nextArea.init()
    }

    public void update() {
        currentArea.update();
    }

    public void render(GraphicsContext gc) {
        currentArea.render(gc);
    }

    public void teardown() {
        this.gameState = null;
        //TODO: tear down areaGrid once its implemented
        this.dialogController.teardown();
        this.dialogController = null;
        this.currentArea.teardown();
    }
}
