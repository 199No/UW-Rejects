package Enemies;


//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 

import src.Player;

//-------------------------------------------------//
//                   Enemies                       //
//-------------------------------------------------// 
public class Slime extends Enemies{
    ///////////////
    //Properties
    ///////////////
    

// slime is going to alerted when it first sees the player and then will go towards the player
// if player moves out of sight slime will continue moving towards the last x and y position they saw player
// if they still dont see player slime will be not alerted anymore



// check if player in radius
// check if slime can see player
// set the slime to alert
// move the slime towards player
// if player moves outside of LOS capture last seen point
// slime keep moving towards point
// slime reaches point and doesnt find player set to not alert


    private int[] lastPlayerPos = new int[]{this.getxPos(), this.getyPos()}; //start with the slimes positon cuz idk what else to do


    ///////////////
    //Constuctor
    //////////////
    public Slime(){
        super();
        this.health = 10;
        this.damage = 1;
        this.speed = 1;
        this.width = 5;
        this.height = 5;
        this.eyesight = 400;
        this.xPos = 500;
        this.yPos = 100;
        this.alert = false;


        System.out.println("Enemies!");
    }

    //-------------------------------------------------//
    //                    Methods                      //
    //-------------------------------------------------// 




    //checks if the slimes position is close to player "alerts" slime when close to
    public void checkStatus(Player player){

        if(this.alert){
            //move towards last point saw player
            move(lastPlayerPos[0], lastPlayerPos[1], this.speed);
        }else{
            //inside eyesight radius, check to see if slime can see player with LOS
            if(calcLOS(player) <= eyesight){
                this.alert = checkAlert(player);
            }else{{
                    if(this.getxPos() == lastPlayerPos[0] && this.getyPos() == lastPlayerPos[1] ){ // check if slime reached last point saw of LOS
                        System.out.println("lost player LOS");
                        this.alert = false;
                    }
                }
            }
        }
        this.angry = checkAnger();


        if(this.health < 5){
            //slime is below half health
            this.angry = true;
        }

    }

    //checks if the players position (with its width and height) is inside the radius of the eyesight of the slime
    public boolean checkAlert(Player player){
        int slimeX = this.getxPos();
        int slimeY = this.getyPos();

        int eyesight = this.getEyesight();

        int playerX = player.getxPos();
        int playerY = player.getyPos();
        
        int differenceX = slimeX - playerX;
        int differenceY = slimeY - playerY;

        if(slimeX - playerX == 0){
            differenceX = 1;
        }

        if((differenceY)/(differenceX) > eyesight){
            System.out.println((differenceY)/(differenceX));
            return true;
        }else{
            return false;
        }
    }

    public boolean checkAnger(){
        if(this.health < 5){
            return true;
        }else{
            return false;
        }
    }

    public void update(Player player){
        //give now
        //update tick
    }
}