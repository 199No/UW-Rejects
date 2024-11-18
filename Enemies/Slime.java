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
    }

    //-------------------------------------------------//
    //                    Methods                      //
    //-------------------------------------------------// 


    
    public void takeDamage(int dmg){

    }

    public void idleMove(){
        //move randomly, in random way
        setxPos(getxPos() + 1);
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
                this.alert = true;
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

}