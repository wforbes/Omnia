package net.wforbes.omnia.topDown.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GUI {

    private Player player;
    private Level level;
    private GameController gameController;

    //chatbox
    private StringBuilder chatBuilder;
    private Button chatSendBtn;
    private TextField chatField;
    private TextArea chatArea;
    private boolean chatWindowOpen;
    private String chatLog;
    private boolean showChatTimeStamps;


    private boolean chatInputIsOpen = false;

    public GUI (GameController gameController) {
        this.gameController = gameController;
        this.chatLog = "";
    }

    public void tick() {

    }

    public void openChatWindow() {
        this.chatInputIsOpen = true;
        chatBuilder = new StringBuilder("");
        chatArea = new TextArea();
        chatArea.setOpacity(0.75);
        chatArea.setPrefSize(200, 200);
        chatArea.getStyleClass().add("chatArea");
        chatArea.setEditable(false);
        chatArea.textProperty().addListener(
                (ChangeListener<Object>) (observable, oldValue, newValue) -> {
            chatArea.setScrollTop(Double.MAX_VALUE); //this will scroll to the bottom
            //use Double.MIN_VALUE to scroll to the top
        });
        if (this.chatLog.length() > 0) {
           this.chatBuilder.append(this.chatLog);
           this.chatArea.setText(this.chatBuilder.toString());
           this.chatArea.appendText("");
        }

        chatField = new TextField();
        chatField.setPrefSize(1168, 50);
        chatField.setFont(new Font("Century Gothic", 20));
        chatField.setFocusTraversable(true);
        chatField.setPromptText("Enter Chat or Commands Here..");
        chatField.setOnAction(event -> {
            this.parseChatField();
        });

        chatSendBtn = new Button("Send");
        chatSendBtn.setPrefSize(113, 58);
        chatSendBtn.setFont(new Font("Franklin Gothic Medium", 20));
        chatSendBtn.setFocusTraversable(true);
        chatSendBtn.setOnMouseClicked(event -> {
            this.parseChatField();
        });

        HBox hBox = new HBox();
        hBox.setPrefSize(1280, 34);
        hBox.getChildren().addAll(chatField, chatSendBtn);

        VBox vBox = new VBox();
        vBox.setPrefSize(1280, 240);
        vBox.getChildren().addAll(chatArea, hBox);

        this.chatWindowOpen = true;
        this.gameController.gameBorder.setBottom(vBox);
        this.chatField.requestFocus();
    }

    public void closeChatWindow() {
        this.chatWindowOpen = false;
        this.chatLog += this.chatArea.getText();
        this.gameController.gameBorder.setBottom(null);
    }

    public boolean chatWindowIsOpen() {
        return this.chatWindowOpen;
    }

    private void parseChatField() {
        String chatMsg = this.chatField.getText();

        System.out.println(chatMsg);

        if (chatMsg.equals("")) return;

        //append timestamp to chat message
        if (showChatTimeStamps) {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm:ss");
            String timeStamp = dateFormat.format(date);
            chatBuilder.append("[").append(timeStamp).append("] ");
        }

        if(chatMsg.startsWith("/")) { //issuing a command
            if (chatMsg.startsWith("/say ")) {
                chatBuilder.append("You say, '").append(chatMsg.substring(5)).append("'\n");
            }

            if (chatMsg.startsWith("/shout ")) {
                chatBuilder.append("You shout, '").append(chatMsg.substring(7)).append("'\n");
            }
        } else { //non-command chat message
            //TODO: add default chat channel setting that non-command text goes to
            chatBuilder.append("You say, '").append(chatMsg).append("'\n");
        }

        chatArea.setText(chatBuilder.toString());
        chatArea.appendText(""); // to trigger the scroll-to-bottom event
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
