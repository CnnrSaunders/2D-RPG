package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    GamePanel gp;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackLeft1, attackLeft2, attackRight1, attackRight2, attackDown1, attackDown2;
    public Rectangle solidArea =  new Rectangle(0,0,48,48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    String dialogues[] = new String[20];
    public BufferedImage image, image2, image3;
    public boolean collision = false;
    public Rectangle attackArea = new Rectangle(0,0,0,0);

    //state
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    public boolean collisionOn = false;
    public Boolean invincible = false;
    int dialogueIndex = 0;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;



    //counters
    public int invincibleCounter;
    public int actionLockCounter = 0;
    public int spriteCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;

    //character attributes
    public int speed;
    public int type; // 0 is player, 1 is npc and 2 is monster.
    public String name;
    public int maxLife;
    public int life;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defence;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;

    // item attributes
    public int attackValue;
    public int defenceValue;




    public Entity(GamePanel gp){
        this.gp = gp;
    }
    public void setAction() {}
    public void damageReaction() {}
    public void speak(){
        if (dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
    public void update() {

        setAction();
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type == 2 && contactPlayer == true){
            if (gp.player.invincible == false){
                gp.playSE(6,0.03f);

                int damage = attack - gp.player.defence;
                if (damage < 0){ damage = 0;}
                gp.player.life -= damage;

                gp.player.invincible = true;
            }
        }

        // if collision is false, entity is able to move.
        if (collisionOn == false){
            switch(direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;

            }
        }

        spriteCounter++;
        if(spriteCounter > 12){
            if (spriteNum == 1){
                spriteNum = 2;
            }
            else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        // invincible counter
        if (invincible == true){
            invincibleCounter++;
            if (invincibleCounter > 40){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        //only draws world within the camera to increase performance for larger maps
        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch(direction){
                case "up":
                    if(spriteNum == 1){image = up1;}
                    if(spriteNum == 2){image = up2;}
                    break;
                case "down":
                    if(spriteNum == 1){image = down1;}
                    if(spriteNum == 2){image = down2;}
                    break;
                case "left":
                    if(spriteNum == 1){image = left1;}
                    if(spriteNum == 2){image = left2;}
                    break;
                case "right":
                    if(spriteNum == 1){image = right1;}
                    if(spriteNum == 2){image = right2;}
                    break;
            }

            // monster hp
            if (type == 2 && hpBarOn) {

                double oneScale = (double) gp.tileSize / maxLife; // length of one hp in px.
                double hpBarValue = oneScale * life;
                hpBarValue = Math.max(0,  hpBarValue);

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX - 1,screenY - 16, gp.tileSize + 2, 12);
                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX,screenY - 15, (int) hpBarValue, 10);

                hpBarCounter++;
                // after 10 seconds bar disappears
                if(hpBarCounter > 600) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }


            if (invincible == true){
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2,0.4f);
            }
            if (dying){
                deathAnimation(g2);
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            changeAlpha(g2, 1f);
        }
    }

    public void deathAnimation(Graphics2D g2){
        dyingCounter++;
        int i = 5;
        if (dyingCounter <= 5){changeAlpha(g2, 0f);}
        if (dyingCounter > i && dyingCounter <= i * 2)  {changeAlpha(g2, 1f);}
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {changeAlpha(g2, 0f);}
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {changeAlpha(g2, 1f);}
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {changeAlpha(g2, 0f);}
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {changeAlpha(g2, 1f);}
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {changeAlpha(g2, 0f);}
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {changeAlpha(g2, 1f);}
        if (dyingCounter > i * 8) {
            dying = false;
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public BufferedImage setup(String imagePath, int width, int height){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }
}
