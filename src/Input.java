package src;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import java.awt.MouseInfo;
import java.awt.event.*;
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
    Player player;
    int latestInput;
    ///////////////
    //Comstuctor
    //////////////
    public Input(Player player){
        this.player = player;
        lastX = MouseInfo.getPointerInfo().getLocation().getX();
        lastY = MouseInfo.getPointerInfo().getLocation().getY();
        mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        mouseY = MouseInfo.getPointerInfo().getLocation().getY();
        automatedMove = false;
        movedX = 0;
        movedY = 0;
        mouseLocked = true;
        latestInput = -1;
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
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() < keys.length){
            keys[e.getKeyCode()] = true;
        }
    }

    //players movements WASD run/walk (with shift): Calls Player methods
    public void playerMove(){
        //figure out and measure the time when the input difference is inbetween 50 and 200 then make sure same direction
        //do calculation in checkDash()
        //System.out.println("difference " + ( Math.abs( (int) System.currentTimeMillis() ) - Math.abs(latestInput)) );
        checkDash();

        if(getKey(KeyEvent.VK_W) == true){
            if(getKey(KeyEvent.VK_SHIFT) == true){
                //shift and W is pressed
                player.moveUpRun();
                this.latestInput = (int) System.currentTimeMillis();
            }else{
                //W pressed
                player.moveUpWalk();
                this.latestInput = (int) System.currentTimeMillis();
            }
        }if(getKey(KeyEvent.VK_A) == true){
            if(getKey(KeyEvent.VK_SHIFT) == true){
                //shift and A pressed
                player.moveLeftRun();
                this.latestInput = (int) System.currentTimeMillis();
            }else{
                //A pressed
                player.moveLeftWalk();
                this.latestInput = (int) System.currentTimeMillis();
            }
        }if(getKey(KeyEvent.VK_S) == true){
            if(getKey(KeyEvent.VK_SHIFT) == true){
                // Shift and S is pressed
                player.moveDownRun();
                this.latestInput = (int) System.currentTimeMillis();
            }else{
                //S is pressed
                player.moveDownWalk();
                this.latestInput = (int) System.currentTimeMillis();
            }
        }if(getKey(KeyEvent.VK_D) == true){
            if(getKey(KeyEvent.VK_SHIFT) == true){
                //Shift and D is pressed
                player.moveRightRun();
                this.latestInput = (int) System.currentTimeMillis();
            }else{
                //D is pressed
                player.moveRightWalk();
                this.latestInput = (int) System.currentTimeMillis();
            }
        }
    }
    public void playerAttack(){
        if(getKey(KeyEvent.VK_SPACE) == true){
            player.attack();
        }
    }
    public void checkDash(){
        //checks to see if the input is 600 milliseconds after the latest input
        if( (int) System.currentTimeMillis() - this.latestInput > 200){
            //makes sure the player is only typing one input when dashing
            if(getKey(KeyEvent.VK_W) && getKey(KeyEvent.VK_A) == false && getKey(KeyEvent.VK_S) == false && getKey(KeyEvent.VK_D) == false){
                player.dashUp();
                System.out.println("DASH");
            }else if(getKey(KeyEvent.VK_W) == false && getKey(KeyEvent.VK_A) && getKey(KeyEvent.VK_S) == false && getKey(KeyEvent.VK_D) == false){
                player.dashLeft();
                System.out.println("DASH");
            }else if(getKey(KeyEvent.VK_W) == false && getKey(KeyEvent.VK_A) == false && getKey(KeyEvent.VK_S) && getKey(KeyEvent.VK_D) == false){
                player.dashDown();
                System.out.println("DASH");
            }else if(getKey(KeyEvent.VK_W) == false && getKey(KeyEvent.VK_A) == false && getKey(KeyEvent.VK_S) == false && getKey(KeyEvent.VK_D)){
                player.dashRight();
                System.out.println("DASH");
            }
        }


    }

    public Player getPlayer(){
        return this.player;
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
        }
    }
    @Override
    public void mouseDragged(MouseEvent e){

    }
}
