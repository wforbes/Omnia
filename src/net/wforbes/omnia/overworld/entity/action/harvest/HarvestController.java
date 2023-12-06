package net.wforbes.omnia.overworld.entity.action.harvest;

import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.entity.Mob;
import net.wforbes.omnia.overworld.entity.action.ActionController;
import net.wforbes.omnia.overworld.gui.loot.Loot;
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
        if ((target instanceof Harvestable)) {
            this.harvestTarget = (Harvestable)target;
            this.startAction();
        } else {
            System.out.println("Is not Harvestable: " + target);
        }
    }

    @Override
    protected void completeAction() {
        System.out.println("HarvestController.completeAction");
        if (this.harvestTarget == null) {
            System.out.println("lost harvestTarget before completeAction...");
            return;
        }
        this.lootHarvest();
        this.harvestTarget.completeAction();
    }

    private void lootHarvest() {
        System.out.println("HarvestController.lootHarvest");
        Loot harvestedLoot = this.harvestTarget.getLoot();
        if (harvestedLoot == null) {
            System.out.println("There was nothing to harvest");
        }
        this.owner.gameState.gui.getLootWindow().show(harvestedLoot);
    }

    public void cancelHarvesting() {
        if (!this.isPerforming) return;
        System.out.println("Harvesting cancelled");
        super.cancelAction();

    }
}
