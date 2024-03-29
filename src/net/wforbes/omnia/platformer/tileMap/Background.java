package net.wforbes.omnia.platformer.tileMap;

import net.wforbes.omnia.game.Game;
import net.wforbes.omnia.gameState.GameState;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Background {
    private BufferedImage bufferedImage;
    private FXGraphics2D fxg;

    private double width;
    private double height;
    private double x;
    private double y;
    private double dx;
    private double dy;

    //moveScale variable
    //the scale by which the background moves in relativity to the foreground
    //		visuals. This decimal number between 0 and 1 can be multiplied
    //    to the movement rate of the foreground and the result multipied to
    //		the movement rate of the background to produce the parallax
    //		effect
    private double moveScale;

    public Background(GameState state, String path, double ms){
        moveScale = ms;
        try{
            bufferedImage = ImageIO.read(getClass().getResourceAsStream(path));
            this.width = bufferedImage.getWidth();
            this.height = bufferedImage.getHeight();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setPosition(double x, double y){
        this.x = (x * moveScale);
        this.y = (y * moveScale);
        fixPosition();
    }


    private void fixPosition(){
        while(x <= -width) x += width;
        while(x >= +width) x += width;
        while(y <= -height) y += height;
        while(y >= +height) y -= height;
    }


    //can use dx, dy to automatically scroll the background
    public void setVector(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }
    //if we are automatically scrolling background, we have to update x and y
    public void update(){
        x += dx;
        y += dy;
    }

    /*
    public void render(GraphicsContext gc) {
        if(fxg == null)
            fxg = new FXGraphics2D(gc);

        fxg.drawImage(bufferedImage, (int)x, (int)y, OmniaFX.getScaledWidth(), OmniaFX.getScaledHeight(), null);
        if (x < 0)
            fxg.drawImage(bufferedImage, (int)x + OmniaFX.getScaledWidth(), (int)y, null);

        if (x > 0)
            fxg.drawImage(bufferedImage, (int)x - OmniaFX.getScaledWidth(), (int)y, null);
    }*/

    public void draw(FXGraphics2D fxg) {
        fxg.drawImage(bufferedImage, (int)x, (int)y, Game.getScaledWidth(), Game.getScaledHeight(),null);
        if(x < 0)
            fxg.drawImage(bufferedImage, (int)x + Game.getScaledWidth(), (int) y, Game.getScaledWidth(), Game.getScaledHeight(), null);

        if(x > 0)
            fxg.drawImage(bufferedImage, (int)x - Game.getScaledWidth(), (int)y, Game.getScaledWidth(), Game.getScaledHeight(), null);
    }

    public void draw(Graphics2D g){
        //draw the background image at position x and y
        g.drawImage(bufferedImage, (int)x, (int)y, null);
        //Since this background scrolls horizontally, we have to make sure its
        //		always filling the screen. For instance if the background scrolls to
        //		the left off of the screen, we'd want to render more background to
        //		the right of the screen and vise versa.
        if(x < 0){//draw the image at the far right side of the screen
            g.drawImage(bufferedImage, (int)x + Game.getWidth(), (int) y, null);
        }
        if(x > 0){//I think this should be if (x > GamePanel.WIDTH) but whatever
            //		we'll see.
            g.drawImage(bufferedImage, (int)x - Game.getWidth(), (int)y, null);
            //STOPPED at 5:23AM at 30:06 of:
            //			http://www.youtube.com/watch?v=9dzhgsVaiSo
        }
    }
}
