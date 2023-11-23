package net.wforbes.omnia.overworld.entity.harvest;

import net.wforbes.omnia.overworld.entity.Mob;
import net.wforbes.omnia.overworld.world.terrain.flora.BushFlora;
import net.wforbes.omnia.overworld.world.terrain.flora.Flora;

import java.util.List;

public class HarvestController {
    private final Mob owner;
    private boolean isHarvesting = false;
    private int harvestCooldown = 1000;
    private int harvestCooldownTimer = 0;
    private int harvestActionTimer;
    private int harvestActionTime = 500;
    private Flora harvestTarget;

    public HarvestController(Mob owner) {
        this.owner = owner;
    }

    public void harvestMaterials() {
        if (this.harvestCooldownTimer != 0 || this.isHarvesting) return;
        // TODO: you're already harvesting message
        // TODO: you have x seconds until you can harvest again
        Flora flora = this.owner.getCollidingFlora();
        if (flora != null) {
            this.harvestCooldownTimer = 0;
            this.isHarvesting = true;
            this.harvestTarget = flora;
            this.owner.gameState.gui.getActionWindow().startAction(
                "harvesting", 0.01, harvestTarget
            );
            this.harvestActionTimer++;
            this.harvestCooldownTimer++;
            System.out.println("Started harvesting... " + flora);
        }
    }

    public void update() {
        if (this.isHarvesting) {
            if (this.harvestActionTimer > 0) {
                this.harvestActionTimer++;
                if (this.harvestActionTimer <= this.harvestActionTime && this.harvestActionTimer % 10 == 0) {
                    System.out.println(
                        "harvest action timer (mod10): " +
                        ((float)this.harvestActionTimer / (float)this.harvestActionTime)
                    );
                    this.owner.gameState.gui.getActionWindow().updateAction(
                        (double)this.harvestActionTimer / (double)this.harvestActionTime
                    );
                }
                if (this.harvestActionTimer >= this.harvestActionTime) {
                    System.out.println("Harvest complete!");
                    this.owner.gameState.gui.getActionWindow().completeAction();
                    this.isHarvesting = false;
                    this.harvestActionTimer = 0;
                    this.transferHarvestMaterials(this.harvestTarget);
                    this.harvestTarget = null;

                }
            }
        }
        if (this.harvestCooldownTimer > 0) {
            this.harvestCooldownTimer++;
            if (this.harvestCooldownTimer <= this.harvestCooldown && this.harvestCooldownTimer % 100 == 0) {
                System.out.println("harvest cooldown timer (mod100): " + this.harvestCooldownTimer);
            }
            if (this.harvestCooldownTimer >= this.harvestCooldown) {
                System.out.println("Ready to Harvest");
                this.harvestCooldownTimer = 0;
            }
        }
    }

    private void transferHarvestMaterials(Flora flora) {
        this.owner.addHarvestMaterialsToInventory(flora);
    }

    public void cancelHarvesting() {
        if (!this.isHarvesting) return;
        System.out.println("Harvesting cancelled");
        this.owner.gameState.gui.getActionWindow().cancelAction();
        if (this.harvestActionTimer > 0) {
            this.isHarvesting = false;
            this.harvestActionTimer = 0;
        }
    }
}
