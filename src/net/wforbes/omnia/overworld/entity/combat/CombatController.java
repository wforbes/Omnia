package net.wforbes.omnia.overworld.entity.combat;

import net.wforbes.omnia.overworld.entity.Entity;

public class CombatController {
    private final Entity owner;
    private boolean attacking;

    public CombatController(Entity owner) {
        this.owner = owner;
    }

    public void toggleAttacking() {
        this.attacking = !this.attacking;
    }

    public void update() {
        if (this.isAttacking() && this.owner.getCollidingEntity() != null) {
            //
        }
    }
    public boolean isAttacking() {
        return this.attacking;
    }
    public void handleMeleeTrigger() {

        if (this.owner.getTarget() == null) {
            this.feedback_NoCombatTarget();
            return;
        }
        System.out.println(this.owner.getTarget().getName());
        //System.out.println("CC.meleeHit on " + this.owner.getCollidingEntity().getName()); //TODO: proximity-based attacks
        Entity target = this.owner.getTarget();
        if (this.owner.isInMeleeRange(target)) {//TODO: 12/15 refactor then continue with Target based attacks
            System.out.println("CC.meleeHit on " + target.getName());
        }
    }

    private void feedback_NoCombatTarget() {}
}
