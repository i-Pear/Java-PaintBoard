package Layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Layer_Bitmap extends Layer {

    Image image;

    public Layer_Bitmap(Image image){
        this.image=image;
    }

    @Override
    public void draw(GraphicsContext graphics) {
        // graphics.draw
    }

}
