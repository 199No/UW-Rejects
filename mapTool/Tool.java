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
    Timer timer; // Provides a "pulse" every 1/60th of a second for frame timing
    int[][] chunk = new int[10][10];
    // Which tile  type is the "paint" right now
    int selectedType;
    // For input on the chunk
    Rectangle chunkRectangle = new Rectangle(100, 100, 500, 500);
    // For input on the pallet
    Rectangle palletRectangle = new Rectangle(800, 100, 200, 500);
    // For each button
    Rectangle saveButtonRectangle = new Rectangle(20, 20, 70, 30);
    Rectangle loadNewButtonRectangle = new Rectangle(110, 20, 70, 30);
    // Which chunk in the file to load/save
    int chunkX, chunkY;
    // Takes user input through the console
    Scanner consoleInput;
    public Tool(){
        // Everything that needs to happen before loading the chunk
        consoleInput = new Scanner(System.in);
        chunk = new int[10][10];
        selectedType = 1;
        
        loadChunk();

        // Everything that needs to happen after loading the chunk
        input = new Input(this);
        gui = new Gui(1080, 720, input, chunk);
        timer = new Timer(17, this);
        timer.start();
    }
    // Run every frame behind the scenes by Swing. The important bit is that it is run EVERY FRAME.
    public void actionPerformed(ActionEvent e){
        // Give Gui a copy of the chunk with whatever new edits were added to it.
        gui.updateChunk(chunk);
        // Draw a background over the last frame, otherwise you get "smearing" (Google it)
        gui.background(255, 255, 255);
        // Draw all the tiles in the chunk
        gui.drawTiles();
        // Draw the grid over that
        gui.drawGrid();
        // Draw the buttons (surprise)
        gui.drawButton(saveButtonRectangle, "Save");
        gui.drawButton(loadNewButtonRectangle, "Load");
        // Draw the newly drawn bits to the screen.
        gui.repaint();
    }

    // Takes user input and loads a chunk from a file into the chunk variable.
    // TODO: Make the user input part a different method.
    public void loadChunk(){
        // A single line of text in the file
        String line;
        // A row of chunks represented as an array of Strings
        String[] row;
        // The chunk to load represented as a String
        String[] rawChunk;

        // Get user input
        System.out.println("Which chunk do you want to load? (x,y)");
        String[] userInput = consoleInput.nextLine().split(",");
        
        // Parse user input
        chunkX = Integer.parseInt(userInput[0]);
        chunkY = Integer.parseInt(userInput[1]);

        if(userInput[0].equals("cancel")){
            // If chunk has not been loaded yet (if the tool was just opened)
            if(chunk[0][0] == 0 /* The array is initialized as all 0s (no tile has id 0) */){
                consoleInput.close();
                System.exit(0);
            }
            // Otherwise there is a chunk loaded, don't modify chunk data at all
            else {
                return;
            }
        }
        // I don't wanna have to deal with methods throwing exceptions
        try {
            // TODO: Make this not a hardcoded map file
            File f = new File("Maps\\map1.map");
            Scanner s = new Scanner(f);
            // Skip lines until one before the line at y...
            for(int i = 0; i < chunkY; i++){
                s.nextLine();
            }
            // so that the next line will be the one we want.
            line = s.nextLine();
            // Split the line into individual chunks; the regex here reads .split(";}").
            row = line.split(";\\}");
                     
            rawChunk = row[chunkX] // Get the chunk at our desired X
                .substring(1) // Get rid of the first character which is always a {
                    .split(";"); // Split into rows of tiles.
            // rawChunk now reads
            // ["n,n,n,n,n,n,n,n,n", "n,n,n,n,n,n,n,n,n", "n,n,n,n,n,n,n,n,n"...]
            // System.out.println(rawChunk.length);
            for(int i = 0; i < 10; i++){ // For each of the ten rows...
                // Reuse the row variable here to now mean "a row of tiles within a chunk"
                row = rawChunk[i].split(",");
                // Plonk the values into chunk.
                for(int k = 0; k < 10; k++){ // For each tile in a row...
                    chunk[i][k] = Integer.parseInt(row[k]);
                }
                
            }
            s.close(); // Make sure no memory leaks sneak their way out
        }catch(Exception e){e.printStackTrace();}

    }
    // Write the newly edited chunk to a file.
    /*
        This section of code works basically as follows:

        Everything in square brackets is UNEDITED chunks.
        The part in curly brackets is "our chunk", e.g. the one we made edits to.

        1. Copy over section 1 from the original map file.
        2. Using a for loop, write chunks from "our line", but stop right before our chunk. (Section 2)
        3. Turn the current chunk array into a save file string.
        4. Write that to the file. (our chunk)
        5. Write the rest of the line to the file after that.
        6. Write section 3 to the file.

        [Section 1 of the file -------------------------
        -----------------------------------------------]
        [Section 2 of the file]{our chunk}[rest of line] <-- this line
        [Section 3 of the file -------------------------
        ------------------------------------------------
        -----------------------------------------------]
    */
    public void saveChunk(){
        try {
            // File for the output
            File outputFile = new File("Maps/toolOutput.map");
            // File for the map
            File mapFile = new File("Maps/map1.map");
            // The line that has our chunk in it, as a String array
            String[] outputLine;
            // Our chunk as a string with the first character to make things easier later on.
            String chunkString = "{";
            // WRITES TO the output file.
            FileWriter fw = new FileWriter(outputFile, false);
            // READS FROM the original map file before we edited it.
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
