package src;


//pressed -> released (check if short)  [100 - 350]
//time inbetween
//pressed2
//check dash will check if released and pressed2 inputs have a short break (released was a short click)
//if click1 and click2 short; dash




//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import java.awt.event.*;
//-------------------------------------------------//
//                    Input                        //
//-------------------------------------------------// 
public class Input implements KeyListener{
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
    
    int p1dash; // keyevent used to distinguish which key was pressed
    int p2dash; // keyevent used to distinguish which key was pressed
    int dash; // keyevent of last found key
    int lastp1Dash = (int) System.currentTimeMillis(); // in mili
    int lastp2Dash = (int) System.currentTimeMillis(); // in mili
    int lastdash; // time of last dash

    // all keys used, besides shift for player 1
    public int[] player1Keys = {
        KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D,
        KeyEvent.VK_X, KeyEvent.VK_C
    };
            
    // all key codes used, besides shift for player 2
    public int[] player2Keys = {
        KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L,
        KeyEvent.VK_N, KeyEvent.VK_M
    };

    ///////////////
    //Comstuctor
    //////////////
    public Input(){
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

    public boolean getKey(int keyCode){
        return keys[keyCode];
    }
//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() < keys.length){
             //checks to see if this input was a new input

            if(getKey(e.getKeyCode()) == false){
                //checkDash

                //check double click
                if (released[e.getKeyCode()] - pressed[e.getKeyCode()] <= 350 && (int) System.currentTimeMillis() - released[e.getKeyCode()] <= 350) {
                    
                    boolean isPlayer1 = checkPlayer1Keys(e.getKeyCode());
                    boolean isPlayer2 = checkPlayer2Keys(e.getKeyCode());
                
                    if (isPlayer1 && (int) System.currentTimeMillis() - this.lastp1Dash > 5000) {
                        this.lastp1Dash = (int) System.currentTimeMillis();
                        this.lastdash = (int) System.currentTimeMillis();
                        this.p1dash = e.getKeyCode();
                        this.dash = e.getKeyCode();
                        System.out.println("dashcodep1: " + e.getKeyCode());
                    } else if (isPlayer2 && (int) System.currentTimeMillis() - this.lastp2Dash > 5000) {
                        this.lastp2Dash = (int) System.currentTimeMillis();
                        this.lastdash = (int) System.currentTimeMillis();
                        this.p2dash = e.getKeyCode();
                        this.dash = e.getKeyCode();
                        System.out.println("dashcodep2: " + e.getKeyCode());
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
            }
        
        }
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {


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
    
    public boolean[] getKeys(){
        return this.keys;
    }
    public boolean[] getShifts(){
        return this.shifts;
    }
    public int[] getPlayer1Keys(){
        return this.player1Keys;
    }
    public int[] getPlayer2Keys(){
        return this.player2Keys;
    }
    public int[] getPlayerKeys(Player player){
        if(player.playernum == 1){
            return getPlayer1Keys();
        }else{
            return getPlayer2Keys();
        }

    }
    public int getDash(){
        return this.dash;
    }
    public int getLastDash(){
        return this.lastdash;
    }

    public boolean checkPlayer1Keys(int key){
        for(int i = 0; i < this.player1Keys.length; i++){
            if(key == player1Keys[i]){
                return true;
            }
        }
        return false;
    }
    public boolean checkPlayer2Keys(int key){
        for(int i = 0; i < this.player2Keys.length; i++){
            if(key == player2Keys[i]){
                return true;
            }
        }
        return false;
    }
    // TODO: Make player handle dash
    public int getLastDash(Player player){
        if(player.playernum == 1){
            return getLastp1Dash();
        }
        if(player.playernum == 2){
            return getLastp2Dash();
        }
        return -1;
    }
    public int getLastp1Dash(){
        return this.lastp1Dash;
    }
    public int getLastp2Dash(){
        return this.lastp2Dash;
    }
    public int[] getPressed(){
        return this.pressed;
    }

}