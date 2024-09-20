import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.*;


public class Input implements KeyListener, MouseMotionListener {

    private Player player;
    private boolean isWPressed = false;
    private boolean isAPressed = false;
    private boolean isSPressed = false;
    private boolean isDPressed = false;
    private boolean isSpacePressed = false;
    private boolean isShiftPressed = false;
    private JFrame panel;

    public Input(Player player) {
        this.player = player;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // We won't use this method in this program
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        if (keyCode == KeyEvent.VK_W) {
            isWPressed = true;
            if (isShiftPressed) {
                player.moveUpRun();
            } else {
                player.moveUpWalk();
            }
        } 
        if (keyCode == KeyEvent.VK_S) {
            isWPressed = true;
            if (isShiftPressed) {
                player.moveDownRun();
            } else {
                player.moveDownWalk();
            }
        } 
        if (keyCode == KeyEvent.VK_A) {
            isAPressed = true;
            if (isShiftPressed) {
                player.moveLeftRun();
            } else {
                player.moveLeftWalk();
            }
        } 
        if (keyCode == KeyEvent.VK_D) {
            isDPressed = true;
            if (isShiftPressed) {
                player.moveRightRun();
            } else {
                player.moveRightWalk();
            }
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            isSpacePressed = true;
            player.attack();
        } 
        if (keyCode == KeyEvent.VK_SHIFT) {
            isShiftPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            isWPressed = false;
        }  else if (keyCode == KeyEvent.VK_S) {
            isSPressed = false;
        } else if (keyCode == KeyEvent.VK_A) {
            isAPressed = false;
        } else if (keyCode == KeyEvent.VK_D) {
            isDPressed = false;    }
        else if (keyCode == KeyEvent.VK_SPACE){
            isSpacePressed = false;
        }
        else if (keyCode == KeyEvent.VK_SHIFT){
            isShiftPressed = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    } 
}