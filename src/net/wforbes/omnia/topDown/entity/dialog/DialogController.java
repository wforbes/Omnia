package net.wforbes.omnia.topDown.entity.dialog;

import net.wforbes.omnia.topDown.entity.Entity;
import net.wforbes.omnia.topDown.entity.Mob;
import net.wforbes.omnia.topDown.entity.NPC;
import net.wforbes.omnia.topDown.entity.Player;
import net.wforbes.omnia.topDown.gui.ChatWindowController;
import net.wforbes.omnia.topDown.level.Level;

import java.util.HashMap;

public class DialogController {

    private Level level;
    private ChatWindowController chatWindowController;
    private HashMap<String,String> chatOutputMap;
    private final int SAY_RANGE_PIXELS = 64;

    public DialogController(Level level) {
        this.level = level;
        this.setChatOutputMap();
    }

    private void setChatOutputMap() {
        chatOutputMap = new HashMap<>();
        chatOutputMap.put("SAY", "You say");
        chatOutputMap.put("SHOUT", "You shout");
        chatOutputMap.put("WHISPER", "You whisper");
    }

    public void setChatWindowController(ChatWindowController cwc) {
        this.chatWindowController = cwc;
    }

    public HashMap<String,String> getChatOutputMap() {
        return this.chatOutputMap;
    }

    private boolean areSameEntity(Entity e1, Entity e2) {
        return e1.x == e2.x && e1.y == e2.y;
    }

    private boolean areWithinSayRange(Entity e1, Entity e2) {
        return Math.abs(e1.x - e2.x) <= SAY_RANGE_PIXELS
                && Math.abs(e1.y - e2.y) <= SAY_RANGE_PIXELS;
    }

    public void submitChatMsg(Entity sender, String chatCmd, String chatMsg) {
        for(Entity entity : this.level.entities) {
            if(!this.areSameEntity(sender, entity) && this.areWithinSayRange(sender, entity)) {
                if(entity instanceof NPC) {
                    Mob senderMob = (Mob) sender;
                    NPC listener = ((NPC)entity);
                    String response = listener.receiveChat(
                            senderMob.getLocationPoint(),
                            chatCmd, chatMsg);
                    if (!response.equals("")) {
                       this.chatWindowController.parseNPCChat(listener.getName(), chatCmd, response);
                    }
                }
            }
        }
    }
}
