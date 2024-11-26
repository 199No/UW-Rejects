package src;


//pressed -> released (check if short)  [100 - 350]
//time inbetween
//pressed2
//check dash will check if released and pressed2 inputs have a short break (released was a short click)
//if click1 and click2 short; dash




//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import java.awt.MouseInfo;
import java.awt.event.*;
import java.util.Arrays;
//-------------------------------------------------//
//                    Input                        //
//-------------------------------------------------// 
public class Input implements MouseListener, KeyListener, MouseMotionListener{
    ///////////////
    //Properties
    //////////////
    double lastX, lastY, mouseX, mouseY;
    boolean automatedMove;
    double movedX, movedY;
    boolean mouseLocked;
    boolean[] keys = new boolean[90];
    Player player1;
    Player player2;
    boolean shortClick;
    int latestInput; // last pressed key
    int dashDirection;
    int pressed; 
    int released;
    int lastDash;
    int INTERVAL = 350; //global time for gaps inbetween short clicks 
    ///////////////
    //Comstuctor
    //////////////
    public Input(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        lastX = MouseInfo.getPointerInfo().getLocation().getX();
        lastY = MouseInfo.getPointerInfo().getLocation().getY();
        mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        mouseY = MouseInfo.getPointerInfo().getLocation().getY();
        automatedMove = false;
        movedX = 0;
        movedY = 0;
        mouseLocked = true;
    }
    public double mouseX(){
        return mouseX;
    }
    public double mouseY(){
        return mouseY;
    }
    public void updateMouse(){
        // If the user moves the mouse...
        if(!automatedMove && mouseLocked){
            // Update last pos
            lastX = mouseX;
            lastY = mouseY;
            // Get current pos
            mouseX = MouseInfo.getPointerInfo().getLocation().getX();
            mouseY = MouseInfo.getPointerInfo().getLocation().getY();
            // Make sure the mouse is farther than 200 px away from the borders of the screen if the mouse is locked.
            if(mouseLocked && (mouseX < 200 || mouseX > 1080 || mouseY < 200 || mouseY > 520)){
                // If so...
                // Calculate x and y dist from the center
                movedX = 640 - mouseX;
                movedY = 360 - mouseY;
                // Notify the program that this was a robot moving the mouse.
                automatedMove = true;
            }
        } else if(automatedMove && mouseLocked){
            // Adjust for the shift to the center, so that the camera doesn't "jump" when the mouse gets moved to center.
            lastX = mouseX + movedX;
            lastY = mouseY + movedY;
            // Get new mouseX and Y.
            mouseX = MouseInfo.getPointerInfo().getLocation().getX();
            mouseY = MouseInfo.getPointerInfo().getLocation().getY();
            // Reset automatedMove.
            automatedMove = false;
        }
    }
//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 

    public boolean getKey(int keyCode){
        return keys[keyCode];
    }
    public double dMouseX(){
        return mouseX - lastX;
    }
    public double dMouseY(){
        return mouseY - lastY;
    }
    @Override
    public void mouseMoved(MouseEvent e){

    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Empty
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Empty
    }

    @Override
    public void mouseDragged(MouseEvent e){

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() < keys.length){
             //checks to see if this input was a new input
            if(getKey(e.getKeyCode()) == false){
                keys[e.getKeyCode()] = true;
                pressed = (int) System.currentTimeMillis();
                //checkDash(e, player); TODO: FIX THIS!
            }else{
                keys[e.getKeyCode()] = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if(e.getKeyCode() == 27){
            mouseLocked = !mouseLocked;
            lastX = MouseInfo.getPointerInfo().getLocation().getX();
            lastY = MouseInfo.getPointerInfo().getLocation().getY();
            mouseX = lastX;
            mouseY = lastY;
        }

        if(e.getKeyCode() < keys.length){
                keys[e.getKeyCode()] = false;
                released = (int) System.currentTimeMillis();
                checkShortClick(e);
        }
    }

    //players movements WASD run/walk (with shift): Calls Player methods
    public void playerMove(Player player){

        //method to stop the player from dashing
        if(player.getIsDashing()){
            if(lastDash <= (int) System.currentTimeMillis() && (int) System.currentTimeMillis() < lastDash + INTERVAL){
                //lil pause
            }else if(lastDash + INTERVAL <= (int) System.currentTimeMillis() && (int) System.currentTimeMillis() < lastDash + player.getDashLength() - INTERVAL){
                //actaully dashing
                int dir = dashDirection;
                if(dir == KeyEvent.VK_W){ // w
                    player.dashUp(player.getSpeed());
                }else if(dir == KeyEvent.VK_A){ // a
                    player.dashLeft(player.getSpeed());
                }else if(dir == KeyEvent.VK_S){ // s
                    player.dashDown(player.getSpeed());
                }else if(dir == KeyEvent.VK_D){ // d
                    player.dashRight(player.getSpeed());
                }
            }else if(lastDash + player.getDashLength() - INTERVAL <= (int) System.currentTimeMillis() && (int) System.currentTimeMillis() < lastDash + player.getDashLength()){
                //lil pause
            }else if(lastDash + player.getDashLength() <= (int) System.currentTimeMillis()){
                player.setIsDashing(false);
                player.setSpeed(5.0);
            }
        }
    
        //they can still input, but it will be less because the boolean isDashing will be true
        if(getKey(KeyEvent.VK_W) == true){
            if(getKey(KeyEvent.VK_SHIFT) == true){
                //shift and W is pressed
                player.moveUpRun();
            }else{
                //W pressed
                player.moveUpWalk();
            }
        }if(getKey(KeyEvent.VK_A) == true){
            if(getKey(KeyEvent.VK_SHIFT) == true){
                //shift and A pressed
                player.moveLeftRun();
            }else{
                //A pressed
                player.moveLeftWalk();
            }
        }if(getKey(KeyEvent.VK_S) == true){
            if(getKey(KeyEvent.VK_SHIFT) == true){
                // Shift and S is pressed
                player.moveDownRun();
            }else{
                //S is pressed
                player.moveDownWalk();
            }
        }if(getKey(KeyEvent.VK_D) == true){
            if(getKey(KeyEvent.VK_SHIFT) == true){
                //Shift and D is pressed
                player.moveRightRun();
            }else{
                //D is pressed
                player.moveRightWalk();
            }
        }
    }

    public void playerAttack(Player player){
        if(getKey(KeyEvent.VK_SPACE) == true){
            player.attack();
        }
    }

    public void checkShortClick(KeyEvent e){
        //System.out.println(Math.abs(pressed - released)); // duration of input
        if(Math.abs(pressed - released) < INTERVAL){
            shortClick = true;
        }
        latestInput = e.getKeyCode();
    }

    public void checkDash(KeyEvent e, Player player){
        //if last dash was longer than the dashcooldown, the time inbetween short click and this click is less than 350, there was a short click before and the lastinput is the same direction as the new input
        if(Math.abs(lastDash - pressed) > player.getDashCooldown() && Math.abs(released - pressed) < INTERVAL && shortClick && latestInput == e.getKeyCode()){            
            System.out.println("Dash! " + e.getKeyCode());
            player.setIsDashing(true);
            player.setSpeed(player.getSpeed() * player.getDashSpeed());
            lastDash = (int) System.currentTimeMillis();
            dashDirection = e.getKeyCode();
        }else{
            shortClick = false;
        }
        latestInput = e.getKeyCode();
    }

    public Player getPlayer1(){
        return this.player1;
    }
    public Player getPlayer2(){
        return this.player2;
    }
    public int getLastDash(){
        return lastDash;
    }
}
