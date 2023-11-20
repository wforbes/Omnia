package net.wforbes.omnia.overworld.entity.dialog.NPCDialog;

import java.util.HashMap;
import java.util.Map;

public class DocDialog {
    public static Map<String,String> dialogMap;
    static{
        dialogMap = new HashMap<>();
        dialogMap.put("greeting", "Why, hello there youngster. I've been studying [synchrotrons] lately in my [lab].");
        dialogMap.put("synchrotrons", "Oh, synchrotrons are fascinating! They accelerate charged electrons through "
            + "sequences of magnets until they almost reach the speed of light! The electron beam it makes produces "
            + "a very bright light, called [synchrotron light]!"
        );
        dialogMap.put("lab", "Yes, my laboratory... would you like to [see it]?");
        dialogMap.put("see it", "Very good, follow me!");
        dialogMap.put("synchrotron light", "Amazingly, synchrotron light can shine more brightly than 1 million stars!"
            + " You see, I believe it's possible to harness this brightness to discover a new form of light "
            + "magic... but I'll need more magnets! Will you give my [research request] to the boys down at the "
            + "Factory? I'll be happy to pay you for your time!"
        );
        dialogMap.put("research request", "Great! Here's the request papers, bring those magnet materials back to me "
            + "and we can get started!"
        );
    }
}
