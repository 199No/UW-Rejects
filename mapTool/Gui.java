package mapTool;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import javax.imageio.ImageIO;
import javax.swing.*;

import Enemies.Enemies;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
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
    // Where things get queued up to be drawn. 
    // Instead of draw commands being fired whenever, allows things to be drawn all at once at the end of the frame.
    ArrayList<GraphicsRunnable> drawQueue;
    // You need a frame to draw things on.
    JFrame frame = new JFrame("The Divided Realms Map Tool");
    Images images;
    BufferedImage[] tileImages;
    int[][] chunk = new int[10][10];
    ///////////////
    //Constuctor
    //////////////
    public Gui(int width, int height, Input input, int[][] chunk) {
        images = new Images();
        this.chunk = chunk;
        tileImages = new BufferedImage[]{
            images.getImage("tile_grass"),
            images.getImage("tile_sand"),
            images.getImage("tile_snow"),
            images.getImage("tile_stone"),
            images.getImage("tile_dirt"),
        };
        this.width = WIDTH;
        this.height = HEIGHT;
        // JFrame setup
        this.setSize(width, height);
        frame.setSize(width, height);

        frame.setVisible(true);
        this.setVisible(true);

        frame.addMouseMotionListener(input);
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
    public void drawTiles(){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                for(int y = 0; y < 10; y++){
                    for(int x = 0; x < 10; x++){
                        g2d.drawImage(tileImages[chunk[y][x] - 1], x * 50 + 100, y * 50 + 110, 50, 50, null);
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
                    g2d.drawLine(100, y * 50 + 110, 600, y * 50 + 110);
                }
                
                for(int x = 0; x < 11; x ++){ 
                    g2d.drawLine(x * 50 + 100, 110, x * 50 + 100, 610);
                }
            }
        });
    }
    // Return the width and height of the 
    public double width() { return width; }
    public double height() { return height; }

}
