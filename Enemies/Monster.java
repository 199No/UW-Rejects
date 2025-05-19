package Enemies;
import src.Entity;
import java.util.ArrayList;
import src.Player;
import java.util.Vector;
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

    private Vector<Double> velocity;

    public Monster(double x, double y, double width, double height, Rectangle hitbox){
        super(x,y,width,height,hitbox);
        state = State.idle;
        this.velocity = new Vector<>(2);
    }

    public void update(ArrayList<Player> players){
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
            double dx = players.get(i).getX() - this.getX();
            double dy = players.get(i).getY() - getY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            
            if(distance <= eyesight){
                return true;
            }
        }
        return false;
    }

    public void idle(){

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
