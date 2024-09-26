import javax.swing.JPanel;

public class Player {

    private JPanel JPanel;
    double x, y, xVel, yVel;
    int xDir;
    int yDir;
    boolean isRunning;
    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        xVel = 0;
        yVel = 0;
        xDir = -1;
        yDir = -1;
        isRunning = false;
    }
    public void updatePosition(){
        x += xVel;
        y += yVel;
        xVel *= 0.98;
        yVel *= 0.98;
    }
    public void setJPanel(JPanel panel){
        this.JPanel = panel;
    }
    
    // <
    public void moveLeft(boolean run){
        xVel -= (run)? 0.2 : 0.1;
        xDir = -1;
        isRunning = run;
    }
    
    public void moveRight(boolean run){
        xVel += (run)? 0.2 : 0.1;
        xDir = 1;
        isRunning = run;
    }
    
    public void moveUp(boolean run){
        yVel -= (run)? 0.2 : 0.1;
        yDir = -1;
        isRunning = run;
    }
    
    public void moveDown(boolean run){
        yVel += (run)? 0.2 : 0.1;
        yDir = 1;
        isRunning = run;
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
    public double xVel(){
        return xVel;
    }
    public double yVel(){
        return yVel;
    }
    public int xDir(){
        return xDir;
    }
    public int yDir(){
        return yDir;
    }
    public void setRunning(boolean run){
        isRunning = run;
    }

}
