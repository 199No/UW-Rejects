package Enemies;

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
    public void moveTowardsPlayer(int playerX, int playerY){
        if(this.xPos < playerX){
            xPos++;
        }else if(this.xPos > playerX){
            xPos--;
        }
        if(this.yPos > playerY){
            yPos--;
        }else if(this.yPos < playerY){
            yPos++;
        }
    }
}