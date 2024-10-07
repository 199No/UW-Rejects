package Enemies;
import src.Player;

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
        this.xPos = 1200;
        this.yPos = 500;
        System.out.println("Enemies!");
    }

    //-------------------------------------------------//
    //                    Methods                      //
    //-------------------------------------------------// 

    //moves towards player by 1 tile (both x and y each)
    //does this when alerted
    public void moveTowardsPlayer(Player player){
        if(this.xPos < player.getxPos()){
            xPos++;
        }else if(this.xPos > player.getxPos()){
            xPos--;
        }
        if(this.yPos > player.getyPos()){
            yPos--;
        }else if(this.yPos < player.getyPos()){
            yPos++;
        }
    }

    //checks if the slimes position is close to player "alerts" slime when close to
    public void lookForPlayer(Player player){
        int slimeX = this.getxPos();
        int slimeY = this.getyPos();
        int playerX = player.getxPos();
        int playerY = player.getyPos();
        if(slimeX < playerX){
            if(slimeY < playerY){
                //bigger playX & playY
                if(playerX - slimeX < 5 || playerY - slimeY < 5){
                    this.alert = true;
                }
            }else{
                //bigger playX & slimeY
                if(playerX - slimeX < 5 || slimeY - playerY < 5){
                    this.alert = true;
                }
            }
        }else{
            if(slimeY < playerY){
                //bigger slimeX & playerY
                if(slimeX - playerX < 5 || playerY - slimeY < 5){
                    this.alert = true;
                }
            }else{
                //bigger slimeX & slimeY
                if(slimeX - playerX < 5 || slimeY - playerY < 5){
                    this.alert = true;
                }
            }
        }

    }
}