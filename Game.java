import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Game implements ActionListener{
    Timer gameTimer;
    public Game() throws AWTException{
        gameTimer = new Timer(11, this);
        gameTimer.start();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Frame");
    }

}
