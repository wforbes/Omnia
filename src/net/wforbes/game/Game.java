package net.wforbes.game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable{

    public static final long serialVersionUID = 1L;

    public static JFrame frame;
    public static final String GAME_FRAME_NAME = "Omnia <0.0.1>";//Av = Alpha Version
    public static final int WIDTH = 160;
    public static final int HEIGHT = 160;
    public static final int SCALE = 3; //To easily change the size of the window by increments
    public static final Dimension DIMENSIONS = new Dimension(WIDTH*SCALE, HEIGHT*SCALE);

    private Thread thread;
    public boolean running = false;
    public int tickCount = 0;

    private BufferedImage image =
            new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ( (DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private int[] colors = new int[6 * 6 * 6];

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
        frame.pack();
        frame.setResizable(false);
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
        init();
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

        initColors();
        /*
        screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/sprite_sheet.png"));
        input = new InputHandler(this);
        level = new Level("/levels/water_test_level.png");
        player = new Player(level, 32, 32, input, "");
        monster1 = new Monster(level, 32, 32);
        level.addEntity(player);
        level.addEntity(monster1);
        */
    }

    private void initColors(){
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

    public void tick()
    {
        tickCount++;
        //level.tick();
    }

    public void render()
    {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null)
        {
            createBufferStrategy(3); //3 means triple buffering -
            //                         reduces image tearing, reduces cross image pixelation
            requestFocus();
            return;
        }
        /*
        int xOffset = player.x - (screen.width/2);
        int yOffset = player.y - (screen.height/2);

        level.renderTiles(screen, xOffset, yOffset);
        level.renderEntities(screen);

        //version ground print
        String msg = "Hello";
        Font.render(msg, screen, 0, 0, Colors.get(-1, -1, -1, 000), 1);

        for(int y = 0; y < screen.height; y++){
            for(int x = 0; x < screen.width; x++){
                int colorCode = screen.pixels[x + y * screen.width];
                if (colorCode < 255) pixels[ x + y * WIDTH] = colors[colorCode];
            }
        }
        */

        Graphics g = bs.getDrawGraphics();
        g.fillRect(0, 0, getWidth(), getHeight() );
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }


    public static void main(String[] args)
    {
        new Game().start();
    }
}
