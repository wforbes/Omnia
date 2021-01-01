package net.wforbes.omnia.menu;

public abstract class Menu {

    protected int waitTicks = 20;
    protected int lastPressTick;
    protected int tickCount;
    protected int currentChoice;

    boolean keyInputReady() {
        return tickCount - lastPressTick > waitTicks || lastPressTick == 0;
    }

    abstract void checkKeyInput();
    abstract void select();
}
