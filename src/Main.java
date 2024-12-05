package src;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import java.awt.AWTException;
import java.io.IOException;
//-------------------------------------------------//
//                     Main                        //
//-------------------------------------------------// 
public class Main{
    ///////////////
    //Properties
    //////////////

    ///////////////
    //Constuctor
    /////////////

//   |
//   V 
    public static void main(String[] args) throws AWTException, IOException{
        new Game();

        NewSounds SirMixALot = new NewSounds();
        SirMixALot.playSound("BuildCreep");
        SirMixALot.playSound("FlowerDance");
        SirMixALot.stopSound("FlowerDance");
        //SirMixALot.Play("AnvilHit");
        //SirMixALot.Stop("AnvilHit");
        
    }
//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 

}
