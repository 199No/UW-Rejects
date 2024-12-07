package src;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import java.awt.AWTException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

import Enemies.*;
//-------------------------------------------------//
//                     Game                        //
//-------------------------------------------------// 
public class Game implements ActionListener{
    ///////////////
    //Properties
    ///////////////
    Timer gameTimer;
    Gui gui;
    Player player1;
    Player player2;
    double now, lastSecond, frameRate, framesLastSecond;
    Input input;
    BufferedImage image;
    int pDashing;
    int dashing;

    // all keys used, besides shift for player 1
    int[] player1Keys = {
        KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D,
        KeyEvent.VK_X, KeyEvent.VK_C
    };
        
    // all key codes used, besides shift for player 2
    int[] player2Keys = {
        KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L,
        KeyEvent.VK_N, KeyEvent.VK_M
    };

    boolean[] movement = new boolean[4];

    private ArrayList<Enemies> enemies = new ArrayList<Enemies>();
    private ArrayList<Player>  players = new ArrayList<Player>();
    ///////////////
    //Constuctor
    //////////////
    public Game() throws AWTException, IOException{
        Images images = new Images();
        image = images.getImage("tree1");
        this.player1 = new Player(0.0,0.0,100,10,10,5);
        this.player2 = new Player(500.0, 500.0, 100, 10, 10, 5);
        players.add(player1);
        players.add(player2);
        this.input = new Input();
        enemies.add(createSlime(500, 700));
        gui = new Gui(1280, 720, input);
        gameTimer = new Timer(5, this);
        gameTimer.start();
        now = System.currentTimeMillis();
        lastSecond = System.currentTimeMillis();
    }

//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 

    @Override
    public void actionPerformed(ActionEvent e) {
        now = System.currentTimeMillis();
        if(now - lastSecond > 1000){
            lastSecond = now;
            frameRate = framesLastSecond;
            framesLastSecond = 0;
        } else {
            framesLastSecond ++;
        }

        ////////////////
        /// Input
        ///////////////
        // Input updates its copy of the player
        //update Player based on Input Information
        updatePlayer();
        
        for(int i = 0; i < this.enemies.size(); i++){

            for(int j = 0; j < this.players.size(); j++){
                enemies.get(i).scanArea(players.get(j));
            }

            if(enemies.get(i).getAlert()){
                enemies.get(i).moveToward(enemies.get(i).getLastSeen());
            }else{
                enemies.get(i).idleMove();
            }
        }
    
        gui.background((int)frameRate, (int)frameRate, (int)frameRate / 2);
        //gui.addToQueue(new GraphicsRunnable() {
        //    public void draw(Graphics2D g2d){
        //        g2d.drawImage(image.getScaledInstance(Gui.WIDTH, Gui.HEIGHT, 0), 0, 0, null);
        //    }
        //});
        // Dash bar
        gui.addToQueue(new GraphicsRunnable() {
            public void draw(Graphics2D g){
                double height = (((double)(int)System.currentTimeMillis() - (double)input.getLastDash()) / 5000) * Gui.HEIGHT;
                g.setColor(Color.BLACK);
                g.fillRect(Gui.WIDTH - 30, 0, 30, Gui.HEIGHT);
                g.setColor(new Color(3, 148, 252));
                g.fillRect(Gui.WIDTH - 30, Gui.HEIGHT - (int)height, 30, (int)height);

            }
        });
        gui.drawPlayers(players);
        gui.drawEnemies(enemies);
        gui.displayFPS((int)frameRate);
        gui.repaint();
        now = System.currentTimeMillis();
    }

    public Slime createSlime(double x, double y){
        return new Slime(x,y); //make a slime given a x and y
    }

    public Player getPlayer1(){
        return this.player1;
    }

    public Player getPlayer2(){
        return this.player2;
    }

    public void updatePlayer(){
        //get input information
        boolean[] keys   = input.getKeys();
        boolean[] shifts = input.getShifts();
        //move player based on shift and keys pressed

        this.movement = new boolean[]{keys[player1Keys[0]], keys[player1Keys[1]], keys[player1Keys[2]], keys[player1Keys[3]]};  // W A S D
        player1.move(movement);
        this.movement = new boolean[]{keys[player2Keys[0]], keys[player2Keys[1]], keys[player2Keys[2]], keys[player2Keys[3]]};  // I J K L
        player2.move(movement);

        //ATTACK
        if(keys[67]){
            //C
            this.player1.attack();
        }
        if(keys[78]){
            //N
            this.player2.attack();
        }
        //BLOCK
        if(keys[88]){
            //X
            this.player1.block();
        }
        if(keys[77]){
            //M
            this.player2.block();
        }
        //DASH
        //can the player dash?
        if((int) System.currentTimeMillis() - input.getLastDash() > 5000){
            int key = input.getDash(); // key event of the dash
            //was it pressed recently?
            if((int) System.currentTimeMillis() - input.getPressed()[key] < 20){
                for(int i = 0; i < player1Keys.length; i++){
                    if(player1Keys[i] == key){
                        player1.dash();
                    }
                    if(player2Keys[i] == key){
                        player2.dash();
                    }
                }
            }
        }
    }

}
