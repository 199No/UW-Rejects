package Enemies;

import src.Entity;
import src.Player;

import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class Enemies extends Entity {

    protected enum State {
        idle,
        chasing,
        dead
    }

    protected State state = State.idle;
    protected boolean alive = true;

    protected double dx = 0;
    protected double dy = 0;

    protected Player target = null;
    protected int now;
    protected int lastNewDirection;

    protected Rectangle hitbox;

    // stats
    protected double health;
    protected double damage;
    protected double speed;
    protected double range;

    public Enemies(double x, double y, double width, double height, Rectangle hitbox) {
        super(x, y, width, height, hitbox);
        this.hitbox = hitbox;
    }

    public void update(ArrayList<Player> players) {
        this.now = (int) System.currentTimeMillis();

        switch(state){
            case idle:
                //System.out.println("Idle!");
                idle();
                break;
            case chasing:
                //System.out.println("Chasing!");
                chase(players);
                break;
            case dead:
                //System.out.println("dead!");
                break;
        }

        if (inRange(players, this.range) && state != State.dead) {
            if (target == null || now - lastNewDirection > 5000) {
                findTarget(players);
            }
            this.state = State.chasing;
        } else if (!inRange(players, this.range) && state != State.dead) {
            this.state = State.idle;
        }
    }

    public void updateState(State state) {
        this.state = state;
    }

    public boolean inRange(ArrayList<Player> players, double eyesight){
        for(int i = 0; i < players.size(); i++){
            // Calculate the distance to the player's location
            double distance = calcDistance(players.get(i));
            
            if(distance <= eyesight){
                return true;
            }
        }
        return false;
    }

    protected double calcDistance(Player player) {
        double dx = player.getX() - this.getX();
        double dy = player.getY() - this.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void takeDamage(double damage) {
        this.health -= damage;
        if (this.health <= 0) {
            die();
            state = State.dead;
        }
    }

    public boolean getIsAlive() {
        return this.alive;
    }

    public double getDamage(){
        return this.damage;
    }

    // Abstract methods to be implemented by subclasses
    public abstract void idle();
    public abstract void findTarget(ArrayList<Player> players);
    public abstract void chase(ArrayList<Player> players);
    public abstract void move();
    public abstract void die();
    public abstract java.awt.image.BufferedImage getImage();
}