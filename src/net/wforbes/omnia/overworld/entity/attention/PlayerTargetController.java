package net.wforbes.omnia.overworld.entity.attention;

import javafx.scene.input.MouseEvent;
import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.entity.mob.Mob;
import net.wforbes.omnia.overworld.world.area.effect.EffectController;

import java.util.List;

public class PlayerTargetController extends TargetController {

    public PlayerTargetController(Entity owner) {
        super(owner);
    }

    public void handleEntityTargeting(MouseEvent event, List<Entity> entities, EffectController ec) {
        for(Entity e : entities) {
            if ((getClickEntityDistance(event, e, ec)) <= (getCursorEntityCollisionDist(e, ec))) {
                ((Mob) e).setTargeted(true);
                this.owner.setTarget(e);
            } else if (((Mob) e).isTargeted()){
                ((Mob) e).setTargeted(false);
                this.owner.setTarget(null);
            }
        }
    }
}
