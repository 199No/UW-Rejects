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
        this.xPos = 500;
        this.yPos = 500;
        System.out.println("Enemies!");
    }

    //-------------------------------------------------//
    //                    Methods                      //
    //-------------------------------------------------// 


    //moves towards player by 1 tile (both x and y each)
    //does this when alerted
    public void move(Player player){
        if(this.alert){
            //moving towards player
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
        }else{
            //move randomly (not moving for slime)
        }
    }

    //checks if the slimes position is close to player "alerts" slime when close to
    public void checkSlimeStatus(Player player){
        int slimeX = this.getxPos();
        int slimeY = this.getyPos();
        int playerX = player.getxPos();
        int playerY = player.getyPos();
        int eyeSight = 20;
        this.alert = false;

        //check to see if slime is alert

        if( Math.abs((slimeX - playerX)/(slimeY - playerY)) < eyeSight || Math.abs((playerX - slimeX)/(playerY - slimeY)) < eyeSight){
            move(player);
        }else{
            //move randomly or dont move at all
        }



        /*



         if(slime alert){
            moveTowardsPlayer{
         }else{
            move randomly OR dont move at all
         }
         */

        if(this.health < 5){
            //slime is below half health
            this.angry = true;
        }

    }
}