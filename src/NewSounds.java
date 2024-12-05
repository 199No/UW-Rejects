package src;
//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import javax.sound.sampled.*;


/*
 * When u remake a clip even if its the exact same it still wont work (AKA u cant refernce the same clip)
 * This is why stop sound isnt working because u redifne the clip
 * A fix Ive tought of is to make to more List
 * One list is all the clips and another being all the audiostreams
 * This way when u call a play sound instead of finding the name and make a clip var
 * U find the name and just play sound stright from the array
 */



//-------------------------------------------------//
//                    Sounds                       //
//-------------------------------------------------// 
public class NewSounds {

    //Create list
    File[] soundFiles;
    String[] soundNames;
    ArrayList<File> tempsoundFiles = new ArrayList<File>();
    ArrayList<String> tempSoundNames = new ArrayList<String>();


    public NewSounds(){

        //Load the sounds
        recursiveSoundLoad("Sounds");

        //Set the size of the Sound classes
        soundFiles = new File[tempsoundFiles.size()];
        soundNames = new String[tempSoundNames.size()];


        // Load tempSoundList into SoundList
        for(int i = 0; i < soundFiles.length; i++)
        soundFiles[i] = tempsoundFiles.get(i);


        // Load tempSoundNames into soundNames
        for(int i = 0; i < soundNames.length; i++)
            soundNames[i] = tempSoundNames.get(i);
    }
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

            //Define the Audio and Clip
            AudioInputStream PlayclipAudio;
            Clip Playclip; 

            //For every SoundName
            for(int i = 0; i < soundNames.length; i++){

                //If the SoundName equal the Given Soundname
                if(soundNames[i].equals(name)){



                    //Give the Vars Data
                    PlayclipAudio = AudioSystem.getAudioInputStream(soundFiles[i]);
                    Playclip = AudioSystem.getClip();

                    //Play the Clip
                    Playclip.open(PlayclipAudio);
                    Playclip.start();
                }
            }
        }

        //Cacth for the program
        catch (IOException | LineUnavailableException | UnsupportedAudioFileException error) {

            //If something hapeens print it out
            error.printStackTrace();
        }
    }
    
    //Play Sound Method
    public void stopSound(String name){
        try{

            //Define the Audio and Clip
            AudioInputStream StopClipAudio;
            Clip Stopclip; 

            //For every SoundName
            for(int i = 0; i < soundNames.length; i++){

                //If the SoundName equal the Given Soundname
                if(soundNames[i].equals(name)){

                    //Give the Vars Data
                    StopClipAudio = AudioSystem.getAudioInputStream(soundFiles[i]);
                    Stopclip = AudioSystem.getClip();

                    //Stop the Clip
                    Stopclip.flush();
                    Stopclip.stop();
                }
            }
        }

        //Cacth for the program
        catch (IOException | LineUnavailableException | UnsupportedAudioFileException error) {

            //If something hapeens print it out
            error.printStackTrace();
        }
    }
}
