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
    boolean[] shifts = new boolean[2];
    int[] pressed = new int[90];
    int[] released = new int[90];
    
    int dash; // keyevent used to distinguish which key was pressed
    int lastDash = (int) System.currentTimeMillis(); // in mili
    ///////////////
    //Comstuctor
    //////////////
    public Input(){
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

    public boolean getKey(int keyCode){
        return keys[keyCode];
    }
    public double dMouseX(){
        return mouseX - lastX;
    }
    public double dMouseY(){
        return mouseY - lastY;
    }
//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 

    @Override
    public void mouseMoved(MouseEvent e){}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e){}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() < keys.length){
             //checks to see if this input was a new input
            if(getKey(e.getKeyCode()) == false){
                keys[e.getKeyCode()] = true;
                //checkDash
                if(released[e.getKeyCode()] - pressed[e.getKeyCode()] <= 350 && (int) System.currentTimeMillis() - released[e.getKeyCode()] <= 350){
                    if((int) System.currentTimeMillis() - this.lastDash > 5000){
                        this.lastDash = (int) System.currentTimeMillis();
                        this.dash = e.getKeyCode();
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    int location = e.getKeyLocation();
                    if (location == KeyEvent.KEY_LOCATION_LEFT) {
                        shifts[0] = true;
                    } else if (location == KeyEvent.KEY_LOCATION_RIGHT) {
                        shifts[1] = true;
                    }
                }
                pressed[e.getKeyCode()] = (int) System.currentTimeMillis(); // new key pressed
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
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                int location = e.getKeyLocation();
                if (location == KeyEvent.KEY_LOCATION_LEFT) {
                    shifts[0] = false;
                } else if (location == KeyEvent.KEY_LOCATION_RIGHT) {
                    shifts[1] = false;
                }
            }
            released[e.getKeyCode()] = (int) System.currentTimeMillis();
        }
    }
    public int getDash(){
        return this.dash;
    }
    public int getLastDash(){
        return this.lastDash;
    }
    public boolean[] getKeys(){
        return this.keys;
    }
    public boolean[] getShifts(){
        return this.shifts;
    }
    public int[] getPressed(){
        return this.pressed;
    }

}
