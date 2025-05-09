package Enemies;

//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 

import src.Player;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.ArrayList;
import src.Images;
import src.Sounds;
import src.Animation;
import src.Gui;
//-------------------------------------------------//
//                   Enemies                       //
//-------------------------------------------------// 

public class Slime extends Enemies {
    ///////////////
    // Properties
    ///////////////
    int maxDistance = 100; // Max distance a slime will hop idly
    int MAX_IDLE_COOLDOWN = 3000; // Cooldown in milliseconds
    int MIN_IDLE_COOLDOWN = 1000;
    long lastIdleMoveTime;
    boolean chasingPlayer = false;
    Player targetPlayer = null;
    double[] randomLoc = {0, 0};
    boolean moving = false;
    private Random rnd = new Random(); // Add a Random instance

    private final int width = Gui.TILE_SIZE;
    private final int height = Gui.TILE_SIZE;
    Animation slimeAnimation;

    ///////////////
    // Constructor
    ///////////////
    public Slime(double x, double y, double width, double height, Rectangle hitbox) {
        super(x, y, width, height, hitbox);
        this.health = 100;
        this.damage = 10;
        this.speed = 2;
        this.eyesight = 400;
        this.alert = false;
        this.idle = true;
        
        // Define a constantly running Animation for the slime (soon to be better)
        slimeAnimation = new Animation(new Images("Images/Enemies", Transparency.BITMASK).getImage("slime"), 4, 2, 7, 100, true);
        slimeAnimation.start(); 
    }

    public Slime(double x, double y, boolean isAlive) {
        super(x, y, Gui.TILE_SIZE, Gui.TILE_SIZE, new Rectangle((int) x, (int) y, Gui.TILE_SIZE, Gui.TILE_SIZE) );
        this.health = 100;
        this.damage = 10;
        this.speed = 2;
        this.eyesight = 400;
        this.alert = false;
        this.idle = true;
        this.isAlive = isAlive;

        
        // Define a constantly running Animation for the slime (soon to be better)
        slimeAnimation = new Animation(new Images("Images/Enemies", Transparency.BITMASK).getImage("slime"), 4, 2, 7, 100, true);
        slimeAnimation.start(); 
    }

    //-------------------------------------------------//
    //                    Methods                      //
    //-------------------------------------------------// 

    public void takeDamage(int dmg) {
        Sounds.playSound("SlimeAttack");
        this.health -= dmg;
        if (this.health <= 0) {
            die(); // Thy end is now!
        }
    }

    public void idleMove() {
        long currentTime = System.currentTimeMillis();

        // Wait before moving again
        if (!moving && (currentTime - lastIdleMoveTime) > MAX_IDLE_COOLDOWN && (currentTime - lastIdleMoveTime) < MIN_IDLE_COOLDOWN) {
            // Generate a random location within maxDistance
            double offsetX = (rnd.nextDouble() * 2 - 1) * maxDistance;
            double offsetY = (rnd.nextDouble() * 2 - 1) * maxDistance;
            randomLoc[0] = this.xPos + offsetX;
            randomLoc[1] = this.yPos + offsetY;

            // Clamp to game bounds
            randomLoc[0] = Math.max(0, Math.min(randomLoc[0], Gui.WIDTH - this.width));
            randomLoc[1] = Math.max(0, Math.min(randomLoc[1], Gui.HEIGHT - this.height));

            moving = true;
            lastIdleMoveTime = currentTime;
        }

        // Move towards the random location
        if (moving) {
            moveToward(randomLoc);
        }
    }

    public boolean scanArea(Player player) {
        double dx = player.getX() - this.xPos;
        double dy = player.getY() - this.yPos;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance <= this.eyesight) {
            this.alert = true;
            this.targetPlayer = player;
            return true;
        }
        return false;
    }

    public void attack() { // move towards player
        if (targetPlayer != null) {
            double[] playerLoc = {targetPlayer.getX(), targetPlayer.getY()};
            moveToward(playerLoc);
            Sounds.playSound("SlimeBejiggle");


            // Check if player is out of range
            double dx = targetPlayer.getX() - this.xPos;
            double dy = targetPlayer.getY() - this.yPos;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance > this.eyesight) {
                // Lose sight of the player
                this.alert = false;
                this.targetPlayer = null;
            }
        }
    }

    public void moveToward(double[] targetLoc) {
        double dx = targetLoc[0] - this.xPos;
        double dy = targetLoc[1] - this.yPos;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance <= speed) {
            // Reached target
            this.xPos = targetLoc[0];
            this.yPos = targetLoc[1];
            moving = false;
        } else {
            // Move closer to the target
            this.xPos += (dx / distance) * speed;
            this.yPos += (dy / distance) * speed;
        }
    }

    @Override
    public void update(ArrayList<int[]> playerLocations) {
        // Check all players to see if one is within eyesight
        for (int[] playerLocation : playerLocations) {
            if (scanArea(playerLocation)) {
                chasing = true;
                break;
            }
        }

        // Handle movement based on state
        if (chasing) {
            moveToward(this.getLastSeen());
        } else {
            idleMove();
        }
    }

    @Override
    public boolean scanArea(int[] playerLocation){
        // Calculate the distance to the player's location
        double dx = playerLocation[0] - this.xPos;
        double dy = playerLocation[1] - this.yPos;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Check if the player is within eyesight
        if (distance <= this.eyesight) {
            this.alert = true;
            this.lastSeen = new double[]{playerLocation[0], playerLocation[1]}; // Update last seen location
            return true;
        } else {
            this.alert = false;
            return false;
        }
    }

    public void die() {
        Sounds.playSound("SlimeDeath");
        this.isAlive = false;
    }

    public BufferedImage getImage(){
        return slimeAnimation.getFrame();
    }
    public BufferedImage getShadowImage(){
        return Gui.toShadow(getImage());
    }
}