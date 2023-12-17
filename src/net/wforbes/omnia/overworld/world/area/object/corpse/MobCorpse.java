package net.wforbes.omnia.overworld.world.area.object.corpse;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.action.Lootable;
import net.wforbes.omnia.overworld.entity.mob.Mob;
import net.wforbes.omnia.overworld.entity.mob.MobCorpseType;
import net.wforbes.omnia.overworld.gui.item.Item;
import net.wforbes.omnia.overworld.gui.loot.Loot;
import net.wforbes.omnia.overworld.gui.loot.LootTimer;
import net.wforbes.omnia.overworld.world.area.object.AreaObject;

public class MobCorpse extends AreaObject implements Lootable {
    private final MobCorpseType mobCorpseType;
    protected Mob owner;
    protected LootTimer lootTimer;
    protected Loot loot;

    public MobCorpse(OverworldState gameState, String mobType, Image sprite, int x, int y) {
        super(gameState, x, y);
        this.lootTimer = new LootTimer(this);
        // reimplementation of loadSprite
        this.spriteImg = sprite;
        this.width = (float)this.spriteImg.getWidth();
        this.height = (float)this.spriteImg.getHeight();
        System.out.println("MOBCORPSE: " + width + "x" + height + " @ " + x + "," + y);
        this.mobCorpseType = new MobCorpseType(mobType);
    }

    public void init() {
        this.initCollisionShape();
    }

    protected void initCollisionShape() {
        // dummy values, no collision on corpses right now
        this.collision_baseX = 0;
        this.collision_baseY = 0;
        this.baseY = this.collision_baseY;
        this.collisionRadius = 0;
        this.collision_baseCenterPnt = new Point2D(collision_baseX, collision_baseY);
        this.collision_baseCircle = new Circle(collision_baseX, collision_baseY, collisionRadius);
    }

    public void spawn() {
        super.spawn();
        this.gameState.getWorld().getCurrentArea().getAreaObjects().add(this);
        this.generateAndSetLoot();
    }

    public void update() {
        //super.update();
        this.lootTimer.update();
    }

    public void render(GraphicsContext gc) {
        //super.render(gc);
        this.refreshMapPosition();
        this.renderSprite(gc);
    }

    @Override
    public void setLoot(Loot loot) {
        this.loot = loot;
    }

    @Override
    public Loot getLoot() {
        for (Item item : loot.getItems()) {
            System.out.println(item.getName());
        }
        return this.loot;
    }

    @Override
    public void returnLoot(Loot loot) {
        //TODO: mob loot
    }

    @Override
    public void notifyLootTimerDone() {
        //TODO: mob loot
    }

    @Override
    public void generateAndSetLoot() {
        this.setLoot(this.mobCorpseType.getRandomLootInstance(this.gameState.db));
    }
}
