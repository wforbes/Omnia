package net.wforbes.omnia.menu.background;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import net.wforbes.omnia.gameFX.OmniaFX;

public class MenuBackground {
    private Image image;
    private double width;
    private double height;
    private double x;
    private double y;
    private double dx;
    private double dy;

    public MenuBackground(String path) throws Exception{
        this.image = new Image(path);
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public void setVector(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }

    public void update() {
        //if x is less than the width of the screen,
        //  reset x value to continue to continue animation
        if(x <= (double)OmniaFX.getScaledWidth()*Math.signum(dx)) {
            x = dx;
        } else {
            x += dx;
        }
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y,  OmniaFX.getScaledWidth(), OmniaFX.getScaledHeight());

        if (x < 0)
            gc.drawImage(image, x + OmniaFX.getScaledWidth(), y, OmniaFX.getScaledWidth(), OmniaFX.getScaledHeight());

        if (x > 0)
            gc.drawImage(image, x - OmniaFX.getScaledWidth(), y, OmniaFX.getScaledWidth(), OmniaFX.getScaledHeight());
    }
}
