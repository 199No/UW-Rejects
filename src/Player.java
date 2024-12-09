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
    }

//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 


   public void move(boolean[] movement){
        //0 W || I
        //1 A || J
        //2 S || K
        //3 D || L

        xDir = 0;
        yDir = 0;

        if(movement[0]) yDir -= 1;
        if(movement[1]) xDir -= 1;
        if(movement[2]) yDir += 1;
        if(movement[3]) xDir += 1;

        this.xPos = xPos + (xDir * speed);
        this.yPos = yPos + (yDir * speed);

   }

    public  void attack(){
        System.out.println("attack!");
    }

    public void block(){
        System.out.println("block!");
    }

    public void dash(){
        System.out.println("dash! (player)");
    }

    public  void moveX(){
       //given a speed? 
    }

    public  void moveY(){
        //given a speed?
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

    public Rectangle getHitbox(){
        return new Rectangle(getWidth()/2, getHeight()/2, (int) getxPos() + getWidth(), (int) getyPos() + getHeight());
    }

}