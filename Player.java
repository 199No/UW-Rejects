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
    private final int width = 10;
    private final int height = 10;
    private boolean[] direction = new boolean[4];
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
        xPos -= 5;
    }

    // D
    public void moveRightWalk(){
        xPos += 5;
    }

    // shift + A
    public void moveLeftRun(){
        xPos -= 10;
    }

    //shift + D
    public void moveRightRun(){
        xPos += 10;
    }

    // W
    public void moveUpWalk(){
        yPos += 5;
    }

    // S
    public void moveDownWalk(){
        yPos -= 5;
    }

    // shift + W
    public void moveUpRun(){
        yPos += 10;
    }
    
    // shift + S
    public void moveDownRun(){
        yPos -= 10;
    }

    public void teleport(int x, int y){
        xPos = x;
        yPos = y;
    }

    // space
    public void attack(){
        //use damage variable
        System.out.println("Space Pressed: Attack! " + this.damage);
    }

}