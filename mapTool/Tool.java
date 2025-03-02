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
    int[][][] chunks = new int[10][10][9]; 
    int[][][] envChunks = new int[10][10][9]; 
    // Which tile type is the "paint" right now
    int selectedType;
    // For input on the chunk
    Rectangle chunkRectangle = new Rectangle(100, 100, 10 * Gui.TILE_SIZE, (int)(10 * (Gui.TILE_SIZE * Gui.HEIGHT_SCALE)));
    // For input on the pallet
    Rectangle palletRectangle = new Rectangle(800, 100, 200, 500);
    // For each button
    Rectangle saveButtonRectangle = new Rectangle(20, 20, 70, 30);
    Rectangle loadNewButtonRectangle = new Rectangle(110, 20, 70, 30);
    // Which chunk in the file to load/save
    int chunkX, chunkY;
    // Takes user input through the console
    Scanner consoleInput;
    boolean inEnvMode = true;
    File mapFile;
    File envFile;
    File outputFile = new File("Maps/toolOutput.map");
    File outputEnvFile = new File("Maps/toolOutputEnv.map");
    String saveButtonText = "Save";
    int lastSave = -Integer.MAX_VALUE;
    public Tool(){
        // Everything that needs to happen before loading the chunk
        consoleInput = new Scanner(System.in);

        System.out.println("Which map do you want to load? (1 for map 1, 2, for map 2, etc)");
        String selectedMap = consoleInput.nextLine();
        mapFile = new File("Maps/map" + selectedMap + ".map");
        envFile = new File("Maps/map" + selectedMap + "Env.map");

        selectedType = 1;
        int[] userInput = getUserInput();
        for(int y = userInput[1] - 1; y < userInput[1] + 3; y++){
            for(int x = userInput[0] - 1; x < userInput[0] + 3; x++){
                if(x >= 0 && x <= 5 && y >= 0 && y <= 5){
                    loadChunk(userInput, mapFile, chunks[y * 3 + x]);
                    loadChunk(userInput, envFile, envChunks[y * 3 + x]);
                }
            }
        }
        

        // Everything that needs to happen after loading the chunk
        input = new Input(this);
        gui = new Gui(1080, 720, input, chunks, envChunks);
        timer = new Timer(17, this);
        timer.start();
    }
    // Run every frame behind the scenes by Swing. The important bit is that it is run EVERY FRAME.
    public void actionPerformed(ActionEvent e){
        // Update mouseX, mouseY, pmouseX, and pmouseY
        input.updateMouse();
        if(input.getMousePressed()){
            handleMouseClick(input.mouseX(), input.mouseY());
        }
        // Give Gui a copy of the chunk with whatever new edits were added to it.
        gui.updateChunk(chunks[5], envChunks[5]);
        // Draw a background over the last frame, otherwise you get "smearing" (Google it)
        gui.background(255, 255, 255);
        // Draw all the tiles in the chunk
        gui.drawTiles(inEnvMode);
        // Draw the grid over that
        gui.drawGrid();
        // Draw the buttons (surprise)
        if(saveButtonText == "Saved!" && (int)System.currentTimeMillis() - lastSave > 1500){
            saveButtonText = "Save";
        }
        gui.drawButton(saveButtonRectangle, saveButtonText);
        gui.drawButton(loadNewButtonRectangle, "Load");
        // Draw the newly drawn bits to the screen.
        gui.repaint();
    }
    public int[] getUserInput(){
        
        // Get user input
        System.out.println("Which chunk do you want to load? (x,y)");
        String[] userInput = consoleInput.nextLine().split(",");
        
        // Parse user input
        chunkX = Integer.parseInt(userInput[0]);
        chunkY = Integer.parseInt(userInput[1]);

        if(userInput[0].equals("cancel")){
            // If chunk has not been loaded yet (if the tool was just opened)
            if(chunks[0][0][5] == 0 /* The array is initialized as all 0s (no tile has id 0) */){
                consoleInput.close();
                System.exit(0);
            }
            // Otherwise there is a chunk loaded, don't modify chunk data at all
            else {
                return new int[]{-1, -1};
            }
        }
        return new int[]{chunkX, chunkY};
    }
    // Takes user input and loads a chunk from a file into the chunk variable.
    public void loadChunk(int[] coords, File mapFile, int[][] result){
        // A single line of text in the file
        String line;
        // A row of chunks represented as an array of Strings
        String[] row;
        // The chunk to load represented as a String
        String[] rawChunk;

        // I don't wanna have to deal with methods throwing exceptions
        try {
            File f = mapFile;
            Scanner s = new Scanner(f);
            // Skip lines until one before the line at y...
            for(int i = 0; i < coords[1]; i++){
                s.nextLine();
            }
            // so that the next line will be the one we want.
            line = s.nextLine();
            // Split the line into individual chunks; the regex here reads .split(";}").
            row = line.split(";\\}");
                     
            rawChunk = row[coords[0]] // Get the chunk at our desired X
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
                    result[i][k] = Integer.parseInt(row[k]);
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
    public void saveChunk(File outputFile, File mapFile){
        try {
            // File for the output
            //File outputFile = new File("Maps/toolOutput.map");

            // File for the map
            //File mapFile = new File("Maps/map1.map");
            
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
                    if(mapFile.getName().endsWith("Env.map")){
                        chunkString += envChunks[y][x][5];
                    } else {
                        chunkString += chunks[y][x][5];
                    }
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
        // Copy the output file over to the map file
        try {
            FileWriter fw = new FileWriter(mapFile, false);
            fw.write("");
            fw.close();
            fw = new FileWriter(mapFile);
            Scanner s = new Scanner(outputFile);
            while(s.hasNextLine()){
                fw.write(s.nextLine() + "\n");
            }
            s.close();
            fw.close();
        }catch(IOException e){e.printStackTrace();}
        saveButtonText = "Saved!";
        lastSave = (int)System.currentTimeMillis();
    }
    public void handleMouseClick(double mouseX, double mouseY){
        System.out.println("Clicked! " + mouseX + " " + (mouseY - 32));
        if(saveButtonRectangle.contains(mouseX, mouseY - 32)){
            System.out.println("Saved");
            saveChunk(outputFile, mapFile);
            saveChunk(outputEnvFile, envFile);
        }
        if(loadNewButtonRectangle.contains(mouseX, mouseY - 32)){
            consoleInput = new Scanner(System.in);
    
            System.out.println("Which map do you want to load? (1 for map 1, 2, for map 2, etc)");
            String selectedMap = consoleInput.nextLine();
            mapFile = new File("Maps/map" + selectedMap + ".map");
            envFile = new File("Maps/map" + selectedMap + "Env.map");
    
            selectedType = 1;
            int[] userInput = getUserInput();
            loadChunk(userInput, mapFile, chunks[5]);
            loadChunk(userInput, envFile, envChunks[5]);
        }
        if(chunkRectangle.contains(mouseX, mouseY - 32)){
            int x = (int)Math.floor((mouseX - 100) / Gui.TILE_SIZE);
            int y = (int)Math.floor((mouseY - 132) / (Gui.TILE_SIZE * Gui.HEIGHT_SCALE));
            if(inEnvMode){
                envChunks[y][x][5] = selectedType - 1;
            } else {
                chunks[y][x][5] = selectedType;
            }
        }  
        if(palletRectangle.contains(mouseX, mouseY)){
            int x = (int)Math.floor((mouseX - 800) / 50);
            int y = (int)Math.floor((mouseY - 125) / 50);
            selectedType = (int)(x + (y * 4)) + 1;
            System.out.println(selectedType);
        }
        gui.setSelectedType(selectedType);
    } 
    public void setEnvMode(boolean t){
        inEnvMode = t;
    }
    public boolean getEnvMode(){
        return inEnvMode;
    }
    public String getSaveText(){
        return saveButtonText;
    }
}
