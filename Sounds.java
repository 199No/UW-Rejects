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



    //When a sound is made take the filePath
    public Sounds(String soundFilePath){

        
        try {
            
            //make the SoundFile Var = the SoundFile Given
            File soundFile = new File(soundFilePath);
            
            //Get the Audio from the Soundfile
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

            //Make the clip equal to the Audio
            Clip clip = AudioSystem.getClip(); // Create a new Clip for each sound
            
            //Open the Clip
            clip.open(audioInputStream);

            //Play the clip of that sound
            clip.start();
        } 


        //Cacth for the program
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            //If something hapeens print it out
            e.printStackTrace();

        }
    }

}