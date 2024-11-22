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
        Scanner s = new Scanner(System.in);
        System.out.println("Which chunk do you want to load? (x,y)");
        String[] userInput = s.nextLine().split(",");
        loadChunk(Integer.parseInt(userInput[0]), Integer.parseInt(userInput[1]));
        s.close();
        // Classes
        input = new Input();
        gui = new Gui(1080, 720, input, chunk);
        timer = new Timer(17, this);
        timer.start();
    }
    public void actionPerformed(ActionEvent e){
        gui.drawTiles();
        gui.drawGrid();
        gui.repaint();
    }
    public void loadChunk(int x, int y){
        String line;
        String[] row;
        String[] rawChunk;
        try {
            File f = new File("Maps\\map1.map");
            Scanner s = new Scanner(f);
            // Skip lines until one before the line at y...
            for(int i = 0; i < y; i++){
                s.nextLine();
            }
            // so that the next line will be the one we want.
            line = s.nextLine();
            
            row = line.split(";\\}");
            
            rawChunk = row[x].substring(1).split(";");

            for(int i = 0; i < 10; i++){
                row = rawChunk[i].split(",");
                for(int k = 0; k < 10; k++){
                    chunk[i][k] = Integer.parseInt(row[k]);
                }
                
            }

        }catch(Exception e){e.printStackTrace();}

    }
}
