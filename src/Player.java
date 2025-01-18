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
    public int playernum;

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
    public int dashLength   = 350 + 500 + 350; // in miliseconds
    private boolean isDashing;
    private int lastDash;
    
    //Hitbox
    private boolean active; //the player is able to be hit if true
    private int[] topLeft; //top left of the hitbox
    private Rectangle hitbox = new Rectangle(getWidth()/2, getHeight()/2, (int) getxPos() + getWidth(), (int) getyPos() + getHeight());

    ///////////////
    //Constuctor
    //////////////
    public Player(double x, double y, int hp, int d, int dmg, double s, int playernum) {
        System.out.println("Player!");

        this.playernum = playernum;

        this.xPos = x;
        this.yPos = y;

        this.health = hp;
        this.defence = d;
        this.damage = dmg;
        this.speed = s;

        this.maxSpeed = 2 * this.speed * this.speed;

        this.attackCooldown = 500; // ↓↓ in miliseconds
        this.dashCooldown = 5000; // 5 sec
        this.blockCooldown = 5000; // 5 sec

        this.xDir = 0;
        this.yDir = 0;
        this.xVel = 0;
        this.yVel = 0;
        this.friction = 0.75; // 0 - 1 closer to 1 is less friction

        this.isDashing = false;
        this.isBlocking = false;
        this.isAttacking = false;
    }

//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 


    public void move(boolean[] movement, boolean isShifting) {
        applyMovement(movement);
        applyFriction();
        capVelocity();
        updatePosition(isShifting ? 0.125 : 1); // 1/8 = 0.125
    }

    public void move(int num, int factor) {
        boolean[] movement = new boolean[4];
        if (num >= 0 && num < movement.length) {
            movement[num] = true;
        }
        applyMovement(movement);
        applyFriction();
        capVelocity();
        updatePosition(factor);
    }

    // Apply movement based on the movement array
    private void applyMovement(boolean[] movement) {
        if (movement[0]) this.yVel -= this.speed; // Up
        if (movement[1]) this.xVel -= this.speed; // Left
        if (movement[2]) this.yVel += this.speed; // Down
        if (movement[3]) this.xVel += this.speed; // Right
    }

    // Apply friction to the velocities
    private void applyFriction() {
        this.xVel *= this.friction;
        this.yVel *= this.friction;
    }

    // Cap the velocities to the maximum speed
    private void capVelocity() {
        this.xVel = Math.max(-this.maxSpeed, Math.min(this.xVel, this.maxSpeed));
        this.yVel = Math.max(-this.maxSpeed, Math.min(this.yVel, this.maxSpeed));
    }

    // Update the player's position based on velocity and a scaling factor
    private void updatePosition(double scale) {
        this.xPos += this.xVel * scale;
        this.yPos += this.yVel * scale;
    }


    public  void attack(){
        System.out.println("attack!");
    }

    public void block(){
        System.out.println("block!");
    }

    public void dash(int i){
        // i is the index of the player keys
        int dl = dashLength;
        //if is dashing is not true, that is the first time player is dashing (aka the start)
        if(!isDashing){
            //this.lastDash = (int) System.currentTimeMillis();
        }else{
            move(i, 8);
        }


        System.out.println("dash! player " + this);
    }

    public double getxPos(){
        return this.xPos;
    }

    public double getyPos(){
        return this.yPos;
    }
    
    /* 
    public void teleport(int x, int y){
        xPos = x;
        yPos = y;
    }

    public double[] getLocation(){
        return new double[] {this.xPos,this.yPos};
    }
    */

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

    public double getLastDash(){
        return lastDash;
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