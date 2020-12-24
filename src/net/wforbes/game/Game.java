package net.wforbes.game;

import net.wforbes.gameState.GameStateManager;
import net.wforbes.topDown.entity.Enemy;
import net.wforbes.topDown.entity.Player;
import net.wforbes.topDown.graphics.Screen;
import net.wforbes.topDown.level.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

    public static final long serialVersionUID = 1L;

    public static JFrame frame;
    public static final String GAME_FRAME_NAME = "Omnia <0.0.1>";
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 4; //To easily change the size of the window by increments
    public static final Dimension DIMENSIONS = new Dimension(WIDTH*SCALE, HEIGHT*SCALE);

    private Thread thread;
    public boolean running = false;
    public int tickCount = 0;

    private BufferStrategy bufferStrategy;
    private Graphics graphics;
    private Graphics2D graphics2D;
    private BufferedImage image;
    private int[] pixels;
    private int[] colors;

    private GameStateManager gsm;
    private Screen screen;
    private Level level;
    private Player player;
    private Enemy enemy;

    public static void main(String[] args)
    {
        new Game().start();
    }

    private Game()
    {
        setMinimumSize(DIMENSIONS);
        setMaximumSize(DIMENSIONS);
        setPreferredSize(DIMENSIONS);
        setupFrame();
    }

    private void setupFrame()
    {
        frame = new JFrame(GAME_FRAME_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private synchronized void start()
    {
        running = true;
        thread = new Thread(this, GAME_FRAME_NAME + "_main");
        thread.start();
    }

    public synchronized void stop()
    {
        running = false;
        try{
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D/60D; //how many nano seconds are in one tick
        double delta = 0;//how many nano seconds have elapsed
        int ticks = 0;
        int frames = 0;
        long lastTimer = System.currentTimeMillis(); //a time to reset the data
        boolean shouldRender = true;
        this.init();
        while(running){
            long now = System.nanoTime();//the current time to check against lastTime
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            //once delta has gone over or is equal to 1,
            while(delta >= 1 ){
                ticks++;
                tick();
                delta -= 1;
                shouldRender = true;
            }

            //sleep the thread to regulate frame rate
            try{
                Thread.sleep(2);
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            //if it should render the game, update the frame count and call render
            if(shouldRender)
            {
                frames++;
                render();
            }

            if(System.currentTimeMillis() - lastTimer >= 1000 )
            {
                lastTimer += 1000;//update another second
                frame.setTitle("Omnia <0.0.1> - " + ticks + " tps, " + frames + " fps" );
                frames = 0;
                ticks = 0;
            }
            shouldRender = false;
        }//end while(running)
    }

    public void init()
    {
        this.image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        //this.pixels = ( (DataBufferInt) image.getRaster().getDataBuffer()).getData();
        //this.colors = new int[6 * 6 * 6];
        //this.initColors();
        this.graphics2D = (Graphics2D) image.getGraphics();
        //screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/sprite_sheet.png"));
        gsm = new GameStateManager(this);
        //input = new InputHandler(this, gsm);

        //TODO: Move these to TopDownState
        /*
        level = new Level("/test_level.png");

        player = new Player(level, 10, 10, input, "ghosty");
        level.addEntity(player);

        enemy = new Enemy(level, 32, 32, "skele");
        level.addEntity(enemy);
        */
    }

    public void tick()
    {
        tickCount++;
        this.gsm.tick();
        //level.tick();
    }

    public void render()
    {
        if(!this.setBufferStrategy()) {
            return;
        }

        this.gsm.render(graphics2D);
        //TODO: Move to TopDownState
        /*
        this.renderTiles();
        level.renderEntities(screen);
        this.renderVersionText();
         */

        //this.setPixelColorsFromScreen();
        this.drawToScreen();
    }

    private boolean setBufferStrategy()
    {
        this.bufferStrategy = getBufferStrategy();
        if(this.bufferStrategy == null)
        {
            createBufferStrategy(3); //3 means triple buffering -
            //                         reduces image tearing, reduces cross image pixelation
            requestFocus();
            return false;
        }
        return true;
    }

    /*
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
                //if (colorCode < 255) pixels[ x + y * WIDTH] = colors[colorCode];
            }
        }
    }
    */
    private void drawToScreen()
    {
        this.graphics = this.bufferStrategy.getDrawGraphics();
        this.graphics.fillRect(0, 0, getWidth(), getHeight() );
        this.graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        this.graphics.dispose();
        this.bufferStrategy.show();
    }

}
