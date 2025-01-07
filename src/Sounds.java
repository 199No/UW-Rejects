package src;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import javax.sound.sampled.*;


//-------------------------------------------------//
//                    Sounds                       //
//-------------------------------------------------// 
public class Sounds {

    //Create list
    File[] soundFiles;
    String[] soundNames;
    AudioInputStream[] soundAudio;
    Clip[] soundClip;
    ArrayList<File> tempsoundFiles = new ArrayList<File>();
    ArrayList<String> tempSoundNames = new ArrayList<String>();


    public Sounds(){

        //Load the sounds
        recursiveSoundLoad("Sounds");

        //Set the size of the Sound classes
        soundFiles = new File[tempsoundFiles.size()];
        soundNames = new String[tempSoundNames.size()];
        soundClip = new Clip[tempSoundNames.size()];
        soundAudio = new AudioInputStream[tempsoundFiles.size()];

        try{

            // Load tempSoundList into SoundList
            for(int i = 0; i < soundFiles.length; i++){
                soundFiles[i] = tempsoundFiles.get(i);
                soundAudio[i] = AudioSystem.getAudioInputStream(soundFiles[i]);
            }


            // Load tempSoundNames into soundNames
            for(int i = 0; i < soundNames.length; i++){
                soundNames[i] = tempSoundNames.get(i);
                soundClip[i] = AudioSystem.getClip();
            }

        }
        //Cacth for the program
        catch (IOException | LineUnavailableException | UnsupportedAudioFileException error) {

            //If something hapeens print it out
            error.printStackTrace();
        }
    }


    //SoundLoading Method
    private void recursiveSoundLoad(String folderPath){
        // New file from the path
        File folder = new File(folderPath);
        // If folder is actually a folder (not an sound)...
        if(!folder.toString().endsWith(".wav")){
            // Get the contents of the folder
            File[] contents = folder.listFiles();
            // Search the contents of the folder
            for(int i = 0; i < contents.length; i++){
                recursiveSoundLoad(contents[i].getAbsolutePath());
            }
        }
        // If folder is an sound...
        else {
            // ImageIO error handling
            try {
                // Add this sound to the list
                tempsoundFiles.add(folder);
                // Add its name to the list of names
                tempSoundNames.add(folder.getName().replace(".wav", ""));
            } catch(Exception e){
                // ImageIO shouldn't throw errors bruh
                System.out.println("This REALLY shouldn't happen. SOUND EDITION");
            }
        }
    }

    //Play Sound Method
    public void playSound(String name){

        try{

            //For every SoundName
            for(int i = 0; i < soundNames.length; i++){

                //If the SoundName equal the Given Soundname
                if(soundNames[i].equals(name)){

                    //Play the Clip
                    soundClip[i].open(soundAudio[i]);
                    soundClip[i].start();
                }
            }
        }

        //Cacth for the program
        catch (IOException | LineUnavailableException error) {

            //If something hapeens print it out
            error.printStackTrace();
        }
    }
    
    //Stop Sound Method
    public void stopSound(String name){

        //For every SoundName
        for(int i = 0; i < soundNames.length; i++){

            //If the SoundName equal the Given Soundname
            if(soundNames[i].equals(name)){

                //Stop the Clip
                soundClip[i].flush();
                soundClip[i].stop();
            }
        }
    }
}