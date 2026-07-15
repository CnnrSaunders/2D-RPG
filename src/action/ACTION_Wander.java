package action;

import entity.Monster;

import java.util.Random;

public class ACTION_Wander implements MonsterAction {

    private final Random random = new Random();
    private final int directionChangeInterval;

    public ACTION_Wander(int directionChangeInterval) {
        if (directionChangeInterval < 1) {
            throw new IllegalArgumentException("Direction change interval must be positive");
        }
        this.directionChangeInterval = directionChangeInterval;
    }

    @Override
    public void onEnter(Monster monster) {
        monster.actionLockCounter = 0;
        chooseDirection(monster);
    }

    @Override
    public void perform(Monster monster) {
        monster.actionLockCounter++;

        if (monster.actionLockCounter >= directionChangeInterval) {
            chooseDirection(monster);
            monster.actionLockCounter = 0;
        }
    }

    @Override
    public boolean canDamagePlayerOnContact() {
        return true;
    }

    private void chooseDirection(Monster monster) {
        int directionNumber = random.nextInt(4);

        switch (directionNumber) {
            case 0: monster.direction = "up"; break;
            case 1: monster.direction = "down"; break;
            case 2: monster.direction = "left"; break;
            case 3: monster.direction = "right"; break;
        }
    }
}
