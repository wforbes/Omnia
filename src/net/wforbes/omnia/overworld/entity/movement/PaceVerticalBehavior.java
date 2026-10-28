package net.wforbes.omnia.overworld.entity.movement;

public final class PaceVerticalBehavior implements MovementBehavior {
    @Override
    public void update(MovementController controller) {
        controller.paceVertically();
    }
}
