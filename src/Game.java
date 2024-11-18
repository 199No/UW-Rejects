package src;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import java.awt.AWTException;
import java.awt.Color;
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
    int pDashing;
    int dashing;
    private ArrayList<Enemies> enemies = new ArrayList<Enemies>();
    ///////////////
    //Constuctor
    //////////////
    public Game() throws AWTException, IOException{
        Images images = new Images();
        image = images.getImage("tree1");
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

        ////////////////
        /// Input
        ///////////////
        // Input updates its copy of the player
        input.playerMove();
        input.playerAttack();
        this.player = input.getPlayer(); // Game gets the newly updated copy
        
        for(int i = 0; i < this.enemies.size(); i++){
            enemies.get(i).scanArea(this.player);
            if(enemies.get(i).getIsAlert()){
                enemies.get(i).moveToward(enemies.get(i).getLastSeen());
            }else{
                enemies.get(i).idleMove();
            }
        }
    
        gui.background((int)frameRate * 2, (int)frameRate, (int)frameRate * 2);
        gui.addToQueue(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                g2d.drawImage(image.getScaledInstance(Gui.WIDTH, Gui.HEIGHT, 0), 0, 0, null);
            }
        });
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
        gui.displayFPS((int)frameRate);
        gui.drawPlayer(player);
        gui.drawEnemies(enemies);
        gui.repaint();
        now = System.currentTimeMillis();
    }

    public Slime createSlime(){
        return new Slime(); //make a slime given a x and y
    }

}
