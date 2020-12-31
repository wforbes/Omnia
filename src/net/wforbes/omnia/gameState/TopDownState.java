package net.wforbes.omnia.gameState;

import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.game.Game;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.topDown.entity.Enemy;
import net.wforbes.omnia.topDown.entity.Player;
import net.wforbes.omnia.topDown.graphics.Colors;
import net.wforbes.omnia.topDown.graphics.Screen;
import net.wforbes.omnia.topDown.graphics.SpriteSheet;
import net.wforbes.omnia.topDown.gui.Font;
import net.wforbes.omnia.topDown.gui.GUI;
import net.wforbes.omnia.topDown.gui.PauseMenu;
import net.wforbes.omnia.topDown.level.Level;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class TopDownState extends GameState{

    private BufferedImage image;
    private FXGraphics2D fxGraphics2D;
    private Screen screen;
    private int[] pixels;
    private int[] colors;
    public GameStateManager gsm;
    public int tickCount = 0;
    private Level level;
    private Player player;
    private Enemy enemy;
    private GUI gui;
    private boolean isPaused;
    private PauseMenu pauseMenu;
    public int lastUnpauseTick = 0;

    public TopDownState(GameStateManager gsm)
    {
        this.gsm = gsm;
        this.init();
    }

    public TopDownState(GameStateManager gsm, String type) {
        if (!type.equals("fx"))
            return;

        this.gsm = gsm;
        this.fxInit();
    }

    public GameStateManager getGsm() {
        return gsm;
    }

    public void fxInit() {
        this.isPaused = false;
        this.fxGraphics2D = null;
        this.image = new BufferedImage(OmniaFX.getScaledWidth(), OmniaFX.getScaledHeight(), BufferedImage.TYPE_INT_RGB);
        this.pixels = ( (DataBufferInt) image.getRaster().getDataBuffer()).getData();
        this.colors = new int[6 * 6 * 6];
        this.initColors();
        this.screen = new Screen(OmniaFX.getScaledWidth(), OmniaFX.getScaledHeight(), new SpriteSheet("/sprite_sheet.png"));
        this.level = new Level("/test_level.png");
        this.player = new Player(level, 10, 10, "ghosty", this);

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

    @Override
    public void render(GraphicsContext gc) {
        if(this.fxGraphics2D == null) {
            this.fxGraphics2D = new FXGraphics2D(gc);
        }
        this.renderTiles();
        this.level.renderEntities(screen);
        this.gui.render(screen);
        this.renderVersionText();
        /*
        if (pauseMenu.isVisible()) {
            pauseMenu.render(graphics2D);
        }*/
        this.setPixelColorsFromScreen();
        fxGraphics2D.drawImage(this.image, 0, 0, OmniaFX.getScaledWidth(), OmniaFX.getScaledHeight(), null);
    }

    @Override
    public void update() {
        if(isPaused) {
            if (!pauseMenu.isVisible()) {
                this.pauseMenu.show();
            }
            pauseMenu.tick();
            return;
        }
        level.tick();
        tickCount++;
    }

    @Override
    public void init()
    {
        this.isPaused = false;
        this.image = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.pixels = ( (DataBufferInt) image.getRaster().getDataBuffer()).getData();
        this.colors = new int[6 * 6 * 6];
        this.initColors();

        this.screen = new Screen(Game.WIDTH, Game.HEIGHT, new SpriteSheet("/sprite_sheet.png"));
        level = new Level("/test_level.png");

        player = new Player(level, 10, 10, "ghosty", this);
        level.addEntity(player);

        enemy = new Enemy(level, 32, 32, "skele");
        level.addEntity(enemy);

        gui = new GUI(screen);
        pauseMenu = new PauseMenu(this);
    }

    private void initColors()
    {
        int i = 0;
        for(int r = 0; r < 6; r++){
            for(int g = 0; g < 6; g++){
                for(int b = 0; b < 6; b++){
                    int rr = (r * 255 / 5);
                    int gg = (g * 255 / 5);
                    int bb = (b * 255 / 5);
                    colors[i++] = rr << 16 | gg << 8 | bb;
                }
            }
        }
    }

    @Override
    public void tick() {
        if(isPaused) {
            if (!pauseMenu.isVisible()) {
                this.pauseMenu.show();
            }
            pauseMenu.tick();
            return;
        }
        level.tick();
        tickCount++;
    }

    @Override
    public void render(Graphics2D graphics2D) {
        this.renderTiles();
        this.level.renderEntities(screen);
        this.gui.render(screen);
        this.renderVersionText();
        if (pauseMenu.isVisible()) {
            pauseMenu.render(graphics2D);
        }
        this.setPixelColorsFromScreen();
        graphics2D.drawImage(this.image, 0, 0, Game.WIDTH, Game.HEIGHT, null);

    }

    public void pause() {
        this.isPaused = true;
        this.pauseMenu.show();
    }
    public void unPause() {
        this.isPaused = false;
        this.lastUnpauseTick = this.tickCount;
        this.pauseMenu.hide();
    }
    public void openChatInput() {
        this.gui.openChatInput();
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



    public void reset() {}
}
