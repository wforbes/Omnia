package net.wforbes.omnia.topDown.gui;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.topDown.entity.Entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatWindowController {

    private GUIController gui;
    private Node chatPanel;
    private Button chatSendBtn;
    private TextField chatField;
    private TextArea chatArea;
    public final int CHATAREA_HEIGHT = 200;
    public final int CHATFIELD_HEIGHT = 50;
    public final int CHATBUTTON_HEIGHT = 58;
    public final int CHAT_VBOX_HEIGHT = 240;
    private final int CHAT_HBOX_HEIGHT = 34;
    private boolean chatWindowOpen;
    private StringBuilder chatBuilder;
    private String chatLog;
    private boolean showChatTimeStamps;


    public ChatWindowController(GUIController gui) {
        this.gui = gui;
        this.chatLog = "";
        this.showChatTimeStamps = true;
        this.chatBuilder = new StringBuilder("");
    }

    public Node getChatPanel() {
        if(chatPanel == null) {
            this.chatPanel =
                    gui.makeDraggableByTitleRegion(
                            createChatPanel()
                    );
        }
        return chatPanel;
    }

    //TODO: break this into helper functions
    private Node createChatPanel() {

        TitledPane titledPane = new TitledPane();
        titledPane.setText("Chat Window");
        titledPane.setCollapsible(false);
        titledPane.setPrefWidth(OmniaFX.getScaledWidth());

        chatArea = new TextArea();
        chatArea.setPrefSize(200, CHATAREA_HEIGHT);
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
        chatField.setPrefSize(1168, CHATFIELD_HEIGHT);
        chatField.setFont(new javafx.scene.text.Font("Century Gothic", 20));
        chatField.setFocusTraversable(true);
        chatField.setPromptText("Enter Chat or Commands Here..");
        chatField.setOnAction(event -> {
            this.parseChatField();
        });

        chatSendBtn = new Button("Send");
        chatSendBtn.setPrefSize(113, CHATBUTTON_HEIGHT);
        chatSendBtn.setFont(new Font("Franklin Gothic Medium", 20));
        chatSendBtn.setFocusTraversable(true);
        chatSendBtn.setOnMouseClicked(event -> {
            this.parseChatField();
        });

        HBox hBox = new HBox();
        hBox.setPrefSize(1280, 34);
        hBox.getChildren().addAll(chatField, chatSendBtn);

        VBox vBox = new VBox();
        vBox.setPrefSize(1280, CHAT_VBOX_HEIGHT);
        vBox.getChildren().addAll(chatArea, hBox);

        titledPane.setOpacity(0.75);

        FadeTransition titledPaneFadeIn = new FadeTransition(Duration.millis(500), titledPane);
        titledPaneFadeIn.setFromValue(0.75);
        titledPaneFadeIn.setToValue(1.0);

        FadeTransition titledPaneFadeOut = new FadeTransition(Duration.millis(500), titledPane);
        titledPaneFadeOut.setFromValue(1.0);
        titledPaneFadeOut.setToValue(0.75);

        titledPane.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            titledPaneFadeIn.play();
        });
        titledPane.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            titledPaneFadeOut.play();
        });

        titledPane.setContent(vBox);

        GUIController.configureBorder(titledPane);

        return titledPane;
    }

    private void checkNPCChat(String chatMsg) {
        //TODO: move NPC chat recognition to its own chatcontroller class
        //  like the chatMsg parts, interaction distance,  into the receiving party's class or chat controller
        for(Entity e : this.gui.level.entities) {
            if(Math.abs(this.gui.player.x - e.x) < 24 && Math.abs(this.gui.player.y - e.y) < 24) {
                //TODO: rework this to use regular expression parsing
                if(chatMsg.contains(e.getName())) {
                    if (chatMsg.contains("hello") || chatMsg.contains("hi")
                            || chatMsg.contains("hey") || chatMsg.contains("yo")
                            || chatMsg.contains("sup") || chatMsg.contains("greetings")
                    ) {
                        String eResponse = e.simpleChatResponse("greeting response");
                        if (!eResponse.equals("")) {
                            if (showChatTimeStamps) {
                                this.prependTimeStamp();
                            }
                            chatBuilder.append(e.getName()).append(" says,'").append(eResponse).append("'\n");
                        }
                    }
                }
            }
        }
    }
    private void prependTimeStamp() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm:ss");
        String timeStamp = dateFormat.format(date);
        chatBuilder.append("[").append(timeStamp).append("] ");
    }

    private void parseChatField() {
        String chatMsg = this.chatField.getText();
        if (chatMsg.equals("")) return;
        chatMsg = chatMsg.toLowerCase(Locale.ROOT);

        if (showChatTimeStamps) {
            this.prependTimeStamp();
        }

        if(chatMsg.startsWith("/")) { //issuing a command
            if (chatMsg.startsWith("/say ")) {
                chatMsg = chatMsg.substring(5);
                chatBuilder.append("You say, '").append(chatMsg).append("'\n");

                this.checkNPCChat(chatMsg);
            }

            if (chatMsg.startsWith("/shout ")) {
                chatBuilder.append("You shout, '").append(chatMsg.substring(7)).append("'\n");
            }
        } else { //non-command chat message
            //TODO: add default chat channel setting that non-command text goes to
            chatBuilder.append("You say, '").append(chatMsg).append("'\n");

            this.checkNPCChat(chatMsg);
        }

        chatArea.setText(chatBuilder.toString());
        chatArea.appendText(""); // to trigger the scroll-to-bottom event
        chatField.setText("");
    }
}
