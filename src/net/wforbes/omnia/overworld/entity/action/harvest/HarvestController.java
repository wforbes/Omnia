package net.wforbes.omnia.overworld.entity.action.harvest;

import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.entity.Mob;
import net.wforbes.omnia.overworld.entity.action.ActionController;
import net.wforbes.omnia.overworld.world.area.object.AreaObject;
import net.wforbes.omnia.overworld.world.area.object.flora.Flora;

public class HarvestController extends ActionController {

    private Harvestable harvestTarget;

    public HarvestController(Entity owner) {
        super(owner, "Harvest");
    }

    public void harvestMaterials() {
        if (this.actionIsReady()) return;
        AreaObject target = ((Mob)this.owner).getCollidingAreaObject();
        if (target == null) {
            System.out.println("Nothing nearby to Harvest");
            return;
        }
        if (!(target instanceof Harvestable)) {
            System.out.println("Is not Harvestable: " + target);
            return;
        }
        this.startAction();
    }

    @Override
    protected void completeAction() {
        System.out.println("HarvestController.completeAction");
    }

    private void transferHarvestMaterials(Flora flora) {
        //this.owner.addHarvestMaterialsToInventory(flora);
    }

    public void cancelHarvesting() {
        if (!this.isPerforming) return;
        System.out.println("Harvesting cancelled");
        super.cancelAction();

    }
}
