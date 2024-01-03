package net.wforbes.omnia.overworld.gui;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import net.wforbes.omnia.game.Game;
import net.wforbes.omnia.overworld.entity.dialog.DialogController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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


    public final int CHATAREA_SCROLL_WIDTH = Game.getScaledWidth() - 50;
    public final int CHATAREA_SCROLL_HEIGHT = 200;
    public final int CHATAREA_FLOW_WIDTH = Game.getScaledWidth();
    public final int CHATAREA_FLOW_HEIGHT = 170;
    public final int CHATFIELD_WIDTH = (int)(Game.getScaledWidth()*0.0875);
    public final int CHATFIELD_HEIGHT = 42;
    public final int CHATBUTTON_WIDTH = 113;
    public final int CHATBUTTON_HEIGHT = 42;
    public final int CHAT_VBOX_HEIGHT = 240;
    public final int CHAT_LOWER_WIDTH = Game.getScaledWidth();
    public final int CHAT_LOWER_HEIGHT = 34;
    public Dimension2D windowSize = new Dimension2D(Game.getScaledWidth(),
            (double)CHATAREA_SCROLL_HEIGHT+(double)CHATFIELD_HEIGHT+75);

    private int lastInputCommandTick = 0;

    private HashMap<String, Color> chatColorMap;
    private HashMap<String,String> chatOutputMap;
    private StringBuilder chatBuilder;
    private String chatLog;
    private ArrayList<Text> chatHistory;

    private boolean showChatTimeStamps;
    private char DEFAULT_CMD_CHAR = '/';
    //TODO: add default chat channel setting that non-command chatMsg goes to
    private static final String DEFAULT_CHAT_CMD = "SAY";
    private String currentChatCmd;
    private ScrollPane chatAreaScroll;

    private DialogController dialogController;

    public ChatWindowController(GUIController gui) {
        this.gui = gui;
        this.windowTitle = "Chat Window";
        this.chatAreaFont = new Font("Century Gothic", 18);
        this.chatFieldFont = new Font("Century Gothic", 18);
        this.chatLog = "";
        if(this.chatHistory == null) this.chatHistory = new ArrayList<>();
        this.showChatTimeStamps = true;
        this.chatBuilder = new StringBuilder();
        this.chatOutputMap = this.gui.gameState.world.getDialogController().getChatOutputMap();
        this.setChatColorMap();
        this.gui.gameState.world.getDialogController().setChatWindowController(this);
        this.dialogController = this.gui.gameState.world.getDialogController();
    }

    //TODO: refactor chat channels and their colors into settings
    private void setChatColorMap() {
        chatColorMap = new HashMap<>();
        chatColorMap.put("SAY", Color.BLACK);
        chatColorMap.put("SHOUT", Color.RED);
        chatColorMap.put("WHISPER", Color.PURPLE);
    }

    public TextField getChatField() { return chatField; }

    public double getWindowHeight() {
        return this.windowSize.getHeight();
    }

    public void focusChatField() {
        chatField.requestFocus();
        this.setActiveThenFadeOut();
    }

    public void tick() {}

    public Node getWindowPanel() {
        if(chatPanel == null) {
            this.chatPanel = createChatPanel();
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
        DragResizer.makeResizable(titledPane, gui);
        return titledPane;
    }

    private void createTitledPane() {
        this.titledPane = new TitledPane();
        this.titledPane.setText("Chat Window");
        this.titledPane.setCollapsible(false);
        this.titledPane.setMinSize(CHATAREA_SCROLL_WIDTH, CHATAREA_FLOW_HEIGHT);
        this.titledPane.setOpacity(GUIController.OPACITY_MAX);
    }

    private void createChatArea() {
        chatAreaScroll = new ScrollPane();
        chatAreaScroll.setFitToWidth(true);
        chatAreaScroll.setPrefViewportWidth(CHATAREA_SCROLL_WIDTH);
        chatAreaScroll.setPrefViewportHeight(CHATAREA_SCROLL_HEIGHT);
        /* Start of javascript implementation...
        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();
        engine.documentProperty().addListener((obs, oldDoc, newDoc) -> {
            if (newDoc != null) {
                JSObject window = (JSObject) engine.executeScript("window");
                window.setMember("app", this);
            }
        });

        StringBuilder html = new StringBuilder();
        html.append("<html><body><div name='text'>");

        html.append("<ul><li>Welcome to <a href='#' onclick='app.process(\"Omnia\")'>")
            .append("OMNIA")
            .append("</a></li></ul>");
        html.append("</div></body></html>");
        engine.loadContent(html.toString());
        */

        chatAreaFlow = new TextFlow();
        chatAreaFlow.getStyleClass().add("chat-area");

        chatAreaFlow.getChildren().addListener((ListChangeListener<Node>) ((change) -> {
            //System.out.println(change);
            chatAreaFlow.layout();
            chatAreaScroll.layout();
            chatAreaScroll.setVvalue(1.0f);
        }));

        chatAreaScroll.setContent(chatAreaFlow);

    }

    private void createChatField() {
        chatField = new TextField();
        chatField.setMinSize(CHATFIELD_WIDTH, CHATFIELD_HEIGHT);
        HBox.setHgrow(chatField, Priority.ALWAYS);
        VBox.setVgrow(chatField, Priority.ALWAYS);
        chatField.setFont(new Font("Century Gothic", 20));
        chatField.setFocusTraversable(true);
        chatField.setPromptText("Enter Chat or Commands Here..");
        chatField.setOnAction(event -> {
            submitChat();
        });

        this.chatFieldFocused = chatField.focusedProperty().getValue();
        chatField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            this.gui.setGUIHasFocus(newValue);
        });
    }

    public boolean chatFieldIsFocused() {
        return chatFieldFocused;
    }

    private void createChatSendButton() {
        chatSendBtn = new Button("Send");
        chatSendBtn.setMinSize(CHATBUTTON_WIDTH, CHATBUTTON_HEIGHT);
        HBox.setHgrow(chatSendBtn, Priority.ALWAYS);
        VBox.setVgrow(chatSendBtn, Priority.ALWAYS);
        chatSendBtn.setFont(new Font("Franklin Gothic Medium", 20));
        chatSendBtn.setFocusTraversable(true);
        chatSendBtn.setOnMouseClicked(event -> {
            submitChat();
        });
        this.chatSendBtnFocused = chatSendBtn.focusedProperty().getValue();
        chatSendBtn.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            this.gui.setGUIHasFocus(newValue);
        });
    }

    private void createChatLowerContainer() {
        chatLowerContainer = new HBox();
        //chatLowerContainer.setPrefSize(1280, 34);
        //chatLowerContainer.setPrefHeight();
        HBox.setHgrow(chatLowerContainer, Priority.ALWAYS);
        VBox.setVgrow(chatLowerContainer, Priority.ALWAYS);
        chatLowerContainer.getChildren().addAll(chatField, chatSendBtn);
    }

    private void createChatVerticalContainer() {
        chatVerticalContainer = new VBox();
        //chatVerticalContainer.setPrefSize(1280, CHAT_VBOX_HEIGHT);
        //chatVerticalContainer.setMinHeight();
        chatVerticalContainer.getChildren().addAll(chatAreaScroll, chatLowerContainer);
    }

    private void submitChat() {
        this.parseChatField();
        gui.gameState.getManager().getGameController().gameCanvas.requestFocus();
        setActiveThenFadeOut();
    }
    private void submitChat(String msg) {
        String timeStampString = "";
        if (showChatTimeStamps) {
            timeStampString = this.getTimeStampString();
        }

        currentChatCmd = DEFAULT_CHAT_CMD;
        Text chat = new Text(chatBuilder.append(timeStampString + " ")
                .append(chatOutputMap.get(DEFAULT_CHAT_CMD))
                .append(", \"").append(msg).append("\"\n").toString()
        );

        this.chatBuilder.setLength(0);
        chat.setFont(this.chatAreaFont);
        chat.setFill(this.chatColorMap.get(this.currentChatCmd));
        chatAreaFlow.getChildren().add(chat);

        gui.gameState.world.getDialogController()
                .submitChatMsg(gui.gameState.player, DEFAULT_CHAT_CMD, msg);
        gui.gameState.getManager().getGameController().gameCanvas.requestFocus();
        setActiveThenFadeOut();
    }

    public void setActiveThenFadeOut() {
        titledPane.setOpacity(1.0);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(3000), actionEvent -> {
            FadeTransition titledPaneFadeOut = new FadeTransition(Duration.millis(500), titledPane);
            titledPaneFadeOut.setFromValue(1.0);
            titledPaneFadeOut.setToValue(GUIController.OPACITY_MAX);
            titledPaneFadeOut.play();
        }));
        timeline.play();
    }

    //TODO: make generic and add to GUIController
    private void setWindowFadeTransitions() {
        FadeTransition titledPaneFadeIn = new FadeTransition(Duration.millis(500), titledPane);
        titledPaneFadeIn.setFromValue(GUIController.OPACITY_MAX);
        titledPaneFadeIn.setToValue(1.0);

        FadeTransition titledPaneFadeOut = new FadeTransition(Duration.millis(500), titledPane);
        titledPaneFadeOut.setFromValue(1.0);
        titledPaneFadeOut.setToValue(GUIController.OPACITY_MAX);

        titledPane.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            titledPaneFadeIn.play();
        });
        titledPane.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            titledPaneFadeOut.play();
        });
    }



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

        gui.gameState.world.getDialogController().submitChatMsg(gui.gameState.player, chatCmd, chatMsg);
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

    private String getTimeStampString() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm:ss");
        String timeStamp = dateFormat.format(date);
        //chatBuilder.append("[").append(timeStamp).append("] ");
        return "[" + timeStamp + "]";
    }

    private void parseChatField() {
        String chatMsg = this.chatField.getText();
        if (chatMsg.equals("")) return;

        if(chatMsg.startsWith(String.valueOf(DEFAULT_CMD_CHAR))) {
            if (!this.tryAsChatCmd(chatMsg)) {
                if(!this.tryAsSystemCmd(chatMsg)) {
                    this.handleUnknownCmd(chatMsg);
                }
            }
        } else {
            this.submitChat(chatMsg);
        }

        chatField.setText("");
    }

    public void parseNPCChat(String name, String chatCmd, String chatMsg) {
        if(chatMsg == null || chatMsg.equals(""))
            return;

        String timeStampString = "";
        if (showChatTimeStamps) {
            //this.prependTimeStamp();
            timeStampString = this.getTimeStampString();
        }

        boolean hasLink = chatMsg.contains("[") && chatMsg.contains("]");
        if (hasLink) {
            chatMsg = name + " says, \"" + chatMsg + "\"\n";
            List<String> links = new ArrayList<>();
            int beforeLastLinkIdx = 0;
            int openIdx = chatMsg.indexOf('[');
            int closeIdx = chatMsg.indexOf(']', openIdx + 1);
            Text chat = new Text(timeStampString + " ");
            chat.setFont(this.chatAreaFont);
            chat.setFill(this.chatColorMap.get(this.currentChatCmd));
            chatAreaFlow.getChildren().add(chat);
            while (openIdx >= 0 && closeIdx >= 0) {
                String link = chatMsg.substring(openIdx+1, closeIdx);

                chat = new Text(chatMsg.substring(beforeLastLinkIdx, openIdx + 1));
                chat.setFont(this.chatAreaFont);
                chat.setFill(this.chatColorMap.get(this.currentChatCmd));
                chatAreaFlow.getChildren().add(chat);

                links.add(link);
                Hyperlink hyperlink = new Hyperlink(link);
                hyperlink.setFont(this.chatAreaFont);
                hyperlink.setOnAction(e -> submitChat(link));
                chatAreaFlow.getChildren().add(hyperlink);

                beforeLastLinkIdx = closeIdx;

                if (chatMsg.length() < openIdx + 1) break;
                if (chatMsg.indexOf('[', openIdx + 1) == -1) {
                    chat = new Text(chatMsg.substring(closeIdx, chatMsg.length() - 1)+ "\n");
                    chat.setFont(this.chatAreaFont);
                    chat.setFill(this.chatColorMap.get(this.currentChatCmd));
                    chatAreaFlow.getChildren().add(chat);
                    break;
                }
                openIdx = chatMsg.indexOf('[', openIdx + 1);
                closeIdx = chatMsg.indexOf(']', openIdx + 1);

            }
            links = null;

        } else {
            currentChatCmd = DEFAULT_CHAT_CMD;
            Text chat = new Text(timeStampString + name + " says, \"" + chatMsg + "\"\n");

            this.chatBuilder.setLength(0);
            chat.setFont(this.chatAreaFont);
            chat.setFill(this.chatColorMap.get(this.currentChatCmd));
            chatAreaFlow.getChildren().add(chat);
        }
    }

    public void teardown() {
        this.gui = null;
        this.chatAreaFont = null;
        this.chatFieldFont = null;
        //this.chatLog = ""; //TODO: save chat log?
        this.chatHistory = null; //TODO: save chat history?
        this.chatBuilder = null;
        this.chatOutputMap = null;
        this.dialogController = null;
        this.chatColorMap = null; //TODO: save chat color map along with history?
        this.titledPane.setVisible(false);
        this.titledPane = null;
        this.chatAreaScroll.setVisible(false);
        this.chatAreaScroll = null;
        this.chatSendBtn.setVisible(false);
        this.chatSendBtn = null;
    }
}
