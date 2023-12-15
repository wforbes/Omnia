package net.wforbes.omnia.overworld.entity.action;

import net.wforbes.omnia.overworld.entity.Entity;

public class ActionController {
    protected final Entity owner;
    private final String actionGerund;
    protected boolean isPerforming = false;
    protected int cooldownDuration = 300;
    protected int cooldownTimer = 0;
    protected int actionDuration = 200;
    protected int actionTimer = 0;
    private Actionable actionTarget;

    protected double progress = 0;
    protected boolean continuable = false;

    public ActionController(Entity owner, String actionGerund) {
        this.owner = owner;
        this.actionGerund = actionGerund;
    }

    public ActionController(Entity owner, String actionGerund, boolean continuable) {
        this.owner = owner;
        this.actionGerund = actionGerund;
        this.continuable = continuable;
    }

    protected boolean actionNotReady() {
        // TODO: you're already harvesting message
        // TODO: you have x seconds until you can harvest again
        return this.cooldownTimer != 0 || this.isPerforming;
    }

    protected void startAction() {
        if (this.continuable && this.progress > 0) {
            this.actionTimer = (int) Math.floor(this.progress * (double)this.actionDuration);
        }
        this.cooldownTimer = 0;
        this.isPerforming = true;
        this.owner.gameState.gui.getActionWindow().startAction(
                this.actionGerund, 0.01, this.owner
        );
        this.actionTimer++;
        this.cooldownTimer++;
    }

    public void update() {
        if (this.isPerforming) {
            if (this.actionTimer > 0) {
                this.actionTimer++;
                if (this.actionTimer <= this.actionDuration && this.actionTimer % 10 == 0) {
                    System.out.println(
                            "action timer (mod10): " +
                                    ((float)this.actionTimer / (float)this.actionDuration)
                    );
                    if (this.continuable) {
                        this.progress = (double) this.actionTimer / (double) this.actionDuration;
                    }
                    this.owner.gameState.gui.getActionWindow().updateAction(
                            (double)this.actionTimer / (double)this.actionDuration
                    );
                }
                if (this.actionTimer >= this.actionDuration) {
                    System.out.println("Action complete!");
                    this.owner.gameState.gui.getActionWindow().completeAction();
                    this.isPerforming = false;
                    this.actionTimer = 0;
                    if (this.continuable) this.progress = 0;
                    this.completeAction();
                    this.actionTarget = null;
                }
            }
        }
        if (this.cooldownTimer > 0) {
            this.cooldownTimer++;
            if (this.cooldownTimer <= this.cooldownDuration && this.cooldownTimer % 100 == 0) {
                System.out.println("action cooldown timer (mod100): " + this.cooldownTimer);
            }
            if (this.cooldownTimer >= this.cooldownDuration) {
                System.out.println("Ready to Harvest");
                this.cooldownTimer = 0;
            }
        }
    }

    // should be overwritten by child specifying the type of action
    protected void completeAction() {
        System.out.println("ActionController.completeAction");
    }

    protected void cancelAction() {
        this.owner.gameState.gui.getActionWindow().cancelAction();
        if (this.actionTimer > 0) {
            this.isPerforming = false;
            this.actionTimer = 0;
        }
    }
}
