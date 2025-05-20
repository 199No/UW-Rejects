package Enemies;
import src.Entity;

import java.util.ArrayList;
import src.Player;
import java.awt.Rectangle;

public abstract class Monster extends Entity {

    double speed;
    double range;
    State state;

    boolean alive = true;

    enum State {
        idle,
        chasing,
        dead
    }

    Player target = null;
    int now;
    int lastNewDirection;


    public Monster(double x, double y, double width, double height, Rectangle hitbox){
        super(x,y,width,height,hitbox);
        state = State.idle;
    }

    public void update(ArrayList<Player> players){
        this.now = (int) System.currentTimeMillis();
        int eyesight = 200;
        switch(state){
            case idle:
                System.out.println("Idle!");
                idle();
                break;
            case chasing:
                System.out.println("Chasing!");
                chase(players);
                break;
            case dead:
                //System.out.println("dead!");
                break;
        }
        if(inRange(players, eyesight) && this.state != State.dead){
            if(target == null || now - lastNewDirection > 5000){
                findTarget(players);
            }
            this.state = State.chasing;
        }
        else if(!inRange(players, eyesight) && this.state != State.dead){
            this.state = State.idle;
        }
    }

    public void updateState(State state){
        this.state = state;
    }

    public boolean inRange(ArrayList<Player> players, int eyesight){
        for(int i = 0; i < players.size(); i++){
            // Calculate the distance to the player's location
            double distance = calcDistance(players.get(i));
            
            if(distance <= eyesight){
                return true;
            }
        }
        return false;
    }

    public void idle(){
        int interval = now - lastNewDirection;
        if(interval > 5000){
            //TODO: find a random direction / point to move to
            lastNewDirection = now;
        }
    }

    public void findTarget(ArrayList<Player> Players){
        Player firstplayer = Players.get(0);
        Player secondplayer = Players.get(1);
        double distance1 = calcDistance(firstplayer);
        double distance2 = calcDistance(secondplayer);
        if(distance1 >= distance2){
            target = firstplayer;
        }else{
            target = secondplayer;
        }
    }

    public double calcDistance(Player player){
        double dx = player.getX() - this.getX();
        double dy = player.getY() - this.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }


    //TODO: Figure out how to use vectors to advantage
    public void chase(ArrayList<Player> Players){

    }

    public void die(){
        this.alive = false;
    }

    public boolean getAlive(){
        return this.alive;
    }

    /* 
    public BufferedImage getShadowImage(){
        return Gui.toShadow(getImage());
    }
    */
}
