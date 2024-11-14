package src;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
public class Animation {
    private int startTime; // Animation start time
    private int widthFrames; // How many frames horizontally the sheet is wide
    private int heightFrames; // How many frames vertically the sheet is tall
    private int frameWidth;
    private int frameHeight;
    private int numFrames;
    private int frameTime;
    private boolean isLooping;
    private int step;
    private BufferedImage sheet;

    public Animation(BufferedImage sheet, int widthFrames, int heightFrames, int numFrames, int frameTime, boolean isLooping){ 
        this.widthFrames = widthFrames;
        this.heightFrames = heightFrames;
        this.frameWidth = sheet.getWidth() / widthFrames;
        this.frameHeight = sheet.getWidth() / heightFrames;
        this.numFrames = numFrames;
        this.isLooping = isLooping;
        this.frameTime = frameTime;
        this.sheet = sheet;
        startTime = (int)System.currentTimeMillis();
        step = 0;
    }
    public void start(){
        startTime = (int)System.currentTimeMillis();
    }
    public BufferedImage getFrame(){
        // Get current step
        step = (int)(((int)System.currentTimeMillis() - startTime) / frameTime);
        // Handle looping/animation end
        if(step >= numFrames){
            if(isLooping){
                startTime = (int)System.currentTimeMillis();
                step = 0;
            } else {
                step = numFrames;
            }
        }
        // Determine which frame to draw
        int x = (step % widthFrames) * frameWidth;
        int y = (step / widthFrames) * frameHeight;
        return sheet.getSubimage(x, y, frameWidth, frameHeight);
    }

}
