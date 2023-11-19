package net.wforbes.omnia.overworld.entity;

import javafx.geometry.Point2D;
import net.wforbes.omnia.gameState.OverworldState;
import net.wforbes.omnia.overworld.entity.animation.MovementAnimation;
import net.wforbes.omnia.overworld.entity.attention.AttentionController;
import net.wforbes.omnia.overworld.entity.dialog.NPCDialog.DocDialog;
import net.wforbes.omnia.overworld.entity.movement.MovementController;

import java.util.Arrays;

public class DocNPC extends NPC {
    private DocDialog npcDialog;
    private static final String[] chatTriggers = {
            "hello", "greetings"
    };
    private AttentionController attentionController;
    public DocNPC(OverworldState gameState) {
        super(gameState, "Doc",0.25);
        this.width = this.height = 16;
        this.numFrames = new int[]{3,3,3,3};
        this.loadSprites(OverworldState.SPRITE_DIR + "doc_pokemon.gif");
        this.facingDir = FACING_S;
        movementAnimation = new MovementAnimation(this);
        npcDialog = new DocDialog();
        this.setAnimationDirection(facingDir);
        this.attentionController = new AttentionController();
    }

    public void init() {
        this.setPosition(200, 200);
        this.movementController.setMovementSpace(this.x-40, this.y-40, this.x+40, this.y+40);
        this.movementController.setMovementType(MovementController.MOVEMENT_PACE_VERTICAL);
    }

    public void update() {
        super.update();

    }

    public void setAttentionFocus(Mob senderMob) {
        this.movementController.standAndFace(senderMob.getLocationPoint());
        this.attentionController.setTarget(senderMob);
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
        if (chatMsg.toUpperCase().contains("SYNCHOTRONS")) {
            this.setAttentionFocus(senderMob);
            chatBuilder.append(DocDialog.dialogMap.get("synchotrons"));
        } else if (chatMsg.toUpperCase().contains("SYNCHOTRON LIGHT")) {
            this.setAttentionFocus(senderMob);
            chatBuilder.append(DocDialog.dialogMap.get("synchotron light"));
        } else if (chatMsg.toUpperCase().contains("RESEARCH REQUEST")) {
            this.setAttentionFocus(senderMob);
            chatBuilder.append(DocDialog.dialogMap.get("research request"));
            //TODO: give 'research request paperwork' item to player
            //TODO: start 'research request' quest
        }
    }
}
