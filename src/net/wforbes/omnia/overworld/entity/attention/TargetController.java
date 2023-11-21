package net.wforbes.omnia.overworld.entity.attention;

import javafx.scene.input.MouseEvent;
import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.entity.Mob;
import net.wforbes.omnia.overworld.world.area.effect.EffectController;

import java.util.List;

import static net.wforbes.omnia.gameFX.OmniaFX.getScale;

public class TargetController {

    public void handleEntityTargeting(MouseEvent event, List<Entity> entities, EffectController ec) {
        //poll for area entities
        for(Entity e : entities) {
            /* debug here
            if (e.getName().equals("ENTITY NAME")) {
                System.out.println(
                    e.getName() + "xymapRadius: " +
                    " | (" + (e.getX() + ((Mob) e).getXMap() - ((Mob) e).getWidth() / 2.0) * OmniaFX.getScale() +
                    ", " + (e.getY() + ((Mob) e).getYMap() - ((Mob) e).getHeight() / 2.0) * OmniaFX.getScale() + ")"
                );
                System.out.println("XY Dist Sqrt: " + xyDistRadius);
                System.out.println("Entity/Cursor collision calc :" + cursorEntityCollisionDist);
            }*/
            // if the distance between the edge of the radi are less than the distance within
            //  the radi combined, the user has clicked the entity
            if ((getXYMapCollsionRadius(event, e, ec)) <= (getCursorEntityCollisionDist(e, ec))) {
                //System.out.println("Clicked on " + e.getName());
                ((Mob) e).setTargeted(true); // set the entity as targeted
            } else if (((Mob) e).isTargeted()){
                // otherwise ensure entity is no longer targeted
                ((Mob) e).setTargeted(false);
            }
        }
    }

    /**
     * returns the distance between the entity and the point at which the cursor was clicked
     * (with entity's tilemap offset and render scaling considered)
     *
     * @param   MouseEvent  the mouse event passed in from canvas UI
     * @param   Entity      the entity being checked for collision
     * @return  the linear distance from the click event and the entity
     */
    private double getXYMapCollsionRadius(MouseEvent event, Entity e, EffectController effectController) {
        // x and y distance between click event and the entity, considering tilemap offset and scaling
        double xDist = (event.getX()
                - (e.getX()+ ((Mob) e).getXMap() - ((Mob) e).getWidth() / 2.0) * getScale());
        double yDist = (event.getY()
                - (e.getY() + ((Mob) e).getYMap() - ((Mob) e).getHeight() / 2.0) * getScale());
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
    private double getCursorEntityCollisionDist(Entity e, EffectController effectController) {
        return (effectController.getClickCircle().getCircleRadius()*2.0)
                + (e.getCollisionRadius()*2.0);
    }

    public void teardown() {}
}
