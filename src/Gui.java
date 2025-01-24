package src;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import javax.swing.*;

import Enemies.Enemies;

import java.awt.*;
import java.awt.Taskbar.State;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
//-------------------------------------------------//
//                      Gui                        //
//-------------------------------------------------// 
public class Gui extends JPanel{
    ///////////////
    //Properties
    ////////////// 
    GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment()
                                        .getDefaultScreenDevice()
                                            .getDefaultConfiguration();
    
    // Width and height of the draw area
    int width;
    int height;
    // Made public so that other classes can see them
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final double HEIGHT_SCALE = (double)2/(double)3;
    public static final int PLAYER_SIZE = 24;
    public static final int TILE_SIZE = 60;
    public static final double FOCAL_LENGTH = 720 / Math.sqrt(2); // 720 / sqrt(2)
    public static final double CAMERA_ANGLE = Math.PI * 0.05;
    public static final double Y_OFFSET = -3200;
    public static final double Z_OFFSET = -1720;
    // Where things get queued up to be drawn. 
    // Instead of draw commands being fired whenever, allows things to be drawn all at once at the end of the frame.
    // Fixes timing issues.
    ArrayList<GraphicsRunnable> drawQueue;
    // You need a frame to draw things on.
    JFrame frame = new JFrame("The Divided Realms INDEV");
    // Preloading. To be deprecated
    BufferedImage[] player1Images;
    BufferedImage[] player2Images;

    // The guy she tells you not to worry about (better image loading)
    Images images;
    Images tileImages;
    Rectangle chunkUnloadBoundary = new Rectangle(-(TILE_SIZE * 10), -(TILE_SIZE * 10), Gui.WIDTH + (TILE_SIZE * 20), Gui.HEIGHT + (TILE_SIZE * 20));
    Animation slimeAnimation;
    StatefulAnimation player1DashAnimation;
    StatefulAnimation player2DashAnimation;
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
        
        player1Images = new BufferedImage[5];
        // Load up all the player images (to be deprectated)
        player1Images[0] = images.getImage("playerIdle").getSubimage(0 * PLAYER_SIZE, 0 * PLAYER_SIZE, PLAYER_SIZE, PLAYER_SIZE);
        player1Images[1] = images.getImage("playerIdle").getSubimage(1 * PLAYER_SIZE, 0 * PLAYER_SIZE, PLAYER_SIZE, PLAYER_SIZE);
        player1Images[2] = images.getImage("playerIdle").getSubimage(0 * PLAYER_SIZE, 1 * PLAYER_SIZE, PLAYER_SIZE, PLAYER_SIZE);
        player1Images[3] = images.getImage("playerIdle").getSubimage(1 * PLAYER_SIZE, 1 * PLAYER_SIZE, PLAYER_SIZE, PLAYER_SIZE);
        player1Images[4] = images.getImage("playerIdle");
        player1Images[4] = images.getImage("playerIdle");


        player1DashAnimation = new StatefulAnimation(100, 3, 2,
            new int[][] {{0,1,2,3}, {4,5}, {4,3,2,1}}, images.getImage("playerDash"), true);
        // Honestly this could be a stateful animation.
        // TODO: fix.

        player2Images = new BufferedImage[5];
        // Load up all the player images (to be deprectated)
        player2Images[0] = images.getImage("player2Idle").getSubimage(0 * PLAYER_SIZE, 0 * PLAYER_SIZE, PLAYER_SIZE, PLAYER_SIZE);
        player2Images[1] = images.getImage("player2Idle").getSubimage(1 * PLAYER_SIZE, 0 * PLAYER_SIZE, PLAYER_SIZE, PLAYER_SIZE);
        player2Images[2] = images.getImage("player2Idle").getSubimage(0 * PLAYER_SIZE, 1 * PLAYER_SIZE, PLAYER_SIZE, PLAYER_SIZE);
        player2Images[3] = images.getImage("player2Idle").getSubimage(1 * PLAYER_SIZE, 1 * PLAYER_SIZE, PLAYER_SIZE, PLAYER_SIZE);
        player2Images[4] = images.getImage("player2Idle");

        player2DashAnimation = new StatefulAnimation(100, 3, 2,
        new int[][] {{0,1,2,3}, {4,5}, {4,3,2,1}}, images.getImage("player2Dash"), true);
        waterAnimation = new Animation(images.getImage("waterTile"), 3, 1, 3, 100, true);
        waterAnimation.start();
        this.width = WIDTH;
        this.height = HEIGHT;
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
    // Draw the player based on animations and current state.
    public void drawPlayer(Player player, Input input){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                    double timeSinceLastDash = (int)System.currentTimeMillis() - input.getLastDash(player);
                    BufferedImage playerImage = getPlayerIdle(player)[0]; // Default idle animation frame
                    StatefulAnimation dashAnimation = getPlayerDash(player);

                    // First phase of dash (going into dash)
                    if(timeSinceLastDash >= 0 && timeSinceLastDash < 250){ 
                        if(dashAnimation.getCurState() != 0){ // If animation is not in first phase make it so
                            dashAnimation.setState(0);  // Go to beginning of first phase anim
                            System.out.println("First phase dash");
                        }
                        dashAnimation.setFrameTime(250 / 4); // First phase lasts 250 ms, has 4 frames
                        playerImage = dashAnimation.getCurFrame();
                    } 

                    // Second phase (dashing)
                    else if(timeSinceLastDash >= 250 && timeSinceLastDash < 750){ 
                        if(dashAnimation.getCurState() != 1){ // If animation is not in second phase make it so
                            dashAnimation.setState(1);  // Go to beginning of second phase anim
                            System.out.println("2nd phase dash");
                        }
                        dashAnimation.setFrameTime(100); // Go back to default frame time, to make second phase loop
                        playerImage = dashAnimation.getCurFrame();
                    } 

                    // Third phase (ending dash)
                    else if(timeSinceLastDash >= 750 && timeSinceLastDash < 1000){ 
                        if(dashAnimation.getCurState() != 2){ // If animation is not in third phase make it so
                            dashAnimation.setState(2);  // Go to beginning of third phase anim
                            System.out.println("Thirs phase dash");
                        }
                        dashAnimation.setFrameTime(250 / 4); // Third phase lasts 250 ms, has 4 frames
                        playerImage = dashAnimation.getCurFrame();
                        
                    }
                    else {
                        if (player.getYDir() == -1) {
                            // Player is facing up
                            if (player.getXDir() == -1) {
                                // Moving left while facing up
                                playerImage = getPlayerIdle(player)[3];
                            } else if (player.getXDir() == 1) {
                                // Moving right while facing up
                                playerImage = getPlayerIdle(player)[2];
                            } else {
                                // Default to right-facing up when no horizontal movement
                                playerImage = getPlayerIdle(player)[3];
                            }
                        } else {
                            // Player is not facing up
                            if (player.getXDir() == -1) {
                                // Moving left
                                playerImage = getPlayerIdle(player)[1];
                            } else if (player.getXDir() == 1) {
                                // Moving right
                                playerImage = getPlayerIdle(player)[0];
                            } else {
                                // Default to idle right-facing when no movement
                                playerImage = getPlayerIdle(player)[0];
                            }
                        }

                    }

                    
                    // Get an affine transform to work with
                    AffineTransform shadowTransform = AffineTransform.getScaleInstance(1, 1);
                    

                    double[] playerScreenPos = absToScreen(player.getxPos(), player.getyPos());

                    // How much to vertically scale the final shadow (for adjustments)
                    double shadowScaleFactor = 0.8;
                    // The ratio between the player IMAGE size and the player's actual size, so that the shadow gets drawn the right size.
                    double playerToTileX = (double)TILE_SIZE/(double)playerImage.getWidth();
                    double playerToTileY = (double)TILE_SIZE/(double)playerImage.getHeight();
                    // How much to shear the shadow (basically shadow angle)
                    double shearFactor = -0.5;

                    // Move the shadow image to where it needs to be
                    shadowTransform.translate(
                                          // Because the image shears from the bottom up it moves the "feet" of the shadow,
                                          // so we account for that and also the imminent scaling of the image.
                        (playerScreenPos[0] + playerImage.getWidth() * shearFactor * playerToTileX * shadowScaleFactor),
                                                  // Flipping the image puts it above the player's head so we move it to be below the feet instead.
                        (double)((playerScreenPos[1] + 2*TILE_SIZE) - playerImage.getHeight() * (1-shadowScaleFactor) * playerToTileY)
                    );
                    // Shear the image so it is at the right angle
                    shadowTransform.shear(shearFactor, 0);
                    // Rescale the image so it appears the right size
                    shadowTransform.scale(playerToTileX, -playerToTileY * shadowScaleFactor);
                    // Draw the player and its shadow
                    
                    if(player.getXDir() <= 0 && player.getIsDashing()){
                        g2d.drawImage(playerImage, (int)playerScreenPos[0] + TILE_SIZE, (int)playerScreenPos[1], -TILE_SIZE, TILE_SIZE, null);
                        shadowTransform.scale(-1, 1);
                        shadowTransform.translate(-TILE_SIZE / playerToTileX, 0);
                        g2d.drawImage(toShadow(playerImage), shadowTransform, null);
                    } else {
                        g2d.drawImage(playerImage, (int)playerScreenPos[0], (int)playerScreenPos[1], TILE_SIZE, TILE_SIZE, null);
                        g2d.drawImage(toShadow(playerImage), shadowTransform, null);

                    }
                    
                } 
            
        });
    }

    public BufferedImage[] getPlayerIdle(Player player){
        BufferedImage[] idle = new BufferedImage[5];
        if(player.playernum == 1){
           idle = player1Images;
        }else if(player.playernum == 2){
            idle = player2Images;
        }
        return idle;
    }

    public StatefulAnimation getPlayerDash(Player player){
        if(player.playernum == 1){
            return player1DashAnimation;
         }else if(player.playernum == 2){
            return player2DashAnimation;
         }
         return null;
    }

    public void drawEnemies(ArrayList<Enemies> enemies){
        //given an arraylist type enemies
        //draw enemies based on their x and y positon {use getxPos() getyPos()}
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                for(int i = 0; i < enemies.size(); i ++){

                    BufferedImage slimeImage = slimeAnimation.getFrame();
                    double[] screenPos = absToScreen(enemies.get(i).getxPos(), enemies.get(i).getyPos());
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
                EnvObject[] envObjects = c.getEnvObjects();
                for(int y = 0; y < 10; y++){
                    for(int x = 0; x < 10; x++){
                        // Apply 3d transform to this tile coords
                        //double[] threeDCoords = Gui.screenTo3D(chunkCoords[0] + (x * TILE_SIZE), chunkCoords[1] + (y * TILE_SIZE));
                        // Take the y pos of the next tile down and subract the y pos of this tile to get the difference in height between this tile and the next one down.                        
                        //double threeDTileSize = Gui.screenTo3D(0, chunkCoords[1] + ((y + 1) * TILE_SIZE) )[1] - threeDCoords[1] + 1;
                        // If this tile is off screen don't draw it.
                        //if(threeDCoords[1] > Gui.WIDTH + 100){ //|| threeDCoords[0] - (Gui.WIDTH / 2) < 0){
                        //    return;
                        //}
                        // Otherwise, do.
                        //A = screenTo3D(threeDCoords[0] + TILE_SIZE - (Gui.WIDTH / 2), threeDCoords[1])[0];
                        //B = screenTo3D(threeDCoords[0] - (Gui.WIDTH / 2), threeDCoords[1])[0];
                        
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
    public void drawEnvObject(EnvObject e, Graphics2D g2d){
        // How much to shear the shadow (basically shadow angle)
        double shearFactor = -0.5;

        double[] objCoords = absToScreen(e.x(), e.y());

        // How much to vertically scale the final shadow (for adjustments)
        double shadowScaleFactor = 0.8;
        // The ratio between the player IMAGE size and the player's actual size, so that the shadow gets drawn the right size.
        double playerToTileX = (double)e.width()/(double)e.getImage().getWidth();
        double playerToTileY = (double)e.height()/(double)e.getImage().getHeight();

        // Get an affine transform to work with
        AffineTransform shadowTransform = AffineTransform.getScaleInstance(1, 1);
        shadowTransform.translate(objCoords[0] + e.getImage().getWidth() * shearFactor * playerToTileX * shadowScaleFactor, 
                                  (double)((objCoords[1] + 2*e.height()) - e.getImage().getHeight() * (1-shadowScaleFactor) * playerToTileY)
                                );
        // Shear the image so it is at the right angle
        shadowTransform.shear(shearFactor, 0);
        // Rescale the image so it appears the right size
        shadowTransform.scale(playerToTileX, -playerToTileY * shadowScaleFactor);
        // Draw the player and its shadow
        g2d.drawImage(e.getImage(), (int)objCoords[0], (int)objCoords[1], (int)e.width(), (int)(e.height()), null);
        if(!e.isFlat()){
            g2d.drawImage(toShadow(e.getImage()), shadowTransform, null);
        }

        drawHitbox(e);
    }
    public void drawEnvLayer1(Chunk c, double player1y, double player2y){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                EnvObject[] envObjects = c.getEnvObjects();
                for(int i = 0; i < envObjects.length; i++){
                    if(envObjects[i].getHitbox().getY() < Math.min(player1y, player2y)){
                        drawEnvObject(envObjects[i], g2d);
                    }

                }
            }
        });
    }
        
    public void drawEnvLayer2(Chunk c, double player1y, double player2y){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                EnvObject[] envObjects = c.getEnvObjects();
                double objY;
                for(int i = 0; i < envObjects.length; i++){
                    objY = envObjects[i].getHitbox().getY();
                    if(objY < Math.max(player1y, player2y) && objY > Math.min(player1y, player2y)){
                        drawEnvObject(envObjects[i], g2d);
                    }

                }
            }
        });
    }
        
    public void drawEnvLayer3(Chunk c, double player1y, double player2y){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                EnvObject[] envObjects = c.getEnvObjects();
                for(int i = 0; i < envObjects.length; i++){
                    if(envObjects[i].getHitbox().getY() > Math.max(player1y, player2y)){
                        drawEnvObject(envObjects[i], g2d);
                    }

                }
            }
        });
    }
    public void drawHitboxes(ArrayList<Player> players, ArrayList<Enemies> enemies){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){

                for(int p = 0; p < players.size(); p++){
                    double[] hitbox = players.get(p).getHitboxTopLeft();
                    double[] location = absToScreen(hitbox[0], hitbox[1]);
                    g2d.drawImage(images.getImage("Square1"), (int) location[0] + players.get(p).getWidth()/4, (int) location[1] + players.get(p).getHeight()/4, players.get(p).getWidth()/2, players.get(p).getHeight()/2, null);
                }

                for(int p = 0; p < players.size(); p++){
                    double[] hitbox = {players.get(p).getSwingHitbox().getX(), players.get(p).getSwingHitbox().getY()};
                    double[] location = absToScreen(hitbox[0], hitbox[1]);
                    g2d.drawImage(images.getImage("Square1"), (int) (location[0] +  players.get(p).getSwingHitbox().getWidth()), (int) (location[1] + players.get(p).getSwingHitbox().getHeight()), players.get(p).swingWidth, players.get(p).swingHeight, null);
                }

                for(int e = 0; e < enemies.size(); e++){
                    double[] hitbox = enemies.get(e).getHitboxTopLeft();
                    double[] location = absToScreen(hitbox[0], hitbox[1]);
                    g2d.drawImage(images.getImage("Square1"), (int) location[0] + enemies.get(e).getWidth()/4, (int) location[1] + enemies.get(e).getHeight()/4, enemies.get(e).getWidth()/2, enemies.get(e).getHeight()/2, null);
                }
            }
        });
    }
    public void drawHitbox(EnvObject obj){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                Rectangle hitbox = obj.getHitbox();
                double[] coords = absToScreen((hitbox.getX()), (hitbox.getY()));
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRect((int)coords[0], (int)coords[1], (int)hitbox.getWidth(), (int)hitbox.getHeight());
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
    public BufferedImage toPersp (BufferedImage texture, double A, double B, double C, double D){
        //x1 = (topWidth/40 * x) * (((bottomWidth/topWidth-1)/h)*y+1) + y*(k1/h);


        double startX, endX; // X initial and X final - coords for the ends of each row on the trapezoid
        double k = Math.abs(D - B); // "Overhang" on the trapezoid - how much space between the absolute corner and the beginning of the bottom of the trapezoid
        double topWidth = Math.abs(A) - Math.abs(B); // Top width of the trapz
        double bottomWidth = Math.abs(C) - Math.abs(D); // Bottom width of the trapz
        double h = texture.getHeight(); // Height of the trapz
        double textureX; // X on the image
        double rowWidth, rx; // rowWidth = width based on y, rx = how far along rowWidth x is
        double resultWidth = Math.abs((int)(Math.max(Math.abs(A), Math.abs(C)) - Math.min(Math.abs(B), Math.abs(D))));
        BufferedImage result = new BufferedImage((int)((resultWidth == 0)? 1 : resultWidth), texture.getHeight(), Transparency.BITMASK);

        boolean isInv = (k < 0);
        for(int y = 0; y < result.getHeight(); y++){
            
            rowWidth = (((bottomWidth-topWidth)/h)*y)+topWidth;

            // Find x initial
            startX = y * k/h;
            // Find x final
            endX = startX + rowWidth;
            // Starting at startX, ending at endX...
            for(int x = (int)startX; x < Math.ceil(endX); x++){

                if(isInv){
                    rx=((x - k)-startX)/rowWidth;
                } else {
                    rx=(x-startX)/rowWidth;
                }
                // Calculate textureX and clamp it between 0 and image width
                textureX=Math.min( Math.max( (rx*(texture.getWidth())), 0 ), texture.getWidth() - 1);

                if(x >= 0 && x < resultWidth){
                    result.setRGB((int)(x), y, texture.getRGB((int)Math.floor(textureX), y));
                }
            }
        }
        return result;
    }
    
    /////////////////////////////////////////////////////
    /// UTILITY
    /////////////////////////////////////////////////////
    
    
    
    // Absolute (pixels) to screenspace
    public static double[] absToScreen(double x, double y){
        return new double[] {x - sCameraX + WIDTH / 2, (y - sCameraY + HEIGHT / 2) * HEIGHT_SCALE};
    }   
    public static double[] tileToScreen(double xTiles, double yTiles){
        return new double[] {(xTiles * TILE_SIZE - sCameraX) + WIDTH/2, ((yTiles * TILE_SIZE - sCameraY) + HEIGHT/2) * HEIGHT_SCALE};
    }
    public static double[] chunkToScreen(double xChunks, double yChunks){
        return new double[] {(xChunks * TILE_SIZE * 10 - sCameraX) + WIDTH / 2, ((yChunks * TILE_SIZE * 10 - sCameraY) + HEIGHT / 2) * HEIGHT_SCALE};
    }
    // Screenspace (2D, pixels) to 2.5D (pixels)
    public static double[] screenTo3D(double x, double y){
        return new double[] {
            -x * FOCAL_LENGTH / ((y + Z_OFFSET) * Math.cos(CAMERA_ANGLE)) + Gui.WIDTH/2,
            ((Y_OFFSET)* Math.sin(CAMERA_ANGLE)) * (FOCAL_LENGTH / ((y + Z_OFFSET) * Math.cos(CAMERA_ANGLE)))

        };
    }
}