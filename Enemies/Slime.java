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
    
    ///////////////
    //Constuctor
    //////////////
    public Slime(){
        super();
        this.health = 10;
        this.damage = 1;
        this.speed = 10;
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
        this.alert = checkAlert(player);
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

    public void update(){
        //give now
        //update tick
    }
}