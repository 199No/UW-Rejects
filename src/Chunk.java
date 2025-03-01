package src;

import java.awt.Rectangle;
import java.util.ArrayList;

// Data type for the map
// Stores a 10x10 area of tiles
public class Chunk {
    private int x; // X (in chunks) 
    private int y; // Y (in chunks) 
    private int[][] tileData; // Ground data (what type of tile is the ground?)
    private int[][] envData; // Environment data (what is on the ground?) (trees, houses, chests, etc)
    private Rectangle chunkBoundary;
    private ArrayList<EnvObject> tempObjects = new ArrayList<EnvObject> ();
    private EnvObject[] envObjects;
    public Chunk(int[][] tileData, int[][] envData, int x, int y){
        this.tileData = tileData;
        this.envData = envData;
        this.x = x;
        this.y = y;
        this.chunkBoundary = new Rectangle(this.x, this.y, Gui.TILE_SIZE * 10, (int)(Gui.TILE_SIZE * 10 * Gui.HEIGHT_SCALE));
        // Extract envObjects from env data
        for(int i = 0; i < this.envData.length; i++){
            for(int k = 0; k < this.envData[i].length; k++){
                if(this.envData[i][k] != 0){
                   tempObjects.add(new EnvObject(
                        (x * Gui.TILE_SIZE * 10) + (k * Gui.TILE_SIZE), 
                        (y * Gui.TILE_SIZE * 10) + (i * Gui.TILE_SIZE), 
                        this.envData[i][k]
                    )); 
                }
            }
        }
        envObjects = new EnvObject[tempObjects.size()];
        envObjects = tempObjects.toArray(envObjects);
    }
    public int getTile(int x, int y){
        return tileData[y][x];
    }
    
    public boolean isVisible(double cameraX, double cameraY){
        this.chunkBoundary.setLocation((int)Gui.absToScreenX(this.x), (int)Gui.absToScreenY(this.y));
        return this.chunkBoundary.intersects(Map.CHUNK_UNLOAD_BOUNDARY);
    }
    public EnvObject[] getEnvObjects(){
        return envObjects;
    }
    public int x(){
        return x;
    }
    public int y(){
        return y;
    }
}
