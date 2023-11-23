package net.wforbes.omnia.overworld.entity.flora;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.world.area.Area;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FloraController {
    private final Area area;
    private List<BushFlora> bushes;

    public FloraController(Area area) {
        //TODO: add concept of regions where flora is acceptable to grow
        this.area = area;
    }

    public void init() {
        int numBushes = 100;
        this.bushes = new ArrayList<>();
        Random rand = new Random();
        List<Point2D> usedPnts = new ArrayList<>();
        for (int i = 0; i < numBushes; i++) {
            Point2D randPnt = this.generateRandomXY(rand, 420);
            while (this.randomPointAlreadyUsed(randPnt, usedPnts)) {
                //System.out.println("regenerating random point for idx " + i);
                randPnt = this.generateRandomXY(rand, 420);
            }
            this.bushes.add(
                new BushFlora(
                    this.area.getWorld().gameState,
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
            if (Math.abs(p.getX() - randPnt.getX()) < 32
                && Math.abs(p.getY() - randPnt.getY()) < 32
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
