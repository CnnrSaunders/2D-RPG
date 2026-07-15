package entity;

import action.MonsterAction;
import main.GamePanel;

public abstract class Monster extends Entity {

    private MonsterAction currentAction;
    private int movementSpeed;

    protected Monster(GamePanel gp) {
        super(gp);
    }

    protected final void changeAction(MonsterAction nextAction) {
        if (currentAction == nextAction) {
            return;
        }

        currentAction = nextAction;
        currentAction.onEnter(this);
    }

    protected final void performCurrentAction() {
        movementSpeed = speed;
        if (currentAction != null) {
            currentAction.perform(this);
        }
    }

    public final void setMovementSpeed(int movementSpeed) {
        if (movementSpeed < 0) {
            throw new IllegalArgumentException("Movement speed cannot be negative");
        }
        this.movementSpeed = movementSpeed;
    }

    @Override
    public final int getMovementSpeed() {
        return movementSpeed;
    }

    public final MonsterAction getCurrentAction() {
        return currentAction;
    }

    protected final boolean isTargetWithinDistance(Entity target, int distanceInPixels) {
        long horizontalDistance = target.getCenterX() - getCenterX();
        long verticalDistance = target.getCenterY() - getCenterY();
        long maximumDistance = distanceInPixels;

        return horizontalDistance * horizontalDistance + verticalDistance * verticalDistance
                <= maximumDistance * maximumDistance;
    }

    @Override
    protected boolean canDamagePlayerOnContact() {
        return currentAction != null && currentAction.canDamagePlayerOnContact();
    }
}
