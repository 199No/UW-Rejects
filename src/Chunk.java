package src;
// Data type for the map
// Stores a 10x10 area of tiles
public class Chunk {
    private int x; // X (in chunks) 
    private int y; // Y (in chunks) 
    private int absoluteX; // X (in pixels)
    private int absoluteY; // Y (in pixels)
    private int[][] tileData; // Ground data (what type of tile is the ground?)
    private int[][] envData; // Environment data (what is on the ground?) (trees, houses, chests, etc)
    public Chunk(int[][] tileData,/* int[][] envData, */ int x, int y){
        this.tileData = tileData;
        this.x = x;
        this.y = y;
    }
    public int getTile(int x, int y){
        return tileData[y][x];
    }
    

}
