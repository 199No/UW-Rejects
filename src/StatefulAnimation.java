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
    public void incrementState(){
        this.state++;
        this.step = 0;
    }
    public int getCurStep(){
        return this.step;
    }
    public boolean isLastStep(int state){
        return getCurStep() ==  getStates()[state].length - 1;
    }
    public BufferedImage getCurFrame(boolean isDashing){
        if ((int) System.currentTimeMillis() - lastStep > frameTime) {
            lastStep = (int) System.currentTimeMillis();
            step++;
        }

        if (step > states[state].length - 1) {
            if (state == 1 && isDashing) {
                // Loop in state 1 while dashing
                step = 0;
            } else {
                // Go to the next state, unless it's the last state
                step = 0;
                if (state < states.length - 1) {
                    incrementState();
                }
            }
        }

        /* 
        int curFrame = states[state][step];
        return spriteSheet.getSubimage(
            (curFrame % widthFrames) * frameWidth,
            (curFrame / widthFrames) * frameHeight,
            frameWidth,
            frameHeight
        );
        */
        int curFrame = states[state][step];

        // Ensure curFrame is within the valid range
        int totalFrames = widthFrames * heightFrames;
        if (curFrame < 0 || curFrame >= totalFrames) {
            // Handle the error, e.g., log it, or set curFrame to a default value
            curFrame = 0;  // or some default frame
        }

        // Calculate the x and y coordinates
        int x = (curFrame % widthFrames) * frameWidth;
        int y = (curFrame / widthFrames) * frameHeight;

        // Ensure x and y are within bounds
        if (x + frameWidth > spriteSheet.getWidth()) {
            x = spriteSheet.getWidth() - frameWidth;  // adjust to fit within bounds
        }
        if (y + frameHeight > spriteSheet.getHeight()) {
            y = spriteSheet.getHeight() - frameHeight;  // adjust to fit within bounds
        }

        // Now safely get the subimage
        return spriteSheet.getSubimage(x, y, frameWidth, frameHeight);
    }
    public int getStateStepCount(int state) {
        return states[state].length;
    }
    public int getCurrentStateStepCount() {
        return getStates()[getCurState()].length;
    }
}