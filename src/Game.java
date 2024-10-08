package src;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

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
    Slime slime;
    double now, lastSecond, frameRate, framesLastSecond;
    Input input;
    private ArrayList<Enemies> enemies = new ArrayList<Enemies>();
    ///////////////
    //Constuctor
    //////////////
    public Game() throws AWTException{
        
        player = new Player();
        this.slime  = new Slime();
        enemies.add(this.slime);
        input = new Input(player);
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



        //movement for slime
        this.slime.checkSlimeStatus(this.player);
        this.slime.move(this.player); //slime moves towards player's new positon (after input);

        gui.background((int)frameRate * 2, (int)frameRate, (int)frameRate * 2);
        gui.displayFPS((int)frameRate);
        gui.drawPlayer(player);
        gui.drawEnemies(enemies);
        gui.repaint();
        now = System.currentTimeMillis();
    }

    public void checkSlime(){
        if(this.slime.getxPos() == this.player.getxPos() && this.slime.getyPos() == this.player.getxPos()){
            System.out.println("YOU DIEDEDEDEDEDEDDEDED");
        }
    }



}
