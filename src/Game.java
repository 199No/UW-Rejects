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
    Player player1;
    Player player2;
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
        player1 = new Swordsmen();
        player2 = new Wizard();
        input = new Input(player1,player2);
        enemies.add(createSlime());
        gui = new Gui(1280, 720, input);
        gameTimer = new Timer(2, this);
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
        // If we are keeping this make the playerMove method return a player
        input.playerMove(player1);
        input.playerMove(player2);
        input.playerAttack(player1);
        input.playerAttack(player2);
        // if(getKey(W){ doPlayerMove(); }
        this.player1 = input.getPlayer1(); // Game gets the newly updated copy
        this.player2 = input.getPlayer2(); // Game gets the newly updated copy
        Player tempPlayer;
        //TODO: FIX THIS
        for(int p = 1; p <= 2; p++){
            if(p == 1){
                tempPlayer = player1;
            }else{
                tempPlayer = player2;
            }
            for(int i = 0; i < this.enemies.size(); i++){
                enemies.get(i).update(tempPlayer);
            }
        }
    
        gui.background((int)frameRate, (int)frameRate, (int)frameRate / 2);
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
        gui.drawPlayer(player1);
        gui.drawPlayer(player2);
        gui.drawEnemies(enemies);
        gui.repaint();
        now = System.currentTimeMillis();
    }

    public Slime createSlime(){
        return new Slime(); //make a slime given a x and y
    }

}
