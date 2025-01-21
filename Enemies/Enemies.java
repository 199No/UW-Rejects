package Enemies;

//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 

import src.Player;
import java.awt.Rectangle;
import java.util.ArrayList;

//-------------------------------------------------//
//                   Enemies                       //
//-------------------------------------------------// 
public abstract class Enemies {
    ///////////////
    //Properties
    ///////////////
    protected int health;
    protected int damage;
    protected double damageMultiplier;
    protected double speed;
    protected int width;        // pixels
    protected int height;       // pixels
    protected int eyesight;
    protected double xPos;
    protected double yPos;
    protected double[] lastSeen; // the x and y pos of the last time the enemy saw the player

    // States of an enemie
    protected boolean alert; // seen player, is aware
    protected boolean chasing; // moving towards/attacking player
    protected boolean idle; // still waiting for patrol movement
    protected boolean patroling; // moving towards a spot, or has a specific path
    
   //Hitbox
   private boolean active; //the player is able to be hit if true
   private int[] topLeft; //top left of the hitbox
   private Rectangle hitbox = new Rectangle(getWidth()/2, getHeight()/2, (int) getxPos() + getWidth(), (int) getyPos() + getHeight());
    ///////////////
    //Constuctor
    //////////////
    public Enemies(){
        
    }

    public Enemies(double x, double y){
        this.xPos = x;
        this.yPos = y;
    }
    
    //-------------------------------------------------//
    //                    Methods                      //
    //-------------------------------------------------// 

    public abstract void takeDamage(int dmg);

    public abstract void idleMove();

    public abstract void attack();

    public abstract void die();

    public abstract boolean isAlive();

    public abstract boolean scanArea(Player player);

    public abstract void moveToward(double[] lastSeen);

    public abstract void update(); // sets and checks all parts of the enemy (ran every frame)


    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public double getSpeed() {
        return speed;
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setxPos(double x) {
        this.xPos = x;
    }

    public void setyPos(double y) {
        this.yPos = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getEyesight() {
        return eyesight;
    }

    public boolean getAlert(){
        return this.alert;
    }

    public boolean getChasing(){
        return this.alert;
    }

    public boolean getIdle(){
        return this.idle;
    }

    public double[] getLocation() {
        return new double[]{getxPos(), getyPos()};
    }

    public double[] getLastSeen() {
        return this.lastSeen;
    }

    //return true if player is inside hitbox (got hit)
    public boolean checkHitbox(Player player){
        return false;
    }

    public double[] getHitboxTopLeft(){
        return new double[]{getxPos(), getyPos()};
    }

    public Rectangle getHitbox(){
        return new Rectangle((int) this.getxPos(), (int) this.getyPos(), this.getWidth(), this.getHeight());
    }


}