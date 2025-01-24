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
    private int facingDirX = 1; // 1 = right, -1 = left, 0 = no horizontal facing
    private int facingDirY = 0; // 1 = down, -1 = up, 0 = no vertical facing

    private final int width = Gui.TILE_SIZE;
    private final int height = Gui.TILE_SIZE;

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
    
    //Hitbox
    private boolean active; //the player is able to be hit if true
    private int[] topLeft; //top left of the hitbox
    private Rectangle hitbox = new Rectangle(getWidth()/2, getHeight()/2, (int) getxPos() + getWidth(), (int) getyPos() + getHeight());

    public int swingWidth  = 40;
    public int swingHeight = Gui.TILE_SIZE * 2;
    private Rectangle swingHitbox = new Rectangle( swingWidth, swingHeight,  (int) getxPos(),  (int) getyPos() );
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
        this.xPos += this.xVel * speed;
        this.yPos += this.yVel * speed;
    }

    public void attack() {

        this.isAttacking = true;
        lastAttack = (int) System.currentTimeMillis();
        System.out.println(lastAttack);
        System.out.println("attack!");
    
        // Call the method to spawn a hitbox based on the player's direction
        spawnHitbox();
    }
    
    public void spawnHitbox() {
        int hitboxX = 0;
        int hitboxY = 0;
    
        // Calculate hitbox position based on facing direction
        if (facingDirX == 1) { // Facing right
            hitboxX = (int) getxPos(); // + getWidth();
            hitboxY = (int) getyPos(); // + getHeight() / 2 - swingHeight / 2;
        } else if (facingDirX == -1) { // Facing left
            hitboxX = (int) getxPos(); // - swingWidth;
            hitboxY = (int) getyPos(); //+ getHeight() / 2 - swingHeight / 2;
        } else if (facingDirY == -1) { // Facing up
            hitboxX = (int) getxPos(); // + getWidth() / 2 - swingWidth / 2;
            hitboxY = (int) getyPos(); // - swingHeight;
        } else if (facingDirY == 1) { // Facing down
            hitboxX = (int) getxPos(); //+ getWidth() / 2 - swingWidth / 2;
            hitboxY = (int) getyPos(); //+ getHeight();
        }
    
        // Create a new hitbox
        swingHitbox = new Rectangle((int) getxPos(), (int) getyPos(), swingWidth, swingHeight);
    
        System.out.println("Hitbox spawned at (" + hitboxX + ", " + hitboxY + ")");
    }

    public void block(){
        System.out.println("block!");
    }

    public void dash(int key, int speed) {
        boolean[] movement = new boolean[4];
        if (key >= 0 && key < movement.length) {
            movement[key] = true;
        }
        applyMovement(movement);
        updatePosition(speed);
    }

    public double getxPos(){
        return this.xPos;
    }

    public void setxPos(double xPos){
        this.xPos = xPos;
    }

    public double getyPos(){
        return this.yPos;
    }

    public void setyPos(double yPos){
        this.yPos = yPos;
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
    
    public int[] getLocation(){
        return new int[] {(int) this.xPos, (int) this.yPos};
    }

    public int[] getDirection(){
        //0,2 for index when grabbing a image from a 2d array of player images
        return new int[]{xDir + 1, yDir + 1};
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

    public double[] getHitboxTopLeft(){
        return new double[]{getxPos(), getyPos()};
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

    public Rectangle getHitbox(){
        return new Rectangle((int) this.getxPos(), (int) this.getyPos(), this.getWidth(), this.getHeight());
    }

}