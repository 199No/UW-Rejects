import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

public class Game implements ActionListener{
    Timer gameTimer;
    public Game() {
        gameTimer = new Timer(17, this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Frame");
    }

}
