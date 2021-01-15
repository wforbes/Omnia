package net.wforbes.omnia.overworld.entity.animation;

import javafx.scene.image.Image;

public class MovementAnimation {

    private Image[] frames;//holds all the frames for the animation
    private int currentFrame;// keeps track of the current frame

    private int facingDir;
    private boolean isMoving;

    private long startTime; //?? research
    private long delay; //how long between each frame

    private boolean playedOnce;//useful for attacking animations where it only happens once

    public MovementAnimation(){
        playedOnce = false;
    }

    public void setFacingDir(int dir) {
        this.facingDir = dir;
    }
    public int getFacingDir() {
        return this.facingDir;
    }
    public boolean isMoving() {
        return this.isMoving;
    }
    public void setIsMoving(boolean m) {
        this.isMoving = m;
    }

    public void setFrames(Image[] frames){
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
        playedOnce = false;
    }

    public void setDelay(long d){ delay = d;}
    public void setFrame(int i){ currentFrame = i;}

    public void update(){
        if(this.isMoving) {
            long elapsed = (System.nanoTime() - startTime) / 1000000;//how long its been since the last frame has come up
            if(elapsed > delay){
                currentFrame ++;//move on to the next frame
                startTime = System.nanoTime();//reset the start time!
            }
            if(currentFrame == frames.length){//if true then we went out of bounds
                currentFrame = 1;
                playedOnce = true;
            }
        } else {
            currentFrame = 0;
        }
    }

    public int getFrame(){ return currentFrame; }

    public Image getImage(){ return frames[currentFrame]; }//this will get the image that we need to draw

    public boolean hasPlayedOnce(){ return playedOnce; }

}
