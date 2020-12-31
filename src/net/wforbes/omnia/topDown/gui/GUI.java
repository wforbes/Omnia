package net.wforbes.omnia.topDown.gui;

import net.wforbes.omnia.game.Game;
import net.wforbes.omnia.topDown.graphics.Colors;
import net.wforbes.omnia.topDown.level.Level;
import net.wforbes.omnia.topDown.entity.Player;
import net.wforbes.omnia.topDown.graphics.Screen;

public class GUI {

    private Player player;
    private Level level;
    private Screen screen;

    //chatbox
    private int color = Colors.get(-1, 100, 555, 543);
    private int xOffsetTiles = 2;
    private int tileSize = 8;
    private int boxXOffset = tileSize * xOffsetTiles;
    private int yOffsetTiles = 10;
    private int boxYOffset = tileSize * yOffsetTiles;
    private int boxHeight = 10;
    private int boxWidth = 20;
    private int topCornerTile = (0 + 22 * 32);
    private int topEdgeTile = (1 + 22 * 32);
    private int sideEdgeTile = (3 + 22 * 32); //TODO: remove right corner sprite tile
    private int centerTile = (4 + 22 * 32);
    private int bottomCornerTile = (5 + 22 * 32);
    private int bottomEdgeTile = (6 + 22 * 32);

    private boolean chatInputIsOpen = false;

    public GUI (Screen screen) {
        this.screen = screen;
    }

    public void tick() {

    }

    public void openChatInput() {
        this.chatInputIsOpen = true;
    }

    public void render(Screen screen) {
        //System.out.println(screen.xOffset + " " + screen.yOffset);
        //System.out.println(player.yOffset);

        renderChatBox(screen);
        if (chatInputIsOpen) {
            renderChatInput(screen);
        }

        //screen.render(screen.xOffset, screen.yOffset + Game.HEIGHT-1, (0+23*32), Colors.get(-1, 100, 555, 543), 0, 1);
    }

    private void renderChatInput(Screen screen) {

        //screen.render();
    }

    private void renderChatBox(Screen screen) {
        //top left corner
        screen.render(
                screen.xOffset + boxXOffset,
                screen.yOffset + Game.HEIGHT - boxYOffset,
                topCornerTile, color, 0, 1
        );

        //top edge
        for (int i = 0; i < boxWidth - 2; i++) {
            screen.render(
                    screen.xOffset + boxXOffset + tileSize + (tileSize * i),
                    screen.yOffset + Game.HEIGHT - boxYOffset,
                    topEdgeTile, color, 0, 1
            );
        }

        //top right corner
        screen.render(
                screen.xOffset + boxXOffset + ((boxWidth - 1) * tileSize),
                screen.yOffset + Game.HEIGHT - boxYOffset,
                topCornerTile, color, 1, 1
        );

        //left edge
        for (int i = 0; i < boxHeight - 2; i++) {
            screen.render(
                    screen.xOffset + boxXOffset,
                    screen.yOffset + Game.HEIGHT - boxYOffset + tileSize + (tileSize * i),
                    sideEdgeTile, Colors.get(-1, 100, 555, 543), 0, 1);
        }

        //center
        for (int i = 0; i < boxHeight - 2; i++) {
            for (int j = 0; j < boxWidth - 2; j++) {
                screen.render(
                        screen.xOffset + boxXOffset + tileSize + (tileSize * j),
                        screen.yOffset + Game.HEIGHT - boxYOffset + tileSize + (tileSize * i),
                        centerTile, color, 0, 1
                );
            }
        }

        //right edge
        for (int i = 0; i < boxHeight - 2; i++) {
            screen.render(
                    screen.xOffset + boxXOffset + tileSize * (boxWidth - 1),
                    screen.yOffset + Game.HEIGHT - boxYOffset + tileSize + (tileSize * i),
                    sideEdgeTile, color, 1, 1
            );
        }

        //bottom left corner
        screen.render(
                screen.xOffset + boxXOffset,
                screen.yOffset + Game.HEIGHT - boxYOffset + (tileSize * (boxHeight - 1)),
                bottomCornerTile, color, 0, 1
        );

        //bottom edge
        for (int i = 0; i < boxWidth - 2; i++) {
            screen.render(
                    screen.xOffset + boxXOffset + tileSize + (tileSize * i),
                    screen.yOffset + Game.HEIGHT - boxYOffset + (tileSize * (boxHeight -1)),
                    bottomEdgeTile, color, 0, 1
            );
        }

        //bottom right corner
        screen.render(
                screen.xOffset + boxXOffset + ((boxWidth - 1) * tileSize),
                screen.yOffset + Game.HEIGHT - boxYOffset + (tileSize * (boxHeight - 1)),
                bottomCornerTile, Colors.get(-1, 100, 555, 543), 1, 1
        );
    }
}
