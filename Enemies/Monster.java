package Enemies;

public class Monster {

    double speed;
    double range;
    State state;

    boolean alive = true;

    enum State {
        idle,
        chasing,
        dead
    }

    public Monster(){
        state = State.idle;
    }

    public void update(){
        switch(state){
            case idle:
                System.out.println("Idle!");
                break;
            case chasing:
                System.out.println("Chasing!");
                break;
            case dead:
                System.out.println("dead!");
                break;
        }
        if(inRange() && this.state != State.dead){
            this.state = State.chasing;
        }
        else if(!inRange() && this.state != State.dead){
            this.state = State.idle;
        }
    }

    public void updateState(State state){
        this.state = state;
    }
    //TODO: Figure out if plater is in range
    public boolean inRange(){
        return false;
    }

    public void idle(){

    }
    //TODO: Figure out how to use vectors to advantage
    public void chase(){

    }

    public void die(){
        this.alive = false;
    }

    public boolean getAlive(){
        return this.alive;
    }
    
}
