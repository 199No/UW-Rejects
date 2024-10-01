package Enemies;
/*
 * DO NOT DELETE 
 * Come up with about 10 Enemies 2-3 each biome + THE BOSS FOR EACH ZONE
 * Make a basic Java class with consutructor for making a emeny 
 * Think of what methods were gonna need 
 * EX: Take Damage, Set Health, Deal Damage, GetLocation, SetLocation, Etc
 * Vaules in this class (Health, Location, DamageOutput, etc)
 * Decided between yourselves whethere this class should be supered and make a java classes for each boss / mob
 * 
 * MOB IDEAS
 * 
 * Forest: Slimes King, Slimes
 * Desert: Gaint Scropains, Skelton
 * Swamp:  Gaint Frog, Bugs
 * Tundra: Yeti, Eskimos
 * 
 */

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