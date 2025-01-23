package src;

import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

public class EnvObject {
    private static Images images = new Images("Images/Enviroment", Transparency.BITMASK);
    Rectangle hitbox;
    BufferedImage image;
    double xPos, yPos;
    int type;
    boolean collidable;
    double width, height;
    boolean isFlat;
    public EnvObject(double xPos, double yPos, int type){
        this.xPos = xPos;
        this.yPos = yPos;
        this.type = type;
        switch(type){
            case 1:
                // Large airwall
                if(Game.inDebugMode){
                    image = images.getImage("X");
                } else {
                    image = new BufferedImage(5, 5, Transparency.BITMASK);
                }
                hitbox = new Rectangle(0, 0, Gui.TILE_SIZE, (int)(Gui.TILE_SIZE * Gui.HEIGHT_SCALE));
                width = Gui.TILE_SIZE;
                height = Gui.TILE_SIZE * Gui.HEIGHT_SCALE;
                this.collidable = true;
                isFlat = true;
                break;
            case 2:
                // Small airwall
                if(Game.inDebugMode){
                    image = images.getImage("X").getSubimage(1, 1, 22, 22);
                } else {
                    image = new BufferedImage(5, 5, Transparency.BITMASK);
                }
                hitbox = new Rectangle(Gui.TILE_SIZE / 4, (Gui.TILE_SIZE / 4), Gui.TILE_SIZE / 2, (int)(Gui.TILE_SIZE / 2 * Gui.HEIGHT_SCALE));
                width = Gui.TILE_SIZE;
                height = Gui.TILE_SIZE * Gui.HEIGHT_SCALE;
                this.collidable = true;
                isFlat = true;
                break;
            case 3:
                image = images.getImage("tree1");
                hitbox = new Rectangle(Gui.TILE_SIZE, Gui.TILE_SIZE * 3, Gui.TILE_SIZE, Gui.TILE_SIZE);
                
                width = Gui.TILE_SIZE * 3;
                height = Gui.TILE_SIZE * 3;
                this.collidable = true;
                isFlat = false;
                break;
                
            case 4:
                image = images.getImage("tree2");
                hitbox = new Rectangle(Gui.TILE_SIZE, Gui.TILE_SIZE * 2, Gui.TILE_SIZE, Gui.TILE_SIZE);
                width = Gui.TILE_SIZE * 3;
                height = Gui.TILE_SIZE * 3;
                this.collidable = true;
                isFlat = false;
                break;

            case 5:
                // Pink flower
                image = images.getImage("flowers").getSubimage(0, 0, 24, 24);
                hitbox = new Rectangle(0, 0, 1, 1);
                width = Gui.TILE_SIZE;
                height = Gui.TILE_SIZE;
                this.collidable = false;
                isFlat = false;
                break;
                
            case 6:
                // Purple flower
                image = images.getImage("flowers").getSubimage(24, 0, 24, 24);
                hitbox = new Rectangle(0, 0, 1, 1);
                width = Gui.TILE_SIZE;
                height = Gui.TILE_SIZE;
                this.collidable = false;
                isFlat = false;
                break;
            case 7:
                // Blue flower
                image = images.getImage("flowers").getSubimage(0, 24, 24, 24);
                hitbox = new Rectangle(0, 0, 1, 1);
                width = Gui.TILE_SIZE;
                height = Gui.TILE_SIZE;
                this.collidable = false;  
                isFlat = false;
                break;      
            case 8:
                image = images.getImage("lilypad");
                hitbox = new Rectangle(0, 0, 1, 1);
                width = Gui.TILE_SIZE;
                height = Gui.TILE_SIZE * Gui.HEIGHT_SCALE;
                this.collidable = false;
                isFlat = false;
                break;
        }
        hitbox.translate((int)this.xPos, (int)this.yPos);
    }
    public double x(){
        return xPos;
    }
    public double y(){
        return yPos;
    }
    public double width(){
        return width;
    }
    public double height(){
        return height;
    }
    public BufferedImage getImage(){
        return image;
    }
    public boolean isFlat(){
        return isFlat;
    }
    public Rectangle getHitbox(){
        return hitbox;
    }
}
