package net.wforbes.omnia.u;

import javafx.application.Platform;
import javafx.scene.Node;

public class W {
    public static void out(String s){
        System.out.println(s);
    }

    //For use nodes that have requestFocus() fail
    //  and need for scene to catch up to their request
    public static void takeFocus(Node node) {
        Platform.runLater(() -> {
            if(!node.isFocused()){
                node.requestFocus();
            }
        });
    }
}
