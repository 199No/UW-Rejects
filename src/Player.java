package src;

import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;

import Enemies.Enemies;

//-------------------------------------------------//
//                    Player Class                 //
//-------------------------------------------------// 
public class Player extends Entity {

    ///////////////
    // Properties
    ///////////////

    // Core stats
    private int health;
    private int damage;
    private double speed = 3.0;
    private double dashSpeed = 9.0;
    private double maxSpeed = 5.0;
    private int maxHealth = 100;

    // Movement variables
    private double xVel = 0;
    private double yVel = 0;
    private int xDir = 1; // -1 (left), 0 (neutral), 1 (right)
    private int yDir = 0; // -1 (Up),   0 (neutral), 1 (down)

    // Misc
    private double friction = 0.65;
    public int playerNum;
    public static final int invincibilityDuration = 2000; // in milliseconds

    // Attack
    private int attackCooldown = 500; // milliseconds
    private int attackLength = 100; // milliseconds
    private boolean isAttacking;
    private int lastAttack = (int) System.currentTimeMillis();

    // Block
    private int blockCooldown = 5000; // milliseconds
    private int blockLength = 1000; // milliseconds
    private boolean isBlocking;
    private int lastBlock;

    // Dash
    private int dashCooldown = 5000; // milliseconds
    private int dashLength = 350 + 250 + 350; // milliseconds
    private boolean isDashing = false;
    private int lastDash = (int) System.currentTimeMillis() - 10;

    // Swing hitbox
    private boolean active;
    public int swingWidth  = Gui.TILE_SIZE * 2;
    public int swingHeight = Gui.TILE_SIZE;
    private Rectangle swingHitbox = new Rectangle(swingWidth, swingHeight, (int) getX(), (int) getY());

    // Animations
    StatefulAnimation idleAnim;
    StatefulAnimation dashAnimation;
    StatefulAnimation swingAnimation;

    // Input tracking
    int[] playerKeys;
    int[] lastKeyPressTimes;

    ///////////////
    // Constructor
    ///////////////
    public Player(double x, double y, int hp, int dmg, int playerNum) {
        super(x, y, Gui.TILE_SIZE, Gui.TILE_SIZE, new Rectangle(Gui.TILE_SIZE/4, Gui.TILE_SIZE/4, Gui.TILE_SIZE / 2, Gui.TILE_SIZE / 2));
        
        // Load animations
        idleAnim = new StatefulAnimation(Integer.MAX_VALUE, 2, 2, new int[][]{{0}, {1}, {2}, {3}}, new Images("Images", Transparency.BITMASK).getImage("player" + playerNum + "Idle"), true);
        dashAnimation = new StatefulAnimation(62, 6, 2, new int[][]{
            {0,1,2,3}, {4,5}, {4,5}, {4,3,2,1},
            {6,7,8,9}, {10,11}, {10,11}, {9,8,7,6}
        }, new Images("Images", Transparency.BITMASK).getImage("player" + playerNum + "Dash"), true);

        // Assign keys based on player number
        if(playerNum == 1)
            playerKeys = new int[]{ KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D };
        else if(playerNum == 2)
            playerKeys = new int[]{ KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L };
        else
            playerKeys = new int[4];

        lastKeyPressTimes = new int[4];
        this.playerNum = playerNum;
        this.health = hp;
        this.damage = dmg;
    }

    //-------------------------------------------------//
    //                  Core Methods                   //
    //-------------------------------------------------//

    // Handle player movement
    public void updateMovement(boolean[] pressedKeys, boolean[] shifts){
        //if(shifts[playerNum - 1]

        // Modify velocity based on input and dash
        if (pressedKeys[playerKeys[0]]) yVel -= (isDashing ? dashSpeed : speed); // Up
        if (pressedKeys[playerKeys[1]]) xVel -= (isDashing ? dashSpeed : speed); // Left
        if (pressedKeys[playerKeys[2]]) yVel += (isDashing ? dashSpeed : speed); // Down
        if (pressedKeys[playerKeys[3]]) xVel += (isDashing ? dashSpeed : speed); // Right

        // Update position
        x += xVel;
        y += yVel;

        // Apply friction
        xVel *= friction;
        yVel *= friction;

        // Cap velocity
        xVel = Math.max(-maxSpeed, Math.min(xVel, maxSpeed));
        yVel = Math.max(-maxSpeed, Math.min(yVel, maxSpeed));

        // Update direction
        xDir = (int)Math.signum(xVel);
        yDir = (int)Math.signum(yVel);
    }

    // Handle collision with enemies and environment
    public void updateCollision(ArrayList<Enemies> enemies, ArrayList<EnvObject> EnvObjects) {
        Rectangle playerHitbox = getAbsHitbox();

        // Enemy collision
        for (Enemies enemy : enemies) {
            if (playerHitbox.intersects(enemy.getAbsHitbox())) {
                long now = System.currentTimeMillis();
                if (now - enemy.getLastHit() > Player.invincibilityDuration) {
                    this.health -= enemy.getDamage();
                    enemy.setLastHit(now);
                }
            }
        }

        // Environment collision (placeholder logic)
        for (EnvObject obj : EnvObjects) {
            if (playerHitbox.intersects(obj.getAbsHitbox())) {
                Rectangle2D clip = obj.getAbsHitbox().createIntersection(playerHitbox);
                // Add more advanced resolution logic if needed
            }
        }

        // Swing hitbox interaction with enemies
        for (Enemies enemy : enemies) {
            if (swingHitbox.intersects(enemy.getAbsHitbox())) {
                Rectangle2D clip = swingHitbox.createIntersection(enemy.getAbsHitbox());
                if (clip.getHeight() > clip.getWidth()) {
                    setX(swingHitbox.getX() > enemy.getAbsHitbox().getX() ? getX() + clip.getWidth() : getX() - clip.getWidth());
                } else {
                    setY(swingHitbox.getY() > enemy.getAbsHitbox().getY() ? enemy.getAbsHitbox().getY() + enemy.getAbsHitbox().getHeight() : enemy.getAbsHitbox().getY() - swingHitbox.getHeight());
                }
            }
        }

        // Despawn swing hitbox
        if ((int) System.currentTimeMillis() - getLastAttack() > getAttackLength()) {
            swingHitbox.setBounds(-1000, -1000, 1, 1); // Move off-screen
        }

        // Keep player within bounds
        inBounds();
    }

    // Update attack state
    public void updateAttack() {
        boolean[] pressedKeys = Input.getKeys();
        int[] playerKeys = Input.getPlayerKeys(this);
        int currentTime = (int) System.currentTimeMillis();

        if (!getIsAttacking()) {
            if (currentTime - getLastAttack() > getAttackLength()) {
                if (pressedKeys[playerKeys[5]] && !getIsAttacking() && !getIsBlocking() && currentTime - getLastAttack() > getAttackCooldown()) {
                    attack();
                }
                setIsAttacking(true);
            }
        } else if (currentTime - getLastAttack() > getAttackLength()) {
            setIsAttacking(false);
        }
    }

    // Update block state
    public void updateBlock() {
        boolean[] pressedKeys = Input.getKeys();
        int[] playerKeys = Input.getPlayerKeys(this);
        int currentTime = (int) System.currentTimeMillis();

        if (!getIsBlocking()) {
            if (currentTime - getLastBlock() > getBlockLength()) {
                if (pressedKeys[playerKeys[4]] && !getIsBlocking() && !getIsAttacking() && currentTime - getLastBlock() > getBlockCooldown()) {
                    block();
                }
                setIsBlocking(true);
            }
        } else if (currentTime - getLastBlock() > getBlockLength()) {
            setIsBlocking(false);
        }
    }

    // Dash logic
    public void dash(int key) {
        boolean[] movement = new boolean[4];
        if (key >= 0 && key < movement.length) {
            movement[key] = true;
        }

        // Only dash in one direction
        if (movement[0]) { yVel += -dashSpeed; xVel = 0; } // Up
        else if (movement[1]) { xVel += -dashSpeed; yVel = 0; } // Left
        else if (movement[2]) { yVel += dashSpeed; xVel = 0; } // Down
        else if (movement[3]) { xVel += dashSpeed; yVel = 0; } // Right

        x += xVel;
        y += yVel;

        xDir = (int)Math.signum(xVel);
        yDir = (int)Math.signum(yVel);
    }

    // Trigger an attack and create hitbox
    public void attack() {
        System.out.println("Attack!");
        lastAttack = (int) System.currentTimeMillis();

        int hitboxX = (int) getX();
        int hitboxY = (int) getY();

        if (xDir == 1) hitboxX += swingWidth;
        else if (xDir == -1) hitboxX -= swingWidth;
        else if (yDir == -1) hitboxY -= swingHeight;
        else if (yDir == 1) hitboxY += swingHeight;

        swingHitbox = new Rectangle(hitboxX, hitboxY, swingWidth, swingHeight);
        System.out.println("Player position: (" + (int) getX() + ", " + (int) getY() + ")");
        System.out.println("Hitbox spawned at: (" + hitboxX + ", " + hitboxY + ")");
    }

    // Trigger a block and disable hits
    public void block() {
        setActive(false);
        System.out.println("Block!");
        if (isBlocking) {
            Sounds.playSound("Blocking");
        }
        lastBlock = (int) System.currentTimeMillis();
    }

    // Keep player inside game bounds
    public void inBounds() {
        if (getX() > Game.xMax) setX(Game.xMax);
        if (getX() < Game.xMin) setX(Game.xMin);
        if (getY() > Game.yMax) setY(Game.yMax);
        if (getY() < Game.yMin) setY(Game.yMin);
    }

    ///////////////
    // Getters/Setters
    ///////////////

    // Movement direction
    public int getXDir() { return (xVel > 0) ? 1 : (xVel < 0) ? -1 : 0; }
    public int getYDir() { return (yVel > 0) ? 1 : (yVel < 0) ? -1 : 0; }
    public int[] getDirection() { return new int[]{xDir + 1, yDir + 1}; }

    // Location
    public double[] getLocation() { return new double[]{getX(), getY()}; }

    // Health
    public double getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    public double getMaxHealth() { return maxHealth; }
    public double getHealthPercent() { return (double)health / maxHealth; }

    // Speed
    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }
    public double getMaxSpeed() { return maxSpeed; }

    // Damage
    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }

    // Attack
    public boolean getIsAttacking() { return isAttacking; }
    public int getLastAttack() { return lastAttack; }
    public int getAttackLength() { return attackLength; }
    public int getAttackCooldown() { return attackCooldown; }
    public void setIsAttacking(boolean bool) { isAttacking = bool; }

    // Block
    public boolean getIsBlocking() { return isBlocking; }
    public int getLastBlock() { return lastBlock; }
    public int getBlockLength() { return blockLength; }
    public int getBlockCooldown() { return blockCooldown; }
    public void setIsBlocking(boolean bool) { isBlocking = bool; }

    // Dash
    public boolean getIsDashing() { return isDashing; }
    public int getLastDash() { return lastDash; }
    public int getDashLength() { return dashLength; }
    public int getDashCooldown() { return dashCooldown; }
    public void setIsDashing(boolean bool) {
        isDashing = bool;
        if (bool) Sounds.playSound("Roll");
    }

    // Hitbox
    public boolean getActive() { return active; }
    public void setActive(boolean bool) { active = bool; }
    public Rectangle getSwingHitbox() { return swingHitbox; }
    public double[] getSwingHitboxTopLeft() { return new double[]{swingHitbox.getX(), swingHitbox.getY()}; }

    // Get current player image for rendering
    public BufferedImage getImage() {
        BufferedImage playerImage = idleAnim.getCurFrame();
        if (isDashing) {
            // Optional: use dashAnimation.getCurFrame()
        }
        return playerImage;
    }

      public BufferedImage getShadowImage(){BufferedImage playerImage = idleAnim.getCurFrame(); // Default idle animation frame

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
        return Gui.toShadow(playerImage);
    }
}
