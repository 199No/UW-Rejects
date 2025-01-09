package src;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Images {
    BufferedImage[] imageList;
    String[] imageNames;
    int transparency;
    GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment()
                                        .getDefaultScreenDevice()
                                            .getDefaultConfiguration();

    ArrayList<BufferedImage> tempImageList = new ArrayList<BufferedImage>();
    ArrayList<String> tempImageNames = new ArrayList<String>();
    public Images(String folderPath, int transparency){
        this.transparency = transparency;
        //Load the images (see recursiveImageLoad)
        recursiveImageLoad(folderPath);
        imageList = new BufferedImage[tempImageList.size()];
        imageNames = new String[tempImageNames.size()];

        // Load tempImageList into imageList
        for(int i = 0; i < imageList.length; i++)
            imageList[i] = tempImageList.get(i);
        // Load tempImageNames into imageNames
        for(int i = 0; i < imageNames.length; i++)
            imageNames[i] = tempImageNames.get(i);
    }
    // Load images via a depth-first search for them.
    private void recursiveImageLoad(String folderPath){
        // New file from the path
        File folder = new File(folderPath);
        // If folder is actually a folder (not an image)...
        if(!folder.toString().endsWith(".png")){
            // Get the contents of the folder
            File[] contents = folder.listFiles();
            // Search the contents of the folder for images
            for(int i = 0; i < contents.length; i++){
                recursiveImageLoad(contents[i].getAbsolutePath());
            }
        }
        // If folder is an image...
        else {
            // ImageIO error handling
            try {
                Image image = ImageIO.read(folder);
                BufferedImage bimage = config.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
                Graphics bGraphics = bimage.getGraphics();
                bGraphics.drawImage(image, 0, 0, null);
                bGraphics.dispose();
                // Add this image to the list
                tempImageList.add(bimage);
                // Add its name to the list of names
                tempImageNames.add(folder.getName().replace(".png", ""));
            } catch(Exception e){
                // ImageIO shouldn't throw errors bruh
                System.out.println("This REALLY shouldn't happen.");
                e.printStackTrace();
            }
        }
    }
    public BufferedImage getImage(String name){
        for(int i = 0; i < imageNames.length; i++){
            if(imageNames[i].equals(name)){
                return imageList[i];
            }
        }
        return null;
    }
    public BufferedImage getImage(int id){
        return imageList[id];
    }

}
