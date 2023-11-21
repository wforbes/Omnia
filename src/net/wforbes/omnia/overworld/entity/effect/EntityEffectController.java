package net.wforbes.omnia.overworld.entity.effect;

import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.world.area.effect.EffectController;

public class EntityEffectController {
    private final Entity entity;
    private final EntityTargetCircle targetCircle;

    public EntityEffectController(Entity e) {
        this.entity = e;
        this.targetCircle = new EntityTargetCircle(this);
    }

    public Entity getEntity() {
        return this.entity;
    }

    public EntityTargetCircle getTargetCircle() {
        return this.targetCircle;
    }

    public void renderBackground(GraphicsContext gc) {
        this.targetCircle.render(gc);
    }

    public void teardown() {
        this.targetCircle.teardown();
    }
}
