package net.wforbes.omnia.gameState;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import net.wforbes.omnia.game.Game;
import net.wforbes.omnia.game.controls.keyboard.KeyboardController;
import net.wforbes.omnia.menu.PauseMenu;
import net.wforbes.omnia.topDown.controls.TopDownKeyboardController;
import net.wforbes.omnia.topDown.entity.*;
import net.wforbes.omnia.topDown.graphics.Colors;
import net.wforbes.omnia.topDown.graphics.Screen;
import net.wforbes.omnia.topDown.graphics.SpriteSheet;
import net.wforbes.omnia.topDown.gui.Font;
import net.wforbes.omnia.topDown.gui.GUIController;
import net.wforbes.omnia.topDown.level.Level;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class TopDownState extends GameState{
    public GameStateManager gsm;
    private KeyboardController keyboardController;

    private BufferedImage image;
    private FXGraphics2D fxGraphics2D;
    private Screen screen;
    private int[] pixels;
    private int[] colors;

    public int tickCount;
    private Level level;
    private Player player;
    private NPC npc;
    private Enemy enemy;
    public GUIController gui;
    private boolean isPaused;
    private PauseMenu pauseMenu;
    public int lastUnpauseTick = 0;

    private boolean debugging;

    public TopDownState(GameStateManager gsm)
    {
        this.gsm = gsm;
        this.keyboardController = new TopDownKeyboardController(this);
    }

    public GameStateManager getGsm() {
        return gsm;
    }

    public Level getLevel() { return level; }

    public Player getPlayer() { return player; }

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
        this.renderVersionText();
        this.setPixelColorsFromScreen();
        fxGraphics2D.drawImage(this.image, 0, 0, Game.getScaledWidth(), Game.getScaledHeight(), null);
        if (pauseMenu.isVisible()){
            pauseMenu.render(gc);
        }
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
        gui.tick();
        tickCount++;
    }

    @Override
    public KeyboardController getKeyboard() {
        return keyboardController;
    }

    @Override
    public void handleKeyPressed(KeyEvent event) {

    }

    @Override
    public void handleKeyReleased(KeyEvent event) {

    }

    @Override
    public void handleCanvasClick(MouseEvent event) {

    }

    @Override
    public void handleCanvasMouseMove(MouseEvent event) {

    }

    public int getTickCount() {
        return this.tickCount;
    }

    public boolean isDebugging() {
        return debugging;
    }

    @Override
    public void init()
    {
        this.image = new BufferedImage(Game.getWidth(), Game.getHeight(), BufferedImage.TYPE_INT_RGB);
        this.screen = new Screen(Game.getWidth(), Game.getHeight(), new SpriteSheet("/sprite_sheet.png"));
        //this.pauseMenu = new PauseMenu(this, "fx");
        this.pauseMenu = new PauseMenu(gsm);

        this.debugging = false;

        this.tickCount = 0;
        this.isPaused = false;
        this.pixels = ( (DataBufferInt) image.getRaster().getDataBuffer()).getData();
        this.colors = new int[6 * 6 * 6];
        this.initColors();
        level = new Level("/test_level2.png");

        Point2D playerStartPos = new Point2D(704, 80);
        player = new Player(level, "blueboi", playerStartPos,this);
        level.addEntity(player);

        //688, 48
        Point2D npcStartPos = new Point2D(688, 48);
        npc = new DocNPC(level,"doc", npcStartPos, this);
        level.addEntity(npc);

        npcStartPos = new Point2D(720, 32);
        BroNPC broNPC = new BroNPC(level, "bro", npcStartPos, this);
        level.addEntity(broNPC);

        //enemy = new Enemy(level, 32, 32, "doc");

        this.gui = new GUIController(this);
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

    public void pause() {
        this.isPaused = true;
        this.pauseMenu.show();
    }
    public void unPause() {
        this.isPaused = false;
        this.lastUnpauseTick = this.tickCount;
        this.pauseMenu.hide();
    }

    public boolean isPaused() {
        return this.isPaused;
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
    public void exit() {}
}
