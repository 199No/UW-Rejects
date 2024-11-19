package Enemies;


//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 

import src.Player;
import java.util.Random;

//-------------------------------------------------//
//                   Enemies                       //
//-------------------------------------------------// 
public class Slime extends Enemies{
    ///////////////
    //Properties
    ///////////////

    double dashLength = 2.0; // dash speed factor
    int maxDistance = 100; //max distance a slime would hop idle-ly
    int MAX = 3000; // + 2000 in miliseconds
    int lastDash;
    Random rnd = new Random();
    int rndLength;
    int startDash;
    boolean isDashing = false;
    double[] randomLoc;

    ///////////////
    //Constuctor
    //////////////
    public Slime(){
        super();
        this.health = 10;
        this.damage = 1;
        this.speed = 3;
        this.width = 5;
        this.height = 5;
        this.eyesight = 400;
        this.xPos = 700;
        this.yPos = 500;
        this.alert = false;
        this.idleMovement = true;
        this.isDashing = false;
    }

    //-------------------------------------------------//
    //                    Methods                      //
    //-------------------------------------------------// 


    
    public void takeDamage(int dmg){

    }

    public void idleMove(){
        //move randomly, in random way
        int doesMove = rnd.nextInt(100) + 1; // 1 in 100 if slime does an idle movement

        if(this.idleMovement && !isDashing){ // looking for when it should move
            if(doesMove == 1){ // 1 in 100 chance
                isDashing = true;
                lastDash = (int) System.currentTimeMillis();
                double offsetX = (rnd.nextDouble() * 2 - 1) * maxDistance; // Range: -maxDistance to maxDistance
                double offsetY = (rnd.nextDouble() * 2 - 1) * maxDistance; // Range: -maxDistance to maxDistance

                // Calculate the target coordinates
                double targetX = this.getxPos() + offsetX;
                double targetY = this.getyPos() + offsetY;
                this.randomLoc = new double[]{targetX,targetY};
            }
        }else if(this.idleMovement && isDashing){
            dashToward(randomLoc);
        }
    }

    public void attack(Vector direction){

    }

    public void die(){
        //play death animation
        //remove from list of enemies
        //remove from everything
        //give xp???
    }


    public boolean isAlive(){
        if(this.health <= 0){
        
            return false;
        }else{
            return true;
        }
    }

    //checks to see if the slime can see the player, LOS of player with obstacles, if it can sets the alert to true
    public boolean scanArea(Player player){ // scans for player around slime, returns true if slime can see the player
        //check to see if player is even inside the radius of the eyesight of the slime
        if(calcDistance(player) < eyesight){
            //the player is inside the circle
            Vector direction = new Vector(player.getxPos() - this.getxPos(), player.getyPos() - this.getyPos());
            //Normalize the direction vector (make it unit length)
            //direction = direction.normalize();
            if(!checkObstaclesLOS(direction)){ //check if theres obstacles in the way of LOS // returns true is there is an obstacle
                if(!idleMovement){
                    this.alert = true;
                }
                this.lastSeen = new double[]{player.getxPos(), player.getyPos()};
                return true;
            }
        }
        return false;
    }

    public void update(){  // sets and checks all parts of the enemy (ran every frame)

    }

    public boolean checkObstaclesLOS(Vector direction){
        //at every point of vector check if there is an obstacle

        //returns true if there is an obstacle
        return false;
    }

    public double calcDistance(Player player) {
        double dx = this.getxPos() - player.getxPos();
        double dy = this.getyPos() - player.getyPos();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void moveToward(double[] lastSeen){
        //move toward the point that was given
         // Target position
         double targetX = lastSeen[0];
         double targetY = lastSeen[1];
 
         // Create a direction vector
         Vector direction = new Vector(targetX - this.getxPos(), targetY - this.getyPos());
 
         // Normalize the direction vector
         Vector normalizedDirection = direction.normalize();
 
         // Scale the normalized vector by the speed
         double deltaX = normalizedDirection.getxPos() * speed;
         double deltaY = normalizedDirection.getyPos() * speed;
 
         // Update current position
         this.setxPos(getxPos() + deltaX);
         this.setyPos(getyPos() + deltaY);

         if( (int) this.getxPos() - speed <= (int) lastSeen[0] && (int) lastSeen[0] <= (int) this.getxPos() + speed && (int) this.getyPos() - speed <= (int) lastSeen[1] && (int) lastSeen[1] <= this.getyPos() + speed){ // check if slime reached last point saw of LOS
            System.out.println("lost player LOS");
            this.alert = false;
        }
    }

    public void dashToward(double[] lastSeen){
        //move toward the point that was given
         // Target position
         double targetX = lastSeen[0];
         double targetY = lastSeen[1];
 
         // Create a direction vector
         Vector direction = new Vector(targetX - this.getxPos(), targetY - this.getyPos());
 
         // Normalize the direction vector
         Vector normalizedDirection = direction.normalize();
 
         // Scale the normalized vector by the speed
         double deltaX = normalizedDirection.getxPos() * speed * dashLength;
         double deltaY = normalizedDirection.getyPos() * speed * dashLength;
 
         // Update current position
         this.setxPos(getxPos() + deltaX);
         this.setyPos(getyPos() + deltaY);
         if(isNumberInRange((int) xPos, (int) speed * (int) dashLength, (int) lastSeen[0]) && isNumberInRange((int) yPos, (int) speed * (int) dashLength, (int) lastSeen[1])){
            System.out.println("Slime reached target!");
            this.isDashing = false;
         }
    }

    public static boolean isNumberInRange(int number, int range, int target) {
        return target >= (number - range) && target <= (number + range);
    }
}