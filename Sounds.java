//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;


//-------------------------------------------------//
//                    Sounds                       //
//-------------------------------------------------// 
public class Sounds {

    //Creates a varible called Clip
    private Clip clip;

    //When a sound is made take the filePath
    public Sounds(String soundFilePath){

        
        try {
            
            //make the SoundFile Var = the SoundFile Given
            File soundFile = new File(soundFilePath);
            
            //Get the Audio from the Soundfile
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

            //Make the clip equal to the Audio
            clip = AudioSystem.getClip();
            
            //Open the Clip
            clip.open(audioInputStream);
        } 


        //Cacth for the program
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            //If something hapeens print it out
            e.printStackTrace();

        }
    }

    //If method playSound is called
    public void playSound(){
        
        //Play the clip of that sound
        clip.start(); 

    }

    //If method stopSound is called
    public void stopSound() {

            //Flush and Close that clip
            clip.flush();
            clip.close(); 

            
    }
}