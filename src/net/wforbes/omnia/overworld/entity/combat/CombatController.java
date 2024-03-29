package net.wforbes.omnia.overworld.entity.combat;

import net.wforbes.omnia.overworld.entity.Entity;

import java.util.ArrayList;
import java.util.Random;

public class CombatController {
    private final Entity owner;
    private boolean attacking;
    private boolean inCombat;
    private ArrayList<Entity> aggroList = new ArrayList<>();

    public CombatController(Entity owner) {
        this.owner = owner;
    }

    public void toggleAttacking() {
        this.attacking = !this.attacking;
    }

    public void update() {
        if (this.owner.getTarget() != null && this.owner.getTarget().isDead()) {
            this.attacking = false;
            this.owner.setTarget(null);
            return;
        }
        if (this.aggroList.isEmpty() && this.inCombat) {
            this.inCombat = false;
        }
    }
    public boolean isAttacking() {
        return this.attacking;
    }

    public boolean isInCombat() { return this.inCombat; }

    public void receiveMeleeDamage(int dmg, Entity dealer) {
        if (!this.inCombat) {
            this.inCombat = true;
        }
        if (!this.aggroList.contains(dealer)) {
            this.aggroList.add(dealer);
        }
    }

    public void notifyEnemyAggro(Entity source) {
        if (!this.inCombat) {
            this.inCombat = true;
        }
        if (!this.aggroList.contains(source)) {
            this.aggroList.add(source);
        }
    }

    public void handleMeleeTrigger() {
        //System.out.println(this.owner.getTarget().getName());
        //System.out.println("CC.meleeHit on " + this.owner.getCollidingEntity().getName()); //TODO: check/handle proximity-based attacks
        Entity target = this.owner.getTarget();
        if (this.owner.equals(target)) {
            System.out.println(this.owner.getName() + " is attacking themself...");
            return;
        }
        if (this.owner.isInMeleeRange(target)) {
            if (target.isDead()) {
                this.owner.setTarget(null);
                //System.out.println(this.owner.getName() + " hit " + target.getName() + "... but nothing happened.");
                return;
            }
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

        // number used to split the range of damage into 4 sections
        //  from 1 to max damage, split into 4 sections
        // TODO: future considerations can bias toward the last two damage parts
        //  perhaps when classes are introduced, melee classes will more likely hit within part 3 or 4
        //  perhaps when stats like Strength are introduced, having higher strength will give better chance for part 3 or 4
        int dmgScale = r.nextInt(4)+1;
        //System.out.println("damage scale " + dmgScale);

        // the range of numbers within a damage section, or damage part
        float dmgPart = (this.owner.getMaxMeleeDamage() / 4.0F);
        //System.out.println("damage part " + dmgPart);

        // the maximum value within this damage part
        float dmgPartMax =  dmgPart * dmgScale;
        //System.out.println("damage part max " + dmgPartMax);

        // the minimum value within this damage part
        float dmgPartMin = ((dmgScale - 1) * dmgPart);
        //System.out.println("damage part min " + dmgPartMin);

        // a random integer within the damage part
        int dmg = r.nextInt(
            Math.abs(
                Math.round(dmgPartMax) - Math.round(dmgPartMin)
            )
        ) + Math.round(dmgPartMin);
        //int dmg = (rng*(dmgScale)); //ehhhh
        //System.out.println(this.owner.getName() + " attempted " + dmg + " melee damage on " + target.getName());

        // use owner's accuracy to check for miss
        // TODO: convert miss chance into a check between attacker's accuracy and receiver's avoidance
        dmg = r.nextFloat() < this.owner.getMeleeAccuracy() ? dmg : 0;

        if (dmg == 0) {
            //System.out.println(this.owner.getName() + " missed " + target.getName() + "!");
        }

        target.receiveMeleeDamage(dmg, this.owner);
        //System.out.println(this.owner.getName() + " sees " + target.getName() + "'s current health is " + target.getCurrentHealth());
    }

    public void notifyCombatKill(Entity source) throws Exception {
        if (!this.aggroList.contains(source)) {
            throw new Exception("Attempting to remove entity from aggroList that wasn't set!");
        }
        this.aggroList.remove(source);


    }
}
