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

    //multipliers
    private double damageMultiplier;
    private double speedMultiplier;

    //positioning
    private double xPos;
    private double yPos;
    private final int width = 24;
    private final int height = 24;

    //misc
    private int temperature;
    private int score;

    private ArrayList<Item> inventory;

    //XP
    private int level;
    private int XP;
    private double XPMultiplier;
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
    private Rectangle hitbox;

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

    private int xDir; // -1 (left), 0 (neutral), 1 (right)
    private int yDir; // -1 (Up),   0 (neutral), 1 (down)
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

        this.level = 0;
        this.XP = 0;

        this.maxMana = 1000;
        this.XPToNextLevel = 20; //levels increase by xpnextlevel *= 1.2
        this.attackCooldown = 500; // ↓↓ in miliseconds
        this.dashCooldown = 5000;
        this.blockCooldown = 5000;

        this.xDir = 0;
        this.yDir = 0;

        this.isDashing = false;
        this.isBlocking = false;
        this.isAttacking = false;
        this.inventory = new ArrayList<Item>();
    }

//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 


    // TODO: Remove these useless move___walk/spint, and dash methods and make 1 move method
    // A
    public void moveLeftWalk(){
        if(isDashing){
            xPos -= speed * 0.1;
        }else{
            xPos -= speed;
        }
        xDir = -1;
    }

    // D
    public void moveRightWalk(){
        if(isDashing){
            xPos += speed * 0.1;
        }else{
            xPos += speed;
        }
        xDir = 1;
    }

    // shift + A
    public void moveLeftRun(){
        if(isDashing){
            xPos -= speed * 0.2;
        }else{
            xPos -= (speed * 1.25);
        }
        xDir = -1;
    }

    //shift + D
    public void moveRightRun(){
        if(isDashing){
            xPos += speed * 0.2;
        }else{
            xPos += (speed * 1.25);
        }
        xDir = 1;
    }

    // W
    public void moveUpWalk(){
        if(isDashing){
            yPos -= speed * 0.1;
        }else{
            yPos -= speed;
        }
        yDir = 1;
    }

    // S
    public void moveDownWalk(){
        if(isDashing){
            yPos += speed * 0.1;
        }else{
            yPos += speed;
        }
        yDir = -1;
    }

    // shift + W
    public void moveUpRun(){
        if(isDashing){
            yPos -= speed * 0.2;
        }else{
            yPos -= (speed * 1.25);
        }
        yDir = 1;
    }
    
    // shift + S
    public void moveDownRun(){
        if(isDashing){
            yPos += speed * 0.2;
        }else{
            yPos += (speed * 1.25);
        }
        yDir = -1;
    }

    public void dashRight(double speed){
        xPos += speed;
        xDir = 1;
    }

    public void dashLeft(double speed){
        xPos -= speed;
        xDir = -1;
    }

    public void dashUp(double speed){
        yPos -= speed;
        yDir = 1;
    }

    public void dashDown(double speed){
        yPos += speed;
        yDir = -1;
    }

/* 
    public abstract void attack();

    public abstract void moveX(); //given a speed?

    public abstract void moveY(); //given a speed?

    public abstract void dash(); //given a direction?
*/
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

    public double[] getHitboxTopLeft(){
        return new double[]{xPos+this.getWidth()/4, yPos+this.getHeight()/4};
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

}