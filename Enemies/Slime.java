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
        this.eyesight = 100;
        this.xPos = 500;
        this.yPos = 500;
        this.alert = false;
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

        int pWidth = player.getWidth();
        int pHeight = player.getHeight();
        
        int differenceX = slimeX - playerX;
        int differenceY = slimeY - playerY;

        if(slimeX - playerX == 0){
            differenceX = 1;
        }

        if(Math.abs((differenceY)/(differenceX)) < eyesight){
            return true;
        }else{
            return false;
        }

        /* 
        //basically asking if the player is on the right or left
        if(slimeX - (playerX + pWidth) < slimeX - (playerX - pWidth)){
            //player is on right side
            closestPlayerX = playerX + pWidth;

        }else if(slimeX - (playerX + pWidth) > slimeX - (playerX - pWidth)){
            //player is on left side
            closestPlayerX = playerX - pWidth;
        }else{
            //x's are equal;
            closestPlayerX = slimeX;
        }

        if(slimeY - (playerY + pHeight) < slimeY - (playerY - pHeight)){
            //player is above
            closestPlayerY = playerY + pHeight;
        }else if(slimeX - (playerX + pHeight) < slimeX - (playerX - pHeight)){
            //player is below
            closestPlayerY = playerY - pHeight;
        }else{
            //y's are equal;
            closestPlayerY = slimeY;
        }

        if(slimeX - closestPlayerX == 0){
            if (slimeY - closestPlayerY < eyesight){
                return true;
            }else{
                return false;
            }
        }
        */

        /* 
        if(Math.abs((slimeY - closestPlayerY)/(slimeX - closestPlayerX)) < eyesight){
            System.out.println("Inside Circle");
            return true;
        }else{
            System.out.println("Outside circle");
            return false;
        }

        */
    }
    public boolean checkAnger(){
        if(this.health < 5){
            return true;
        }else{
            return false;
        }
    }
}