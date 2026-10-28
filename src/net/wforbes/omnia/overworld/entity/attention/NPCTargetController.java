package net.wforbes.omnia.overworld.entity.attention;

import net.wforbes.omnia.overworld.entity.Entity;

public class NPCTargetController extends TargetController {
    protected Entity owner;
    protected Entity target;
    protected int attentionSpan;
    private boolean isFocusing;
    private int startFocusTime;
    private int secondsUntilAttentionFree;

    public NPCTargetController(Entity owner) {
        super(owner);
    }

    /**
     * @param  int  the number of seconds to set as the default attention span
     */
    public void setAttentionSpan(int span) {
        this.attentionSpan = span;
    }

    public int getSecondsUntilAttentionFree() {
        return this.secondsUntilAttentionFree;
    }

    public void update() {
        this.updateFocus();
    }
    private void updateFocus() {
        if (!isFocusing) return;
        int currentUpTime = this.owner.gameState.gsm.gameController.timer.getUpTime();
        this.secondsUntilAttentionFree = (attentionSpan - (currentUpTime - startFocusTime));
        if ((currentUpTime - startFocusTime) >= attentionSpan) {
            System.out.println(owner.getName() + " lost focus on " + target.getName());
            this.loseFocus();
        }
    }

    /**
     * @param  Entity  the target Entity for AttentionController owner to focus on
     */
    public void startFocusing(Entity target) {
        this.setTarget(target);
        this.setIsFocusing(true);
        this.startFocusTime = this.owner.gameState.gsm.gameController.timer.getUpTime();
    }

    public void setIsFocusing(boolean isFocusing) {
        this.isFocusing = isFocusing;
    }

    public boolean getIsFocusing() {
        return this.isFocusing;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }
    
    public void loseFocus() {
        if (!isFocusing) return;
        this.setIsFocusing(false);
        if (this.startFocusTime > 0) {
            this.setStartFocusTime(0);
        }
        if (this.hasTarget()) {
            this.setTarget(null);
        }
    }
    public boolean hasTarget() {
        return this.target != null;
    }
    public void setStartFocusTime(int sec) {
        this.startFocusTime = sec;
    }

    public void teardown() {
        this.owner = null;
    }
}
