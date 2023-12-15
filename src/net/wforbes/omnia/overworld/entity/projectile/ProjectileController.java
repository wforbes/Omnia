package net.wforbes.omnia.overworld.entity.projectile;

import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.overworld.entity.mob.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ProjectileController {

    private Player owner;
    private List<Projectile> projectiles;
    private int projectileCooldown = 20;
    private int projectileCooldownTimer = 0;

    public ProjectileController(Player owner) {
        this.owner = owner;
        this.projectiles = new ArrayList<>();
    }
    public void fireProjectile() {
        if (this.projectileCooldownTimer == 0) {
            Projectile p = new Projectile(owner);
            //System.out.println("created projectile: " + p);
            this.projectiles.add(p);
            this.projectileCooldownTimer++;
            p.launch();
        }
        if (this.projectileCooldownTimer > 20) {
            System.out.println("Can't fire projectile for another: " + this.projectileCooldownTimer);
        }
    }

    private void updateProjectiles() {
        if (!this.projectiles.isEmpty()) {
            List<Integer> removals = new ArrayList<>();
            for (int i = 0; i < this.projectiles.size(); i++) {
                this.projectiles.get(i).update();
                if (this.projectiles.get(i).isMarkedForTeardown()) {
                    removals.add(i);
                }
            }
            if (!removals.isEmpty()) {
                for (Integer r : removals) {
                    try {
                        if (this.projectiles.get(r).isMarkedForTeardown()) {
                            this.projectiles.get(r).teardown();
                            this.removeProjectile(r.intValue());
                        }
                    } catch (IndexOutOfBoundsException ex) {
                        System.out.println("PROJECTILE REMOVAL ERROR! " + this.projectiles.toString());
                    }
                }
            }
        }
        if (this.projectileCooldownTimer > 0) {
            this.projectileCooldownTimer++;
            if (this.projectileCooldownTimer >= this.projectileCooldown) {
                this.projectileCooldownTimer = 0;
            }
        }
    }

    public void removeProjectile(int i) {
        //System.out.println("Player attempting projectile removal");
        Projectile p = this.projectiles.remove(i);
        if (p != null) {
            //System.out.println("Projectile removed : " + p);
            return;
        }
        //System.out.println("Player can't find projectile");
    }

    public void update() {
        this.updateProjectiles();
    }

    public void render(GraphicsContext gc) {
        this.renderProjectiles(gc);
    }
    private void renderProjectiles(GraphicsContext gc) {
        if (!this.projectiles.isEmpty()) {
            for (Projectile p : this.projectiles) {
                //System.out.println(p);
                p.render(gc);
            }
        }
    }

    public void teardown() {
        this.projectiles = null;
    }
}
