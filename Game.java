//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
//-------------------------------------------------//
//                     Game                        //
//-------------------------------------------------// 
public class Game implements ActionListener{
    ///////////////
    //Properties
    ///////////////
    Timer gameTimer;
    Gui gui;
    Player player;
    Slime slime;
    double now, lastSecond, frameRate, framesLastSecond;
    Input input;
    ///////////////
    //Constuctor
    //////////////
    public Game() throws AWTException{
        
        player = new Player();
        input = new Input(player);
        gui = new Gui(1280, 720, input);
        gameTimer = new Timer(5, this);
        gameTimer.start();
        now = System.currentTimeMillis();
        lastSecond = System.currentTimeMillis();
    }

//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 

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
        this.player = input.getPlayer(); //updates player with the inputs copy of player
        this.slime  = new Slime();
        gui.background((int)frameRate * 2, (int)frameRate, (int)frameRate * 2);
        gui.displayFPS((int)frameRate);
        gui.drawPlayer(player);
        gui.repaint();
        now = System.currentTimeMillis();
    }



}
