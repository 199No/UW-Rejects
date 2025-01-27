package src;

import java.awt.Rectangle;


public class Entity {

    double x,y,width,height;
    Rectangle hitbox;

    public Entity(double x, double y, double width, double height, Rectangle hitbox){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hitbox = hitbox;
    }
}
