import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Game implements ActionListener{
    Timer gameTimer;
    Gui gui;
    
    double now, lastSecond, frameRate, framesLastSecond;
    public Game() throws AWTException{
        gui = new Gui(1280, 720);
        gameTimer = new Timer(5, this);
        gameTimer.start();
        now = System.currentTimeMillis();
        lastSecond = System.currentTimeMillis();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        
        now = System.currentTimeMillis();
        if(now - lastSecond > 1000){
            lastSecond = now;
            frameRate = framesLastSecond;
            framesLastSecond = 0;
        } else {
            framesLastSecond ++;
        }
        gui.background((int)frameRate * 2, (int)frameRate, (int)frameRate * 2);
        gui.displayFPS((int)frameRate);
        gui.repaint();
        now = System.currentTimeMillis();
    }

}
