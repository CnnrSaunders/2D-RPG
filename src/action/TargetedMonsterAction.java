package action;

import entity.Entity;
import entity.Monster;

abstract class TargetedMonsterAction implements MonsterAction {

    protected final Entity target;

    protected TargetedMonsterAction(Entity target) {
        this.target = target;
    }

    protected void moveTowardTarget(Monster monster, boolean horizontalFirst) {
        int horizontalDistance = target.getCenterX() - monster.getCenterX();
        int verticalDistance = target.getCenterY() - monster.getCenterY();

        if (horizontalFirst && horizontalDistance != 0) {
            monster.direction = horizontalDistance < 0 ? "left" : "right";
        }
        else if (verticalDistance != 0) {
            monster.direction = verticalDistance < 0 ? "up" : "down";
        }
        else if (horizontalDistance != 0) {
            monster.direction = horizontalDistance < 0 ? "left" : "right";
        }
    }

    protected void moveAwayFromTarget(Monster monster, boolean horizontalFirst) {
        int horizontalDistance = target.getCenterX() - monster.getCenterX();
        int verticalDistance = target.getCenterY() - monster.getCenterY();

        if (horizontalFirst && horizontalDistance != 0) {
            monster.direction = horizontalDistance < 0 ? "right" : "left";
        }
        else if (verticalDistance != 0) {
            monster.direction = verticalDistance < 0 ? "down" : "up";
        }
        else if (horizontalDistance != 0) {
            monster.direction = horizontalDistance < 0 ? "right" : "left";
        }
    }
}
