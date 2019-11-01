package net.wforbes.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener
{
    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();

    public InputHandler(Game game)
    {
        game.addKeyListener(this);
        game.requestFocus();
    }

    public class Key
    {
        private int numTimesPressed = 0;
        private boolean pressed = false;

        public int getNumTimesPressed(){ return numTimesPressed; }

        public boolean isPressed(){ return pressed; }

        public void toggle(boolean isPressed)
        {
            pressed = isPressed;
            if(isPressed)
                numTimesPressed++;
        }
    }

    //TODO: move the keyevent settings into a config file
    public void toggleKey(int keyCode, boolean isPressed)
    {
        if(keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP){
            up.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN){
            down.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT){
            right.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT){
            left.toggle(isPressed);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        this.toggleKey(e.getKeyCode(), true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.toggleKey(e.getKeyCode(), false);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {}
}
