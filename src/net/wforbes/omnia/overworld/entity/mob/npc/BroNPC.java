package net.wforbes.omnia.overworld.entity.mob.npc;

import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.entity.animation.MovementAnimation;
import net.wforbes.omnia.overworld.entity.attention.NPCTargetController;
import net.wforbes.omnia.overworld.entity.combat.stat.MobStats;
import net.wforbes.omnia.overworld.entity.mob.Mob;
import net.wforbes.omnia.overworld.entity.movement.MovementController;

import java.util.Arrays;

public class BroNPC extends NPC {

    private static final String[] chatTriggers = {
        "yo", "sup", "hey"
    };

    private NPCTargetController attentionController;

    public BroNPC(OverworldState gameState, String name, double speed) {
        super(gameState, name, speed, new MobStats(1000, 50, 0.65F));
        this.width = this.height = 16;
        this.numFrames = new int[]{3,3,3,3};
        this.combatNumFrames = new int[]{3,3,3,3};//TODO: UNUSED SO FAR
        //this.loadSprites(OverworldState.SPRITE_DIR + "bro_pokemon.gif");
        this.facingDir = Mob.FACING_S;
        movementAnimation = new MovementAnimation(this);
        //npcDialog = new BroDialog();
        this.setAnimationDirection(facingDir);
        this.mobType = "bro";
    }

    @Override
    public void init() {
        this.setPosition(100, 200);
        this.movementController.setMovementSpace(this.x-20, this.y-20, this.x+20, this.y+20);
        this.movementController.setMovementType(MovementController.MOVEMENT_PACE_VERTICAL);
    }

    public void init(int xPos, int yPos) {}

    @Override
    public boolean hasAttentionOnSomething() {
        return false;
    }

    public void update() { super.update(); }


    public void setAttentionFocus(Mob senderMob) {
        this.movementController.standAndFace(senderMob.getLocationPoint());
        this.attentionController.setTarget(senderMob);
    }

    private boolean hasTriggerPhrase(String str) {
        return Arrays.asList(chatTriggers).contains(str.toLowerCase())
                && str.toUpperCase().contains(this.getName().toUpperCase());
    }

    @Override
    protected void handleGreetings(Mob senderMob, String chatMsg) {
        if (hasTriggerPhrase(chatMsg)){
            this.setAttentionFocus(senderMob);
            chatBuilder.append("sup dude... life's been [gnarly] for me broseph.");
        }
    }

    @Override
    protected void handleQuests(Mob senderMob, String chatMsg) {
        if (chatMsg.toUpperCase().contains("GNARLY")) {
            this.setAttentionFocus(senderMob);
            chatBuilder.append("ya.. it's all gnarly bro. you know what's gnarly for me right now? "+
                "I lost my [taco sauce], my dude. I'm hella bummed on it."
            );
        }
    }
}
