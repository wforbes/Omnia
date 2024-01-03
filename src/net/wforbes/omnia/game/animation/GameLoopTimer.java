package net.wforbes.omnia.game.animation;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;

public abstract class GameLoopTimer extends AnimationTimer {
    private double delta;
    private double goalFPS = 60D; //TODO: make fps adjustable
    double nsPerTick = 1000000000D / 60D;
    double nsPerRender = 1000000000D/60D; //how many nano seconds are in one render (on high FPS)
    private int FPS = 0;
    public int getFPS() { return this.FPS; }
    private int TPS = 0;
    public int getTPS() { return this.TPS; }

    private int upTime = 0;
    public int getUpTime() { return this.upTime; }
    long lastTimer = System.currentTimeMillis();
    long startTime = 0;

    boolean shouldRender;
    boolean useHighFPS;
    double rDelta = 0; //how many nano seconds have elapsed since last render (on high FPS)

    boolean isPaused;
    boolean isActive;

    boolean pauseScheduled;
    boolean playScheduled;
    boolean restartScheduled;

    long pauseStart;
    DoubleProperty animationDuration = new SimpleDoubleProperty(0L);

    final LongProperty lastUpdateTime = new SimpleLongProperty(0);
    public void handle(long now) {
        if (lastUpdateTime.isEqualTo(0L).get()) {
            startTime = now;
        }
        if (lastUpdateTime.get() > 0) {
            long nanosElapsed = now - lastUpdateTime.get();

            this.delta += nanosElapsed / nsPerTick;
            if(useHighFPS) rDelta += nanosElapsed / nsPerRender;
            lastUpdateTime.set(now);
            while (delta >= 1){
                TPS++;
                tick();
                delta -= 1;
                shouldRender = true;
            }
            //sleep the thread to regulate frame rate
            try{
                Thread.sleep(2);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(shouldRender) {
                if(useHighFPS) {
                    while (rDelta >= 1) {
                        FPS++;
                        render();
                        rDelta -= 1;
                    }
                } else {
                    FPS++;
                    render();
                }
            }

            if (System.currentTimeMillis() - lastTimer >= 1000){
                lastTimer += 1000;
                upTime = (int) Math.floor((now - startTime) / 1e9);
                //System.out.println(frames + " " + ticks);
                output(FPS, TPS, upTime);
                FPS = 0;
                TPS = 0;
            }

        }
        lastUpdateTime.set(now);

    }

    public abstract void render();
    public abstract void tick();
    public abstract void output(int FPS, int TPS, int upTime);

    public void pause() {
        if (!isPaused) {
            pauseScheduled = true;
        }
    }

    public void play() {
        if (isPaused) {
            playScheduled = true;
        }
    }

    @Override
    public void start() {
        super.start();
        isActive = true;
        restartScheduled = true;
    }

    @Override
    public void stop() {
        super.stop();
        pauseStart = 0;
        isPaused = false;
        isActive = false;
        pauseScheduled = false;
        playScheduled = false;
        animationDuration.set(0);
    }
}
