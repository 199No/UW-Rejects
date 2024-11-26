package mapTool;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Rectangle;
public class Tool implements ActionListener {
    Input input;
    Gui gui;
    Timer timer;
    int[][] chunk = new int[10][10];
    int selectedType;
    Rectangle chunkRectangle = new Rectangle(100, 100, 500, 500);
    Rectangle palletRectangle = new Rectangle(800, 100, 200, 500);
    int chunkX, chunkY;
    public Tool(){
        chunk = new int[10][10];
        selectedType = 1;

        Scanner s = new Scanner(System.in);
        System.out.println("Which chunk do you want to load? (x,y)");
        String[] userInput = s.nextLine().split(",");
        loadChunk(Integer.parseInt(userInput[0]), Integer.parseInt(userInput[1]));
        s.close();
        saveChunk();
        // Classes
        input = new Input(this);
        gui = new Gui(1080, 720, input, chunk);
        timer = new Timer(17, this);
        timer.start();
    }
    public void actionPerformed(ActionEvent e){
        gui.updateChunk(chunk);
        gui.background(255, 255, 255);
        gui.drawTiles();
        gui.drawGrid();
        gui.repaint();
    }
    public void loadChunk(int x, int y){
        String line;
        String[] row;
        String[] rawChunk;
        chunkX = x;
        chunkY = y;
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
            System.out.println(rawChunk.length);
            for(int i = 0; i < 10; i++){
                row = rawChunk[i].split(",");
                for(int k = 0; k < 10; k++){
                    chunk[i][k] = Integer.parseInt(row[k]);
                }
                
            }

        }catch(Exception e){e.printStackTrace();}

    }
    public void saveChunk(){
        try {
            // File for the output
            File outputFile = new File("Maps/toolOutput.map");
            // File for the map
            File mapFile = new File("Maps/map1.map");
            // The line that has our chunk in it, as a String array
            String[] outputLine;
            // Our chunk as a string
            String chunkString = "{";
            FileWriter fw = new FileWriter(outputFile, false);
            Scanner s = new Scanner(mapFile);
            fw.write("");
            fw.close();
            fw = new FileWriter(outputFile, true);
            for(int i = 0; i < chunkY; i++){
                fw.write(s.nextLine() + "\n");
            }
            outputLine = s.nextLine().split(";\\}");
            for(int y = 0; y < 10; y++){
                for(int x = 0; x < 10; x++){
                    chunkString += chunk[y][x];
                    if(x < 9){
                        chunkString += ",";
                    }
                }
                if(y < 9){ // See line 100
                    chunkString += ";";
                }
            }
            System.out.println(chunkString);
            outputLine[chunkX] = chunkString;
            for(int i = 0; i < outputLine.length; i++){
                fw.write(outputLine[i] + ";}"); 
            }
            while(s.hasNextLine()){
                fw.write(s.nextLine() + "\n");
            }
            // chunkString += "}"; Does not happen because the ";}" at the end of every line got sheared off during the split, so now each chunk in outputLine looks like "{chunk contents" instead of "{chunk contents;}"
            // The ";}" will get added back when we write to the file.

            fw.close();
        }catch(IOException e){e.printStackTrace();}
    }
    public void handleMouseClick(double mouseX, double mouseY){
        System.out.println("Clicked! " + mouseX + " " + mouseY);
        // Click on chunk
        if(chunkRectangle.contains(mouseX, mouseY)){
            System.out.println("chunk");
            int x = (int)Math.floor((mouseX - 100) / 50);
            int y = (int)Math.floor((mouseY - 125) / 50);
            chunk[y][x] = selectedType;
            System.out.println(chunk[y][x]);
        }  
        if(palletRectangle.contains(mouseX, mouseY)){
            int x = (int)Math.floor((mouseX - 800) / 50);
            int y = (int)Math.floor((mouseY - 125) / 50);
            selectedType = (int)(x + (y * 4)) + 1;
            System.out.println(selectedType);
        }
        gui.setSelectedType(selectedType);
    } 
}
