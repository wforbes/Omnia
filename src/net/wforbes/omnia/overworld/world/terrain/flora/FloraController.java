package net.wforbes.omnia.overworld.world.terrain.flora;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.overworld.world.area.Area;
import net.wforbes.omnia.overworld.world.terrain.TerrainController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FloraController {
    private final TerrainController terrainController;
    private List<BushFlora> bushes;

    public FloraController(TerrainController tc) {
        //TODO: add concept of regions where flora is acceptable to grow
        this.terrainController = tc;
    }

    public List<BushFlora> getBushes() {
        return this.bushes;
    }

    public void init() {
        int numBushes = 10;
        this.bushes = new ArrayList<>();
        Random rand = new Random();
        List<Point2D> usedPnts = new ArrayList<>();
        for (int i = 0; i < numBushes; i++) {
            Point2D randPnt = this.generateRandomXY(rand, 240);
            while (this.randomPointAlreadyUsed(randPnt, usedPnts)) {
                //System.out.println("regenerating random point for idx " + i);
                randPnt = this.generateRandomXY(rand, 240);
            }
            this.bushes.add(
                new BushFlora(
                    this.terrainController.getArea().getWorld().gameState,
                    randPnt.getX(),
                    randPnt.getY()
                )
            );
            usedPnts.add(randPnt);
        }
        System.out.println("FloraController initialized!");
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
            if (Math.abs(p.getX() - randPnt.getX()) < 64
                && Math.abs(p.getY() - randPnt.getY()) < 64
                && Math.abs(randPnt.getX() - this.terrainController.getArea().getWorld().player.getX() + (this.terrainController.getArea().getWorld().player.getWidth()/2.0)) < 64
                && Math.abs(randPnt.getY() - this.terrainController.getArea().getWorld().player.getY() + (this.terrainController.getArea().getWorld().player.getHeight()/2.0)) < 64
                && Math.abs(randPnt.getX() - this.terrainController.getArea().TEST_NPC_XPOS + (this.terrainController.getArea().getWorld().player.getWidth()/2.0)) < 64
                && Math.abs(randPnt.getY() - this.terrainController.getArea().TEST_NPC_YPOS + (this.terrainController.getArea().getWorld().player.getHeight()/2.0)) < 64
            ) {
               return true;
            }
        }
        return false;
    }

    public void render(GraphicsContext gc) {
        for(BushFlora b: this.bushes) {
            b.render(gc);
        }
    }
}
