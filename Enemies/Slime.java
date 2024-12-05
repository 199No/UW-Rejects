package Enemies;


//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 

import src.Player;
import java.awt.Rectangle;
import java.util.Random;

//-------------------------------------------------//
//                   Enemies                       //
//-------------------------------------------------// 
public class Slime extends Enemies{
    ///////////////
    //Properties
    ///////////////

    int maxDistance = 100; //max distance a slime would hop idle-ly
    int MAX = 3000; // + 2000 in miliseconds
    int lastDash;
    Random rnd = new Random();
    int rndLength;
    int startDash;
    boolean chasing;
    double[] randomLoc;
    Vector direction;

    ///////////////
    //Constuctor
    //////////////
    public Slime(double x, double y){
        super();
        this.health = 10;
        this.damage = 1;
        this.speed = 3;
        this.width = 24;
        this.height = 24;
        this.eyesight = 400;
        this.alert = false;
        this.idle = true;
        this.chasing = false;
    }

    //-------------------------------------------------//
    //                    Methods                      //
    //-------------------------------------------------// 


    
    public void takeDamage(int dmg){

    }

    public void idleMove(){



        /* 
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

                System.out.println("target (" + (int) targetX + "," + (int) targetY + ")");
                this.direction = new Vector(offsetX, offsetY);
                //this.randomLoc = new double[]{targetX,targetY};
                
            }
        }else if(this.idleMovement && isDashing){
            dashToward(direction);
        }
        */
    }

    public void attack(Vector direction){

    }

    public void runAway(Vector direction){
        
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

            if(!checkObstaclesLOS(direction)){ //check if theres obstacles in the way of LOS // returns true is there is an obstacle
                if(!idle){
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
        direction.normalize(1);

        //returns true if there is an obstacle
        return false;
    }

    public double calcDistance(Player player) {
        double dx = this.getxPos() - player.getxPos();
        double dy = this.getyPos() - player.getyPos();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void moveToward(double[] lastSeen){


        /* 
        //move toward the point that was given
         // Target position
         double targetX = lastSeen[0];
         double targetY = lastSeen[1];
 
         // Create a direction vector
         Vector direction = new Vector(targetX - this.getxPos(), targetY - this.getyPos());
 
         // Normalize the direction vector
         Vector normalizedDirection = direction.normalize( (int) speed);
 
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
        */
    }

    public void dashToward(Vector direction){
 

        /* 
         // Normalize the direction vector
         Vector normalizedDirection = direction.normalize( (int) dashLength);
 
         // Scale the normalized vector by the speed
         double deltaX = normalizedDirection.getxPos();
         double deltaY = normalizedDirection.getyPos();
 
         // Update current position
         this.setxPos(getxPos() + deltaX);
         this.setyPos(getyPos() + deltaY);
         System.out.println("(" + (int) this.getxPos() + "," + (int) this.getyPos() + ")");
         System.out.println("(" + (int) direction.getxPos() + "," + (int) direction.getyPos() + ")");
         if(isNumberInRange((int) xPos, 30, (int) direction.getxPos()) && isNumberInRange((int) yPos, 30, (int) direction.getyPos())){
            System.out.println("Slime reached target!");
            this.chasing = false;
         }
        */
    }

    public static boolean isNumberInRange(int number, int range, int target) {
        return target >= (number - range) && target <= (number + range);
    }
}