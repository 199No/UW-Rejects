import javax.swing.JPanel;

public class Player {

    private JPanel JPanel;
    double x, y, xVel, yVel;
    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        xVel = 0;
        yVel = 0;
    }
    public void updatePosition(){
        x += xVel;
        y += yVel;
        xVel *= 0.99;
        yVel *= 0.99;
    }
    public void setJPanel(JPanel panel){
        this.JPanel = panel;
    }

    // <
    public void moveLeftWalk(){
        xVel -= 0.1;
    }

    // >
    public void moveRightWalk(){
        xVel += 0.1;
    }

    // shift + A
    public void moveLeftRun(){
        xVel -= 0.2;
    }

    //shift + D
    public void moveRightRun(){
        xVel += 0.2;
    }

    // W
    public void moveUpWalk(){
        yVel -= 0.1;
    }

    // S
    public void moveDownWalk(){
        yVel += 0.1;
    }

    // shift + W
    public void moveUpRun(){
        yVel -= 0.2;
    }
    
    // shift + S
    public void moveDownRun(){
        yVel += 0.2;
    }

    // space
    public void attack(){

    }
    public JPanel getJPanel() {
        return JPanel;
    }
    public double x() {
        return x;
    }
    public double y() {
        return y;
    }
    

}
