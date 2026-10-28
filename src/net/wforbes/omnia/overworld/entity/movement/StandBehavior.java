package net.wforbes.omnia.overworld.entity.movement;

public final class StandBehavior implements MovementBehavior {
    @Override
    public void update(MovementController controller) {
        controller.standStill();
    }
}
