package src;

//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------//
import java.awt.event.*;

//-------------------------------------------------//
//                    Input                        //
//-------------------------------------------------//
public class Input implements KeyListener {

    //////////////////////////
    // Constants and Globals
    //////////////////////////
    
    public static final int DASH_COOLDOWN = 3000;  // Minimum milliseconds between dashes
    static boolean[] keys = new boolean[90];       // Tracks current key state (pressed/released)
    static boolean[] shifts = new boolean[2];      // Tracks left/right Shift keys

    static int[] pressed = new int[90];            // Time when key was pressed
    static int[] released = new int[90];           // Time when key was released

    // DASHING
    // TODO: Move to Player class – Input should only track input, not handle dash logic directly
    static int p1dash;                             // Last dash key for Player 1
    static int p2dash;                             // Last dash key for Player 2
    static int dash;                               // Last dash key used
    static int lastp1Dash = (int) System.currentTimeMillis();  // Last time P1 dashed
    static int lastp2Dash = (int) System.currentTimeMillis();  // Last time P2 dashed
    static int lastdash;                           // Last time any dash occurred

    // Key bindings for Player 1 (movement + actions)
    static int[] player1Keys = {
        KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D,
        KeyEvent.VK_X, KeyEvent.VK_C
    };

    // Key bindings for Player 2 (movement + actions)
    static int[] player2Keys = {
        KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L,
        KeyEvent.VK_N, KeyEvent.VK_M
    };

    ///////////////
    // Constructor
    ///////////////
    public Input() {
        // No initialization needed here
    }

    //////////////////////
    // Utility Accessors
    //////////////////////
    public static boolean getKey(int keyCode) {
        return keys[keyCode];
    }

    public static boolean[] getKeys() {
        return keys;
    }

    public static boolean[] getShifts() {
        return shifts;
    }

    public static int[] getPlayer1Keys() {
        return player1Keys;
    }

    public static int[] getPlayer2Keys() {
        return player2Keys;
    }

    public static int[] getPlayerKeys(Player player) {
        return player.playernum == 1 ? getPlayer1Keys() : getPlayer2Keys();
    }

    public static int getDash() {
        return dash;
    }

    public static int getLastDash() {
        return lastdash;
    }

    public static int getLastDash(Player player) {
        return (player.playernum == 1) ? lastp1Dash : (player.playernum == 2 ? lastp2Dash : -1);
    }

    public static int getDashKey(Player player) {
        return (player.playernum == 1) ? p1dash : (player.playernum == 2 ? p2dash : -1);
    }

    public static int getLastp1Dash() {
        return lastp1Dash;
    }

    public static int getLastp2Dash() {
        return lastp2Dash;
    }

    public static int[] getPressed() {
        return pressed;
    }

    //////////////////////////////
    // Player Key Verifications
    //////////////////////////////
    
    public static boolean checkPlayer1Keys(int key) {
        for (int k : player1Keys) {
            if (key == k) return true;
        }
        return false;
    }

    public static boolean checkPlayer2Keys(int key) {
        for (int k : player2Keys) {
            if (key == k) return true;
        }
        return false;
    }

    // Movement keys are the first 4 keys in the key arrays
    public static boolean checkPlayer1MoveKeys(int key) {
        for (int i = 0; i < player1Keys.length - 2; i++) {
            if (key == player1Keys[i]) return true;
        }
        return false;
    }

    public static boolean checkPlayer2MoveKeys(int key) {
        for (int i = 0; i < player2Keys.length - 2; i++) {
            if (key == player2Keys[i]) return true;
        }
        return false;
    }

    //////////////////////////
    // KeyListener Overrides
    //////////////////////////

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < keys.length) {
            // Check if this is a new press (not holding)
            if (!getKey(e.getKeyCode())) {

                // DOUBLE-TAP DASH LOGIC:
                // If key was just released recently, and pressed again quickly
                if (
                    released[e.getKeyCode()] - pressed[e.getKeyCode()] <= 350 &&
                    (int) System.currentTimeMillis() - released[e.getKeyCode()] <= 350
                ) {
                    boolean isPlayer1 = checkPlayer1MoveKeys(e.getKeyCode());
                    boolean isPlayer2 = checkPlayer2MoveKeys(e.getKeyCode());

                    // If valid double-tap and cooldown expired, trigger dash
                    if (isPlayer1 && System.currentTimeMillis() - lastp1Dash > DASH_COOLDOWN) {
                        lastp1Dash = (int) System.currentTimeMillis();
                        lastdash = lastp1Dash;
                        p1dash = e.getKeyCode();
                        dash = e.getKeyCode();
                    } else if (isPlayer2 && System.currentTimeMillis() - lastp2Dash > DASH_COOLDOWN) {
                        lastp2Dash = (int) System.currentTimeMillis();
                        lastdash = lastp2Dash;
                        p2dash = e.getKeyCode();
                        dash = e.getKeyCode();
                    }
                }

                // SHIFT KEY LOGIC (block input)
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    int location = e.getKeyLocation();
                    if (location == KeyEvent.KEY_LOCATION_LEFT) {
                        shifts[0] = true;
                    } else if (location == KeyEvent.KEY_LOCATION_RIGHT) {
                        shifts[1] = true;
                    }
                }

                // Record the time this key was pressed
                pressed[e.getKeyCode()] = (int) System.currentTimeMillis();
            }

            // Update key state
            keys[e.getKeyCode()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < keys.length) {
            // Mark key as released
            keys[e.getKeyCode()] = false;

            // Shift key released
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                int location = e.getKeyLocation();
                if (location == KeyEvent.KEY_LOCATION_LEFT) {
                    shifts[0] = false;
                } else if (location == KeyEvent.KEY_LOCATION_RIGHT) {
                    shifts[1] = false;
                }
            }

            // Record time of release
            released[e.getKeyCode()] = (int) System.currentTimeMillis();
        }
    }
}
