package src;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import Enemies.Slime;
public class Main {
    public static void main(String[] args) throws IOException, AWTException{
      new Sounds();
      //Sounds.playSound("AnvilHit");
      new Game();
      
    }
}
