package Enemies;

import src.Player;
import src.Images;
import src.Sounds;
import src.Animation;
import src.Gui;

import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Slime is a type of enemy with simple AI behaviors: idle wandering and chasing players.
 * It uses animated sprites and can deal damage when in range.
 */
public class Slime extends Enemies {

    private Animation slimeAnimation; // Handles slime's animation frames

    /**
     * Constructor to initialize the slime enemy at a given position.
     */
    public Slime(double x, double y) {
        super(x, y, Gui.TILE_SIZE, Gui.TILE_SIZE, new Rectangle((int)x, (int)y, Gui.TILE_SIZE, Gui.TILE_SIZE));

        // Set stats
        this.health = 100.0;
        this.damage = 10.0;
        this.speed = 3.0;
        this.range = 250.0;

        // Load slime animation (4 columns, 2 rows, 7 frames, 100ms delay, looped)
        this.slimeAnimation = new Animation(
            new Images("Images/Enemies", Transparency.BITMASK).getImage("slime"),
            4, 2, 7, 100, true
        );
        this.slimeAnimation.start(); // Start the animation
    }

    /**
     * Update slime state each frame.
     */
    @Override
    public void update(ArrayList<Player> players) {
        super.update(players);           // Perform any shared enemy update logic
        slimeAnimation.getFrame();       // Update the animation frame
    }

    /**
     * Handles idle wandering behavior.
     * Slime randomly picks a direction every few seconds or stays still.
     */
    @Override
    public void idle() {
        now = (int) System.currentTimeMillis(); // Ensure current time is set
        int interval = (int)(now - lastNewDirection);
        double idleSpeed = speed * 0.5;

        // Change direction every 2–5 seconds
        if (interval > 2000 + Math.random() * 3000) {
            double chance = Math.random();
            if (chance < 0.5) {
                dx = 0;
                dy = 0; // 50% chance to stop moving
            } else {
                double angle = Math.random() * 2 * Math.PI;
                dx = Math.cos(angle);
                dy = Math.sin(angle);

                // Normalize vector and apply speed
                double length = Math.sqrt(dx * dx + dy * dy);
                if (length != 0) {
                    dx = (dx / length) * idleSpeed;
                    dy = (dy / length) * idleSpeed;
                }
            }
            lastNewDirection = now; // Update time for next direction change
        }

        move(); // Apply movement
    }

    /**
     * Find the closest player within range and set as target.
     */
    @Override
    public void findTarget(ArrayList<Player> players) {
        if (players.isEmpty()) return;

        Player closest = null;
        double closestDistance = Double.MAX_VALUE;

        // Loop through players to find the closest one
        for (Player player : players) {
            double distance = calcDistance(player);
            if (distance < closestDistance) {
                closest = player;
                closestDistance = distance;
            }
        }

        target = closest; // Set the closest player as the target
    }

    /**
     * Move toward the target player using vector direction.
     */
    @Override
    public void chase(ArrayList<Player> players) {
        if (target == null) return;

        double tx = target.getX();
        double ty = target.getY();
        double dirX = tx - this.getX();
        double dirY = ty - this.getY();

        // Normalize direction vector and apply speed
        double length = Math.sqrt(dirX * dirX + dirY * dirY);
        if (length != 0) {
            dx = (dirX / length) * speed;
            dy = (dirY / length) * speed;
        } else {
            dx = 0;
            dy = 0;
        }

        move(); // Apply movement
    }

    /**
     * Apply movement vector to slime's position.
     */
    @Override
    public void move() {
        this.setX(this.getX() + dx);
        this.setY(this.getY() + dy);
    }

    /**
     * Handle slime death: play sound and mark as not alive.
     */
    @Override
    public void die() {
        Sounds.playSound("SlimeDeath");
        this.alive = false;
    }

    /**
     * Get the current animation frame of the slime for rendering.
     */
    @Override
    public BufferedImage getImage() {
        return slimeAnimation.getFrame();
    }

    /**
     * Get a shadow version of the slime's current image for rendering.
     */
    @Override
    public BufferedImage getShadowImage() {
        return Gui.toShadow(getImage());
    }
}
