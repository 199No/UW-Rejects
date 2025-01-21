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
public class Slime extends Enemies{
    ///////////////
    //Properties
    ///////////////

    int maxDistance = 100; //max distance a slime would hop idle-ly
    int MAX = 3000; // + 2000 in miliseconds
    int lastDash;
    Random rnd = new Random();
    int rndLength;
    int startDash;
    boolean chasing;
    double[] randomLoc;

    ///////////////
    //Constuctor
    //////////////
    public Slime(double x, double y){
        super(x,y);
        this.health = 10;
        this.damage = 1;
        this.speed = 3;
        this.width = Gui.TILE_SIZE;
        this.height = Gui.TILE_SIZE;
        this.eyesight = 400;
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


    public boolean isAlive(){
        if(this.health <= 0){
        
            return false;
        }else{
            return true;
        }
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