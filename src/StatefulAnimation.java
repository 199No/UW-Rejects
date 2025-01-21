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
    boolean autoLoop;
    int lastStep;
    public StatefulAnimation(int frameTime, int widthFrames, int heightFrames, int[][] options, BufferedImage spriteSheet, boolean autoLoop){
        this.frameTime = frameTime;
        this.widthFrames = widthFrames;
        this.heightFrames = heightFrames;
        frameWidth = spriteSheet.getWidth() / widthFrames;
        frameHeight = spriteSheet.getHeight() / heightFrames;
        this.states = options;
        this.spriteSheet = spriteSheet;
        state = 0;
        step = 0;
        lastStep = (int)System.currentTimeMillis();
    }
    public int getCurState(){
        return this.state;
    }
    public void setState(int state){
        this.state = state;
    }
    public int[][] getStates(){
        return this.states;
    }
    public void incrementState(int amount){
        this.state += amount;
    }
    public BufferedImage getCurFrame(){
        state = 1;
        if((int)System.currentTimeMillis() - lastStep > frameTime){
            lastStep = (int)System.currentTimeMillis();
            step ++;
        }
        if(step > states[state].length - 1){
            step = 0;
        }
        int curFrame = states[state][step];
        return spriteSheet.getSubimage(
            (curFrame % widthFrames) * frameWidth,
            (curFrame / widthFrames) * frameHeight,
            frameWidth,
            frameHeight
        );
    }
}