package Enemies;

//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 

import src.Player;

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
    protected int xPos;
    protected int yPos;
    protected int width;
    protected int height;
    protected int eyesight;
    protected boolean alert = false;
    protected boolean angry = false;
    protected int now;
    protected int nextMovement; //random number
    protected int lastMovement;
    ///////////////
    //Constuctor
    //////////////
    public Enemies(){
        System.out.println("Enemies!");
    }
    
    //-------------------------------------------------//
    //                    Methods                      //
    //-------------------------------------------------// 

    public void checkStatus(Player player){
        //checks if the enemy is alert
    }

    public void updateMovement(){
        //updates ticks positions
    }

    public void updateTimer(){
        //update the ingame timer
    }

    public void update(){
        // up date the status (?)
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
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
    
    public int[] getLocation() {
        return new int[]{getxPos(), getyPos()};
    }

}