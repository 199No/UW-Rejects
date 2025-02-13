package src;

import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.concurrent.TransferQueue;

import javax.swing.plaf.nimbus.State;

//-------------------------------------------------//
//                    Player                       //
//-------------------------------------------------// 
public class Player extends Entity{
    ///////////////
    //Properties
    //////////////
    private int health;
    private int armor;
    private int defence;
    private int damage;
    private double speed;
    private double maxSpeed;


    //positioning
    private double xVel;
    private double yVel;
    private int xDir; // -1 (left), 0 (neutral), 1 (right)
    private int yDir; // -1 (Up),   0 (neutral), 1 (down)
    private int facingDirX = 1; // 1 = right, -1 = left, 0 = no horizontal facing
    private int facingDirY = 0; // 1 = down, -1 = up, 0 = no vertical facing

    private final double width = Gui.TILE_SIZE;
    private final double height = Gui.TILE_SIZE;

    //misc
    private int temperature;
    private int score;
    private double friction;
    public int playernum;

    //Attack
    private int attackCooldown; // in miliseconds
    private int attackLength = 100; // in miliseconds
    private boolean isAttacking; //is swinging
    private int lastAttack = (int) System.currentTimeMillis();

    //Block
    private int blockCooldown; // in miliseconds
    private int blockLength; // in miliseconds
    private boolean isBlocking;

    //Dash
    private int dashCooldown; // in miliseconds
    public int dashLength   = 250 + 500 + 250; // in miliseconds
    private boolean isDashing;
    private boolean wasDashing;
    
    //Hitbox
    private boolean active; //the player is able to be hit if true
    public int swingWidth  = Gui.TILE_SIZE * 2;
    public int swingHeight = Gui.TILE_SIZE * 2;
    private Rectangle swingHitbox = new Rectangle( swingWidth, swingHeight,  (int) getX(),  (int) getY() );

    //Animation
    StatefulAnimation idleAnim;
    StatefulAnimation dashAnimation;
    ///////////////
    //Constuctor
    //////////////
    public Player(double x, double y, int hp, int d, int dmg, double s, int playernum) {
        super(x, y, Gui.TILE_SIZE, Gui.TILE_SIZE, new Rectangle(Gui.TILE_SIZE/4, Gui.TILE_SIZE/4, Gui.TILE_SIZE / 2, Gui.TILE_SIZE / 2));
        idleAnim      = new StatefulAnimation(Integer.MAX_VALUE, 2, 2, new int[][]{{0}, {1}, {2}, {3}}, new Images("Images", Transparency.BITMASK).getImage("player" + playernum + "Idle"), true);
        dashAnimation = new StatefulAnimation(62, 6, 2, 
                            new int[][] {
                                {0,1,2,3}, {4,5}, {4,5}, {4,3,2,1}, // Rightwards dash
                                {6,7,8,9}, {10,11}, {10,11}, {9,8,7,6} // Leftwards dash
                            }, 
                            new Images("Images", Transparency.BITMASK).getImage("player" + playernum + "Dash"), true);
        System.out.println("Player!");

        this.playernum = playernum;


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
        if(isShifting){
            updatePosition(1.5 * this.speed);
        }else{
            updatePosition(this.speed);
        }
    }

    // Apply movement based on the movement array
    private void applyMovement(boolean[] movement) {
        xDir = getXDir();
        yDir = getYDir();
        if (movement[0]) this.yVel -= this.speed; // Up
        if (movement[1]) this.xVel -= this.speed; // Left
        if (movement[2]) this.yVel += this.speed; // Down
        if (movement[3]) this.xVel += this.speed; // Right

        if (xDir != 0) {
            facingDirX = xDir;
            facingDirY = 0; // Prioritize horizontal facing
        } else if (yDir != 0) {
            facingDirY = yDir;
            facingDirX = 0; // Vertical facing
        }
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
    private void updatePosition(double speed) {
        x += this.xVel * speed;
        y += this.yVel * speed;
    }

    public void attack() {
        //Sounds.playSound("SwordAttack");
        this.isAttacking = true;
        lastAttack = (int) System.currentTimeMillis();
        System.out.println(lastAttack);
        // System.out.println("attack!");
    
        // Call the method to spawn a hitbox based on the player's direction
        spawnHitbox();
    }
    
    public void spawnHitbox() {
        int hitboxX = 0;
        int hitboxY = 0;
    
        // Calculate hitbox position based on facing direction
        if (facingDirX == 1) { // Facing right
            hitboxX = (int) getX(); // + getWidth();
            hitboxY = (int) getY(); // + getHeight() / 2 - swingHeight / 2;
        } else if (facingDirX == -1) { // Facing left
            hitboxX = (int) getX(); // - swingWidth;
            hitboxY = (int) getY(); //+ getHeight() / 2 - swingHeight / 2;
        } else if (facingDirY == -1) { // Facing up
            hitboxX = (int) getX(); // + getWidth() / 2 - swingWidth / 2;
            hitboxY = (int) getY(); // - swingHeight;
        } else if (facingDirY == 1) { // Facing down
            hitboxX = (int) getX(); //+ getWidth() / 2 - swingWidth / 2;
            hitboxY = (int) getY(); //+ getHeight();
        }
    
        // Create a new hitbox
        swingHitbox = new Rectangle((int) getX(), (int) getY(), swingWidth, swingHeight);
    
        // System.out.println("Player positon at (" + (int) getX() + ", " + (int) getY() );
        // System.out.println("Hitbox spawned at (" + hitboxX + ", " + hitboxY + ")");
    }
   
    public void block(){
        System.out.println("block!");
    }

    public void dash(int key, int speed) {
        //Sounds.playSound("Roll");
        boolean[] movement = new boolean[4];
        if (key >= 0 && key < movement.length) {
            movement[key] = true;
        }
        applyMovement(movement);
        updatePosition(speed);
    }

    public int getXDir(){
        if(xVel > 0){
            return 1;
        }else if(xVel < 0){
            return -1;
        }else{
            return 0;
        }
    }

    public int getYDir(){
        if(yVel > 0){
            return 1;
        }else if(yVel < 0){
            return -1;
        }else{
            return 0;
        }
    }

    public int[] getDirection(){
        //0,2 for index when grabbing a image from a 2d array of player images
        return new int[]{xDir + 1, yDir + 1};
    }

    public double[] getLocation(){
        return new double[]{getX(),getY()};
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
    public boolean getIsAttacking(){
        return this.isAttacking;
    }
    public boolean getIsBlocking(){
        return this.isBlocking;
    }
    public int getLastAttack(){
        return this.lastAttack;
    }
    public int getAttackLength(){
        return this.attackLength;
    }
    public int getAttackCooldown(){
        return this.attackCooldown;
    }
    public void setIsAttacking(boolean bool){
        this.isAttacking = bool; 
    }
    public double getSpeed(){
        return speed;
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }

    public double[] getSwingHitboxTopLeft(){
        return new double[]{this.swingHitbox.getX(), this.swingHitbox.getY()};
    }

    public Rectangle getSwingHitbox(){
        return this.swingHitbox;
    }
    public int getDamage(){
        return this.damage;
    }   
    public BufferedImage getImage(){
        BufferedImage playerImage = idleAnim.getCurFrame(); // Default idle animation frame

        // Handle dash animation
        if(isDashing){
                               // time since last dash 
            int currentState = (int)(((int)System.currentTimeMillis() - Input.getLastDash(this)) / 250);
            
            if(xDir == -1){
                currentState += 4;
            }

            if(dashAnimation.getCurState() != currentState) dashAnimation.setState(currentState);

            playerImage = dashAnimation.getCurFrame();
        }
        else /* if player is not dashing */{
            if (yDir == -1) {
                // Player is facing up
                if (xDir == -1) {
                    // Moving left while facing up
                    idleAnim.setState(3);
                } else if (xDir == 1) {
                    // Moving right while facing up
                    idleAnim.setState(2);
                } else {
                    // Default to right-facing up when no horizontal movement
                    idleAnim.setState(3);
                }
            } else {
                // Player is not facing up
                if (xDir == -1) {
                    // Moving left
                    idleAnim.setState(1);
                } else if (xDir == 1) {
                    // Moving right
                    idleAnim.setState(0);
                } else {
                    // Default to idle right-facing when no movement
                    idleAnim.setState(0);
                }
            }
            playerImage = idleAnim.getCurFrame();
        }
        return playerImage;
    }

}