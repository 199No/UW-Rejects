package Enemies;


//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 

//-------------------------------------------------//
//                   Enemies                       //
//-------------------------------------------------// 
public class Enemies {
    ///////////////
    //Properties
    ///////////////
    protected int health;
    protected int damage;
    protected int speed;
    protected int xPos;
    protected int yPos;
    protected int width;
    protected int height;
    protected boolean alert = false;
    protected boolean angry = false;
    ///////////////
    //Constuctor
    //////////////
    public Enemies(){
        System.out.println("Enemies!");
    }
    
    //-------------------------------------------------//
    //                    Methods                      //
    //-------------------------------------------------// 

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public int[] getLocation() {
        return new int[]{getxPos(), getyPos()};
    }

}