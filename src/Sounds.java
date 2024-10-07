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
public class Sounds {
    ///////////////
    //Properties
    //////////////
    Clip clipAnvilHit;
    Clip clipBang;
    Clip clipCarnival;
    Clip clipDeath;
    Clip clipGameboySound;
    Clip clipLevelUp;
    Clip clipMoneyChing;

    ///////////////
    //Constuctor
    //////////////
    //Dont do anything when a sound is created
    public Sounds(){} 



//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 

    public void AnvilHitPlay(){
        try {
            
            //make the SoundFile Var = the SoundFile Given
            File soundFileLevelUp = new File("Sounds\\AnvilHit.wav");
            
            //Get the Audio from the Soundfile
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileLevelUp);

            //Make the clip equal to the Audio
            clipAnvilHit = AudioSystem.getClip(); // Create a new Clip for each sound
            
            //Open the Clip
            clipAnvilHit.open(audioInputStream);

            //Play the clip of that sound
            clipAnvilHit.start();
        } 
        //Cacth for the program
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            //If something hapeens print it out
            e.printStackTrace();

        }
    }
    public void AnvilHitStop() {
        clipAnvilHit.flush();
        clipAnvilHit.close(); 
    }


    public void BangPlay(){
        try {
            
            //make the SoundFile Var = the SoundFile Given
            File soundFileLevelUp = new File("Sounds\\Bang.wav");
            
            //Get the Audio from the Soundfile
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileLevelUp);

            //Make the clip equal to the Audio
            clipBang = AudioSystem.getClip(); // Create a new Clip for each sound
            
            //Open the Clip
            clipBang.open(audioInputStream);

            //Play the clip of that sound
            clipBang.start();
        } 
        //Cacth for the program
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            //If something hapeens print it out
            e.printStackTrace();

        }
    }
    public void BangStop() {
        clipBang.flush();
        clipBang.close(); 
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


    public void DeathPlay(){
        try {
            
            //make the SoundFile Var = the SoundFile Given
            File soundFileLevelUp = new File("Sounds\\Death.wav");
            
            //Get the Audio from the Soundfile
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileLevelUp);

            //Make the clip equal to the Audio
            clipDeath = AudioSystem.getClip(); // Create a new Clip for each sound
            
            //Open the Clip
            clipDeath.open(audioInputStream);

            //Play the clip of that sound
            clipDeath.start();
        } 
        //Cacth for the program
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            //If something hapeens print it out
            e.printStackTrace();

        }
    }
    public void DeathStop() {
        clipDeath.flush();
        clipDeath.close(); 
    }


    public void GameboySoundPlay(){
        try {
            
            //make the SoundFile Var = the SoundFile Given
            File soundFileLevelUp = new File("Sounds\\GameboySound.wav");
            
            //Get the Audio from the Soundfile
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileLevelUp);

            //Make the clip equal to the Audio
            clipGameboySound = AudioSystem.getClip(); // Create a new Clip for each sound
            
            //Open the Clip
            clipGameboySound.open(audioInputStream);

            //Play the clip of that sound
            clipGameboySound.start();
        } 
        //Cacth for the program
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            //If something hapeens print it out
            e.printStackTrace();

        }
    }
    public void GameboySoundStop() {
        clipGameboySound.flush();
        clipGameboySound.close(); 
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


    public void MoneyChingPlay(){
        try {
            
            //make the SoundFile Var = the SoundFile Given
            File soundFileLevelUp = new File("Sounds\\MoneyChing.wav");
            
            //Get the Audio from the Soundfile
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileLevelUp);

            //Make the clip equal to the Audio
            clipMoneyChing = AudioSystem.getClip(); // Create a new Clip for each sound
            
            //Open the Clip
            clipMoneyChing.open(audioInputStream);

            //Play the clip of that sound
            clipMoneyChing.start();
        } 
        //Cacth for the program
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            //If something hapeens print it out
            e.printStackTrace();

        }
    }
    public void MoneyChingStop() {
        clipMoneyChing.flush();
        clipMoneyChing.close(); 
    }

    

}