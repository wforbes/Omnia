package net.wforbes.omnia.overworld.entity.dialog;

import net.wforbes.omnia.overworld.entity.Entity;
import net.wforbes.omnia.overworld.entity.Mob;
import net.wforbes.omnia.overworld.entity.NPC;
import net.wforbes.omnia.overworld.gui.ChatWindowController;
import net.wforbes.omnia.overworld.world.World;

import java.util.HashMap;

public class DialogController {
    public World world;
    private ChatWindowController chatWindowController;
    private HashMap<String, String> chatOutputMap;
    private String[] chatChannels = new String[]{"SAY", "SHOUT", "WHISPER"};
    private final int AUDIBLE_RANGE = 64;

    public DialogController(World world) {
        this.world = world;
        this.setChatOutputMap();

    }

    private void setChatOutputMap() {
        chatOutputMap = new HashMap<>();
        for(String channel : this.chatChannels) {
            chatOutputMap.put(channel, "You " + channel.toLowerCase());
        }
    }

    public void setChatWindowController(ChatWindowController cwc) { this.chatWindowController = cwc; }

    public HashMap<String,String> getChatOutputMap() { return this.chatOutputMap; }

    //TODO: Find better way to determine uniqueness and equivalence of entities, move this into entity
    private boolean areSameEntity(Entity e1, Entity e2) { return e1.getX() == e2.getX() && e1.getY() == e2.getY(); }

    private boolean areWithinAudibleRange(Entity e1, Entity e2) {
        return Math.abs(e1.getX() - e2.getX()) <= AUDIBLE_RANGE
                && Math.abs(e1.getY() - e2.getY()) <= AUDIBLE_RANGE;
    }

    public void submitChatMsg(Entity sender, String chatCmd, String chatMsg) {
        for(Entity entity : this.world.area.getEntities()) {
            if(!this.areSameEntity(sender, entity) && this.areWithinAudibleRange(sender, entity)) {
                if(entity instanceof NPC) {
                    Mob senderMob = (Mob) sender;
                    NPC listener = ((NPC) entity);
                    String response = listener.receiveChat(
                            senderMob,
                            chatCmd, chatMsg);
                    if (!response.equals("")) {
                        this.chatWindowController.parseNPCChat(listener.getName(), chatCmd, response);
                    }

                }
            }
        }
    }
}
