package net.wforbes.omnia.overworld.entity;

import net.wforbes.omnia.overworld.entity.mob.enemy.Procyon;
import net.wforbes.omnia.overworld.entity.mob.npc.DocNPC;
import net.wforbes.omnia.overworld.entity.mob.npc.NPC;
import net.wforbes.omnia.overworld.world.area.Area;

import java.util.ArrayList;
import java.util.List;

public class EntityController {
    private Area area;
    public List<Entity> entities;
    public List<Entity> spirits;
    public double TEST_NPC_XPOS = 200;
    public double TEST_NPC_YPOS = 200;
    public double TEST_ENEMY_XPOS = 140;
    public double TEST_ENEMY_YPOS = 140;

    public EntityController(Area area) {
        this.area = area;
        this.entities = new ArrayList<>();
        this.spirits = new ArrayList<>();
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }
    public List<Entity> getEntities() {
        return this.entities;
    }

    public void addSpirits(Entity spirit) {
        this.spirits.add(spirit);
    }

    public List<Entity> getSpirits() {
        return this.spirits;
    }

    public void init() {
        NPC testNPC = new DocNPC(this.area.getWorld().getGameState());
        testNPC.init(TEST_NPC_XPOS, TEST_NPC_YPOS);
        this.addEntity(testNPC);

        Procyon testEnemy = new Procyon(this.area.getWorld().getGameState(), "A Procyon");
        testEnemy.init(TEST_ENEMY_XPOS, TEST_ENEMY_YPOS);
        this.addEntity(testEnemy);
    }

    public void teardown() {
        for (Entity e : this.getEntities()) {
            e.teardown();
        }
        this.entities = null;
        for (Entity s : this.getSpirits()) {
            s.teardown();
        }
        this.spirits = null;
    }
}
