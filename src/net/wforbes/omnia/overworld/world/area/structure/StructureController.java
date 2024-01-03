package net.wforbes.omnia.overworld.world.area.structure;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import net.wforbes.omnia.overworld.world.area.Area;

import java.util.ArrayList;
import java.util.List;

public class StructureController {
    private final Area area;
    private final ArrayList<StructureDoor> doorObjs;

    private List<Structure> structures;

    public StructureController(Area area) {
        this.area = area;
        this.doorObjs = new ArrayList<>();
    }

    public void init() {
        this.addStructures();
        for(Structure s: this.structures) {
            s.init();
            System.out.println(s.getStructureDoor());
            if(s.getStructureDoor() != null) {
                s.getStructureDoor().init();
                //TODO: handle multiple doors on one structure?
                this.doorObjs.add(s.getStructureDoor());
            }
        }
    }

    public void update() {
        for (StructureDoor sd: this.doorObjs) {
            sd.update();
        }
    }

    public void addStructures() {
        this.structures = new ArrayList<>();
        Structure testStruct = new Structure(
            this.area.getWorld().gameState,
            StructureType.TYPES.HOUSE_WIDE,
            50,
            50
        );
        this.structures.add(testStruct);
    }

    public List<Structure> getStructures() {
        return this.structures;
    }
    public List<StructureDoor> getStructureDoors() {
        return this.doorObjs;
    }

    public void renderDoorSprites(GraphicsContext gc) {
        if (this.doorObjs == null || this.doorObjs.isEmpty()) return;
        for (StructureDoor sd: this.doorObjs) {
            sd.renderDoor(gc);
        }
    }

    public void renderAboveDoors(GraphicsContext gc) {
        if (this.doorObjs == null || this.doorObjs.isEmpty()) return;
        for (StructureDoor sd: doorObjs) {
            sd.renderAboveDoor(gc);
        }
    }
    public List<Image> getAboveDoorSprites() {
        List<Image> sprites = new ArrayList<>();
        for (Structure s: this.structures) {
            if(s.getAboveDoorSprite() != null) {
                sprites.add(s.getDoorSprite());
            }
        }
        return sprites;
    }
}
