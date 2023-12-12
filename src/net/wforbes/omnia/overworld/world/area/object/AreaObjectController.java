package net.wforbes.omnia.overworld.world.area.object;

import javafx.geometry.Point2D;
import net.wforbes.omnia.db.AreaObjectDBA;
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

    public void init() {
        this.areaObjects = new ArrayList<>();
        this.areaObjects.add(
            new Shrub(
                this.area.getWorld().getGameState(),
                ShrubType.GENERA.BLUEBERRY,
                20, 500
            )
        );
        this.areaObjects.add(
            new Shrub(
                this.area.getWorld().getGameState(),
                ShrubType.GENERA.BLUEBERRY,
                120, 20
            )
        );
        this.areaObjects.add(
            new Shrub(
                this.area.getWorld().getGameState(),
                ShrubType.GENERA.BLUEBERRY,
                20, 120
            )
        );
        this.areaObjects.add(
            new Shrub(
                this.area.getWorld().getGameState(),
                ShrubType.GENERA.BLUEBERRY,
                120, 175
            )
        );
        this.areaObjects.add(
            new Tree(
                this.area.getWorld().getGameState(),
                TreeType.GENERA.OAK,
                175, 175
            )
        );

        this.areaObjects.add(
            new Tree(
                this.area.getWorld().getGameState(),
                TreeType.GENERA.OAK,
                420, 100
            )
        );
        for (AreaObject ao: this.areaObjects) {
            ao.init();
        }
    }

    public void update() {
        for (AreaObject ao: this.areaObjects) {
            ao.update();
        }
        this.areaObjects.removeIf(AreaObject::isFlaggedForDespawn);
    }

    public void initRANDOM() {
        int numBushes = 50;
        this.areaObjects = new ArrayList<>();
        Random rand = new Random();
        List<Point2D> usedPnts = new ArrayList<>();
        for (int i = 0; i < numBushes; i++) {
            Point2D randPnt = this.generateRandomXY(rand, 240);
            while (this.randomPointAlreadyUsed(randPnt, usedPnts)) {
                //System.out.println("regenerating random point for idx " + i);
                randPnt = this.generateRandomXY(rand, 240);
            }
            this.areaObjects.add(
                    new Shrub(
                            this.area.getWorld().gameState,
                            ShrubType.GENERA.BLUEBERRY,
                            (float)randPnt.getX(),
                            (float)randPnt.getY()
                    )
            );
            usedPnts.add(randPnt);
        }
        System.out.println("AreaObjectController initialized!");
    }
    private Point2D generateRandomXY(Random rand, int range) {
        return new Point2D(Math.abs(rand.nextDouble() * range), Math.abs(rand.nextDouble() * range));
    }

    private boolean randomPointAlreadyUsed (Point2D randPnt, List<Point2D> usedPnts) {
        //System.out.println("randomPointAlreadyUsed");
        for (Point2D p: usedPnts) {
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
            if (Math.abs(p.getX() - randPnt.getX()) < 16
                    && Math.abs(p.getY() - randPnt.getY()) < 16
                    && Math.abs(randPnt.getX() - this.area.getWorld().player.getX() + (this.area.getWorld().player.getWidth()/2.0)) < 32
                    && Math.abs(randPnt.getY() - this.area.getWorld().player.getY() + (this.area.getWorld().player.getHeight()/2.0)) < 32
                    && Math.abs(randPnt.getX() - this.area.TEST_NPC_XPOS - (this.area.getWorld().player.getWidth()/2.0)) < 32
                    && Math.abs(randPnt.getY() - this.area.TEST_NPC_YPOS - (this.area.getWorld().player.getHeight()/2.0)) < 32
            ) {
                return true;
            }
        }
        return false;
    }
}
