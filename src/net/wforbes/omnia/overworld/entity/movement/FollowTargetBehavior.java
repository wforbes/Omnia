package net.wforbes.omnia.overworld.entity.movement;

public final class FollowTargetBehavior implements MovementBehavior {
    @Override
    public void update(MovementController controller) {
        controller.followTarget();
    }
}
