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
    AudioInputStream clipAnvilHitaudioInputStream;
    Clip clipAnvilHit;
    AudioInputStream clipBangaudioInputStream;
    Clip clipBang;
    AudioInputStream clipCarnivalaudioInputStream;
    Clip clipCarnival; 
    AudioInputStream clipCountrysideaudioInputStream;
    Clip clipCountryside;
    AudioInputStream clipDeathaudioInputStream;
    Clip clipDeath;
    AudioInputStream clipForestWalkaudioInputStream;
    Clip clipForestWalk;
    AudioInputStream clipGameboySoundaudioInputStream;
    Clip clipGameboySound;
    AudioInputStream clipLevelUpaudioInputStream;
    Clip clipLevelUp;
    AudioInputStream clipMoneyChingaudioInputStream;
    Clip clipMoneyChing;
    AudioInputStream clipRainForestaudioInputStream;
    Clip clipRainForest;
    AudioInputStream clipSwordAttackaudioInputStream;
    Clip clipSwordAttack;
    AudioInputStream clipWalkingaudioInputStream;
    Clip clipWalking;


    ///////////////
    //Constuctor
    //////////////
    //Dont do anything when a sound is created
    public Sounds(){
        try{
            clipAnvilHitaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\AnvilHit.wav"));
            clipAnvilHit = AudioSystem.getClip();
            clipBangaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Bang.wav"));
            clipBang = AudioSystem.getClip();
            clipCarnivalaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Carnival.wav"));
            clipCarnival = AudioSystem.getClip();
            clipCountrysideaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Countryside.wav"));
            clipCountryside = AudioSystem.getClip();
            clipDeathaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Death.wav"));
            clipDeath = AudioSystem.getClip();
            clipForestWalkaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\ForestWalk.wav"));
            clipForestWalk = AudioSystem.getClip();
            clipGameboySoundaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\GameboySound.wav"));
            clipGameboySound = AudioSystem.getClip();
            clipLevelUpaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\LevelUp.wav"));
            clipLevelUp = AudioSystem.getClip();
            clipMoneyChingaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\MoneyChing.wav"));
            clipMoneyChing = AudioSystem.getClip();
            clipRainForestaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\RainForest.wav"));
            clipRainForest = AudioSystem.getClip();
            clipSwordAttackaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\SwordAttack.wav"));
            clipSwordAttack = AudioSystem.getClip();
            clipWalkingaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Walking.wav"));
            clipWalking = AudioSystem.getClip();
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
                clipAnvilHit.open(clipAnvilHitaudioInputStream);
                clipAnvilHit.start();
            }
            else if(clipType == "Bang"){
                clipBang.open(clipBangaudioInputStream);
                clipBang.start();
            }
            else if(clipType == "Carnival"){
                clipCarnival.open(clipCarnivalaudioInputStream);
                clipCarnival.start();
            }
            else if(clipType == "CountrySide"){
                clipCountryside.open(clipCountrysideaudioInputStream);
                clipCountryside.start();
            }
            else if(clipType == "Death"){
                clipDeath.open(clipDeathaudioInputStream);
                clipDeath.start();
            }
            else if(clipType == "ForestWalk"){
                clipForestWalk.open(clipForestWalkaudioInputStream);
                clipForestWalk.start();
            }
            else if(clipType == "GameboySound"){
                clipGameboySound.open(clipGameboySoundaudioInputStream);
                clipGameboySound.start();
            }
            else if(clipType == "LevelUp"){
                clipLevelUp.open(clipLevelUpaudioInputStream);
                clipLevelUp.start();
            }
            else if(clipType == "MoneyChing"){
                clipMoneyChing.open(clipMoneyChingaudioInputStream);
                clipMoneyChing.start();
            }
            else if(clipType == "RainForest"){
                clipRainForest.open(clipRainForestaudioInputStream);
                clipRainForest.start();
            }
            else if(clipType == "SwordAttack"){
                clipSwordAttack.open(clipSwordAttackaudioInputStream);
                clipSwordAttack.start();
            }
            else if(clipType == "Walking"){
                clipWalking.open(clipWalkingaudioInputStream);
                clipWalking.start();
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
        else if(clipType == "Carnival"){
            clipCarnival.flush();
            clipCarnival.stop();
        }
        else if(clipType == "CountrySide"){
            clipCountryside.flush();
            clipCountryside.stop();
        }
        else if(clipType == "Death"){
            clipDeath.flush();
            clipDeath.stop();
        }
        else if(clipType == "ForestWalk"){
            clipForestWalk.flush();
            clipForestWalk.stop();
        }
        else if(clipType == "GameboySound"){
            clipGameboySound.flush();
            clipGameboySound.stop();
        }
        else if(clipType == "LevelUp"){
            clipLevelUp.flush();
            clipLevelUp.stop();
        }
        else if(clipType == "MoneyChing"){
            clipMoneyChing.flush();
            clipMoneyChing.stop();
        }
        else if(clipType == "RainForest"){
            clipRainForest.flush();
            clipRainForest.stop();
        }
        else if(clipType == "SwordAttack"){
            clipSwordAttack.flush();
            clipSwordAttack.stop();
        }
        else if(clipType == "Walking"){
            clipWalking.flush();
            clipWalking.stop();
        }
    }
}