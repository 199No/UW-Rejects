package src;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import java.awt.event.*;
//-------------------------------------------------//
//                    Player                       //
//-------------------------------------------------// 
public class Player {
    ///////////////
    //Properties
    //////////////
    private int health;
    private int damage;
    private int speed;
    private int xPos;
    private int yPos;
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
        xDir = -1;
        xPos -= 2;
    }

    // D
    public void moveRightWalk(){
        xDir = 1;
        xPos += 2;
    }

    // shift + A
    public void moveLeftRun(){
        xDir = -1;
        xPos -= 4;
    }

    //shift + D
    public void moveRightRun(){
        xDir = 1;
        xPos += 4;
    }

    // W
    public void moveUpWalk(){
        yPos -= 2;
        yDir = 1;
    }

    // S
    public void moveDownWalk(){
        yPos += 2;
        yDir = -1;
    }

    // shift + W
    public void moveUpRun(){
        yPos -= 4;
        yDir = 1;
    }
    
    // shift + S
    public void moveDownRun(){
        yPos += 4;
        yDir = -1;
    }
    
    //given the direction (key code) dash in that direction
    public void playerDash(int key){
        //find out direction
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

}