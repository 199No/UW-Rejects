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
    ///////////////
    AudioInputStream clipAnvilHitaudioInputStream;                  //Define the Audio InputStream  (Do this once for each Clip)
    Clip clipAnvilHit;                                              //Define the Clip               (Do this once for each Sound)
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
    AudioInputStream clipTrumpOliveraudioInputStream;
    Clip clipTrumpOliver;
    AudioInputStream clipSlimeAttackaudioInputStream;
    Clip clipSlimeAttack;
    AudioInputStream clipSlimeDeathaudioInputStream;
    Clip clipSlimeDeath;
    AudioInputStream clipSlimeMoveaudioInputStream;
    Clip clipSlimeMove;
    AudioInputStream clipRollaudioInputStream;
    Clip clipRoll;
    AudioInputStream clipBuildCreepaudioInputStream;
    Clip clipBuildCreep;
    AudioInputStream clipWalkingFullaudioInputStream;
    Clip clipWalkingFull; 
    AudioInputStream clipWalking1audioInputStream;
    Clip clipWalking1; 
    AudioInputStream clipWalking2audioInputStream;
    Clip clipWalking2; 
    AudioInputStream clipWalking3audioInputStream;
    Clip clipWalking3; 
    AudioInputStream clipWalking4audioInputStream;
    Clip clipWalking4; 
    AudioInputStream clipWalking5audioInputStream;
    Clip clipWalking5; 
    AudioInputStream clipWalking6audioInputStream;
    Clip clipWalking6; 
    AudioInputStream clipWalking7audioInputStream;
    Clip clipWalking7; 
    AudioInputStream clipWalking8audioInputStream;
    Clip clipWalking8; 
    AudioInputStream clipWalking9audioInputStream;
    Clip clipWalking9; 
    AudioInputStream clipRandomBeataudioInputStream;
    Clip clipRandomBeat; 
    AudioInputStream clipFlowerDanceaudioInputStream;
    Clip clipFlowerDance; 
    AudioInputStream clipDogBarkaudioInputStream;
    Clip clipDogBark; 
    AudioInputStream clipCricketsAmbientaudioInputStream;
    Clip clipCricketsAmbient; 
    AudioInputStream clipKeyboardTypingaudioInputStream;
    Clip clipKeyboardTyping; 
    AudioInputStream clipMouseClickaudioInputStream;
    Clip clipMouseClick; 
    AudioInputStream clipFallAmbientaudioInputStream;
    Clip clipFallAmbient; 
    AudioInputStream clipSummerAmbientaudioInputStream;
    Clip clipSummerAmbient; 
    AudioInputStream clipSnowyStepaudioInputStream;
    Clip clipSnowyStep; 
    AudioInputStream clipSwordSwipeaudioInputStream;
    Clip clipSwordSwipe; 

    /*
        AudioInputStream clipTemp10audioInputStream;
        Clip clipTemp10; 
     */

    //When Sound is created define all Sounds 
    public Sounds(){
        try{
            //Use the Already Define variables and assign them their values 
            //Defineing all hear preloads all the Sounds that way their is no lag in the game
            clipAnvilHitaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Misc Sounds\\AnvilHit.wav"));
            clipAnvilHit = AudioSystem.getClip();
            clipBangaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\EasterEgg\\Bang.wav"));
            clipBang = AudioSystem.getClip();
            clipCarnivalaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\EasterEgg\\Carnival.wav"));
            clipCarnival = AudioSystem.getClip();
            clipCountrysideaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Background Music\\Countryside.wav"));
            clipCountryside = AudioSystem.getClip();
            clipDeathaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\EasterEgg\\Death.wav"));
            clipDeath = AudioSystem.getClip();
            clipForestWalkaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Background Music\\ForestWalk.wav"));
            clipForestWalk = AudioSystem.getClip();
            clipGameboySoundaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\EasterEgg\\GameboySound.wav"));
            clipGameboySound = AudioSystem.getClip();
            clipLevelUpaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\EasterEgg\\LevelUp.wav"));
            clipLevelUp = AudioSystem.getClip();
            clipMoneyChingaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\EasterEgg\\MoneyChing.wav"));
            clipMoneyChing = AudioSystem.getClip();
            clipRainForestaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Background Music\\RainForest.wav"));
            clipRainForest = AudioSystem.getClip();
            clipSwordAttackaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Player Sounds\\SwordAttack.wav"));
            clipSwordAttack = AudioSystem.getClip();
            clipTrumpOliveraudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\EasterEgg\\TrumpOliver.wav"));
            clipTrumpOliver = AudioSystem.getClip();
            clipSlimeAttackaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Enemies Sounds\\SlimeAttack.wav"));
            clipSlimeAttack = AudioSystem.getClip();
            clipSlimeDeathaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Enemies Sounds\\SlimeDeath.wav"));
            clipSlimeDeath = AudioSystem.getClip();
            clipSlimeMoveaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Enemies Sounds\\SlimeMove.wav"));
            clipSlimeMove = AudioSystem.getClip();
            clipRollaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Player Sounds\\Roll.wav"));
            clipRoll = AudioSystem.getClip();
            clipBuildCreepaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Background Music\\BuildCreep.wav"));
            clipBuildCreep = AudioSystem.getClip();
            clipWalkingFullaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Player Sounds\\Walking\\FullWalking.wav"));
            clipWalkingFull = AudioSystem.getClip();
            clipWalking1audioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Player Sounds\\Walking\\Walking1.wav"));
            clipWalking1 = AudioSystem.getClip();
            clipWalking2audioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Player Sounds\\Walking\\Walking2.wav"));
            clipWalking2 = AudioSystem.getClip();
            clipWalking3audioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Player Sounds\\Walking\\Walking3.wav"));
            clipWalking3 = AudioSystem.getClip();
            clipWalking4audioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Player Sounds\\Walking\\Walking4.wav"));
            clipWalking4 = AudioSystem.getClip();
            clipWalking5audioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Player Sounds\\Walking\\Walking5.wav"));
            clipWalking5 = AudioSystem.getClip();
            clipWalking6audioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Player Sounds\\Walking\\Walking6.wav"));
            clipWalking6 = AudioSystem.getClip();
            clipWalking7audioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Player Sounds\\Walking\\Walking7.wav"));
            clipWalking7 = AudioSystem.getClip();
            clipWalking8audioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Player Sounds\\Walking\\Walking8.wav"));
            clipWalking8 = AudioSystem.getClip();
            clipWalking9audioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Player Sounds\\Walking\\Walking9.wav"));
            clipWalking9 = AudioSystem.getClip();
            clipRandomBeataudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Background Music\\RandomBeat.wav"));
            clipRandomBeat = AudioSystem.getClip();
            clipFlowerDanceaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Background Music\\FlowerDance.wav"));
            clipFlowerDance = AudioSystem.getClip();
            clipDogBarkaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Misc Sounds\\DogBark.wav"));
            clipDogBark = AudioSystem.getClip();
            clipCricketsAmbientaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Background Music\\CricketsAmbient.wav"));
            clipCricketsAmbient = AudioSystem.getClip();
            clipKeyboardTypingaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Misc Sounds\\KeyboardTyping.wav"));
            clipKeyboardTyping = AudioSystem.getClip();
            clipMouseClickaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Misc Sounds\\MouseClick.wav"));
            clipMouseClick = AudioSystem.getClip();
            clipFallAmbientaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Background Music\\FallAmbient.wav"));
            clipFallAmbient = AudioSystem.getClip();
            clipSummerAmbientaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Background Music\\SummerAmbient.wav"));
            clipSummerAmbient = AudioSystem.getClip();
            clipSnowyStepaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Player Sounds\\Walking\\SnowyStep.wav"));
            clipSnowyStep = AudioSystem.getClip();
            clipSwordSwipeaudioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Player Sounds\\SwordSwipe.wav"));
            clipSwordSwipe = AudioSystem.getClip();
            
            /*
                clipTemp10audioInputStream = AudioSystem.getAudioInputStream(new File("Sounds\\Player Sounds\\Walking\\Walking9.wav"));
                clipTemp10 = AudioSystem.getClip();
             */
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



    //Play method is unviersal for all sounds
    public void Play(String clipType){
        //Takes in the name of the Sound that wants to be played
        try{
            //If statements find which sound the Player is talking about.
            if(clipType == "AnvilHit"){
                //If we have found the right sound play that sound
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
            else if(clipType == "TrumpOliver"){
                clipTrumpOliver.open(clipTrumpOliveraudioInputStream);
                clipTrumpOliver.start();
            }
            else if(clipType == "SlimeAttack"){
                clipSlimeAttack.open(clipSlimeAttackaudioInputStream);
                clipSlimeAttack.start();
            }
            else if(clipType == "SlimeDeath"){
                clipSlimeDeath.open(clipSlimeDeathaudioInputStream);
                clipSlimeDeath.start();
            }
            else if(clipType == "SlimeMove"){
                clipSlimeMove.open(clipSlimeMoveaudioInputStream);
                clipSlimeMove.start();
            }
            else if(clipType == "Roll"){
                clipRoll.open(clipRollaudioInputStream);
                clipRoll.start();
            }
            else if(clipType == "BuildCreep"){
                clipBuildCreep.open(clipBuildCreepaudioInputStream);
                clipBuildCreep.start();
            }
            else if(clipType == "WalkingFull"){
                clipWalkingFull.open(clipWalkingFullaudioInputStream);
                clipWalkingFull.start();
            }
            else if(clipType == "Walking1"){
                clipWalking1.open(clipWalking1audioInputStream);
                clipWalking1.start();
            }
            else if(clipType == "Walking2"){
                clipWalking2.open(clipWalking2audioInputStream);
                clipWalking2.start();
            }
            else if(clipType == "Walking3"){
                clipWalking3.open(clipWalking3audioInputStream);
                clipWalking3.start();
            }
            else if(clipType == "Walking4"){
                clipWalking4.open(clipWalking4audioInputStream);
                clipWalking4.start();
            }
            else if(clipType == "Walking5"){
                clipWalking5.open(clipWalking5audioInputStream);
                clipWalking5.start();
            }
            else if(clipType == "Walking6"){
                clipWalking6.open(clipWalking6audioInputStream);
                clipWalking6.start();
            }
            else if(clipType == "Walking7"){
                clipWalking7.open(clipWalking7audioInputStream);
                clipWalking7.start();
            }
            else if(clipType == "Walking8"){
                clipWalking8.open(clipWalking8audioInputStream);
                clipWalking8.start();
            }
            else if(clipType == "Walking9"){
                clipWalking9.open(clipWalking9audioInputStream);
                clipWalking9.start();
            }
            else if(clipType == "RandomBeat"){
                clipRandomBeat.open(clipRandomBeataudioInputStream);
                clipRandomBeat.start();
            }
            else if(clipType == "FlowerDance"){
                clipFlowerDance.open(clipFlowerDanceaudioInputStream);
                clipFlowerDance.start();
            }
            else if(clipType == "DogBark"){
                clipDogBark.open(clipDogBarkaudioInputStream);
                clipDogBark.start();
            }
            else if(clipType == "CricketsAmbient"){
                clipCricketsAmbient.open(clipCricketsAmbientaudioInputStream);
                clipCricketsAmbient.start();
            }
            else if(clipType == "KeyboardTyping"){
                clipKeyboardTyping.open(clipKeyboardTypingaudioInputStream);
                clipKeyboardTyping.start();
            }
            else if(clipType == "MouseClick"){
                clipMouseClick.open(clipMouseClickaudioInputStream);
                clipMouseClick.start();
            }
            else if(clipType == "FallAmbient"){
                clipFallAmbient.open(clipFallAmbientaudioInputStream);
                clipFallAmbient.start();
            }
            else if(clipType == "SummerAmbient"){
                clipSummerAmbient.open(clipSummerAmbientaudioInputStream);
                clipSummerAmbient.start();
            }
            else if(clipType == "SnowyStep"){
                clipSnowyStep.open(clipSnowyStepaudioInputStream);
                clipSnowyStep.start();
            }
            else if(clipType == "SwordSwipe"){
                clipSwordSwipe.open(clipSwordSwipeaudioInputStream);
                clipSwordSwipe.start();
            }

            /*        
                else if(clipType == "Temp10"){
                    clipTemp10.open(clipTemp10audioInputStream);
                    clipTemp10.start();
                } 
            */
        }
        
        //Cacth for the program
        catch (IOException | LineUnavailableException e) {
            //If something hapeens print it out
            e.printStackTrace();
        }
    }



    //Stop method is unviresal for all sounds
    public void Stop(String clipType){
        //Takes in the name of the sound that needs to be stopped
        if(clipType == "AnvilHit"){
            //If the correct sounds has been found then stop it
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
        else if(clipType == "TrumpOliver"){
            clipTrumpOliver.flush();
            clipTrumpOliver.stop();
        }
        else if(clipType == "SlimeAttack"){
            clipSlimeAttack.flush();
            clipSlimeAttack.stop();
        }
        else if(clipType == "SlimeDeath"){
            clipSlimeDeath.flush();
            clipSlimeDeath.stop();
        }
        else if(clipType == "SlimeMove"){
            clipSlimeMove.flush();
            clipSlimeMove.stop();
        }
        else if(clipType == "Roll"){
            clipRoll.flush();
            clipRoll.stop();
        }
        else if(clipType == "BuildCreep"){
            clipBuildCreep.flush();
            clipBuildCreep.stop();
        }
        else if(clipType == "WalkingFull"){
            clipWalkingFull.flush();
            clipWalkingFull.stop();
        }
        else if(clipType == "Walking1"){
            clipWalking1.flush();
            clipWalking1.stop();
        }
        else if(clipType == "Walking2"){
            clipWalking2.flush();
            clipWalking2.stop();
        }
        else if(clipType == "Walking3"){
            clipWalking3.flush();
            clipWalking3.stop();
        }
        else if(clipType == "Walking4"){
            clipWalking4.flush();
            clipWalking4.stop();
        }
        else if(clipType == "Walking5"){
            clipWalking5.flush();
            clipWalking5.stop();
        }
        else if(clipType == "Walking6"){
            clipWalking6.flush();
            clipWalking6.stop();
        }
        else if(clipType == "Walking7"){
            clipWalking7.flush();
            clipWalking7.stop();
        }
        else if(clipType == "Walking8"){
            clipWalking8.flush();
            clipWalking8.stop();
        }
        else if(clipType == "Walking9"){
            clipWalking9.flush();
            clipWalking9.stop();
        }
        else if(clipType == "RandomBeat"){
            clipRandomBeat.flush();
            clipRandomBeat.stop();
        }
        else if(clipType == "FlowerDance"){
            clipFlowerDance.flush();
            clipFlowerDance.stop();
        }
        else if(clipType == "DogBark"){
            clipDogBark.flush();
            clipDogBark.stop();
        }
        else if(clipType == "CricketsAmbient"){
            clipCricketsAmbient.flush();
            clipCricketsAmbient.stop();
        }
        else if(clipType == "KeyboardTyping"){
            clipKeyboardTyping.flush();
            clipKeyboardTyping.stop();
        }
        else if(clipType == "MouseClick"){
            clipMouseClick.flush();
            clipMouseClick.stop();
        }
        else if(clipType == "FallAmbient"){
            clipFallAmbient.flush();
            clipFallAmbient.stop();
        }
        else if(clipType == "SummerAmbient"){
            clipSummerAmbient.flush();
            clipSummerAmbient.stop();
        }
        else if(clipType == "SnowyStep"){
            clipSnowyStep.flush();
            clipSnowyStep.stop();
        }
        else if(clipType == "SwordSwipe"){
            clipSwordSwipe.flush();
            clipSwordSwipe.stop();
        }


        /*
            else if(clipType == "Temp10"){
                clipTemp10.flush();
                clipTemp10.stop();
            }
         */
    }
}