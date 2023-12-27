package net.wforbes.omnia.overworld.world.area.object;

import javafx.geometry.Point2D;
import net.wforbes.omnia.db.AreaObjectDBA;
import net.wforbes.omnia.gameFX.rendering.Renderable;
import net.wforbes.omnia.overworld.world.area.Area;
import net.wforbes.omnia.overworld.world.area.object.flora.shrub.Shrub;
import net.wforbes.omnia.overworld.world.area.object.flora.shrub.ShrubType;
import net.wforbes.omnia.overworld.world.area.object.flora.tree.Tree;
import net.wforbes.omnia.overworld.world.area.object.flora.tree.TreeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AreaObjectController {
    private final Area area;
    //private final AreaObjectDBA areaObjectDBA;
    private List<AreaObject> areaObjects;
    private List<AreaObject> corpses;

    public AreaObjectController(Area area) {
        //TODO: add concept of regions where flora is acceptable to grow
        this.area = area;
        /*
        this.areaObjectDBA = new AreaObjectDBA(
            this.area.getWorld().getGameState().getDb()
        );*/
    }

    public List<AreaObject> getAreaObjects() {
        return this.areaObjects;
    }
    public List<AreaObject> getCorpses() { return this.corpses; }

    public void init() {
        this.corpses = new ArrayList<>();
        this.areaObjects = new ArrayList<>();
        //System.out.println(this.area.getAreaMaxX() + ", " + this.area.getAreaMaxY());
        this.addShrubsInRandomLoc(100, this.area.getAreaMaxX(), this.area.getAreaMaxY());
        this.addTreesInRandomLoc(30, this.area.getAreaMaxX(), this.area.getAreaMaxY());
        for (AreaObject ao: this.areaObjects) {
            ao.init();
        }
        System.out.println("AreaObjectController initialized!");
        /*
        this.areaObjects.add(
            new Shrub(
                this.area.getWorld().getGameState(),
                ShrubType.TYPES.BLUEBERRY,
                20, 500
            )
        );
        this.areaObjects.add(
            new Shrub(
                this.area.getWorld().getGameState(),
                ShrubType.TYPES.BLUEBERRY,
                120, 20
            )
        );
        this.areaObjects.add(
            new Shrub(
                this.area.getWorld().getGameState(),
                ShrubType.TYPES.BLUEBERRY,
                20, 120
            )
        );
        this.areaObjects.add(
            new Shrub(
                this.area.getWorld().getGameState(),
                ShrubType.TYPES.BLUEBERRY,
                120, 175
            )
        );
        this.areaObjects.add(
            new Tree(
                this.area.getWorld().getGameState(),
                TreeType.TYPES.OAK,
                175, 175
            )
        );

        this.areaObjects.add(
            new Tree(
                this.area.getWorld().getGameState(),
                TreeType.TYPES.OAK,
                420, 100
            )
        );*/
    }

    public void update() {
        for (AreaObject ao: this.areaObjects) {
            ao.update();
        }
        if (!this.corpses.isEmpty()) {
            for (AreaObject c : this.corpses) {
                c.update();
            }
        }
        //this.areaObjects.removeIf(AreaObject::isFlaggedForDespawn);
    }

    public void addShrubsInRandomLoc(int population, int xRange, int yRange) {
        Random rand = new Random();
        int checkX = 32,checkY = 32;
        //List<Point2D> usedPnts = new ArrayList<>();
        for (int i = 0; i < population; i++) {
            Point2D randPnt = this.generateRandomXY(rand, xRange, yRange);
            while (this.randomPointAlreadyUsed(randPnt, checkX, checkY)) {
                //System.out.println("regenerating random point for idx " + i);
                randPnt = this.generateRandomXY(rand, xRange, yRange);
            }
            this.areaObjects.add(
                    new Shrub(
                            this.area.getWorld().gameState,
                            ShrubType.TYPES.BLUEBERRY,
                            (int)randPnt.getX(),
                            (int)randPnt.getY()
                    )
            );
            //usedPnts.add(randPnt);
        }
    }

    public void addTreesInRandomLoc(int population, int xRange, int yRange) {
        Random rand = new Random();
        int checkX = 64, checkY = 64;
        for (int i = 0; i < population; i++) {
            Point2D randPnt = this.generateRandomXY(rand, xRange, yRange);
            while (this.randomPointAlreadyUsed(randPnt, checkX, checkY)) {
                //System.out.println("regenerating random point for idx " + i);
                randPnt = this.generateRandomXY(rand, xRange, yRange);
            }
            this.areaObjects.add(
                    new Tree(
                            this.area.getWorld().gameState,
                            TreeType.TYPES.OAK,
                            (int)randPnt.getX(),
                            (int)randPnt.getY()
                    )
            );
        }
    }

    private Point2D generateRandomXY(Random rand, int xRange, int yRange) {
        return new Point2D(
            Math.abs(rand.nextInt((xRange - 80) + 1) + 80),
            Math.abs(rand.nextInt((yRange - 80) + 1) + 80)
        );
    }

    private boolean randomPointAlreadyUsed (Point2D randPnt, int checkX, int checkY) {
        //System.out.println("randomPointAlreadyUsed");
        for (Renderable renderable: this.area.getSortedRenderableList()) {
            /*debuggos
            System.out.println("absVal pX: " + Math.abs(p.getX()));
            System.out.println("absVal rpX: " + Math.abs(randPnt.getX()));
            System.out.println("absVal pY: " + Math.abs(p.getY()));
            System.out.println("absVal rpY: " + Math.abs(randPnt.getY()));
            System.out.println(
                    (Math.abs(p.getX()) - Math.abs(randPnt.getX())) + " ("
                    + (Math.abs(p.getX()) - Math.abs(randPnt.getX()) < 72) + ") "
                    + (Math.abs(p.getY()) - Math.abs(randPnt.getY())) + "( "
                    + (Math.abs(p.getY()) - Math.abs(randPnt.getY()) < 72) + ")"
            );*/

            if (Math.abs(renderable.getX() - randPnt.getX()) < checkX
                && Math.abs(renderable.getY() - randPnt.getY()) < checkY
            ) {
                return true;
            }
        }
        return false;
    }
}
