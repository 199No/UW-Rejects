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
    public static final int DASH_COOLDOWN = 3000;
    static boolean[] keys = new boolean[90];
    static boolean[] shifts = new boolean[2];
    static int[] pressed = new int[90];
    static int[] released = new int[90];
    

    //TODO: Make player handle this (Input should only handle input)
    static int p1dash; // keyevent used to distinguish which key was pressed
    static int p2dash; // keyevent used to distinguish which key was pressed
    static int dash; // keyevent of last found key
    static int lastp1Dash = (int) System.currentTimeMillis(); // in mili
    static int lastp2Dash = (int) System.currentTimeMillis(); // in mili
    static int lastdash; // time of last dash

    // all keys used, besides shift for player 1
    static int[] player1Keys = {
        KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D,
        KeyEvent.VK_X, KeyEvent.VK_C
    };
            
    // all key codes used, besides shift for player 2
    static int[] player2Keys = {
        KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L,
        KeyEvent.VK_N, KeyEvent.VK_M
    };

    ///////////////
    //Comstuctor
    //////////////
    public Input(){

    }

    public static boolean getKey(int keyCode){
        return keys[keyCode];
    }
//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() < keys.length){

             //checks to see if this input was a new input
            if(getKey(e.getKeyCode()) == false){

                /////////////////
                /// DOUBLE CLICK MOVEMENT KEYS
                ////////////////
                if (released[e.getKeyCode()] - pressed[e.getKeyCode()] <= 350 && (int) System.currentTimeMillis() - released[e.getKeyCode()] <= 350) {
                    
                    boolean isPlayer1 = checkPlayer1MoveKeys(e.getKeyCode());
                    boolean isPlayer2 = checkPlayer2MoveKeys(e.getKeyCode());
                
                    if (isPlayer1 && (int) System.currentTimeMillis() - lastp1Dash > DASH_COOLDOWN) {
                        lastp1Dash = (int) System.currentTimeMillis();
                        lastdash = (int) System.currentTimeMillis();
                        p1dash = e.getKeyCode();
                        dash = e.getKeyCode();
                    } else if (isPlayer2 && (int) System.currentTimeMillis() - lastp2Dash > DASH_COOLDOWN) {
                        lastp2Dash = (int) System.currentTimeMillis();
                        lastdash = (int) System.currentTimeMillis();
                        p2dash = e.getKeyCode();
                        dash = e.getKeyCode();
                    }
                }
                /////////////////
                /// SHIFTS
                ////////////////
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
    
    public static boolean[] getKeys(){
        return keys;
    }
    public static boolean[] getShifts(){
        return shifts;
    }
    public static int[] getPlayer1Keys(){
        return player1Keys;
    }
    public static int[] getPlayer2Keys(){
        return player2Keys;
    }
    public static int[] getPlayerKeys(Player player){
        if(player.playernum == 1){
            return getPlayer1Keys();
        }else{
            return getPlayer2Keys();
        }
    }
    public static int getDash(){
        return dash;
    }
    public static int getLastDash(){
        return lastdash;
    }

    public static boolean checkPlayer1Keys(int key){
        for(int i = 0; i < player1Keys.length; i++){
            if(key == player1Keys[i]){
                return true;
            }
        }
        return false;
    }
    public static boolean checkPlayer2Keys(int key){
        for(int i = 0; i < player2Keys.length; i++){
            if(key == player2Keys[i]){
                return true;
            }
        }
        return false;
    }
    public static boolean checkPlayer1MoveKeys(int key){
        for(int i = 0; i < player1Keys.length - 2; i++){
            if(key == player1Keys[i]){
                return true;
            }
        }
        return false;
    }
    public static boolean checkPlayer2MoveKeys(int key){
        for(int i = 0; i < player2Keys.length - 2; i++){
            if(key == player2Keys[i]){
                return true;
            }
        }
        return false;
    }
    public static int getLastDash(Player player){
        if(player.playernum == 1){
            return lastp1Dash;
        }
        if(player.playernum == 2){
            return lastp2Dash;
        }
        return -1;
    }
    
    public static int getDashKey(Player player){
        if(player.playernum == 1){
            return p1dash;
        }
        if(player.playernum == 2){
            return p2dash;
        }
        return -1;
    }

    public static int getLastp1Dash(){
        return lastp1Dash;
    }
    public static int getLastp2Dash(){
        return lastp2Dash;
    }
    public static int[] getPressed(){
        return pressed;
    }
}