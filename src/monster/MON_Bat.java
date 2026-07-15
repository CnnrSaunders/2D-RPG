package monster;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_Bat extends Entity {

    GamePanel gp;

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

        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
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
