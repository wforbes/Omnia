package net.wforbes.omnia.overworld.entity.animation;

import javafx.scene.image.Image;
import net.wforbes.omnia.overworld.entity.mob.Mob;

public class MovementAnimation {
    Mob mover;
    private Image[] frames;//holds all the frames for the animation
    private int currentFrame;// keeps track of the current frame
    private Image[] c_frames;
    private int c_currentFrame;
    private long c_startTime;
    private long c_animationDelay;
    private long combatDelay = 1000;
    private long lastCombatHit = 0;
    private boolean c_playedOnce;

    private int facingDir;
    private boolean isMoving;

    private long startTime;
    private long delay; //how long between each frame

    private boolean playedOnce;//useful for attacking animations where it only happens once

    public MovementAnimation(Mob mover){
        this.mover = mover;
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
    public void setCombatFrames(Image[] frames){
        this.c_frames = frames;
        c_currentFrame = 0;
        c_startTime = System.nanoTime();
        c_playedOnce = false;
    }

    public void setDelay(long d){ delay = d;}
    public void setCombatDelay(long d){ c_animationDelay = d;}
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

            if (this.lastCombatHit == 0 || (System.nanoTime() - this.lastCombatHit)/1000000 > this.combatDelay) {
                if (this.mover.getCombatController().isAttacking() && this.mover.getCombatController().shouldHit()) {
                    long elapsed = (System.nanoTime() - c_startTime) / 1000000;//how long its been since the last frame has come up
                    if (elapsed > c_animationDelay) {
                        c_currentFrame++;//move on to the next frame
                        c_startTime = System.nanoTime();//reset the start time!
                    }
                    if (c_currentFrame == c_frames.length) {//if true then we went out of bounds
                        c_currentFrame = 1;
                        c_playedOnce = true;
                        this.mover.getCombatController().handleMeleeTrigger();
                        this.lastCombatHit = System.nanoTime();
                    }
                }
            } else {
                currentFrame = 0;
            }
    }

    public int getFrame(){ return currentFrame; }

    //this will get the image that we need to draw
    public Image getImage(){
        return frames[currentFrame];
    }
    public Image getCombatImage(){
        return c_frames[c_currentFrame];
    }

    public boolean hasPlayedOnce(){ return playedOnce; }

}
