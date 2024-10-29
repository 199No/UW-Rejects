package src;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import javax.imageio.ImageIO;
import javax.swing.*;

import Enemies.Enemies;

import java.awt.*;
import java.util.ArrayList;
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
    public static final double FOCAL_LENGTH = -400; 
    int width;
    int height;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    ArrayList<GraphicsRunnable> drawQueue;
    JFrame frame = new JFrame("The Divided Realms INDEV");
    BufferedImage[] playerImages;
    int lastPlayerXDir = -1;
    double lastPlayerStep;
    BufferedImage[] envImages;
    int step;
    ///////////////
    //Constuctor
    //////////////
    public Gui(int width, int height, Input input) {
        playerImages = new BufferedImage[5];
        try {
            /* 
            playerImages[0] = ImageIO.read(new File("Images\\Player\\Old Player Stuff\\player_LL-1.png.png"));
            playerImages[1] = ImageIO.read(new File("Images\\Player\\Old Player Stuff\\player_UL.png.png"));
            playerImages[2] = ImageIO.read(new File("Images\\Player\\Old Player Stuff\\player_RL.png-1.png.png"));
            playerImages[3] = ImageIO.read(new File("Images\\Player\\Old Player Stuff\\player_UL.png.png"));
            */


            // *********** Figure out how to get sections of the spread sheet, line 130 does not work which should be 
            //   the line to determine the x and y size for the sections of the spreadsheet ******************8
            playerImages[0] = ImageIO.read(new File("Images\\Player\\Player spritesheet.png"));
            playerImages[1] = ImageIO.read(new File("Images\\Player\\Player spritesheet.png"));
            playerImages[2] = ImageIO.read(new File("Images\\Player\\Player spritesheet.png"));
            playerImages[3] = ImageIO.read(new File("Images\\Player\\Player spritesheet.png"));
            playerImages[4] = ImageIO.read(new File("Images\\Player\\Player spritesheet.png"));


        } catch (Exception e){e.printStackTrace();}
            envImages = new BufferedImage[5];
        try{
            envImages[0] = ImageIO.read(new File("Images\\Enviroment\\Tiles\\Snow Tile.png.png"));
        } catch (Exception e){e.printStackTrace();};
        this.width = WIDTH;
        this.height = HEIGHT;
        this.setSize(width, height);
        frame.setSize(width, height);

        frame.setVisible(true);
        this.setVisible(true);

        frame.addMouseMotionListener(input);
        frame.addKeyListener(input);
        frame.setFocusable(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        drawQueue = new ArrayList<GraphicsRunnable>();
        lastPlayerStep = System.currentTimeMillis();
        step = 0;
    }

//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 

    // Paint renamed
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for(int i = 0; i < drawQueue.size(); i++){
            drawQueue.get(i).draw(g2d);
        }
        drawQueue.clear();
    }
    public void addToQueue(GraphicsRunnable g){
        drawQueue.add(g);
    }
    public void background(int r, int g, int b){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                try {
                    g2d.setColor(new Color(r, g, b));
                    g2d.fillRect(0, 0, width, height);
                    for(int y = 0; y < 12; y++){
                        for(int x = 0; x < 24; x++){
                            AffineTransform a = AffineTransform.getScaleInstance(0.4, 0.4);
                            a.translate(x* 38.4 * 2.5, y * 38.4 * 2.5);
                            g2d.drawImage(envImages[0], a, null);
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    public void displayFPS(int n){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                g2d.setColor(new Color(0, 0, 0));
                g2d.drawString(String.valueOf(n), 40, 60);
            }
        });
    }
    public void drawPlayer(Player p){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
            //    BufferedImage playerImage = playerImages[4].getSubimage(step * 19, 0, 19, 19);
                AffineTransform f = AffineTransform.getScaleInstance(1, 1);
                if(p.getXDir() > 0){
                    f.translate((int)(p.getxPos()), p.getyPos());
                } else {
                    //f.translate((int)(p.getxPos() + playerImage.getWidth() * 2), p.getyPos());
                }
                f.scale(p.getXDir() * 2, 2);
                g2d.drawRect((int)p.getxPos(), (int)p.getyPos(), 38, 38);
                //g2d.drawImage(playerImage, f, null);
                if(System.currentTimeMillis() - lastPlayerStep > 200){
                    step = (step + 1) % 4;
                    lastPlayerStep = System.currentTimeMillis();
                }
            }
        });
    }

    
    public double width() { return width; }
    public double height() { return height; }
    public double getFocalLength(){
        return FOCAL_LENGTH;
    }

    public void drawEnemies(ArrayList<Enemies> enemies){
        //given an arraylist type enemies
        //draw enemies based on their x and y positon {use getxPos() getyPos()}
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                try {
                    for(int i = 0; i < enemies.size(); i ++){
                        BufferedImage slimeImage = ImageIO.read(new File("Images\\Enemys\\Slime.png"));
                        AffineTransform a = AffineTransform.getScaleInstance(1, 1);
                        a.translate(enemies.get(i).getxPos(), enemies.get(i).getyPos() - 10);
                        a.scale(0.1, 0.1);
                        g2d.drawImage(slimeImage, a, null);
                    }
                }
                catch(Exception e){
                    System.out.println("This shouldn't happen.");
                }
            }
        });

    }
}
