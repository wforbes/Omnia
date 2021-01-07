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
import java.util.HashMap;
import java.util.Locale;

public class ChatWindowController {

    private GUIController gui;

    private String windowTitle;
    private TitledPane titledPane;
    private TextArea chatArea;
    private TextField chatField;
    private Button chatSendBtn;
    private HBox chatLowerContainer;
    private VBox chatVerticalContainer;
    private Node chatPanel;

    public final int CHATAREA_HEIGHT = 200;
    public final int CHATFIELD_HEIGHT = 50;
    public final int CHATBUTTON_HEIGHT = 58;
    public final int CHAT_VBOX_HEIGHT = 240;
    private final int CHAT_HBOX_HEIGHT = 34;
    private boolean chatWindowOpen;

    private HashMap<String,String> chatOutputMap;
    private StringBuilder chatBuilder;
    private String chatLog;
    private boolean showChatTimeStamps;
    private char DEFAULT_CMD_CHAR = '/';
    //TODO: add default chat channel setting that non-command chatMsg goes to
    private static final String DEFAULT_CHAT_CMD = "SAY";


    public ChatWindowController(GUIController gui) {
        this.gui = gui;
        this.windowTitle = "Chat Window";
        this.chatLog = "";
        this.showChatTimeStamps = true;
        this.chatBuilder = new StringBuilder("");
        this.chatOutputMap = this.gui.level.dialogController.getChatOutputMap();
        this.gui.level.dialogController.setChatWindowController(this);
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

    private Node createChatPanel() {
        this.createTitledPane();
        this.createChatArea();
        this.reloadChatLog();
        this.createChatField();
        this.createChatSendButton();
        this.createChatLowerContainer();
        this.createChatVerticalContainer();
        this.setWindowFadeTransitions();
        titledPane.setContent(chatVerticalContainer);
        GUIController.configureBorder(titledPane);
        return titledPane;
    }

    private void createTitledPane() {
        this.titledPane = new TitledPane();
        this.titledPane.setText("Chat Window");
        this.titledPane.setCollapsible(false);
        this.titledPane.setPrefWidth(OmniaFX.getScaledWidth());
        this.titledPane.setOpacity(0.75);
    }

    private void createChatArea() {
        chatArea = new TextArea();
        chatArea.setPrefSize(200, CHATAREA_HEIGHT);
        chatArea.getStyleClass().add("chatArea");
        chatArea.setEditable(false);
        chatArea.textProperty().addListener(
                (ChangeListener<Object>) (observable, oldValue, newValue) -> {
                    chatArea.setScrollTop(Double.MAX_VALUE); //this will scroll to the bottom
                    //use Double.MIN_VALUE to scroll to the top
                });
    }

    private void reloadChatLog() {
        if (this.chatLog.length() > 0) {
            this.chatBuilder.append(this.chatLog);
            this.chatArea.setText(this.chatBuilder.toString());
            this.chatArea.appendText("");
        }
    }

    private void createChatField() {
        chatField = new TextField();
        chatField.setPrefSize(1168, CHATFIELD_HEIGHT);
        chatField.setFont(new javafx.scene.text.Font("Century Gothic", 20));
        chatField.setFocusTraversable(true);
        chatField.setPromptText("Enter Chat or Commands Here..");
        chatField.setOnAction(event -> {
            this.parseChatField();
        });
    }

    private void createChatSendButton() {
        chatSendBtn = new Button("Send");
        chatSendBtn.setPrefSize(113, CHATBUTTON_HEIGHT);
        chatSendBtn.setFont(new Font("Franklin Gothic Medium", 20));
        chatSendBtn.setFocusTraversable(true);
        chatSendBtn.setOnMouseClicked(event -> {
            this.parseChatField();
        });
    }

    private void createChatLowerContainer() {
        chatLowerContainer = new HBox();
        chatLowerContainer.setPrefSize(1280, 34);
        chatLowerContainer.getChildren().addAll(chatField, chatSendBtn);
    }

    private void createChatVerticalContainer() {
        chatVerticalContainer = new VBox();
        chatVerticalContainer.setPrefSize(1280, CHAT_VBOX_HEIGHT);
        chatVerticalContainer.getChildren().addAll(chatArea, chatLowerContainer);
    }

    private void setWindowFadeTransitions() {
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
    }

    private void checkNPCChat(String chatMsg) {
        //TODO: move NPC chat recognition to its own chatcontroller class
        //  like the chatMsg parts, interaction distance,  into the receiving party's class or chat controller

    }

    private void prependTimeStamp() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm:ss");
        String timeStamp = dateFormat.format(date);
        chatBuilder.append("[").append(timeStamp).append("] ");
    }

    //TODO: move to dialogC


    private boolean tryAsChatCmd(String chatMsg) {
        for(String chatCmd : chatOutputMap.keySet()) {
            if(chatMsg.toUpperCase().startsWith("/"+chatCmd)) {
                this.handleChatMsg(chatCmd, chatMsg);
                return true;
            }
        }
        return false;
    }

    private void handleChatMsg(String chatCmd, String chatMsg) {
        chatMsg = chatMsg.substring(chatCmd.length() + 2);//2 for '/' and ' '
        chatBuilder.append(chatOutputMap.get(chatCmd)).append(chatMsg).append("\n");
        gui.level.dialogController.submitChatMsg(gui.player, chatCmd, chatMsg);
    }


    //TODO: add default chat channel setting that non-command chatMsg goes to
    private void handleChatMsg(String chatMsg) {
        chatBuilder.append(chatOutputMap.get(DEFAULT_CHAT_CMD)).append(chatMsg).append("\n");
        gui.level.dialogController.submitChatMsg(gui.player, DEFAULT_CHAT_CMD, chatMsg);
    }

    private boolean tryAsSystemCmd(String chatMsg) {
        //TODO: gui.level.commandController.checkForSysCmd(chatMsg)
        return false;
    }

    private void handleUnknownCmd(String chatMsg) {
        String attemptedCmd;
        int firstSpaceIndex = chatMsg.indexOf(" ");
        attemptedCmd = (firstSpaceIndex != -1) ?
                chatMsg.substring(1, firstSpaceIndex)
                : chatMsg.substring(1);
        chatBuilder.append("The command \"")
                .append(attemptedCmd)
                .append("\" is not recognized!");

    }

    private void parseChatField() {
        String chatMsg = this.chatField.getText();
        if (chatMsg.equals("")) return;
        chatMsg = chatMsg.toLowerCase();

        if (showChatTimeStamps) {
            this.prependTimeStamp();
        }

        if(chatMsg.startsWith(String.valueOf(DEFAULT_CMD_CHAR))) {
            if (!this.tryAsChatCmd(chatMsg)) {
                if(!this.tryAsSystemCmd(chatMsg)) {
                    this.handleUnknownCmd(chatMsg);
                }
            }
        } else {
            //TODO: add default chat channel setting that non-command chatMsg goes to
            this.handleChatMsg(chatMsg);
        }

        chatArea.setText(chatBuilder.toString());
        chatArea.appendText(""); // to trigger the scroll-to-bottom event
        chatField.setText("");
    }

    public void parseNPCChat(String name, String chatCmd, String chatMsg) {
        if(chatMsg == null || chatMsg.equals(""))
            return;

        if (showChatTimeStamps) {
            this.prependTimeStamp();
        }

        //TODO: add recognition of chat command used
        chatBuilder.append(name).append(" says, '").append(chatMsg).append("'\n");

    }
}
