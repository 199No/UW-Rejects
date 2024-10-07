package src;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import java.awt.AWTException;
//-------------------------------------------------//
//                     Main                        //
//-------------------------------------------------// 
public class Main{
    ///////////////
    //Properties
    //////////////

    ///////////////
    //Constuctor
    //////////////
    public static void main(String[] args) throws AWTException{
        new Game();


        Sounds SirMixALot = new Sounds();
        SirMixALot.CountrysidePlay();
        SirMixALot.WalkingPlay();        
    }
//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 

}
