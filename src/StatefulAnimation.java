package src;

import java.awt.image.BufferedImage;

public class StatefulAnimation {
    int frameTime;
    int widthFrames;
    int heightFrames;
    int[][] states;
    int state;
    int step;
    BufferedImage spriteSheet;
    int frameWidth;
    int frameHeight;
    public StatefulAnimation(int frameTime, int widthFrames, int heightFrames, int[][] options, BufferedImage spriteSheet){
        this.frameTime = frameTime;
        this.widthFrames = widthFrames;
        this.heightFrames = heightFrames;
        frameWidth = spriteSheet.getWidth() / widthFrames;
        frameHeight = spriteSheet.getHeight() / heightFrames;
        this.states = options;
        this.spriteSheet = spriteSheet;
        state = 0;
        step = 0;
    }
    public void setState(int state){
        this.state = state;
    }
    public BufferedImage getCurFrame(){
        if(step >= states[state].length){
            step = 0;
        }
        int curFrame = states[state][step];
        return spriteSheet.getSubimage(
            (curFrame % widthFrames) * ,

        )
    }
}
