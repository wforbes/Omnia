package net.wforbes.omnia.overworld.world.area.structure;

import net.wforbes.omnia.overworld.world.area.Area;

import java.util.ArrayList;
import java.util.List;

public class StructureController {
    private final Area area;

    private List<Structure> structures;

    public StructureController(Area area) {
        this.area = area;
    }

    public void init() {
        this.addStructures();
        for(Structure s: this.structures) {
            s.init();
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
}
