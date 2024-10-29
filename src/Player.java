package src;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import javax.swing.JPanel;

//-------------------------------------------------//
//                    Player                       //
//-------------------------------------------------// 
public class Player {
    ///////////////
    //Properties
    //////////////
    private JPanel JPanel;
    private int health;
    private int damage;
    private int speed;
    private int xPos;
    private int yPos;
    private final int width = 38;
    private final int height = 38;
    private boolean[] direction = new boolean[4];
    private int xDir;
    ///////////////
    //Constuctor
    //////////////
    public Player() {
        System.out.println("Player!");
        health = 100;
        damage = 1;
        xPos = 0;
        yPos = 0;
        this.direction[0] = true;
        xDir = 1;
    }

//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 
    public int getxPos(){
        return this.xPos;
    }

    public int getyPos(){
        return this.yPos;
    }

    public int[] getLocation(){
        return new int[] {this.xPos,this.yPos};
    }

    public void setDirection(int direction){
        this.direction = new boolean[4];
        this.direction[direction] = true; // 1w 2a 3s 4d
    }

    public int getDirection(){
        for(int i = 0; i < this.direction.length; i++){
            if(this.direction[i] = true){
                return i;
            }
        }
        return -1;
    }

    public void setJPanel(JPanel panel){
        this.JPanel = panel;
    }

    // A
    public void moveLeftWalk(){
        xDir = -1;
        xPos -= 2;
    }

    // D
    public void moveRightWalk(){
        xDir = 1;
        xPos += 2;
    }

    // shift + A
    public void moveLeftRun(){
        xDir = -1;
        xPos -= 4;
    }

    //shift + D
    public void moveRightRun(){
        xDir = 1;
        xPos += 4;
    }

    // W
    public void moveUpWalk(){
        yPos -= 2;
    }

    // S
    public void moveDownWalk(){
        yPos += 2;
    }

    // shift + W
    public void moveUpRun(){
        yPos -= 4;
    }
    
    // shift + S
    public void moveDownRun(){
        yPos += 4;
    }
    
    public void dashUp(){

    }

    public void dashDown(){
        
    }

    public void dashLeft(){
        
    }

    public void dashRight(){
        
    }

    public void teleport(int x, int y){
        xPos = x;
        yPos = y;
    }

    public void loseHealth(){
        this.health--;
    }
    public void checkPlayer(){
        if(this.health == 0){
            System.out.println("IM DEAD");
        }
    }

    // space
    public void attack(){
        //use damage variable
        System.out.println("Space Pressed: Attack! " + this.damage);
    }

    public int getXDir(){
        return xDir;
    }
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
}