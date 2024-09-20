import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Game implements ActionListener{
    Timer gameTimer;
    Gui gui;
    Player player;
    double now, lastSecond, frameRate, framesLastSecond;
    Input input;
    public Game() throws AWTException{
        
        player = new Player(0, 0);
        input = new Input();
        gui = new Gui(1280, 720, input);
        gameTimer = new Timer(5, this);
        gameTimer.start();
        now = System.currentTimeMillis();
        lastSecond = System.currentTimeMillis();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(input.getKey(87)){

        } else if(input.getKey(83)){
            //playerVel.move(0, 0, -0.05);
        }
        if(input.getKey(65)){
            //playerVel.move(0.05, 0, 0);
        } else if(input.getKey(68)){
            //playerVel.move(-0.05, 0, 0);
        }
        if(input.getKey(32)){
            //playerVel.move(0, 0.05, 0);
        } else if(input.getKey(17)){
            //playerVel.move(0, -0.05, 0);
        }
        now = System.currentTimeMillis();
        if(now - lastSecond > 1000){
            lastSecond = now;
            frameRate = framesLastSecond;
            framesLastSecond = 0;
        } else {
            framesLastSecond ++;
        }
        player.updatePosition();
        gui.background((int)frameRate * 2, (int)frameRate, (int)frameRate * 2);
        gui.displayFPS((int)frameRate);
        gui.drawPlayer(player);
        gui.repaint();
        now = System.currentTimeMillis();
    }

}
