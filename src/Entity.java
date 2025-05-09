package src;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public abstract class Entity{
    public static final int TYPE_PLAYER = 5;
    double x,y,width,height;
    Rectangle hitbox;
    int type;

    public Entity(double x, double y, double width, double height, Rectangle hitbox){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hitbox = hitbox;
    }
    public void setType(int type){
        this.type = type;
    }
    public int getType(){
        return type;
    }   
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public void setPos(double x, double y){
        this.x = x;
        this.y = y;
    }
    public void setX(double newX){
        this.x = newX;
    }
    public void setY(double newY){
        this.y = newY;
    }
    public double getWidth(){
        return this.width;
    }
    public double getHeight(){
        return this.height;
    }
    // Returns a hitbox with an x and a y relative to the x and y of this Entity.
    public Rectangle getRelHitbox(){
        return this.hitbox;
    }
    // Returns a hitbox with an x and a y relative to the origin.
    public Rectangle getAbsHitbox(){
        return new Rectangle(
            (int)(this.x + this.hitbox.getX()),
            (int)(this.y + this.hitbox.getY()),
            (int)(this.hitbox.getWidth()),
            (int)(this.hitbox.getHeight())
        );
    }
    public abstract BufferedImage getImage();

    public abstract BufferedImage getShadowImage();
}
