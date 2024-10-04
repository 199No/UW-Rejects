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
    Clip clipCountryside;
    Clip clipDeath;
    Clip clipForestWalk;
    Clip clipGameboySound;
    Clip clipLevelUp;
    Clip clipMoneyChing;
    Clip clipRainForest;
    Clip clipWalking;

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


    public void CountrysidePlay(){
        try {
            
            //make the SoundFile Var = the SoundFile Given
            File soundFileLevelUp = new File("Sounds\\Countryside.wav");
            
            //Get the Audio from the Soundfile
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileLevelUp);

            //Make the clip equal to the Audio
            clipCountryside = AudioSystem.getClip(); // Create a new Clip for each sound
            
            //Open the Clip
            clipCountryside.open(audioInputStream);

            //Play the clip of that sound
            clipCountryside.start();
        } 
        //Cacth for the program
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            //If something hapeens print it out
            e.printStackTrace();

        }
    }
    public void CountrysideStop() {
        clipCountryside.flush();
        clipCountryside.close(); 
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


    public void ForestWalkPlay(){
        try {
            
            //make the SoundFile Var = the SoundFile Given
            File soundFileLevelUp = new File("Sounds\\ForestWalk.wav");
            
            //Get the Audio from the Soundfile
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileLevelUp);

            //Make the clip equal to the Audio
            clipForestWalk = AudioSystem.getClip(); // Create a new Clip for each sound
            
            //Open the Clip
            clipForestWalk.open(audioInputStream);

            //Play the clip of that sound
            clipForestWalk.start();
        } 
        //Cacth for the program
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            //If something hapeens print it out
            e.printStackTrace();

        }
    }
    public void ForestWalkStop() {
        clipForestWalk.flush();
        clipForestWalk.close(); 
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


    public void RainForestPlay(){
        try {
            
            //make the SoundFile Var = the SoundFile Given
            File soundFileLevelUp = new File("Sounds\\RainForest.wav");
            
            //Get the Audio from the Soundfile
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileLevelUp);

            //Make the clip equal to the Audio
            clipRainForest = AudioSystem.getClip(); // Create a new Clip for each sound
            
            //Open the Clip
            clipRainForest.open(audioInputStream);

            //Play the clip of that sound
            clipRainForest.start();
        } 
        //Cacth for the program
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            //If something hapeens print it out
            e.printStackTrace();

        }
    }
    public void RainForestStop() {
        clipRainForest.flush();
        clipRainForest.close(); 
    }


    public void WalkingPlay(){
        try {
            
            //make the SoundFile Var = the SoundFile Given
            File soundFileLevelUp = new File("Sounds\\Walking.wav");
            
            //Get the Audio from the Soundfile
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileLevelUp);

            //Make the clip equal to the Audio
            clipWalking = AudioSystem.getClip(); // Create a new Clip for each sound
            
            //Open the Clip
            clipWalking.open(audioInputStream);

            //Play the clip of that sound
            clipWalking.start();
        } 
        //Cacth for the program
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            //If something hapeens print it out
            e.printStackTrace();

        }
    }
    public void WalkingStop() {
        clipWalking.flush();
        clipWalking.close(); 
    }


}