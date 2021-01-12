package net.wforbes.omnia.topDown.gui;

import javafx.animation.FadeTransition;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import net.wforbes.omnia.gameFX.OmniaFX;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ChatWindowController {

    private GUIController gui;

    private String windowTitle;
    private TitledPane titledPane;
    private TextArea chatArea;
    private TextFlow chatAreaFlow;
    private Font chatAreaFont;
    private TextField chatField;
    private boolean chatFieldFocused;
    private Font chatFieldFont;
    private Button chatSendBtn;
    private boolean chatSendBtnFocused;
    private HBox chatLowerContainer;
    private VBox chatVerticalContainer;
    private Node chatPanel;

    public final int CHATAREA_HEIGHT = 200;
    public final int CHATFIELD_HEIGHT = 50;
    public final int CHATBUTTON_HEIGHT = 58;
    public final int CHAT_VBOX_HEIGHT = 240;
    private final int CHAT_HBOX_HEIGHT = 34;
    private boolean chatWindowOpen;

    private int lastInputCommandTick = 0;

    private HashMap<String, Color> chatColorMap;
    private HashMap<String,String> chatOutputMap;
    private StringBuilder chatBuilder;
    private String chatLog;
    private ArrayList<Text> chatHistory;
    //private Text chatItem;

    private boolean showChatTimeStamps;
    private char DEFAULT_CMD_CHAR = '/';
    //TODO: add default chat channel setting that non-command chatMsg goes to
    private static final String DEFAULT_CHAT_CMD = "SAY";
    private String currentChatCmd;
    private ScrollPane chatAreaScroll;


    public ChatWindowController(GUIController gui) {
        this.gui = gui;
        this.windowTitle = "Chat Window";
        this.chatAreaFont = new Font("Century Gothic", 18);
        this.chatFieldFont = new Font("Century Gothic", 18);
        this.chatLog = "";
        if(this.chatHistory == null) this.chatHistory = new ArrayList<>();
        this.showChatTimeStamps = true;
        this.chatBuilder = new StringBuilder("");
        this.chatOutputMap = this.gui.level.dialogController.getChatOutputMap();
        this.setChatColorMap();
        this.gui.level.dialogController.setChatWindowController(this);
    }

    private void setChatColorMap() {
        chatColorMap = new HashMap<>();
        chatColorMap.put("SAY", Color.BLACK);
        chatColorMap.put("SHOUT", Color.RED);
        chatColorMap.put("WHISPER", Color.PURPLE);
    }

    public TextField getChatField() {
        return chatField;
    }

    private boolean keyInputIsReady() {
        return gui.gameController.gsm.getCurrentState().getTickCount()
                - this.lastInputCommandTick > 20;
    }

    public void tick() {
        /*
        int tickCount = gui.gameController.gsm.getCurrentState().getTickCount();
        if (gui.gameController.gsm.isKeyDown(KeyCode.ENTER)
                && !gui.gameController.gsm.getCurrentState().isPaused()
                && this.keyInputIsReady()) {
            System.out.println("chatwindow got enter key press");
            this.lastInputCommandTick = tickCount;
            this.chatField.requestFocus();
        }
         */
    }

    public Node getWindowPanel() {
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
        //this.reloadChatLog();
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
        chatAreaScroll = new ScrollPane();
        chatAreaScroll.setPrefViewportWidth(1280);
        chatAreaScroll.setPrefViewportHeight(200);
        chatAreaFlow = new TextFlow();
        chatAreaFlow.setPrefSize(1220, 170);
        chatAreaFlow.getStyleClass().add("chatArea");
        chatAreaFlow.getChildren().addListener((ListChangeListener<Node>) ((change) -> {
            chatAreaFlow.layout();
            chatAreaScroll.layout();
            chatAreaScroll.setVvalue(1.0f);
        }));
        chatAreaScroll.setContent(chatAreaFlow);
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

        this.chatFieldFocused = chatField.focusedProperty().getValue();
        chatField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            this.gui.setComponentHasFocus(newValue);
        });
    }

    public boolean chatFieldIsFocused() {
        return chatFieldFocused;
    }

    private void createChatSendButton() {
        chatSendBtn = new Button("Send");
        chatSendBtn.setPrefSize(113, CHATBUTTON_HEIGHT);
        chatSendBtn.setFont(new Font("Franklin Gothic Medium", 20));
        chatSendBtn.setFocusTraversable(true);
        chatSendBtn.setOnMouseClicked(event -> {
            this.parseChatField();
        });
        this.chatSendBtnFocused = chatSendBtn.focusedProperty().getValue();
        chatSendBtn.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            this.gui.setComponentHasFocus(newValue);
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
        //chatVerticalContainer.getChildren().addAll(chatArea, chatLowerContainer);
        chatVerticalContainer.getChildren().addAll(chatAreaScroll, chatLowerContainer);
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
        currentChatCmd = chatCmd;
        chatBuilder.append(chatOutputMap.get(chatCmd)).append(", \"")
                .append(chatMsg).append("\"\n");
        gui.level.dialogController.submitChatMsg(gui.player, chatCmd, chatMsg);
    }


    //TODO: add default chat channel setting that non-command chatMsg goes to
    //TODO: move chat/string building to dialog controller (?)
    private void handleChatMsg(String chatMsg) {
        currentChatCmd = DEFAULT_CHAT_CMD;
        chatBuilder.append(chatOutputMap.get(DEFAULT_CHAT_CMD))
                .append(", \"").append(chatMsg).append("\"\n");
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
            this.handleChatMsg(chatMsg);
        }
        Text chat = new Text(chatBuilder.toString());
        this.chatBuilder.setLength(0);
        chat.setFont(this.chatAreaFont);
        chat.setFill(this.chatColorMap.get(this.currentChatCmd));
        chatAreaFlow.getChildren().add(chat);
        chatField.setText("");
        /*
        chatArea.setText(chatBuilder.toString());
        chatArea.appendText(""); // to trigger the scroll-to-bottom event
        chatField.setText("");
         */
    }

    public void parseNPCChat(String name, String chatCmd, String chatMsg) {
        if(chatMsg == null || chatMsg.equals(""))
            return;

        if (showChatTimeStamps) {
            this.prependTimeStamp();
        }

        //TODO: add recognition of chat command used
        chatBuilder.append(name).append(" says, \"").append(chatMsg).append("\"\n");

    }


    //TODO: store history with cmd and formatting data
    //  to avoid double loop and preserve previous appearance
    //TODO: wait to implement until tearDown of GUI is implemented
    /*
    private void reloadChatLog() {
        /*
        if (this.chatLog.length() > 0) {
            this.chatBuilder.append(this.chatLog);
            this.chatArea.setText(this.chatBuilder.toString());
            this.chatArea.appendText("");
        }
        for(String chatCmd : chatOutputMap.keySet()) {
            if(chatMsg.toUpperCase().startsWith("/"+chatCmd)) {
                this.handleChatMsg(chatCmd, chatMsg);
                return true;
            }
        }
        chatMsg = chatMsg.substring(chatCmd.length() + 2);//2 for '/' and ' '
        currentChatCmd = chatCmd;
        chatBuilder.append(chatOutputMap.get(chatCmd)).append(chatMsg).append("\n");
        Text chat = new Text(chatBuilder.toString());
        this.chatBuilder.setLength(0);
        chat.setFont(this.chatAreaFont);
        chat.setFill(this.chatColorMap.get(this.currentChatCmd));
        chatAreaFlow.getChildren().add(chat);
        */
    //String chatMsg = "";
    //if(this.chatHistory.size() > 0)
    //System.out.println(this.chatHistory.get(0).toString());
                /*
        for(Text t : this.chatHistory) {
            for(String chatCmd : chatOutputMap.keySet()) {
                if(t.toString().toUpperCase().startsWith("/")) {

                    this.chatAreaFlow.getChildren().addAll(this.chatHistory);
                }
            }
        }*/

    //}
}
