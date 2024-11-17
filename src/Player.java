package src;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import java.awt.event.*;
import java.awt.Rectangle;
//-------------------------------------------------//
//                    Player                       //
//-------------------------------------------------// 
public class Player {
    ///////////////
    //Properties
    //////////////
    private int health;
    private int damage;
    private double speed;
    private int xPos;
    private int yPos;


    private int dashCooldown = 5000; // in miliseconds
    private int dashLength   = 350 + 500 + 350; // in miliseconds
    private int dashSpeed    = 3; // factor to multiply the speed when dashing
    private boolean isDashing;


    private boolean active; //the player is able to be hit if true
    private int[] topLeft; //top left of the hitbox
    private final int width = 38;
    private final int height = 38;
    private int xDir, yDir;
    ///////////////
    //Constuctor
    //////////////
    public Player() {
        System.out.println("Player!");
        xPos = 0;
        yPos = 0;
        this.topLeft = new int[]{xPos-this.getWidth(), yPos-this.getHeight()};
        xDir = 2;
        yDir = 1;
        speed = 5;
        isDashing = false;
    }

//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 
    // A
    public void moveLeftWalk(){
        if(isDashing){
            xPos -= speed * 0.1;
        }else{
            xPos -= speed;
        }
        xDir = -1;
    }

    // D
    public void moveRightWalk(){
        if(isDashing){
            xPos += speed * 0.1;
        }else{
            xPos += speed;
        }
        xDir = 1;
    }

    // shift + A
    public void moveLeftRun(){
        if(isDashing){
            xPos -= speed * 0.2;
        }else{
            xPos -= (speed * 1.25);
        }
        xDir = -1;
    }

    //shift + D
    public void moveRightRun(){
        if(isDashing){
            xPos += speed * 0.2;
        }else{
            xPos += (speed * 1.25);
        }
        xDir = 1;
    }

    // W
    public void moveUpWalk(){
        if(isDashing){
            yPos -= speed * 0.1;
        }else{
            yPos -= speed;
        }
        yDir = 1;
    }

    // S
    public void moveDownWalk(){
        if(isDashing){
            yPos += speed * 0.1;
        }else{
            yPos += speed;
        }
        yDir = -1;
    }

    // shift + W
    public void moveUpRun(){
        if(isDashing){
            yPos -= speed * 0.2;
        }else{
            yPos -= (speed * 1.25);
        }
        yDir = 1;
    }
    
    // shift + S
    public void moveDownRun(){
        if(isDashing){
            yPos += speed * 0.2;
        }else{
            yPos += (speed * 1.25);
        }
        yDir = -1;
    }

    public void dashRight(double speed){
        xPos += speed;
        xDir = 1;
    }

    public void dashLeft(double speed){
        xPos -= speed;
        xDir = -1;
    }

    public void dashUp(double speed){
        yPos -= speed;
        yDir = 1;
    }

    public void dashDown(double speed){
        yPos += speed;
        yDir = -1;
    }


    // space
    public void attack(){
        //use damage variable
        //System.out.println("Space Pressed: Attack! " + this.damage);
    }

    public int getxPos(){
        return this.xPos;
    }

    public int getyPos(){
        return this.yPos;
    }

    public void teleport(int x, int y){
        xPos = x;
        yPos = y;
    }

    public int[] getLocation(){
        return new int[] {this.xPos,this.yPos};
    }

    public int[] getHitboxTopLeft(){
        return new int[]{xPos-this.getWidth(), yPos-this.getHeight()};
    }

    public int getXDir(){
        return xDir;
    }
    
    public int getYDir(){
        return yDir;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public int getDashCooldown(){
        return this.dashCooldown;
    }

    public boolean getIsDashing(){
        return this.isDashing;
    }

    public void setIsDashing(boolean temp){
        this.isDashing = temp;
    }

    public boolean getActive(){
        return this.active;
    }

    public int getDashLength(){
        return this.dashLength;
    }

    public double getSpeed(){
        return speed;
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }
    public int getDashSpeed(){
        return this.dashSpeed;
    }

}