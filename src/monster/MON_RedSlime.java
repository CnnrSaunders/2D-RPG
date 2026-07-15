package monster;

import action.ACTION_Attack;
import action.ACTION_Wander;
import action.MonsterAction;
import entity.Monster;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_RedSlime extends Monster {

    private static final int ATTACK_RANGE_IN_TILES = 2;

    GamePanel gp;
    private final MonsterAction wanderAction;
    private final MonsterAction attackAction;

    public MON_RedSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_monster;
        name = "Red Slime";
        speed = 3;
        maxLife = 8;
        life = maxLife;
        attack = 8;
        defence = 2;
        exp = 6;
        projectile = new OBJ_Rock(gp);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        wanderAction = new ACTION_Wander(120);
        attackAction = new ACTION_Attack(gp.player);
        changeAction(wanderAction);

        getImage();
    }
    public void getImage(){
        up1 = setup("/monster/redslime_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/redslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/redslime_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/redslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/redslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/redslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/redslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/redslime_down_2", gp.tileSize, gp.tileSize);

    }

    public void setAction(){

        if (isTargetWithinDistance(gp.player, gp.tileSize * ATTACK_RANGE_IN_TILES)) {
            changeAction(attackAction);
        }
        else {
            changeAction(wanderAction);
        }

        performCurrentAction();

        int i = new Random().nextInt(100) + 1;
        if(i > 99 && projectile.alive == false && shotAvailableCounter == 30) {

            projectile.set(worldX, worldY, direction, true, this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }
    }
    @Override
    public void damageReaction(){
        actionLockCounter = 0;
        direction = gp.player.direction;
    }

    public void checkDrop(){

        //check if we drop an item.
        int i = new Random().nextInt(100)+1;

        //set monster drop
        if (i < 50){
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if ( i >= 50 && i < 75){
            dropItem(new OBJ_Heart(gp));
        }
        if ( i >= 75 && i < 100){
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }

}
