package net.wforbes.omnia.topDown.entity;

import net.wforbes.omnia.topDown.level.Level;
import net.wforbes.omnia.topDown.graphics.Screen;

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
    public abstract String simpleChatResponse(String type);
    public abstract void tick();
    public abstract void render(Screen screen);
    public abstract String getName();
}
