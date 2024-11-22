package mapTool;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Scanner;
import java.io.File;
import java.awt.Rectangle;
public class Tool implements ActionListener {
    Input input;
    Gui gui;
    Timer timer;
    int[][] chunk = new int[10][10];
    int selectedType;
    Rectangle chunkRectangle = new Rectangle(100, 100, 500, 500);
    Rectangle palletRectangle = new Rectangle(600, 100, 200, 500);
    public Tool(){
        chunk = new int[10][10];
        selectedType = 1;
        Scanner s = new Scanner(System.in);
        System.out.println("Which chunk do you want to load? (x,y)");
        String[] userInput = s.nextLine().split(",");
        loadChunk(Integer.parseInt(userInput[0]), Integer.parseInt(userInput[1]));
        s.close();
        // Classes
        input = new Input(this);
        gui = new Gui(1080, 720, input, chunk);
        timer = new Timer(17, this);
        timer.start();
    }
    public void actionPerformed(ActionEvent e){
        gui.updateChunk(chunk);
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
    public void handleMouseClick(double mouseX, double mouseY){
        System.out.println("Clicked! " + mouseX + " " + mouseY);
        // Click on chunk
        if(chunkRectangle.contains(mouseX, mouseY)){
            System.out.println("chunk");
            int x = (int)Math.floor((mouseX - 100) / 50);
            int y = (int)Math.floor((mouseY - 100) / 50);
            chunk[y][x] = selectedType;
            System.out.println(chunk[y][x]);
        }  
        if(palletRectangle.contains(mouseX, mouseY)){
            int x = (int)Math.floor((mouseX - 800) / 50);
            int y = (int)Math.floor((mouseY - 100) / 50);
            selectedType = (int)(x + (y * 4));
        }

    } 
}
