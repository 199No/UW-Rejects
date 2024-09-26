import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
public class Gui extends JPanel{
    public static final double FOCAL_LENGTH = -400; 
    int width;
    int height;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    ArrayList<GraphicsRunnable> drawQueue;
    JFrame frame = new JFrame("The Divided Realms INDEV");
    BufferedImage[] playerImages;
    public Gui(int width, int height, Input input) {
        playerImages = new BufferedImage[4];
        try {
        playerImages[0] = ImageIO.read(new File("Images\\Player\\Old Player Stuff\\player_LL-1.png.png"));
        playerImages[1] = ImageIO.read(new File("Images\\Player\\Old Player Stuff\\player_UL.png.png"));
        playerImages[2] = ImageIO.read(new File("Images\\Player\\Old Player Stuff\\player_RL.png-1.png.png"));
        playerImages[3] = ImageIO.read(new File("Images\\Player\\Old Player Stuff\\player_UL.png.png"));


        } catch (Exception e){e.printStackTrace();}
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
    }
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
    public void background(int r, int g, int b){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                g2d.setColor(new Color(r, g, b));
                g2d.fillRect(0, 0, width, height);
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
                BufferedImage playerImage = playerImages[((p.yDir == 1)? 0 : 1) + ((p.isRunning)? 2 : 0)];
                AffineTransform f = AffineTransform.getScaleInstance(-p.xDir(), 1);
                f.translate((int)(-p.xDir() * p.x() - playerImage.getWidth() / 20), p.y());
                f.scale(0.1, 0.1);
                g2d.drawImage(playerImage, f, null);
            }
        });
    }

    
    public double width() { return width; }
    public double height() { return height; }
    public double getFocalLength(){
        return FOCAL_LENGTH;
    }
}