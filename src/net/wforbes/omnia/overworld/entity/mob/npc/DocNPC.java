package net.wforbes.omnia.overworld.entity.mob.npc;

import javafx.geometry.Point2D;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.entity.animation.MovementAnimation;
import net.wforbes.omnia.overworld.entity.combat.stat.MobStats;
import net.wforbes.omnia.overworld.entity.dialog.NPCDialog.DocDialog;
import net.wforbes.omnia.overworld.entity.mob.Mob;
import net.wforbes.omnia.overworld.entity.movement.MovementController;

import java.util.Arrays;

public class DocNPC extends NPC {
    private DocDialog npcDialog;
    private static final String[] chatTriggers = {
            "hello", "greetings"
    };

    public DocNPC(OverworldState gameState) {
        super(gameState, "Doc",0.25, new MobStats(1000, 0.5F, 50, 0.65F));
        this.width = this.height = 16;
        this.numFrames = new int[]{3,3,3,3};
        this.combatNumFrames = new int[]{3,3,3,3};//TODO: UNUSED SO FAR
        this.loadSprites(OverworldState.SPRITE_DIR + "doc_pokemon.gif");
        this.movementAnimation = new MovementAnimation(this);
        this.facingDir = Mob.FACING_S;
        this.setAnimationDirection(facingDir);
        this.npcTargetController.setAttentionSpan(30);
        npcDialog = new DocDialog();
        this.mobType = "doc";
    }

    @Override
    public Entity getCollidingEntity() {
        return null; //TODO
    }

    @Override
    public void init() {
        // nopes
    }

    public void init(double xPos, double yPos) {
        super.init(xPos, yPos);
        this.setPosition(xPos, yPos);
        this.movementController.setMovementSpace(this.x-40, this.y-40, this.x+40, this.y+40);
        this.movementController.setMovementType(MovementController.MOVEMENT_PACE_VERTICAL);
    }

    public void update() {
        super.update();
    }

    public void setAttentionFocus(Mob senderMob) {
        //W.out(this.getName() + " setting attention focus on " + senderMob.getName());
        this.movementController.standAndFace(senderMob.getLocationPoint());
        this.npcTargetController.startFocusing(senderMob);
    }

    private boolean hasTriggerPhrase(String str) {
        return Arrays.stream(chatTriggers).anyMatch(s -> str.toUpperCase().contains(s.toUpperCase()))
                && str.toUpperCase().contains(this.getName().toUpperCase());
    }

    @Override
    protected void handleGreetings(Mob senderMob, String chatMsg) {
        if (hasTriggerPhrase(chatMsg)) {
            this.setAttentionFocus(senderMob);
            chatBuilder.append(DocDialog.dialogMap.get("greeting"));
        }
    }
    @Override
    protected void handleQuests(Mob senderMob, String chatMsg) {
        Point2D sourceLoc = senderMob.getLocationPoint();
        //TODO: work out a system that can parse quest dialog without
        //  string literals here...
        if (chatMsg.toUpperCase().contains("SYNCHROTRONS")) {
            this.setAttentionFocus(senderMob);
            chatBuilder.append(DocDialog.dialogMap.get("synchrotrons"));
        } else if (chatMsg.toUpperCase().contains("SYNCHROTRON LIGHT")) {
            this.setAttentionFocus(senderMob);
            chatBuilder.append(DocDialog.dialogMap.get("synchrotron light"));
        } else if (chatMsg.toUpperCase().contains("LAB")) {
            this.setAttentionFocus(senderMob);
            chatBuilder.append(DocDialog.dialogMap.get("lab"));
        } else if (chatMsg.toUpperCase().contains("SEE IT")) {
            this.setAttentionFocus(senderMob);
            chatBuilder.append(DocDialog.dialogMap.get("see it"));
        } else if (chatMsg.toUpperCase().contains("RESEARCH REQUEST")) {
            this.setAttentionFocus(senderMob);
            chatBuilder.append(DocDialog.dialogMap.get("research request"));
            //TODO: give 'research request paperwork' item to player
            //TODO: start 'research request' quest
        }
    }
}
