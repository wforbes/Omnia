package net.wforbes.omnia.overworld.entity;

import javafx.scene.image.Image;

public class MovementAnimation {

    private Image[] frames;//holds all the frames for the animation
    private int currentFrame;// keeps track of the current frame

    private long startTime; //?? research
    private long delay; //how long between each frame

    private boolean playedOnce;//useful for attacking animations where it only happens once

    public MovementAnimation(){
        playedOnce = false;
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
        if(delay == -1) return;// there is no animation
        long elapsed = (System.nanoTime() - startTime) / 1000000;//how long its been since the last frame has come up
        if(elapsed > delay){
            currentFrame ++;//move on to the next frame
            startTime = System.nanoTime();//reset the start time!
        }
        if(currentFrame == frames.length){//if true then we went out of bounds
            currentFrame = 0;
            playedOnce = true;
        }
    }

    public int getFrame(){ return currentFrame; }

    public Image getImage(){ return frames[currentFrame]; }//this will get the image that we need to draw

    public boolean hasPlayedOnce(){ return playedOnce; }

}
