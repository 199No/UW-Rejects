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

    Clip clipLevelUp;
    Clip clipCarnival;

    //Dont do anything when a sound is created
    public Sounds(){} 
/*
    public void Test3Play(){
        try {
            
            //make the SoundFile Var = the SoundFile Given
            File soundFileLevelUp = new File("Sounds\\CARNIVAL(KanyeWest).wav");
            
            //Get the Audio from the Soundfile
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileLevelUp);

            //Make the clip equal to the Audio
            clipCarnival = AudioSystem.getClip(); // Create a new Clip for each sound
            
            //Open the Clip
            clipCarnival.open(audioInputStream);

            //Play the clip of that sound
            clipCarnival.start();
        } 
        //Cacth for the program
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            //If something hapeens print it out
            e.printStackTrace();

        }
    }
    public void Test3Stop() {
        clipCarnival.flush();
        clipCarnival.close(); 
    }

    
    public void Test3Play(){
        try {
            
            //make the SoundFile Var = the SoundFile Given
            File soundFileLevelUp = new File("Sounds\\CARNIVAL(KanyeWest).wav");
            
            //Get the Audio from the Soundfile
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileLevelUp);

            //Make the clip equal to the Audio
            clipCarnival = AudioSystem.getClip(); // Create a new Clip for each sound
            
            //Open the Clip
            clipCarnival.open(audioInputStream);

            //Play the clip of that sound
            clipCarnival.start();
        } 
        //Cacth for the program
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            //If something hapeens print it out
            e.printStackTrace();

        }
    }
    public void Test3Stop() {
        clipCarnival.flush();
        clipCarnival.close(); 
    }
 */

    public void Test2Play(){
        try {
            
            //make the SoundFile Var = the SoundFile Given
            File soundFileLevelUp = new File("Sounds\\CARNIVAL(KanyeWest).wav");
            
            //Get the Audio from the Soundfile
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileLevelUp);

            //Make the clip equal to the Audio
            clipCarnival = AudioSystem.getClip(); // Create a new Clip for each sound
            
            //Open the Clip
            clipCarnival.open(audioInputStream);

            //Play the clip of that sound
            clipCarnival.start();
        } 
        //Cacth for the program
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            //If something hapeens print it out
            e.printStackTrace();

        }
    }
    public void Test2Stop() {
        clipCarnival.flush();
        clipCarnival.close(); 
    }


    public void Test1Play(){
        try {
            
            //make the SoundFile Var = the SoundFile Given
            File soundFileLevelUp = new File("Sounds\\CARNIVAL(KanyeWest).wav");
            
            //Get the Audio from the Soundfile
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileLevelUp);

            //Make the clip equal to the Audio
            clipCarnival = AudioSystem.getClip(); // Create a new Clip for each sound
            
            //Open the Clip
            clipCarnival.open(audioInputStream);

            //Play the clip of that sound
            clipCarnival.start();
        } 
        //Cacth for the program
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            //If something hapeens print it out
            e.printStackTrace();

        }
    }
    public void Test1Stop() {
        clipCarnival.flush();
        clipCarnival.close(); 
    }


    public void CarnivalPlay(){
        try {
            
            //make the SoundFile Var = the SoundFile Given
            File soundFileLevelUp = new File("Sounds\\CARNIVAL(KanyeWest).wav");
            
            //Get the Audio from the Soundfile
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileLevelUp);

            //Make the clip equal to the Audio
            clipCarnival = AudioSystem.getClip(); // Create a new Clip for each sound
            
            //Open the Clip
            clipCarnival.open(audioInputStream);

            //Play the clip of that sound
            clipCarnival.start();
        } 
        //Cacth for the program
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            //If something hapeens print it out
            e.printStackTrace();

        }
    }
    public void CarnivalStop() {
        clipCarnival.flush();
        clipCarnival.close(); 
    }


    public void LevelUpPlay(){
        try {
            
            //make the SoundFile Var = the SoundFile Given
            File soundFileLevelUp = new File("Sounds\\LevelUp.wav");
            
            //Get the Audio from the Soundfile
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileLevelUp);

            //Make the clip equal to the Audio
            clipLevelUp = AudioSystem.getClip(); // Create a new Clip for each sound
            
            //Open the Clip
            clipLevelUp.open(audioInputStream);

            //Play the clip of that sound
            clipLevelUp.start();
        } 
        //Cacth for the program
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            //If something hapeens print it out
            e.printStackTrace();

        }
    }
    public void LevelUpStop() {
        clipLevelUp.flush();
        clipLevelUp.close(); 
    }

}