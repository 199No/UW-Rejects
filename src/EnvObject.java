package src;

import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

public class EnvObject extends Entity{
    private static Images images = new Images("Images/Enviroment", Transparency.BITMASK);
    
    private static double[][] possibleDimensions = new double[][] {
        {0, 0},
        {Gui.TILE_SIZE, Gui.TILE_SIZE * Gui.HEIGHT_SCALE},
        {Gui.TILE_SIZE, Gui.TILE_SIZE * Gui.HEIGHT_SCALE},
        {Gui.TILE_SIZE * 3, Gui.TILE_SIZE * 3},
        {Gui.TILE_SIZE * 3, Gui.TILE_SIZE * 3},
        {Gui.TILE_SIZE, Gui.TILE_SIZE},
        {Gui.TILE_SIZE, Gui.TILE_SIZE},
        {Gui.TILE_SIZE, Gui.TILE_SIZE},
        {Gui.TILE_SIZE, Gui.TILE_SIZE * Gui.HEIGHT_SCALE},
        {Gui.TILE_SIZE * 2, Gui.TILE_SIZE * 3},
        {Gui.TILE_SIZE, Gui.TILE_SIZE},
        {Gui.TILE_SIZE, Gui.TILE_SIZE},
        {Gui.TILE_SIZE, Gui.TILE_SIZE * Gui.HEIGHT_SCALE},
        {Gui.TILE_SIZE, Gui.TILE_SIZE * Gui.HEIGHT_SCALE},
        {Gui.TILE_SIZE, Gui.TILE_SIZE * Gui.HEIGHT_SCALE}
    };
    Rectangle hitbox;
    BufferedImage image;
    BufferedImage shadowImage;
    double xPos, yPos;
    int type;
    boolean collidable;
    double width, height;
    boolean isFlat;
    public EnvObject(double xPos, double yPos, int type){
        super(xPos, yPos, possibleDimensions[type][0], possibleDimensions[type][1], EnvObject.createHitbox(type));
        this.xPos = xPos;
        this.yPos = yPos;
        this.type = type;
        this.hitbox = createHitbox(type);
        switch(type){
            case 1:
                // Large airwall
                if(Game.inDebugMode()){
                    image = images.getImage("X");
                } else {
                    image = new BufferedImage(5, 5, Transparency.BITMASK);
                }
                this.collidable = true;
                isFlat = true;
                break;
            case 2:
                // Small airwall
                if(Game.inDebugMode()){
                    image = images.getImage("X").getSubimage(5, 5, Gui.TILE_SIZE - 5, Gui.TILE_SIZE - 5);
                } else {
                    image = new BufferedImage(5, 5, Transparency.BITMASK);
                }
                this.collidable = true;
                isFlat = true;
                break;
            case 3:
                image = images.getImage("tree1");
                this.collidable = true;
                isFlat = false;
                break;
            case 4:
                image = images.getImage("tree2");
                this.collidable = true;
                isFlat = false;
                break;
            case 5:
                // Pink flower
                image = images.getImage("flowers").getSubimage(0, 0, Gui.TILE_SIZE, Gui.TILE_SIZE);
                this.collidable = false;
                isFlat = false;
                break;
            case 6:
                // Purple flower
                image = images.getImage("flowers").getSubimage(Gui.TILE_SIZE, 0, Gui.TILE_SIZE, Gui.TILE_SIZE);
                this.collidable = false;
                isFlat = false;
                break;
            case 7:
                // Blue flower
                image = images.getImage("flowers").getSubimage(0, Gui.TILE_SIZE, Gui.TILE_SIZE, Gui.TILE_SIZE);
                this.collidable = false;  
                isFlat = false;
                break;
            case 8:
                image = images.getImage("lilypad");
                this.collidable = false;
                isFlat = false;
                break;
            case 9:
                image = images.getImage("cactus");
                this.collidable = true;
                isFlat = false;
                break;
            case 10:
                image = images.getImage("rocks").getSubimage(0, 0, 32, 32);
                this.collidable = false;
                isFlat = false;
                break;
            case 11:
                image = images.getImage("rocks").getSubimage(0, 32, 32, 32);
                this.collidable = false;
                isFlat = false;
                break;
            case 12:
                image = images.getImage("bonePile").getSubimage(0, 0, 32, 32);
                this.collidable = false;
                isFlat = true;
                break;
            case 13:
                image = images.getImage("bonePile").getSubimage(32, 0, 32, 32);
                this.collidable = false;
                isFlat = true;
                break;
            case 14:
                image = images.getImage("bonePile").getSubimage(0, 32, 32, 32);
                this.collidable = false;
                isFlat = true;
                break;
        }
        shadowImage = Gui.toShadow(image);
        hitbox.translate((int)this.xPos, (int)this.yPos);
    }
    public BufferedImage getImage(){
        return image;
    }
    public BufferedImage getShadowImage(){
        return shadowImage;
    }
    public boolean isFlat(){
        return isFlat;
    }
    public static Rectangle createHitbox(int type){
        switch(type){
            case 1:
                return new Rectangle(0, 0, Gui.TILE_SIZE, (int)(Gui.TILE_SIZE * Gui.HEIGHT_SCALE));
            case 2:
                return new Rectangle(Gui.TILE_SIZE / 8, (Gui.TILE_SIZE / 8), (int)(Gui.TILE_SIZE * 0.75), (int)(Gui.TILE_SIZE * 0.75 * Gui.HEIGHT_SCALE));

            case 3:
                return new Rectangle(Gui.TILE_SIZE, Gui.TILE_SIZE * 3, Gui.TILE_SIZE, Gui.TILE_SIZE);
                                
            case 4:
                return new Rectangle(Gui.TILE_SIZE, Gui.TILE_SIZE * 3, Gui.TILE_SIZE, Gui.TILE_SIZE);
                

            case 5:
                return new Rectangle(0, 0, 1, 1);
                
                
            case 6:
                return new Rectangle(0, 0, 1, 1);
                
            case 7:
                // Blue flower
                return new Rectangle(0, 0, 1, 1);
                      
            case 8:
                return new Rectangle(0, 0, 1, 1);

            case 9:
                return new Rectangle((int)(Gui.TILE_SIZE * 0.5), Gui.TILE_SIZE * 2, Gui.TILE_SIZE, Gui.TILE_SIZE);
            case 10:
                return new Rectangle(0, 0, 1, 1);
            case 11:
                return new Rectangle(0, 0, 1, 1);
            case 12:
                return new Rectangle(0, 0, 1, 1);
            case 13:
                return new Rectangle(0, 0, 1, 1);
            case 14:
                return new Rectangle(0, 0, 1, 1);
    

                
        }
        return new Rectangle();
    }
}
