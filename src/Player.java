package src;

import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

//-------------------------------------------------//
//                    Player                       //
//-------------------------------------------------// 
public class Player extends Entity{
    ///////////////
    //Properties
    //////////////
    private int health;
    private int damage;
    private double speed = 3.0;
    private double shiftSpeed = 6.0;
    private double dashSpeed = 12.0;
    private double maxSpeed = 5.0;
    private int maxHealth = 100;

    //positioning
    private double xVel = 0;
    private double yVel = 0;
    private int xDir = 0; // -1 (left), 0 (neutral), 1 (right)
    private int yDir = 0; // -1 (Up),   0 (neutral), 1 (down)
    private int facingDirX = 1; // 1 = right, -1 = left, 0 = no horizontal facing
    private int facingDirY = 0; // 1 = down, -1 = up, 0 = no vertical facing

    //misc
    private int score;
    private double friction = 0.85;
    public int playernum;

    //Attack
    private int attackCooldown = 500; // in miliseconds
    private int attackLength = 100; // in miliseconds
    private boolean isAttacking; //is swinging
    private int lastAttack = (int) System.currentTimeMillis();

    //Block
    private int blockCooldown = 5000; // in miliseconds
    private int blockLength = 1000; // in miliseconds
    private boolean isBlocking;
    private int lastBlock;

    //Dash
    private int dashCooldown = 5000; // in miliseconds
    private int dashLength   = 350 + 250 + 350; // in miliseconds
    private boolean isDashing;
    private int lastDash;
    private int lastDashKey = -1;
    
    //Hitbox
    private boolean active; //the player is able to be hit if true
    public int swingWidth  = Gui.TILE_SIZE * 2;
    public int swingHeight = Gui.TILE_SIZE;
    private Rectangle swingHitbox = new Rectangle( swingWidth, swingHeight,  (int) getX(),  (int) getY() );

    //Animation
    StatefulAnimation idleAnim;
    StatefulAnimation dashAnimation;
    StatefulAnimation swingAnimation;
    ///////////////
    //Constuctor
    //////////////
    public Player(double x, double y, int hp, int dmg, int playernum) {
        super(x, y, Gui.TILE_SIZE, Gui.TILE_SIZE, new Rectangle(Gui.TILE_SIZE/4, Gui.TILE_SIZE/4, Gui.TILE_SIZE / 2, Gui.TILE_SIZE / 2));
        idleAnim      = new StatefulAnimation(Integer.MAX_VALUE, 2, 2, new int[][]{{0}, {1}, {2}, {3}}, new Images("Images", Transparency.BITMASK).getImage("player" + playernum + "Idle"), true);
        dashAnimation = new StatefulAnimation(62, 6, 2, 
                            new int[][] {
                                {0,1,2,3}, {4,5}, {4,5}, {4,3,2,1}, // Rightwards dash
                                {6,7,8,9}, {10,11}, {10,11}, {9,8,7,6} // Leftwards dash
                            }, 
                            new Images("Images", Transparency.BITMASK).getImage("player" + playernum + "Dash"), true);
        /*swingAnimation = new StatefulAnimation(62, , , 
                            new int[][] {}, 
                            new Images("Images", Transparency.BITMASK).getImage(), true);*/
        System.out.println("Player!");

        this.playernum = playernum;
        this.health = hp;
        this.damage = dmg;

    }

//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 

    public void move(boolean[] movement, boolean isShifting) {
        
        // Adjust velocity based on input
        if (movement[0]) yVel -= speed; // W (Up)
        if (movement[1]) xVel -= speed; // A (Left)
        if (movement[2]) yVel += speed; // S (Down)
        if (movement[3]) xVel += speed; // D (Right)

        // Normalize diagonal movement to prevent speed boost
        if ((movement[0] || movement[2]) && (movement[1] || movement[3])) {
            xVel *= 0.7071; // 1/sqrt(2)
            yVel *= 0.7071;
        }

        // Apply friction
        xVel *= friction;
        yVel *= friction;

        // Cap velocity
        xVel = Math.max(-maxSpeed, Math.min(xVel, maxSpeed));
        yVel = Math.max(-maxSpeed, Math.min(yVel, maxSpeed));

        // Update position
        x += xVel;
        y += yVel;
    }

    public void dash(int key) {

        boolean[] movement = new boolean[4];
        if (key >= 0 && key < movement.length) {
            movement[key] = true;
        }
            
        // Only allow movement in one direction
        if (movement[0]) { // Up
            yVel += -dashSpeed;
            xVel = 0;
        } else if (movement[1]) { // Left
            xVel += -dashSpeed;
            yVel = 0;
        } else if (movement[2]) { // Down
            yVel += dashSpeed;
            xVel = 0;
        } else if (movement[3]) { // Right
            xVel += dashSpeed;
            yVel = 0;
        }

        // Update position
        x += xVel;
        y += yVel;
    }

    public void attack() {
        System.out.println("Attack!");
        lastAttack = (int) System.currentTimeMillis();
        xDir = getXDir(); // -1 left 0 neutral 1 right
        yDir = getYDir(); // -1 up 0 neutral 1 down

        //toggle swing animation ?
        // spawn swing hitbox
        int hitboxX = (int) getX();
        int hitboxY = (int) getY();
        int swingWidth = Gui.TILE_SIZE;  // Set appropriate size
        int swingHeight = Gui.TILE_SIZE; // Set appropriate size

        // Adjust hitbox position based on direction
        if (xDir == 1) { 
            hitboxX += swingWidth; // Attack Right
        } else if (xDir == -1) { 
            hitboxX -= swingWidth; // Attack Left
        } else if (yDir == -1) { 
            hitboxY -= swingHeight; // Attack Up
        } else if (yDir == 1) { 
            hitboxY += swingHeight; // Attack Down
        }

        // Create a new hitbox
        swingHitbox = new Rectangle(hitboxX, hitboxY, swingWidth, swingHeight);

        System.out.println("Player position: (" + (int) getX() + ", " + (int) getY() + ")");
        System.out.println("Hitbox spawned at: (" + hitboxX + ", " + hitboxY + ")");

    }

    public void block() {
        setActive(false);
        System.out.println("Block!");
        lastBlock = (int) System.currentTimeMillis();
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

    //ATTACK

    public boolean getIsAttacking(){
        return this.isAttacking;
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

    //BLOCK

    public boolean getIsBlocking(){
        if(isBlocking){
            Sounds.playSound("Blocking");
        }
        return this.isBlocking;
    }
    public int getLastBlock(){
        return this.lastBlock;
    }
    public int getBlockLength(){
        return this.blockLength;
    }
    public int getBlockCooldown(){
        return this.blockCooldown;
    }
    public void setIsBlocking(boolean bool){
        this.isBlocking = bool; 
    }

    //DASH

    public boolean getIsDashing(){
        return this.isDashing;
    }
    public int getLastDash(){
        return this.lastDash;
    }
    public int getDashKey(){
        return this.lastDashKey;
    }
    public void setDashKey(int keyevent){
        this.lastDashKey = keyevent;
    }
    public int getDashLength(){
        return this.dashLength;
    }
    public int getDashCooldown(){
        return this.dashCooldown;
    }
    public void setIsDashing(boolean bool){
        this.isDashing = bool; 
        if(bool){
            Sounds.playSound("Roll");
        }
    }

    //HEALTH

    public double getHealth(){
        return health;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public double getMaxHealth(){
        return maxHealth;
    }

    //return percentage for health bar
    public double getHealthPercent(){
        return ((double)this.health/(double)this.maxHealth);
    }

    //SPEED

    public double getSpeed(){
        return speed;
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }

    public double getMaxSpeed(){
        return maxSpeed;
    }

    //DAMAGE

    public int getDamage(){
        return damage;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    //HITBOX

    public double[] getSwingHitboxTopLeft(){
        return new double[]{this.swingHitbox.getX(), this.swingHitbox.getY()};
    }

    public boolean getActive(){
        return this.active;
    }

    public void setActive(boolean bool){
        this.active = bool;
    }

    public Rectangle getSwingHitbox(){
        return this.swingHitbox;
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