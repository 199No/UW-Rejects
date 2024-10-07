package src;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.event.*;
import java.awt.Robot;
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

        if(getKey(KeyEvent.VK_W) == true){
            if(getKey(KeyEvent.VK_SHIFT) == true){
            //shift is pressed
            player.moveUpRun();
        }else{
            player.moveUpWalk();
        }
        }if(getKey(KeyEvent.VK_A) == true){
            if(getKey(KeyEvent.VK_SHIFT) == true){
                player.moveLeftRun();
            }else{
                player.moveLeftWalk();
            }
        }if(getKey(KeyEvent.VK_S) == true){
            if(getKey(KeyEvent.VK_SHIFT) == true){
                player.moveDownRun();
            }else{
                player.moveDownWalk();
            }
        }if(getKey(KeyEvent.VK_D) == true){
            if(getKey(KeyEvent.VK_SHIFT) == true){
                player.moveRightRun();
            }else{
                player.moveRightWalk();
            }
        }
    }
    
    public void playerAttack(){
        if(getKey(KeyEvent.VK_SPACE) == true){
            player.attack();
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
