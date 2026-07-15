package action;

import entity.Monster;

/**
 * A movement or combat behaviour that can be assigned to a monster.
 */
public interface MonsterAction {

    default void onEnter(Monster monster) {
    }

    void perform(Monster monster);

    default boolean canDamagePlayerOnContact() {
        return false;
    }
}
