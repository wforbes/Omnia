package net.wforbes.omnia.gameFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.wforbes.omnia.gameFX.controls.keyboard.KeyPolling;

public class OmniaFX extends Application {
//For implementing window size change listener:
//  https://stackoverflow.com/questions/44159794/get-single-stage-resize-event-when-user-releases-left-mouse-button
    private static Stage stage;
    private Scene scene;
    private static int WIDTH = 320;//1920;
    private static int HEIGHT = 240; //1080;
    private static int SCALE = 4;//1;
    private static double screenWidth = 320;
    private static double screenHeight = 240;
    private static double screenScale = 1;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        Parent root = loader.load();
        scene = new Scene(root, WIDTH * SCALE, HEIGHT * SCALE);
        this.initStyles(scene);

         //TODO: set game to scale on window resize
        /*
        GameController gameController = loader.getController();
        gameController.setupScaling(scene);
        */

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

    public Scene getScene() {
        return scene;
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
