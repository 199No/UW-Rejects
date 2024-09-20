import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.awt.event.*;
public class Gui extends JPanel{
    public static final double FOCAL_LENGTH = -400; 
    int width;
    int height;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    ArrayList<GraphicsRunnable> drawQueue;
    JFrame frame = new JFrame("The Divided Realms INDEV");
    public Gui(int width, int height, Input input){
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
                g2d.drawRect((int)p.x() - 10, (int)p.y() - 10, 20, 20);
            }
        });
    }

    
    public double width() { return width; }
    public double height() { return height; }
    public double getFocalLength(){
        return FOCAL_LENGTH;
    }
}