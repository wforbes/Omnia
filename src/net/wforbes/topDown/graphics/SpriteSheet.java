package net.wforbes.topDown.graphics;

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

        for(int i = 0; i < this.pixels.length; i++){
            this.pixels[i] = (pixels[i] & 0xff) / 64;
        }
    }

    public int getWidth(){
        return this.width;
    }
}
