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
    private boolean active; //the player is able to be hit if true
    private int speed;
    private int xPos;
    private int yPos;
    private int dashCooldown; // in miliseconds
    private int dashLength; // in miliseconds
    private boolean isDashing;
    private int[] topLeft; //top left of the hitbox
    private final int width = 38;
    private final int height = 38;
    private int xDir, yDir;
    ///////////////
    //Constuctor
    //////////////
    public Player() {
        System.out.println("Player!");
        health = 100;
        damage = 1;
        xPos = 0;
        yPos = 0;
        this.topLeft = new int[]{xPos-this.getWidth(), yPos-this.getHeight()};
        xDir = 2;
        yDir = 1;
        speed = 5;
        dashCooldown = 3000;
        dashLength = 2000;
        isDashing = false;
    }

//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 
    public int getxPos(){
        return this.xPos;
    }

    public int getyPos(){
        return this.yPos;
    }

    public int[] getLocation(){
        return new int[] {this.xPos,this.yPos};
    }
    public int[] getHitboxTopLeft(){
        return new int[]{xPos-this.getWidth(), yPos-this.getHeight()};
    }

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
        xPos -= (speed * 2);
        xDir = -1;
    }

    //shift + D
    public void moveRightRun(){
        xPos += (speed * 2);
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
        yPos -= (speed * 2);
        yDir = 1;
    }
    
    // shift + S
    public void moveDownRun(){
        yPos += (speed * 2);
        yDir = -1;
    }

    public void dash(int direction){
        //given a direction move the direction they watned make speed 3* the speed and then keep moving
    }
    
    //given the direction (key code) dash in that direction
    public void setDash(int key){
        //find out direction
        boolean temp = false;
        if(key == 87){
            //W
            temp = true;
        }else if(key == 68){
            //D
            temp = true;
        }else if(key == 83){
            //S
            temp = true;
        }else if(key == 65){
            //A
            temp = true;
        }

        if(temp){
            isDashing = true;
            //direction??
            active = false;
            speed = 10; 
        }

        System.out.println("player dash " + key);

    }

    public void teleport(int x, int y){
        xPos = x;
        yPos = y;
    }

    // space
    public void attack(){
        //use damage variable
        System.out.println("Space Pressed: Attack! " + this.damage);
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

}