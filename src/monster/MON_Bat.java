package monster;

import action.ACTION_Attack;
import action.ACTION_Chase;
import action.ACTION_Wander;
import action.MonsterAction;
import entity.Monster;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.util.Random;

public class MON_Bat extends Monster {

    public static final int CHASE_RANGE_IN_TILES = 6;
    public static final int ATTACK_RANGE_IN_TILES = 2;

    GamePanel gp;
    private final MonsterAction wanderAction;
    private final MonsterAction chaseAction;
    private final MonsterAction attackAction;

    public MON_Bat(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_monster;
        name = "Bat";
        speed = 2;
        maxLife = 20;
        life = maxLife;
        attack = 3;
        defence = 0;
        exp = 3;
//        projectile = new OBJ_Rock(gp);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        wanderAction = new ACTION_Wander(120);
        chaseAction = new ACTION_Chase(gp.player);
        attackAction = new ACTION_Attack(gp.player);
        changeAction(wanderAction);

        getImage();
    }
    public void getImage(){
        up1 = setup("/monster/bat_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/bat_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/bat_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/bat_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/bat_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/bat_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/bat_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/bat_down_2", gp.tileSize, gp.tileSize);

    }

    public void setAction(){

        if (isTargetWithinDistance(gp.player, gp.tileSize * ATTACK_RANGE_IN_TILES)) {
            changeAction(attackAction);
        }
        else if (isTargetWithinDistance(gp.player, gp.tileSize * CHASE_RANGE_IN_TILES)) {
            changeAction(chaseAction);
        }
        else {
            changeAction(wanderAction);
        }

        performCurrentAction();
    }
    @Override
    public void damageReaction(){
        actionLockCounter = 0;
        if ( gp.player.direction == "right") {
            direction = "left";
        }
        if ( gp.player.direction == "left") {
            direction = "right";
        }
        if ( gp.player.direction == "up") {
            direction = "down";
        }
        if ( gp.player.direction == "down") {
            direction = "up";
        }


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
