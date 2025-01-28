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
// TODO: Make Gui a static class
public class Gui extends JPanel{
    ///////////////
    //Properties
    ////////////// 
    GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment()
                                        .getDefaultScreenDevice()
                                            .getDefaultConfiguration();
    // Made public so that other classes can see them
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final double HEIGHT_SCALE = (double)2/(double)3;
    public static final int PLAYER_SIZE = 24;
    public static final int TILE_SIZE = 60;
    // Queues up all the draw commands in a frame so that they can all be executed at the end of the frame at the correct time.
    ArrayList<GraphicsRunnable> drawQueue;
    // You need a frame to draw things on.
    JFrame frame = new JFrame("The Divided Realms INDEV");
    // TODO: Make this a stateful animation.

    // General images
    Images images;
    // Images only for tiles
    Images tileImages;

    Rectangle chunkUnloadBoundary = new Rectangle(-(TILE_SIZE * 10), -(TILE_SIZE * 10), Gui.WIDTH + (TILE_SIZE * 20), Gui.HEIGHT + (TILE_SIZE * 20));

    Animation slimeAnimation;
    StatefulAnimation[] playerDashAnimations;
    StatefulAnimation[] playerIdleAnimations;
    StatefulAnimation player1Idle;
    StatefulAnimation player2Idle;
    Animation waterAnimation;

    ////////// CAMERA ///////////
    double cameraX;
    double cameraY;
    static double sCameraX;
    static double sCameraY; // Static version of camera coords

    ///////////////
    //Constuctor
    //////////////
    public Gui(int width, int height, Input input) {
        // General images, uses bitmask transparency
        images = new Images("Images", Transparency.BITMASK);
        // Images for tiles only
        tileImages = new Images("Images/Enviroment/Tiles", Transparency.OPAQUE);

        // Define a constantly running Animation for the slime (soon to be better)
        slimeAnimation = new Animation(images.getImage("slime"), 4, 2, 7, 100, true);
        slimeAnimation.start(); 

        playerIdleAnimations = new StatefulAnimation[] {

            new StatefulAnimation(Integer.MAX_VALUE, 2, 2, new int[][]{{0}, {1}, {2}, {3}}, images.getImage("playerIdle"), true),
            new StatefulAnimation(Integer.MAX_VALUE, 2, 2, new int[][]{{0}, {1}, {2}, {3}}, images.getImage("player2Idle"), true)
        };

        playerDashAnimations = new StatefulAnimation[] {
            // Player 1 dash
            new StatefulAnimation(63, 3, 2, new int[][] {{0,1,2,3}, {4,5}, {4,5}, {4,3,2,1}}, images.getImage("playerDash"), true),
            // Player 2 dash
            new StatefulAnimation(63, 3, 2, new int[][] {{0,1,2,3}, {4,5}, {4,5}, {4,3,2,1}}, images.getImage("player2Dash"), true)

        };

        waterAnimation = new Animation(images.getImage("waterTile"), 3, 1, 3, 250, true);
        waterAnimation.start();
        this.cameraX = 0;
        this.cameraY = 0;
        // JFrame setup
        this.setSize(width, height);
        frame.setSize(width, height);

        frame.setVisible(true);
        this.setVisible(true);

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
                g2d.setColor(new Color(r, g, b));
                g2d.fillRect(0, 0, Gui.WIDTH, Gui.HEIGHT);
            }
        });
    }
    // Draws a number to the screen, usually the FPS. Could really be anything.
    public void displayFPS(int n){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                g2d.setColor(new Color(0, 175, 255));
                g2d.drawString(String.valueOf(n), 40, 60);
                
            }
        });
    }
    public BufferedImage getPlayerImage(Player player, Input input){
        BufferedImage playerImage = playerDashAnimations[player.playernum - 1].getCurFrame(); // Default idle animation frame

        // Handle dash animation
        if(player.getIsDashing()){
            StatefulAnimation dashAnimation = playerDashAnimations[player.playernum - 1];
                               // time since last dash 
            int currentState = (int)(((int)System.currentTimeMillis() - input.getLastDash(player)) / 250);

            if(dashAnimation.getCurState() != currentState) dashAnimation.setState(currentState);

            playerImage = dashAnimation.getCurFrame();
        }
        else /* if player is not dashing */{
            StatefulAnimation idleAnim = playerIdleAnimations[player.playernum - 1];
            if (player.getYDir() == -1) {
                // Player is facing up
                if (player.getXDir() == -1) {
                    // Moving left while facing up
                    idleAnim.setState(3);
                } else if (player.getXDir() == 1) {
                    // Moving right while facing up
                    idleAnim.setState(2);
                } else {
                    // Default to right-facing up when no horizontal movement
                    idleAnim.setState(3);
                }
            } else {
                // Player is not facing up
                if (player.getXDir() == -1) {
                    // Moving left
                    idleAnim.setState(1);
                } else if (player.getXDir() == 1) {
                    // Moving right
                    idleAnim.setState(0);
                } else {
                    // Default to idle right-facing when no movement
                    idleAnim.setState(0);
                }
            }
            playerImage = idleAnim.getCurFrame();
        }
        return playerImage;
    }

    // Draw the player based on animations and current state.
    public void drawPlayer(Player player, Input input){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){

                    // TODO: Make this a function call
                    // // Get an affine transform to work with
                    // AffineTransform shadowTransform = AffineTransform.getScaleInstance(1, 1);
                    

                    // double[] playerScreenPos = absToScreen(player.getxPos(), player.getyPos());

                    // // How much to vertically scale the final shadow (for adjustments)
                    // double shadowScaleFactor = 0.8;
                    // // The ratio between the player IMAGE size and the player's actual size, so that the shadow gets drawn the right size.
                    // double playerToTileX = (double)TILE_SIZE/(double)playerImage.getWidth();
                    // double playerToTileY = (double)TILE_SIZE/(double)playerImage.getHeight();
                    // // How much to shear the shadow (basically shadow angle)
                    // double shearFactor = -0.5;

                    // // Move the shadow image to where it needs to be
                    // shadowTransform.translate(
                    //                       // Because the image shears from the bottom up it moves the "feet" of the shadow,
                    //                       // so we account for that and also the imminent scaling of the image.
                    //     (playerScreenPos[0] + playerImage.getWidth() * shearFactor * playerToTileX * shadowScaleFactor),
                    //                               // Flipping the image puts it above the player's head so we move it to be below the feet instead.
                    //     (double)((playerScreenPos[1] + 2*TILE_SIZE) - playerImage.getHeight() * (1-shadowScaleFactor) * playerToTileY)
                    // );
                    // // Shear the image so it is at the right angle
                    // shadowTransform.shear(shearFactor, 0);
                    // // Rescale the image so it appears the right size
                    // shadowTransform.scale(playerToTileX, -playerToTileY * shadowScaleFactor);
                    // // Draw the player and its shadow
                    
                    // if(player.getXDir() <= 0 && player.getIsDashing()){
                    //     g2d.drawImage(playerImage, (int)playerScreenPos[0] + TILE_SIZE, (int)playerScreenPos[1], -TILE_SIZE, TILE_SIZE, null);
                    //     shadowTransform.scale(-1, 1);
                    //     shadowTransform.translate(-TILE_SIZE / playerToTileX, 0);
                    //     g2d.drawImage(toShadow(playerImage), shadowTransform, null);
                    // } else {
                    //     g2d.drawImage(playerImage, (int)playerScreenPos[0], (int)playerScreenPos[1], TILE_SIZE, TILE_SIZE, null);
                    //     g2d.drawImage(toShadow(playerImage), shadowTransform, null);

                    // }
                    if(Game.inDebugMode){
                        g2d.drawString("" + (int)(player.getX() / TILE_SIZE) + ", " + (int)(player.getY() / TILE_SIZE), (int)absToScreenX(player.getX() + 10), (int)absToScreenY(player.getY() - 30));
                    }
                } 
            
        });
    }
    public void drawShadow(Entity e){

    }

    public void drawEntity(){

    }



    // TODO: Make this a function call
    public void drawEnemies(ArrayList<Enemies> enemies){
        //given an arraylist type enemies
        //draw enemies based on their x and y positon {use getxPos() getyPos()}
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                for(int i = 0; i < enemies.size(); i ++){

                    BufferedImage slimeImage = slimeAnimation.getFrame();
                    double[] screenPos = absToScreen(enemies.get(i).getX(), enemies.get(i).getY());
                    g2d.drawImage(slimeImage, (int)screenPos[0], (int)screenPos[1], TILE_SIZE, TILE_SIZE, null);
                    g2d.drawImage(toShadow(slimeImage), (int)screenPos[0], (int)(screenPos[1] + 2 * TILE_SIZE), TILE_SIZE, -TILE_SIZE, null);
                }
            }
        });

    }

    public void drawChunk(Chunk c){
        drawQueue.add(new GraphicsRunnable() {
            BufferedImage tileImage;
            public void draw(Graphics2D g2d){
                double[] chunkCoords = Gui.chunkToScreen(c.x(), c.y());
                for(int y = 0; y < 10; y++){
                    for(int x = 0; x < 10; x++){
                        tileImage =
                        tileImages.getImage(c.getTile(x, y)-1);
                        if(c.getTile(x, y) - 1 == 17){
                            tileImage = waterAnimation.getFrame();
                        }
                        //g2d.setColor(Color.GREEN);
                        //g2d.fillOval((int)threeDCoords[0] - 2, (int)threeDCoords[1] - 2, 4, 4);
                        g2d.drawImage(tileImage, (int)(chunkCoords[0] + (x * TILE_SIZE)), (int)(chunkCoords[1] + (y * (TILE_SIZE * 2/3))), TILE_SIZE, (TILE_SIZE * 2/3), null);

                    }
                }
            }
        });
    }
    // TODO: Make a function call
    public void drawEnvObject(EnvObject e, Graphics2D g2d){
        // How much to shear the shadow (basically shadow angle)
        double shearFactor = -0.5;

        double[] objCoords = absToScreen(e.getX(), e.getY());

        // How much to vertically scale the final shadow (for adjustments)
        double shadowScaleFactor = 0.8;
        // The ratio between the player IMAGE size and the player's actual size, so that the shadow gets drawn the right size.
        double playerToTileX = (double)e.getWidth()/(double)e.getImage().getWidth();
        double playerToTileY = (double)e.getHeight()/(double)e.getImage().getHeight();

        // Get an affine transform to work with
        AffineTransform shadowTransform = AffineTransform.getScaleInstance(1, 1);
        shadowTransform.translate(objCoords[0] + e.getImage().getWidth() * shearFactor * playerToTileX * shadowScaleFactor, 
                                  (double)((objCoords[1] + 2*e.getHeight()) - e.getImage().getHeight() * (1-shadowScaleFactor) * playerToTileY)
                                );
        // Shear the image so it is at the right angle
        shadowTransform.shear(shearFactor, 0);
        // Rescale the image so it appears the right size
        shadowTransform.scale(playerToTileX, -playerToTileY * shadowScaleFactor);
        // Draw the player and its shadow
        g2d.drawImage(e.getImage(), (int)objCoords[0], (int)objCoords[1], (int)e.getWidth(), (int)(e.getHeight()), null);
        if(!e.isFlat()){
            g2d.drawImage(toShadow(e.getImage()), shadowTransform, null);
        }

        drawHitbox(e);
    }
    public void drawHitbox(Entity e){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                if(Game.inDebugMode){
                    Rectangle hitbox = e.getAbsHitbox();
                    double[] coords = absToScreen((hitbox.getX()), (hitbox.getY()));
                    g2d.setColor(Color.RED);
                    g2d.setStroke(new BasicStroke(3));
                    g2d.drawRect((int)coords[0], (int)coords[1], (int)hitbox.getWidth(), (int)hitbox.getHeight());
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
    
    /////////////////////////////////////////////////////
    /// UTILITY
    /////////////////////////////////////////////////////
    
    
    
    // Absolute (pixels) to screenspace
    public static double absToScreenX(double x){
        return x - sCameraX + WIDTH / 2;
    }   
    public static double absToScreenY(double y){
        return (y - sCameraY + HEIGHT / 2) * HEIGHT_SCALE;
    }   
    public static double[] absToScreen(double x, double y){
        return new double[] {x - sCameraX + WIDTH / 2, (y - sCameraY + HEIGHT / 2) * HEIGHT_SCALE};
    }   
    public static double[] tileToScreen(double xTiles, double yTiles){
        return new double[] {(xTiles * TILE_SIZE - sCameraX) + WIDTH/2, ((yTiles * TILE_SIZE - sCameraY) + HEIGHT/2) * HEIGHT_SCALE};
    }
    public static double[] chunkToScreen(double xChunks, double yChunks){
        return new double[] {(xChunks * TILE_SIZE * 10 - sCameraX) + WIDTH / 2, ((yChunks * TILE_SIZE * 10 - sCameraY) + HEIGHT / 2) * HEIGHT_SCALE};
    }

}