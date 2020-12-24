package net.wforbes.input;

import net.wforbes.game.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener
{
    public Key space = new Key("space");
    public Key shift = new Key("shift");
    public Key up = new Key("up");
    public Key down = new Key("down");
    public Key left = new Key("left");
    public Key right = new Key("right");
    public Key enter = new Key("enter");
    public Key w = new Key("w");
    public Key a = new Key("a");
    public Key s = new Key("s");
    public Key d = new Key("d");
    public Key q = new Key("q");
    public Key e = new Key("e");
    public Key m = new Key("m");


    public InputHandler(Game game) {
        game.addKeyListener(this);
        game.requestFocus();
    }

    public class Key
    {
        private String name;
        private int numTimesPressed = 0;
        private boolean pressed = false;
        private boolean released = false;

        public int getNumTimesPressed(){ return numTimesPressed; }

        public boolean isPressed(){ return pressed; }
        public boolean isReleased() { return released; }

        public Key(String name) {
            this.name = name;
        }

        public void toggle(boolean isPressed)
        {
            //System.out.println("Toggle " + this.name +": " + isPressed);
            pressed = isPressed;
            released = !pressed;
            if(isPressed)
                numTimesPressed++;
        }

        public void reset() {
            this.pressed = false;
            this.released = false;
        }
    }
    public void resetKeys() {
        space.reset();
        shift.reset();
        up.reset();
        down.reset();
        left.reset();
        right.reset();
        enter.reset();
        w.reset();
        a.reset();
        s.reset();
        d.reset();
        q.reset();
        e.reset();
        m.reset();
    }

    public void toggleKey(int keyCode, boolean isPressed)
    {
        if(keyCode == KeyEvent.VK_SPACE) {
            space.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_SHIFT) {
            shift.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_UP){
            up.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_DOWN){
            down.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_RIGHT){
            right.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_LEFT){
            left.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_ENTER) {
            enter.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_W) {
            w.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_A) {
            a.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_S) {
            s.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_D) {
            d.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_Q) {
            q.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_E) {
            e.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_M) {
            m.toggle(isPressed);
        }
    }

    public void keyPressed(KeyEvent e) {
        this.toggleKey(e.getKeyCode(), true);
    }

    public void keyReleased(KeyEvent e) {
        this.toggleKey(e.getKeyCode(), false);
    }

    public void keyTyped(KeyEvent e) {
        //this.toggleKey(e.getKeyCode(), true);
    }
}
