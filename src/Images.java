package src;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
public class Images {
    Image[] imageList;
    ArrayList<Image> tempImageList = new ArrayList<Image>();
    public Images(){
        recursiveImageLoad("Images");
        for(int i = 0; i < tempImageList.size(); i++){
            System.out.println(tempImageList.get(i).getWidth(null));
        }
    }
    private void recursiveImageLoad(String folderPath){
        File folder = new File(folderPath);
        if(!folder.toString().endsWith(".png")){
            File[] contents = folder.listFiles();
            for(int i = 0; i < contents.length; i++){
                recursiveImageLoad(contents[i].getAbsolutePath());
            }
        }
        else {
            try {
                tempImageList.add(ImageIO.read(folder));
            } catch(Exception e){
                System.out.println("This REALLY shouldn't happen.");
            }
        }
    }
}
