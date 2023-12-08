module net.wforbes.gameFX {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    requires kotlin.stdlib;
    requires java.desktop;
    requires org.jfree.fxgraphics2d;
    requires com.almasb.fxgl.all;
    requires javafx.web;
    requires jdk.jsobject;
    requires java.sql;

    opens net.wforbes.omnia.gameFX to javafx.graphics;
    opens net.wforbes.omnia.gameFX.controllers to javafx.fxml;
}