package src;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.Image;
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
                // BufferedImage bimage = ImageIO.read(folder);
                // // Add this image to the list
                // tempImageList.add(toCompatibleImage(bimage));
                // // Add its name to the list of names
                // tempImageNames.add(folder.getName().replace(".png", ""));
                
                Image image = ImageIO.read(folder);
                BufferedImage bimage;
                if(folderPath.contains("Flats") || folderPath.contains("Tiles")){
                    bimage = new BufferedImage(
                                (int)(((double)image.getWidth(null) / 24.0) * Gui.TILE_SIZE),
                                (int)(((double)image.getHeight(null) / 24.0) * Gui.TILE_SIZE * Gui.HEIGHT_SCALE),
                                transparency
                             );       
                } else {
                    bimage = new BufferedImage(
                                (int)(((double)image.getWidth(null) / 24.0) * Gui.TILE_SIZE),
                                (int)(((double)image.getHeight(null) / 24.0) * Gui.TILE_SIZE),
                                transparency
                             );       

                }
                Graphics bGraphics = bimage.getGraphics();
                bGraphics.drawImage(image, 0, 0, bimage.getWidth(), bimage.getHeight(), null);
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
    // Image optimization method stolen from Consty on StackOverflow: https://stackoverflow.com/questions/196890/java2d-performance-issues
    private BufferedImage toCompatibleImage(BufferedImage image)
{
    // obtain the current system graphical settings
    GraphicsConfiguration gfxConfig = GraphicsEnvironment.
        getLocalGraphicsEnvironment().getDefaultScreenDevice().
        getDefaultConfiguration();

    /*
     * if image is already compatible and optimized for current system 
     * settings, simply return it
     */
    if (image.getColorModel().equals(gfxConfig.getColorModel()))
        return image;

    // image is not optimized, so create a new image that is
    BufferedImage newImage = gfxConfig.createCompatibleImage(
            image.getWidth(), image.getHeight(), image.getTransparency());

    // get the graphics context of the new image to draw the old image on
    Graphics2D g2d = newImage.createGraphics();

    // actually draw the image and dispose of context no longer needed
    g2d.drawImage(image, 0, 0, null);
    g2d.dispose();

    // return the new optimized image
    return newImage; 
}
}
