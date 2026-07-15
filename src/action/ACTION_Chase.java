package action;

import entity.Entity;
import entity.Monster;

public class ACTION_Chase extends TargetedMonsterAction {

    public ACTION_Chase(Entity target) {
        super(target);
    }

    @Override
    public void perform(Monster monster) {
        int horizontalDistance = Math.abs(target.getCenterX() - monster.getCenterX());
        int verticalDistance = Math.abs(target.getCenterY() - monster.getCenterY());

        boolean horizontalFirst = horizontalDistance >= verticalDistance;
        // If the preferred direction collided last frame, try the other axis.
        if (monster.collisionOn) {
            horizontalFirst = !horizontalFirst;
        }
        moveTowardTarget(monster, horizontalFirst);
    }
}
