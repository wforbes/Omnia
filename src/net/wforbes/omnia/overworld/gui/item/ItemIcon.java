package net.wforbes.omnia.overworld.gui.item;

import javafx.scene.image.Image;

import java.util.Objects;

public class ItemIcon {
    private Image displayImage;

    public ItemIcon(String iconPath) {
        this.displayImage = new Image(Objects.requireNonNull(getClass().getResource(iconPath)).toExternalForm());
    }

    public Image getDisplayImage() {
        return this.displayImage;
    }
}
