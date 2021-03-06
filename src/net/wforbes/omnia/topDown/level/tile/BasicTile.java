package net.wforbes.omnia.topDown.level.tile;

import net.wforbes.omnia.topDown.graphics.Screen;
import net.wforbes.omnia.topDown.level.Level;

public class BasicTile extends Tile {

    //protected int tileId;
    protected int tileColor;

    public BasicTile(int id, int x, int y, int tileColor, int levelColor)
    {
        super(id, false, false, levelColor);
        this.tileId = x + y * 32;
        this.tileColor = tileColor;
    }

    public void tick(){}

    public void render(Screen screen, Level level, int x, int y)
    {
        //System.out.println("Bt render() " + x + " " + y + " " + tileId + " " + tileColor);
        screen.render(x, y, tileId, tileColor, 0x00, 1);
    }
}
