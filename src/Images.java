package src;
import java.io.File;
// Preloads images at the start of the program so that they are easier and faster to access.
public class Images {
    public Images(){
        File imageFolder = new File("Images");
        File[] folders = imageFolder.listFiles();
        for(int i = 0; i < folders.length; i++){
            System.out.println(folders[i].toString());
        }
    }
    private void recursiveImageGrab(String folderPath){
        File folder = new File(folderPath);
        if(!folder.toString().endsWith(".png")){
            File[] contents = folder.listFiles();
            for(int i = 0; i < contents.length; i++){

            }
        }
    }
}
