package net.wforbes.omnia.gameFX.rendering;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Renderer {

    Canvas canvas;
    GraphicsContext context;
    private int shouldRenderCount = 0;
    private double delta = 0;
    long lastTime = System.nanoTime();
    long lastTimer = System.currentTimeMillis();
    private int fps = 0;
    boolean shouldRender = true;
    double nsPerTick = 1000000000D/60D; //how many nano seconds are in one tick
    int secondCount = 0;
    int lastFPS = 0;

    public Renderer(Canvas canvas) {
        this.canvas = canvas;
        this.context = canvas.getGraphicsContext2D();
    }

    public void render(int tickCount, float secondsSinceLastFrame) {
        context.save();
        Font font = new Font("Century Gothic", 20);
        context.setFont(font);


        // Set line width
        //context.setLineWidth(1.0);
        // Set fill color
        context.setFill(Color.BLACK);
        //System.out.println(tickCount);
        // Draw a Text
        context.fillText(("tickCount: " + tickCount), 5, 15);

        context.fillText("secondsSinceLastFrame: " + secondsSinceLastFrame, 5, 35);

        long n = System.nanoTime();
        this.delta += (n - lastTime) / nsPerTick;
        lastTime = n;

        if (this.delta >= 1) {
            shouldRender = true;
            shouldRenderCount++;
            this.delta -= 1;
        }
        if (shouldRender) {
            fps++;
            //render();
        }
        if(System.currentTimeMillis() - lastTimer >= 1000) {
            lastTimer += 1000;
            lastFPS = fps;
            context.fillText("FPS: " + (fps), 5, 215);
            fps = 0;
        } else {
            context.fillText("FPS: " + (lastFPS), 5, 215);
        }
        context.fillText("lastTimer: " + lastTimer, 5, 235);
        if(fps == 0) {
            secondCount++;
        }
        context.fillText("Exact second Count: " + secondCount + "." + (System.currentTimeMillis() - lastTimer), 5, 255);

        Date date = new Date();
        date.setTime(lastTimer);
        context.fillText("date.toString(): " + date.toString(), 5, 275);

        //String formattedDate = DateFormat.getDateInstance(DateFormat.FULL, Locale.ENGLISH).format(lastTimer);
        //context.fillText("formattedDate: " + formattedDate, 5, 295);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
        String formattedDate = dateFormat.format(lastTimer);
        context.fillText("formattedDate: " + formattedDate, 5, 295);




        shouldRender = false;
        // Draw a filled Text
        //context.fillText(("tickCount: " + tickCount), 50, 50);
        //Can use stroke text to embolden text
        //context.strokeText("tickCount: " + tickCount, 50, 50);


        /*
        if(background!=null){
            context.drawImage(background, 0, 0);
        }

        for (Entity entity : entities) {

            transformContext(entity);

            Point2D pos = entity.getDrawPosition();
            context.drawImage(
                    entity.getImage(),
                    pos.getX(),
                    pos.getY(),
                    entity.getWidth(),
                    entity.getHeight()
            );
        }
        */

        context.restore();
    }

    public void prepare(){
        context.setFill( new Color(1, 1, 1, 1.0) );
        context.fillRect(0,0, canvas.getWidth(),canvas.getHeight());

    }

    /*
    private void transformContext(Entity entity){
        Point2D centre = entity.getCenter();
        Rotate r = new Rotate(entity.getRotation(), centre.getX(), centre.getY());
        context.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }*/
}
