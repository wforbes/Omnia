package net.wforbes.gameState;

import net.wforbes.entity.Enemy;
import net.wforbes.entity.Player;
import net.wforbes.graphics.Colors;
import net.wforbes.graphics.Screen;
import net.wforbes.graphics.SpriteSheet;
import net.wforbes.gui.Font;
import net.wforbes.level.Level;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class TopDownState extends GameState{

    private BufferedImage image;
    private Graphics2D graphics2D;
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    private Screen screen;
    private int[] pixels;
    private int[] colors;
    public GameStateManager gsm;
    private Level level;
    private Player player;
    private Enemy enemy;


    public TopDownState(GameStateManager gsm)
    {
        this.gsm = gsm;
        this.init();
    }

    @Override
    public void init()
    {
        this.image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.pixels = ( (DataBufferInt) image.getRaster().getDataBuffer()).getData();
        this.colors = new int[6 * 6 * 6];
        this.initColors();
        //this.graphics2D = (Graphics2D) image.getGraphics();
        this.screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/sprite_sheet.png"));
        level = new Level("/test_level.png");

        player = new Player(level, 10, 10, "ghosty", this);
        level.addEntity(player);

        enemy = new Enemy(level, 32, 32, "skele");
        level.addEntity(enemy);
    }

    private void initColors()
    {
        int i = 0;
        for(int r = 0; r < 6; r++){
            for(int g = 0; g < 6; g++){
                for(int b = 0; b < 6; b++){
                    int rr = ( r * 255 / 5);
                    int gg = ( g * 255 / 5);
                    int bb = ( b * 255 / 5);
                    colors[i++] = rr << 16 | gg << 8 | bb;
                }
            }
        }
    }

    @Override
    public void tick() {
        level.tick();
    }

    @Override
    public void render(Graphics2D graphics2D) {
        //graphics2D = this.graphics2D;
        this.renderTiles();
        level.renderEntities(screen);
        this.renderVersionText();
        this.setPixelColorsFromScreen();
        graphics2D.drawImage(this.image, 0, 0, WIDTH, HEIGHT, null);
    }

    private void renderTiles()
    {
        int xOffset = player.x - (screen.getWidth()/2);
        int yOffset = player.y - (screen.getHeight()/2);
        level.renderTiles(screen, xOffset, yOffset);
    }

    private void renderVersionText()
    {
        String msg = "Omnia (v0.0.1) @wforbes87";
        Font.render(msg, screen, 0, 0, Colors.get(-1, -1, -1, 000), 1);
    }

    private void setPixelColorsFromScreen()
    {
        for(int y = 0; y < screen.getHeight(); y++){
            for(int x = 0; x < screen.getWidth(); x++){
                int colorCode = screen.getPixels()[x + y * screen.getWidth()];
                if (colorCode < 255) this.pixels[ x + y * screen.getWidth()] = this.colors[colorCode];
            }
        }
    }
}
