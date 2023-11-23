package net.wforbes.omnia.overworld.world.terrain;

import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.overworld.world.area.Area;
import net.wforbes.omnia.overworld.world.terrain.flora.FloraController;

public class TerrainController {

    private final Area area;
    private FloraController floraController;
    private boolean enableFlora;

    public TerrainController(Area area) {
        this.area = area;
        this.enableFlora = true;
        this.floraController = new FloraController(this);
    }

    public Area getArea() {
        return area;
    }
    public FloraController getFloraController() {
        return this.floraController;
    }

    public void init() {
        if (enableFlora) this.floraController.init();
    }

    public void render(GraphicsContext gc) {
        if (enableFlora) this.floraController.render(gc);
    }
}
