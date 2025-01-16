package src;
import java.util.ArrayList;
import java.awt.Rectangle;

//-------------------------------------------------//
//                    Player                       //
//-------------------------------------------------// 
public class Player {
    ///////////////
    //Properties
    //////////////
    private int health;
    private int armor;
    private int defence;
    private int damage;
    private double speed;
    private double maxSpeed;

    //multipliers
    private double damageMultiplier;
    private double speedMultiplier;
    private double XPMultiplier;

    //positioning
    private double xPos;
    private double yPos;
    private double xVel;
    private double yVel;
    private int xDir; // -1 (left), 0 (neutral), 1 (right)
    private int yDir; // -1 (Up),   0 (neutral), 1 (down)

    private final int width = Gui.TILE_SIZE;
    private final int height = Gui.TILE_SIZE;

    //misc
    private int temperature;
    private int score;
    private double friction;

    //XP
    private int level;
    private int XP;
    private int XPToNextLevel;

    //Attack
    private int attackCooldown; // in miliseconds
    private int attackLength; // in miliseconds
    private boolean isAttacking; //is swinging

    //Block
    private int blockCooldown; // in miliseconds
    private int blockLength; // in miliseconds
    private boolean isBlocking;

    //Dash
    private int dashCooldown; // in miliseconds
    private int dashLength   = 350 + 500 + 350; // in miliseconds
    private boolean isDashing;
    
    //Hitbox
    private boolean active; //the player is able to be hit if true
    private int[] topLeft; //top left of the hitbox
    private Rectangle hitbox = new Rectangle(getWidth()/2, getHeight()/2, (int) getxPos() + getWidth(), (int) getyPos() + getHeight());

    //Mage
    private int mana;
    private int maxMana;
    private double manaRegenRate;

    //Animation

    /*         xDir     yDir
    UP	        0	    -1

    UP-RIGHT	1	    -1

    RIGHT	    1	     0

    DOWN-RIGHT	1	     1

    DOWN	    0	     1

    DOWN-LEFT -1	     1 

    LEFT	   -1	     0

    UP-LEFT   -1	    -1 
     */

    ///////////////
    //Constuctor
    //////////////
    public Player(double x, double y, int hp, int d, int dmg, double s) {
        System.out.println("Player!");

        this.xPos = x;
        this.yPos = y;

        this.health = hp;
        this.defence = d;
        this.damage = dmg;
        this.speed = s;

        this.maxSpeed = 2 * this.speed * this.speed;
        this.level = 0;
        this.XP = 0;

        this.maxMana = 10000; // "mana" of 10
        this.XPToNextLevel = 20; // levels increase by xpnextlevel *= 1.1
        this.attackCooldown = 500; // ↓↓ in miliseconds
        this.dashCooldown = 5000; // 5 sec
        this.blockCooldown = 5000; // 5 sec

        this.xDir = 0;
        this.yDir = 0;
        this.xVel = 0;
        this.yVel = 0;
        this.friction = 0.99; // 0 - 1 closer to 1 is less friction

        this.isDashing = false;
        this.isBlocking = false;
        this.isAttacking = false;
    }

//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 


    public void move(boolean[] movement, boolean isShifting){
        //0 W || I
        //1 A || J
        //2 S || K
        //3 D || L

        //xVel + going right - going left
        //given that figure out if it starts going left or is going right
        // max out xVel at a specific point ( -(s^2), (s^2) )

        double tempXVel = 0;
        double tempYVel = 0;

        if(movement[0]){
            tempYVel -= (this.speed);
        }

        if(movement[1]){
            tempXVel -= (this.speed);
        }

        if(movement[2]){
            tempYVel += (this.speed);
        }

        if(movement[3]){
            tempXVel += (this.speed);
        }

        this.xVel += tempXVel;
        this.yVel += tempYVel;

        this.xVel *= this.friction;
        this.yVel *= this.friction;

        if(Math.abs(this.xVel) > this.maxSpeed){
            if(Math.abs(this.xVel) > this.maxSpeed){
                if(this.xVel < 0){
                    this.xVel = -this.maxSpeed;
                }else{
                    this.xVel = this.maxSpeed;
                }
            }
        }

        if(Math.abs(this.xVel) > this.maxSpeed){
            if(this.xVel < 0){
                this.xVel = -this.maxSpeed;
            }else{
                this.xVel = this.maxSpeed;
            }
        }
            

        this.xPos += xVel;
        this.yPos += yVel;



        /* 
        int xDirection = 0;
        int yDirection = 0;

        if(movement[0]){ yDirection -= 1; }
        if(movement[1]){ xDirection -= 1; }
        if(movement[2]){ yDirection += 1; }
        if(movement[3]){ xDirection += 1; }
        
        double mag = Math.sqrt(this.xVel * this.xVel  + this.yVel * this.yVel);

        if(mag > 0){

            double tempxVel = this.xVel;
            double tempyVel = this.yVel;

            tempxVel /= mag;
            tempyVel /= mag;

            this.xVel += xDirection * tempxVel * (this.speed);
            this.yVel += yDirection * tempyVel * (this.speed);
            
        }else{

            this.xVel *= this.friction;
            this.yVel *= this.friction;
            
            
            //make sure there isnt always friction
            if(Math.abs(this.xVel) < 0.01) this.xVel = 0;
            if(Math.abs(this.yVel) < 0.01) this.yVel = 0;
            
            
        }
        

         
        if(Math.abs(this.xVel) > this.maxSpeed){
            if(this.xVel < 0){
                this.xVel = -this.maxSpeed;
            }else{
                this.xVel = this.maxSpeed;
            }
        }

        if(Math.abs(this.yVel) > this.maxSpeed){ 
            if(this.yVel < 0){
                this.yVel = -this.maxSpeed;
            }else{
                this.yVel = this.maxSpeed;
            }
        }
        */
        

        //this.xPos += this.xVel;
        //this.yPos += this.yVel;

    }


    public  void attack(){
        System.out.println("attack!");
    }

    public void block(){
        System.out.println("block!");
    }

    public void dash(){
        //System.out.println("dash! player " + this);
    }

    public double getxPos(){
        return this.xPos;
    }

    public double getyPos(){
        return this.yPos;
    }

    public void teleport(int x, int y){
        xPos = x;
        yPos = y;
    }

    public double[] getLocation(){
        return new double[] {this.xPos,this.yPos};
    }

    public int[] getDirection(){
        //0,2 for index when grabbing a image from a 2d array of player images
        return new int[]{xDir + 1, yDir + 1};
    }

    public int getXDir(){
        return xDir;
    }
    
    public int getYDir(){
        return yDir;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public int getDashCooldown(){
        return this.dashCooldown;
    }

    public boolean getIsDashing(){
        return this.isDashing;
    }

    public void setIsDashing(boolean temp){
        this.isDashing = temp;
    }

    public boolean getActive(){
        return this.active;
    }

    public int getDashLength(){
        return this.dashLength;
    }

    public double getSpeed(){
        return speed;
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }

    public double[] getHitboxTopLeft(){
        return new double[]{getxPos(), getyPos()};
    }

    public Rectangle getHitbox(){
        return new Rectangle((int) this.getxPos(), (int) this.getyPos(), this.getWidth(), this.getHeight());
    }

}