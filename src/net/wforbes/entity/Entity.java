package net.wforbes.entity;

import net.wforbes.graphics.Screen;
import net.wforbes.level.Level;

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
