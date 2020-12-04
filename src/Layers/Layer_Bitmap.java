package Layers;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Layer_Bitmap extends Layer {

    Image image;
    int pos_x, pos_y;

    public Layer_Bitmap(Image image, int x, int y) {
        this.image = image;
        pos_x=x;
        pos_y=y;
    }

    @Override
    public void draw(GraphicsContext graphics) {
        graphics.drawImage(image, pos_x + x_shifting, pos_y + y_shifting);
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
