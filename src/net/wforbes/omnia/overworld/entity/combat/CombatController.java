package net.wforbes.omnia.overworld.entity.combat;

import net.wforbes.omnia.overworld.entity.Entity;

import java.util.Random;

public class CombatController {
    private final Entity owner;
    private boolean attacking;

    private int meleeDamageMax = 8;
    private float accuracy = 0.62F;

    public CombatController(Entity owner) {
        this.owner = owner;
    }

    public void toggleAttacking() {
        this.attacking = !this.attacking;
    }

    public void update() {
    }
    public boolean isAttacking() {
        return this.attacking;
    }
    public void handleMeleeTrigger() {
        System.out.println(this.owner.getTarget().getName());
        //System.out.println("CC.meleeHit on " + this.owner.getCollidingEntity().getName()); //TODO: proximity-based attacks
        Entity target = this.owner.getTarget();
        if (this.owner.isInMeleeRange(target)) {//TODO: 12/15 refactor then continue with Target based attacks
            this.meleeHit(target);
        }
    }
    public boolean shouldHit() {
        if (this.owner.getTarget() == null) {
            this.feedback_NoCombatTarget();
            return false;
        }
        return this.owner.isInMeleeRange(this.owner.getTarget());
    }

    private void feedback_NoCombatTarget() {}

    private void meleeHit(Entity target) {
        Random r = new Random();
        int dmgRng = r.nextInt(4);
        System.out.println("damage scale " + dmgRng);
        System.out.println("damage part " + (this.meleeDamageMax / 4));
        int rng = r.nextInt((this.meleeDamageMax / 4))+1;
        System.out.println("rngOutput " + rng);
        int dmg = (rng*dmgRng);
        System.out.println(this.owner.getName() + " attempted " + dmg + " melee damage on " + target.getName());
        if (dmg == 0) {
            System.out.println(this.owner.getName() + " missed " + target.getName());
        }
        target.receiveMeleeDamage(dmg, this.owner);
        System.out.println(target.getName() + " current health now " + target.getCurrentHealth());
    }
}
