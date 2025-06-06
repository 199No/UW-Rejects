package src;

import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {
    ArrayList<Chunk> loadedChunks = new ArrayList<Chunk>();

    String tilePath;
    String envPath;
    public static final Rectangle CHUNK_UNLOAD_BOUNDARY = new Rectangle(-100, -100, Gui.WIDTH + 200, Gui.HEIGHT + 200);
    public Map(String tilePath, String envPath){
        for(int y = 0; y < 6; y++){
            for(int x = 0; x < 6; x++){
                loadedChunks.add(new Chunk(loadChunk(x, y, tilePath), loadChunk(x, y, envPath), x, y));
            }
        }
        this.tilePath = tilePath;
        this.envPath = envPath;
    }
    public void doChunkLoadUnload(double cameraX, double cameraY){

    }
    // Goes through the list of chunks and unloads any that are outside the chunk loading boundary
    public int[][] loadChunk(int chunkX, int chunkY, String filePath){
        // A single line of text in the file
        String line;
        // A row of chunks represented as an array of Strings
        String[] row;
        // The chunk to load represented as a String
        String[] rawChunk;

        int[][] chunk = new int[10][10]; // The chunk to be loaded (the result)

        // I don't wanna have to deal with methods throwing exceptions
        try {
            Scanner s = new Scanner(new File(filePath));
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
        return chunk;
    }
    public Chunk getChunk(int i){
        return loadedChunks.get(i);
    }
    public int numLoadedChunks(){
        return loadedChunks.size();
    }
    public ArrayList<EnvObject> getAllEnvObjects(){
        ArrayList<EnvObject> result = new ArrayList<EnvObject> ();
        for(int i = 0; i < loadedChunks.size(); i++){
            for(EnvObject e : loadedChunks.get(i).getEnvObjects()){
                result.add(e);
            }
        }
        return result;
    }
    public String getTilePath(){
        return tilePath;
    }
}
