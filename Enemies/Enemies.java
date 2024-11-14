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
    protected int speed;
    protected int width;
    protected int height;
    protected int eyesight;
    protected double xPos;
    protected double yPos;
    protected boolean alert;
    protected boolean angry;
    ///////////////
    //Constuctor
    //////////////
    public Enemies(){

    }
    
    //-------------------------------------------------//
    //                    Methods                      //
    //-------------------------------------------------// 

     //return true if player is inside hitbox (got hit)
     public boolean checkHitbox(Player player){
        return false;
    }

    public Rectangle getHitbox(){
        return new Rectangle((int)this.xPos, (int) this.yPos, this.width, this.height);
    }

    public abstract void checkStatus(Player player);

    public abstract void takeDamage(int dmg);

    public abstract void idleMove();

    public abstract void attack();

    public abstract void die();

    public abstract void spawn();

    public abstract boolean isAlive();

    public abstract boolean checkAlert(Player player);

    public abstract void update(); // sets and checks all parts of the enemy (ran every frame)


    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
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
    
    public double[] getLocation() {
        return new double[]{getxPos(), getyPos()};
    }

}