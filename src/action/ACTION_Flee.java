package action;

import entity.Entity;
import entity.Monster;

/**
 * Moves away from a target, trying the other axis after a collision.
 */
public class ACTION_Flee extends TargetedMonsterAction {

    private final double speedMultiplier;
    private double fractionalMovement;

    public ACTION_Flee(Entity target) {
        this(target, 1.0);
    }

    public ACTION_Flee(Entity target, double speedMultiplier) {
        super(target);
        if (speedMultiplier <= 0) {
            throw new IllegalArgumentException("Speed multiplier must be positive");
        }
        this.speedMultiplier = speedMultiplier;
    }

    @Override
    public void onEnter(Monster monster) {
        // Start with the faster frame when the result falls between two pixels.
        fractionalMovement = 0.5;
    }

    @Override
    public void perform(Monster monster) {
        double boostedSpeed = monster.speed * speedMultiplier + fractionalMovement;
        int movementSpeed = (int) boostedSpeed;
        fractionalMovement = boostedSpeed - movementSpeed;
        monster.setMovementSpeed(movementSpeed);

        int horizontalDistance = Math.abs(target.getCenterX() - monster.getCenterX());
        int verticalDistance = Math.abs(target.getCenterY() - monster.getCenterY());

        boolean horizontalFirst = horizontalDistance >= verticalDistance;
        if (monster.collisionOn) {
            horizontalFirst = !horizontalFirst;
        }
        moveAwayFromTarget(monster, horizontalFirst);
    }
}
