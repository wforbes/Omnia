package net.wforbes.omnia.overworld.entity.attention;

import javafx.scene.input.MouseEvent;
import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.entity.mob.Mob;
import net.wforbes.omnia.overworld.world.area.effect.EffectController;

import static net.wforbes.omnia.game.Game.getScale;

public class TargetController {

    protected final Entity owner;
    protected Entity target;

    public TargetController(Entity owner) {
        this.owner = owner;
    }

    public void setTarget(Entity e) {
        this.target = e;
    }

    public Entity getTarget() {
        return this.target;
    }

    /**
     * returns the distance between the entity and the point at which the cursor was clicked
     * (with entity's tilemap offset and render scaling considered)
     *
     * @param   MouseEvent  the mouse event passed in from canvas UI
     * @param   Entity      the entity being checked for collision
     * @return  the linear distance from the click event and the entity
     */
    protected double getXYMapCollsionRadius(MouseEvent event, Entity e, EffectController effectController) {
        // x and y distance between click event and the entity, considering tilemap offset and scaling
        double xDist = (event.getX()
                - (e.getX()+ ((Mob) e).getXMap() + ((Mob)e).getCollisionBaseX()) * getScale());
        double yDist = (event.getY()
                - (e.getY() + ((Mob) e).getYMap() + ((Mob)e).getCollisionBaseY()) * getScale());
        // equation of circle around click point, plus ClickCircle radius size
        return (Math.sqrt((xDist * xDist) + (yDist * yDist))/2.0)
                + effectController.getClickCircle().getCircleRadius();
    }

    /**
     * calculates the combined distance between the click point and cursors visual 'click circle'
     * and the entity's centerpoint position and its collision radius
     * @param   Entity  entity to check
     * @return double
     */
    protected double getCursorEntityCollisionDist(Entity e, EffectController effectController) {
        return (effectController.getClickCircle().getCircleRadius()*2.0)
                + (e.getCollisionRadius()*2.0);
    }

    public void teardown() {}
}
