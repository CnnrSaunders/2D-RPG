package action;

import entity.Entity;
import entity.Monster;

/**
 * A close-range attack. The normal monster collision handling applies damage
 * when this action moves the monster into the player.
 */
public class ACTION_Attack extends TargetedMonsterAction {

    public ACTION_Attack(Entity target) {
        super(target);
    }

    @Override
    public void perform(Monster monster) {
        int horizontalDistance = Math.abs(target.getCenterX() - monster.getCenterX());
        int verticalDistance = Math.abs(target.getCenterY() - monster.getCenterY());
        moveTowardTarget(monster, horizontalDistance >= verticalDistance);
    }

    @Override
    public boolean canDamagePlayerOnContact() {
        return true;
    }
}
