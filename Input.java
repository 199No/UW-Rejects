import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {

    private Player player;
    private boolean isWPressed = false;
    private boolean isAPressed = false;
    private boolean isDPressed = false;
    private boolean isSpacePressed = false;
    private boolean isShiftPressed = false;
    private Panel panel;

    public Input(Player player, Panel panelGiven) {
        this.player = player;
        this.player.setPanel(panelGiven);
        panel = panelGiven;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // We won't use this method in this program
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        if (keyCode == KeyEvent.VK_W && !isWPressed) {
            isWPressed = true;
            player.moveUp();
        } else if (keyCode == KeyEvent.VK_A) {
            panel.setDirectionPanel("Left");
            isAPressed = true;
            if (isShiftPressed) {
                player.moveLeftRun();
            } else {
                player.moveLeftWalk();
            }
        } else if (keyCode == KeyEvent.VK_D) {
            panel.setDirectionPanel("Right");
            isDPressed = true;
            if (isShiftPressed) {
                player.moveRightRun();
            } else {
                player.moveRightWalk();
            }
        } else if (keyCode == KeyEvent.VK_SPACE) {
            isSpacePressed = true;
            player.attack();
        } else if (keyCode == KeyEvent.VK_SHIFT) {
            isShiftPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            isWPressed = false;
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
}