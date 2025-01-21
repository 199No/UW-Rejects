package Enemies;


//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 

import src.Player;
import java.awt.Rectangle;
import java.util.Random;
import java.util.ArrayList;
import src.Gui;

//-------------------------------------------------//
//                   Enemies                       //
//-------------------------------------------------// 
public class Bee extends Enemies{
    ///////////////
    //Properties
    ///////////////

    ///////////////
    //Constuctor
    //////////////
    public Bee(double x, double y){
        super(x,y);
        this.health = 50;
        this.damage = 5;
        this.speed = 4;
        this.width = Gui.TILE_SIZE;
        this.height = Gui.TILE_SIZE;
        this.eyesight = 200;
        this.alert = false;
        this.idle = true;
        this.chasing = false;
    }

    //-------------------------------------------------//
    //                    Methods                      //
    //-------------------------------------------------// 


    
    public void takeDamage(int dmg){

    }

    //Move idlely after some time.
    public void idleMove(){

    }

    public void attack(){

    }

    public void die(){
        //play death animation
        //remove from list of enemies
        //remove from everything
        //give xp???
    }

    //checks to see if the slime can see the player, LOS of player with obstacles, if it can sets the alert to true
    public boolean scanArea(int[] location){ // scans for players around slime, returns true if slime can see the player
        return false;
    }
    //update slime position and state
    public void update(){

    }

    //Check for obstacles in the way of the line of sight
    public boolean checkObstaclesLOS(){

        return false;
    }

    //Moving towards the last time the player was seen, (not attacking just moving)
    public void moveToward(double[] lastSeen){

    }


    // dashing towards, whether that be a idle movement, or a player attack 
    // TODO: Given a parameter that tells the slime to move that direction
    public void dashToward(){

    }
}
