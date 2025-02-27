package mapTool;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
//-------------------------------------------------//
//                      Gui                        //
//-------------------------------------------------// 
public class Gui extends JPanel{
    ///////////////
    //Properties
    ////////////// 
    
    // Width and height of the draw area
    int width;
    int height;
    // Made public so that other classes can see them
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final double HEIGHT_SCALE = (double)2/(double)3;
    public static final int TILE_SIZE = 60;
    // Where things get queued up to be drawn. 
    // Instead of draw commands being fired whenever, allows things to be drawn all at once at the end of the frame.
    ArrayList<GraphicsRunnable> drawQueue;
    // You need a frame to draw things on.
    JFrame frame = new JFrame("The Divided Realms Map Tool");
    Images images;
    Images tempImages = new Images("Images/Enviroment");
    BufferedImage[] envImages;   
    int[][] chunk = new int[10][10];
    int[][] envChunk = new int[10][10];
    int selectedType = 1;
    
    private static double[][] possibleDimensions = new double[][] {
        {0, 0},
        {Gui.TILE_SIZE, Gui.TILE_SIZE * Gui.HEIGHT_SCALE},
        {Gui.TILE_SIZE, Gui.TILE_SIZE * Gui.HEIGHT_SCALE},
        {Gui.TILE_SIZE * 3, Gui.TILE_SIZE * 3},
        {Gui.TILE_SIZE * 3, Gui.TILE_SIZE * 3},
        {Gui.TILE_SIZE, Gui.TILE_SIZE},
        {Gui.TILE_SIZE, Gui.TILE_SIZE},
        {Gui.TILE_SIZE, Gui.TILE_SIZE},
        {Gui.TILE_SIZE, Gui.TILE_SIZE * Gui.HEIGHT_SCALE},
        {Gui.TILE_SIZE * 2, Gui.TILE_SIZE * 3}

    };
    ///////////////
    //Constuctor
    //////////////
    public Gui(int width, int height, Input input, int[][] chunk, int[][] envChunk) {
        images = new Images("Images/Enviroment/Tiles");
        envImages = new BufferedImage[] {
            new BufferedImage(1, 1, Transparency.BITMASK),
            tempImages.getImage("X"),
            tempImages.getImage("X").getSubimage(1, 1, 22, 22),
            tempImages.getImage("tree1"),
            tempImages.getImage("tree2"),
            tempImages.getImage("flowers").getSubimage(0, 0, 24, 24),
            tempImages.getImage("flowers").getSubimage(24, 0, 24, 24),
            tempImages.getImage("flowers").getSubimage(0, 24, 24, 24),
            tempImages.getImage("lilypad"),
            tempImages.getImage("cactus")
        };
        this.chunk = chunk;
        this.envChunk = envChunk;
        this.width = WIDTH;
        this.height = HEIGHT;
        // JFrame setup
        this.setSize(width, height);
        frame.setSize(width, height);

        frame.setVisible(true);
        this.setVisible(true);

        frame.addMouseListener(input);
        frame.addKeyListener(input);
        frame.setFocusable(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        // End JFrame setup
        drawQueue = new ArrayList<GraphicsRunnable>();
    }

//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 

    // Runs every frame and draws stuff to the screen.
    // Called internally by Swing.
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        // Antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Go through every item in the queue and draw it.
        for(int i = 0; i < drawQueue.size(); i++){
            drawQueue.get(i).draw(g2d);
        }
        // Prevent memory leaks
        drawQueue.clear();
    }
    // Allows other classes to run custom draw code.
    public void addToQueue(GraphicsRunnable g){
        drawQueue.add(g);
    }
    // Draws background, trees, environment, etc for now.
    public void background(int r, int g, int b){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                g2d.setColor(new Color(r, g, b));
                g2d.fillRect(0, 0, width, height);
            }
        });
    }
    public void drawTiles(boolean inEnvMode){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                for(int y = 0; y < 10; y++){
                    for(int x = 0; x < 10; x++){
                        g2d.drawImage(images.getImage(chunk[y][x] - 1), x * TILE_SIZE + 100, (int)(y * TILE_SIZE * HEIGHT_SCALE + 100), TILE_SIZE, (int)(TILE_SIZE * HEIGHT_SCALE)+ 1, null);
                    }
                }
                for(int y = 0; y < 10; y++){
                    for(int x = 0; x < 10; x++){
                        g2d.drawImage(envImages[envChunk[y][x]], x * TILE_SIZE + 100, (int)(y * TILE_SIZE * HEIGHT_SCALE + 100), (int)possibleDimensions[envChunk[y][x]][0], (int)possibleDimensions[envChunk[y][x]][1], null);
                    }
                }
                for(int y = 0; y < 10; y++){
                    for(int x = 0; x < 4; x++){
                        if(inEnvMode){
                            if(x + (y * 4) < envImages.length){
                                g2d.drawImage(envImages[x + (y * 4)], x * 50 + 800, y * 50 + 100, 50, 50, null);
                            }

                        } else {
                            if(x + (y * 4) < images.numImages()){
                                g2d.drawImage(images.getImage(x + (y * 4)), x * 50 + 800, y * 50 + 100, 50, 50, null);
                            }
                        }
                    }
                }
            }
        });
    }
    public void drawGrid(){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                g2d.setColor(Color.BLACK);
                for(int y = 0; y < 11; y ++){ 
                    g2d.drawLine(100, (int)(y * TILE_SIZE * HEIGHT_SCALE + 100), 100 + 10 * TILE_SIZE, (int)(y * TILE_SIZE * HEIGHT_SCALE + 100));
                }
                
                for(int x = 0; x < 11; x ++){ 
                    g2d.drawLine(x * TILE_SIZE + 100, 100, x * TILE_SIZE + 100, 100 + (int)(10 * TILE_SIZE * HEIGHT_SCALE));
                }
                
                for(int y = 0; y < 11; y ++){ 
                    g2d.drawLine(800, y * 50  + 100, 1000, y * 50 + 100);
                }
                
                for(int x = 0; x < 4; x ++){ 
                    g2d.drawLine(x * 50 + 800, 100, x * 50 + 800, 600);
                }
                g2d.drawRect(800, 100, 200, 500);
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRect(800 + (((selectedType - 1) % 4) * 50), 100 + (int)(Math.floor((selectedType - 1) / 4) * 50), 50, 50);
            }
        });
    }
    public void drawButton(Rectangle button, String text){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                g2d.setColor(Color.BLACK);
                if(text.equals("Saved!")){
                    g2d.setColor(Color.GRAY);
                }
                g2d.drawRect((int)button.getX(), (int)button.getY(), (int)button.getWidth(), (int)button.getHeight());
                g2d.drawString(text, (int)button.getX() + 20, (int)button.getY() + 20);
            }     
        });
    }

    public void updateChunk(int[][] c, int[][] e){
        chunk = c;
        envChunk = e;
    }
    // Return the width and height of the 
    public double width() { return width; }
    public double height() { return height; }
    public void setSelectedType(int type){
        selectedType = type;
    }
}
