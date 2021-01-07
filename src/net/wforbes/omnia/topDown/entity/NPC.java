package net.wforbes.omnia.topDown.entity;

import javafx.geometry.Point2D;
import net.wforbes.omnia.topDown.entity.dialog.DialogController;
import net.wforbes.omnia.topDown.graphics.Colors;
import net.wforbes.omnia.topDown.graphics.Screen;
import net.wforbes.omnia.topDown.level.Level;

public class NPC extends Mob {
    private final DialogController dialogController;

    /*
    public NPC(Level level, String name, int x, int y) {
        super(level, name, x, y, 1);
        this.canSwim = true;
        this.dialogController = new DialogController();
    }*/

    public NPC(Level level, String name, Point2D startPos) {
        super(level, name, startPos, 1);
        this.canSwim = true;
        this.dialogController = new DialogController();
        this.setSpriteLoc(new Point2D(0, 17));
        this.setSpriteColor(Colors.get(-1, 111, 222, 555));
        this.setNameColor(Colors.get(-1, -1, -1, 034));
    }

    @Override
    public String simpleChatResponse(String type) {
        return null;
    }

    @Override
    public void tick() {
        this.movementController.tick();
        super.tick();
    }

    @Override
    public void render(Screen screen) {
        super.render(screen);
    }
}
