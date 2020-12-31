package net.wforbes.omnia.topDown.graphics;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {

    private String path;
    private int width;
    private int height;
    public int[] pixels;

    public SpriteSheet(String path)
    {
        BufferedImage img = null;
        try {
            img = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
        }catch(IOException ex){
            ex.printStackTrace();
        }

        if(img == null)
            return;

        this.path = path;
        this.width = img.getWidth();
        this.height = img.getHeight();

        this.pixels = img.getRGB(0, 0, width, height, null, 0, width);

        for(int i = 0; i < this.pixels.length; i++) {
            this.pixels[i] = (pixels[i] & 0xff) / 64;
        }
    }

    public SpriteSheet(String path, String type)
    {
        if(!type.equals("fx"))
            return;

        Image img = null;
        try {
            img = new Image(getClass().getResourceAsStream(path));
        } catch(Exception e) {
            e.printStackTrace();
        }
        if(img == null)
            return;

        this.path = path;
        this.width = (int)img.getWidth();
        this.height = (int)img.getHeight();
        PixelReader pixelReader = img.getPixelReader();
        int[] pixels = new int[width * height];
        pixelReader.getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixels, 0, width);

        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = (pixels[i] & 0xff) / 64;
        }
    }

    public int getWidth(){
        return this.width;
    }
}
