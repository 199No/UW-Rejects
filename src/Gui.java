package src;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import javax.swing.*;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.awt.image.AffineTransformOp;
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
    public static final boolean showGridOverlay = false && Game.inDebugMode;
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
    // Draws a shadow for the given Entity.
    public void drawShadow(Entity e){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                // @see javadoc for AffineTranform
                double shearFactor = 0.5; // Determines the "angle" at which the shadow projects.

                BufferedImage eImage = e.getImage();

                BufferedImage result = new BufferedImage( // Elongated image to account for shear
                    eImage.getWidth() + (int)(eImage.getWidth() * shearFactor),
                    eImage.getHeight(), Transparency.BITMASK);
                // Used to apply the shear onto the image
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getShearInstance(shearFactor, 0), null);
                // Apply shear
                result = op.filter(toShadow(eImage), result);
                // Draw the final result at the correct position
                g2d.drawImage(
                    result,                        // Correction because of shear
                    (int)(absToScreenX(e.getX()) - (e.getWidth() * shearFactor) ), 
                    (int)absToScreenY(e.getY()) + (int)(2 * e.getHeight()), 
                    (int)(e.getWidth() * 1.5), 
                    -(int)e.getHeight(), 
                    null
                );
            }
        });
    }
    // Draws an Entity and its shadow.
    public void drawEntity(Entity e){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                // Draw the shadow behind the player
                drawShadow(e);
                // Draw the player image in screen space
                g2d.drawImage(
                    e.getImage(), 
                    (int)absToScreenX(e.getX()), 
                    (int)absToScreenY(e.getY()),
                    (int)e.getWidth(), 
                    (int)e.getHeight(),
                    null
                );
                if(Game.inDebugMode){
                    // Draw hitbox
                    drawHitbox(e);
                    // Draw outline rectangle
                    g2d.drawRect((int)absToScreenX(e.getX()), (int)absToScreenY(e.getY()), (int)e.getWidth(), (int)e.getHeight());
                }
            }
        });
    }
    // Draw one 10x10 "chunk" of tiles.
    public void drawChunk(Chunk c){
        drawQueue.add(new GraphicsRunnable() {
            BufferedImage tileImage;

            public void draw(Graphics2D g2d){
                // Coordinates of the top-left of this chunk
                double[] chunkCoords = Gui.chunkToScreen(c.x(), c.y());

                for(int y = 0; y < 10; y++){
                    for(int x = 0; x < 10; x++){
                        // Get the image for this tile
                        tileImage = tileImages.getImage(c.getTile(x, y)-1);
                        // Special condition: water tile is animated
                        if(c.getTile(x, y) - 1 == 17){
                            tileImage = waterAnimation.getFrame();
                        }
                        // Draw the image for this tile
                        g2d.drawImage(
                            (tileImage), 
                            (int)(chunkCoords[0] + (x * TILE_SIZE)), 
                            (int)(chunkCoords[1] + (y * (TILE_SIZE * HEIGHT_SCALE))), 
                            TILE_SIZE, 
                            (int)(TILE_SIZE * HEIGHT_SCALE), 
                            null
                        );
                        // Draw a rectangle on the grid, same size as the image
                        if(showGridOverlay) 
                            g2d.drawRect(
                                (int)(chunkCoords[0] + (x * TILE_SIZE)), 
                                (int)(chunkCoords[1] + (y * (TILE_SIZE * HEIGHT_SCALE))), 
                                TILE_SIZE, 
                                (int)(TILE_SIZE * HEIGHT_SCALE)
                            );

                    }
                }
            }
        });
    }
    // Draw the hitbox for a given Entity.
    public void drawHitbox(Entity e){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                if(Game.inDebugMode){
                    // Get the hitbox
                    Rectangle hitbox = e.getAbsHitbox();
                    // Convert absolute coords to screenspace
                    double[] coords = absToScreen((hitbox.getX()), (hitbox.getY()));
                    // Draw a red rectangle representing the hitbox
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
    // Recolors a BufferedImage so that it is gray and translucent
    public BufferedImage toShadow(BufferedImage image){
        // Color of the final shadow (usually black)
        Color color = new Color(0, 0, 0);
        // Result image
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), Transparency.TRANSLUCENT);

        // Copy the alpha channel from the original image
        Graphics2D g = result.createGraphics();
        g.drawImage(image, 0, 0, null);

        // Set the composite rule to only affect non-transparent pixels
        // @see https://ssp.impulsetrain.com/porterduff.html
        g.setComposite(AlphaComposite.SrcIn.derive(0.3f));

        // Set the desired color and fill the entire image
        g.setColor(color);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        
        // Return the final result
        return result;
        
    }
    
    /////////////////////////////////////////////////////
    /// UTILITY (static)
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
        return new double[] {(xTiles * TILE_SIZE - sCameraX) + WIDTH/2, ((yTiles * TILE_SIZE - sCameraY) + HEIGHT/2 + TILE_SIZE /2) * HEIGHT_SCALE};
    }
    public static double[] chunkToScreen(double xChunks, double yChunks){
        return new double[] {(xChunks * TILE_SIZE * 10 - sCameraX) + WIDTH / 2, ((yChunks * TILE_SIZE * 10 - sCameraY) + HEIGHT / 2 + TILE_SIZE / 2) * HEIGHT_SCALE};
    }

}