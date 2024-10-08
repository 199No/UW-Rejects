package src;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;


//-------------------------------------------------//
//                    Sounds                       //
//-------------------------------------------------// 
public class Sounds{
    ///////////////
    //Properties
    //////////////
    AudioInputStream AnvilHitaudioInputStream;
    Clip clipAnvilHit;
    AudioInputStream clipBangaudioInputStream;
    Clip clipBang;

    ///////////////
    //Constuctor
    //////////////
    //Dont do anything when a sound is created
    public Sounds(){
        try{
            AnvilHitaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\AnvilHit.wav"));
            clipAnvilHit = AudioSystem.getClip();
            
            clipBangaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Bang.wav"));
            clipBang = AudioSystem.getClip();
            
        }  









        //Cacth for the program
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            //If something hapeens print it out
            e.printStackTrace();
        }


    } 



//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 

    public void Play(String clipType){
        try{
            if(clipType == "AnvilHit"){
                clipAnvilHit.open(AnvilHitaudioInputStream);
                clipAnvilHit.start();
            }
            else if(clipType == "Bang"){
                clipBang.open(clipBangaudioInputStream);
                clipBang.start();
            }
        }
        catch (IOException | LineUnavailableException e) {
            //If something hapeens print it out
            e.printStackTrace();
        }
    }

    public void Stop(String clipType){
        if(clipType == "AnvilHit"){
            clipAnvilHit.flush();
            clipAnvilHit.close(); 
        }
        else if(clipType == "Bang"){
            clipBang.flush();
            clipBang.stop();
        }
    }
}