package Enemies;

//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 

import src.Player;
import java.awt.Rectangle;
import java.util.Random;
import java.util.ArrayList;
import src.Gui;

//-------------------------------------------------//
//                   Enemies                       //
//-------------------------------------------------// 
public class Slime extends Enemies {
    ///////////////
    // Properties
    ///////////////

    int maxDistance = 100; // Max distance a slime will hop idly
    int MAX = 3000; // Cooldown in milliseconds (+/- variation)
    int lastDash;
    Random rnd = new Random();
    int rndLength;
    int startDash;
    boolean chasing;
    double[] randomLoc = {0, 0};
    boolean moving = false;

    ///////////////
    // Constructor
    ///////////////
    public Slime(double x, double y) {
        super(x, y);
        this.health = 100;
        this.damage = 10;
        this.speed = 2;
        this.width = Gui.TILE_SIZE;
        this.height = Gui.TILE_SIZE;
        this.eyesight = 400;
        this.alert = false;
        this.idle = true;
        this.chasing = false;
    }

    //-------------------------------------------------//
    //                    Methods                      //
    //-------------------------------------------------// 

    public void takeDamage(int dmg) {
        this.health -= dmg;
        if (this.health <= 0) {
            die();
        }
    }

    // Move idly after some time
    public void idleMove() {
        long currentTime = System.currentTimeMillis();

        // Check if it's time for the slime to move again
        if (!moving && (currentTime - lastDash) > MAX) {
            // Generate a random location within maxDistance
            double offsetX = (rnd.nextDouble() * 2 - 1) * maxDistance;
            double offsetY = (rnd.nextDouble() * 2 - 1) * maxDistance;
            randomLoc[0] = this.xPos + offsetX;
            randomLoc[1] = this.yPos + offsetY;
            
            // Clamp the random location to ensure it's within the game bounds
            randomLoc[0] = Math.max(0, Math.min(randomLoc[0], Gui.WIDTH - this.width));
            randomLoc[1] = Math.max(0, Math.min(randomLoc[1], Gui.HEIGHT - this.height));

            moving = true;
            lastDash = (int) currentTime;
        }

        // If moving, continue towards the random location
        if (moving) {
            double dx = randomLoc[0] - this.xPos;
            double dy = randomLoc[1] - this.yPos;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance <= speed) {
                // Reached destination
                this.xPos = randomLoc[0];
                this.yPos = randomLoc[1];
                moving = false;
            } else {
                // Move towards destination
                this.xPos += (dx / distance) * speed;
                this.yPos += (dy / distance) * speed;
            }
        }
    }

    public void attack() {
        // Attack logic, to be implemented
    }

    public void die() {
        // Play death animation
        // Remove from the list of enemies
        // Drop loot or give XP, if applicable
    }

    public boolean scanArea(int[] location) {
        // Scan for players and set alert to true if visible
        return false;
    }

    public void update() {
        // Update slime's position and state
        if (alert) {
            moveToward(this.getLastSeen());
        } else {
            idleMove();
        }
    }

    public boolean checkObstaclesLOS() {
        // Check for obstacles in the line of sight
        return false;
    }

    public void moveToward(double[] lastSeen) {
        // Move towards the last known location of the player
        double dx = lastSeen[0] - this.xPos;
        double dy = lastSeen[1] - this.yPos;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            this.xPos += (dx / distance) * speed;
            this.yPos += (dy / distance) * speed;
        }
    }

    public void dashToward() {
        // Dash towards a location or player
    }
}
