package src;
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
    JFrame frame = new JFrame("The Divided Realms INDEV");
    // Preloading. To be deprecated
    BufferedImage[] playerImages;
    BufferedImage[] envImages;
    Images images;
    int slimeStep = 0;
    double lastSlimeStep = System.currentTimeMillis();
    Animation slimeAnimation;
    ///////////////
    //Constuctor
    //////////////
    public Gui(int width, int height, Input input) {
        images = new Images();
        slimeAnimation = new Animation(images.getImage("slimeSheet"), 3, 3, 7, 150, true);
        slimeAnimation.start();
        playerImages = new BufferedImage[5];
        try {
            // Load up all the player images (to be deprectated)
            playerImages[0] = images.getImage("Player spritesheet").getSubimage(0, 0, 96, 96);
            playerImages[1] = images.getImage("Player spritesheet").getSubimage(96, 0, 96, 96);
            playerImages[2] = images.getImage("Player spritesheet").getSubimage(0, 96, 96, 96);
            playerImages[3] = images.getImage("Player spritesheet").getSubimage(96, 96, 96, 96);
            playerImages[4] = images.getImage("Player spritesheet");


        } catch (Exception e){e.printStackTrace();}
        // Load environment images
        envImages = new BufferedImage[5];
        try{
            envImages[0] = images.getImage("tile_grass");
        } catch (Exception e){e.printStackTrace();};

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
        super.paintComponent(g);
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
                for(int y = 0; y < 24; y++){
                    for(int x = 0; x < 48; x++){
                        AffineTransform a = AffineTransform.getScaleInstance(0.4, 0.4);
                        a.translate(x* 38.4 * 2.5, y * 38.4 * 2.5);
                        g2d.drawImage(envImages[0], a, null);
                    }
                }
            }
        });
    }
    // Draws a number to the screen, usually the FPS.
    public void displayFPS(int n){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                g2d.setColor(new Color(0, 0, 0));
                g2d.drawString(String.valueOf(n), 40, 60);
            }
        });
    }
    // Draw the player based on animations and current state.
    public void drawPlayer(Player p){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                Image playerImage = playerImages[0];
                AffineTransform f = AffineTransform.getScaleInstance(0.5, 0.5);
                f.translate(p.getxPos() * 2, p.getyPos() * 2);
                if(p.getXDir() == 1){
                    if(p.getYDir() == 1){
                        playerImage = playerImages[2];
                    }
                    if(p.getYDir() == -1){
                        playerImage = playerImages[0];
                    }
                }
                if(p.getXDir() == -1){
                    if(p.getYDir() == 1){
                        playerImage = playerImages[3];
                    }
                    if(p.getYDir() == -1){
                        playerImage = playerImages[1];
                        
                    }
                }
                g2d.drawImage(playerImage, f, null);
            }
        });
    }

    // Return the width and height of the 
    public double width() { return width; }
    public double height() { return height; }

    public void drawEnemies(ArrayList<Enemies> enemies){
        //given an arraylist type enemies
        //draw enemies based on their x and y positon {use getxPos() getyPos()}
        double now = System.currentTimeMillis();
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                for(int i = 0; i < enemies.size(); i ++){
                    //Get the slime sheet image
                    BufferedImage slimeImage = slimeAnimation.getFrame();
                    // Scale the sheet up to the right size
                    AffineTransform a = AffineTransform.getScaleInstance(3, 3);
                    // Move the image to where the slime is and shrink it a bit
                    a.translate((enemies.get(i).getxPos()) / 3, (enemies.get(i).getyPos() - 10) / 3);
                    a.scale(0.2, 0.2);
                    // Draw the final result
                    g2d.drawImage(slimeImage, a, null);
                }
            }
        });
        if(now - lastSlimeStep > 150){
            slimeStep ++;
            lastSlimeStep = now;
            if(slimeStep >= 7){
                slimeStep = 0;
            }
        }

    }
}
