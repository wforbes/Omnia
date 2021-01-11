package net.wforbes.omnia.topDown.gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventTarget;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.gameFX.controllers.GameController;
import net.wforbes.omnia.gameState.TopDownState;
import net.wforbes.omnia.topDown.level.Level;
import net.wforbes.omnia.topDown.entity.Player;

public class GUIController {

    public Player player;
    public Level level;
    public GameController gameController;

    private boolean componentHasFocus;

    //chat window
    public ChatWindowController chatWindowController;
    private Node chatPanel;

    private final BooleanProperty dragModeActiveProperty =
            new SimpleBooleanProperty(this, "dragModeActive", true);
    private final CheckBox dragModeCheckbox = new CheckBox("Drag mode");

    public GUIController(TopDownState state) {
        this.gameController = state.getGsm().gameController; //TODO:improve encapsulation
        this.level = state.getLevel();
        this.player = state.getPlayer();

        this.componentHasFocus = false;

        this.chatWindowController = new ChatWindowController(this);
        this.chatPanel = this.chatWindowController.getChatPanel();
        chatPanel.relocate(0, OmniaFX.getScaledHeight() - (chatWindowController.CHAT_VBOX_HEIGHT + 42));

        final Pane panelsPane = new Pane();
        panelsPane.setStyle("-fx-background-color: rgba(0, 100, 100, 0.5); -fx-background-radius: 10;");
        panelsPane.setPrefSize(0.1, 0.1);
        panelsPane.getChildren().addAll(chatPanel);

        dragModeActiveProperty.bind(dragModeCheckbox.selectedProperty());

        this.gameController.gameBorder.setTop(panelsPane);

    }
    public boolean getComponentHasFocus() {
        return this.componentHasFocus;
    }
    public void setComponentHasFocus(boolean focused) {
        this.componentHasFocus = focused;
    }

    public void tick() {
        chatWindowController.tick();
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
    public Node makeDraggableByTitleRegion(final Node node) {
        final DragContext dragContext = new DragContext();
        final Group wrapGroup = new Group(node);
        wrapGroup.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            System.out.println("UI got a mouse press..");
            if (targetIsTitleRegion(event.getTarget())) {
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
            if (targetIsTitleRegion(event.getTarget())) {
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


    public static void configureBorder(final Region region) {
        region.setStyle("-fx-background-color: white;"
                + "-fx-border-color: black;"
                + "-fx-border-width: 1;"
                + "-fx-border-radius: 6;"
                + "-fx-padding: 6;");
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
