package src;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.util.ArrayList;

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
    Player player;
    double now, lastSecond, frameRate, framesLastSecond;
    Input input;
    BufferedImage image;
    private ArrayList<Enemies> enemies = new ArrayList<Enemies>();
    ///////////////
    //Constuctor
    //////////////
    public Game() throws AWTException, IOException{
        image = ImageIO.read(new File("Images\\Enviroment\\appleTree.png"));
        player = new Player();
        input = new Input(player);
        enemies.add(createSlime());
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
        input.playerMove();
        input.playerAttack();
        this.player = input.getPlayer(); //updates player with the inputs copy of player



        for(int i = 0; i < this.enemies.size(); i++){
            this.enemies.get(i).checkStatus(this.player);
        }
    
        gui.background((int)frameRate * 2, (int)frameRate, (int)frameRate * 2);
        gui.displayFPS((int)frameRate);
        gui.drawPlayer(player);
        gui.drawEnemies(enemies);
        gui.addToQueue(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                g2d.drawImage(image.getScaledInstance(76, 114, 0), 0, 0, null);
            }
        });
        gui.repaint();
        now = System.currentTimeMillis();
    }

    public Slime createSlime(){
        return new Slime();
    }

    public void checkHitBoxes(){

        //checks if any of the hitboxes are touching eachother
        //use for loop with arraylist of enemies and the x & y values of the player (width and height)

    }



}
