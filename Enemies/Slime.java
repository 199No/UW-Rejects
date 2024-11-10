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
    private int[] dashTowards = new int[2]; // dash towards
    private long lastDash;
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
    }

    //-------------------------------------------------//
    //                    Methods                      //
    //-------------------------------------------------// 




    //checks if the slimes position is close to player "alerts" slime when close to
    public void checkStatus(Player player){
        if(this.alert){
            //move towards last point saw player
            move(lastPlayerPos[0], lastPlayerPos[1], this.speed);
        }
        //inside eyesight radius, check to see if slime can see player with LOS
        if(calcDistance(player) <= eyesight){
            this.alert = checkAlert(player);
        }else{
            if(this.getxPos() == lastPlayerPos[0] && this.getyPos() == lastPlayerPos[1] ){ // check if slime reached last point saw of LOS
                System.out.println("lost player LOS");
                this.alert = false;
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
        //pre condition player is insidde radius and alert wasnt already true

        Vector direction = new Vector(player.getxPos() - this.getxPos(), player.getyPos() - this.getyPos());

        // Normalize the direction vector (make it unit length)
        direction = direction.normalize();

        if(checkLOS(direction)){ //check if theres obstacles in the way of LOS
            return false;
        }else{
            lastPlayerPos = new int[]{(int) player.getxPos(), (int) player.getyPos()};
            return true;
        }
    }

    public boolean checkLOS(Vector direction){
        //at every point of vector check if there is an obstacle

        //returns true if there is an obstacle
        return false;
    }

    public double calcDistance(Player player) {
        double dx = this.getxPos() - player.getxPos();
        double dy = this.getyPos() - player.getyPos();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void move(int x, int y, int speed){

        int difference = (int) System.currentTimeMillis() - (int) this.lastDash;
        
        if(difference > 0 && difference >= 3000){
            //do nothing
        }else if(difference > 3000 && difference <= 5000){
            //move towards player
        }else if(difference > 5000 && difference <= 6000){
            //do nothing
        }else if(difference > 6000 && difference <= 10000){
            //dashing (speed 3x)
        }else if(difference > 10000) {
            //do nothing
            System.out.println("reset attack!");
            lastDash = System.currentTimeMillis();
        }


    }

    public boolean checkAnger(){
        if(this.health < 5){
            return true;
        }else{
            return false;
        }
    }

}