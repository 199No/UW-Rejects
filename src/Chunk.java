package src;

import java.awt.Rectangle;
import java.util.ArrayList;

// Data type for the map
// Stores a 10x10 area of tiles
public class Chunk {
    private int xChunks; // X (in chunks) 
    private int yChunks; // Y (in chunks) 
    private int[][] tileData; // Ground data (what type of tile is the ground?)
    private int[][] envData; // Environment data (what is on the ground?) (trees, houses, chests, etc)
    private Rectangle chunkBoundary;
    private ArrayList<EnvObject> tempObjects = new ArrayList<EnvObject> ();
    private EnvObject[] envObjects;
    public Chunk(int[][] tileData, int[][] envData, int xChunks, int yChunks){
        this.tileData = tileData;
        this.envData = envData;
        this.xChunks = xChunks;
        this.yChunks = yChunks;
        this.chunkBoundary = new Rectangle(this.xChunks * Gui.CHUNK_WIDTH, (int)(this.yChunks * Gui.CHUNK_WIDTH), Gui.CHUNK_WIDTH, (int)(Gui.CHUNK_HEIGHT));
        // Extract envObjects from env data
        for(int i = 0; i < this.envData.length; i++){
            for(int k = 0; k < this.envData[i].length; k++){
                if(this.envData[i][k] != 0){
                   tempObjects.add(new EnvObject(
                        (xChunks * Gui.CHUNK_WIDTH) + (k * Gui.TILE_SIZE), 
                        (yChunks * Gui.CHUNK_WIDTH) + (i * Gui.TILE_SIZE), 
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
    
    public boolean isVisible(){
        this.chunkBoundary.setLocation((int)Gui.absToScreenX(this.xChunks * Gui.CHUNK_WIDTH), (int)Gui.absToScreenY(this.yChunks * Gui.CHUNK_HEIGHT));
        return this.chunkBoundary.intersects(Map.CHUNK_UNLOAD_BOUNDARY);
    }
    public EnvObject[] getEnvObjects(){
        return envObjects;
    }
    public int x(){
        return xChunks;
    }
    public int y(){
        return yChunks;
    }
    public double getAbsX(){
        return this.xChunks * Gui.CHUNK_WIDTH;
    }
    public double getAbsY(){
        return this.yChunks * Gui.CHUNK_HEIGHT;
    }
}
