package Enemies;

//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 

import src.Player;
import java.awt.Rectangle;

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
    protected boolean alert;
    protected boolean chasing;
    protected boolean idle;
    ///////////////
    //Constuctor
    //////////////
    public Enemies(){
        
    }
    
    //-------------------------------------------------//
    //                    Methods                      //
    //-------------------------------------------------// 

    public abstract void takeDamage(int dmg);

    public abstract void idleMove();

    public abstract void attack(Vector direction);

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
    
    public Rectangle getHitbox(){
        return new Rectangle((int)this.xPos - this.width/2, (int) this.yPos - this.height/2, this.width, this.height);
    }

}