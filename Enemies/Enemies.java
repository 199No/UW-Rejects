package Enemies;

//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 

import src.Entity;
import java.awt.Rectangle;
import java.util.ArrayList;

//-------------------------------------------------//
//                   Enemies                       //
//-------------------------------------------------// 
public abstract class Enemies extends Entity {
    ///////////////
    //Properties
    ///////////////
    protected int health;
    protected int damage;
    protected double speed;
    protected double width;        // pixels
    protected double height;       // pixels
    protected int eyesight;
    protected double xPos;
    protected double yPos;
    protected double[] lastSeen; // the x and y pos of the last time the enemy saw the player
    protected boolean isAlive;

    // States of an enemy
    protected boolean alert; // seen player, is aware
    protected boolean chasing; // moving towards/attacking player
    protected boolean idle; // still waiting for patrol movement
    
    //Hitbox
    private boolean active; //the player is able to be hit if true
    ///////////////
    //Constuctor
    //////////////

    public Enemies(double x, double y, double width, double height, Rectangle hitbox){
        super(x,y,width,height,hitbox);
    }
    
    //-------------------------------------------------//
    //                    Methods                      //
    //-------------------------------------------------// 

    public abstract void takeDamage(int dmg);

    public abstract void idleMove();

    public abstract void attack();

    public abstract void die();

    public abstract void moveToward(double[] lastSeen);

    public abstract void update(ArrayList<int[]> PlayerLocations); // sets and checks all parts of the enemy (ran every frame)

    public abstract boolean scanArea(int[] playerLocation);

    public double[] getLastSeen(){
        return this.lastSeen;
    }
    public boolean getAlert(){
        return this.alert;
    }
    public boolean getIsAlive(){
        return this.isAlive;
    }

}