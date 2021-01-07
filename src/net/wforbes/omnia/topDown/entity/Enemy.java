package net.wforbes.omnia.topDown.entity;

import javafx.geometry.Point2D;
import net.wforbes.omnia.topDown.graphics.Colors;
import net.wforbes.omnia.topDown.graphics.Screen;
import net.wforbes.omnia.topDown.gui.Font;
import net.wforbes.omnia.topDown.level.Level;

public class Enemy extends Mob{

    private int color = Colors.get(-1, 111, 222, 555);
    private int scale = 1;
    private int tickCount = 0;


    public Enemy(Level level, Point2D startPos, String name) {
        super(level, name, startPos, 1);
        int tickCount = 0;
        this.canSwim = false;
    }

    public String simpleChatResponse(String type) {
        if (type.equals("greeting response")) {
            return "Greetings, traveler.";
        }
        return "";
    }

    @Override
    public void tick() {
        this.movementController.tick();
        super.tick();
    }

    private boolean isInIntArray(int[] arr, int a){
        for(int el : arr){
            if(el == a){
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(Screen screen) {
        super.render(screen);
    }
}
