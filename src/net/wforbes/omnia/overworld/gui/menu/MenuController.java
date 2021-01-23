package net.wforbes.omnia.overworld.gui.menu;

import net.wforbes.omnia.gameState.GameStateManager;
import net.wforbes.omnia.menu.PauseMenu;


public class MenuController {

    private PauseMenu pauseMenu;

    public MenuController(GameStateManager gsm) {
        this.pauseMenu = new PauseMenu(gsm);
    }

    public PauseMenu getPauseMenu() {
        return pauseMenu;
    }

    //TODO: implement death menu
    /*
    public DeathMenu getDeathMenu() {}
     */
}
