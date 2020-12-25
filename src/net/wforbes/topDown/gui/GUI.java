package net.wforbes.topDown.gui;

import net.wforbes.game.Game;
import net.wforbes.topDown.entity.Player;
import net.wforbes.topDown.graphics.Colors;
import net.wforbes.topDown.graphics.Screen;
import net.wforbes.topDown.level.Level;

public class GUI {

    private Player player;
    private Level level;
    private Screen screen;

    public GUI (Player player, Level level, Screen screen) {
        this.player = player;
        this.level = level;
        this.screen = screen;
    }

    public void tick() {

    }

    public void render(Screen screen) {
        //System.out.println(screen.xOffset + " " + screen.yOffset);
        //System.out.println(player.yOffset);
        //chatbox
        int color = Colors.get(-1, 100, 555, 543);
        int xOffsetTiles = 2;
        int tileSize = 8;
        int boxXOffset = tileSize * xOffsetTiles;
        int yOffsetTiles = 10;
        int boxYOffset = tileSize * yOffsetTiles;
        int boxHeight = 10;
        int boxWidth = 20;
        int topCornerTile = (0 + 22 * 32);
        int topEdgeTile = (1 + 22 * 32);
        int sideEdgeTile = (3 + 22 * 32); //TODO: remove right corner sprite tile
        int centerTile = (4 + 22 * 32);
        int bottomCornerTile = (5 + 22 * 32);
        int bottomEdgeTile = (6 + 22 * 32);

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
                sideEdgeTile, Colors.get(-1, 100, 555, 543), 1, 1
            );
        }

        //bottom left corner
        screen.render(
            screen.xOffset + boxXOffset,
            screen.yOffset + Game.HEIGHT - boxYOffset + (tileSize * (boxHeight - 1)),
            bottomCornerTile, Colors.get(-1, 100, 555, 543), 0, 1
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

        screen.render(screen.xOffset, screen.yOffset + Game.HEIGHT-1, (0+23*32), Colors.get(-1, 100, 555, 543), 0, 1);
    }
}
