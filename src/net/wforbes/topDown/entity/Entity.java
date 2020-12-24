package net.wforbes.topDown.entity;

import net.wforbes.topDown.graphics.Screen;
import net.wforbes.topDown.level.Level;

public abstract class Entity {

    public int x, y;
    public Level level;

    public Entity(Level level)
    {
        init(level);
    }

    public final void init(Level level)
    {
        this.level = level;
    }

    public abstract void tick();
    public abstract void render(Screen screen);
    public abstract String getName();
}
