package monster;

import action.ACTION_Flee;
import action.ACTION_Wander;
import action.MonsterAction;
import entity.Monster;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_GreenSlime extends Monster {

    public static final int FLEE_DURATION_FRAMES = 600;
    public static final double FLEE_SPEED_MULTIPLIER = 1.5;

    GamePanel gp;
    private final MonsterAction wanderAction;
    private final MonsterAction fleeAction;
    private int fleeFramesRemaining;

    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_monster;
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;
        attack = 5;
        defence = 0;
        exp = 2;
        projectile = new OBJ_Rock(gp);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        wanderAction = new ACTION_Wander(120);
        fleeAction = new ACTION_Flee(gp.player, FLEE_SPEED_MULTIPLIER);
        changeAction(wanderAction);

        getImage();
    }
    public void getImage(){
        up1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);

    }

    public void setAction(){

        if (fleeFramesRemaining > 0) {
            changeAction(fleeAction);
            fleeFramesRemaining--;
        }
        else {
            changeAction(wanderAction);
        }

        performCurrentAction();
    }
    @Override
    public void damageReaction(){
        actionLockCounter = 0;
        fleeFramesRemaining = FLEE_DURATION_FRAMES;
        changeAction(fleeAction);
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
