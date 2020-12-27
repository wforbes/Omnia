package net.wforbes.gameFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.wforbes.gameFX.controls.KeyPolling;

public class OmniaFX extends Application {

    private Stage stage;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
        Scene scene = new Scene(root);

        KeyPolling.getInstance().pollScene(scene);

        primaryStage.setScene(scene);
        primaryStage.setTitle("OmniaFX - Testing JavaFX for the Omnia game engine");

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/windowIcon.png")));

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setStageTitle(String title) {
        stage.setTitle(title);
    }
}
