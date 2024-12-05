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
    int[] pressed = new int[90];
    int[] released = new int[90];

    Player player1;
    Player player2;
    
    boolean isDashing;
    int lastDash;
    boolean isAttacking;
    boolean isBlocking;
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
                if(released[e.getKeyCode()] - pressed[e.getKeyCode()] <= 200 && (int) System.currentTimeMillis() - released[e.getKeyCode()] <= 200){
                    this.lastDash = (int) System.currentTimeMillis();
                    System.out.println("dash");
                }
                //checkDash(e);
                //checkAttack(e);
                //checkBlock(e);
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
                released[e.getKeyCode()] = (int) System.currentTimeMillis();
        }
    }
    




    //players movements WASD run/walk (with shift): Calls Player methods
    public void playerMove(){
/* 
        //method to stop the player from dashing
        if(getPlayer1().getIsDashing()){
            if(lastDash <= (int) System.currentTimeMillis() && (int) System.currentTimeMillis() < lastDash + INTERVAL){
                //lil pause
            }else if(lastDash + INTERVAL <= (int) System.currentTimeMillis() && (int) System.currentTimeMillis() < lastDash + getPlayer1().getDashLength() - INTERVAL){
                //actaully dashing
                int dir = dashDirection;
                if(dir == KeyEvent.VK_W){ // w
                    getPlayer1().dashUp(getPlayer1().getSpeed());
                }else if(dir == KeyEvent.VK_A){ // a
                    getPlayer1().dashLeft(getPlayer1().getSpeed());
                }else if(dir == KeyEvent.VK_S){ // s
                    getPlayer1().dashDown(getPlayer1().getSpeed());
                }else if(dir == KeyEvent.VK_D){ // d
                    getPlayer1().dashRight(getPlayer1().getSpeed());
                }
            }else if(lastDash + getPlayer1().getDashLength() - INTERVAL <= (int) System.currentTimeMillis() && (int) System.currentTimeMillis() < lastDash + getPlayer1().getDashLength()){
                //lil pause
            }else if(lastDash + getPlayer1().getDashLength() <= (int) System.currentTimeMillis()){
                getPlayer1().setIsDashing(false);
                getPlayer1().setSpeed(5.0);
            }
        }

         //method to stop the player from dashing
         if(getPlayer2().getIsDashing()){
            if(lastDash <= (int) System.currentTimeMillis() && (int) System.currentTimeMillis() < lastDash + INTERVAL){
                //lil pause
            }else if(lastDash + INTERVAL <= (int) System.currentTimeMillis() && (int) System.currentTimeMillis() < lastDash + getPlayer2().getDashLength() - INTERVAL){
                //actaully dashing
                int dir = dashDirection;
                if(dir == KeyEvent.VK_W){ // w
                    getPlayer2().dashUp(getPlayer2().getSpeed());
                }else if(dir == KeyEvent.VK_A){ // a
                    getPlayer2().dashLeft(getPlayer2().getSpeed());
                }else if(dir == KeyEvent.VK_S){ // s
                    getPlayer2().dashDown(getPlayer2().getSpeed());
                }else if(dir == KeyEvent.VK_D){ // d
                    getPlayer2().dashRight(getPlayer2().getSpeed());
                }
            }else if(lastDash + getPlayer2().getDashLength() - INTERVAL <= (int) System.currentTimeMillis() && (int) System.currentTimeMillis() < lastDash + getPlayer2().getDashLength()){
                //lil pause
            }else if(lastDash + getPlayer2().getDashLength() <= (int) System.currentTimeMillis()){
                getPlayer2().setIsDashing(false);
                getPlayer2().setSpeed(5.0);
            }
        }
    */
        //they can still input, but it will be less because the boolean isDashing will be true
        if(getKey(KeyEvent.VK_W) == true){
            if(getKey(KeyEvent.VK_SHIFT) == true){
                //shift and W is pressed
                player1.moveUpRun();
            }else{
                //W pressed
                player1.moveUpWalk();
            }
        }if(getKey(KeyEvent.VK_A) == true){
            if(getKey(KeyEvent.VK_SHIFT) == true){
                //shift and A pressed
                player1.moveLeftRun();
            }else{
                //A pressed
                player1.moveLeftWalk();
            }
        }if(getKey(KeyEvent.VK_S) == true){
            if(getKey(KeyEvent.VK_SHIFT) == true){
                // Shift and S is pressed
                player1.moveDownRun();
            }else{
                //S is pressed
                player1.moveDownWalk();
            }
        }if(getKey(KeyEvent.VK_D) == true){
            if(getKey(KeyEvent.VK_SHIFT) == true){
                //Shift and D is pressed
                player1.moveRightRun();
            }else{
                //D is pressed
                player1.moveRightWalk();
            }
        }
    }
/* 
    public void playerAttack(){
        if(getKey(KeyEvent.VK_X) == true){
            player1.attack();
        }
        if(getKey(KeyEvent.VK_M) == true){
            player2.attack();
        }
    }
/* 
    public void checkShortClick(KeyEvent e){
        //System.out.println(Math.abs(pressed - released)); // duration of input
        if(Math.abs(pressed - released) < INTERVAL){
            shortClick = true;
        }
        latestInput = e.getKeyCode();
    }
/* 
    public void checkDash(KeyEvent e){
        //if last dash was longer than the dashcooldown, the time inbetween short click and this click is less than 350, there was a short click before and the lastinput is the same direction as the new input
        if(Math.abs(lastDash - pressed) > getPlayer().getDashCooldown() && Math.abs(released - pressed) < INTERVAL && shortClick && latestInput == e.getKeyCode()){            
            System.out.println("Dash! " + e.getKeyCode());
            getPlayer().setIsDashing(true);
            getPlayer().setSpeed(getPlayer().getSpeed() * getPlayer().getDashSpeed());
            lastDash = (int) System.currentTimeMillis();
            dashDirection = e.getKeyCode();
        }else{
            shortClick = false;
        }
        latestInput = e.getKeyCode();
    }
*/
/* 
    public void checkRoll(KeyEvent e){
        //if last dash was longer than the dashcooldown, the time inbetween short click and this click is less than 350, there was a short click before and the lastinput is the same direction as the new input
        if(Math.abs(lastDash - pressed) > getPlayer1().getDashCooldown() && Math.abs(released - pressed) < INTERVAL && shortClick && latestInput == e.getKeyCode()){            
            System.out.println("Dash! " + e.getKeyCode());
            getPlayer1().setIsDashing(true);
            getPlayer1().setSpeed(getPlayer1().getSpeed() * getPlayer1().getDashSpeed());
            lastDash = (int) System.currentTimeMillis();
            dashDirection = e.getKeyCode();
        }else{
            shortClick = false;
        }
        latestInput = e.getKeyCode();
    }
*/

    public Player getPlayer1(){
        return this.player1;
    }

    public Player getPlayer2(){
        return this.player2;
    }
    public void setIsDashing(boolean bool){
        this.isDashing = bool;
    }

    public void setIsAttacking(boolean bool){
        this.isAttacking = bool;
    }

    public void setIsBlocking(boolean bool){
        this.isBlocking = bool;
    }
    public int getLastDash(){
        return this.lastDash;
    }

}
