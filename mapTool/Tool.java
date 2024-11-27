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
    Rectangle saveButtonRectangle = new Rectangle(20, 20, 70, 30);
    Rectangle loadNewButtonRectangle = new Rectangle(110, 20, 70, 30);
    int chunkX, chunkY;
    Scanner consoleInput;
    public Tool(){
        consoleInput = new Scanner(System.in);
        chunk = new int[10][10];
        selectedType = 1;
        
        loadChunk();

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
        gui.drawButton(saveButtonRectangle, "Save");
        gui.drawButton(loadNewButtonRectangle, "Load");
        gui.repaint();
    }
    public void loadChunk(){
        String line;
        String[] row;
        String[] rawChunk;
        System.out.println("Which chunk do you want to load? (x,y)");
        String[] userInput = consoleInput.nextLine().split(",");
        if(userInput[0].equals("cancel")){
            // If chunk has not been loaded yet; there is no tile with id 0
            if(chunk[0][0] == 0){
                consoleInput.close();
                System.exit(0);
            }
            else {
                return;
            }
        }
        chunkX = Integer.parseInt(userInput[0]);
        chunkY = Integer.parseInt(userInput[1]);

        try {
            File f = new File("Maps\\map1.map");
            Scanner s = new Scanner(f);
            // Skip lines until one before the line at y...
            for(int i = 0; i < chunkY; i++){
                s.nextLine();
            }
            // so that the next line will be the one we want.
            line = s.nextLine();
            
            row = line.split(";\\}");
            
            rawChunk = row[chunkX].substring(1).split(";");
            System.out.println(rawChunk.length);
            for(int i = 0; i < 10; i++){
                row = rawChunk[i].split(",");
                for(int k = 0; k < 10; k++){
                    chunk[i][k] = Integer.parseInt(row[k]);
                }
                
            }
            s.close();
        }catch(Exception e){e.printStackTrace();}

    }
    public void saveChunk(){
        System.out.println("Bruh");
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
            // Skip lines until reaching our desired line
            for(int i = 0; i < chunkY; i++){
                fw.write(s.nextLine() + "\n");
            }
            // Get the desired line and split it into 
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
            // Write outputLine to a file
            for(int i = 0; i < outputLine.length; i++){
                fw.write(outputLine[i] + ";}"); 
            }
            // New line so that the next line actually starts on the next line
            fw.write("\n");
            // Write the rest of the file
            while(s.hasNextLine()){
                fw.write(s.nextLine() + "\n");
            }
            // chunkString += "}"; Does not happen because the ";}" at the end of every line got sheared off during the split, so now each chunk in outputLine looks like "{chunk contents" instead of "{chunk contents;}"
            // The ";}" will get added back when we write to the file.

            fw.close();
            s.close();
        }catch(IOException e){e.printStackTrace();}
    }
    public void handleMouseClick(double mouseX, double mouseY){
        System.out.println("Clicked! " + mouseX + " " + (mouseY - 32));
        if(saveButtonRectangle.contains(mouseX, mouseY - 32)){
            System.out.println("Saved");
            saveChunk();
        }
        if(loadNewButtonRectangle.contains(mouseX, mouseY - 32)){
            loadChunk();
        }
        if(chunkRectangle.contains(mouseX, mouseY - 32)){
            System.out.println("chunk");
            int x = (int)Math.floor((mouseX - 100) / 50);
            int y = (int)Math.floor((mouseY - 132) / 50);
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
