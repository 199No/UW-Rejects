package src;

import java.awt.Rectangle;

// Data type for the map
// Stores a 10x10 area of tiles
public class Chunk {
    private int x; // X (in chunks) 
    private int y; // Y (in chunks) 
    private int absoluteX; // X (in pixels)
    private int absoluteY; // Y (in pixels)
    private int[][] tileData; // Ground data (what type of tile is the ground?)
    private int[][] envData; // Environment data (what is on the ground?) (trees, houses, chests, etc)
    private Rectangle unloadBoundary = new Rectangle(-Gui.TILE_SIZE*10, -Gui.TILE_SIZE*10, Gui.WIDTH + 20*Gui.TILE_SIZE, Gui.HEIGHT + 20*Gui.TILE_SIZE); 
    public Chunk(int[][] tileData, int[][] envData, int x, int y){
        this.tileData = tileData;
        this.envData = envData;
        this.x = x;
        this.y = y;
    }
    public int getTile(int x, int y){
        return tileData[y][x];
    }
    
    public boolean isVisible(double cameraX, double cameraY){
        return new Rectangle(-(int)cameraX, -(int)cameraY, Gui.TILE_SIZE*10, Gui.TILE_SIZE*10).contains(unloadBoundary);
    }
    public int x(){
        return x;
    }
    public int y(){
        return y;
    }
}
