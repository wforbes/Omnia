package net.wforbes.omnia.topDown.gui;

import javafx.animation.FadeTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;
import net.wforbes.omnia.game.Game;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameFX.controllers.GameController;
import net.wforbes.omnia.gameState.TopDownState;
import net.wforbes.omnia.topDown.entity.Entity;
import net.wforbes.omnia.topDown.graphics.Colors;
import net.wforbes.omnia.topDown.level.Level;
import net.wforbes.omnia.topDown.entity.Player;
import net.wforbes.omnia.topDown.graphics.Screen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GUI {

    private Player player;
    private Level level;
    private GameController gameController;

    //chatbox
    private StringBuilder chatBuilder;
    private Button chatSendBtn;
    private TextField chatField;
    private TextArea chatArea;
    private final int CHATAREA_HEIGHT = 200;
    private final int CHATFIELD_HEIGHT = 50;
    private final int CHATBUTTON_HEIGHT = 58;
    private final int CHAT_VBOX_HEIGHT = 240;
    private final int CHAT_HBOX_HEIGHT = 34;
    private boolean chatWindowOpen;
    private String chatLog;
    private boolean showChatTimeStamps;
    private final BooleanProperty dragModeActiveProperty =
            new SimpleBooleanProperty(this, "dragModeActive", true);
    private final CheckBox dragModeCheckbox = new CheckBox("Drag mode");

    private boolean chatInputIsOpen = false;

    public GUI (TopDownState state) {

        this.gameController = state.getGsm().gameController; //TODO:improve encapsulation
        this.level = state.getLevel();
        this.player = state.getPlayer();
        this.chatLog = "";
        this.showChatTimeStamps = true;
        this.chatBuilder = new StringBuilder("");

        final Node chatPanel =
                makeDraggableByTitleRegion(createChatPanel());

        chatPanel.relocate(0, OmniaFX.getScaledHeight() - (CHAT_VBOX_HEIGHT + 42));


        final Pane panelsPane = new Pane();
        panelsPane.setStyle("-fx-background-color: rgba(0, 100, 100, 0.5); -fx-background-radius: 10;");
        panelsPane.setPrefSize(0.1,0.1);
        panelsPane.getChildren().addAll(chatPanel);

        dragModeActiveProperty.bind(dragModeCheckbox.selectedProperty());

        this.gameController.gameBorder.setTop(panelsPane);

    }

    private static final class DragContext {
        public double mouseAnchorX;
        public double mouseAnchorY;
        public double initialTranslateX;
        public double initialTranslateY;
    }

    //https://stackoverflow.com/questions/41252958/how-to-detect-when-javafx-mouse-event-occurs-in-the-label-are-of-a-titledpane
    //TODO: find better implementation that doesnt rely on literal class name
    private boolean targetIsTitleRegion(EventTarget target) {
        String titleClass = "class javafx.scene.control.skin.TitledPaneSkin$TitleRegion";
        return target.getClass().toString().equals(titleClass) || // anywhere on title region except title Text
                (target instanceof Node && ((Node) target).getParent().getClass().toString().equals(titleClass));// title Text
    }

    //https://docs.oracle.com/javase/8/javafx/events-tutorial/draggablepanelsexamplejava.htm
    private Node makeDraggableByTitleRegion(final Node node) {
        final DragContext dragContext = new DragContext();
        final Group wrapGroup = new Group(node);
        wrapGroup.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            System.out.println("UI got a mouse press..");
            if(targetIsTitleRegion(event.getTarget())) {
                System.out.println("UI title was pressed");
                //if (dragModeActiveProperty.get()) {//TODO: add toggle feature for locking windows
                    // remember initial mouse cursor coordinates
                    // and node position
                    dragContext.mouseAnchorX = event.getX();
                    dragContext.mouseAnchorY = event.getY();
                    dragContext.initialTranslateX =
                            node.getTranslateX();
                    dragContext.initialTranslateY =
                            node.getTranslateY();
                //}
            }
        });

        wrapGroup.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
            System.out.println("UI got a mouse drag..");
            if(targetIsTitleRegion(event.getTarget())) {
                System.out.println("UI title is being dragged");
                //if (dragModeActiveProperty.get()) {//TODO: add toggle feature for locking windows
                    // shift node from its initial position by delta
                    // calculated from mouse cursor movement
                    node.setTranslateX(
                            dragContext.initialTranslateX
                                    + event.getX()
                                    - dragContext.mouseAnchorX);
                    node.setTranslateY(
                            dragContext.initialTranslateY
                                    + event.getY()
                                    - dragContext.mouseAnchorY);
                //}
            }
        });
        return wrapGroup;
    }

    private static RadioButton createRadioButton(final String text,
                                                 final ToggleGroup toggleGroup,
                                                 final boolean selected) {
        final RadioButton radioButton = new RadioButton(text);
        radioButton.setToggleGroup(toggleGroup);
        radioButton.setSelected(selected);

        return radioButton;
    }

    private static HBox createHBox(final double spacing,
                                   final Node... children) {
        final HBox hbox = new HBox(spacing);
        hbox.getChildren().addAll(children);
        return hbox;
    }

    private static VBox createVBox(final double spacing,
                                   final Node... children) {
        final VBox vbox = new VBox(spacing);
        vbox.getChildren().addAll(children);
        return vbox;
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
        chatField.setFont(new Font("Century Gothic", 20));
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

        configureBorder(titledPane);

        return titledPane;
    }
    private static void configureBorder(final Region region) {
        region.setStyle("-fx-background-color: white;"
                + "-fx-border-color: black;"
                + "-fx-border-width: 1;"
                + "-fx-border-radius: 6;"
                + "-fx-padding: 6;");
    }

    public void tick() {

    }

    //TODO: removal candidate
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

    //TODO: removal candidate
    public void closeChatWindow() {
        this.chatWindowOpen = false;
        this.chatLog += this.chatArea.getText();
        this.gameController.gameBorder.setBottom(null);
    }

    //TODO: removal candidate
    public boolean chatWindowIsOpen() {
        return this.chatWindowOpen;
    }

    private void prependTimeStamp() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm:ss");
        String timeStamp = dateFormat.format(date);
        chatBuilder.append("[").append(timeStamp).append("] ");
    }

    private void checkNPCChat(String chatMsg) {
        //TODO: move NPC chat recognition to its own chatcontroller class
        //  like the chatMsg parts, interaction distance,  into the receiving party's class or chat controller
        for(Entity e : this.level.entities) {
            if(Math.abs(player.x - e.x) < 24 && Math.abs(player.y - e.y) < 24) {
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
//Copyright from https://docs.oracle.com/javase/8/javafx/events-tutorial/draggablepanelsexamplejava.htm
/*
 * Copyright (c) 2012, 2014, Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
