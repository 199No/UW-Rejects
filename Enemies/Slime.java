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

    }

    //checks if the players is in the LOS of slime 
    public boolean checkAlert(Player player){
        //pre condition: slime is not already alerted

        Vector LOS = getDirection(player); //line of sight direction

        //check if theres no obstacles in the way of the line of sight
        if(checkObstaclesLOS(LOS)){
            return false;
        }else{
            this.lastPlayerPos = new int[]{player.getxPos(), player.getyPos()};
            return true;
        }
    }

    // Method to calculate the direction vector from slime to player
    public Vector getDirection(Player player) {
        // Subtract the slime's position from the player's position to get the direction
        return new Vector(player.getxPos() - this.getxPos(), player.getyPos() - this.getyPos());
    }

    public boolean checkObstaclesLOS(Vector direction){
        //pre condition: the player is already in the radius

        //given the direction check if there is any obstacles in the way

        return false;
    }

    public double calcLOS(Player player) {
        int dx = (int)(this.getxPos() - player.getxPos());
        int dy = (int)(this.getyPos() - player.getyPos());
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void move(int x, int y, double speed) {
        // Calculate the direction vector from slime to target
        Vector direction = new Vector(x - this.getxPos(), y - this.getyPos());

        direction.normalize();

        // Move the slime in the direction of the target by the given speed
        this.xPos += (int) direction.getxPos() * this.speed;
        this.yPos += (int) direction.getyPos() * this.speed;
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