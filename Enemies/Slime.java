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

    int dashLength = 15;
    int MAX = 3000; // + 2000 in miliseconds
    int dashAt;
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
        this.xPos = 500;
        this.yPos = 100;
        this.alert = false;
        this.idleMovement = true;
    }

    //-------------------------------------------------//
    //                    Methods                      //
    //-------------------------------------------------// 


    
    public void takeDamage(int dmg){

    }



    /*
     * idle = true && isDashing true == hopping to random location
     * idle = false && isDashing true == hopping towards player
     * idle = true && isDashing false == still
     */
    public void idleMove(){
        //move randomly, in random way
        int doesMove = rnd.nextInt(20) + 1;

        //figure out if it can dash

        if(this.idleMovement && !isDashing){ // looking for when it should move
            if(doesMove == 20){ // 1 in 5 chance
                isDashing = true;
                this.rndLength = rnd.nextInt(MAX) + 2000;
                this.startDash = (int) System.currentTimeMillis();
                this.dashAt = startDash + rndLength;
            }
        }else if(this.idleMovement && isDashing){
            //find a random location next to slime and jump towards it
            double rndX = this.xPos + rnd.nextInt(200) - 200 + 1;
            double rndY = this.yPos + rnd.nextInt(200) - 200 + 1;
            this.randomLoc = new double[]{rndX, rndY};
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
         double deltaX = normalizedDirection.getxPos() * speed;
         double deltaY = normalizedDirection.getyPos() * speed;
 
         // Update current position
         this.setxPos(getxPos() + deltaX);
         this.setyPos(getyPos() + deltaY);

         if( (int) this.getxPos() - speed <= (int) lastSeen[0] && (int) lastSeen[0] <= (int) this.getxPos() + speed && (int) this.getyPos() - speed <= (int) lastSeen[1] && (int) lastSeen[1] <= this.getyPos() + speed){ // check if slime reached last point saw of LOS
            System.out.println("stopped idle hop");
            this.isDashing = false;
        }
    }

}