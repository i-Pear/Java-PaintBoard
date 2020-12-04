package Layers;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.media.jai.remote.SerializableRenderedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;

/**
 * Implement for bitmap image layer
 */
public class Layer_Bitmap extends Layer {

    SerializableRenderedImage image;
    Image memoryImage;
    int pos_x, pos_y;

    public Layer_Bitmap(SerializableRenderedImage image, int x, int y) {
        this.image = image;
        pos_x=x;
        pos_y=y;
    }

    public static Image convertRenderedImage(RenderedImage img) {
        try{
            File file= File.createTempFile("temp",".png");
            ImageIO.write(img,"png",file);
            // System.out.println("File IO");
            return new Image(new FileInputStream(file.getAbsolutePath()));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void draw(GraphicsContext graphics) {
        if(memoryImage==null){
            memoryImage=convertRenderedImage(image);
        }
        graphics.drawImage(memoryImage, pos_x + x_shifting, pos_y + y_shifting);
    }

    public void deleteCache(){
        memoryImage=null;
    }

    @Override
    public boolean isInner(float x, float y) {
        Rectangle2D rectangle_outer = new Rectangle2D(pos_x, pos_y, pos_x + image.getWidth(), pos_y + image.getHeight());
        return rectangle_outer.contains(x, y);
    }

    @Override
    public void applyShifting() {
        pos_x += x_shifting;
        pos_y += y_shifting;
        x_shifting = y_shifting = 0;
    }

    @Override
    public Layer getClone() {
        return new Layer_Bitmap(image,pos_x,pos_y);
    }

}
