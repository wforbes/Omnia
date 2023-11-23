package net.wforbes.omnia.overworld.entity;

import javafx.geometry.Point2D;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.animation.MovementAnimation;
import net.wforbes.omnia.overworld.entity.dialog.NPCDialog.DocDialog;
import net.wforbes.omnia.overworld.entity.movement.MovementController;
import net.wforbes.omnia.u.W;

import java.util.Arrays;

public class DocNPC extends NPC {
    private DocDialog npcDialog;
    private static final String[] chatTriggers = {
            "hello", "greetings"
    };

    public DocNPC(OverworldState gameState) {
        super(gameState, "Doc",0.25);
        this.width = this.height = 16;
        this.numFrames = new int[]{3,3,3,3};
        this.loadSprites(OverworldState.SPRITE_DIR + "doc_pokemon.gif");
        movementAnimation = new MovementAnimation(this);
        this.facingDir = FACING_S;
        this.setAnimationDirection(facingDir);
        this.attentionController.setAttentionSpan(30);
        npcDialog = new DocDialog();
    }

    @Override
    public void init() {
        // nopes
    }

    public void init(int xPos, int yPos) {
        this.setPosition(xPos, yPos);
        this.movementController.setMovementSpace(this.x-40, this.y-40, this.x+40, this.y+40);
        this.movementController.setMovementType(MovementController.MOVEMENT_PACE_VERTICAL);
    }

    public void update() {
        super.update();
    }

    public void setAttentionFocus(Mob senderMob) {
        W.out(this.getName() + " setting attention focus on " + senderMob.getName());
        this.movementController.standAndFace(senderMob.getLocationPoint());
        this.attentionController.startFocusing(senderMob);
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
