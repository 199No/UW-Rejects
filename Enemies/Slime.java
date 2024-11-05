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
    public Slime(int x, int y){
        super();
        this.health = 10;
        this.damage = 1;
        this.speed = 10;
        this.width = 5;
        this.height = 5;
        this.eyesight = 400;
        this.xPos = x;
        this.yPos = y;
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

        if(this.alert){
            moveTowardsPlayer(player);
        }
    }

    public void moveTowardsPlayer(Player player){
        int playx = player.getxPos();
        int playy = player.getyPos();


        int y = this.getyPos();
        int x = this.getxPos();

        if(playx >= x){
            //move right
            this.xPos++;
        }
        if(playx <= x){
            //move left
            this.xPos--;
        }
        if(playy >= y){
            //move down
            this.yPos++;
        }
        if(playy <= y){
            //move up
            this.yPos--;
        }
    }

    
    public boolean checkAlert(Player player){
        
        int playx = player.getxPos();
        int playy = player.getyPos();


        int y = this.getyPos();
        int x = this.getxPos();

        // x    play   +this.getwidth         x - this.getwidth playx x    
        if(x <= playx && playx <= x + this.eyesight || x - this.eyesight <= playx && playx <= x){
            if(y <= playy && playy <= y + this.eyesight || y - this.eyesight <= playy && playy <= y ){
                return true;
            }
        }
        return false;


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