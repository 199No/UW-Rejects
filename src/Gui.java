package src;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import javax.swing.*;

import Enemies.Enemies;

import java.awt.*;
import java.awt.geom.AffineTransform;
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
    public static final int TILE_SIZE = 68;
    public static final double FOCAL_LENGTH = 720 / Math.sqrt(2); // 720 / sqrt(2)
    public static final double CAMERA_ANGLE = Math.PI * 0.10;
    public static final double Y_OFFSET = -1200;
    public static final double Z_OFFSET = -1720;
    // Where things get queued up to be drawn. 
    // Instead of draw commands being fired whenever, allows things to be drawn all at once at the end of the frame.
    // Fixes timing issues.
    ArrayList<GraphicsRunnable> drawQueue;
    // You need a frame to draw things on.
    JFrame frame = new JFrame("The Divided Realms INDEV");
    // Preloading. To be deprecated
    BufferedImage[] playerImages;
    // The guy she tells you not to worry about (better image loading)
    Images images;
    Images tileImages;
    Rectangle chunkUnloadBoundary = new Rectangle(-(TILE_SIZE * 10), -(TILE_SIZE * 10), Gui.WIDTH + (TILE_SIZE * 20), Gui.HEIGHT + (TILE_SIZE * 20));
    Animation slimeAnimation;
    StatefulAnimation playerAnimation;
    ////////// CAMERA ///////////
    double cameraX;
    double cameraY;
    static double sCameraX;
    static double sCameraY; // Static version of camera coords
    ///////////////
    //Constuctor
    //////////////
    public Gui(int width, int height, Input input) {
        // Guess what this does.
        images = new Images("Images", Transparency.BITMASK);
        tileImages = new Images("Images/Enviroment/Tiles", Transparency.OPAQUE);
        // Define a constantly running Animation for the slime (soon to be better)
        slimeAnimation = new Animation(images.getImage("slimeSheet"), 3, 3, 7, 150, true);
        slimeAnimation.start(); 
        playerAnimation = new StatefulAnimation(100, 3, 4,
            new int[][] {{0,1,2,3}, {4,5}, {6,7,8,9}}, images.getImage("Player Dashing"), true);

        // Honestly this could be a stateful animation.
        // TODO: fix.
        playerImages = new BufferedImage[5];
        // Load up all the player images (to be deprectated)
        playerImages[0] = images.getImage("Player spritesheet").getSubimage(0, 0, 96, 96);
        playerImages[1] = images.getImage("Player spritesheet").getSubimage(96, 0, 96, 96);
        playerImages[2] = images.getImage("Player spritesheet").getSubimage(0, 96, 96, 96);
        playerImages[3] = images.getImage("Player spritesheet").getSubimage(96, 96, 96, 96);
        playerImages[4] = images.getImage("Player spritesheet");

        this.width = WIDTH;
        this.height = HEIGHT;
        this.cameraX = 0;
        this.cameraY = 0;
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
        // Go through every item in the queue and draw it.
        for(int i = 0; i < drawQueue.size(); i++){
            drawQueue.get(i).draw(g2d);
        }
        // Prevent memory leaks (turns out adding 10-50 items to an ArrayList every frame gets slow fast)
        drawQueue.clear();
    }
    // Allows other classes to run custom draw code.
    public void addToQueue(GraphicsRunnable g){
        drawQueue.add(g);
    }

    /////////////////////////////////////////////////
    /// DRAW METHODS
    /////////////////////////////////////////////////



    // Draws background, trees, environment, etc for now.
    public void background(int r, int g, int b){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                for(int y = 0; y < 18; y++){
                    for(int x = 0; x < 34; x++){
                        g2d.drawImage(tileImages.getImage(0), x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                    }
                }
            }
        });
    }
    // Draws a number to the screen, usually the FPS. Could really be anything.
    public void displayFPS(int n){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                g2d.setColor(new Color(255, 255, 255));
                g2d.drawString(String.valueOf(n), 40, 60);
            }
        });
    }
    // Draw the player based on animations and current state.
    public void drawPlayers(ArrayList<Player> players){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                    for(int i = 0; i < players.size(); i++){
                    BufferedImage playerImage = playerImages[0];
                    // TODO: Get a single direction int from player and use that + an array index instead.
                    if(players.get(i).getXDir() == 1){
                        if(players.get(i).getYDir() == -1){
                            playerImage = playerImages[2];
                        }
                        
                        else // if players.get(i).getYDir() == 1 OR players.get(i).getYDir == 0
                        {
                            playerImage = playerImages[0];
                        }
                        
                    }
                    if(players.get(i).getXDir() == -1){
                        if(players.get(i).getYDir() == -1){
                            playerImage = playerImages[3];
                        }
                        
                        else // if players.get(i).getYDir() == 1 OR players.get(i).getYDir == 0
                        {
                            playerImage = playerImages[1];
                        }
                    }
                    double[] playerScreenPos = absToScreen(players.get(i).getxPos(), players.get(i).getyPos());
                    double[] player3dPos = screenTo3D(playerScreenPos[0], playerScreenPos[1]);

                    // How much to vertically scale the final shadow (for adjustments)
                    double shadowYScaleFactor = 0.8;
                    // The ratio between the player IMAGE size and the player's actual size, so that the shadow gets drawn the right size.
                    double playerToTileX = (double)TILE_SIZE/(double)playerImage.getWidth();
                    double playerToTileY = (double)TILE_SIZE/(double)playerImage.getHeight();
                    // How much to shear the shadow (basically shadow angle)
                    double shearFactor = -0.5;

                    // Get an affine transform to work with
                    AffineTransform shadowTransform = AffineTransform.getScaleInstance(1, 1);
                    // Move the shadow image to where it needs to be
                    shadowTransform.translate(
                                          // Because the image shears from the bottom up it moves the "feet" of the shadow,
                                          // so we account for that and also the imminent scaling of the image.
                        (player3dPos[0] + playerImage.getWidth() * shearFactor * playerToTileX * shadowYScaleFactor),
                                                  // Flipping the image puts it above the player's head so we move it to be below the feet instead.
                        (double)((player3dPos[1] + 2*TILE_SIZE) - playerImage.getHeight() * (1-shadowYScaleFactor) * playerToTileY)
                    );
                    // Shear the image so it is at the right angle
                    shadowTransform.shear(shearFactor, 0);
                    // Rescale the image so it appears the right size
                    shadowTransform.scale(playerToTileX, -playerToTileY * shadowYScaleFactor);
                    // Draw the player and its shadow
                    g2d.drawImage(playerImage, (int)player3dPos[0], (int)player3dPos[1], TILE_SIZE, TILE_SIZE, null);
                    g2d.drawImage(toShadow(playerImage), shadowTransform, null);
                }
            }
        });
    }

    public void drawEnemies(ArrayList<Enemies> enemies){
        //given an arraylist type enemies
        //draw enemies based on their x and y positon {use getxPos() getyPos()}
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                for(int i = 0; i < enemies.size(); i ++){

                    BufferedImage slimeImage = slimeAnimation.getFrame();
                    Rectangle slimeHitbox = enemies.get(i).getHitbox();
                    double[] screenPos = absToScreen(enemies.get(i).getxPos(), enemies.get(i).getyPos());
                    double[] finalPos = screenTo3D(screenPos[0], screenPos[1]);
                    g2d.drawImage(slimeImage, (int)finalPos[0], (int)finalPos[1], TILE_SIZE, TILE_SIZE, null);
                    g2d.drawRect((int)slimeHitbox.getX(),(int)slimeHitbox.getY(),(int)slimeHitbox.getWidth(),(int)slimeHitbox.getHeight());
                }
            }
        });

    }
    public void drawChunk(Chunk c){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                double[] chunkCoords = Gui.chunkToScreen(c.x(), c.y());
                for(int y = 0; y < 10; y++){
                    for(int x = 0; x < 10; x++){
                        // Apply 3d transform to this tile coords
                        double[] threeDCoords = Gui.screenTo3D(chunkCoords[0] + (x * TILE_SIZE), chunkCoords[1] + (y * TILE_SIZE));
                        // Take the y pos of the next tile down and subract the y pos of this tile to get the difference in height between this tile and the next one down.                        
                        double threeDTileSize = Gui.screenTo3D(0, chunkCoords[1] + ((y + 1) * TILE_SIZE) )[1] - threeDCoords[1] + 1;
                        // If this tile is off screen don't draw it.
                        if(threeDCoords[1] > Gui.WIDTH + 100){
                            return;
                        }
                        // Otherwise, do.
                        g2d.drawImage(tileImages.getImage(c.getTile(x, y)-1), (int)threeDCoords[0], (int)threeDCoords[1], TILE_SIZE, (int)threeDTileSize, null);
                    }
                }
            }
        });
    }
    public void drawHitboxes(ArrayList<Player> players, ArrayList<Enemies> enemies){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){

                for(int p = 0; p < players.size(); p++){
                    g2d.drawImage(images.getImage("Square1"), null, (int) players.get(p).getHitbox().getX(), (int) players.get(p).getHitbox().getY());
                }
                for(int e = 0; e < enemies.size(); e++){
                    g2d.drawImage(images.getImage("Square1"), null, (int) enemies.get(e).getHitbox().getX(), (int) enemies.get(e).getHitbox().getY());  
                }
            }
        });
    }
    //////////////////////////////////////////////////
    /// CAMERA
    //////////////////////////////////////////////////
    
    public double cameraX(){
        return cameraX;
    }
    public double cameraY(){
        return cameraY;
    }
    // Move camera BY an amount.
    public void moveCamera(double x, double y){
        cameraX += x;
        cameraY += y;
        sCameraX = cameraX;
        sCameraY = cameraY;
    }
    public void moveCameraTo(double x, double y){
        cameraX = x;
        cameraY = y;
        sCameraX = cameraX;
        sCameraY = cameraY;
    }
    public BufferedImage toShadow(BufferedImage image){
        Color color = new Color(0, 0, 0);
        // Create a copy of the image to avoid modifying the original
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), Transparency.TRANSLUCENT);

        // Copy the alpha channel from the original image
        Graphics2D g = result.createGraphics();
        g.drawImage(image, 0, 0, null);

        // Set the composite rule to only affect non-transparent pixels
        // See https://ssp.impulsetrain.com/porterduff.html
        g.setComposite(AlphaComposite.SrcIn.derive(0.2f));

        // Set the desired color and fill the entire image
        g.setColor(color);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        
        
        return result;
        
    }
    // Absolute (pixels) to screenspace
    public static double[] absToScreen(double x, double y){
        return new double[] {x - sCameraX - 25 + WIDTH / 2, y - sCameraY  - 25 + HEIGHT / 2};
    }   
    public static double[] tileToScreen(double xTiles, double yTiles){
        return new double[] {(xTiles * TILE_SIZE - sCameraX) + WIDTH/2, (yTiles * TILE_SIZE - sCameraY) + HEIGHT/2};
    }
    public static double[] chunkToScreen(double xChunks, double yChunks){
        return new double[] {(xChunks * TILE_SIZE * 10 - sCameraX) + WIDTH / 2, (yChunks * TILE_SIZE * 10 - sCameraY) + HEIGHT / 2};
    }
    // Screenspace (2D, pixels) to 2.5D (pixels)
    public static double[] screenTo3D(double x, double y){
        return new double[] {
            x,
            ((Y_OFFSET)* Math.sin(CAMERA_ANGLE)) * (FOCAL_LENGTH / ((y + Z_OFFSET) * Math.cos(CAMERA_ANGLE))) - TILE_SIZE

        };
    }
}