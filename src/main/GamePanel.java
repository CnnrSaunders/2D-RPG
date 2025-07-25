package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int originalTileSize = 16; //16 * 16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels.

    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //FPS
    int FPS = 60;

    //system
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;
    public EventHandler ehandler = new EventHandler(this);


    //Entity and objects
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    //game state
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;



    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){

        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        // play theme song
        // playMusic(0);
        gameState = titleState;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
            }

        }

    }

    public void update(){

        if (gameState == playState){
            player.update();

            // npc
            for(int i = 0; i < npc.length;i++){
                if (npc[i] != null){
                    npc[i].update();
                }
            }

            // monster
            for(int i = 0; i < monster.length;i++){
                if (monster[i] != null){
                    if (monster[i].alive && !monster[i].dying) {monster[i].update();}
                    if (!monster[i].alive) {monster[i] = null;}
                }
            }
        }
        if (gameState == pauseState){
            //nothing
        }
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        //ensure tiles are drawn before player

        //title screen
        if (gameState == titleState){
            ui.draw(g2);
        }

        // others
        else {
            //Tiles
            tileM.draw(g2);
            // add entities to list
            entityList.add(player);
            for(int i = 0; i < npc.length; i++){
                if (npc[i] != null){
                    entityList.add(npc[i]);
                }
            }
            // add objects to list.
            for (int i = 0; i < obj.length; i++){
                if (obj[i] != null){
                    entityList.add(obj[i]);
                }
            }
            // add monsters
            for (int i = 0; i < monster.length; i++){
                if (monster[i] != null){
                    entityList.add(monster[i]);
                }
            }

            //sorting
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {

                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            //draw entities
            for (int i = 0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
            }
            // empty the list
            entityList.clear();

            // ui
            ui.draw(g2);
        }



        g2.dispose();
    }
    public void playMusic(int i){
        music.setFile(i);
        music.setVolume(0.01F);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i, float vol){
        se.setFile(i);
        se.setVolume(vol);
        se.play();
    }
}
