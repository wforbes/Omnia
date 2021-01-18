package net.wforbes.omnia.gameFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.wforbes.omnia.gameFX.controls.KeyPolling;

public class OmniaFX extends Application {
//For implementing window size change listener:
//  https://stackoverflow.com/questions/44159794/get-single-stage-resize-event-when-user-releases-left-mouse-button
    private static Stage stage;
    private static final int WIDTH = 320;
    private static final int HEIGHT = 240;
    private static final int SCALE = 4;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
        Scene scene = new Scene(root, WIDTH * SCALE, HEIGHT * SCALE);
        this.initStyles(scene);

        KeyPolling.getInstance().pollScene(scene);

        primaryStage.setResizable(true);
        primaryStage.setWidth(WIDTH * SCALE);
        primaryStage.setHeight(HEIGHT * SCALE);


        primaryStage.setScene(scene);
        //primaryStage.setTitle("OmniaFX - Testing JavaFX for the Omnia game engine");

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/windowIcon.png")));


        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private void initStyles(Scene scene) {
        String mainMenuStyles = getClass().getResource("/css/main-menu.css").toExternalForm();
        String topDownChatbox = getClass().getResource("/css/topdown-chatbox.css").toExternalForm();
        String pauseMenuStyles = getClass().getResource("/css/pause-menu.css").toExternalForm();
        String deathMenuStyles = getClass().getResource("/css/death-menu.css").toExternalForm();
        String guiWindowsStyles = getClass().getResource("/css/gui-windows.css").toExternalForm();
        scene.getStylesheets().addAll(
                mainMenuStyles, topDownChatbox, pauseMenuStyles, deathMenuStyles,
                guiWindowsStyles
        );

    }

    public static Stage getPrimaryStage() {
        return stage;
    }

    public static int getScaledWidth() {
        return WIDTH * SCALE;
    }

    public static int getScaledHeight() {
        return HEIGHT * SCALE;
    }

    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static int getScale() {
        return SCALE;
    }
}
