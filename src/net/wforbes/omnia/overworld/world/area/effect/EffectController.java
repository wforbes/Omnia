package net.wforbes.omnia.overworld.world.area.effect;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import net.wforbes.omnia.overworld.world.area.Area;


public class EffectController {
    private Area area;
    private ClickCircle clickCircle;

    public EffectController(Area area) {
        this.area = area;
        this.clickCircle = new ClickCircle(this);
    }

    public Area getArea() {
        return this.area;
    }

    public ClickCircle getClickCircle() {
        return this.clickCircle;
    }

    public void init() {
        clickCircle.init();
    }

    public void handleCanvasClick(MouseEvent event) {
        clickCircle.set(event.getX(), event.getY());
    }

    public void render(GraphicsContext gc) {
        this.clickCircle.render(gc);
    }


}
