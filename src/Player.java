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
    private int INTERVAL = 350;


    private int dashCooldown = 3000; // in miliseconds
    private int dashLength   = 2000; // in miliseconds
    private boolean isDashing;
    private double dashTimer;
    private int dashDirection;


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
        xPos -= speed;
        xDir = -1;
    }

    // D
    public void moveRightWalk(){
        xPos += (speed);
        xDir = 1;
    }

    // shift + A
    public void moveLeftRun(){
        xPos -= (speed * 1.25);
        xDir = -1;
    }

    //shift + D
    public void moveRightRun(){
        xPos += (speed * 1.25);
        xDir = 1;
    }

    // W
    public void moveUpWalk(){
        yPos -= speed;
        yDir = 1;
    }

    // S
    public void moveDownWalk(){
        yPos += speed;
        yDir = -1;
    }

    // shift + W
    public void moveUpRun(){
        yPos -= (speed * 1.25);
        yDir = 1;
    }
    
    // shift + S
    public void moveDownRun(){
        yPos += (speed * 1.25);
        yDir = -1;
    }

    // A
    public void dashLeft(double speed){
        xPos -= speed;
        xDir = -1;
    }

    // D
    public void dashRight(double speed){
        xPos += speed;
        xDir = 1;
    }

    // W
    public void dashUp(double speed){
        yPos -= speed;
        yDir = 1;
    }

    // S
    public void dashDown(double speed){
        yPos += speed;
        yDir = -1;
    }

    //movement when the player is dashing
    public void dash(){
        int dir = getDashDirection();
        if(Math.abs(System.currentTimeMillis() - dashTimer) < (INTERVAL/2)){
            //lil pause
        }else if(Math.abs(System.currentTimeMillis() - dashTimer) >= (INTERVAL/2) && Math.abs(System.currentTimeMillis() - dashTimer) < dashLength - (INTERVAL/2)){
            //actually dashing (can still take input)
        }else if(Math.abs(System.currentTimeMillis() - dashTimer) >= dashLength - (INTERVAL/2) && Math.abs(System.currentTimeMillis() - dashTimer) < dashLength){
            //lil pause
        }else if(Math.abs(System.currentTimeMillis() - dashTimer) >= dashLength){
            //after the dash
            //set everything back to normal
            isDashing = false;
            active = true;
            
            //idk how to set dashtimer and dashdirection back to normal
        }
    }
    
    //basically tells the player when it first dashes
    //given the direction (key code) dash in that direction
    public void setDash(int key){

        // set the direction
        // set is dashing

        isDashing = true;
        active = false;

        dashTimer = System.currentTimeMillis();
        this.dashDirection = key;

        System.out.println("player dash " + key);

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

    public int getDashDirection(){
        return dashDirection;
    }
    public double getSpeed(){
        return speed;
    }

}