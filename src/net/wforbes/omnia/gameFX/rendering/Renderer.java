package net.wforbes.omnia.gameFX.rendering;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Renderer {

    Canvas canvas;
    GraphicsContext gc;


    public Renderer(Canvas canvas) {
        this.canvas = canvas;
        canvas.getGraphicsContext2D().setImageSmoothing(false);
        this.gc = canvas.getGraphicsContext2D();
    }
    public GraphicsContext getContext() {
        return this.gc;
    }

    //public void render(int tickCount, float secondsSinceLastFrame) {
    public void render(GraphicsContext gc){
        gc.save();
        //Font font = new Font("Century Gothic", 20);
        //gc.setFont(font);


        // Set line width
        //gc.setLineWidth(1.0);
        // Set fill color
        //gc.setFill(Color.BLACK);
        //System.out.println(tickCount);
        // Draw a Text
        //gc.fillText(("tickCount: " + tickCount), 5, 15);

        //gc.fillText("secondsSinceLastFrame: " + secondsSinceLastFrame, 5, 35);

        /*
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
            gc.fillText("FPS: " + (fps), 5, 215);
            fps = 0;
        } else {
            gc.fillText("FPS: " + (lastFPS), 5, 215);
        }
        gc.fillText("lastTimer: " + lastTimer, 5, 235);
        if(fps == 0) {
            secondCount++;
        }*/
        //gc.fillText("Exact second Count: " + secondCount + "." + (System.currentTimeMillis() - lastTimer), 5, 255);

        //Date date = new Date();
        //date.setTime(lastTimer);
        //gc.fillText("date.toString(): " + date.toString(), 5, 275);

        //String formattedDate = DateFormat.getDateInstance(DateFormat.FULL, Locale.ENGLISH).format(lastTimer);
        //gc.fillText("formattedDate: " + formattedDate, 5, 295);
        //DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
        //String formattedDate = dateFormat.format(lastTimer);
        //gc.fillText("formattedDate: " + formattedDate, 5, 295);

        // Draw a filled Text
        //gc.fillText(("tickCount: " + tickCount), 50, 50);
        //Can use stroke text to embolden text
        //gc.strokeText("tickCount: " + tickCount, 50, 50);

        /*
        if(background!=null){
            gc.drawImage(background, 0, 0);
        }

        for (Entity entity : entities) {

            transformContext(entity);

            Point2D pos = entity.getDrawPosition();
            gc.drawImage(
                    entity.getImage(),
                    pos.getX(),
                    pos.getY(),
                    entity.getWidth(),
                    entity.getHeight()
            );
        }
        */

        gc.restore();
    }

    public void prepare(){
        gc.setFill( new Color(1, 1, 1, 1.0) );
        gc.fillRect(0,0, canvas.getWidth(),canvas.getHeight());

    }

    /*
    private void transformContext(Entity entity){
        Point2D centre = entity.getCenter();
        Rotate r = new Rotate(entity.getRotation(), centre.getX(), centre.getY());
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }*/
}
