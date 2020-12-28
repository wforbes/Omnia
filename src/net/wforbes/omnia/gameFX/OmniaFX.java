package net.wforbes.omnia.gameFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.wforbes.omnia.gameFX.controls.KeyPolling;

public class OmniaFX extends Application {

    private static Stage stage;
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    private static final int SCALE = 2;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
        Scene scene = new Scene(root, WIDTH * SCALE, HEIGHT * SCALE);

        KeyPolling.getInstance().pollScene(scene);

        primaryStage.setResizable(true);
        primaryStage.setWidth(WIDTH * SCALE);
        primaryStage.setHeight(HEIGHT * SCALE);


        primaryStage.setScene(scene);
        //primaryStage.setTitle("OmniaFX - Testing JavaFX for the Omnia game engine");

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/windowIcon.png")));

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return stage;
    }

    public void setStageTitle(String title) {
        stage.setTitle(title);
    }
}
