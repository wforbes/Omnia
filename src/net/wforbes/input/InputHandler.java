package net.wforbes.input;

import net.wforbes.game.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener
{
    public Key space = new Key();
    public Key shift = new Key();
    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key enter = new Key();
    public Key w = new Key();
    public Key a = new Key();
    public Key s = new Key();
    public Key d = new Key();
    public Key q = new Key();
    public Key e = new Key();
    public Key m = new Key();


    public InputHandler(Game game) {
        game.addKeyListener(this);
        game.requestFocus();
    }

    public class Key
    {
        private int numTimesPressed = 0;
        private boolean pressed = false;
        private boolean released = false;

        public int getNumTimesPressed(){ return numTimesPressed; }

        public boolean isPressed(){ return pressed; }
        public boolean isReleased() { return released; }

        public void toggle(boolean isPressed)
        {
            pressed = isPressed;
            released = !pressed;
            if(isPressed)
                numTimesPressed++;
        }
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
