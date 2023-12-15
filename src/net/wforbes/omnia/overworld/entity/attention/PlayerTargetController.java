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
        //poll for area entities
        for(Entity e : entities) {
            // if the distance between the edge of the radi are less than the distance within
            //  the radi combined, the user has clicked the entity
            if ((getXYMapCollsionRadius(event, e, ec)) <= (getCursorEntityCollisionDist(e, ec))) {
                //System.out.println("Clicked on " + e.getName());
                ((Mob) e).setTargeted(true); // set the entity as targeted//TODO:Moving away from this method
                this.owner.setTarget(e);
            } else if (((Mob) e).isTargeted()){
                // otherwise ensure entity is no longer targeted
                ((Mob) e).setTargeted(false);
                this.owner.setTarget(null);
            }
        }
    }
}
