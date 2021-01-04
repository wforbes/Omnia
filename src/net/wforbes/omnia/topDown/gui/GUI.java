package net.wforbes.omnia.topDown.gui;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import net.wforbes.omnia.game.Game;
import net.wforbes.omnia.gameFX.controllers.GameController;
import net.wforbes.omnia.topDown.graphics.Colors;
import net.wforbes.omnia.topDown.level.Level;
import net.wforbes.omnia.topDown.entity.Player;
import net.wforbes.omnia.topDown.graphics.Screen;

public class GUI {

    private Player player;
    private Level level;
    private GameController gameController;

    //chatbox
    private StringBuilder chatBuilder;
    private Button chatSendBtn;
    private TextField chatField;
    private TextArea chatArea;


    private boolean chatInputIsOpen = false;

    public GUI (GameController gameController) {
        this.gameController = gameController;
    }

    public void tick() {

    }

    public void openChatInput() {
        this.chatInputIsOpen = true;
        chatBuilder = new StringBuilder("");
        chatArea = new TextArea();
        chatArea.setOpacity(0.75);
        chatArea.setPrefSize(200, 200);
        chatArea.getStyleClass().add("chatArea");
        chatArea.setDisable(true);

        chatField = new TextField();
        chatField.setPrefSize(1168, 50);
        chatField.setFont(new Font("Century Gothic", 20));
        chatField.setFocusTraversable(false);
        chatField.setPromptText("Enter Chat or Commands Here..");
        chatField.setOnAction(event -> {
            this.parseChatField();
        });

        chatSendBtn = new Button("Send");
        chatSendBtn.setPrefSize(113, 58);
        chatSendBtn.setFont(new Font("Franklin Gothic Medium", 20));
        chatSendBtn.setFocusTraversable(false);
        chatSendBtn.setOnMouseClicked(event -> {
            this.parseChatField();
        });

        HBox hBox = new HBox();
        hBox.setPrefSize(1280, 34);
        hBox.getChildren().addAll(chatField, chatSendBtn);

        VBox vBox = new VBox();
        vBox.setPrefSize(1280, 240);
        vBox.getChildren().addAll(chatArea, hBox);

        this.gameController.gameBorder.setBottom(vBox);
    }

    private void parseChatField() {
        String chatMsg = this.chatField.getText();
        System.out.println(chatMsg);
        if (chatMsg.equals("")) return;
        if(chatMsg.startsWith("/")) {
            if (chatMsg.startsWith("/say ")) {
                chatBuilder.append("You say, '").append(chatMsg.substring(5)).append("'\n");
            }

            if (chatMsg.startsWith("/shout ")) {
                chatBuilder.append("You shout, '").append(chatMsg.substring(6)).append("'\n");
            }
        } else {
            chatBuilder.append("You say, '").append(chatMsg).append("'\n");
        }

        chatArea.setText(chatBuilder.toString());
        chatField.setText("");
    }

    public void render(Screen screen) {
        //System.out.println(screen.xOffset + " " + screen.yOffset);
        //System.out.println(player.yOffset);

        if (chatInputIsOpen) {
            renderChatInput(screen);
        }

        //screen.render(screen.xOffset, screen.yOffset + Game.HEIGHT-1, (0+23*32), Colors.get(-1, 100, 555, 543), 0, 1);
    }

    private void renderChatInput(Screen screen) {

        //screen.render();
    }
}
