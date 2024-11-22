package mapTool;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Scanner;
import java.io.File;
public class Tool implements ActionListener {
    Input input;
    Gui gui;
    Timer timer;
    int[][] chunk = new int[10][10];
    public Tool(){

        chunk = new int[10][10];
        // Classes
        input = new Input();
        gui = new Gui(1080, 720, input);
        timer = new Timer(17, this);
        timer.start();
    }
    public void actionPerformed(ActionEvent e){
        gui.drawTiles();
        gui.drawGrid();
        gui.repaint();
    }
    public void loadChunk(int x, int y){
        try {
            File f = new File("Maps\\map1.map");
            Scanner s = new Scanner(f);
            for(int i = 0; i < y; i++){
                s.nextLine();
            }
            System.out.println(s.nextLine());


        }catch(Exception e){e.printStackTrace();}

    }
}
