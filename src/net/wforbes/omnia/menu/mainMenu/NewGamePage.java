package net.wforbes.omnia.menu.mainMenu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import net.wforbes.omnia.gameState.GameStateManager;
import net.wforbes.omnia.menu.MenuPage;


public class NewGamePage extends MenuPage {
    public Node getDisplayUI(MainMenu mainMenu) {
        VBox topVbox = new VBox(0);
        topVbox.setAlignment(Pos.CENTER);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.BASELINE_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 0, 0,20));
        grid.setMinWidth(600);
        grid.setMaxWidth(600);
        grid.setMinHeight(600);
        grid.setMaxHeight(600);
        grid.setBorder(new Border(new BorderStroke(
            Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT
        )));
        grid.setBackground(new Background(new BackgroundFill(
                Color.rgb(0, 0, 0, 0.5),
                new CornerRadii(10),
                new Insets(0)
        )));

        Button backBtn = new Button("< Back");
        grid.add(backBtn, 0, 0);

        Text pageTitleTxt = new Text("New Game");
        pageTitleTxt.getStyleClass().add("page-title-txt");
        grid.add(pageTitleTxt, 0, 1);

        Label nameLabel = new Label("Player Name");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter Name...");
        nameField.getStyleClass().add("menu-text-field");
        VBox nameVbox = new VBox(2);
        nameVbox.getChildren().addAll(nameLabel, nameField);
        grid.add(nameVbox, 0, 2);

        Button startBtn = new Button("Start Game");
        startBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            mainMenu.clearGameBorder();
            mainMenu.getState().gsm.initializePlayerData(nameField.getText());
            mainMenu.getState().gsm.setState(GameStateManager.OVERWORLDSTATE);
        });
        grid.add(startBtn, 0, 3);

        topVbox.getChildren().addAll(grid);
        return topVbox;
    }
}
